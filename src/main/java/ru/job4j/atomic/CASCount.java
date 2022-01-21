package ru.job4j.atomic;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CASCount.
 * <p>
 * Simple counter.
 * Increments initial value by starting increment() method.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.01.2022.
 */
@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount(Integer start) {
        this.count.set(start);
    }

    public void increment() {
        this.count.incrementAndGet();
    }

    public int get() {
        return this.count.get();
    }
}
