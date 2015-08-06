package com.example.server;

import java.util.List;

import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;

/**
 * Сервер, работающий с задачами.
 *
 * @author Q-OLN
 */
public interface TaskServer {

    /**
     * Обновить задачу
     * @param task задача для изменения
     */
    void update(Task task) throws HttpConnectionException;

    /**
     * Загрузка списка задач
     * @param start начальная позиция списка, start > = 0
     * @param finish N-ая позиция списка, finish > = 0
     * @return список задач
     */
    List<Task> load(int start, int finish) throws HttpConnectionException;

    /**
     * Добавить новую задачу
     * @param task задача для добавления
     */
    void add(Task task) throws HttpConnectionException;

    /**
     * Удалить задачу
     * @param task задача для удаления
     */
    void remove(Task task) throws HttpConnectionException;
}
