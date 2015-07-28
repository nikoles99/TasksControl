package com.example.server;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

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
    void update(Task task) throws IOException;

    /**
     * @param start начальная позиция списка
     * @param finish N-ая позиция списка
     * @return @return tasks список задач из N элементов
     */
    List<Task> load(int start, int finish) throws IOException, JSONException;

    /**
     * @param task задача для добавления
     */
    void add(Task task) throws IOException;

    /**
     * @param task задача для удаления
     */
    void remove(Task task) throws IOException;
}
