package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * InputFile.
 * Read file and return content.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 28.12.2021.
 */
public class InputFile implements Input {
    private final File file;

    public InputFile(File file) {
        this.file = file;
    }

    /**
     * Read file and return content according to Predicate.
     *
     * @param predicate Predicate.
     * @return String.
     */
    @Override
    public String getContent(Predicate<Character> predicate) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = bufferedReader.read()) > 0) {
                if (predicate.test((char) data)) {
                    sb.append((char) data);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return sb.toString();
    }
}
