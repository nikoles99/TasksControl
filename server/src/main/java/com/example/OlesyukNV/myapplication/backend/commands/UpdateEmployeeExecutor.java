package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.server.TaskServer;
import com.example.utils.EmployeeFormatUtility;

/**
 * Команда, обновления сотрудника
 *
 * @author Q-OLN
 */
public class UpdateEmployeeExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String jsonString = request.getParameter(Constants.ENTITY);
        taskServer.update(EmployeeFormatUtility.getEmployee(jsonString));
        return null;
    }
}