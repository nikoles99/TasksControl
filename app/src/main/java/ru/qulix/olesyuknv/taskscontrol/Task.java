package ru.qulix.olesyuknv.taskscontrol;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
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

    private Task(Parcel in) {
        this.name = in.readString();
        this.workTime = in.readString();
        this.startDate = in.readString();
        this.finishDate = in.readString();
        this.status = in.readInt();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(workTime);
        dest.writeString(startDate);
        dest.writeString(finishDate);
        dest.writeInt(status);
    }
}
