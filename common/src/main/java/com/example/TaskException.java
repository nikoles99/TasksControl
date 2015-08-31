package com.example;

/**
 * Ошибка при работе с Task
 *
 * @author Q-OLN
 */
public class TaskException extends Exception {

    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public TaskException(String message) {
        super(message);
    }
}
