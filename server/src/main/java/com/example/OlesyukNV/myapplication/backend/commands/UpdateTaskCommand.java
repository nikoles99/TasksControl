package com.example.OlesyukNV.myapplication.backend.commands;

import com.example.server.TaskServer;
import org.json.JSONArray;

/**
 *  оманда, обновлени€ задачи на сервере
 *
 * @author QULIX-OLESYUKNV
 */
public class UpdateTaskCommand extends Command {

    public UpdateTaskCommand(TaskServer taskServer) {
        super(taskServer);
    }

    @Override
    public JSONArray execute(String json, String start, String finish) {
        taskServer.update(getTask(json));
        return null;
    }
}
