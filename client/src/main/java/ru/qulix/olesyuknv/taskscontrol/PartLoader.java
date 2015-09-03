package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.List;

import com.example.server.TaskServer;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;


/**
 * Поток почастичной загрузки
 *
 * @author Q-OLN
 */
public abstract class PartLoader<T> extends TasksControlLoader<T, List<T>> {

    private TaskServer server;

    private PageView pageView;

    private ProgressBar progressBar;

    private TasksControlAdapter adapter;

    public PartLoader(TaskServer server, PageView pageView, ProgressBar progressBar, Activity activity,
                      TasksControlAdapter adapter) {
        super(server, activity);
        this.server = server;
        this.progressBar = progressBar;
        this.pageView = pageView;
        this.adapter = adapter;
    }

    @Override
    protected void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<T> list) {
        super.onPostExecute(list);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void postExecuteSuccess(List<T> projects) {
        updateProjectAdapter(projects);
    }

    @Override
    protected void postExecuteFailed(IOException exception) {
        super.postExecuteFailed(exception);
        pageView.disable();
    }

    private void updateProjectAdapter(List<T> projects) {
        boolean existData = projects.size() >= Math.abs(getFinishPosition() - getStartPosition());
        adapter.update(projects);
        pageView.setExistData(existData);
    }

    protected TaskServer getServer() {
        return server;
    }

    public int getFinishPosition() {
        return pageView.getFinishPosition();
    }

    public int getStartPosition() {
        return pageView.getStartPosition();
    }
}
