package ru.qulix.olesyuknv.taskscontrol.threads;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Поток добавления задачи
 *
 * @author Q-OLN
 */
public class AddTaskLoader extends AsyncTask<Task, Void, Void> {
    private TaskServer server;
    private Activity inputTaskActivity;

    public AddTaskLoader(TaskServer server, Activity inputTaskActivity) {
        this.server = server;
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        for (Task task : tasks) {
            try {
                server.add(task);
            } catch (IOException e) {
                Logger.getLogger(AddTaskLoader.class.getName()).log(Level.ALL, e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        inputTaskActivity.finish();
    }
}
