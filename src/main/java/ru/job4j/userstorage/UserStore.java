package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;

/**
 * UserStore.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 11.02.2022.
 */
@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final HashSet<User> storage;

    public UserStore() {
        this.storage = new HashSet<>();
    }

    /**
     * Adds User to storage.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean add(User user) {
        return this.storage.add(user);
    }

    /**
     * Updates user in storage.
     * Delete from storage, than add given, if already exist.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean update(User user) {
        return this.storage.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .map(this::delete)
                .map(usr -> this.storage.add(user))
                .orElse(false);
    }

    /**
     * Deletes user from storage.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean delete(User user) {
        return this.storage.remove(user);
    }

    /**
     * Transfers amount from one user to another.
     *
     * @param fromId Initial user id.
     * @param toId   Target user id.
     * @param amount Amount.
     * @return Result.
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean success = false;
        User fromUser = this.storage.parallelStream().filter(u -> u.getId() == fromId).findFirst().orElse(null);
        User toUser = this.storage.parallelStream().filter(u -> u.getId() == toId).findFirst().orElse(null);
        if (fromUser != null && toUser != null) {
            if (fromUser.getAmount() >= amount) {
                fromUser.setAmount(fromUser.getAmount() - amount);
                toUser.setAmount(toUser.getAmount() + amount);
                success = true;
            }
        }
        return success;
    }

    /**
     * Return user by id.
     *
     * @param id User id.
     * @return Found User or Null.
     */
    public synchronized User getUser(int id) {
        return this.storage.parallelStream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }
}
