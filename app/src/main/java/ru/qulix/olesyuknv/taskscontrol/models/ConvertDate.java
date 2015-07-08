package ru.qulix.olesyuknv.taskscontrol.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    public Date getDataFromString(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }


    public String getStringFromData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}
