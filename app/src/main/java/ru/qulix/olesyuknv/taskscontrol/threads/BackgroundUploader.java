package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;

import android.os.AsyncTask;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.activities.MainActivity;
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
    private MainActivity mainActivity;
    private int startPosition;
    private int finishPosition;

    public BackgroundUploader(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter,
                              MainActivity mainActivity) {
        this.server = server;
        this.progressBar = progressBar;
        this.taskAdapter = taskAdapter;
        this.mainActivity = mainActivity;
        startPosition = mainActivity.getStartPosition();
        finishPosition = mainActivity.getFinishPosition();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (startPosition < 0) {
            startPosition += 10;
            finishPosition += 10;
            setListPositions(startPosition, finishPosition);
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return server.load(startPosition, finishPosition);
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);

        if (tasks.size() == 0) {
            startPosition -= 10;
            finishPosition -= 10;
            setListPositions(startPosition, finishPosition);
            progressBar.setVisibility(View.GONE);
            return;
        }
        taskAdapter.updateTasksList(tasks);
        progressBar.setVisibility(View.GONE);

    }

    private void setListPositions(int startPosition, int finishPosition) {
        Toast.makeText(progressBar.getContext(), "no data", Toast.LENGTH_SHORT).show();
        mainActivity.setStartPosition(startPosition);
        mainActivity.setFinishPosition(finishPosition);
    }

}
