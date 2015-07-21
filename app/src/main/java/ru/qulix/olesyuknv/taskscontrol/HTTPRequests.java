package ru.qulix.olesyuknv.taskscontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * ����������, ���������� ������� �� ������
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
            URL url = new URL("http://192.168.8.10:8080/hello");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(JsonFormatUtility.format(new Task()).toString());
            Log.d("inputString", JsonFormatUtility.format(new Task()).toString());
            out.close();
        } catch (Exception e) {
        //    Log.d("Exception", e.toString());
        }

    }

    private void request(String param) {
        JSONObject json = null;
        String str = "";
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.9.117:8080/hello");

        try {
            response = myClient.execute(httpPost);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONArray jArray = new JSONArray(str);
            json = jArray.getJSONObject(0);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
        //    e.printStackTrace();
        } catch (JSONException e) {
         //   e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        request(UPDATE, task);
    }

    @Override
    public List<Task> load(int i, int i1) {
        request(LOAD);
        return null;
    }

    @Override
    public void add(Task task) {
        request(ADD, task);
    }

    @Override
    public void remove(Task task) {
        request(REMOVE, task);
    }

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
          //  e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
              //  e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
