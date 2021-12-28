package ru.job4j.io;

import java.util.function.Predicate;

/**
 * Input.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 28.12.2021.
 */
public interface Input {
    /**
     * Read input and return content according to Predicate.
     *
     * @param predicate Predicate.
     * @return String.
     */
    String getContent(Predicate<Character> predicate);
}
