package ru.qulix.olesyuknv.taskscontrol;

import com.example.EmployeeException;
import com.example.models.Employee;
import com.example.server.TaskServer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.qulix.olesyuknv.taskscontrol.comands.AddEmployeeLoader;
import ru.qulix.olesyuknv.taskscontrol.comands.RemoveEmployeeLoader;
import ru.qulix.olesyuknv.taskscontrol.comands.UpdateEmployeeLoader;

/**
 * Форма создания и изменения сотрудника
 *
 * @author Q-OLN
 */
public class EmployeeActivity extends Activity {

    /**
     * Параметр Intent, для хранения сотрудника
     */
    public static final String EMPLOYEE = EmployeeActivity.class + ".EMPLOYEE";

    /**
     * Идентификатор EmployeeActivity
     */
    public static final int REQUEST_CODE = 1;

    private static final String LOG_TAG = EmployeeActivity.class.getName();

    private TextView id;

    private EditText surname;
    private EditText name;
    private EditText middleName;
    private EditText post;

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        employee = (Employee) getIntent().getSerializableExtra(EMPLOYEE);

        boolean addMode = employee == null;

        if (addMode) {
            employee = new Employee();
        }
        createView(addMode);

        setEmployee(employee);
    }

    private void createView(final boolean addMode) {
        setContentView(R.layout.activity_employee);

        id = (TextView) findViewById(R.id.id);
        surname = (EditText) findViewById(R.id.surname);
        name = (EditText) findViewById(R.id.name);
        middleName = (EditText) findViewById(R.id.middleName);
        post = (EditText) findViewById(R.id.post);

        ImageView deleteEmployeeButton = (ImageView) findViewById(R.id.deleteEmployeeButton);
        deleteEmployeeButton.setVisibility((!addMode) ? View.VISIBLE : View.INVISIBLE);
        deleteEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(new RemoveEmployeeLoader(getServer(), EmployeeActivity.this));
            }
        });

        final ImageView addEmployeeButton = (ImageView) findViewById(R.id.addEmployeeButton);
        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute(addMode ? new AddEmployeeLoader(getServer(), EmployeeActivity.this) : new UpdateEmployeeLoader(getServer(),
                        EmployeeActivity.this));
                addEmployeeButton.setEnabled(false);
            }
        });
    }

    private void setEmployee(Employee employee) {
        this.employee = employee;
        id.setText(String.valueOf(employee.getId()));
        surname.setText(employee.getSurname());
        name.setText(employee.getName());
        middleName.setText(employee.getMiddleName());
        post.setText(employee.getPost());
    }

    private Employee getEmployee() throws EmployeeException {
        validate();
        employee.setSurname(surname.getText().toString());
        employee.setName(name.getText().toString());
        employee.setMiddleName(middleName.getText().toString());
        employee.setPost(post.getText().toString());
        return employee;
    }

    private void validate() throws EmployeeException {
        if (TextUtils.isEmpty(surname.getText())) {
            throw new EmployeeException("Введите Фамилию сотрудника");
        }
        if (TextUtils.isEmpty(name.getText())) {
            throw new EmployeeException("Введите Имя сотрудника");
        }
        if (TextUtils.isEmpty(middleName.getText())) {
            throw new EmployeeException("Введите Отчество Сотрудника");
        }
        if (TextUtils.isEmpty(post.getText())) {
            throw new EmployeeException("Введите Должность Сотрудника");
        }
    }

    private TaskServer getServer() {
        return ((TasksControlApplication) getApplicationContext()).getServer();
    }

    private void execute(TasksControlLoader thread) {
        try {
            Employee employee = getEmployee();
            thread.execute(employee);
        } catch (EmployeeException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            Toast.makeText(EmployeeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
