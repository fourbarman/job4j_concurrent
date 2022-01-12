package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final Map<Integer, User> storage = new ConcurrentHashMap<>();

    /**
     * Adds User to storage.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean add(User user) {
        return this.storage.putIfAbsent(user.getId(), user) != null;
    }

    /**
     * Updates user in storage.
     * Delete from storage, than add given, if already exist.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean update(User user) {
        return this.storage.replace(user.getId(), user) != null;
    }

    /**
     * Deletes user from storage.
     *
     * @param user User.
     * @return Result.
     */
    public synchronized boolean delete(User user) {
        return this.storage.remove(user.getId(), user);
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
        User fromUser = this.getUser(fromId);
        User toUser = this.getUser(toId);
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
        return this.storage.get(id);
    }
}
