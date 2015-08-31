package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Employee;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.adapters.EmployeeAdapter;
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
 * Форма списка сотрудников.
 *
 * @author Q-OLN
 */
public class EmployeeListActivity extends Activity {

    /**
     * Идентификатор EmployeeListActivity
     */
    private static final int REQUEST_CODE = 2;

    private TasksControlAdapter employeeAdapter;

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

        employeeAdapter = new EmployeeAdapter(this, new ArrayList<Employee>());

        ListView listView = (ListView) findViewById(R.id.listView);
        listViewOnItemClick(listView);
        listView.setAdapter(employeeAdapter);

        loadDataFromServer();
    }

    private void listViewOnItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeActivity.class);
                intent.putExtra(EmployeeActivity.EMPLOYEE, (Serializable) employeeAdapter.getItem(position));
                startActivityForResult(intent, EmployeeActivity.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EmployeeActivity.REQUEST_CODE:
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
        new PartEmployeesLoader((((TasksControlApplication) getApplicationContext()).getServer())).execute();
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
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeActivity.class);
                startActivityForResult(intent, EmployeeActivity.REQUEST_CODE);
                break;
            case R.id.update_button:
                loadDataFromServer();
                break;
            case R.id.action_settings:
                startActivityForResult(new Intent(EmployeeListActivity.this, SettingActivity.class), REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Поток загрузки сотрудников
     *
     * @author Q-OLN
     */
    private class PartEmployeesLoader extends PartLoader<Employee> {

        public PartEmployeesLoader(TaskServer server) {
            super(server, pageView, progressBar, EmployeeListActivity.this, employeeAdapter);
        }

        @Override
        protected List<Employee> processing(List<Employee> employee) throws IOException {
            return getServer().loadEmployees(getStartPosition(), getFinishPosition());
        }
    }
}
