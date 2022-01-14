package ru.job4j.wait;

/**
 * CountBarrier.
 * <p>
 * Class for testing wait(), notifyAll().
 * Blocks all threads while one of them counts {@link #count()} method calls.
 * Until {@link #total} value.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 14.02.2022.
 */
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Counts calls, than notifies all threads to go further.
     */
    public void count() {
        synchronized (monitor) {
            this.count++;
            monitor.notifyAll();
        }
    }

    /**
     * Blocks all threads until {@link #count} >= {@link #total}.
     */
    public synchronized void await() {
        while (count < this.total) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
