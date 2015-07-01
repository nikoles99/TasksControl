package ru.qulix.olesyuknv.taskscontrol;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author OlesyukNV
 */
public class AddTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public AddTask(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Task task : tasks) {
            server.add(task);
        }
        return null;
    }
}
