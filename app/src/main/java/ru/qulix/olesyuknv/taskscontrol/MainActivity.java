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
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Главная форма приложения.
 * Просмотр списка задач и их изменение.
 *
 * @author OlesyukNV
 */
public class MainActivity extends Activity {

    private ListView listView;

    /**
     * The list of tasks.
     */
    private List<Task> tasks = new ArrayList<Task>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        initialData();
        listView.setAdapter(new DisplayEachItemListViw(this, tasks));

    }

    /**
     * начальные данные.
     */
    private void initialData() {
        try {
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name1",1,new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2011"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.COMPLETED));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name2",2,new SimpleDateFormat("dd.MM.yyyy").parse("1.08.2014"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.IN_PROCESS));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name3",3,new SimpleDateFormat("dd.MM.yyyy").parse("18.01.2010"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.NOT_STARTED));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name4",4,new SimpleDateFormat("dd.MM.yyyy").parse("20.12.2009"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.POSTPONED));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработчик нажатия элемента списка
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
                listView.setAdapter(new DisplayEachItemListViw(this, tasks));
                break;
        }

    }

    /**
     * Загрузка всех задач с с сервера
     */
    private void loadDataFromServer() {
        progressBar.setVisibility(View.VISIBLE);
        LoadTasks loadTasks = new LoadTasks((((TasksControlApplication) getApplicationContext()).
                getServer()));
        loadTasks.execute();
        progressBar.setVisibility(View.INVISIBLE);
        try {
            tasks = loadTasks.get();
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
                listView.setAdapter(new DisplayEachItemListViw(this, tasks));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}