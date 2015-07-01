package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;


public class UpdateTaskThread extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public UpdateTaskThread(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {

        for (Task task : tasks) {
            server.update(task);
        }
        return null;
    }
}
