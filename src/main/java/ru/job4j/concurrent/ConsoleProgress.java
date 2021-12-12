package ru.job4j.concurrent;

/**
 * ConsoleProgress.
 * <p>
 * Emulates console progress.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 12.12.2021.
 */
public class ConsoleProgress implements Runnable {

    /**
     * While !terminated emulates console progress.
     */
    @Override
    public void run() {
        try {
            char[] process = new char[]{'\\', '|', '/'};
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < process.length; i++) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + process[i]);
                }
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
