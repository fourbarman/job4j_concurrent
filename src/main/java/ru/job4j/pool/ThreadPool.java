package ru.job4j.pool;

import ru.job4j.simpleblockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * ThreadPool.
 * Represents thread pool.
 * Uses SimpleBlockingQueue for tasks (with limit by tasks) and LikedList to store worker threads.
 * Initializes by current CPU Threads.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 07.02.2022.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private SimpleBlockingQueue<Runnable> tasks = null;
    private volatile boolean isStopped = false;
    int size;

    public ThreadPool(int maxTasks) {
        this.tasks = new SimpleBlockingQueue<>(maxTasks);
        this.size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread workerThread = new WorkerThread(tasks);
            this.threads.add(workerThread);
            workerThread.start();
        }
    }

    /**
     * Except runnable task and offer it to queue.
     * If Pool is stopped throws IllegalStateException.
     *
     * @param job Runnable task.
     * @throws InterruptedException Exception.
     */
    public void work(Runnable job) throws InterruptedException {
        if (isStopped) {
            throw new IllegalStateException("Pool is stopped");
        }
        this.tasks.offer(job);
    }

    /**
     * Set isStopped flag and interrupt working threads.
     */
    public void shutdown() {
        this.isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Wait for threads to complete all tasks from queue.
     */
    public synchronized void waitUntilAllTasksFinished() {
        while (!this.tasks.isEmpty()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * WorkerThread.
     * Represents working threads for runnable tasks.
     * Used in thread pool.
     * Takes task from queue and executes it's run() method.
     * Does nothing in catch block if tread is already interrupted to keep pool alive.
     */
    public class WorkerThread extends Thread {
        SimpleBlockingQueue<Runnable> taskQueue;

        public WorkerThread(SimpleBlockingQueue<Runnable> queue) {
            this.taskQueue = queue;
        }

        @Override
        public void run() {
            while (!isStopped) {
                try {
                    Runnable r = this.taskQueue.poll();
                    r.run();
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
