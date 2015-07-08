package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import android.os.AsyncTask;

import android.view.View;
import android.widget.ProgressBar;

import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток загрузки задач
 * *
 *
 * @author QULIX-OLESYUKNV
 */
public class LoadTask extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;
    private ProgressBar progressBar;
    private TaskAdapter taskAdapter;

    public LoadTask(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter) {
        this.server = server;
        this.progressBar = progressBar;
        this.taskAdapter = taskAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return server.load();
    }


    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        taskAdapter.updateTasksList(tasks);
        progressBar.setVisibility(View.GONE);

    }
}
