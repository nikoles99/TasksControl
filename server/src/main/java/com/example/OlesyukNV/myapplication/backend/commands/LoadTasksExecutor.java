package com.example.OlesyukNV.myapplication.backend.commands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.InterruptServerException;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;


/**
 * Команда, загрузки задач с сервера
 *
 * @author Q-OLN
 */
public class LoadTasksExecutor extends TaskExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws ServletException {
        return super.execute(request, taskServer);
    }

    @Override
    protected String processing(HttpServletRequest request, TaskServer taskServer) throws InterruptServerException {
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);
        List<Task> tasks = taskServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        return JsonFormatUtility.format(tasks).toString();
    }
}