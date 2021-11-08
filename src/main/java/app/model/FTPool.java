package app.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class FTPool implements Executor {
    private BlockingQueue<Runnable> taskPool = new ArrayBlockingQueue<>(64);
    private int poolSize;

    private final TaskSubmitter taskSubmitter = new TaskSubmitter();

    public FTPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            new Thread(new PollQueue()).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        //taskPool.add(command);
        try {
            taskSubmitter.addTask(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class PollQueue implements Runnable {

        @Override
        public void run() {
            while (true) {
                /*if (!taskPool.isEmpty()) {
                    Runnable poll = taskPool.poll();
                    poll.run();
                }*/
                if (taskSubmitter.getSize() > 0) {
                    Runnable poll = null;
                    try {
                        poll = taskSubmitter.getTask();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    poll.run();
                }
            }
        }
    }
}


