package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {
    List<Task> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton addButton = (ImageButton)findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                Intent intent = new Intent(this, InputTaskActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.update_button:

                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {return;}

        Task task = data.getParcelableExtra("task");
        tasks.add(task);
        CustomAdapter customAdapter = new CustomAdapter(this, tasks);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customAdapter);


    }
}