package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import android.app.Application;
import android.os.AsyncTask;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток загрузки задач
 * *
 *
 * @author QULIX-OLESYUKNV
 */
public class BackgroundUploader extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;
    private ProgressBar progressBar;
    private TaskAdapter taskAdapter;
    private int loadFlag;

    public static final int NEXT_PAGE = 1;
    public static final int PREVIOUS_PAGE = 2;

    public BackgroundUploader(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter) {
        this.server = server;
        this.progressBar = progressBar;
        this.taskAdapter = taskAdapter;
        this.loadFlag = loadFlag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {

        if (loadFlag == NEXT_PAGE) {
            return server.loadNextPack();
        } else if (loadFlag == PREVIOUS_PAGE) {
            return server.loadPreviousPack();
        } else {
            return server.load();
        }
    }


    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        taskAdapter.updateTasksList(tasks);

        if (tasks.size() == 0) {
            Toast.makeText(progressBar.getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);

    }
}
