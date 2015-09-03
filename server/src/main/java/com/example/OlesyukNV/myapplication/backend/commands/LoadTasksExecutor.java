package com.example.OlesyukNV.myapplication.backend.commands;

import java.util.List;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.TaskFormatUtility;


/**
 * Команда, загрузки задач
 *
 * @author Q-OLN
 */
public class LoadTasksExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);
        List<Task> tasks = taskServer.loadTasks(Integer.parseInt(startPosition), Integer.parseInt(finishPosition));
        return TaskFormatUtility.getListTasks(tasks).toString();
    }
}