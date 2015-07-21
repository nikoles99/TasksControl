package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import com.example.server.StubServer;

import android.app.Application;

/**
 * Получение сервера.
 *
 * @author QULIX-OLESYUKNV
 */
public class TasksControlApplication extends Application {

    private TaskServer server = new StubServer();
 /*   public TaskServer getServer() {
        return server;
    }*/

    private TaskServer httpRequests = new HTTPRequests();
    public TaskServer getServer() {
        return httpRequests;
    }


}
