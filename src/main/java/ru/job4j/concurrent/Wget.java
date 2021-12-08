package ru.job4j.concurrent;

/**
 * Wget.
 * <p>
 * Emulates loading process 0% to 100%
 * Add 1% to counter every 1000ms.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 08.12.2021.
 */
public class Wget {
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            try {
                System.out.print("\rLoading : " + i + "%");
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
