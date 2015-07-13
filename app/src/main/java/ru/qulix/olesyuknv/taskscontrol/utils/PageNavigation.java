package ru.qulix.olesyuknv.taskscontrol.utils;

/**
 * Постраничная навигация
 *
 * @author QULIX-OLESYUKNV
 */
public class PageNavigation {

    private static final int INCREMENT = 10;

    private static int startPosition = 0;

    private static int finishPosition = INCREMENT;

    private static boolean isEndReached;

    public void setIsEndReached(boolean isEndReached) {
        this.isEndReached = isEndReached;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void nextPage() {
        if (startPosition > 30) {
            return;
        }
        startPosition += INCREMENT;
        finishPosition += INCREMENT;
    }

    public void previousPage() {
        if (startPosition < 0) {
            return;
        }
        startPosition -= INCREMENT;
        finishPosition -= INCREMENT;
    }

    public boolean checkRightPositions() {
        if (startPosition < 0 || isEndReached==true) {
            return false;
        }
        return true;


    }

}
