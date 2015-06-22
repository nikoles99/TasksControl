package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;

public interface Server {
    public void  updateDataOnServer(Task task);
    public ArrayList<Task> loadDataFromServer();
    public void  addDataOnServer(Task task);
    public void  removeTask(Task task);
}
