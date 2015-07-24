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
import com.example.OlesyukNV.myapplication.backend.commands.AddTaskCommand;
import com.example.OlesyukNV.myapplication.backend.commands.Command;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksCommand;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskCommand;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskCommand;
import com.example.server.StubServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author QULIX-OLESYUKNV
 */
public class Servlet extends HttpServlet {

    private static StubServer stubServer = new StubServer();

    private static final String SERVLET_MESSAGE = "Servlet is working";

    private static final Map<String, Command> SERVLET_COMMAND = new HashMap<String, Command>() {
        {
            put(Constants.ADD, new AddTaskCommand(stubServer));
            put(Constants.REMOVE, new RemoveTaskCommand(stubServer));
            put(Constants.UPDATE, new UpdateTaskCommand(stubServer));
            put(Constants.LOAD, new LoadTasksCommand(stubServer));
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

