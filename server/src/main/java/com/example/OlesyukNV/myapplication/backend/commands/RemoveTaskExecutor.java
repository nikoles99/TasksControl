package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.server.TaskServer;
import com.example.utils.TaskFormatUtility;

/**
 * Команда, удаления задачи
 *
 * @author Q-OLN
 */
public class RemoveTaskExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.remove(TaskFormatUtility.getTask(jsonString));
        return null;
    }
}
