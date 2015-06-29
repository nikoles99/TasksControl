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
     */
    void updateTask(Task task);

    /**
     * @return objects from server.
     */
    List<Task> loadTasks();

    /**
     * @param task object for adding.
     */
    void addTask(Task task);

    /**
     * @param task object for remove.
     */
    void removeTask(Task task);
}
