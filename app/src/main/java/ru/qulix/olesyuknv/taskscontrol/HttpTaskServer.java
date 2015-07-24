package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Constants;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;



/**
 * Контроллер, посылающий запросы на сервер;
 *
 * @author Q-OLN
 */
public class HttpTaskServer implements TaskServer {

    private static final String URL = "http://192.168.9.117:8080/server/Servlet";

    private List<NameValuePair> setRequestParams(String param, Task task) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(Constants.JSON, JsonFormatUtility.format(task).toString()));
        nameValuePairs.add(new BasicNameValuePair(Constants.ACTION, param));
        return nameValuePairs;
    }

    private List<NameValuePair> setRequestParams(String param, int start, int finish) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(Constants.START_POSITION, String.valueOf(start)));
        nameValuePairs.add(new BasicNameValuePair(Constants.FINISH_POSITION, String.valueOf(finish)));
        nameValuePairs.add(new BasicNameValuePair(Constants.ACTION, param));
        return nameValuePairs;
    }

    private String doRequest(List<NameValuePair> nameValuePairs) {
        try {
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = myClient.execute(httpPost);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            return str;
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(HttpTaskServer.class.getName()).log(Level.ALL, e.getMessage(), e);
        }
        return null;
    }

    private List<Task> getListTasks(String str) {
        try {
            JSONArray jArray = new JSONArray(str);
            ArrayList<Task> tasks = new ArrayList<Task>();

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                Task task = JsonFormatUtility.format(json);
                tasks.add(task);
            }
            return tasks;

        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", str), e);
        }
    }


    @Override
    public void update(Task task) {
        doRequest(setRequestParams(Constants.UPDATE, task));
    }

    @Override
    public List<Task> load(int start, int finish) {
        String result = doRequest(setRequestParams(Constants.LOAD, start, finish));
        return getListTasks(result);
    }

    @Override
    public void add(Task task) {
        doRequest(setRequestParams(Constants.ADD, task));
    }

    @Override
    public void remove(Task task) {
        doRequest(setRequestParams(Constants.REMOVE, task));
    }

}
