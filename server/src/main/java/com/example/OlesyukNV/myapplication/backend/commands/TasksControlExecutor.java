package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.server.TaskServer;

/**
 * Исполнитель действий на сервере
 *
 * @author Q-OLN
 */
public abstract class TasksControlExecutor {

    /**
     * Выполнить действие
     *
     * @param request    получение параметов запроса
     * @param taskServer сервер для выбора действий
     * @return строку, хранящую результат действий сервера
     * @throws IOException ошибка сервлета
     */
    public abstract String execute(HttpServletRequest request, TaskServer taskServer) throws IOException;

}
