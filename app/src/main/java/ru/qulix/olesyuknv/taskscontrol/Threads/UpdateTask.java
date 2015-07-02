package ru.qulix.olesyuknv.taskscontrol.Threads;

import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.os.AsyncTask;

import ru.qulix.olesyuknv.taskscontrol.Task;
import ru.qulix.olesyuknv.taskscontrol.TaskServer;

/**
 * Поток обновления задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class UpdateTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public UpdateTask(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Log.e("ERROR", e.toString());
        }
        for (Task task : tasks) {
            server.update(task);
        }
        return null;
    }
}
