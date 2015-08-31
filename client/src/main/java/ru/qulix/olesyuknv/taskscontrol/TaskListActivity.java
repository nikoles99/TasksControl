package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.adapters.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.adapters.TasksControlAdapter;
import ru.qulix.olesyuknv.taskscontrol.utils.NavigationListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Форма списка задач.
 *
 * @author Q-OLN
 */
public class TaskListActivity extends Activity {

    /**
     * Идентификатор TaskListActivity
     */
    private static final int REQUEST_CODE = 2;

    private TasksControlAdapter<Task> taskAdapter;

    private ProgressBar progressBar;

    private PageView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        pageView = (PageView) findViewById(R.id.pageNavigation);
        pageView.setListener(new NavigationListener() {
            @Override
            public void onPage() {
                loadDataFromServer();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        taskAdapter = new TaskAdapter(this, new ArrayList<Task>());

        ListView listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        listView.setAdapter(taskAdapter);
        loadDataFromServer();
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TaskListActivity.this, TaskActivity.class);
                intent.putExtra(TaskActivity.TASK, (Serializable) taskAdapter.getItem(position));
                startActivityForResult(intent, TaskActivity.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TaskActivity.REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    loadDataFromServer();
                }
                break;
            case REQUEST_CODE:
                pageView.setAppParams();
                loadDataFromServer();
                break;
        }
    }

    private void loadDataFromServer() {
        new PartTaskLoader((((TasksControlApplication) getApplicationContext()).getServer())).execute();
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
                Intent intent = new Intent(TaskListActivity.this, TaskActivity.class);
                startActivityForResult(intent, TaskActivity.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                break;
            case R.id.action_settings:
                startActivityForResult(new Intent(TaskListActivity.this, SettingActivity.class), REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Поток загрузки задач
     *
     * @author Q-OLN
     */
    private class PartTaskLoader extends PartLoader<Task> {

        public PartTaskLoader(TaskServer server) {
            super(server, pageView, progressBar, TaskListActivity.this, taskAdapter);
        }

        @Override
        protected List<Task> processing(List<Task> task) throws IOException {
            return getServer().loadTasks(getStartPosition(), getFinishPosition());
        }

    }
}