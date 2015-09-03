package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.Project;

/**
 * Конвертирование  Project
 *
 * @author Q-OLN
 */
public class ProjectFormatUtility {

    /**
     * Парараметр JSON обьекта, для хранения идентификатора
     */
    private static final String ID = "ID";

    /**
     * Парараметр JSON обьекта, для хранения имени
     */
    private static final String NAME = "NAME";

    /**
     * Парараметр JSON обьекта, для хранения сокрощенного названия
     */
    private static final String SUB_NAME = "SUB_NAME";

    /**
     * Парараметр JSON обьекта, для хранения описания
     */
    private static final String DESCRIPTION = "DESCRIPTION";

    /**
     * Парараметр JSON обьекта, для хранения задач проекта
     */
    private static final String TASKS_OF_PROJECT = "TASKS_OF_PROJECT";


    /**
     * Преобразование Project в Json
     *
     * @param project задача для преобразования
     * @return Json
     */
    public static JSONObject format(Project project) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ID, String.valueOf(project.getId()));
            jsonObject.put(NAME, project.getName());
            jsonObject.put(SUB_NAME, project.getSubName());
            jsonObject.put(DESCRIPTION, project.getDescription());
            jsonObject.put(TASKS_OF_PROJECT, TaskFormatUtility.getListTasks(project.getTasks()).toString());
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid Task format %s", project), e);
        }
    }

    /**
     * Преобразование Json в Project
     *
     * @param jsonObject json для преобразования
     * @return проект
     */
    public static Project getProject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString(ID);
            String name = jsonObject.getString(NAME);
            String subName = jsonObject.getString(SUB_NAME);
            String description = jsonObject.getString(DESCRIPTION);
            String listTasks = jsonObject.getString(TASKS_OF_PROJECT);
            Project project = new Project(name, subName, description);
            project.setId(Integer.valueOf(id));
            project.addTasks(TaskFormatUtility.getListTasks(listTasks));
            return project;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid JSONObject format %s", jsonObject), e);
        }
    }

    /**
     * Преобразование списка Project в массив Json
     *
     * @param projectList проектов для преобразования
     * @return Массив Json
     */
    public static JSONArray getListProjects(List<Project> projectList) {
        JSONArray jsonArray = new JSONArray();
        for (Project project : projectList) {
            jsonArray.put(format(project));
        }
        return jsonArray;
    }

    /**
     * Преобразование String в список Project
     *
     * @param jsonString строка для преобразования
     * @return Список проектов
     */
    public static List<Project> getListProjects(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<Project> projects = new ArrayList<Project>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Project project = getProject(json);
                projects.add(project);
            }
            return projects;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

    /**
     * Преобразование String в Project
     *
     * @param jsonString строка для преобразования
     * @return проект
     */
    public static Project getProject(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return getProject(json);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }
}
