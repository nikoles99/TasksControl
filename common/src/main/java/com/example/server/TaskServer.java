package com.example.server;

import java.util.List;

import com.example.models.Task;

/**
 * Сервер позволяющий хранить, добавлять, удалять и изменять задачи.
 *
 * @author Q-OLN
 */
public interface TaskServer {

    /**
     * @param task задача для изменения
     */
    void update(Task task);

    /**
     * @param start начальная позиция списка
     * @param finish N-ая позиция списка
     * @return @return tasks список задач из N элементов
     */
    List<Task> load(int start, int finish);

    /**
     * @param task задача для добавления
     */
    void add(Task task);

    /**
     * @param task задача для удаления
     */
    void remove(Task task);
}
