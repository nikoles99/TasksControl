package com.example.OlesyukNV.myapplication.backend.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author QULIX-OLESYUKNV
 */
abstract public class Command {
    protected TaskServer taskServer;

    abstract public JSONArray execute(String json, String start, String finish);

    public Command(TaskServer taskServer) {
        this.taskServer = taskServer;
    }

    protected Task getTask(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return JsonFormatUtility.format(json);
        } catch (JSONException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
