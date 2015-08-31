package com.example.models;

/**
 * Статусы задач.
 *
 * @author Q-OLN
 */
public enum StatusTask {

    /**
     * Выполнение задачи не начато
     */
    NOT_STARTED,

    /**
     * Задача в процессе выполнения
     */
    IN_PROCESS,

    /**
     * Задача выполнена
     */
    COMPLETED,

    /**
     * Выполнение задачи приостоновлено
     */
    POSTPONED;
}
