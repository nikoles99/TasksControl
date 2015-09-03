package ru.qulix.olesyuknv.taskscontrol.task;

import java.io.IOException;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

import android.app.Activity;

/**
 * Поток добавления задачи
 *
 * @author Q-OLN
 */
public class AddTaskLoader extends TasksControlLoader<Task, Void> {

    public AddTaskLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Task> tasks) throws IOException {
        for (Task task : tasks) {
            getServer().add(task);
        }
        return null;
    }
}
