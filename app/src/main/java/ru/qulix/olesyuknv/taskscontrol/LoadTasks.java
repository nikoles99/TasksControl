package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class LoadTasks extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;

    public LoadTasks(TaskServer server) {
        this.server = server;
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return server.load();
    }
}
