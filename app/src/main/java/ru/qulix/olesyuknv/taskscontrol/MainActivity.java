package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Main application form.
 * View all list tasks and changes them.
 *
 * @author OlesyukNV
 */
public class MainActivity extends Activity {

    private ListView listView;

    /**
     * The list of tasks.
     */
    private List<Task> tasks = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        ListViwAdapter listViwAdapter = new ListViwAdapter(this, tasks);
        listView.setAdapter(listViwAdapter);
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
                intent.putExtra(getString(R.string.TASK_POSITION), position);
                intent.putExtra(getString(R.string.ACTION), R.string.CHANGE_TASK_FLAG);
                startActivityForResult(intent, R.string.REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case R.string.REQUEST_CODE:
                loadDataFromServer();
                ListViwAdapter listViwAdapter = new ListViwAdapter(this, tasks);
                listView.setAdapter(listViwAdapter);
                break;
        }

    }

    private void loadDataFromServer() {
        LoadDataThread loadDataThread = new LoadDataThread((((TasksControlApplication) getApplicationContext()).
                getServer()));
        loadDataThread.execute();
        try {
            tasks = loadDataThread.get();
        } catch (InterruptedException e) {
            Log.e(getString(R.string.ERROR), e.toString());
        } catch (ExecutionException e) {
            Log.e(getString(R.string.ERROR), e.toString());
        }
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
                intent.putExtra(getString(R.string.ACTION), R.string.ADD_TASK_FLAG);
                startActivityForResult(intent, R.string.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                listView.setAdapter(new ListViwAdapter(this, tasks));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}