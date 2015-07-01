package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;


public class AddTaskTread extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public AddTaskTread(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {

        for (Task task : tasks) {
            server.add(task);
        }
        return null;
    }
}
