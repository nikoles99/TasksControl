package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import com.example.exceptions.HttpConnectionException;
import com.example.server.TaskServer;

/**
 * Исполнитель действий на сервере
 *
 * @author Q-OLN
 */
public abstract class TaskExecutor {
    /**
     * Выполнить действие
     *
     * @param request    парамерты запроса
     * @param taskServer сервер для выбора действий(загручить задачи, обновить задачу, добавить задачу, удалить задачу )
     * @return строку, хранящую результат действий сервера
     */
    public String execute(HttpServletRequest request, TaskServer taskServer) {

        try {
            return getAction(request, taskServer);
        } catch (HttpConnectionException e) {
            throw new IllegalStateException("Error with server connection");
        }
    }

    protected abstract String getAction(HttpServletRequest request, TaskServer taskServer) throws HttpConnectionException;

}
