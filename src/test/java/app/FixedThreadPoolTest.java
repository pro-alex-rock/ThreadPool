package app;

import app.exceptions.TooManyThreadsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Haleta
 * 2021
 */
class FixedThreadPoolTest {

    FixedThreadPool threadPool = new FixedThreadPool(4);

    @Test
    public void shouldThrowTooManyThreadsExceptionSoMoreThenMaxPoolSize() {
        int poolSize = 17;
        TooManyThreadsException exception = assertThrows(TooManyThreadsException.class, () -> {
            new FixedThreadPool(poolSize);
        });
        assertEquals("The capacity " + poolSize + " is too large.", exception.getMessage());
    }

    @Test
    public void givenNullInExecuteThenThrowNPException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            threadPool.execute(null);
        });
        assertEquals("Need Runnable command.", exception.getMessage());
    }

    @Test
    public void shouldShutdownNowAndReturnListTasks() {
        List<Runnable> actual = null;
        int count = 0;
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Task(++count));
            if (i == 4) {
                actual = threadPool.shutdownNow();
            }
        }
        assertTrue(!actual.isEmpty());
    }

    @Test
    public void shouldShutdownNowAndReturnListOfTenTasks() throws InterruptedException {
        List<Runnable> actual = null;
        int count = 0;
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Task(++count));
        }
        actual = threadPool.shutdownNow();
        assertTrue(!actual.isEmpty());
    }

    @Test
    public void shouldShutdownNowAndReturnEmptyList() throws InterruptedException {
        List<Runnable> actual = null;
        int count = 0;
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Task(++count));
        }
        Thread.sleep(1000);
        actual = threadPool.shutdownNow();
        assertTrue(actual.isEmpty());
    }

    @Test
    public void whenShutdownNowThenIsTerminatedTrue() {
        threadPool.execute(new Task(1));
        threadPool.shutdownNow();
        assertTrue(threadPool.isTerminated());
    }

    static class Task implements Runnable {
        private int number;

        public Task(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("Test class print message: " + number);
        }

        @Override
        public String toString() {
            return "Test " +
                    "number=" + number;
        }
    }
}