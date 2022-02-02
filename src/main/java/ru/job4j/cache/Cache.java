package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * Cache.
 * <p>
 * Represents cache with ConcurrentHashMap with version control.
 * When update than version++.
 * If stored version != new version than throws OptimisticException.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 02.02.2022.
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Add to HashMap.
     *
     * @param model Base model.
     * @return success.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Update.
     * If stored version != new version than throws OptimisticException.
     * Else, updates version by 1 and updates model in HashMap.
     *
     * @param model Base model.
     * @return Base if success.
     */
    public Base update(Base model) {
        BiFunction<Integer, Base, Base> equalVer = new BiFunction<Integer, Base, Base>() {
            @Override
            public Base apply(Integer integer, Base base) {
                Base stored = memory.get(model.getId());
                if (stored.getVersion() != model.getVersion()) {
                    throw new OptimisticException("Versions are NOT equal!");
                }
                AtomicInteger newVersion = new AtomicInteger(model.getVersion());
                stored = new Base(model.getId(), newVersion.incrementAndGet());
                stored.setName(model.getName());
                return stored;
            }
        };
        return memory.computeIfPresent(model.getId(), equalVer);
    }

    /**
     * Delete Base.
     *
     * @param model Base model.
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * Returns Base by id.
     *
     * @param key Base id.
     * @return Base.
     */
    public Base getBase(Integer key) {
        return memory.get(key);
    }
}
