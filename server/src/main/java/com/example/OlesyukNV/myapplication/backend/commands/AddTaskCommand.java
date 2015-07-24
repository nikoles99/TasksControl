package com.example.OlesyukNV.myapplication.backend.commands;

import org.json.JSONArray;

import com.example.server.StubServer;


/**
 * Команда, добавления задачи на сервер
 *
 * @author QULIX-OLESYUKNV
 */
public class AddTaskCommand extends Command {

    public AddTaskCommand(StubServer servlet) {
        super(servlet);
    }

    @Override
    public JSONArray execute(String json, String start, String finish) {
        taskServer.add(getTask(json));
        return null;
    }
}
