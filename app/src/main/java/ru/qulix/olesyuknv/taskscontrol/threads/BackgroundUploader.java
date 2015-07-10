package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
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
    private int loadFlag;

    private static final int INCREMENT = 10;

    public static int startPosition = 0;

    public static int finishPosition = INCREMENT;
    private static boolean iseDataFinish = true;
    private static boolean iseDataStart = true;

    public BackgroundUploader(TaskServer server, ProgressBar progressBar, TaskAdapter taskAdapter,
                              int loadFlag) {
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

            switch (loadFlag) {
                case 1:
                    if(iseDataFinish) {
                        startPosition += INCREMENT;
                        finishPosition += INCREMENT;
                        iseDataFinish = false;
                        iseDataStart = true;
                    }
                    break;
                case 2:
                    if(iseDataStart) {
                        startPosition -= INCREMENT;
                        finishPosition -= INCREMENT;
                        iseDataStart = false;
                        iseDataFinish = true;
                    }
                    break;
                default:
                    startPosition = 0;
                    finishPosition = INCREMENT;
                    break;
            }

        return server.load(startPosition, finishPosition);
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        taskAdapter.updateTasksList(tasks);

        if (tasks.size() == 0) {
            Toast.makeText(progressBar.getContext(), "no data", Toast.LENGTH_SHORT).show();
            iseDataFinish = false;
            iseDataStart = false;
        }
        else {
            iseDataFinish = true;
            iseDataStart = true;
        }
        progressBar.setVisibility(View.GONE);

    }
}
