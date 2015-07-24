package com.example.OlesyukNV.myapplication.backend.commands;

import org.json.JSONArray;

import com.example.server.StubServer;


/**
 * Команда, добавления задачи на сервер
 *
 * @author Q-OLN
 */
public class AddTaskHandler extends Handler {

    public AddTaskHandler(StubServer servlet) {
        super(servlet);
    }

    @Override
    public JSONArray execute(String json, String start, String finish) {
        taskServer.add(getTask(json));
        return null;
    }
}
