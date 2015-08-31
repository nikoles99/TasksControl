package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.server.TaskServer;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Поток работы с задачами
 *
 * @author Q-OLN
 */
public abstract class TasksControlLoader<T, L> extends AsyncTask<T, Void, L> {

    private static final String LOG_TAG = TasksControlLoader.class.getName();
    private IOException serverException;
    private Activity activity;
    private TaskServer server;

    public TasksControlLoader(TaskServer server, Activity activity) {
        this.activity = activity;
        this.server = server;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preExecute();
    }

    @Override
    protected L doInBackground(T... params) {
        try {
            return processing(new ArrayList<T>(Arrays.asList(params)));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            serverException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(L list) {
        super.onPostExecute(list);

        if (serverException == null) {
            postExecuteSuccess(list);
        } else {
            postExecuteFailed(serverException);
        }
    }

    /**
     * Действие, выполняемое при ошибке работы потока
     *
     * @param exception ошибка
     */
    protected void postExecuteFailed(IOException exception) {
        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Обработка задачи
     *
     * @param object объект, для обработки
     * @return обработанные обьекты
     * @throws IOException
     */
    protected abstract L processing(List<T> object) throws IOException;

    /**
     * Действие, выполняемое до запуска потока
     */
    protected void preExecute() {

    };

    /**
     * Действие, выполняемое после успешного завершения работы потока
     *
     * @param list список обьектов
     */
    protected void postExecuteSuccess(L list) {
        activity.setResult(Activity.RESULT_OK, activity.getIntent());
        activity.finish();
    }

    protected TaskServer getServer() {
        return server;
    }
}
