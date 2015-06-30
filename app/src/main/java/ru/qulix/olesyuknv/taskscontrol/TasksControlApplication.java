package ru.qulix.olesyuknv.taskscontrol;

import android.app.Application;

/**
 * Created by OlesyukNV on 30.06.2015.
 */
public class TasksControlApplication extends Application {

    private Server server = new StubServer();

    public Server getStubServer() {
        return server;
    }

}
