package ru.job4j.threads.simpleblockingqueue;

import org.junit.Test;
import ru.job4j.simpleblockingqueue.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

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
                simpleBlockingQueue.offer(i);
            }
        },
                "producer");

        Thread consumer = new Thread(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <= 200; i++) {
                list.add(simpleBlockingQueue.poll());
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
}
