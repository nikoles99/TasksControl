package com.example.OlesyukNV.myapplication.backend.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.Constants;
import com.example.models.Project;
import com.example.server.TaskServer;
import com.example.utils.ProjectFormatUtility;

/**
 * Команда, загрузки проектов
 *
 * @author Q-OLN
 */
public class LoadProjectsExecutor extends TasksControlExecutor {

    @Override
    public String execute(HttpServletRequest request, TaskServer taskServer) throws IOException {
        String startPosition = request.getParameter(Constants.START_POSITION);
        String finishPosition = request.getParameter(Constants.FINISH_POSITION);
        List<Project> projects = taskServer.loadProjects(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
        return ProjectFormatUtility.getListProjects(projects).toString();
    }
}
