package app;

import app.exceptions.TooManyThreadsException;
import app.model.TaskSubmitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class FixedThreadPool implements IExecutorService {

    private final int poolSize;
    private final int maximumPoolSize = 16;
    private final TaskSubmitter taskSubmitter = new TaskSubmitter();
    private Set<Thread> threadPool;

    public FixedThreadPool(int poolSize) {
        this.poolSize = poolSize;
        createThreadPool(poolSize);
        startTasksInThreadPool();
    }

    private void createThreadPool(int poolSize) {
        if (isMoreThenMaximumPoolSize(poolSize)) {
            throw new TooManyThreadsException("The capacity " + poolSize + " is too large.");
        }
        threadPool = new HashSet<>(poolSize, 1.1f);
        for (int i = 0; i < poolSize; i++) {
            try {
                threadPool.add(new Thread(taskSubmitter.getTask()));
            } catch (InterruptedException e) {
                throw new RuntimeException("Cannot run new task.");
            }
        }
    }

    private void startTasksInThreadPool() {
        threadPool.forEach(Thread::start);
    }


    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        //put the given command in a thread
    }

    private boolean isMoreThenMaximumPoolSize(int newCapacity) {
        return newCapacity > maximumPoolSize;
    }

    public static void main(String[] args) {
        FixedThreadPool threadPool = new FixedThreadPool(4);
    }
}
