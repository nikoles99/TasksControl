package ru.qulix.olesyuknv.taskscontrol.utils;

/**
 * Постраничная навигация
 *
 * @author Q-OLN
 */
public class PageNavigation {

    /**
     * позиция первого элемента списка
     */
    private int startPosition;

    /**
     * позиция последнего элемента списка
     */
    private int finishPosition;

    /**
     * количество записей на одной странице
     */
    private int pageSize;

    /**
     * переход на следующую страницу
     */
    public void nextPage() {
        startPosition += pageSize;
        finishPosition += pageSize;
    }

    /**
     * переход на предыдущую страницу
     */
    public void previousPage() {
        startPosition -= pageSize;
        finishPosition -= pageSize;
    }

    /**
     * установка размера страницы
     * @param size размер, size > 0
     */
    public void setPageSize(int size) {
        pageSize = size;
        startPosition = 0;
        finishPosition = pageSize;
    }

    /**
     * Получение начальной позиции страницы
     * @return начальную позицию
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Получение конечной позиции страницы
     * @return конечную позицию
     */
    public int getFinishPosition() {
        return finishPosition;
    }

}
