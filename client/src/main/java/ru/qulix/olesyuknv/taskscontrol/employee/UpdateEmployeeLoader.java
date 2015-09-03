package ru.qulix.olesyuknv.taskscontrol.employee;

import java.io.IOException;
import java.util.List;

import com.example.models.Employee;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

import android.app.Activity;

/**
 * Поток обновления сотрудника
 *
 * @author Q-OLN
 */
public class UpdateEmployeeLoader extends TasksControlLoader<Employee, Void> {

    public UpdateEmployeeLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Employee> employees) throws IOException {
        for (Employee employee : employees) {
            getServer().update(employee);
        }
        return null;
    }
}