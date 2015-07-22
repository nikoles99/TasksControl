package com.example.utils;

import java.util.HashSet;
import java.util.Set;

import com.example.models.StatusTask;
import com.example.models.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Конвертирование Task в Json и наоборот
 *
 * @author QULIX-OLESYUKNV
 */
public  class JsonFormatUtility {
    public static final String ID = JsonFormatUtility.class + ".ID";
    public static final String NAME = JsonFormatUtility.class + ".NAME";
    public static final String WORK_TIME = JsonFormatUtility.class + ".WORK_TIME";
    public static final String START_DATE = JsonFormatUtility.class + ".START_DATE";
    public static final String FINISH_DATE = JsonFormatUtility.class + ".FINISH_DATE";
    public static final String STATUS = JsonFormatUtility.class + ".STATUS";

    public static JSONObject format(Task task) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ID, task.getId());
            jsonObject.put(NAME, task.getName());
            jsonObject.put(WORK_TIME, task.getWorkTime());
            jsonObject.put(START_DATE, DateFormatUtility.format(task.getStartDate()));
            jsonObject.put(FINISH_DATE, DateFormatUtility.format(task.getFinishDate()));
            jsonObject.put(STATUS, task.getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

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
            e.printStackTrace();
        }
        return null;

    }

    public static JSONArray format(Set<Task> tasksSet) {
        JSONArray jsonArray = new JSONArray();
        for(Task index: tasksSet){
            format(index);
            jsonArray.put(format(index));
        }

       /* for (Task index : tasksSet) {

        }*/
        return jsonArray;

    }

    public static Set<Task> format(JSONArray jArray) {
        Set<Task> tasksSet = new HashSet<Task>();
        return  tasksSet;
    }
}



