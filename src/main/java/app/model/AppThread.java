package app.model;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class AppThread extends Thread {

    public boolean isFree() {
        Thread.State currState = Thread.currentThread().getState();
        return (currState != Thread.State.NEW && this.isAlive());
    }
}
