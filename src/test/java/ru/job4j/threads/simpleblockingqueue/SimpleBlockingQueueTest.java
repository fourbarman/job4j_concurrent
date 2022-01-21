package ru.job4j.threads.simpleblockingqueue;

import org.junit.Test;
import ru.job4j.simpleblockingqueue.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * UserStoreTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 18.01.2022.
 */
public class SimpleBlockingQueueTest {
    /**
     * Test when producer offers and consumer polls than success.
     *
     * @throws InterruptedException InterruptedException.
     */
    @Test
    public void whenProducerOffersAndConsumerPollsThanSuccess() throws InterruptedException {
        int size = 10;
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(size);
        List<Integer> offerList = new ArrayList<>();
        List<Integer> pollList = new ArrayList<>();
        for (int i = 0; i <= 200; i++) {
            offerList.add(i);
        }
        Thread producer = new Thread(() -> {
            for (int i : offerList) {
                try {
                    simpleBlockingQueue.offer(i);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        },
                "producer");

        Thread consumer = new Thread(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <= 200; i++) {
                try {
                    list.add(simpleBlockingQueue.poll());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            pollList.addAll(list);
        },
                "consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertTrue(pollList.containsAll(offerList));
    }

    /**
     * Test when Fetch All Then Get It.
     *
     * @throws InterruptedException Exception.
     */
    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }

                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
