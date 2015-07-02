package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.util.Log;

import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток удаления задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class RemoveTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public RemoveTask(TaskServer server) {
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
            server.remove(task);
        }
        return null;
    }
}
