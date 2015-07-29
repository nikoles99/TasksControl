package ru.qulix.olesyuknv.taskscontrol.activities;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.models.Task;

import ru.qulix.olesyuknv.taskscontrol.PageView;
import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.PartTaskLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Главная форма приложения.
 * Просмотр списка задач и их изменение.
 *
 * @author Q-OLN
 */
public class MainActivity extends Activity {

    private TaskAdapter taskAdapter;
    private ProgressBar progressBar;
    private PageView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pageView = (PageView) findViewById(R.id.pageNavigation);
        ListView listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);

        taskAdapter = new TaskAdapter(this, new ArrayList<Task>());
        listView.setAdapter(taskAdapter);

        pageView.setListener(new PageView.NavigationListener() {
            @Override
            public void sendMessage() {
                loadDataFromServer();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAppParams();
        loadDataFromServer();
    }

    private void setAppParams() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        pageView.setPageSize(Integer.valueOf(sharedPreferences.getString("PAGE_SIZE", "9").trim()));
        pageView.setDefaultParams();
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra(TaskActivity.TASK_POSITION, (Serializable) taskAdapter.getItem(position));
                startActivityForResult(intent, TaskActivity.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TaskActivity.REQUEST_CODE:
                    loadDataFromServer();
                    break;
            }
        }
    }

    public void loadDataFromServer() {
        new PartTaskLoader((((TasksControlApplication) getApplicationContext()).getServer()), progressBar,
                taskAdapter, pageView).execute();
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
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivityForResult(intent, TaskActivity.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}