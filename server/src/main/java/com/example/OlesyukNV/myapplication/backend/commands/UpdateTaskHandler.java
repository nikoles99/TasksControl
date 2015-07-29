package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

/**
 * Команда, обновления задачи на сервере
 *
 * @author Q-OLN
 */
public class UpdateTaskHandler extends Handler {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.update(JsonFormatUtility.format(jsonString));
        return null;
    }
}
