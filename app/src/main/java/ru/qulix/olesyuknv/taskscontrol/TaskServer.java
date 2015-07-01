package ru.qulix.olesyuknv.taskscontrol;

import java.util.List;

/**
 * Server.
 *
 * @author OlelesyukNV
 */
public interface TaskServer {

    /**
     * @param task object for changes.
     */
    void update(Task task);

    /**
     * @return objects from server.
     */
    List<Task> load();

    /**
     * @param task object for adding.
     */
    void add(Task task);

    /**
     * @param task object for remove.
     */
    void remove(Task task);
}
