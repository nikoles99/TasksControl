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


public class MainActivity extends Activity {


    private ListView listView;
    final String TASK_POSITION = "taskPosition";
    final String ADD_TASK_FLAG = "taskAdd";
    final String CHANGE_TASK_FLAG = "taskChanges";
    final int REQUEST_CODE = 1;
    ServerTasks serverTasks = new ServerTasks();
    private  List<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        ImageButton addButton = (ImageButton)findViewById(R.id.add_button);
        addButtonOnClick(addButton);
        ImageButton updateButton = (ImageButton)findViewById(R.id.update_button);
        updateButtonOnClick(updateButton);

    }

    private void updateButtonOnClick(ImageButton updateButton) {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks = serverTasks.loadDataFromServer();
            }
        });
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(TASK_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void addButtonOnClick(ImageButton addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
               // intent.putExtra(" ", serverTasks);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }

        Task taskAdd = data.getParcelableExtra(ADD_TASK_FLAG);
        Task task = data.getParcelableExtra(CHANGE_TASK_FLAG);
        int positionTask = data.getIntExtra(TASK_POSITION, REQUEST_CODE);

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


}