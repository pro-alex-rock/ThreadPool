package app;

import java.util.List;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class FixedThreadPool implements IExecutorService {

    private final int poolSize;

    public FixedThreadPool(int poolSize) {
        this.poolSize = poolSize;
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
}
