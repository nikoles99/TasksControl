package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import com.example.exceptions.HttpConnectionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Поток работы с задачами
 *
 * @author Q-OLN
 */
public abstract class TaskLoader<T> extends AsyncTask<T, Void, List<T>> {

    private static final String LOG_TAG = TaskLoader.class.getName();
    private HttpConnectionException httpConnectionException;
    private Activity inputTaskActivity;

    public TaskLoader(Activity inputTaskActivity) {
        this.inputTaskActivity = inputTaskActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preExecute();
    }

    @Override
    protected List<T> doInBackground(T... tasks) {
        for (T task : tasks) {
            try {
                return getAction(task);
            } catch (HttpConnectionException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                httpConnectionException = e;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<T> successConnection) {
        super.onPostExecute(successConnection);

        if (httpConnectionException == null) {
            postExecute(successConnection);
        } else {
            Toast.makeText(inputTaskActivity.getApplication(), "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract List<T> getAction(T task) throws HttpConnectionException;

    public abstract void preExecute();

    public abstract void postExecute(List<T> tasks);
}
