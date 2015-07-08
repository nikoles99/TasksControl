package ru.qulix.olesyuknv.taskscontrol;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Конвертирование даты по маске "dd.MM.yyyy"
 *
 * @author QULIX-OLESYUKNV
 */
public class ConvertDate {

    public Date getDataFromString(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Некоректный формат даты " + getClass().getName(), e);
        }
    }

    public String getStringFromData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}
