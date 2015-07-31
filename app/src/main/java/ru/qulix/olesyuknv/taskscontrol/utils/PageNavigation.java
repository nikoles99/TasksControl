package ru.qulix.olesyuknv.taskscontrol.utils;

/**
 * Постраничная навигация
 *
 * @author Q-OLN
 */
public interface PageNavigation {

    /**
     * переход на следующую страницу
     */
    void nextPage();

    /**
     * переход на предыдущую страницу
     */
    void previousPage();

    /**
     * установка размера страницы
     * @param size
     */
    void setPageSize(int size);

    /**
     * Получение начальной позиции страницы
     * @return
     */
    int getStartPosition();

    /**
     * Получение конечной позиции страницы
     * @return
     */
    int getFinishPosition();

}
