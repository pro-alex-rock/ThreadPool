package app;

import app.exceptions.TooManyThreadsException;
import app.model.IExecutorService;
import app.model.Submitter;
import app.model.TaskSubmitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class FixedThreadPool implements IExecutorService {

    private static final int MAXIMUM_POOL_SIZE = 16;
    private final Submitter taskSubmitter = new TaskSubmitter();
    private boolean isShutdownNow;
    private boolean isShutdown;

    public FixedThreadPool(int poolSize) {
        if (isMoreThenMaximumPoolSize(poolSize)) {
            throw new TooManyThreadsException("The capacity " + poolSize + " is too large.");
        }
        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread(new PollQueue());
            thread.start();
        }
    }

    @Override
    public void shutdown() {
        isShutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> list = new ArrayList<>(taskSubmitter.getStore());
        taskSubmitter.clearStore();
        isShutdownNow = true;
        return list;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return (isShutdown() && taskSubmitter.getSize() == 0) || isShutdownNow;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException("Need Runnable command.");
        }
        try {
            if (!isShutdown()) {
                taskSubmitter.addTask(command);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot execute this task: " + command.toString(), e);
        }
    }

    private boolean isMoreThenMaximumPoolSize(int newCapacity) {
        return newCapacity > MAXIMUM_POOL_SIZE;
    }

    private class PollQueue implements Runnable {

        @Override
        public void run() {
            while (!isShutdownNow) {
                if (taskSubmitter.getSize() > 0) {
                    Runnable poll = null;
                    try {
                        poll = taskSubmitter.getTask();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    poll.run();
                }
                if (isShutdown()) {
                    break;
                }
            }
        }
    }
}
