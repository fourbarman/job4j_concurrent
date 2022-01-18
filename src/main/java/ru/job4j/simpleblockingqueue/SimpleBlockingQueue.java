package ru.job4j.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    public SimpleBlockingQueue(int size) {
        this.limit = size;
    }

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        System.out.println("offer q size: " + this.queue.size());
        while (this.queue.size() == this.limit) {
            try {
                System.out.println("Producer Waiting");
                notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.queue.offer(value);
        if (this.queue.size() > 1) {
            notifyAll();
        }
    }

    public synchronized T poll() {
        System.out.println("poll q size: " + this.queue.size());
        while (this.queue.isEmpty()) {
            try {
                System.out.println("Consumer Waiting");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (this.queue.size() == this.limit) {
            notifyAll();
        }
        return this.queue.poll();
    }
}
