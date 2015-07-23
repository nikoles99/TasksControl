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

import com.example.Constants;
import com.example.models.Task;
import com.example.server.StubServer;
import com.example.utils.JsonFormatUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet, обрабатывающий POST запросы.
 *
 * @author QULIX-OLESYUKNV
 */
public class Servlet extends HttpServlet {

    private static final StubServer stubServer = new StubServer();

    private static final String SERVLET_MESSAGE = "Servlet is Work";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.getWriter().print(SERVLET_MESSAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String st = request.getParameter(Constants.ACTION);
        String jsonString = request.getParameter(Constants.JSON);

        if(st.equals(Constants.ADD)){
            stubServer.add(getTask(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            out.flush();
        }
        else if(st.equals(Constants.REMOVE)){
            stubServer.remove(getTask(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            out.flush();
        }
        else if(st.equals(Constants.UPDATE)){
            stubServer.update(getTask(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            out.flush();
        }
        else if(st.equals(Constants.LOAD)){
            String startPosition = request.getParameter(Constants.START_POSITION);
            String finishPosition = request.getParameter(Constants.FINISH_POSITION);
            List<Task> tasks= stubServer.load(Integer.valueOf(startPosition), Integer.valueOf(finishPosition));
            JSONArray jsonObject = JsonFormatUtility.format(tasks);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
        }

    }

    private Task getTask(String jsonString){
        try {
            JSONObject json = new JSONObject(jsonString);
            return JsonFormatUtility.format(json);
        } catch (JSONException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

