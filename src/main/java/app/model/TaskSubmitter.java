package app.model;

import app.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class TaskSubmitter {

    private final int taskPoolCapacity = 64;
    private BlockingQueue<Runnable> taskPool = new ArrayBlockingQueue<>(taskPoolCapacity);

    {
        int count = 0;
        try {
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
            taskPool.put(new Test(++count));
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot add new task.");
        }

    }

    public void addTask(Runnable task) throws InterruptedException {
        taskPool.put(task);
    }

    public Runnable getTask() throws InterruptedException {
        return taskPool.take();
    }
}
