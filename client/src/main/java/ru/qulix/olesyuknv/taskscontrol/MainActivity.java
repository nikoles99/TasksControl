package ru.qulix.olesyuknv.taskscontrol;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Главная форма приложения.
 * Просмотр списка проектов, задач, содрудников
 *
 * @author Q-OLN
 */
public class MainActivity extends Activity {

    /**
     * Activities приложения.
     */
    private static final Map<MainMenuItems, Class> ACTIVITIES = new HashMap<MainMenuItems, Class>() {
        {
            put(MainMenuItems.PROJECTS, ProjectListActivity.class);
            put(MainMenuItems.TASKS, TaskListActivity.class);
            put(MainMenuItems.EMPLOYEES, EmployeeListActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mainMenu = (ListView) findViewById(R.id.mainMenu);
        final ArrayAdapter<MainMenuItems> mainMenuAdapter = new ArrayAdapter<MainMenuItems>(this,
                android.R.layout.simple_list_item_1, MainMenuItems.values());
        mainMenu.setAdapter(mainMenuAdapter);
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ACTIVITIES.get(mainMenuAdapter.getItem(position)));
                startActivity(intent);
            }
        });
    }
}
