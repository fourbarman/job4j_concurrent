package ru.job4j.concurrent;

/**
 * DCLSingleton.
 * Use "volatile" to prevent wrong check:
 * When one thread locks synchronized constructor,
 * other thread could think that object is initialized and return wrong object.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 14.12.2021.
 */
public class DCLSingleton {
    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }
}
