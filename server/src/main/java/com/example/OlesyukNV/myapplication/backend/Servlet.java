package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.example.server.TaskServer;
import com.example.Constants;
import com.example.OlesyukNV.myapplication.backend.commands.AddTaskHandler;
import com.example.OlesyukNV.myapplication.backend.commands.Handler;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksHandler;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskHandler;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskHandler;
import com.example.server.StubServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author Q-OLN
 */
public class Servlet extends HttpServlet {

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
        request.setCharacterEncoding("Cp1251");
        String action = request.getParameter(Constants.ACTION);
        JSONArray jsonArray = SERVLET_COMMAND.get(action).execute(request, taskServer);
        response.setCharacterEncoding("Cp1251");
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }
}

