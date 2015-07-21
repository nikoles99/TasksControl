package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.server.TaskServer;
import com.example.models.Task;

import android.app.Activity;
import android.os.AsyncTask;
/**
 * Поток обновления задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class UpdateTaskLoader extends AsyncTask<Task, Void, Void> {
    private TaskServer server;
    private Activity inputTaskActivity;

    public UpdateTaskLoader(TaskServer server, Activity inputTaskActivity) {
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        for (Task task : tasks) {
            server.update(task);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        inputTaskActivity.finish();
    }
}
