package ru.job4j.concurrent;

/**
 * ConsoleProgress.
 * <p>
 * Run two new threads. Print thread's name.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.12.2021.
 */
public class ConsoleProgress implements Runnable {

    /**
     * While thread !terminated than print progress.
     */
    @Override
    public void run() {
        char[] process = new char[]{'\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < process.length; i++) {
                System.out.print("\r load: " + process[i]);
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        progress.interrupt();
    }
}
