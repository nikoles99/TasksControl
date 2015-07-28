package ru.qulix.olesyuknv.taskscontrol.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

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
            return server.load(pageView.getPageNavigation().getStartPosition(),
                    pageView.getPageNavigation().getFinishPosition());
        } catch (JSONException e) {
            Logger.getLogger(PartTaskLoader.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        catch (IOException e) {
            Logger.getLogger(PartTaskLoader.class.getName()).log(Level.ALL, e.getMessage(), e);
            publishProgress();
        }
        return new ArrayList<Task>();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);

        if (tasks.size() < Math.abs(pageView.getPageNavigation().getFinishPosition() -
                pageView.getPageNavigation().getStartPosition())) {
            pageView.setExistData(false);
        }
        if (tasks.isEmpty()) {
            Toast.makeText(pageView.getContext().getApplicationContext(), "Данных нет", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.GONE);
        taskAdapter.updateTasksList(tasks);
    }

    @Override
    protected void onProgressUpdate(Void... voids) {
        super.onProgressUpdate();
        Toast.makeText(pageView.getContext().getApplicationContext(), "Ошибка соеденения с сервером", Toast.LENGTH_SHORT).show();
    }
}
