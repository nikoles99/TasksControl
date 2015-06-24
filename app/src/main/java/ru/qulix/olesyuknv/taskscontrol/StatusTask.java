package ru.qulix.olesyuknv.taskscontrol;

/**
 * Task statuses.
 *
 * @author OlesyukNV
 */
public enum StatusTask {
    NOT_STARTED("not started"),
    IN_PROCESS("in process"),
    COMPLETED("completed"),
    POSTPONED("postponed");

    /**
     * current statuses.
     */
    private String status;

    StatusTask(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
