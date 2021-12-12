package ru.job4j.concurrent;

/**
 * ConsoleProgress.
 * <p>
 * Run two new threads. Print thread's name.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 12.12.2021.
 */
public class ConsoleProgress implements Runnable {

    /**
     * While thread !terminated than print progress.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + "\\");
                Thread.sleep(500);
                System.out.print("\r load: " + "|");
                Thread.sleep(500);
                System.out.print("\r load: " + "/");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
        progress.interrupt();
    }
}
