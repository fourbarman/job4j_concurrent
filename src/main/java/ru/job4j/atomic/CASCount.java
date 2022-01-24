package ru.job4j.atomic;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

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
    private final AtomicReference<Integer> count;

    public CASCount(Integer start) {
        this.count = new AtomicReference<>(start);
    }

    public CASCount() {
        this.count = new AtomicReference<>(0);
    }

    public void increment() {
        Integer curValue;
        Integer newValue;
        do {
            curValue = count.get();
            newValue = count.get() + 1;
        } while (!(count.compareAndSet(curValue, newValue)));
    }

    public int get() {
        return this.count.get();
    }
}
