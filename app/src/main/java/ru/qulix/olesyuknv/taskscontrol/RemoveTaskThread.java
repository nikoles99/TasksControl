package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;


public class RemoveTaskThread extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public RemoveTaskThread(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {

        for (Task task : tasks) {
            server.remove(task);
        }
        return null;
    }
}
