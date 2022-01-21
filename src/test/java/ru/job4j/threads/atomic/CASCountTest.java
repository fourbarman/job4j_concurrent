package ru.job4j.threads.atomic;

import org.junit.Test;
import ru.job4j.atomic.CASCount;

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
        CASCount casCount = new CASCount(0);
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
        CASCount casCount = new CASCount(0);
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
}
