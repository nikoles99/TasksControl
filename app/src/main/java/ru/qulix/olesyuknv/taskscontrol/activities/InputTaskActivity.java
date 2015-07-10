package ru.qulix.olesyuknv.taskscontrol.activities;

import java.util.Calendar;
import java.util.Date;

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

import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.ConvertDate;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.BackgroundAddingTask;
import ru.qulix.olesyuknv.taskscontrol.threads.BackgroundDeletingTask;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;
import ru.qulix.olesyuknv.taskscontrol.threads.BackgroundUpdatingTask;


/**
 * Форма создания и изменения задачи.
 *
 * @author QULIX-OLESYUKNV
 */
public class InputTaskActivity extends Activity {

    public static final String TASK_POSITION = "Position";
    public static final String ACTION = "Action";
    public static final int CHANGE_TASK_FLAG = 3;
    public static final int ADD_TASK_FLAG = 2;
    public static final int REQUEST_CODE = 1;

    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
    private Spinner statusWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);
        startDate = (Button) findViewById(R.id.startDate);
        finishDate = (Button) findViewById(R.id.finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerAdapter(statusWork);

        ImageView cancelButton = (ImageView) findViewById(R.id.cancelButton);
        cancelButtonOnClick(cancelButton);

        ImageView saveButton = (ImageView) findViewById(R.id.saveButton);
        saveButtonOnClick(saveButton);

        showDateDialog(startDate);
        showDateDialog(finishDate);
        initialViews();

    }

    private void saveButtonOnClick(ImageView saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingOrChangingTask();
            }
        });
    }

    private void addingOrChangingTask() {
        if (isFieldsEmpty()) {
            Task task = recreateTaskById(getIdSelectedTask());
            TaskServer server = ((TasksControlApplication) getApplicationContext()).getServer();
            switch (getModeOpenForm()) {
                case ADD_TASK_FLAG:
                    new BackgroundAddingTask(server, InputTaskActivity.this).execute(task);
                    break;
                case CHANGE_TASK_FLAG:
                    new BackgroundUpdatingTask(server, InputTaskActivity.this).execute(task);
                    break;
            }
            setResult(RESULT_OK, getIntent());
        } else {
            Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void cancelButtonOnClick(ImageView cancelButton) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTask();
                finish();
            }
        });
    }

    private int getModeOpenForm() {
        return getIntent().getIntExtra(ACTION, REQUEST_CODE);
    }

    private void removeTask() {
        switch (getModeOpenForm()) {
            case CHANGE_TASK_FLAG:
                new BackgroundDeletingTask(((TasksControlApplication) getApplicationContext()).getServer()).
                        execute(recreateTaskById(getIdSelectedTask()));
                setResult(RESULT_OK, getIntent());
                break;
        }
    }


    private void initialViews() {
        switch (getModeOpenForm()) {
            case CHANGE_TASK_FLAG:
                Task task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
                nameTask.setText(task.getName());
                workTime.setText(String.valueOf(task.getWorkTime()));
                startDate.setText(new ConvertDate().getString(task.getStartDate()));
                finishDate.setText(new ConvertDate().getString(task.getFinishDate()));
                break;
        }
    }

    private int getIdSelectedTask() {
        Task task = (Task) getIntent().getSerializableExtra(TASK_POSITION);

        if (task != null) {
            return task.getId();
        }
        return 0;
    }

    private boolean isFieldsEmpty() {
        return !(TextUtils.isEmpty(workTime.getText()) ||
                TextUtils.isEmpty(nameTask.getText()) ||
                TextUtils.isEmpty(startDate.getText()) ||
                TextUtils.isEmpty(finishDate.getText()) ||
                new ConvertDate().getData(startDate.getText().toString()).
                        after(new ConvertDate().getData(finishDate.getText().toString())));
    }


    private Task recreateTaskById(int idTask) {
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = new ConvertDate().getData(startDate.getText().toString());
        Date dateFinishWork = new ConvertDate().getData(finishDate.getText().toString());
        Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().
                toString()), dateStartWork, dateFinishWork, status);
        task.setId(idTask);
        return task;
    }


    private void showDateDialog(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener timeDialog = inputDate(date, calendar);
                DatePickerDialog datePickerDialog = new DatePickerDialog(InputTaskActivity.this, timeDialog, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    private DatePickerDialog.OnDateSetListener inputDate(final Button date, final Calendar calendar) {
        DatePickerDialog.OnDateSetListener timeDialog = (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(new ConvertDate().getString(today));
            }
        });
        return timeDialog;
    }


    private Spinner setSpinnerAdapter(Spinner statusWork) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this,
                android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        return statusWork;
    }
}
