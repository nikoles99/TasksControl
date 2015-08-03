package ru.qulix.olesyuknv.taskscontrol.threads;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Поток
 *
 * @author Q-OLN
 */
public abstract class TaskLoader extends AsyncTask<Task, Void, Object> {

    private static final String LOG_TAG = TaskLoader.class.getName();
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
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Object successConnection) {
        super.onPostExecute(successConnection);

        if ((Boolean) successConnection == true) {
            inputTaskActivity.finish();
        } else {
            Toast.makeText(inputTaskActivity.getApplication(), "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract Object getAction(Task task) throws HttpConnectionException;

}
