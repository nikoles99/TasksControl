package com.example;

/**
 * Ошибка при работе с Project
 *
 * @author Q-OLN
 */
public class ProjectException extends Exception {

    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public ProjectException(String message) {
        super(message);
    }
}
