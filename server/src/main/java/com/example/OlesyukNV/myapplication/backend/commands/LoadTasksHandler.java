package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.Constants;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;


/**
 * Команда, загрузки задач с сервера
 *
 * @author Q-OLN
 */
public class LoadTasksHandler extends Handler {

    @Override
    public JSONArray execute(HttpServletRequest request, TaskServer taskServer) {
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);
        List<Task> tasks = null;
        try {
            tasks = taskServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        }
        catch (IOException e) {
            Logger.getLogger(LoadTasksHandler.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        catch (JSONException e) {
            Logger.getLogger(LoadTasksHandler.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return JsonFormatUtility.format(tasks);
    }
}
