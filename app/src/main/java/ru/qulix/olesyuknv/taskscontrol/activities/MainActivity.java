package ru.qulix.olesyuknv.taskscontrol.activities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import ru.qulix.olesyuknv.taskscontrol.DisplayEachItemListViw;
import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.AddTask;
import ru.qulix.olesyuknv.taskscontrol.threads.LoadTasks;


/**
 * Главная форма приложения.
 * Просмотр списка задач и их изменение.
 *
 * @author QULIX-OLESYUKNV
 */
public class MainActivity extends Activity {

    private ListView listView;

    private DisplayEachItemListViw displayEachItemListViw;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        initialData();
    }

    /**
     * начальные данные.
     */
    private void initialData() {
        try {
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name1", 1, new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2011"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.COMPLETED));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name2", 2, new SimpleDateFormat("dd.MM.yyyy").parse("1.08.2014"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.IN_PROCESS));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name3", 3, new SimpleDateFormat("dd.MM.yyyy").parse("18.01.2010"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.NOT_STARTED));
            new AddTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(
                    new Task("name4", 4, new SimpleDateFormat("dd.MM.yyyy").parse("20.12.2009"),
                            new SimpleDateFormat("dd.MM.yyyy").parse("10.02.2015"), StatusTask.POSTPONED));
        } catch (ParseException e) {
            Log.e(InputTaskActivity.ERROR, e.toString());
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
                intent.putExtra(InputTaskActivity.TASK_POSITION, (Serializable) displayEachItemListViw.getItem(position));
                intent.putExtra(InputTaskActivity.ACTION, InputTaskActivity.CHANGE_TASK_FLAG);
                startActivityForResult(intent, InputTaskActivity.REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case InputTaskActivity.REQUEST_CODE:
                displayEachItemListViw = new DisplayEachItemListViw(this, loadDataFromServer());
                listView.setAdapter(displayEachItemListViw);
                break;
        }

    }

    /**
     * Загрузка всех задач с с сервера
     */
    private List<Task> loadDataFromServer() {
        LoadTasks loadTasks = new LoadTasks((((TasksControlApplication) getApplicationContext()).
                getServer()));
        loadTasks.execute();

        try {
            return loadTasks.get();
        } catch (InterruptedException e) {
            Log.e(InputTaskActivity.ERROR, e.toString());
        } catch (ExecutionException e) {
            Log.e(InputTaskActivity.ERROR, e.toString());
        }
        return null;
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
                displayEachItemListViw = new DisplayEachItemListViw(this, loadDataFromServer());
                listView.setAdapter(displayEachItemListViw);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}