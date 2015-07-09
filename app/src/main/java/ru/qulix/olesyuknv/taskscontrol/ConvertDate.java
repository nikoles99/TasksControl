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
    /**
     * Маска ввода
     */
    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Преобразование Строки в Дату
     */
    public static Date getData(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format ", e);
        }
    }

    /**
     * Преобразование Даты в Строку
     */
    public static String getString(Date date) {
        return dateFormat.format(date);
    }
}
