package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

/**
 * Команда, удаления задачи с сервера
 *
 * @author Q-OLN
 */
public class RemoveTaskHandler extends Handler {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.remove(JsonFormatUtility.format(jsonString));
        return null;
    }
}
