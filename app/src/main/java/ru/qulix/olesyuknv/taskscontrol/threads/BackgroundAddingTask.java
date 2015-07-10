package ru.qulix.olesyuknv.taskscontrol.threads;

import android.app.Activity;
import android.os.AsyncTask;

import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток добавления задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class BackgroundAddingTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;
    private Activity inputTaskActivity;

    public BackgroundAddingTask(TaskServer server, Activity inputTaskActivity) {
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        for (Task task : tasks) {
            server.add(task);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        inputTaskActivity.finish();
    }
}
