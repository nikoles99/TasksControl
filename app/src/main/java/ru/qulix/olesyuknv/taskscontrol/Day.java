package ru.qulix.olesyuknv.taskscontrol;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Day {
    private final Calendar calendar;

    /**
     * get current year
     */
    private int year;

    /**
     * get current month
     */
    private int month;

    /**
     * get current day
     */
    private int day;

    /**
     * Dialog input date.
     */
    private DatePickerDialog.OnDateSetListener timeDialog;

    public Day() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DatePickerDialog.OnDateSetListener getTimeDialog() {
        return timeDialog;
    }

    public int getYear() {
        return year;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setTimeDialog(DatePickerDialog.OnDateSetListener timeDialog) {
        this.timeDialog = timeDialog;
    }

    public Date getDataFromString(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.parse(date);
    }

    public String getStringFromData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

}
