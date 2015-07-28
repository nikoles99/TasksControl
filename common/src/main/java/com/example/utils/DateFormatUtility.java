package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Конвертирование даты по маске "dd.MM.yyyy"
 *
 * @author Q-OLN
 */
public class DateFormatUtility {

    private static final String MASK = "dd.MM.yyyy";

    /**
     * Преобразование Строки в Дату
     */
    public static Date format(String date) {
        try {
            return getSimpleDateFormat().parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("Invalid date format %s expected '" + MASK + "'", date), e);
        }
    }

    /**
     * Преобразование Даты в Строку
     */
    public static String format(Date date) {
        return getSimpleDateFormat().format(date);
    }

    /**
     * @return маску ввода
     */
    private static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat(MASK);
    }
}
