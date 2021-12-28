package ru.job4j.io;

import java.io.*;

/**
 * ParseFile.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 28.12.2021.
 */
public final class ParseFile {
    private final File file;
    private final Input input;
    private final Output output;

    public ParseFile(File file, Input input, Output output) {
        this.file = file;
        this.input = input;
        this.output = output;
    }

    /**
     * Return file.
     *
     * @return File.
     */
    public synchronized File getFile() {
        return file;
    }

    /**
     * Return content from file.
     *
     * @return String.
     */
    public synchronized String getContent() {
        return this.input.getContent(x -> true);
    }

    /**
     * Return content from file without unicode chars.
     *
     * @return String.
     */
    public synchronized String getContentWithoutUnicode() {
        return this.input.getContent(x -> (x < 0x80));
    }

    /**
     * Save content.
     *
     * @param content Content.
     */
    public synchronized void saveContent(String content) {
        this.output.saveContent(content);
    }
}
