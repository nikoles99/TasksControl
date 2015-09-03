package ru.qulix.olesyuknv.taskscontrol.employee;

import java.io.IOException;
import java.util.List;

import com.example.models.Employee;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

import android.app.Activity;

/**
 * Поток добавления сотрудника
 *
 * @author Q-OLN
 */
public class AddEmployeeLoader extends TasksControlLoader<Employee, Void> {

    public AddEmployeeLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Employee> employees) throws IOException {
        for (Employee employee : employees) {
            getServer().add(employee);
        }
        return null;
    }
}
