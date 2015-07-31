package com.example.exceptions;

/**
 * Ошибка соединения с сервером
 * @author Q-OLN
 */
public class HttpConnectionException extends Exception {

    public HttpConnectionException(Throwable cause) {
        super(cause);
    }
}
