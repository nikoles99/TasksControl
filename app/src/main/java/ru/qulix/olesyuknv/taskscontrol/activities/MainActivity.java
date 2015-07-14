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

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import ru.qulix.olesyuknv.taskscontrol.utils.PageNavigation;
import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.PartTaskLoader;

/**
 * Главная форма приложения.
 * Просмотр списка задач и их изменение.
 *
 * @author QULIX-OLESYUKNV
 */
public class MainActivity extends Activity {

    private TaskAdapter taskAdapter;
    private ImageView previousPage;
    private ImageView nextPage;
    private ProgressBar progressBar;
    private PageNavigation pageNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageNavigation = new PageNavigation();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ListView listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);

        taskAdapter = new TaskAdapter(this, new ArrayList<Task>());

        listView.setAdapter(taskAdapter);

        nextPage = (ImageView) findViewById(R.id.nextPage);
        setNextPageButtonListener(nextPage);

        previousPage = (ImageView) findViewById(R.id.previousPage);
        setPreviousPageButtonListener(previousPage);
        loadDataFromServer();
    }

    private void setNextPageButtonListener(final ImageView nextPage) {
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPage.setVisibility(View.VISIBLE);
                pageNavigation.nextPage();
                loadDataFromServer();

            }
        });
    }


    private void setPreviousPageButtonListener(final ImageView previousPage) {
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage.setVisibility(View.VISIBLE);
                pageNavigation.previousPage();
                loadDataFromServer();

                if (pageNavigation.noData()) {
                    previousPage.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InputTaskActivity.class);
                intent.putExtra(InputTaskActivity.TASK_POSITION, (Serializable) taskAdapter.getItem(position));
                startActivityForResult(intent, InputTaskActivity.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case InputTaskActivity.REQUEST_CODE:
                    loadDataFromServer();
                    break;
            }
        }
    }

    private void loadDataFromServer() {
        new PartTaskLoader((((TasksControlApplication) getApplicationContext()).getServer()), progressBar,
                taskAdapter, nextPage).execute().getStatus();
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
                startActivityForResult(intent, InputTaskActivity.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}