package com.example;

/**
 * Ошибка при работе с Employee
 *
 * @author Q-OLN
 */
public class EmployeeException extends Exception {

    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public EmployeeException(String message) {
        super(message);
    }
}
