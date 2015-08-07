package com.example.server;

import java.util.List;

import com.example.InterruptServerException;
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
     * throws InterruptServerException
     */
    void update(Task task) throws InterruptServerException;

    /**
     * Загрузка списка задач
     * @param start начальная позиция списка, start > = 0
     * @param finish N-ая позиция списка, finish > = 0
     * @return список задач
     * throws InterruptServerException
     */
    List<Task> load(int start, int finish) throws InterruptServerException;

    /**
     * Добавить новую задачу
     * @param task задача для добавления
     * throws InterruptServerException
     */
    void add(Task task) throws InterruptServerException;

    /**
     * Удалить задачу
     * @param task задача для удаления
     * throws InterruptServerException
     */
    void remove(Task task) throws InterruptServerException;
}
