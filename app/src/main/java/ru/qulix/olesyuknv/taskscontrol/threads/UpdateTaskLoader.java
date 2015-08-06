package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

/**
 * Поток обновления задачи
 *
 * @author Q-OLN
 */
public class UpdateTaskLoader extends TaskLoader<Task, Void> {

    private  TaskServer server;

    public UpdateTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super(inputTaskActivity);
        this.server = server;
    }

    @Override
    protected Void processing(Task task) throws HttpConnectionException {
        server.update(task);
        return null;
    }
}
