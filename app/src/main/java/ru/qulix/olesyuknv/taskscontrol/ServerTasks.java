package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;


public class ServerTasks implements Server {

    private ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void updateDataOnServer(Task task) {

    }

    @Override
    public ArrayList<Task> loadDataFromServer() {
        return null;
    }

    @Override
    public void addDataOnServer(Task task) {

    }

    @Override
    public void removeTask(Task task) {

    }
}
