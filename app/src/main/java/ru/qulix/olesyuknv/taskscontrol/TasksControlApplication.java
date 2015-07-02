package ru.qulix.olesyuknv.taskscontrol;

import android.app.Application;

import ru.qulix.olesyuknv.taskscontrol.server.StubServer;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;

/**
 * Получение сервера.
 * @author QULIX-OLESYUKNV
 */
public class TasksControlApplication extends Application {

    private TaskServer server = new StubServer();

    public TaskServer getServer() {
        return server;
    }


}
