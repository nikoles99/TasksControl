package ru.qulix.olesyuknv.taskscontrol.threads;

import android.os.AsyncTask;

import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток удаления задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class BackgroundDeletingTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public BackgroundDeletingTask(TaskServer server) {
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
