package ru.qulix.olesyuknv.taskscontrol;

import java.util.Date;

/**
 * The task description
 *
 * @author OlesyukNV
 */
public class Task {

    private int id;

    private String name;

    /**
     * The amount of time required to complete this task, in hours.
     */
    private int workTime;

    private Date startDate;

    private Date finishDate;

    private StatusTask status;

    /**
     * Constructor
     *
     * @param name       the name this task
     * @param workTime   the amount of time required to complete this task, in hours
     * @param finishDate finish date this task
     * @param startDate  start date this task
     * @param status     the status this task*
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
        result = prime * result + workTime;
        return result;
    }
}
