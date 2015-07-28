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
    private static int startPosition;

    /**
     * позиция последнего элемента списка
     */
    private static int finishPosition;

    /**
     * количество записей на одной странице
     */
    private static int pageSize;


    public static void setPageSize(int size) {
        pageSize = size;
        startPosition = 0;
        finishPosition = pageSize;
    }

    public void nextPage() {
        startPosition += pageSize;
        finishPosition += pageSize;
    }

    public void previousPage() {
        startPosition -= pageSize;
        finishPosition -= pageSize;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

}
