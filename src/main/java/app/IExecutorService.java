package app;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public interface IExecutorService extends ExecutorService {


    default boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    default <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    default  <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    default <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    default <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    default <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    default <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    default Future<?> submit(Runnable task) {
        return null;
    }
}
