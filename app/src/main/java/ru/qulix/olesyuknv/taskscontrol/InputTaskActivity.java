package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Input task form.
 *
 * @author OlesyukNV
 */
public class InputTaskActivity extends Activity {

    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
    private Day day = new Day();
    private int idTask;

    /*   public static final String TASK_POSITION = "taskPosition";
       public static final String CHANGE_TASK_FLAG = "taskChanges";
       public static final String ACTION = "Action";
       public static final String ADD_TASK_FLAG = "ADD_TASK_FLAG";
       public static final int REQUEST_CODE = 1;
       public static final String ERROR = "Error";
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);
        startDate = (Button) findViewById(R.id.startDate);
        finishDate = (Button) findViewById(R.id.finishDate);

        Spinner statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerParameters(statusWork);

        ImageView cancelButton = (ImageView) findViewById(R.id.cancelButton);
        cancelButtonOnClick(cancelButton, statusWork);

        ImageView saveButton = (ImageView) findViewById(R.id.saveButton);
        saveButtonOnClick(saveButton, statusWork);

        setDateOnClick(startDate);
        setDateOnClick(finishDate);

        try {
            fillTheFields();
        } catch (ExecutionException e) {
            Log.e(getString(R.string.ERROR), e.toString());
        } catch (InterruptedException e) {
            Log.e(getString(R.string.ERROR), e.toString());
        }
    }

    /**
     * adding new task or changes selected task
     *
     * @param saveButton
     * @param statusWork
     */
    private void saveButtonOnClick(ImageView saveButton, final Spinner statusWork) {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (ifCorrectDataInput()) {
                        Task task = getTask(statusWork, idTask);
                        Intent intent = getIntent();

                        switch (intent.getIntExtra(getString(R.string.ACTION), R.string.REQUEST_CODE)) {
                            case R.string.ADD_TASK_FLAG:
                                new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(task);
                                break;
                            case R.string.CHANGE_TASK_FLAG:
                                new UpdateTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(task);
                                break;
                        }

                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    Log.e(getString(R.string.ERROR), e.toString());
                }

            }

        });
    }

    /**
     * close this form or delete selected task
     *
     * @param cancelButton
     */
    private void cancelButtonOnClick(ImageView cancelButton, final Spinner statusWork) {
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                switch (intent.getIntExtra(getString(R.string.ACTION), R.string.REQUEST_CODE)) {
                    case R.string.CHANGE_TASK_FLAG:
                        try {
                            new RemoveTask(((TasksControlApplication) getApplicationContext()).getServer()).
                                    execute(getTask(statusWork, idTask));
                        } catch (ParseException e) {
                            Log.e(getString(R.string.ERROR), e.toString());
                        }
                        setResult(RESULT_OK, intent);
                        break;
                }
                finish();
            }
        });
    }

    private void fillTheFields() throws ExecutionException, InterruptedException {
        switch (getIntent().getIntExtra(getString(R.string.ACTION), R.string.REQUEST_CODE)) {
            case R.string.CHANGE_TASK_FLAG:
                LoadTasks loadTasks = new LoadTasks((((TasksControlApplication) getApplicationContext()).
                        getServer()));
                loadTasks.execute();
                List<Task> tasks = loadTasks.get();
                int itemPosition = getIntent().getIntExtra(getString(R.string.TASK_POSITION), R.string.REQUEST_CODE);
                nameTask.setText(tasks.get(itemPosition).getName());
                workTime.setText(String.valueOf(tasks.get(itemPosition).getWorkTime()));
                startDate.setText(day.getStringFromData(tasks.get(itemPosition).getStartDate()));
                finishDate.setText(day.getStringFromData(tasks.get(itemPosition).getFinishDate()));
                idTask = tasks.get(itemPosition).getId();
                break;
        }
    }

    private boolean ifCorrectDataInput() throws ParseException {
        return !(TextUtils.isEmpty(workTime.getText()) ||
                TextUtils.isEmpty(nameTask.getText()) ||
                TextUtils.isEmpty(startDate.getText()) ||
                TextUtils.isEmpty(finishDate.getText()) ||
                day.getDataFromString(startDate.getText().toString()).after(day.getDataFromString(finishDate.getText().toString())));
    }

    private Task getTask(Spinner statusWork, int idTask) throws ParseException {
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = day.getDataFromString(startDate.getText().toString());
        Date dateFinishWork = day.getDataFromString(finishDate.getText().toString());
        Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().toString()), dateStartWork, dateFinishWork, status);
        task.setId(idTask);
        return task;
    }

    private void setDateOnClick(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataPickerDialog(date);
                new DatePickerDialog(InputTaskActivity.this, day.getTimeDialog(), day.getDay(), day.getMonth(), day.getYear()).show();
            }
        });
    }

    private void setDataPickerDialog(final Button date) {
        day.setTimeDialog(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                day.setYear(selectedYear);
                day.setMonth(selectedMonth);
                day.setDay(selectedDay);
                day.getCalendar().set(day.getYear(), day.getMonth(), day.getDay());
                Date today = day.getCalendar().getTime();
                date.setText(day.getStringFromData(today));
            }
        });
    }

    /**
     * @param statusWork status task
     * @return selected status task
     */
    public Spinner setSpinnerParameters(Spinner statusWork) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this, android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        return statusWork;
    }

}
