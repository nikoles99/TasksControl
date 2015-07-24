package com.example.OlesyukNV.myapplication.backend.commands;

import java.util.List;

import org.json.JSONArray;

import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;


/**
 * Команда, загрузки задач с сервера
 *
 * @author Q-OLN
 */
public class LoadTasksHandler extends Handler {


    public LoadTasksHandler(TaskServer taskServer) {
        super(taskServer);
    }

    @Override
    public JSONArray execute(String json, String startPosition, String finishPosition) {
        List<Task> tasks = taskServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        return JsonFormatUtility.format(tasks);
    }
}
