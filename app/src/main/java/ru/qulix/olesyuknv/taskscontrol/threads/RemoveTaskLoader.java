package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Поток удаления задачи
 *
 * @author Q-OLN
 */
public class RemoveTaskLoader extends AsyncTask<Task, Void, Void> {
    private TaskServer server;
    private Activity inputTaskActivity;

    public RemoveTaskLoader(TaskServer server, Activity inputTaskActivity) {
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        for (Task task : tasks) {
            server.remove(task);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        inputTaskActivity.finish();
    }
}
