package com.example.OlesyukNV.myapplication.backend.commands;

import com.example.server.TaskServer;
import org.json.JSONArray;

/**
 * Команда, удаления задачи с сервера
 *
 * @author QULIX-OLESYUKNV
 */
public class RemoveTaskCommand extends Command {

    public RemoveTaskCommand(TaskServer taskServer) {
        super(taskServer);
    }

    @Override
    public JSONArray execute(String json, String start, String finish) {
        taskServer.remove(getTask(json));
        return null;
    }
}
