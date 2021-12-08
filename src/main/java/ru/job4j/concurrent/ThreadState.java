package ru.job4j.concurrent;

/**
 * ThreadState.
 * <p>
 * Run two new threads. Print thread's state and name.
 * When both new threads terminated, print "work done".
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.12.2021.
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + " " + first.getState() + " | " + second.getName() + " " + second.getState());
        }
        System.out.println("Work done!");
    }
}
