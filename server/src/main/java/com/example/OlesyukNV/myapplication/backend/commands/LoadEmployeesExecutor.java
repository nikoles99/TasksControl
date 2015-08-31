package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.models.Employee;
import com.example.server.TaskServer;
import com.example.utils.EmployeeFormatUtility;

/**
 * Команда, загрузки сотрудников
 *
 * @author Q-OLN
 */
public class LoadEmployeesExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);
        List<Employee> employees = taskServer.loadEmployees(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        return EmployeeFormatUtility.getListEmployees(employees).toString();
    }
}