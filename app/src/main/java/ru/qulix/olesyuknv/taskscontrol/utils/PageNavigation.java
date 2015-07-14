package ru.qulix.olesyuknv.taskscontrol.utils;

/**
 * Постраничная навигация
 *
 * @author QULIX-OLESYUKNV
 */
public class PageNavigation {

    /**
     * количество записей на одной странице
     */
    private static final int INCREMENT = 10;

    /**
     * позиция первого элемента списка
     */
    private static int startPosition = 0;

    /**
     * позиция последнего элемента списка
     */
    private static int finishPosition = INCREMENT;

    /**
     * переход на следующую страницу
     */
    public void nextPage() {
        startPosition += INCREMENT;
        finishPosition += INCREMENT;
    }

    /**
     *
     * @return  true или false в зависимости от налия записей
     */
    public boolean noData() {
        if (startPosition <= 0) {
            return true;
        }
        return false;
    }

    /**
     * переход на предыдущую страницу
     */
    public void previousPage() {
        startPosition -= INCREMENT;
        finishPosition -= INCREMENT;
    }

    public int getINCREMENT() {
        return INCREMENT;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }
}
