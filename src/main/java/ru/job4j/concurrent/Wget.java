package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Wget.
 * <p>
 * Download file with given speed in bytes.
 * Need to specify arguments:
 * 1. full url to file with filename.
 * 2. speed in byte/s.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 14.12.2021.
 */
public class Wget implements Runnable {
    private final String url;
    private final long speed;

    public Wget(String url, long speed) {
        this.url = url;
        this.speed = speed;
    }

    /**
     * Validate args.
     *
     * @param argsNum args length.
     * @throws IllegalArgumentException if condition doesn't met.
     */
    private static void validateArgs(int argsNum) throws IllegalArgumentException {
        if (argsNum != 2) {
            throw new IllegalArgumentException(
                    "Usage: 2 arguments must be provided. Full url to file and speed in bytes/s!"
                            + System.lineSeparator()
                            + "Example: https://somesite/somefile.txt 1048576"
            );
        }
    }

    /**
     * Download file be url and with given speed.
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(new URI(url).getPath()).getFileName().toString())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            long timeStart = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    long diffTime = System.currentTimeMillis() - timeStart;
                    timeStart = System.currentTimeMillis();
                    bytesWrited = 0;
                    if (diffTime < 1000) {
                        Thread.sleep(1000 - diffTime);
                    }
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (URISyntaxException | IOException ur) {
            ur.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long t1 = System.currentTimeMillis();
        validateArgs(args.length);
        String url = args[0];
        long speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
        long t2 = System.currentTimeMillis();
        System.out.println("Total time: " + (t2 - t1));
    }
}