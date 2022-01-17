package ru.job4j.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int size;

    public synchronized int getSize() {
        return this.size;
    }

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    /**
     * Если размер коллекции <= границе,
     * то добаляем элемент.
     * @param value Element.
     */
    public synchronized void offer(T value) {
        int curSize = this.queue.size();
        System.out.println("Current size: " + curSize);
        if (curSize == this.size) {
            System.out.println("Queue overload ");
            this.notifyAll();
        }
        else {
            this.queue.offer(value);
            System.out.println("Added ");
        }
    }

    /**
     * Если очередь пустая - усыпить нить.
     * Иначе вернуть head и удалить из очереди.
     *
     * @return Head element.
     */
    public synchronized T poll() {
        System.out.println("Polling ");
        return this.queue.poll();
    }

    public synchronized void await() {
        while (this.queue.peek() == null) {
            try {
                System.out.println("Waiting ");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
