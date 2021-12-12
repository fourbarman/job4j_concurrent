package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Wget.
 * <p>
 * Download file with given speed in bytes.
 * Need to specify arguments:
 * 1. full url to file with filename.
 * 2. speed in byte.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 12.12.2021.
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    /**
     * Calculates difference in current download time and given time.
     *
     * @param speed     Given speed.
     * @param timeStart Current time before.
     * @param timeEnd   Current time after.
     * @return Difference in times.
     */
    private long getTime(int speed, long timeStart, long timeEnd) {
        return (1024 / speed) - (timeEnd - timeStart);
    }

    /**
     * Return filename from url.
     *
     * @param url Url.
     * @return Filename.
     */
    private String getFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    /**
     * Download file be url and with given speed.
     */
    @Override
    public void run() {
        String fileName = getFileName(url);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long timeStart = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timeEnd = System.currentTimeMillis();
                long diff = this.getTime(speed, timeStart, timeEnd);
                if (diff > 0) {
                    Thread.sleep(diff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}