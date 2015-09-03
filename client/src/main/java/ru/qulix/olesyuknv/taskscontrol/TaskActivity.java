package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.Constants;
import com.example.models.Employee;
import com.example.models.Project;
import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.DateFormatUtility;

import ru.qulix.olesyuknv.taskscontrol.task.AddTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.task.RemoveTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.task.UpdateTaskLoader;
import ru.qulix.olesyuknv.taskscontrol.utils.NavigationListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Форма создания и изменения задачи.
 *
 * @author Q-OLN
 */
public class TaskActivity extends Activity {

    /**
     * Параметр Intent, для хранения задачи
     */
    public static final String TASK = TaskActivity.class + ".TASK";

    /**
     * Параметр Intent, для хранения проекта задачи
     */
    public static final String PROJECT = TaskActivity.class + ".PROJECT";

    /**
     * Идентификатор TaskActivity
     */
    public static final int REQUEST_CODE = 2;

    private static final int PAGE_SIZE = 1;

    private static final String LOG_TAG = TaskActivity.class.getName();

    private TextView id;

    private EditText nameTask;
    private EditText workTime;

    private Button startDate;
    private Button finishDate;

    private Spinner statusWork;
    private Spinner projectsSpinner;
    private Spinner employeesSpinner;

    private Button addTaskInProject;

    private ImageView saveButton;

    private PageView pageView;

    private Task task;

    private Project project;

    private TasksControlAdapter<Employee> employeeListAdapter;
    private TasksControlAdapter<Employee> employeesSpinnerAdapter;

    private TasksControlAdapter<Project> projectAdapter;

    /**
     * Все сотрудники
     */
    private List<Employee> employees = new ArrayList<Employee>();

    private boolean projectActivityMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task = (Task) getIntent().getSerializableExtra(TASK);

        boolean addMode = task == null;

        if (addMode) {
            task = new Task();
        }
        project = (Project) getIntent().getSerializableExtra(PROJECT);

        projectActivityMode = project != null;

        createView(addMode);

        setTask(task);
    }

    private void createView(final boolean addMode) {
        setContentView(R.layout.activity_task);

        id = (TextView) findViewById(R.id.id);

        nameTask = (EditText) findViewById(R.id.nameTask);

        workTime = (EditText) findViewById(R.id.workHours);

        startDate = (Button) findViewById(R.id.startDate);
        setDateButtonListener(startDate);

        finishDate = (Button) findViewById(R.id.finishDate);
        setDateButtonListener(finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setStatusAdapter(statusWork);

        projectsSpinner = (Spinner) findViewById(R.id.projects);
        employeesSpinner = (Spinner) findViewById(R.id.employees);

        ImageView deleteButton = (ImageView) findViewById(R.id.deleteButton);
        deleteButton.setVisibility((!addMode) ? View.VISIBLE : View.INVISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectActivityMode) {
                    execute(Constants.REMOVE_TASK);
                } else {
                    execute(new RemoveTaskLoader(getServer(), TaskActivity.this));
                }
            }
        });

        saveButton = (ImageView) findViewById(R.id.addTaskButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectActivityMode) {
                    execute(addMode ? Constants.ADD_TASK : Constants.UPDATE_TASK);
                } else {
                    execute(addMode ? new AddTaskLoader(getServer(), TaskActivity.this) : new UpdateTaskLoader(getServer(),
                            TaskActivity.this));
                }
            }
        });
        createEmployeeListView();
    }

    private void setTask(Task task) {
        this.task = task;
        id.setText(String.valueOf(task.getId()));
        nameTask.setText(task.getName());
        workTime.setText(String.valueOf(task.getWorkTime()));
        startDate.setText(DateFormatUtility.format(task.getStartDate()));
        finishDate.setText(DateFormatUtility.format(task.getFinishDate()));
        loadProjects();
        loadEmployees();
    }

    private Task getTask() throws TaskControlException {
        validate();
        task.setName(nameTask.getText().toString());
        Project project = ((Project) projectAdapter.getItem(projectsSpinner.getSelectedItemPosition()));
        task.setProjectId(project.getId());
        task.setWorkTime(Integer.parseInt(workTime.getText().toString()));
        task.setStartDate(DateFormatUtility.format(startDate.getText().toString()));
        task.setFinishDate(DateFormatUtility.format(finishDate.getText().toString()));
        task.setStatus((StatusTask) statusWork.getSelectedItem());
        return task;
    }

    private void loadEmployees() {
        new EmployeeLoader().execute();
    }

    private void loadProjects() {
        if (projectActivityMode) {
            List<Project> projects = new ArrayList<Project>();
            projects.add(project);
            setProjectAdapter(projects);
        } else {
            new ProjectsLoader().execute();
        }
    }

    private void validate() throws TaskControlException {
        if (TextUtils.isEmpty(workTime.getText()) || !workTime.getText().toString().matches("^-?\\d+$")) {
            throw new TaskControlException("Некоректное время");
        }
        if (TextUtils.isEmpty(nameTask.getText())) {
            throw new TaskControlException("Введите имя задачи");
        }
        if (DateFormatUtility.format(startDate.getText().toString()).
                after(DateFormatUtility.format(finishDate.getText().toString()))) {
            throw new TaskControlException("Начальная дата позже даты окончания");
        }
        if (projectAdapter == null) {
            throw new TaskControlException("Проект не выбран");
        }
        saveButton.setEnabled(false);
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private void execute(TasksControlLoader<Task, Void> thread) {
        try {
            Task task = getTask();
            thread.execute(task);
        } catch (TaskControlException e) {
            showMessage(e);
        }
    }

    private void execute(String action) {
        try {
            Intent intent = new Intent();
            intent.putExtra(ProjectActivity.ACTION, action);
            intent.putExtra(TaskActivity.TASK, getTask());
            setResult(RESULT_OK, intent);
            finish();
        } catch (TaskControlException e) {
            showMessage(e);
        }
    }

    private void showMessage(TaskControlException e) {
        Log.e(LOG_TAG, e.getMessage(), e);
        Toast.makeText(TaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setDateButtonListener(final Button date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(TaskActivity.this, setDatePickerListener(date, calendar), year, month, day).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener setDatePickerListener(final Button date, final Calendar calendar) {
        return (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(DateFormatUtility.format(today));
            }
        });
    }

    private void setStatusAdapter(Spinner spinner) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this,
                android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setProjectAdapter(List<Project> projectList) {
        projectAdapter = new ProjectAdapter(TaskActivity.this, projectList);
        projectsSpinner.setAdapter(projectAdapter);
    }

    private void createEmployeeListView() {
        View list = findViewById(R.id.list);

        addTaskInProject = (Button) findViewById(R.id.addEmployee);
        addTaskInProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        pageView = (PageView) list.findViewById(R.id.pageNavigation);
        pageView.setPageSize(PAGE_SIZE);
        pageView.setListener(new NavigationListener() {
            @Override
            public void onPage() {
                loadTaskEmployees();
            }
        });

        employeeListAdapter = new EmployeeAdapter(this, new ArrayList<Employee>());
        ListView listView = (ListView) list.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                createRemoveEmployeeDialog(position).show();
            }
        });

        listView.setAdapter(employeeListAdapter);
        loadTaskEmployees();
    }

    private void addEmployee() {
        if (!employees.isEmpty()) {
            Employee employee = (Employee) employeesSpinnerAdapter.getItem(employeesSpinner.getSelectedItemPosition());
            task.addEmployee(employee);
            employees.remove(employee);
            addTaskInProject.setEnabled(false);
            updateEmployees();
        } else {
            Toast.makeText(TaskActivity.this, "Работников нет", Toast.LENGTH_SHORT).show();
        }
    }

    private AlertDialog createRemoveEmployeeDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        builder.setTitle("Удалить сотрудника с текущей задачи?");
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Employee employee = (Employee) employeeListAdapter.getItem(position);
                task.removeEmployee(employee);
                employees.add(employee);
                updateEmployees();
            }
        });
        return builder.create();
    }

    private void loadTaskEmployees() {
        int startPosition = pageView.getStartPosition();
        int finishPosition = pageView.getFinishPosition();
        List<Employee> employees = getEmployees(startPosition, finishPosition);
        boolean existData = employees.size() >= Math.abs(startPosition - finishPosition);
        employeeListAdapter.update(employees);
        pageView.setExistData(existData);
    }

    private void updateEmployees() {
        employeesSpinnerAdapter.notifyDataSetChanged();
        loadTaskEmployees();
        addTaskInProject.setEnabled(true);
    }

    private List<Employee> getEmployees(int startPosition, int finishPosition) {
        if (startPosition < 0 || startPosition > task.getEmployees().size()) {
            return new ArrayList<Employee>();
        }
        return task.getEmployees().subList(startPosition, Math.min(finishPosition, task.getEmployees().size()));
    }

    /**
     * Поток загрузки проектов
     *
     * @author Q-OLN
     */
    private class ProjectsLoader extends TasksControlLoader<Project, List<Project>> {

        private final int INCREMENT = 100;

        private int startPosition = 0;

        private List<Project> allProjects = new ArrayList<Project>();

        public ProjectsLoader() {
            super(((TasksControlApplication) getApplicationContext()).getServer(), TaskActivity.this);
        }

        @Override
        protected List<Project> processing(List<Project> projects) throws IOException {
            do {
                projects = getServer().loadProjects(startPosition, startPosition + INCREMENT);
                allProjects.addAll(projects);
                startPosition += INCREMENT;
            } while (!projects.isEmpty());

            return allProjects;
        }

        @Override
        protected void postExecuteSuccess(List<Project> projectList) {
            setProjectAdapter(projectList);
        }
    }

    /**
     * Поток загрузки сотрудников
     *
     * @author Q-OLN
     */
    private class EmployeeLoader extends TasksControlLoader<Employee, List<Employee>> {

        private final int INCREMENT = 100;

        private int startPosition = 0;

        private List<Employee> allEmployees = new ArrayList<Employee>();

        public EmployeeLoader() {
            super(((TasksControlApplication) getApplicationContext()).getServer(), TaskActivity.this);
        }

        @Override
        protected List<Employee> processing(List<Employee> employees) throws IOException {
            do {
                employees = getServer().loadEmployees(startPosition, startPosition + INCREMENT);
                allEmployees.addAll(employees);
                startPosition += INCREMENT;
            } while (!employees.isEmpty());

            return allEmployees;
        }

        @Override
        protected void postExecuteSuccess(List<Employee> employeeList) {
            for (Employee employee : employeeList) {
                if (task.getEmployees().contains(employee)) {
                    continue;
                }
                employees.add(employee);
            }
            employeesSpinnerAdapter = new EmployeeAdapter(TaskActivity.this, employees);
            employeesSpinner.setAdapter(employeesSpinnerAdapter);
        }
    }
}

