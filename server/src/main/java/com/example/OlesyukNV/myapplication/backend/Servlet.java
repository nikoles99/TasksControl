package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.models.Task;
import com.example.server.StubServer;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Servlet extends HttpServlet {

    TaskServer stubServer = new StubServer();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Servlet is work!!!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String st = request.getParameter("action");

        if(st.equals("add")){
            stubServer.add(getTask(request));
            PrintWriter out = response.getWriter();
            out.print(stubServer);
            out.flush();
        }
        else if(st.equals("remove")){
            stubServer.remove(getTask(request));
            PrintWriter out = response.getWriter();
            out.print(stubServer);
            out.flush();
        }
        else if(st.equals("update")){
            stubServer.update(getTask(request));
            PrintWriter out = response.getWriter();
            out.print(stubServer);
            out.flush();
        }
        else if(st.equals("load")){
            String startPosition = request.getParameter("start");
            String finishPosition = request.getParameter("finish");
            List<Task> tasks= stubServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
            JSONArray jsonObject = JsonFormatUtility.format(tasks);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
        }

    }

    private Task getTask(HttpServletRequest request){
        try {
            String jsonString = request.getParameter("json");
            JSONObject json = new JSONObject(jsonString);
            return JsonFormatUtility.format(json);
        } catch (JSONException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

