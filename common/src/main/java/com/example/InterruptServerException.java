package com.example;

/**
 * Ошибка соединения с сервером
 *
 * @author Q-OLN
 */
public class InterruptServerException extends Exception {

    public InterruptServerException(Throwable cause) {
        super(cause);
    }

    public InterruptServerException(String message) {
        super(message);
    }
}
