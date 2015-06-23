package ru.qulix.olesyuknv.taskscontrol;

import android.app.ActionBar;
import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import java.util.List;


public class MainActivity extends Activity {

    SubServer serverTasks = SubServer.getInstance();
    private ListView listView;
    final String TASK_POSITION = "taskPosition";
    final String ADD_TASK_FLAG = "taskAdd";
    final String CHANGE_TASK_FLAG = "taskChanges";
    final int REQUEST_CODE = 1;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(TASK_POSITION, position);
                intent.putExtra("Action", CHANGE_TASK_FLAG);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }

        tasks = serverTasks.loadDataFromServer();
        CustomAdapter customAdapter = new CustomAdapter(this, tasks);
        listView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemActionBar = (String) item.getTitle();
        if (itemActionBar.equals("Item1")) {
            Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
            intent.putExtra("Action", ADD_TASK_FLAG);
            startActivityForResult(intent, REQUEST_CODE);
        }

        if (itemActionBar.equals("Item2")) {
            tasks = serverTasks.loadDataFromServer();
        }

        return super.onOptionsItemSelected(item);
    }
}