package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Описание проекта
 *
 * @author Q-OLN
 */
public class Project implements Serializable {

    /**
     * Идентификатор
     */
    private int id;

    /**
     * Название
     */
    private String name;

    /**
     * Сокращенное имя
     */
    private String subName;

    /**
     * Описание
     */
    private String description;

    /**
     * Список задач принадлежащих проекту
     */
    private List<Task> tasks = new ArrayList<Task>();

    /**
     * Конструктор
     *
     * @param name        Название
     * @param subName     Сокращенное имя
     * @param description Описание
     */
    public Project(String name, String subName, String description) {
        this.name = name;
        this.subName = subName;
        this.description = description;
    }

    /**
     * Конструктор
     */
    public Project() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubName() {
        return subName;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Добавить задачи к проекту
     *
     * @param tasks задачи
     */
    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Добавить задачу к проекту
     *
     * @param task задача
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Удалить задачу с проекта
     *
     * @param task задача
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        return id == project.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
