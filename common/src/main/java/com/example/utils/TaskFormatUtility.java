package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.StatusTask;
import com.example.models.Task;

/**
 * Конвертирование Task
 *
 * @author Q-OLN
 */
public class TaskFormatUtility {

    /**
     * Парараметр JSON обьекта, для хранения идентификатора
     */
    private static final String ID = "ID";

    /**
     * Парараметр JSON обьекта, для хранения имени
     */
    private static final String NAME = "NAME";

    /**
     * Парараметр JSON обьекта, для хранения времени на выполнения
     */
    private static final String WORK_TIME = "WORK_TIME";

    /**
     * Парараметр JSON обьекта, для хранения начальной даты выполнения
     */
    private static final String START_DATE = "START_DATE";

    /**
     * Парараметр JSON обьекта, для хранения конечной даты выполнения
     */
    private static final String FINISH_DATE = "FINISH_DATE";

    /**
     * Парараметр JSON обьекта, для хранения проекта, в котором описана задача
     */
    private static final String PROJECT_OF_TASK = "PROJECT_OF_TASK";

    /**
     * Парараметр JSON обьекта, для хранения сотрудников, работающих над задачей
     */
    private static final String EMPLOYEES_OF_TASK = "EMPLOYEES_OF_TASK";

    /**
     * Парараметр JSON обьекта, для хранения статуса задачи
     */
    private static final String STATUS = "STATUS";

    /**
     * Преобразование Task в Json
     *
     * @param task задача для преобразования
     * @return Json
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
            jsonObject.put(PROJECT_OF_TASK, String.valueOf(task.getProjectId()));
            jsonObject.put(EMPLOYEES_OF_TASK, EmployeeFormatUtility.getListEmployees(task.getEmployees()).toString());
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid Task format %s", task), e);
        }
    }

    /**
     * Преобразование Json в Task
     *
     * @param jsonObject json для преобразования
     * @return задачу
     */
    public static Task getTask(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString(ID);
            String name = jsonObject.getString(NAME);
            String workTime = jsonObject.getString(WORK_TIME);
            String startDate = jsonObject.getString(START_DATE);
            String finishDate = jsonObject.getString(FINISH_DATE);
            String status = jsonObject.getString(STATUS);
            String projectId = jsonObject.getString(PROJECT_OF_TASK);
            String listEmployee = jsonObject.getString(EMPLOYEES_OF_TASK);
            Task task = new Task(name, Integer.parseInt(workTime), DateFormatUtility.format(startDate),
                    DateFormatUtility.format(finishDate), StatusTask.valueOf(status), Integer.parseInt(projectId));
            task.setId(Integer.valueOf(id));
            task.addEmployees(EmployeeFormatUtility.getListEmployees(listEmployee));
            return (task);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid JSONObject format %s", jsonObject), e);
        }
    }

    /**
     * Преобразование списка Task в массив Json
     *
     * @param taskList список задач для преобразования
     * @return Массив Json
     */
    public static JSONArray getListTasks(List<Task> taskList) {
        JSONArray jsonArray = new JSONArray();
        for (Task task : taskList) {
            jsonArray.put(format(task));
        }
        return jsonArray;
    }

    /**
     * Преобразование String в список Task
     *
     * @param jsonString строка для преобразования
     * @return Список задач
     */
    public static List<Task> getListTasks(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<Task> tasks = new ArrayList<Task>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Task task = TaskFormatUtility.getTask(json);
                tasks.add(task);
            }
            return tasks;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

    /**
     * Преобразование String в Task
     *
     * @param jsonString строка для преобразования
     * @return задачу
     */
    public static Task getTask(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return TaskFormatUtility.getTask(json);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

}



