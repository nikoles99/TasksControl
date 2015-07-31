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
import com.example.OlesyukNV.myapplication.backend.commands.Executor;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskExecutor;
import com.example.exceptions.HttpConnectionException;
import com.example.server.StubServer;
import com.example.server.TaskServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author Q-OLN
 */
public class TaskServlet extends HttpServlet {

    private TaskServer taskServer = new StubServer();

    private static final Map<String, Executor> SERVLET_COMMAND = new HashMap<String, Executor>() {
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
        String tasks = null;
        try {
            tasks = SERVLET_COMMAND.get(action).execute(request, taskServer);
        } catch (HttpConnectionException e) {
            throw new RuntimeException("Error with server connection");
        }
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(tasks.getBytes());
    }
}

