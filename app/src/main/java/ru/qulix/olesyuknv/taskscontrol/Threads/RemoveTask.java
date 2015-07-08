package ru.qulix.olesyuknv.taskscontrol.threads;

import android.os.AsyncTask;

import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * ����� �������� ������
 *
 * @author QULIX-OLESYUKNV
 */
public class RemoveTask extends AsyncTask<Task, Void, Void> {
    private TaskServer server;

    public RemoveTask(TaskServer server) {
        this.server = server;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        for (Task task : tasks) {
            server.remove(task);
        }
        return null;
    }
}
