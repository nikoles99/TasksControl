package com.example.OlesyukNV.myapplication.backend.commands;

import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;
import org.json.JSONArray;

/**
 * �������, �������� ����� � �������
 *
 * @author QULIX-OLESYUKNV
 */
public class LoadTasksCommand extends Command {


    public LoadTasksCommand(TaskServer taskServer) {
        super(taskServer);
    }

    @Override
    public JSONArray execute(String json, String startPosition, String finishPosition) {
        List<Task> tasks = taskServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        return JsonFormatUtility.format(tasks);
    }
}
