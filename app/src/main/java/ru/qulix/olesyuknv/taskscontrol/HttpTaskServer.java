package ru.qulix.olesyuknv.taskscontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.Constants;
import com.example.exceptions.HttpConnectionException;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.JsonFormatUtility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import ru.qulix.olesyuknv.taskscontrol.utils.Url;

/**
 * Контроллер, посылающий запросы на сервер;
 *
 * @author Q-OLN
 */
public class HttpTaskServer implements TaskServer, Url {

    private static final int TIMEOUT_MS = 5 * 1000;

    public static final String LOG_TAG = HttpTaskServer.class.getName();

    private Context context;

    private HttpClient httpClient;

    public HttpTaskServer(Context context) {
        this.context = context;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_MS);
        httpClient = new DefaultHttpClient(httpParameters);
    }

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
        HttpPost httpPost = new HttpPost(getUrl(context));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            return result;
        } else {
            Log.e(LOG_TAG, "Error connection with server");
            return null;
        }
    }

    @Override
    public String getUrl(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("URL", "http://192.168.9.117:8080/server/Servlet").trim();
    }

    @Override
    public void update(Task task) throws HttpConnectionException {
        try {
            doRequest(setRequestParams(Constants.UPDATE, task));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
    }

    @Override
    public List<Task> load(int start, int finish) throws HttpConnectionException {
        String result = null;
        try {
            result = doRequest(setRequestParams(Constants.LOAD, start, finish));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
        return JsonFormatUtility.getListTasks(result);
    }

    @Override
    public void add(Task task) throws HttpConnectionException {
        try {
            doRequest(setRequestParams(Constants.ADD, task));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
    }

    @Override
    public void remove(Task task) throws HttpConnectionException {
        try {
            doRequest(setRequestParams(Constants.REMOVE, task));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
    }
}
