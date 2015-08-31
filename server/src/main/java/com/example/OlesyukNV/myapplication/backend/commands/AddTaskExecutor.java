package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.server.TaskServer;
import com.example.utils.TaskFormatUtility;

/**
 * Команда, добавления задачи
 *
 * @author Q-OLN
 */
public class AddTaskExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.add(TaskFormatUtility.getTask(jsonString));
        return null;
    }
}
