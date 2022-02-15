package ru.job4j.forkjoinpool;

import java.util.concurrent.RecursiveTask;

/**
 * ParallelIndexSearch.
 * Represents RecursiveTask for searching object in given array.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.01.2022.
 */
public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private T[] arr;
    private int start;
    private int last;
    private int arrLength;
    private T target;

    /**
     * Constructor.
     *
     * @param array Array to search in.
     * @param start Start index of array.
     * @param last  Last index of array.
     * @param obj   Object to search.
     */
    public ParallelIndexSearch(T[] array, int start, int last, T obj) {
        this.arr = array;
        this.start = start;
        this.last = last;
        this.arrLength = array.length;
        this.target = obj;
    }

    /**
     * Search object index in array using Fork Join.
     * If array.length < 10 starts linear search.
     * Else, divides array by 2 and searches object in left and right parts of array.
     *
     * @return Index or -1 if not found.
     */
    @Override
    protected Integer compute() {
        if ((last - start) < 10) {
            return this.linearSearch(arr, target);
        }
        int middle = (start + last) / 2;
        if (target.equals(arr[middle])) {
            return middle;
        }
        ParallelIndexSearch<T> parallelLeft = new ParallelIndexSearch<>(arr, start, middle, target);
        ParallelIndexSearch<T> parallelRight = new ParallelIndexSearch<>(arr, middle + 1, last, target);
        parallelLeft.fork();
        parallelRight.fork();
        Integer resultLeft = parallelLeft.join();
        Integer resultRight = parallelRight.join();
        return resultLeft == -1 ? resultLeft : resultRight;
    }

    /**
     * Linear search object in given array.
     *
     * @param array Array.
     * @param obj   Object.
     * @return Index or -1 if not found.
     */
    private int linearSearch(T[] array, T obj) {
        for (int i = 0; i < array.length; i++) {
            if (obj.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
