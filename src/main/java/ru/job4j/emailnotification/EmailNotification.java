package ru.job4j.emailnotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EmailNotification.
 *
 * Uses Thread pool.
 * Generate body, subject for email.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 10.02.2022.
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Create fields for email notification.
     * Submit task to pool.
     *
     * @param user User.
     */
    public void emailTo(User user) {
        if (user != null) {
            String username = user.getUserName();
            String email = user.getEmail();
            String subject = String.format("Notification {%s} to email {%s}", username, email);
            String body = String.format(" Add a new event to {%s}", username);
            this.pool.submit(() -> send(subject, body, email));
        }
    }

    /**
     * Close pool.
     */
    public void close() {
        this.pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send.
     *
     * @param subject Subject.
     * @param body    Body.
     * @param email   Email.
     */
    public void send(String subject, String body, String email) {
    }
}
