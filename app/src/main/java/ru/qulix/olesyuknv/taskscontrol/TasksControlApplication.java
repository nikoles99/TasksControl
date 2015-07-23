package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import android.app.Application;

/**
 * Получение сервера.
 *
 * @author QULIX-OLESYUKNV
 */
public class TasksControlApplication extends Application {

    private TaskServer server = new HTTPRequests();  // or StubServer

    public TaskServer getServer() {
        return server;
    }

}
