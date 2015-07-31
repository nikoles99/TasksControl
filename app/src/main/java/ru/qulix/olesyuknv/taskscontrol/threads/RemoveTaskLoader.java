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
public class RemoveTaskLoader extends Loader {

    public RemoveTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super.server = server;
        super.inputTaskActivity = inputTaskActivity;
    }

    @Override
    public void getAction(Task task) throws HttpConnectionException {
        server.remove(task);
    }
}
