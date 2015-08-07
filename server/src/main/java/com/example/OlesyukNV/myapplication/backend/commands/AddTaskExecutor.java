package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.InterruptServerException;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

/**
 * Команда, добавления задачи на сервер
 *
 * @author Q-OLN
 */
public class AddTaskExecutor extends TaskExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws ServletException {
        return super.execute(request, taskServer);
    }

    @Override
    protected String processing(HttpServletRequest request, TaskServer taskServer) throws InterruptServerException {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.add(JsonFormatUtility.format(jsonString));
        return null;
    }
}
