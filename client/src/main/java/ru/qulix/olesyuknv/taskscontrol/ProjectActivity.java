package ru.qulix.olesyuknv.taskscontrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.Constants;
import com.example.models.Project;
import com.example.models.Task;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.project.AddProjectLoader;
import ru.qulix.olesyuknv.taskscontrol.project.RemoveProjectLoader;
import ru.qulix.olesyuknv.taskscontrol.project.UpdateProjectLoader;
import ru.qulix.olesyuknv.taskscontrol.utils.NavigationListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Форма создания и изменения проекта
 *
 * @author Q-OLN
 */
public class ProjectActivity extends Activity {

    /**
     * Параметр Intent, для хранения проекта
     */
    public static final String PROJECT = ProjectActivity.class + ".PROJECT";

    /**
     * Параметр Intent, для хранения действия над задачей
     */
    public static final String ACTION = ProjectActivity.class + ".ACTION";

    /**
     * Идентификатор ProjectActivity
     */
    public static final int REQUEST_CODE = 2;

    private static final int PAGE_SIZE = 1;

    private static final String LOG_TAG = ProjectActivity.class.getName();

    private TextView id;

    private EditText subName;
    private EditText name;
    private EditText description;

    private ImageView addProjectButton;

    private TasksControlAdapter<Task> taskAdapter;

    private PageView pageView;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project = (Project) getIntent().getSerializableExtra(PROJECT);

        boolean addMode = project == null;

        if (addMode) {
            project = new Project();
        }
        createView(addMode);

        setProject(project);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TaskActivity.REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    Task task = (Task) data.getSerializableExtra(TaskActivity.TASK);
                    String action = data.getStringExtra(ACTION);
                    execute(task, action);
                    loadTasks();
                    break;
                }
            }
        }
    }

    private void execute(Task task, String action) {
        if (action.equals(Constants.ADD_TASK)) {
            project.addTask(task);
        }
        if (action.equals(Constants.REMOVE_TASK)) {
            project.removeTask(task);
        }
        if (action.equals(Constants.UPDATE_TASK)) {
            project.removeTask(task);
            project.addTask(task);
        }
    }

    private void createView(final boolean addMode) {
        setContentView(R.layout.activity_project);

        id = (TextView) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        subName = (EditText) findViewById(R.id.subName);
        description = (EditText) findViewById(R.id.description);

        ImageView deleteProjectButton = (ImageView) findViewById(R.id.deleteProjectButton);
        deleteProjectButton.setVisibility((!addMode) ? View.VISIBLE : View.INVISIBLE);
        deleteProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new RemoveProjectLoader(getServer(), ProjectActivity.this));
            }
        });

        addProjectButton = (ImageView) findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(addMode ? new AddProjectLoader(getServer(), ProjectActivity.this) : new UpdateProjectLoader(getServer(),
                        ProjectActivity.this));
            }
        });
        createTaskListView();
    }

    private void createTaskListView() {
        View list = findViewById(R.id.list);

        Button addTaskInProject = (Button) findViewById(R.id.addTaskInProject);
        addTaskInProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, TaskActivity.class);
                setIntentParams(intent);
            }
        });

        pageView = (PageView) list.findViewById(R.id.pageNavigation);
        pageView.setPageSize(PAGE_SIZE);
        pageView.setListener(new NavigationListener() {
            @Override
            public void onPage() {
                loadTasks();
            }
        });

        taskAdapter = new TaskAdapter(this, new ArrayList<Task>());
        ListView listView = (ListView) list.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProjectActivity.this, TaskActivity.class);
                intent.putExtra(TaskActivity.TASK, (Serializable) taskAdapter.getItem(position));
                setIntentParams(intent);
            }
        });
        listView.setAdapter(taskAdapter);
    }

    private void setIntentParams(Intent intent) {
        try {
            intent.putExtra(TaskActivity.PROJECT, getProject());
            startActivityForResult(intent, TaskActivity.REQUEST_CODE);
        } catch (TaskControlException e) {
            showMessage(e);
        }
    }

    private void setProject(Project project) {
        this.project = project;
        id.setText(String.valueOf(project.getId()));
        name.setText(project.getName());
        subName.setText(project.getSubName());
        description.setText(project.getDescription());
        loadTasks();
    }

    private Project getProject() throws TaskControlException {
        validate();
        project.setName(name.getText().toString());
        project.setSubName(subName.getText().toString());
        project.setDescription(description.getText().toString());
        return project;
    }

    private void validate() throws TaskControlException {
        if (TextUtils.isEmpty(name.getText())) {
            throw new TaskControlException("Введите название проекта");
        }
        if (TextUtils.isEmpty(subName.getText())) {
            throw new TaskControlException("Введите сокращенное имя проекта");
        }
        if (TextUtils.isEmpty(description.getText())) {
            throw new TaskControlException("Введите описание проекта");
        }
        addProjectButton.setEnabled(false);
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private void execute(TasksControlLoader<Project, Void> thread) {
        try {
            Project project = getProject();
            thread.execute(project);
        } catch (TaskControlException e) {
            showMessage(e);
        }
    }

    private void showMessage(TaskControlException e) {
        Log.e(LOG_TAG, e.getMessage(), e);
        Toast.makeText(ProjectActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void loadTasks() {
        int startPosition = pageView.getStartPosition();
        int finishPosition = pageView.getFinishPosition();
        List<Task> tasks = getTasks(startPosition, finishPosition);
        boolean existData = tasks.size() >= Math.abs(startPosition - finishPosition);
        taskAdapter.update(tasks);
        pageView.setExistData(existData);
    }

    private List<Task> getTasks(int startPosition, int finishPosition) {
        if (startPosition < 0 || startPosition > project.getTasks().size()) {
            return new ArrayList<Task>();
        }
        return project.getTasks().subList(startPosition, Math.min(finishPosition, project.getTasks().size()));
    }
}
