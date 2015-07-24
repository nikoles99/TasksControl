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

    private static StubServer stubServer = new StubServer();

    private static final String SERVLET_MESSAGE = "Servlet is working хорошо";

    private static final Map<String, Handler> SERVLET_COMMAND = new HashMap<String, Handler>() {
        {
            put(Constants.ADD, new AddTaskHandler(stubServer));
            put(Constants.REMOVE, new RemoveTaskHandler(stubServer));
            put(Constants.UPDATE, new UpdateTaskHandler(stubServer));
            put(Constants.LOAD, new LoadTasksHandler(stubServer));
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print(SERVLET_MESSAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter(Constants.ACTION);
        String jsonString = request.getParameter(Constants.JSON);
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);

        JSONArray jsonArray = SERVLET_COMMAND.get(action).execute(jsonString, startPosition, finishPosition);
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }
}

