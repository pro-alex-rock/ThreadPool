package app.model;

import java.util.ArrayList;
import java.util.List;

public class TaskListSubmitter implements Submitter {
    private int taskPoolCapacity = 64;
    private volatile List<Runnable> taskStore = new ArrayList<>(taskPoolCapacity);


    @Override
    public int getSize() {
        return taskStore.size();
    }

    @Override
    public List<Runnable> getStore() {
        return taskStore;
    }

    @Override
    public void clearStore() {
        taskStore.clear();
    }

    @Override
    public synchronized void addTask(Runnable task) throws InterruptedException {
        while (getSize() == taskPoolCapacity) {
            wait();
        }
        taskStore.add(task);
        notify();
    }

    @Override
    public Runnable getTask() throws InterruptedException {
        while (getSize() == 0) {
            wait();
        }
        Runnable task = taskStore.get(0);
        taskStore.remove(0);
        return task;
    }
}
