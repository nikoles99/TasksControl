package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Поток
 *
 * @author Q-OLN
 */
public abstract class Loader extends AsyncTask<Task, Void, Boolean>  {

    protected TaskServer server;
    protected Activity inputTaskActivity;
    protected boolean successConnection;
    protected String action;

    @Override
    protected Boolean doInBackground(Task... tasks) {
        for (Task task : tasks) {
            try {
                getAction(task);
                successConnection = true;
            } catch (HttpConnectionException e) {
                successConnection = false;
            }
        }
        return successConnection;
    }

    @Override
    protected void onPostExecute(Boolean successConnection) {
        super.onPostExecute(successConnection);

        if (successConnection == true) {
            inputTaskActivity.finish();
        } else {
            Toast.makeText(inputTaskActivity.getApplication(), "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
        }

    }

    public abstract void getAction(Task task) throws HttpConnectionException;

}
