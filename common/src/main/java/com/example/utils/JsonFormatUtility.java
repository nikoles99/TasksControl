package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.StatusTask;
import com.example.models.Task;

/**
 * Конвертирование Task в Json, Json в Task, List<Task> в JsonArray
 *
 * @author Q-OLN
 */
public class JsonFormatUtility {

    /**
     * Парараметр JSON обьекта, для хранения идентификатора задачи
     */
    private static final String ID = "ID";

    /**
     * Парараметр JSON обьекта, для хранения наименования задачи
     */
    private static final String NAME = "NAME";

    /**
     * Парараметр JSON обьекта, для хранения времени на выполнения задачи
     */
    private static final String WORK_TIME = "WORK_TIME";

    /**
     * Парараметр JSON обьекта, для хранения начальной даты выполнения задачи
     */
    private static final String START_DATE = "START_DATE";

    /**
     * Парараметр JSON обьекта, для хранения конечной даты выполнения задачи
     */
    private static final String FINISH_DATE = "FINISH_DATE";

    /**
     * Парараметр JSON обьекта, для хранения статуса задачи
     */
    private static final String STATUS = "STATUS";

    /**
     * Преобразование Task в Json
     */
    public static JSONObject format(Task task) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ID, String.valueOf(task.getId()));
            jsonObject.put(NAME, task.getName());
            jsonObject.put(WORK_TIME, String.valueOf(task.getWorkTime()));
            jsonObject.put(START_DATE, DateFormatUtility.format(task.getStartDate()));
            jsonObject.put(FINISH_DATE, DateFormatUtility.format(task.getFinishDate()));
            jsonObject.put(STATUS, task.getStatus());
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid Task format %s", task), e);
        }
    }

    /**
     * Преобразование Json в Task
     */
    public static Task format(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString(ID);
            String name = jsonObject.getString(NAME);
            String workTime = jsonObject.getString(WORK_TIME);
            String startDate = jsonObject.getString(START_DATE);
            String finishDate = jsonObject.getString(FINISH_DATE);
            String status = jsonObject.getString(STATUS);
            Task task = new Task(name, Integer.parseInt(workTime), DateFormatUtility.format(startDate),
                    DateFormatUtility.format(finishDate), StatusTask.valueOf(status));
            task.setId(Integer.valueOf(id));
            return (task);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid JSONObject format %s", jsonObject), e);
        }
    }

    /**
     * Преобразование List<Task> в массив Json
     */
    public static JSONArray format(List<Task> taskList) {
        JSONArray jsonArray = new JSONArray();
        for (Task task : taskList) {
            format(task);
            jsonArray.put(format(task));
        }
        return jsonArray;

    }

    /**
     * Преобразование String в List<Task>
     */
    public static List<Task> getListTasks(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<Task> tasks = new ArrayList<Task>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json  = jsonArray.getJSONObject(i);
                Task task = JsonFormatUtility.format(json);
                tasks.add(task);
            }
            return tasks;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

    /**
     * Преобразование String в Task
     */
    public static Task format(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return JsonFormatUtility.format(json);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }
}



