package app.model;

import java.util.concurrent.BlockingQueue;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public interface Submitter {

    int getSize();

    BlockingQueue<Runnable> getStore();

    void clearStore();

    void addTask(Runnable task) throws InterruptedException;

    Runnable getTask() throws InterruptedException;
}
