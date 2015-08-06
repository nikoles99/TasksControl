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
import ru.qulix.olesyuknv.taskscontrol.threads.TaskLoader;
import ru.qulix.olesyuknv.taskscontrol.threads.RemoveTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.threads.UpdateTaskLoader;

import android.app.Activity;
import android.app.DatePickerDialog;
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
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
        boolean addMode = task == null;

        if (addMode) {
            task = new Task();
        }
        createView(addMode);
        setTask(task);
    }

    private void createView(final boolean addMode) {
        setContentView(R.layout.activity_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);

        startDate = (Button) findViewById(R.id.startDate);
        setDateButtonListener(startDate);

        finishDate = (Button) findViewById(R.id.finishDate);
        setDateButtonListener(finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerAdapter(statusWork);

        ImageView deleteButton = (ImageView) findViewById(R.id.deleteButton);
        deleteButton.setVisibility((!addMode) ? View.VISIBLE : View.INVISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new RemoveTaskLoader(getServer(), TaskActivity.this));
            }
        });

        ImageView saveButton = (ImageView) findViewById(R.id.addTaskButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMode) {
                    execute(new AddTaskLoader(getServer(), TaskActivity.this));
                } else {
                    execute(new UpdateTaskLoader(getServer(), TaskActivity.this));
                }
            }
        });
    }

    private void setTask(Task task) {
        this.task = task;
        nameTask.setText(task.getName());
        workTime.setText(String.valueOf(task.getWorkTime()));
        startDate.setText(DateFormatUtility.format(task.getStartDate()));
        finishDate.setText(DateFormatUtility.format(task.getFinishDate()));
    }

    private Task getTask() {
        if (validate()) {
            task.setName(nameTask.getText().toString());
            task.setWorkTime(Integer.parseInt(workTime.getText().toString()));
            task.setStartDate(DateFormatUtility.format(startDate.getText().toString()));
            task.setFinishDate(DateFormatUtility.format(finishDate.getText().toString()));
            task.setStatus((StatusTask) statusWork.getSelectedItem());
            return task;
        } else {
            return new Task();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(workTime.getText()) || !workTime.getText().toString().matches("^-?\\d+$")) {
            workTime.setError("Некоректное время");
            return false;
        }
        if (TextUtils.isEmpty(nameTask.getText())) {
            nameTask.setError("Некоректное имя");
            return false;
        }
        if (DateFormatUtility.format(startDate.getText().toString()).
                after(DateFormatUtility.format(finishDate.getText().toString()))) {
            startDate.setError("Не коректро введена начальная дата");
            return false;
        }
        return true;
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private void execute(TaskLoader thread) {
        Task task = getTask();

        if (task.isEmpty()) {
            thread.execute(task);
            setResult(RESULT_OK, getIntent());
        } else {
            Toast.makeText(getApplicationContext(), "Введите данные корректно", Toast.LENGTH_SHORT).show();
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
