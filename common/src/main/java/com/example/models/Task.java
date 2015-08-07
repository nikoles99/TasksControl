package com.example.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Описание Задачи
 *
 * @author Q-OLN
 */
public class Task implements Serializable {

    /**
     * Идентификатор задачи на сервере
     */
    private int id;

    /**
     * Наименование задачи
     */
    private String name;

    /**
     * Время необходимое на выполнение задачи, в часах
     */
    private int workTime;

    /**
     * Начало работы над задачей
     */
    private Date startDate;

    /**
     * Окончание работы над задачей
     */
    private Date finishDate;

    /**
     * Статус выполнения задачи
     */
    private StatusTask status;

    public Task(String name, int workTime, Date startDate, Date finishDate, StatusTask status) {
        this.name = name;
        this.workTime = workTime;
        this.finishDate = finishDate;
        this.status = status;
        this.startDate = startDate;
    }

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

    public int getId() {
        return id;
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

    /**
     * Проверка задачи на пустоту полей
     */
    public boolean isEmpty() {
        return name == null;
    }
}
