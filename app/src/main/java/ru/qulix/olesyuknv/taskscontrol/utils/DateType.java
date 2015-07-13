package ru.qulix.olesyuknv.taskscontrol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Конвертирование даты по маске "dd.MM.yyyy"
 *
 * @author QULIX-OLESYUKNV
 */
public class DateType {
    /**
     * Маска ввода
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Преобразование Строки в Дату
     */
    public static Date getData(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format ", e);
        }
    }

    /**
     * Преобразование Даты в Строку
     */
    public static String getString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
