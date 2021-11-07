package app.model;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class AppThread extends Thread {

    public boolean isFree(Thread thread) {
        Thread.State currState = thread.getState();
        return (currState == Thread.State.NEW && this.isAlive());
    }
}
