package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

import android.util.Log;

/**
 * Контроллер, посылающий запросы на сервер;
 *
 * @author QULIX-OLESYUKNV
 */
public class HTTPRequests implements TaskServer {

    private static final String UPDATE = HTTPRequests.class + ".UPDATE";
    private static final String REMOVE = HTTPRequests.class + ".REMOVE";
    private static final String LOAD = HTTPRequests.class + ".LOAD";
    private static final String ADD = HTTPRequests.class + ".ADD";

    private void request(String param, Task task) {
        try {
            URL url = new URL("http://192.168.9.117:8080/server/hello");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(JsonFormatUtility.format(new Task()).toString());
            Log.d("inputString", JsonFormatUtility.format(new Task()).toString());
            out.close();
        } catch (Exception e) {
            //    Log.d("Exception", e.toString());
            System.out.print(e.toString());
        }

    }

    private List<Task>  request(String param, int start, int finish) {
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.9.117:8080/server/hello");
        try {
            response = myClient.execute(httpPost);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONArray jArray = new JSONArray(str);
            Set<Task> taskSet = new HashSet<Task>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                Task task = JsonFormatUtility.format(json);
                taskSet.add(task);
            }
            return new ArrayList<Task>(taskSet);
        } catch (IOException e) {
            System.out.print(e.toString());
        }
        catch (JSONException e) {
            System.out.print(e.toString());
        }
        return new ArrayList<Task>();
    }

    @Override
    public void update(Task task) {
        request(UPDATE, task);
    }

    @Override
    public List<Task> load(int start, int finish) {
        return request(LOAD, start, finish);
    }

    @Override
    public void add(Task task) {
        request(ADD, task);
    }

    @Override
    public void remove(Task task) {
        request(REMOVE, task);
    }

}
