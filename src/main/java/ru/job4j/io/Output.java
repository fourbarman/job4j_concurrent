package ru.job4j.io;

/**
 * Output.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 28.12.2021.
 */
public interface Output {
    /**
     * Write content to output.
     *
     * @param content String content.
     */
    void saveContent(String content);
}
