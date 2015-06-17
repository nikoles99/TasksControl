package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class InputTaskActivity extends Activity  implements View.OnClickListener{
    private EditText nameTask;
    private EditText workTime;
    private EditText startWork;
    private EditText finishDate;
    private Spinner statusWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        nameTask = (EditText)findViewById(R.id.name_task);
        workTime = (EditText)findViewById(R.id.work_hours);
        startWork = (EditText)findViewById(R.id.start_date);
        finishDate = (EditText)findViewById(R.id.finish_date);
        statusWork = (Spinner)findViewById(R.id.status);
        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_button);
        ImageButton saveButton = (ImageButton)findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                Task task=new Task("a", "b", "c", "d", 1);
                Intent intent = new Intent("android.intent.action.MainActivity");
                intent.putExtra("task", 122);
                startActivity(intent);
            case R.id.cancel_button:
                finish();
        }
    }
}
