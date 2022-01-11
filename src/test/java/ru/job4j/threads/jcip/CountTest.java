package ru.job4j.threads.jcip;

import org.junit.Test;
import ru.job4j.jcip.Count;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * CountTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 11.01.2022.
 */
public class CountTest {
    /**
     * Класс описывает нить со счетчиком.
     */
    private class ThreadCount extends Thread {
        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    /**
     * Test when execute 2 threads than count should work correct.
     * Создаем счетчик.
     * Создаем нити.
     * Запускаем нити.
     * Заставляем главную нить дождаться выполнения наших нитей.
     * Проверяем результат.
     *
     * @throws InterruptedException IE.
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}
