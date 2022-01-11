package ru.job4j.threads.userstorage;

import org.junit.Test;
import ru.job4j.userstorage.User;
import ru.job4j.userstorage.UserStore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * UserStoreTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 11.02.2022.
 */
public class UserStoreTest {
    /**
     * Test when initial user has enough amount to transfer
     * than transfer success.
     */
    @Test
    public void whenEnoughAmountForSuccessTransferThanTransferSucceed() {
        UserStore userStore = new UserStore();
        userStore.add(new User(1, 200));
        userStore.add(new User(2, 100));
        assertThat(userStore.transfer(1, 2, 100), is(true));
        assertThat(userStore.getUser(1).getAmount(), is(100));
        assertThat(userStore.getUser(2).getAmount(), is(200));
    }

    /**
     * Test when initial user has not enough amount to transfer
     * than transfer will not work.
     */
    @Test
    public void whenNotEnoughAmountThanTransferWillNotWork() {
        UserStore userStore = new UserStore();
        userStore.add(new User(1, 0));
        userStore.add(new User(2, 100));
        assertThat(userStore.transfer(1, 2, 100), is(false));
        assertThat(userStore.getUser(1).getAmount(), is(0));
        assertThat(userStore.getUser(2).getAmount(), is(100));
    }

    /**
     * Test when two threads trying to transfer
     * and initial user has enough amount to transfer
     * than transfer success.
     */
    @Test
    public void whenTwoThreadsUseStorageAndEnoughAmountThanTransferCorrect() throws InterruptedException {
        UserStore userStore = new UserStore();
        userStore.add(new User(1, 200));
        userStore.add(new User(2, 100));
        Thread thread1 = new Thread(() -> userStore.transfer(1, 2, 20));
        Thread thread2 = new Thread(() -> userStore.transfer(1, 2, 80));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStore.getUser(1).getAmount(), is(100));
        assertThat(userStore.getUser(2).getAmount(), is(200));
    }

    /**
     * Test when two threads trying to transfer
     * and initial user has not enough amount to transfer
     * than transfer will not work.
     */
    @Test
    public void whenTwoThreadsUseStorageAndNotEnoughAmountThanTransferWillNotWork() throws InterruptedException {
        UserStore userStore = new UserStore();
        userStore.add(new User(1, 0));
        userStore.add(new User(2, 100));
        Thread thread1 = new Thread(() -> userStore.transfer(1, 2, 20));
        Thread thread2 = new Thread(() -> userStore.transfer(1, 2, 80));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStore.getUser(1).getAmount(), is(0));
        assertThat(userStore.getUser(2).getAmount(), is(100));
    }
}