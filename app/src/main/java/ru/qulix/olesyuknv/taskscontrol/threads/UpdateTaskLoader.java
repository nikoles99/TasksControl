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
public class UpdateTaskLoader extends Loader {

    public UpdateTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super.server = server;
        super.inputTaskActivity = inputTaskActivity;
    }

    @Override
    public void getAction(Task task) throws HttpConnectionException {
        server.update(task);
    }
}
