package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SingleLockList.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 13.01.2022.
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = this.clone(list);
    }

    /**
     * Adds element to SingleLockList.
     *
     * @param value element.
     */
    public synchronized void add(T value) {
        this.list.add(value);
    }

    /**
     * Get element from SingleLockList by index.
     *
     * @param index Index of element.
     * @return element.
     */
    public synchronized T get(int index) {
        return this.list.get(index);
    }

    /**
     * Iterator.
     *
     * @return Iterator.
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return clone(this.list).iterator();
    }

    /**
     * Clone collection to prevent multithreading problems.
     *
     * @param list Initial List.
     * @return Cloned List.
     */
    private synchronized List<T> clone(List<T> list) {
        return list.parallelStream().collect(Collectors.toList());
    }

}
