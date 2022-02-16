package ru.job4j.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
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
            return this.linearSearch();
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
        return resultLeft == -1 ? resultRight : resultLeft;
    }

    /**
     * Linear search object in given array.
     *
     * @return Index or -1 if not found.
     */
    private int linearSearch() {
        for (int i = start; i <= last; i++) {
            if (target.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Execute Fork Join Pool and search element in array.
     *
     * @param array  Given array.
     * @param object Object to search.
     * @param <T>    Type.
     * @return Index of searchable object.
     */
    public static <T> int find(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<T>(array, 0, array.length - 1, object));
    }
}
