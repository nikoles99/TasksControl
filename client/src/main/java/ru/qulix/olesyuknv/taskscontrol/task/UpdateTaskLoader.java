package ru.qulix.olesyuknv.taskscontrol.task;

import java.io.IOException;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

import android.app.Activity;

/**
 * Поток обновления задачи
 *
 * @author Q-OLN
 */
public class UpdateTaskLoader extends TasksControlLoader<Task, Void> {

    public UpdateTaskLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Task> tasks) throws IOException {
        for (Task task : tasks) {
            getServer().update(task);
        }
        return null;
    }
}
