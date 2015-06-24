package ru.qulix.olesyuknv.taskscontrol;

import java.util.Date;

/**
 *
 * The task description
 * @author OlesyukNV
 */
public class Task {
    /**
     * The name this task.
     */
    private String name;

    /**
     * The amount of time required to complete this task, in hours.
     */
    private String workTime;

    /**
     * Start date this task.
     */
    private Date startDate;

    /**
     * Finish date this task.
     */
    private Date finishDate;

    /**
     * The status this task.
     */
    private StatusTask status;

    /**
     * Constructor
     * @param name the name this task
     * @param workTime the amount of time required to complete this task, in hours
     * @param finishDate finish date this task
     * @param startDate start date this task
     * @param status the status this task*
     */
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
