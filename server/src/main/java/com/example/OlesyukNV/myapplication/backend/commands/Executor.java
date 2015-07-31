package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import com.example.exceptions.HttpConnectionException;
import com.example.server.TaskServer;

/**
 * Исполнитель действий на сервере
 *
 * @author Q-OLN
 */
public interface Executor {
    /**
     * Выполнить действие
     * @param request парамерты запроса
     * @param taskServer сервер для выбора действий(загручить задачи, обновить задачу, добавить задачу, удалить задачу )
     * @return строку, хранящую результат действий сервера
     */
    String execute(HttpServletRequest request, TaskServer taskServer) throws HttpConnectionException;
}
