package ru.qulix.olesyuknv.taskscontrol.threads;
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
public abstract class TaskLoader<T, L> extends AsyncTask<T, Void, L> {

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
    protected L doInBackground(T... tasks) {
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
    protected void onPostExecute(L listTasks) {
        super.onPostExecute(listTasks);

        if (httpConnectionException == null) {
            postExecuteSuccess(listTasks);
        } else {
            postExecuteFailed(httpConnectionException);
        }
    }

    private void postExecuteFailed(HttpConnectionException httpConnectionException) {
        Toast.makeText(inputTaskActivity.getApplication(), "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
    }

    /**
     * Обработка задачи
     *
     * @param task задача, для обработки
     * @return обработанные задачи
     * @throws HttpConnectionException
     */
    protected abstract L processing(T task) throws HttpConnectionException;

    /**
     * Действие, выполняемое до запуска потока
     */
    protected void preExecute() {

    };

    /**
     * Действие, выполняемое после запуска потока
     *
     * @param tasks
     */
    protected void postExecuteSuccess(L tasks) {
        inputTaskActivity.finish();
    }
}
