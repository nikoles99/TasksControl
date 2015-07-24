package com.example.OlesyukNV.myapplication.backend.commands;

import org.json.JSONArray;

import com.example.server.TaskServer;


/**
 * Команда, обновления задачи на сервере
 *
 * @author Q-OLN
 */
public class UpdateTaskHandler extends Handler {

    public UpdateTaskHandler(TaskServer taskServer) {
        super(taskServer);
    }

    @Override
    public JSONArray execute(String json, String start, String finish) {
        taskServer.update(getTask(json));
        return null;
    }
}
