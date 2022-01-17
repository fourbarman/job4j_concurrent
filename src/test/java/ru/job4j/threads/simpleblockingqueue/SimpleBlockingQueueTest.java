package ru.job4j.threads.simpleblockingqueue;

import org.junit.Test;
import ru.job4j.simpleblockingqueue.SimpleBlockingQueue;

public class SimpleBlockingQueueTest {
    @Test
    public void test1() throws InterruptedException {
        int size = 10;
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<Integer>(size);

        Thread producer = new Thread(() -> {
            for (int i = 0; i <= 200; i++) {
                System.out.println("Try to offer: " + i);
                simpleBlockingQueue.offer(i);
            }
        });

        Thread consumer = new Thread(() -> {
            System.out.println("Poll: " + simpleBlockingQueue.poll());
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

}
