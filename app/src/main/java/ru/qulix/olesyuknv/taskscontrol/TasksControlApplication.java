package ru.qulix.olesyuknv.taskscontrol;

import android.app.Application;


public class TasksControlApplication extends Application {

    private TaskServer server = new StubServer();

    public TaskServer getServer() {
        return server;
    }

}
