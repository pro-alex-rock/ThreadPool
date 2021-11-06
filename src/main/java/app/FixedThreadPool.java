package app;

import app.exceptions.TooManyThreadsException;

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
    private final int taskPoolCapacity = 64;
    private BlockingQueue<Runnable> taskPool = new ArrayBlockingQueue<>(taskPoolCapacity);
    private Set<Runnable> threadPool;

    public FixedThreadPool(int poolSize) {
        this.poolSize = poolSize;
        createThreadPool(poolSize);
    }

    private void createThreadPool(int poolSize) {
        if (!isLessThenMaximumPoolSize(poolSize)) {
            throw new TooManyThreadsException("The capacity " + poolSize + " is too large.");
        }
        threadPool = new HashSet<>(poolSize, 1.1f);
        threadPool.stream().limit(poolSize).forEach(i -> new Thread());
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

    private boolean isLessThenMaximumPoolSize(int newCapacity) {
        return newCapacity < maximumPoolSize;
    }
}
