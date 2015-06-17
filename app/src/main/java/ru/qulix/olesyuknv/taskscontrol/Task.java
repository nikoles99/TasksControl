package ru.qulix.olesyuknv.taskscontrol;

public class Task {
    private String name;
    private String workTime;
    private String startDate;
    private String finishDate;
    private int status;

    public Task(String name, String workTime, String finishDate, String startDate, int status) {
        this.name = name;
        this.workTime = workTime;
        this.finishDate = finishDate;
        this.status = status;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getWorkTime() {
        return workTime;
    }
}
