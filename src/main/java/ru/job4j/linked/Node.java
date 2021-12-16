package ru.job4j.linked;

/**
 * Node.
 * Immutable class fo multithreading.
 * 1. Class itself should be final.
 * 2. Class fields should not change (be final and there should be no setters).
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 16.12.2021.
 */
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
