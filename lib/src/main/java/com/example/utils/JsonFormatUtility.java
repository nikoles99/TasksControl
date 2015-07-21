package com.example.utils;

import com.example.models.StatusTask;
import com.example.models.Task;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Конвертирование Task в Json и наоборот
 *
 * @author QULIX-OLESYUKNV
 */
public class JsonFormatUtility {
    private static final String ID = JsonFormatUtility.class + ".ID";
    private static final String NAME = JsonFormatUtility.class + ".NAME";
    private static final String WORK_TIME = JsonFormatUtility.class + ".WORK_TIME";
    private static final String START_DATE = JsonFormatUtility.class + ".START_DATE";
    private static final String FINISH_DATE = JsonFormatUtility.class + ".FINISH_DATE";
    private static final String STATUS = JsonFormatUtility.class + ".STATUS";

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

    public static String format(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString(ID);
            String name = jsonObject.getString(NAME);
            String workTime = jsonObject.getString(WORK_TIME);
            String startDate = jsonObject.getString(START_DATE);
            String finishDate = jsonObject.getString(FINISH_DATE);
            String status = jsonObject.getString(STATUS);
            return (new Task(name, Integer.parseInt(workTime), DateFormatUtility.format(startDate),
                    DateFormatUtility.format(finishDate), StatusTask.valueOf(status))).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}



