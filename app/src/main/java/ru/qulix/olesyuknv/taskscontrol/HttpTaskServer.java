package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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

    private static String url;

    private HttpClient httpClient = new DefaultHttpClient();

    private List<NameValuePair> setRequestParams(String param, Task task) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(Constants.ENTITY, JsonFormatUtility.format(task).toString()));
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

    private String doRequest(List<NameValuePair> nameValuePairs) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        return result;
    }

    public static void setUrl(String url) {
        HttpTaskServer.url = url;
    }

    @Override
    public void update(Task task) throws IOException {
        doRequest(setRequestParams(Constants.UPDATE, task));
    }

    @Override
    public List<Task> load(int start, int finish) throws IOException, JSONException {
        String result = doRequest(setRequestParams(Constants.LOAD, start, finish));
        return JsonFormatUtility.getListTasks(result);
    }

    @Override
    public void add(Task task) throws IOException {
        doRequest(setRequestParams(Constants.ADD, task));
    }

    @Override
    public void remove(Task task) throws IOException {
        doRequest(setRequestParams(Constants.REMOVE, task));
    }

}
