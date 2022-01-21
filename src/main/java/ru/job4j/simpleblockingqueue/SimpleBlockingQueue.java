package ru.job4j.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * SimpleBlockingQueue.
 * <p>
 * Represents simple blocking queue with limit.
 * Need 2 threads to work.
 * Producer offers elements to queue. If queue has met limit, than wait until Consumer tales all elements.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 18.01.2022.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    public SimpleBlockingQueue(int size) {
        this.limit = size;
    }

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    /**
     * Offer element to queue if it's possible.
     * If queue is full than wait.
     *
     * @param value Element.
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == this.limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    /**
     * Return queue head.
     * If queue is empty than wait.
     *
     * @return Queue head.
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T element = queue.poll();
        notifyAll();
        return element;
    }

    /**
     * Return true if queue is empty.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
