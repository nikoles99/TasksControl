package com.example.utils;

import com.example.models.Task;

/**
 * Проверка Task
 *
 * @author Q-OLN
 */
public class TaskValidator {

    private static final String EXCEPTION = "exception";

    /**
     * Пробрасывание исключения, если имя "exception"
     *
     * @param task задача для проверки
     */
    public static void checkName(Task task) {

        if (EXCEPTION.equals(task.getName())) {
            throw new IllegalStateException("Invalid Task name");
        }
    }
}