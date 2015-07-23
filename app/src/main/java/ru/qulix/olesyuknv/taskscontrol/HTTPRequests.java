package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @author QULIX-OLESYUKNV
 */
public class HTTPRequests implements TaskServer {

    private void request(String param, Task task) {
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Constants.URL);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(Constants.JSON, JsonFormatUtility.format(task)
                    .toString()));
            nameValuePairs.add(new BasicNameValuePair(Constants.ACTION, param));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = myClient.execute(httpPost);

            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            str.toString();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    private List<Task> request(String param, int start, int finish) {
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Constants.URL);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(Constants.START_POSITION,
                    String.valueOf(start)));
            nameValuePairs.add(new BasicNameValuePair(Constants.FINISH_POSITION,
                    String.valueOf(finish)));
            nameValuePairs.add(new BasicNameValuePair(Constants.ACTION, param));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
        }
        catch (IOException e) {
            System.out.print(e.toString());
        }
        catch (JSONException e) {
            System.out.print(e.toString());
        }
        return new ArrayList<Task>();
    }

    @Override
    public void update(Task task) {
        request(Constants.UPDATE, task);
    }

    @Override
    public List<Task> load(int start, int finish) {
        return request(Constants.LOAD, start, finish);
    }

    @Override
    public void add(Task task) {
        request(Constants.ADD, task);
    }

    @Override
    public void remove(Task task) {
        request(Constants.REMOVE, task);
    }

}
