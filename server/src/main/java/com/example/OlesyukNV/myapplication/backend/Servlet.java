package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.server.StubServer;
import com.example.utils.DateFormatUtility;
import com.example.utils.JsonFormatUtility;
import org.json.JSONArray;

public class Servlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StubServer stubServer = new StubServer();
        Set<Task> taskSet = StubServer.getTasksSet();
        try {
            JSONArray jsonObject;
            Task task = new Task("task15", 4, DateFormatUtility.format("20.12.2009"), DateFormatUtility.format("10.02.2015"), StatusTask.COMPLETED);
            jsonObject= JsonFormatUtility.format(taskSet);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
        } catch (IOException ioe) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StubServer stubServer = new StubServer();
        Set<Task> taskSet = StubServer.getTasksSet();
        try {
            JSONArray jsonObject;
            Task task = new Task("task15", 4, DateFormatUtility.format("20.12.2009"), DateFormatUtility.format("10.02.2015"), StatusTask.COMPLETED);
            jsonObject= JsonFormatUtility.format(taskSet);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
        } catch (IOException ioe) {
        }

    }
}

