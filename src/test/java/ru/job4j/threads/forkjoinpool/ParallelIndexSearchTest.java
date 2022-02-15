package ru.job4j.threads.forkjoinpool;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.forkjoinpool.ParallelIndexSearch;

import java.util.Arrays;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ParallelIndexSearchTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 15.02.2022.
 */
public class ParallelIndexSearchTest {
    String[] arrayLessTen;
    String[] arrayMoreTen;
    int arrLength;

    /**
     * Set variables.
     */
    @Before
    public void setVars() {
        arrLength = 40;
        arrayMoreTen = new String[arrLength];
        for (int i = 0; i < arrLength; i++) {
            arrayMoreTen[i] = "Number" + i;
        }
        arrayLessTen = Arrays.copyOfRange(arrayMoreTen, 0, 9);
    }

    /**
     * Test when array.length < 10 and array has object
     * than linear search is performed
     * and return index of object
     * and success.
     */
    @Test
    public void whenArrayLengthLessTenAndLinearSearchAndArrayHasThatObjectThanSuccess() {
        String find = arrayLessTen[4];
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new ParallelIndexSearch<>(arrayLessTen, 0, arrayLessTen.length - 1, find));
        assertThat(result, is(4));
    }

    /**
     * Test when array.length < 10 and array doesn't contain object
     * than linear search is performed
     * and return -1
     * and success.
     */
    @Test
    public void whenArrayLengthLessTenAndLinearSearchAndArrayDoesntContainObjectThanReturnMinusOneThanSuccess() {
        String find = "not contained";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new ParallelIndexSearch<>(arrayLessTen, 0, arrayLessTen.length - 1, find));
        assertThat(result, is(-1));
    }

    /**
     * Test when array.length > 10 and array contains object
     * than forkjoinpool is performing search
     * and return object index
     * and success.
     */
    @Test
    public void whenArrayLengthMoreTenAndContainsObjectThanReturnIndexAndSuccess() {
        String find = arrayMoreTen[35];
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new ParallelIndexSearch<>(arrayMoreTen, 0, arrayMoreTen.length - 1, find));
        assertThat(result, is(35));
    }

    /**
     * Test when array.length > 10 and array doesn't contain object
     * than forkjoinpool is performing search
     * and return -1
     * and success.
     */
    @Test
    public void whenArrayLengthMoreTenAndDoesntContainObjectThanReturnMinusOneAndSuccess() {
        String find = "not contained";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new ParallelIndexSearch<>(arrayMoreTen, 0, arrayMoreTen.length - 1, find));
        assertThat(result, is(-1));
    }

}
