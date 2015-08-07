package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Constants;
import com.example.OlesyukNV.myapplication.backend.commands.AddTaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.TaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskExecutor;
import com.example.server.StubServer;
import com.example.server.TaskServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author Q-OLN
 */
public class TaskServlet extends HttpServlet {

    private TaskServer taskServer = new StubServer();

    /**
     * Действия, выполняемые на сервере
     */
    private static final Map<String, TaskExecutor> SERVLET_COMMAND = new HashMap<String, TaskExecutor>() {
        {
            put(Constants.ADD, new AddTaskExecutor());
            put(Constants.REMOVE, new RemoveTaskExecutor());
            put(Constants.UPDATE, new UpdateTaskExecutor());
            put(Constants.LOAD, new LoadTasksExecutor());
        }
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(Constants.ACTION);
        String tasks = SERVLET_COMMAND.get(action).execute(request, taskServer);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(tasks.getBytes());
    }
}

