package ru.qulix.olesyuknv.taskscontrol.threads;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;

import android.view.View;
import android.widget.Toast;

import ru.qulix.olesyuknv.taskscontrol.TaskAdapter;
import ru.qulix.olesyuknv.taskscontrol.activities.MainActivity;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Поток загрузки всех задач
 *
 * @author QULIX-OLESYUKNV
 */
public class LoadTasks extends AsyncTask<Void, Void, List<Task>> {
    private TaskServer server;
    private MainActivity mainActivity;

    public LoadTasks(TaskServer server, MainActivity mainActivity) {
        this.server = server;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mainActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            throw new RuntimeException();
        }
        return server.load();
    }


    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        mainActivity.taskAdapter = new TaskAdapter(mainActivity, tasks);
        mainActivity.listView.setAdapter(mainActivity.taskAdapter);
        mainActivity.progressBar.setVisibility(View.GONE);
        Toast.makeText(mainActivity, "Tasks Downloaded", Toast.LENGTH_SHORT).show();
    }
}
