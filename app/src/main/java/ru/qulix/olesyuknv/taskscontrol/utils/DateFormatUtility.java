package ru.qulix.olesyuknv.taskscontrol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Конвертирование даты по маске "dd.MM.yyyy"
 *
 * @author QULIX-OLESYUKNV
 */
public class DateFormatUtility {
    /**
     * Маска ввода
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    /**
     * Преобразование Строки в Дату
     */
    public static Date format(String date) {
        try {
            LOCK.writeLock().lock();
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("Invalid date format %s expected 'dd.MM.yyyy'", date), e);
        }
        finally {
            LOCK.writeLock().unlock();
        }
    }

    /**
     * Преобразование Даты в Строку
     */
    public static String format(Date date) {
        try {
            LOCK.writeLock().lock();
            return DATE_FORMAT.format(date);
        } finally {
            LOCK.writeLock().unlock();
        }

    }
}
