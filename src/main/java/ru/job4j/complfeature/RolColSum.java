package ru.job4j.complfeature;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * RolColSum.
 * CompletableFeature example.
 * Compute row and column summing.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 22.02.2022.
 */
public class RolColSum {
    /**
     * Sums.
     * Helper class.
     * Used for storing columns and rows sums.
     *
     * @author fourbarman (maks.java@yandex.ru).
     * @version %I%, %G%.
     * @since 22.02.2022.
     */
    public static class Sums {
        public Sums(int rowSum, int colSum) {
            this.colSum = colSum;
            this.rowSum = rowSum;
        }

        private final int rowSum;
        private final int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    /**
     * Linear summing method.
     *
     * @param matrix given array of ints.
     * @return Sums[] array.
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int midSumRow = 0;
            int midSumCol = 0;
            for (int k = 0; k < matrix.length; k++) {
                midSumRow += matrix[i][k];
            }
            for (int c = 0; c < matrix.length; c++) {
                midSumCol += matrix[c][i];
            }
            sums[i] = new Sums(midSumRow, midSumCol);
        }
        return sums;
    }

    /**
     * Asynchronous summing method.
     *
     * @param matrix given array of ints.
     * @return Sums[] array.
     * @throws ExecutionException   Exception.
     * @throws InterruptedException Exception.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getRaw(matrix, i).thenCombine(getCol(matrix, i), Sums::new).get();
        }
        return sums;
    }

    /**
     * Returns sums in columns by index.
     *
     * @param matrix Given array.
     * @param index  Current index of given column.
     * @return Array of sums by index.
     */
    private static CompletableFuture<Integer> getCol(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int k = 0; k < matrix.length; k++) {
                sum += matrix[index][k];
            }
            return sum;
        });
    }

    /**
     * Returns sums in raws by index
     *
     * @param matrix Given array.
     * @param index  Current index of given raw.
     * @return Array of sums by index.
     */
    private static CompletableFuture<Integer> getRaw(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int k = 0; k < matrix.length; k++) {
                sum += matrix[k][index];
            }
            return sum;
        });
    }
}
