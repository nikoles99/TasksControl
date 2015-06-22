package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputTaskActivity extends Activity {
    private EditText nameTask;
    private EditText workTime;
    private EditText startWork;
    private EditText finishDate;
    private final String TASK_POSITION = "taskPosition";
    private final String ADD_TASK_FLAG = "taskAdd";
    private final String CHANGE_TASK_FLAG = "taskChanges";
    final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        nameTask = (EditText)findViewById(R.id.name_task);
        workTime = (EditText)findViewById(R.id.work_hours);
        startWork = (EditText)findViewById(R.id.start_date);
        finishDate = (EditText)findViewById(R.id.finish_date);
        Spinner statusWork = (Spinner)findViewById(R.id.status);
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this, android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton)findViewById(R.id.save_button);
        setDateOnClick(startWork);
        setDateOnClick(finishDate);
        saveButtonOnClick(saveButton, statusWork);
        cancelButtonOnClick(cancelButton, statusWork);
    }

    private void setDateOnClick(final EditText date) {

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int year = 2015;
                final int month = 01;
                final int day = 01;
                DatePickerDialog.OnDateSetListener timeDialog = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(day + "." + month + "." + year);
                    }

                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(InputTaskActivity.this, timeDialog, day, month, year);
                datePickerDialog.show();
            }
        });
    }


    private void saveButtonOnClick(ImageButton saveButton, final Spinner statusWork) {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StatusTask status = (StatusTask)statusWork.getSelectedItem();
                Intent intent = getIntent();
                Date dateStartWork = null;
                Date dateFinishWork = null;
                try {
                    dateStartWork = getDataFromString(startWork.getText().toString());
                    dateFinishWork = getDataFromString(finishDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Task task = new Task(nameTask.getText().toString(), workTime.getText().toString(), dateStartWork, dateFinishWork, status);
                //serverTasks.addDataOnServer(task);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

    }

    private Date getDataFromString(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(date);
    }

    private void cancelButtonOnClick(ImageButton cancelButton, final Spinner statusWork) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = ((StatusTask)statusWork.getSelectedItem()).name();
                Intent intent = getIntent();
                int positionTask = intent.getIntExtra(TASK_POSITION, REQUEST_CODE);
               // serverTasks.removeTask();
                finish();
            }
        });
    }
}
