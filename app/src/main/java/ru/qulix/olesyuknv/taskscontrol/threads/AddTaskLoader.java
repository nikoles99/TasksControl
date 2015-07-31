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
public class AddTaskLoader extends Loader {

    public AddTaskLoader(TaskServer server, Activity inputTaskActivity) {
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }


    @Override
    public void getAction(Task task) throws HttpConnectionException {
        server.add(task);
    }
}
