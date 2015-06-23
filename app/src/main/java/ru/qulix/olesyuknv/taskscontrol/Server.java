package ru.qulix.olesyuknv.taskscontrol;

import java.util.List;

public interface Server {
    void  updateDataOnServer(Task task, int position);
    List<Task> loadDataFromServer();
    void  addDataOnServer(Task task);
    void  removeTask(int position);
}
