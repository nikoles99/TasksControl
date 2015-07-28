package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.example.Constants;
import com.example.utils.JsonFormatUtility;
import com.example.server.TaskServer;

/**
 * Команда, удаления задачи с сервера
 *
 * @author Q-OLN
 */
public class RemoveTaskHandler extends Handler {

    @Override
    public JSONArray execute(HttpServletRequest request, TaskServer taskServer) {
        String jsonString = request.getParameter(Constants.ENTITY);
        try {
            taskServer.remove(JsonFormatUtility.format(jsonString));
        } catch (IOException e) {
            Logger.getLogger(RemoveTaskHandler.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return null;
    }
}
