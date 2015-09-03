package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Constants;
import com.example.OlesyukNV.myapplication.backend.commands.AddEmployeeExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.AddProjectExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.AddTaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.LoadEmployeesExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.LoadProjectsExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveEmployeeExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveProjectExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.TasksControlExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.LoadTasksExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.RemoveTaskExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateEmployeeExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateProjectExecutor;
import com.example.OlesyukNV.myapplication.backend.commands.UpdateTaskExecutor;
import com.example.server.StubServer;
import com.example.server.TaskServer;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author Q-OLN
 */
public class TasksControlServlet extends HttpServlet {

    private TaskServer taskServer = new StubServer();

    /**
     * Действия, выполняемые на сервере
     */
    private static final Map<String, TasksControlExecutor> SERVLET_COMMAND = new HashMap<String, TasksControlExecutor>() {
        {
            put(Constants.ADD_TASK, new AddTaskExecutor());
            put(Constants.REMOVE_TASK, new RemoveTaskExecutor());
            put(Constants.UPDATE_TASK, new UpdateTaskExecutor());
            put(Constants.LOAD_TASKS, new LoadTasksExecutor());

            put(Constants.ADD_PROJECT, new AddProjectExecutor());
            put(Constants.REMOVE_PROJECT, new RemoveProjectExecutor());
            put(Constants.UPDATE_PROJECT, new UpdateProjectExecutor());
            put(Constants.LOAD_PROJECTS, new LoadProjectsExecutor());

            put(Constants.ADD_EMPLOYEE, new AddEmployeeExecutor());
            put(Constants.REMOVE_EMPLOYEE, new RemoveEmployeeExecutor());
            put(Constants.UPDATE_EMPLOYEE, new UpdateEmployeeExecutor());
            put(Constants.LOAD_EMPLOYEES, new LoadEmployeesExecutor());
        }
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(Constants.ACTION);
        String result = SERVLET_COMMAND.get(action).execute(request, taskServer);
        response.getOutputStream().write(result.getBytes("UTF-8"));
    }
}