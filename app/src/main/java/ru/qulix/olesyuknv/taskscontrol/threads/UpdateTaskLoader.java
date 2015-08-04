package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

/**
 * Поток обновления задачи
 *
 * @author Q-OLN
 */
public class UpdateTaskLoader extends TaskLoader {

    private  TaskServer server;
    private  Activity inputTaskActivity;

    public UpdateTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super(inputTaskActivity);
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    public List getAction(Object task) throws HttpConnectionException {
        server.update((Task)task);
        return null;
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(List tasks) {
        inputTaskActivity.finish();
    }


}
