package app.model;

import java.util.Collection;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public interface Submitter {

    int getSize();

    Collection<Runnable> getStore();

    void clearStore();

    void addTask(Runnable task) throws InterruptedException;

    Runnable getTask() throws InterruptedException;
}
