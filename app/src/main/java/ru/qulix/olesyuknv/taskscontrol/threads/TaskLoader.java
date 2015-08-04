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
                return processing(task);
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

    /**
     * Обработка задачи
     * @param task задача, для обработки
     * @return список обработанных задач
     * @throws HttpConnectionException
     */
    public abstract List<T> processing(T task) throws HttpConnectionException;

    /**
     * Действие, выполняемое до запуска потока
     */
    public abstract void preExecute();

    /**
     * Действие, выполняемое после запуска потока
     * @param tasks список задач
     */
    public abstract void postExecute(List<T> tasks);
}
