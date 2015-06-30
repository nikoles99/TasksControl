package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by OlesyukNV on 30.06.2015.
 */
public class LoadDataThread extends AsyncTask<Void, Void, List<Task>> {
    Context context;

    public LoadDataThread(Context context) {
        this.context = context;
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        Server server = ((TasksControlApplication) context).getStubServer();
        return server.loadTasks();
    }
}
