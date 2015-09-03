package ru.qulix.olesyuknv.taskscontrol.server;

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

import com.example.models.Employee;
import com.example.models.Project;
import com.example.utils.EmployeeFormatUtility;
import com.example.utils.ProjectFormatUtility;
import com.example.utils.TaskValidator;
import com.example.Constants;
import com.example.models.Task;
import com.example.server.TaskServer;
import com.example.utils.TaskFormatUtility;

/**
 * Сервер, посылающий http запросы;
 *
 * @author Q-OLN
 */
public class HttpTaskServer implements TaskServer {

    private static final int TIMEOUT_MS = 5 * 1000;

    private String Url;

    private HttpClient httpClient;

    public HttpTaskServer() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_MS);
        httpClient = new DefaultHttpClient(httpParameters);
    }

    private List<NameValuePair> setRequestParams(String param, String entity) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(Constants.ENTITY, entity));
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
        HttpPost httpPost = new HttpPost(Url);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        int httpStatus = response.getStatusLine().getStatusCode();

        if (httpStatus == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } else {
            throw new IOException(String.format("Http status unsuccessful %d expected '" +
                    HttpStatus.SC_OK + "'", httpStatus));
        }
    }

    /**
     * Установка URL
     *
     * @param Url
     */
    public void setUrl(String Url) {
        this.Url = Url;
    }

    @Override
    public void update(Task task) throws IOException {
        TaskValidator.checkName(task);
        doRequest(setRequestParams(Constants.UPDATE_TASK, TaskFormatUtility.format(task).toString()));
    }

    @Override
    public List<Task> loadTasks(int start, int finish) throws IOException {
        String result = doRequest(setRequestParams(Constants.LOAD_TASKS, start, finish));
        return TaskFormatUtility.getListTasks(result);
    }

    @Override
    public void add(Task task) throws IOException {
        TaskValidator.checkName(task);
        doRequest(setRequestParams(Constants.ADD_TASK, TaskFormatUtility.format(task).toString()));
    }

    @Override
    public void remove(Task task) throws IOException {
        doRequest(setRequestParams(Constants.REMOVE_TASK, TaskFormatUtility.format(task).toString()));
    }

    @Override
    public void update(Project project) throws IOException {
        doRequest(setRequestParams(Constants.UPDATE_PROJECT, ProjectFormatUtility.format(project).toString()));
    }

    @Override
    public List<Project> loadProjects(int start, int finish) throws IOException {
        String result = doRequest(setRequestParams(Constants.LOAD_PROJECTS, start, finish));
        return ProjectFormatUtility.getListProjects(result);
    }

    @Override
    public void add(Project project) throws IOException {
        doRequest(setRequestParams(Constants.ADD_PROJECT, ProjectFormatUtility.format(project).toString()));
    }

    @Override
    public void remove(Project project) throws IOException {
        doRequest(setRequestParams(Constants.REMOVE_PROJECT, ProjectFormatUtility.format(project).toString()));
    }

    @Override
    public void update(Employee employee) throws IOException {
        doRequest(setRequestParams(Constants.UPDATE_EMPLOYEE, EmployeeFormatUtility.format(employee).toString()));
    }

    @Override
    public List<Employee> loadEmployees(int start, int finish) throws IOException {
        String result = doRequest(setRequestParams(Constants.LOAD_EMPLOYEES, start, finish));
        return EmployeeFormatUtility.getListEmployees(result);
    }

    @Override
    public void add(Employee employee) throws IOException {
        doRequest(setRequestParams(Constants.ADD_EMPLOYEE, EmployeeFormatUtility.format(employee).toString()));
    }

    @Override
    public void remove(Employee employee) throws IOException {
        doRequest(setRequestParams(Constants.REMOVE_EMPLOYEE, EmployeeFormatUtility.format(employee).toString()));
    }
}
