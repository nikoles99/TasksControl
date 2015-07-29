package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Constants;
import com.example.OlesyukNV.myapplication.backend.commands.AddTaskHandler;
import com.example.OlesyukNV.myapplication.backend.commands.Handler;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksHandler;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskHandler;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskHandler;
import com.example.server.StubServer;
import com.example.server.TaskServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author Q-OLN
 */
public class TaskServlet extends HttpServlet {

    private TaskServer taskServer = new StubServer();

    private static final Map<String, Handler> SERVLET_COMMAND = new HashMap<String, Handler>() {
        {
            put(Constants.ADD, new AddTaskHandler());
            put(Constants.REMOVE, new RemoveTaskHandler());
            put(Constants.UPDATE, new UpdateTaskHandler());
            put(Constants.LOAD, new LoadTasksHandler());
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

