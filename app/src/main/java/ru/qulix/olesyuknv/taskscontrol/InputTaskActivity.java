package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class InputTaskActivity extends Activity  implements View.OnClickListener{
    private String nameTask;
    private String workTime;
    private String startWork;
    private String finishDate;
    private int status;
    private final String[] statusData = {"not started", "in process", "completed ", "postponed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        nameTask = ((EditText)findViewById(R.id.name_task)).getText();
        workTime = ((EditText)findViewById(R.id.work_hours)).getText();
        startWork = ((EditText)findViewById(R.id.start_date)).getText();
        finishDate = ((EditText)findViewById(R.id.finish_date)).getText();
        Spinner statusWork = (Spinner)findViewById(R.id.status);

        statusWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);

        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton)findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_button:
                Task task=new Task(nameTask, workTime, startWork, finishDate, status);
                Intent intent = new Intent();
                intent.putExtra("task", (Parcelable) task);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.cancel_button:
                finish();
                break;

        }
    }
}
