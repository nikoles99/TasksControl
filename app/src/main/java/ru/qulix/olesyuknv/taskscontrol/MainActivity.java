package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private  List<Task> tasks = new ArrayList<>();
    private ListView listView;
    final String TASK_POSITION = "taskPosition";
    final String ADD_TASK_FLAG = "taskAdd";
    final String CHANGE_TASK_FLAG = "taskChanges";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
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

        Task taskAdd = data.getParcelableExtra(ADD_TASK_FLAG);
        Task task = data.getParcelableExtra(CHANGE_TASK_FLAG);
        int positionTask = data.getIntExtra(TASK_POSITION, 0);

        if(taskAdd!=null) {
            tasks.add(taskAdd);
        }

        else if(task!=null) {
            Task taskChanges = tasks.get(positionTask);
            taskChanges.setName(task.getName());
            taskChanges.setWorkTime(task.getWorkTime());
            taskChanges.setStartDate(task.getStartDate());
            taskChanges.setStatus(task.getStatus());
            taskChanges.setFinishDate(task.getFinishDate());
        }


        if (positionTask>0 && task == null && taskAdd == null) {
            tasks.remove(positionTask);
        }

        CustomAdapter customAdapter = new CustomAdapter(this, tasks);
        listView.setAdapter(customAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, InputTaskActivity.class);
        intent.putExtra(TASK_POSITION, position);
        startActivityForResult(intent, 0);
    }
}