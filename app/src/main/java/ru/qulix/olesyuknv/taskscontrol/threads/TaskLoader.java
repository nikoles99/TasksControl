package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.ArrayList;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Поток работы с задачами
 *
 * @author Q-OLN
 */
public abstract class TaskLoader extends AsyncTask<Task, Void, Object> {

    private static final String LOG_TAG = TaskLoader.class.getName();
    private HttpConnectionException httpConnectionException;
    private Activity inputTaskActivity;

    public TaskLoader(Activity inputTaskActivity) {
        this.inputTaskActivity = inputTaskActivity;
    }

    public TaskLoader() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Task... tasks) {
        for (Task task : tasks) {
            try {
                return getAction(task);
            } catch (HttpConnectionException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                httpConnectionException = e;
            }
        }
        return new ArrayList<Task>();
    }

    @Override
    protected void onPostExecute(Object successConnection) {
        super.onPostExecute(successConnection);

        if (httpConnectionException == null) {
            inputTaskActivity.finish();
        } else {
            Toast.makeText(inputTaskActivity.getApplication(), "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract Object getAction(Task task) throws HttpConnectionException;

}
