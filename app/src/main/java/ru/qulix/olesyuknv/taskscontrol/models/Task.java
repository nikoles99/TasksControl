package ru.qulix.olesyuknv.taskscontrol.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Описание Задачи
 *
 * @author QULIX-OLESYUKNV
 */
public class Task implements Serializable {

    private int id;

    private String name;

    /**
     * Время необходимое на выполнение задачи, в часах
     */
    private int workTime;

    private Date startDate;

    private Date finishDate;

    private StatusTask status;

    /**
     * Конструктор
     *
     * @param name       Наименование задачи
     * @param workTime   Время необходимое на выполнение задачи, в часах
     * @param finishDate Дата завершения выполнения задачи
     * @param startDate  Дата начала выполнения задачи
     * @param status     Статус задачи
     */
    public Task(String name, int workTime, Date startDate, Date finishDate, StatusTask status) {
        this.name = name;
        this.workTime = workTime;
        this.finishDate = finishDate;
        this.status = status;
        this.startDate = startDate;
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

    public void setId(int id) {
        this.id = id;
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
}
