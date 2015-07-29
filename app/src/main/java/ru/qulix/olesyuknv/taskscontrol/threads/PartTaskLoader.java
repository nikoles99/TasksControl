package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.ArrayList;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.PageView;
import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Поток загрузки задач
 *
 * @author Q-OLN
 */
public class PartTaskLoader extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;
    private ProgressBar progressBar;
    private TaskAdapter taskAdapter;
    private PageView pageView;

    public PartTaskLoader(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter, PageView pageView) {
        this.server = server;
        this.progressBar = progressBar;
        this.taskAdapter = taskAdapter;
        this.pageView = pageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        try {
            return server.load(pageView.getStartPosition(), pageView.getFinishPosition());
        } catch (RuntimeException e) {
            return new ArrayList<Task>();
        }
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);

        if (tasks.isEmpty()) {
            Toast.makeText(pageView.getContext().getApplicationContext(), "Данных нет", Toast.LENGTH_SHORT).show();
        }
        if (tasks.size() < Math.abs(pageView.getFinishPosition() - pageView.getStartPosition())) {
            pageView.setExistData(false);
        }

        progressBar.setVisibility(View.GONE);
        taskAdapter.updateTasksList(tasks);
    }

}
