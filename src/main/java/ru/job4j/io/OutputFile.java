package ru.job4j.io;

import java.io.*;

/**
 * OutputFile.
 * Write content to file.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 28.12.2021.
 */
public class OutputFile implements Output {
    private final File file;

    public OutputFile(File file) {
        this.file = file;
    }

    /**
     * Write content to file.
     *
     * @param content String content.
     */
    @Override
    public void saveContent(String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
