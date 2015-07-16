package ru.qulix.olesyuknv.taskscontrol.activities;

import java.util.Calendar;
import java.util.Date;

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

import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.utils.DateFormatUtility;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.AddTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.threads.RemoveTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;
import ru.qulix.olesyuknv.taskscontrol.threads.UpdateTaskLoader;


/**
 * Форма создания и изменения задачи.
 *
 * @author QULIX-OLESYUKNV
 */
public class InputTaskActivity extends Activity {

    public static final String TASK_POSITION = InputTaskActivity.class + ".TASK_POSITION";
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
        setContentView(R.layout.activity_input_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);

        startDate = (Button) findViewById(R.id.startDate);
        setDateButtonListener(startDate);

        finishDate = (Button) findViewById(R.id.finishDate);
        setDateButtonListener(finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerAdapter(statusWork);

        ImageView deleteButton = (ImageView) findViewById(R.id.deleteButton);
        setDeleteButtonListener(deleteButton);

        ImageView saveButton = (ImageView) findViewById(R.id.addTaskButton);
        setSaveButtonListener(saveButton);

        ImageView changeButton = (ImageView) findViewById(R.id.changeTaskButton);
        setChangeButtonListener(changeButton);

        formInitialization(deleteButton, saveButton, changeButton);
    }

    private void formInitialization(ImageView deleteButton, ImageView saveButton, ImageView changeButton) {
        task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
        if (task != null) {
            setButtonsVisibility(deleteButton, saveButton, changeButton);
            fillingFormFields(task);
        }
    }

    private void fillingFormFields(Task task) {
        nameTask.setText(task.getName());
        workTime.setText(String.valueOf(task.getWorkTime()));
        startDate.setText(DateFormatUtility.format(task.getStartDate()));
        finishDate.setText(DateFormatUtility.format(task.getFinishDate()));
    }

    private void setButtonsVisibility(ImageView deleteButton, ImageView saveButton, ImageView changeButton) {
        deleteButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        changeButton.setVisibility(View.VISIBLE);
    }

    private Task getTask() {
        task.setName(nameTask.getText().toString());
        task.setWorkTime(Integer.parseInt(workTime.getText().toString()));
        task.setStartDate(DateFormatUtility.format(startDate.getText().toString()));
        task.setFinishDate(DateFormatUtility.format(finishDate.getText().toString()));
        task.setStatus((StatusTask) statusWork.getSelectedItem());
        return task;
    }

    private boolean isFieldsEmpty() {
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

    private void setChangeButtonListener(ImageView changeButtonListener) {
        changeButtonListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                execute(new UpdateTaskLoader(getServer(), InputTaskActivity.this));
            }
        });
    }

    private void setSaveButtonListener(ImageView saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new AddTaskLoader(getServer(), InputTaskActivity.this));
            }
        });
    }

    private void setDeleteButtonListener(ImageView deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new RemoveTaskLoader(getServer(), InputTaskActivity.this));
            }
        });
    }

    private void execute(AsyncTask<Task, Void, Void> thread) {
        if (isFieldsEmpty()) {
            Task task = getTask();
            thread.execute(task);
            setResult(RESULT_OK, getIntent());
            return;
        }
        Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
    }

    private void setDateButtonListener(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(InputTaskActivity.this, setDatePickerListener(date, calendar), year, month, day).show();
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
