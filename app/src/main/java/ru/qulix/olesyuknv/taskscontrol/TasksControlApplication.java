package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import android.app.Application;

/**
 * Получение сервера.
 *
 * @author Q-OLN
 */
public class TasksControlApplication extends Application {

    private TaskServer server = new HttpTaskServer();  // or StubServer

    public TaskServer getServer() {
        return server;
    }

}
