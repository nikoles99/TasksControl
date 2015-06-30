package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by OlesyukNV on 30.06.2015.
 */
public class UpdateTaskThread extends AsyncTask<Task, Void, Void> {
    Context context;

    public UpdateTaskThread(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        Server server = ((TasksControlApplication) context).getStubServer();

        for (Task task : tasks) {
            server.updateTask(task);
        }
        return null;
    }
}
