package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;
import java.util.List;


public class SubServer implements Server {

    private List<Task> tasks = new ArrayList<Task>();

    private static volatile SubServer instance;

    public static synchronized SubServer getInstance() {
        if (instance == null) {
            instance = new SubServer();
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
