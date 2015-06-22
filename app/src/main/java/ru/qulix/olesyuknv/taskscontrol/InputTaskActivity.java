package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class InputTaskActivity extends Activity {
    private EditText nameTask;
    private EditText workTime;
    private EditText startWork;
    private EditText finishDate;
    private final String TASK_POSITION = "taskPosition";
    private final String ADD_TASK_FLAG = "taskAdd";
    private final String CHANGE_TASK_FLAG = "taskChanges";
    private String status;
    private final String[] statusData = {"not started", "in process", "completed", "postponed"};
    Spinner statusWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        nameTask = (EditText)findViewById(R.id.name_task);
        workTime = (EditText)findViewById(R.id.work_hours);
        startWork = (EditText)findViewById(R.id.start_date);
        finishDate = (EditText)findViewById(R.id.finish_date);
        statusWork = (Spinner)findViewById(R.id.status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton)findViewById(R.id.save_button);
        saveButtonOnClick(saveButton);
        cancelButtonOnClick(cancelButton);
    }

    private void saveButtonOnClick(ImageButton saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                status = statusWork.getSelectedItem().toString();
                Intent intent = getIntent();
                int positionTask = intent.getIntExtra(TASK_POSITION, 0);
                Task task = new Task(nameTask.getText().toString(), workTime.getText().toString(), startWork.getText().toString(), finishDate.getText().toString(), status);

                if (positionTask>0) {
                    intent.putExtra(CHANGE_TASK_FLAG, (Parcelable) task);
                    intent.putExtra(TASK_POSITION, positionTask);
                }

                else {
                    intent = new Intent();
                    intent.putExtra(ADD_TASK_FLAG, (Parcelable) task);
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void cancelButtonOnClick(ImageButton cancelButton) {
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                status = (String)statusWork.getSelectedItem();
                Intent intent = getIntent();
                int positionTask = intent.getIntExtra(TASK_POSITION, 0);

                if (positionTask>0) {
                    intent.putExtra(TASK_POSITION, positionTask);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }
}
