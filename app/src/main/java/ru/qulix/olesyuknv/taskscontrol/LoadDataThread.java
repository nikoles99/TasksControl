package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;

import java.util.List;


public class LoadDataThread extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;

    public LoadDataThread(TaskServer server) {
        this.server = server;
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return server.load();
    }
}
