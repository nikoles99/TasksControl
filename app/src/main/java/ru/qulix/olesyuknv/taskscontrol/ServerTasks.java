package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;
import java.util.List;


public class ServerTasks implements Server {

    private List<Task> tasks = new ArrayList<>();

    private static volatile ServerTasks instance;

    public static synchronized ServerTasks getInstance() {
        if (instance == null) {
            instance = new ServerTasks();
        }
        return instance;
    }

    @Override
    public void updateDataOnServer(Task task, int position) {

    }

    @Override
    public List<Task> loadDataFromServer() {
        return null;
    }

    @Override
    public void addDataOnServer(Task task) {

    }

    @Override
    public void removeTask(int position) {

    }
}
