package app.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class TaskSubmitter implements Submitter {

    private int taskPoolCapacity = 64;
    private final BlockingQueue<Runnable> taskStore = new ArrayBlockingQueue<>(taskPoolCapacity);

    public void setTaskPoolCapacity(int newCapacity) {
        taskPoolCapacity = newCapacity;
    }

    @Override
    public int getSize() {
        return taskStore.size();
    }

    @Override
    public BlockingQueue<Runnable> getStore() {
        return taskStore;
    }

    @Override
    public void clearStore() {
        taskStore.clear();
    }

    @Override
    public void addTask(Runnable task) throws InterruptedException {
        taskStore.put(task);
    }

    @Override
    public Runnable getTask() throws InterruptedException {
        return taskStore.take();
    }
}
