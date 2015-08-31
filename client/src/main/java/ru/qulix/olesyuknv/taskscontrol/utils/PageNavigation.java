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
     * количество записей на одной странице
     */
    private int pageSize;

    /**
     * переход на следующую страницу
     */
    public void nextPage() {
        startPosition += pageSize;
    }

    /**
     * переход на предыдущую страницу
     */
    public void previousPage() {
        if (startPosition <= 0) {
            return;
        }
        startPosition -= pageSize;
    }

    /**
     * установка размера страницы
     *
     * @param size размер, size > 0
     */
    public void setPageSize(int size) {
        if (size <= 0) {
            return;
        }
        pageSize = size;
        startPosition = 0;
    }

    /**
     * Получение начальной позиции страницы
     *
     * @return начальную позицию
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Получение конечной позиции страницы
     *
     * @return конечную позицию
     */
    public int getFinishPosition() {
        return startPosition + pageSize;
    }
}
