package ru.qulix.olesyuknv.taskscontrol.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;

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
import ru.qulix.olesyuknv.taskscontrol.models.ConvertDate;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.AddTask;
import ru.qulix.olesyuknv.taskscontrol.threads.RemoveTask;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;
import ru.qulix.olesyuknv.taskscontrol.threads.UpdateTask;


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
    private int idTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_input_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);
        startDate = (Button) findViewById(R.id.startDate);
        finishDate = (Button) findViewById(R.id.finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerParameters(statusWork);

        ImageView cancelButton = (ImageView) findViewById(R.id.cancelButton);
        cancelButtonOnClick(cancelButton);

        ImageView saveButton = (ImageView) findViewById(R.id.saveButton);
        saveButtonOnClick(saveButton);

        setDateOnClick(startDate);
        setDateOnClick(finishDate);
        fillTheFields();

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
            Task task = getTask(idTask);
            Intent intent = getIntent();
            TaskServer server = ((TasksControlApplication) getApplicationContext()).getServer();

            switch (intent.getIntExtra(ACTION, REQUEST_CODE)) {
                case ADD_TASK_FLAG:
                    new AddTask(server, InputTaskActivity.this).execute(task);
                    break;
                case CHANGE_TASK_FLAG:
                    new UpdateTask(server, InputTaskActivity.this).execute(task);
                    break;
            }
            setResult(RESULT_OK, intent);
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

    private void removeTask() {
        Intent intent = getIntent();
        switch (intent.getIntExtra(ACTION, REQUEST_CODE)) {
            case CHANGE_TASK_FLAG:
                new RemoveTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(getTask(idTask));
                setResult(RESULT_OK, intent);
                break;
        }
    }


    private void fillTheFields() {
        switch (getIntent().getIntExtra(ACTION, REQUEST_CODE)) {
            case CHANGE_TASK_FLAG:
                ConvertDate convertDate = new ConvertDate();
                Task task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
                nameTask.setText(task.getName());
                workTime.setText(String.valueOf(task.getWorkTime()));
                startDate.setText(convertDate.getStringFromData(task.getStartDate()));
                finishDate.setText(convertDate.getStringFromData(task.getFinishDate()));
                idTask = task.getId();
                break;
        }
    }


    private boolean isFieldsEmpty() {
        return !(TextUtils.isEmpty(workTime.getText()) ||
                TextUtils.isEmpty(nameTask.getText()) ||
                TextUtils.isEmpty(startDate.getText()) ||
                TextUtils.isEmpty(finishDate.getText()) ||
                new ConvertDate().getDataFromString(startDate.getText().toString()).after(new ConvertDate().getDataFromString(finishDate.getText().toString())));
    }


    private Task getTask(int idTask) {
        ConvertDate convertDate = new ConvertDate();
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = convertDate.getDataFromString(startDate.getText().toString());
        Date dateFinishWork = convertDate.getDataFromString(finishDate.getText().toString());
        Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().
                toString()), dateStartWork, dateFinishWork, status);
        task.setId(idTask);
        return task;
    }


    private void setDateOnClick(final Button date) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener timeDialog = setDataPickerDialog(date, calendar);
                DatePickerDialog datePickerDialog = new DatePickerDialog(InputTaskActivity.this, timeDialog, day, month, year);
                datePickerDialog.updateDate(year, month, day);
                datePickerDialog.show();
            }
        });
    }


    private DatePickerDialog.OnDateSetListener setDataPickerDialog(final Button date, final Calendar calendar) {
        DatePickerDialog.OnDateSetListener timeDialog = (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(new ConvertDate().getStringFromData(today));
            }
        });
        return timeDialog;
    }


    private Spinner setSpinnerParameters(Spinner statusWork) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this,
                android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        return statusWork;
    }
}
