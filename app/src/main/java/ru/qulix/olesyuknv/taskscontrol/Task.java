package ru.qulix.olesyuknv.taskscontrol;

import java.util.Date;

public class Task {

    private String name;
    private String workTime;
    private Date startDate;
    private Date finishDate;
    private StatusTask status;

    public Task(String name, String workTime, Date finishDate, Date startDate, StatusTask status) {
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

    public String getWorkTime() {
        return workTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }
}
