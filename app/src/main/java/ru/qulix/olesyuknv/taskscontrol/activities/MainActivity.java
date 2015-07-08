package ru.qulix.olesyuknv.taskscontrol.activities;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;

import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.LoadTasks;

/**
 * Главная форма приложения.
 * Просмотр списка задач и их изменение.
 *
 * @author QULIX-OLESYUKNV
 */
public class MainActivity extends Activity {

    private ListView listView;

    private TaskAdapter taskAdapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        taskAdapter = new TaskAdapter(this, new ArrayList<Task>());
        listView.setAdapter(taskAdapter);
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(InputTaskActivity.TASK_POSITION, (Serializable) taskAdapter.getItem(position));
                intent.putExtra(InputTaskActivity.ACTION, InputTaskActivity.CHANGE_TASK_FLAG);
                startActivityForResult(intent, InputTaskActivity.REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == InputTaskActivity.REQUEST_CODE) {
            return;
        }
        switch (requestCode) {
            case InputTaskActivity.REQUEST_CODE:
                loadDataFromServer();
                break;
        }

    }

    private void loadDataFromServer() {
        new LoadTasks((((TasksControlApplication) getApplicationContext()).getServer()), progressBar, taskAdapter).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(InputTaskActivity.ACTION, InputTaskActivity.ADD_TASK_FLAG);
                startActivityForResult(intent, InputTaskActivity.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}