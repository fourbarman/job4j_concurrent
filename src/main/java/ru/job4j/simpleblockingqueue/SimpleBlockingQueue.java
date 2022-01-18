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
    public synchronized void offer(T value) {
        check(x -> (x == this.limit));
        queue.offer(value);
        if (this.queue.size() == 1) {
            notifyAll();
        }
    }

    /**
     * Return queue head.
     * If queue is empty than wait.
     *
     * @return Queue head.
     */
    public synchronized T poll() {
        check(x -> (x == 0));
        if (queue.size() == this.limit) {
            notifyAll();
        }
        return queue.poll();
    }

    /**
     * Wait thread if condition has been met.
     *
     * @param predicate Condition.
     */
    private synchronized void check(Predicate<Integer> predicate) {
        while (predicate.test(queue.size())) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
