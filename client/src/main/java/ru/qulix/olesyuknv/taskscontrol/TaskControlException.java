package ru.qulix.olesyuknv.taskscontrol;

/**
 * Ошибка при работе с Task, Employee, Project
 *
 * @author Q-OLN
 */
public class TaskControlException extends Exception {

    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public TaskControlException(String message) {
        super(message);
    }
}
