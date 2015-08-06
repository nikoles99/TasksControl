package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

/**
 * Поток удаления задачи
 *
 * @author Q-OLN
 */
public class RemoveTaskLoader extends TaskLoader<Task, Void> {
    private  TaskServer server;

    public RemoveTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super(inputTaskActivity);
        this.server = server;
    }

    @Override
    protected Void processing(Task task) throws HttpConnectionException {
        server.remove(task);
        return null;
    }
}
