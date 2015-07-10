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
import ru.qulix.olesyuknv.taskscontrol.DateType;
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
    public static final int REQUEST_CODE = 1;

    private DateType dateType;
    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
    private Spinner statusWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        dateType = new DateType();

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

        defineModeForm(deleteButton, saveButton, changeButton);
    }

    private void defineModeForm(ImageView deleteButton, ImageView saveButton, ImageView changeButton) {
        Task task = (Task) getIntent().getSerializableExtra(TASK_POSITION);

        if (task != null) {
            fillViews(task);
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            changeButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            changeButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setChangeButtonListener(ImageView changeButtonListener) {
        changeButtonListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFieldsEmpty()) {
                    Task task = recreateTaskById(getIdSelectedTask());
                    new BackgroundUpdatingTask(getServer(), InputTaskActivity.this).execute(task);
                    setResult(RESULT_OK, getIntent());
                    return;
                }
                Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setSaveButtonListener(ImageView saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFieldsEmpty()) {
                    Task task = recreateTaskById(getIdSelectedTask());
                    new BackgroundAddingTask(getServer(), InputTaskActivity.this).execute(task);
                    setResult(RESULT_OK, getIntent());
                    return;
                }
                Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setDeleteButtonListener(ImageView deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFieldsEmpty()) {
                    Task task = recreateTaskById(getIdSelectedTask());
                    new BackgroundDeletingTask(getServer(), InputTaskActivity.this).execute(task);
                    setResult(RESULT_OK, getIntent());
                    return;
                }
                Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillViews(Task task) {
        nameTask.setText(task.getName());
        workTime.setText(String.valueOf(task.getWorkTime()));
        startDate.setText(dateType.getString(task.getStartDate()));
        finishDate.setText(dateType.getString(task.getFinishDate()));
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
                dateType.getData(startDate.getText().toString()).
                        after(dateType.getData(finishDate.getText().toString())));
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private Task recreateTaskById(int idTask) {
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = dateType.getData(startDate.getText().toString());
        Date dateFinishWork = dateType.getData(finishDate.getText().toString());
        Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().
                toString()), dateStartWork, dateFinishWork, status);
        task.setId(idTask);
        return task;
    }

    private void setDateButtonListener(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener timeDialog = setDatePickerListener(date, calendar);
                DatePickerDialog datePickerDialog = new DatePickerDialog(InputTaskActivity.this, timeDialog, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener setDatePickerListener(final Button date, final Calendar calendar) {
        DatePickerDialog.OnDateSetListener timeDialog = (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(dateType.getString(today));
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
