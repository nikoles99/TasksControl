package ru.qulix.olesyuknv.taskscontrol.Threads;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import ru.qulix.olesyuknv.taskscontrol.Task;
import ru.qulix.olesyuknv.taskscontrol.TaskServer;

/**
 * Поток загрузки всех задач
 *
 * @author QULIX-OLESYUKNV
 */
public class LoadTasks extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;

    private ProgressBar progressBar;

    public LoadTasks(TaskServer server) {
        this.server = server;
        // this.progressBar = progressBar;
    }

  /*  @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }*/

    @Override
    protected List<Task> doInBackground(Void... voids) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Log.e("ERROR", e.toString());
        }
        return server.load();
    }


  /*  @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        progressBar.setVisibility(View.GONE);
    }*/
}
