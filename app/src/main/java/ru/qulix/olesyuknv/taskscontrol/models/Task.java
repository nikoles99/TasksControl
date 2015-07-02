package ru.qulix.olesyuknv.taskscontrol.models;

import java.io.Serializable;
import java.util.Date;

import ru.qulix.olesyuknv.taskscontrol.StatusTask;

/**
 * Описание Задачи
 *
 * @author OlesyukNV
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
    public boolean equals(Object object) {
        if (object instanceof Task) {
            Task task = (Task) object;
            return task.getId() == this.getId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + startDate.hashCode();
        result = prime * result + finishDate.hashCode();
        result = prime * result + status.hashCode();
        return 0;
    }
}
