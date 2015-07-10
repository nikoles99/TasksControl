package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.List;

import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * Сервер позволяющий хранить, добавлять, удалять и изменять задачи.
 *
 * @author QULIX-OLESYUKNV
 */
public interface TaskServer {

    /**
     * @param task задача для изменения
     */
    void update(Task task);

    /**
     * @return tasks список задач из N элементов
     */
    List<Task> load(int startPosition, int finishPosition);

    /**
     * @param task задача для добавления
     */
    void add(Task task);

    /**
     * @param task задача для удаления
     */
    void remove(Task task);
}
