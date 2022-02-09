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
    private final List<Thread> threads;
    private SimpleBlockingQueue<Runnable> tasks;
    private volatile boolean isStopped = false;
    int size;

    public ThreadPool(int maxTasks) {
        this.threads = new LinkedList<>();
        this.tasks = new SimpleBlockingQueue<>(maxTasks);
        this.size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread workerThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        this.tasks.poll().run();
                    } catch (InterruptedException e) {
                    }
                }
            });
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
        this.tasks.offer(job);
    }

    /**
     * Set isStopped flag and interrupt working threads.
     */
    public void shutdown() {
        for (Thread thread : threads) {
            while (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }
}
