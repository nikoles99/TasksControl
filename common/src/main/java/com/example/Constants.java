package com.example;

/**
 * Общие константы для Sever и App модулей
 *
 * @author Q-OLN
 */
public class Constants {

    /**
     * Парметр HTTP запроса - задача, передаваемая на сервер
     */
    public static final String ENTITY = "JSON";

    /**
     * Парметр HTTP запроса -  выбора действия на сервере
     */
    public static final String ACTION = "ACTION";

    /**
     * Парметр HTTP запроса -  обновление задачи
     */
    public static final String UPDATE = ".UPDATE";

    /**
     * Парметр HTTP запроса -  удаление задачи
     */
    public static final String REMOVE = "REMOVE";

    /**
     * Парметр HTTP запроса - загрузка задач
     */
    public static final String LOAD = "LOAD";

    /**
     * Парметр HTTP запроса - добавление задачи
     */
    public static final String ADD = "ADD";

    /**
     *  Начальная позиция загрузки задач
     */
    public static final String START_POSITION = "START_POSITION";

    /**
     * Конечная позиция загрузки задач
     */
    public static final String FINISH_POSITION = "FINISH_POSITION";
}
