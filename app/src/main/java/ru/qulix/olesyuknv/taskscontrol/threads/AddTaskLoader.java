package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;

/**
 * Поток добавления задачи
 *
 * @author Q-OLN
 */
public class AddTaskLoader extends TaskLoader {

    private  TaskServer server;
    private  Activity inputTaskActivity;

    public AddTaskLoader(TaskServer server, Activity inputTaskActivity) {
        super(inputTaskActivity);
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }


    @Override
    public List getAction(Object task) throws HttpConnectionException {
        server.add((Task)task);
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
