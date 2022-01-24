package ru.job4j.threads.atomic;

import org.junit.Test;
import ru.job4j.atomic.CASCount;

import java.util.concurrent.ThreadPoolExecutor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * CASCountTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 21.01.2022.
 */
public class CASCountTest {
    /**
     * Test when one thread increments start value 10 times
     * than result will be plus 10.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void whenIncrement10TimesInOneThreadThanResultWillBePlus10() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread count = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        count.start();
        count.join();
        assertThat(casCount.get(), is(10));
    }

    /**
     * Test when two threads increment start value 10 times each
     * than result will be plus 20.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void whenIncrement10TimesInTwoThreadsEachThanResultWillBePlus20() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread count1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        Thread count2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        count1.start();
        count1.join();
        count2.start();
        count2.join();
        assertThat(casCount.get(), is(20));
    }

    /**
     * Test when start int is 20
     * and two threads increment start value 10 times each
     * than result will be plus 20.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void whenStartFrom20Increment10TimesInTwoThreadsEachThanResultWillBePlus20() throws InterruptedException {
        CASCount casCount = new CASCount(20);
        Thread count1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        Thread count2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        count1.start();
        count1.join();
        count2.start();
        count2.join();
        assertThat(casCount.get(), is(40));
    }

    /**
     * Test when start from 10
     * and 100 threads call increment() 2 times each
     * than result should be 210 and success.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void thpool() throws InterruptedException {
        CASCount casCount = new CASCount(10);
        for (int i = 0; i < 100; i++) {
            Thread count = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    casCount.increment();
                }
            }, "Thread " + i);
            count.start();
            count.join();
        }
        assertThat(casCount.get(), is(210));
    }
}
