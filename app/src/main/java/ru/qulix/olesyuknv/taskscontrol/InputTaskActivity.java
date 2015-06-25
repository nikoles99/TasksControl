package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Input task form.
 *
 * @author OlesyukNV
 */
public class InputTaskActivity extends Activity {

    /**
     * Get the imaginary server object.
     */
    StubServer serverTasks = StubServer.getInstance();
    private EditText nameTask;
    private EditText workTime;
    private EditText startWork;
    private EditText finishDate;
    private final String TASK_POSITION = "taskPosition";
    private final String ADD_TASK_FLAG = "taskAdd";
    private final String CHANGE_TASK_FLAG = "taskChanges";
    private final String ACTION = "Action";

    /**
     * flag success call activity
     */
    final int REQUEST_CODE = 1;

    /**
     * Dialog input date.
     */
    DatePickerDialog.OnDateSetListener timeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        nameTask = (EditText) findViewById(R.id.name_task);
        workTime = (EditText) findViewById(R.id.work_hours);
        startWork = (EditText) findViewById(R.id.start_date);
        finishDate = (EditText) findViewById(R.id.finish_date);
        Spinner statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerParameters(statusWork);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton) findViewById(R.id.save_button);
        setDateOnClick(startWork);
        setDateOnClick(finishDate);
        saveButtonOnClick(saveButton, statusWork);
        cancelButtonOnClick(cancelButton);
        setDataView();
    }

    private void setDataView() {
        if(getIntent().getStringExtra(ACTION).equals(CHANGE_TASK_FLAG)) {
            List<Task> tasks = serverTasks.loadDataFromServer();
            int itemPosition = getIntent().getIntExtra(TASK_POSITION, REQUEST_CODE);
            workTime.setText(tasks.get(itemPosition).getName());
            workTime.setText(tasks.get(itemPosition).getWorkTime());
            workTime.setText(tasks.get(itemPosition).getStartDate().toString());
            workTime.setText(tasks.get(itemPosition).getFinishDate().toString());
            workTime.setText(tasks.get(itemPosition).getStatus().toString());
        }
    }

    private final Calendar c = Calendar.getInstance();

    /**
     * get current year
     */
    private int year = c.get(Calendar.YEAR);

    /**
     * get current month
     */
    private int month = c.get(Calendar.MONTH);

    /**
     * get current day
     */
    private int day = c.get(Calendar.DAY_OF_MONTH);

    /**
     * set new date
     *
     * @param date
     */
    private void setDateOnClick(final EditText date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1111);
                timeDialog = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;
                        date.setText(new StringBuilder().append(day).append(".").append(month + 1).append(".").append(year));
                    }
                };
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(InputTaskActivity.this, timeDialog, day, month, year);
    }

    /**
     * adding new task or changes selected task
     *
     * @param saveButton
     * @param statusWork
     */
    private void saveButtonOnClick(ImageButton saveButton, final Spinner statusWork) {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StatusTask status = (StatusTask) statusWork.getSelectedItem();
                Date dateStartWork = null;
                Date dateFinishWork = null;

                try {
                    dateStartWork = getDataFromString(startWork.getText().toString());
                    dateFinishWork = getDataFromString(finishDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().toString()), dateStartWork, dateFinishWork, status);
                Intent intent = getIntent();
                String action = intent.getStringExtra(ACTION);

                if (action.equals(ADD_TASK_FLAG)) {
                    serverTasks.addDataOnServer(task);
                }
                else if (action.equals(CHANGE_TASK_FLAG)) {
                    int itemPosition = intent.getIntExtra(TASK_POSITION, REQUEST_CODE);
                    serverTasks.updateDataOnServer(task, itemPosition);
                }
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }

    /**
     * close this form or delete selected task
     *
     * @param cancelButton
     */
    private void cancelButtonOnClick(ImageButton cancelButton) {
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String action = intent.getStringExtra("Action");

                if (action.equals(CHANGE_TASK_FLAG)) {
                    int itemPosition = intent.getIntExtra(TASK_POSITION, REQUEST_CODE);
                    serverTasks.removeTask(itemPosition);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    /**
     * @param date current date
     * @return date
     * @throws ParseException
     */
    private Date getDataFromString(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(date);
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
