package ru.job4j.threads.pool;

import org.junit.Test;
import ru.job4j.pool.ThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ThreadPoolTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 02.02.2022.
 */
public class ThreadPoolTest {
    /**
     * Test when queue has 10 tasks that increment one variable than success.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void test1() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10);
        AtomicInteger result = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            threadPool.work(result::incrementAndGet);
        }
        threadPool.shutdown();
        assertThat(result.get(), is(10));
    }
}
