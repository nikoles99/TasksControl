package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Описание Задачи
 *
 * @author Q-OLN
 */
public class Task implements Serializable {

    /**
     * Идентификатор
     */
    private int id;

    /**
     * Наименование
     */
    private String name;

    /**
     * Время необходимое на выполнение задачи, в часах
     */
    private int workTime;

    /**
     * Дата начала работы над задачей
     */
    private Date startDate;

    /**
     * Дата окончания работы над задачей
     */
    private Date finishDate;

    /**
     * Статус выполнения задачи
     */
    private StatusTask status;

    /**
     * Сотрудники выполняющие задачу
     */
    private List<Employee> employees = new ArrayList<Employee>();

    /**
     * Имя проекта в котором описана задача
     */
    private int projectId;

    /**
     * Конструктор
     *
     * @param name       имя задачи
     * @param workTime   время работы
     * @param startDate  дата начала выполнения
     * @param finishDate дата окончания выполнения
     * @param status     статус задачи
     * @param projectId  Id проекта в который входит данная задача
     */
    public Task(String name, int workTime, Date startDate, Date finishDate, StatusTask status, int projectId) {
        this.name = name;
        this.workTime = workTime;
        this.finishDate = finishDate;
        this.status = status;
        this.startDate = startDate;
        this.projectId = projectId;
    }

    /**
     * Конструктор
     */
    public Task() {
        this.startDate = new Date();
        this.finishDate = new Date();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public StatusTask getStatus() {
        return status;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getId() {
        return id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Добавить сотрудника к текущей задаче
     *
     * @param employee сотрудник
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Удалить сотрудника с текущей задачи
     *
     * @param employee сотрудник
     * @return true, если сотрудник был удален
     */
    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    /**
     * Добавить сотрудников к задаче
     *
     * @param employees сотрудники
     */
    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        return id == task.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
