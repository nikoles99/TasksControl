package com.example.utils;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.models.Employee;

/**
 * Конвертирование Employee
 *
 * @author Q-OLN
 */
public class EmployeeFormatUtility {

    /**
     * Парараметр JSON обьекта, для хранения идентификатора
     */
    private static final String ID = "ID";

    /**
     * Парараметр JSON обьекта, для хранения имени
     */
    private static final String NAME = "NAME";

    /**
     * Парараметр JSON обьекта, для хранения фамилии
     */
    private static final String SURNAME = "SURNAME";

    /**
     * Парараметр JSON обьекта, для хранения отчества
     */
    private static final String MIDDLE_NAME = "MIDDLE_NAME";

    /**
     * Парараметр JSON обьекта, для хранения должности
     */
    private static final String POST = "POST";

    /**
     * Преобразование Employee в Json
     *
     * @param employee сотрудник для преобразования
     * @return Json
     */
    public static JSONObject format(Employee employee) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ID, String.valueOf(employee.getId()));
            jsonObject.put(NAME, employee.getName());
            jsonObject.put(SURNAME, employee.getSurname());
            jsonObject.put(MIDDLE_NAME, employee.getMiddleName());
            jsonObject.put(POST, employee.getPost());
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid Task format %s", employee), e);
        }
    }

    /**
     * Преобразование Json в Employee
     *
     * @param jsonObject json для преобразования
     * @return сотрудника
     */
    public static Employee getEmployee(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString(ID);
            String name = jsonObject.getString(NAME);
            String surname = jsonObject.getString(SURNAME);
            String middleName = jsonObject.getString(MIDDLE_NAME);
            String post = jsonObject.getString(POST);
            Employee employee = new Employee(name, surname, middleName, post);
            employee.setId(Integer.valueOf(id));
            return (employee);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid JSONObject format %s", jsonObject), e);
        }
    }

    /**
     * Преобразование String в список Employee
     *
     * @param jsonString строка для преобразования
     * @return Список сотрудников
     */
    public static List<Employee> getListEmployees(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<Employee> employees = new ArrayList<Employee>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Employee employee = getEmployee(json);
                employees.add(employee);
            }
            return employees;
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

    /**
     * Преобразование String в Employee
     *
     * @param jsonString строка для преобразования
     * @return сотрудника
     */
    public static Employee getEmployee(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return getEmployee(json);
        } catch (JSONException e) {
            throw new IllegalArgumentException(String.format("Invalid String format %s", jsonString), e);
        }
    }

    /**
     * Преобразование списка Employee в массив Json
     *
     * @param employeeList список задач для преобразования
     * @return Массив Json
     */
    public static JSONArray getListEmployees(List<Employee> employeeList) {
        JSONArray jsonArray = new JSONArray();
        for (Employee employee : employeeList) {
            jsonArray.put(format(employee));
        }
        return jsonArray;
    }
}
