package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

/**
 * Поток добавления задачи
 *
 * @author Q-OLN
 */
public class AddTaskLoader extends TaskLoader<Task, Void> {

    private  TaskServer server;

    public AddTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super(inputTaskActivity);
        this.server = server;
    }

    @Override
    protected Void processing(Task task) throws HttpConnectionException {
        server.add(task);
        return null;
    }
}
