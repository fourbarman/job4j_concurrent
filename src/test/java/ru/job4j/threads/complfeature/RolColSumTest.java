package ru.job4j.threads.complfeature;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.complfeature.RolColSum;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * RolColSumTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 22.02.2022.
 */
public class RolColSumTest {
    int smallLength;
    int bigLength;
    int[][] small;
    int[][] big;
    RolColSum.Sums[] smallSums;
    RolColSum.Sums[] bigSums;

    /**
     * Set test variables.
     */
    @Before
    public void setVars() {
        smallLength = 3;
        bigLength = 1000;

        small = new int[smallLength][smallLength];
        big = new int[bigLength][bigLength];

        smallSums = new RolColSum.Sums[smallLength];
        bigSums = new RolColSum.Sums[bigLength];

        for (int i = 0; i < smallLength; i++) {
            for (int k = 0; k < smallLength; k++) {
                small[i][k] = 1;
            }
        }
        for (int i = 0; i < bigLength; i++) {
            for (int k = 0; k < bigLength; k++) {
                big[i][k] = 1;
            }
        }
    }

    /**
     * Test sum
     * when small matrix filled with 1
     * than any sum of rows or columns should be equal to matrix length.
     */
    @Test
    public void whenSumAndSmallMatrixFilledWithOneThanThanSumOfColOrRawShouldBeEqualToMatrixLength() {
        RolColSum.Sums[] s = RolColSum.sum(small);
        int midElIndex = s.length / 2;
        assertThat(s[midElIndex].getColSum(), is(s.length));
        assertThat(s[midElIndex].getRowSum(), is(s.length));
    }

    /**
     * Test sum
     * when big matrix filled with 1
     * than any sum of rows or columns should be equal to matrix length.
     */
    @Test
    public void whenSumAndBigMatrixFilledWithOneThanThanSumOfColOrRawShouldBeEqualToMatrixLength() {
        RolColSum.Sums[] s = RolColSum.sum(big);
        int midElIndex = s.length / 2;
        assertThat(s[midElIndex].getColSum(), is(big.length));
        assertThat(s[midElIndex].getRowSum(), is(big.length));
    }

    /**
     * Test sumAsync
     * when small matrix filled with 1
     * than any sum of rows or columns should be equal to matrix length.
     */
    @Test
    public void whenSumAsyncAndSmallMatrixFilledWithOneThanSumOfColOrRawShouldBeEqualToMatrixLength() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] s = RolColSum.asyncSum(small);
        int midElIndex = s.length / 2;
        assertThat(s[midElIndex].getColSum(), is(s.length));
        assertThat(s[midElIndex].getRowSum(), is(s.length));
    }

    /**
     * Test sumAsync
     * when big matrix filled with 1
     * than any sum of rows or columns should be equal to matrix length.
     */
    @Test
    public void whenSumAsyncAndBigMatrixFilledWithOneThanThanSumOfColOrRawShouldBeEqualToMatrixLength() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] s = RolColSum.asyncSum(big);
        int midElIndex = s.length / 2;
        assertThat(s[midElIndex].getColSum(), is(big.length));
        assertThat(s[midElIndex].getRowSum(), is(big.length));
    }
}
