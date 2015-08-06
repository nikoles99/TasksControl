package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.ServletException;
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
     * @throws ServletException
     */
    public String execute(HttpServletRequest request, TaskServer taskServer) throws ServletException {
        try {
            return processing(request, taskServer);
        } catch (HttpConnectionException e) {
            throw new ServletException("Error with server connection", e);
        }
    }

    /**
     * Обработать действие
     *
     * @param request    получение параметов запроса
     * @param taskServer сервер для выбора действий(загручить задачи, обновить задачу, добавить задачу, удалить задачу )
     * @return строку, хранящую результат действий сервера
     * @throws HttpConnectionException
     */
    protected abstract String processing(HttpServletRequest request, TaskServer taskServer) throws HttpConnectionException;

}
