package ru.qulix.olesyuknv.taskscontrol.comands;

import java.io.IOException;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

/**
 * Поток удаления задачи
 *
 * @author Q-OLN
 */
public class RemoveTaskLoader extends TasksControlLoader<Task, Void> {

    public RemoveTaskLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Task> tasks) throws IOException {
        for (Task task : tasks) {
            getServer().remove(task);
        }
        return null;
    }
}
