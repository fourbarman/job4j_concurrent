package ru.job4j.cache;

/**
 * Base.
 * <p>
 * Represents simple class for cache with version control.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 02.02.2022.
 */
public class Base {
    private final int id;
    private final int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + this.id + " | "
                + this.version + " | "
                + this.name + "}";
    }
}
