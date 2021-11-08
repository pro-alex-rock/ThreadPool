package app;

import app.exceptions.TooManyThreadsException;
import app.model.FTPool;
import app.model.TaskSubmitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class FixedThreadPool implements IExecutorService {

    private final int poolSize;
    private final int maximumPoolSize = 16;
    private final TaskSubmitter taskSubmitter = new TaskSubmitter();
    private Set<Thread> threadPool;
    private int countWorkingThreads;
    private boolean isShutdown;

    public FixedThreadPool(int poolSize) {
        this.poolSize = poolSize;
        createThreadPool(poolSize);
    }

    private void createThreadPool(int poolSize) {
        if (isMoreThenMaximumPoolSize(poolSize)) {
            throw new TooManyThreadsException("The capacity " + poolSize + " is too large.");
        }
        threadPool = new HashSet<>(poolSize, 1.1f);
        for (int i = 0; i < poolSize; i++) {
            //threadPool.add(new Thread(taskSubmitter.getTask()));
            threadPool.add(new Thread());
        }

        for (Thread thread : threadPool) {
            if (isFree(thread)) {
                thread.start();
            }
        }
    }

    public boolean isFree(Thread thread) {
        Thread.State currState = thread.getState();
        return (currState == Thread.State.NEW && thread.isAlive());
    }

    @Override
    public void shutdown() {
        isShutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            try {
                boolean isEstablishedThread = proceedFromTaskSubmitter();
                if (isEstablishedThread) {
                    countWorkingThreads--;
                }
                return;
            } catch (InterruptedException e) {
                throw new RuntimeException("argbv");
            }

        }
        Thread thread = null;
        if (countWorkingThreads < threadPool.size()) {
            thread = new Thread(command);
            thread.start();
            countWorkingThreads++;
            if (!thread.isAlive()) {
                countWorkingThreads--;
            }
        } else {
            try {
                taskSubmitter.addTask(command);
            } catch (InterruptedException e) {
                throw new RuntimeException("Cannot add task to TaskSubmitter.");
            }
        }
    }

    private boolean proceedFromTaskSubmitter() throws InterruptedException {
        if (taskSubmitter.getSize() > 0) {
            new Thread(taskSubmitter.getTask()).start();
            countWorkingThreads++;
            return true;
        }
        return false;
    }

    private boolean isMoreThenMaximumPoolSize(int newCapacity) {
        return newCapacity > maximumPoolSize;
    }

    public static void main(String[] args) {
        /*FixedThreadPool threadPool = new FixedThreadPool(4);
        int count = 0;
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Test(++count));
        }
        threadPool.shutdown();*/

        FTPool ftPool = new FTPool(4);
        int count = 0;
        for (int i = 0; i < 10; i++) {
            ftPool.execute(new Test(++count));
        }
    }
}
