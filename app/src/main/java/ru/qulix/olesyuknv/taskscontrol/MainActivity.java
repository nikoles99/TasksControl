package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application form.
 * View all list tasks and changes them.
 *
 * @author OlesyukNV
 */
public class MainActivity extends Activity {

    /**
     *  Get the imaginary server object.
     */
    StubServer serverTasks = StubServer.getInstance();


    private ListView listView;
    final String TASK_POSITION = "taskPosition";
    final String ADD_TASK_FLAG = "taskAdd";
    final String CHANGE_TASK_FLAG = "taskChanges";
    private final String ACTION = "Action";
    private final String ITEM_ADD = "Item1";
    private final String ITEM_UPDATE = "Item2";

    /**
     * flag success call activity
     */
    final int REQUEST_CODE = 1;

    /**
     * The list of tasks.
     */
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
    }

    /**
     * listView item click handler
     *
     * @param listView
     */
    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(TASK_POSITION, position);
                intent.putExtra(ACTION, CHANGE_TASK_FLAG);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }

        tasks = (ArrayList) serverTasks.loadTasks();
        ListViwAdapter listViwAdapter = new ListViwAdapter(this, tasks);
        listView.setAdapter(listViwAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemActionBar = (String) item.getTitle();

        if (itemActionBar.equals(ITEM_ADD)) {
            Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
            intent.putExtra(ACTION, ADD_TASK_FLAG);
            startActivityForResult(intent, REQUEST_CODE);
        }

        if (itemActionBar.equals(ITEM_UPDATE)) {
            tasks = (ArrayList) serverTasks.loadTasks();
            ListViwAdapter listViwAdapter = new ListViwAdapter(this, tasks);
            listView.setAdapter(listViwAdapter);
        }

        return super.onOptionsItemSelected(item);
    }
}