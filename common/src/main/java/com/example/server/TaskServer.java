package com.example.server;

import java.io.IOException;
import java.util.List;

import com.example.models.Employee;
import com.example.models.Project;
import com.example.models.Task;

/**
 * Сервер, управляющий задачами.
 *
 * @author Q-OLN
 */
public interface TaskServer {

    /**
     * Обновить задачу
     *
     * @param task задача для обновления
     * @throws IOException ошибка работы с сервером
     */
    void update(Task task) throws IOException;

    /**
     * Добавить новую задачу
     *
     * @param task задача для добавления
     * @throws IOException ошибка работы с сервером
     */
    void add(Task task) throws IOException;

    /**
     * Удалить задачу
     *
     * @param task задача для удаления
     * @throws IOException ошибка работы с сервером
     */
    void remove(Task task) throws IOException;

    /**
     * Загрузка списка задач
     *
     * @param start  начальная позиция списка, start > = 0
     * @param finish N-ая позиция списка, finish > = 0
     * @return список задач
     * @throws IOException ошибка работы с сервером
     */
    List<Task> loadTasks(int start, int finish) throws IOException;

    /**
     * Обновить проект
     *
     * @param project проект для обновления
     * @throws IOException ошибка работы с сервером
     */
    void update(Project project) throws IOException;

    /**
     * Добавить проект
     *
     * @param project проект для добавления
     * @throws IOException ошибка работы с сервером
     */
    void add(Project project) throws IOException;

    /**
     * Удалить проект
     *
     * @param project проект для удаления
     * @throws IOException ошибка работы с сервером
     */
    void remove(Project project) throws IOException;

    /**
     * Загрузить список проектов
     *
     * @param start  начальная позиция списка, start > = 0
     * @param finish N-ая позиция списка, finish > = 0
     * @return список проектов
     * @throws IOException ошибка работы с сервером
     */
    List<Project> loadProjects(int start, int finish) throws IOException;

    /**
     * Обновить сотрудника
     *
     * @param employee сотрудник для обновления
     * @throws IOException ошибка работы с сервером
     */
    void update(Employee employee) throws IOException;

    /**
     * Добавить сотрудника
     *
     * @param employee сотрудник для добавления
     * @throws IOException ошибка работы с сервером
     */
    void add(Employee employee) throws IOException;

    /**
     * Удалить сотрудника
     *
     * @param employee сотрудник для удаления
     * @throws IOException ошибка работы с сервером
     */
    void remove(Employee employee) throws IOException;

    /**
     * Загрузить список сотрудников
     *
     * @param start  начальная позиция списка, start > = 0
     * @param finish N-ая позиция списка, finish > = 0
     * @return список сотрудников
     * @throws IOException ошибка работы с сервером
     */
    List<Employee> loadEmployees(int start, int finish) throws IOException;
}
