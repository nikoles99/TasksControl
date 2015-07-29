package ru.qulix.olesyuknv.taskscontrol.activities;

import java.util.Calendar;
import java.util.Date;

import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.DateFormatUtility;

import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.AddTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.threads.RemoveTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.threads.UpdateTaskLoader;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Форма создания и изменения задачи.
 *
 * @author Q-OLN
 */
public class TaskActivity extends Activity {

    public static final String TASK_POSITION = TaskActivity.class + ".TASK_POSITION";
    public static final int REQUEST_CODE = 1;

    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
    private Spinner statusWork;
    private ImageView deleteButton;
    private ImageView saveButton;
    private ImageView changeButton;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initializationViews();
        task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
        formInitialization(task);
    }

    private void initializationViews() {
        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);

        startDate = (Button) findViewById(R.id.startDate);
        setDateButtonListener(startDate);

        finishDate = (Button) findViewById(R.id.finishDate);
        setDateButtonListener(finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerAdapter(statusWork);

        deleteButton = (ImageView) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new RemoveTaskLoader(getServer(), TaskActivity.this));
            }
        });

        saveButton = (ImageView) findViewById(R.id.addTaskButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new AddTaskLoader(getServer(), TaskActivity.this));
            }
        });

        changeButton = (ImageView) findViewById(R.id.changeTaskButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new UpdateTaskLoader(getServer(), TaskActivity.this));
            }
        });
    }

    private void formInitialization(Task task) {
        if (isTaskExist(task)) {
            setTask(task);
        } else {
            this.task = new Task();
        }
        setButtonsVisibility(task);
    }

    private void setTask(Task task) {
        this.task = task;
        nameTask.setText(task.getName());
        workTime.setText(String.valueOf(this.task.getWorkTime()));
        startDate.setText(DateFormatUtility.format(this.task.getStartDate()));
        finishDate.setText(DateFormatUtility.format(this.task.getFinishDate()));
    }

    private Task getTask() {
        if (isCorrectInput()) {
            task.setName(nameTask.getText().toString());
            task.setWorkTime(Integer.parseInt(workTime.getText().toString()));
            task.setStartDate(DateFormatUtility.format(startDate.getText().toString()));
            task.setFinishDate(DateFormatUtility.format(finishDate.getText().toString()));
            task.setStatus((StatusTask) statusWork.getSelectedItem());
            return task;
        }
        return null;
    }

    private void setButtonsVisibility(Task task) {
        deleteButton.setVisibility(isTaskExist(task) ? View.VISIBLE : View.INVISIBLE);
        saveButton.setVisibility((isTaskExist(task)) ? View.INVISIBLE : View.VISIBLE);
        changeButton.setVisibility((isTaskExist(task)) ? View.VISIBLE : View.INVISIBLE);
    }

    private boolean isTaskExist(Task task) {
        return task != null;
    }

    private boolean isCorrectInput() {
        return !(TextUtils.isEmpty(workTime.getText()) ||
                TextUtils.isEmpty(nameTask.getText()) ||
                TextUtils.isEmpty(startDate.getText()) ||
                TextUtils.isEmpty(finishDate.getText()) ||
                DateFormatUtility.format(startDate.getText().toString()).
                        after(DateFormatUtility.format(finishDate.getText().toString())));
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private void execute(AsyncTask<Task, Void, Void> thread) {
        Task task = getTask();

        if (isTaskExist(task)) {
            thread.execute(task);
            setResult(RESULT_OK, getIntent());
        } else {
            Toast.makeText(TaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDateButtonListener(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(TaskActivity.this, setDatePickerListener(date, calendar), year, month, day).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener setDatePickerListener(final Button date, final Calendar calendar) {
        return (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(DateFormatUtility.format(today));
            }
        });
    }

    private Spinner setSpinnerAdapter(Spinner statusWork) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this,
                android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        return statusWork;
    }
}
