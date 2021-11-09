package app.model;

import java.util.List;

public interface IExecutorService {
    void shutdown();

    List<Runnable> shutdownNow();

    boolean isShutdown();

    boolean isTerminated();

    void execute(Runnable command);
}
