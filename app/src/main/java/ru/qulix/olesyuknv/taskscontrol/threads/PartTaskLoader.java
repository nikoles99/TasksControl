package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import android.os.AsyncTask;

import android.view.View;
import android.widget.ProgressBar;

import ru.qulix.olesyuknv.taskscontrol.utils.PageNavigation;
import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток загрузки задач
 * *
 *
 * @author QULIX-OLESYUKNV
 */
public class PartTaskLoader extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;
    private ProgressBar progressBar;
    private TaskAdapter taskAdapter;
    private PageNavigation pageNavigation;

    public PartTaskLoader(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter, PageNavigation pageNavigation) {
        this.server = server;
        this.progressBar = progressBar;
        this.taskAdapter = taskAdapter;
        this.pageNavigation = pageNavigation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return server.load(pageNavigation.getStartPosition(), pageNavigation.getFinishPosition());
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);

        if (tasks.isEmpty()) {
            pageNavigation.getNextPage().setVisibility(View.INVISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        taskAdapter.updateTasksList(tasks);

    }
}
