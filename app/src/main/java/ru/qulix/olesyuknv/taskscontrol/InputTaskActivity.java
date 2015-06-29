package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    Server serverTasks = StubServer.getInstance();
    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
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
        startDate = (Button) findViewById(R.id.start_date);
        finishDate = (Button) findViewById(R.id.finish_date);
        Spinner statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerParameters(statusWork);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton) findViewById(R.id.save_button);
        setDateOnClick(startDate);
        setDateOnClick(finishDate);
        saveButtonOnClick(saveButton, statusWork);
        cancelButtonOnClick(cancelButton, statusWork);
        setViews();
    }

    private void setViews() {
        if (getIntent().getStringExtra(ACTION).equals(CHANGE_TASK_FLAG)) {
            List<Task> tasks = serverTasks.loadTasks();
            int itemPosition = getIntent().getIntExtra(TASK_POSITION, REQUEST_CODE);
            nameTask.setText(tasks.get(itemPosition).getName());
            workTime.setText(String.valueOf(tasks.get(itemPosition).getWorkTime()));
            startDate.setText(getStringFromData(tasks.get(itemPosition).getStartDate()));
            finishDate.setText(getStringFromData(tasks.get(itemPosition).getFinishDate()));
        }
    }

    private final Calendar calendar = Calendar.getInstance();

    /**
     * get current year
     */
    private int year = calendar.get(Calendar.YEAR);

    /**
     * get current month
     */
    private int month = calendar.get(Calendar.MONTH);

    /**
     * get current day
     */
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    /**
     * set new date
     *
     * @param date
     */
    private void setDateOnClick(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeDialog = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;
                        calendar.set(year, month, day);
                        Date today = calendar.getTime();
                        date.setText(getStringFromData(today));
                    }
                };
                new DatePickerDialog(InputTaskActivity.this, timeDialog, day, month, year).show();
            }
        });
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
                try {
                    if (ifCorrectDataInput()) {
                        Task task = getTask(statusWork);
                        Intent intent = getIntent();
                        String action = intent.getStringExtra(ACTION);

                        if (action.equals(ADD_TASK_FLAG)) {
                            serverTasks.addTask(task);
                        } else if (action.equals(CHANGE_TASK_FLAG)) {
                            serverTasks.updateTask(task);
                        }
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private boolean ifCorrectDataInput() throws ParseException {
        if (workTime.getText().toString().equals("") ||
                nameTask.getText().toString().equals("") ||
                startDate.getText().toString().equals("") ||
                finishDate.getText().toString().equals("") ||
                getDataFromString(startDate.getText().toString()).after(getDataFromString(finishDate.getText().toString()))) {
            return false;
        } else {
            return true;
        }
    }

    private Task getTask(Spinner statusWork) {
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = null;
        Date dateFinishWork = null;

        try {
            dateStartWork = getDataFromString(startDate.getText().toString());
            dateFinishWork = getDataFromString(finishDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().toString()), dateStartWork, dateFinishWork, status);
    }

    /**
     * close this form or delete selected task
     *
     * @param cancelButton
     */
    private void cancelButtonOnClick(ImageButton cancelButton, final Spinner statusWork) {
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String action = intent.getStringExtra("Action");

                if (action.equals(CHANGE_TASK_FLAG)) {
                    int itemPosition = intent.getIntExtra(TASK_POSITION, REQUEST_CODE);
                    serverTasks.removeTask(getTask(statusWork));
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
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.parse(date);
    }

    private String getStringFromData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
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
