package ru.qulix.olesyuknv.taskscontrol;

import java.util.List;

/**
 * Server.
 *
 * @author OlelesyukNV
 */
public interface Server {

    /**
     * @param task     object for changes.
     * @param position position this object.
     */
    void updateDataOnServer(Object task, int position);

    /**
     * @return objects from server.
     */
    List<Object> loadDataFromServer();

    /**
     * @param task object for adding.
     */
    void addDataOnServer(Object task);

    /**
     * @param position of delete object.
     */
    void removeTask(int position);
}
