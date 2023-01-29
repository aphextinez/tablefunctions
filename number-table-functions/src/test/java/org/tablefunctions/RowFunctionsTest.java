package org.tablefunctions;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RowFunctionsTest {
    RowFunctions rf = new RowFunctions();
    //edge cases: diff of 0, 0, empty, duplicate, negative numbers, 1 item in array
    @Test
    public void testRowMinMaxDiff() {
        Map<Integer, int[]> inputToExpectedMap = Map.of(
              Integer.valueOf(8), new int[]{5, 1, 9, 5},
                Integer.valueOf(4), new int[]{7, 5, 3},
                Integer.valueOf(12), new int[]{-7, 5, -3},
                Integer.valueOf(6), new int[]{2, 4, 6, 8},
                Integer.valueOf(0), new int[]{7}
                );
        for(Integer key : inputToExpectedMap.keySet()) {
            assertEquals(key.intValue(), rf.rowMinMaxDiff(inputToExpectedMap.get(key)));
        }
    }

    @Test(expected= IllegalArgumentException.class)
    public void testRowMinMaxEmptyInput() {
        rf.rowMinMaxDiff(new int[]{});
    }

    //edge cases: 0, perfect square, empty, no solution, duplicate, negative numbers, 1 item in array
    @Test
    public void testEvenlyDivisibleValidInputs() {
        Map<Integer, int[]> inputToExpectedMap = Map.of(
                Integer.valueOf(4), new int[]{5, 9, 2, 8},
                Integer.valueOf(1), new int[]{8, 9, 7, 8},
                Integer.valueOf(3), new int[]{9, 4, 7, 3},
                Integer.valueOf(30), new int[]{4095, 191, 4333, 161, 3184, 193, 4830, 4153, 2070, 3759, 1207, 3222, 185, 176, 2914, 4152},
                Integer.valueOf(2), new int[]{3, 8, 6},
                Integer.valueOf(0), new int[]{0, 0, 7},
                Integer.valueOf(8), new int[]{8, 1},
                Integer.valueOf(-3), new int[]{9, 4, 7, -3},
                Integer.valueOf(-6), new int[]{-30, 4, 7, 5}
        );
        for(Integer key : inputToExpectedMap.keySet()) {
            assertEquals(key.intValue(), rf.evenlyDivisible(inputToExpectedMap.get(key)));
        }
    }
    @Test
    public void testEvenlyDivisibleFactorizationValidInputs() {
        Map<Integer, HashSet<Integer>> inputToExpectedMap = Map.of(
                Integer.valueOf(4), new HashSet<>(Arrays.asList(5, 9, 2, 8)),
                Integer.valueOf(3), new HashSet<>(Arrays.asList(9, 4, 7, 3)),
                Integer.valueOf(30), new HashSet<>(Arrays.asList(4095, 191, 4333, 161, 3184, 193, 4830, 4153, 2070, 3759, 1207, 3222, 185, 176, 2914, 4152)),
                Integer.valueOf(2), new HashSet<>(Arrays.asList(3, 8, 6)),
                Integer.valueOf(-3), new HashSet<>(Arrays.asList(9, 4, 7, -3)),
                Integer.valueOf(-6), new HashSet<>(Arrays.asList(-30, 4, 7, 5))
        );
        for(Integer key : inputToExpectedMap.keySet()) {
            HashSet<Integer> rowHashSet = inputToExpectedMap.get(key);
            int[] rowArray = rowHashSet.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(key.intValue(), rf.evenlyDivisibleFactorization(rowArray, rowHashSet));
        }
    }

    @Test
    public void testEvenlyDivisibleBruteForceValidInputs() {
        Map<Integer, int[]> inputToExpectedMap = Map.of(
                Integer.valueOf(4), new int[]{5, 9, 2, 8},
                Integer.valueOf(1), new int[]{8, 9, 7, 8},
                Integer.valueOf(3), new int[]{9, 4, 7, 3},
                Integer.valueOf(30), new int[]{4095, 191, 4333, 161, 3184, 193, 4830, 4153, 2070, 3759, 1207, 3222, 185, 176, 2914, 4152},
                Integer.valueOf(2), new int[]{3, 8, 6},
                Integer.valueOf(-3), new int[]{9, 4, 7, -3},
                Integer.valueOf(-6), new int[]{-30, 4, 7, 5}
        );
        for(Integer key : inputToExpectedMap.keySet()) {
            assertEquals(key.intValue(), rf.evenlyDivisibleBruteForce(inputToExpectedMap.get(key)));
        }
    }

    @Test(expected= IllegalArgumentException.class)
    public void testEvenlyDivisibleNoSolutionFound() {
        rf.evenlyDivisible(new int[]{2, 3, 7, 11});
    }

    @Test(expected= IllegalArgumentException.class)
    public void testEvenlyDivisibleEmptyInput() {
        rf.evenlyDivisible(new int[]{});
    }

    @Test(expected= IllegalArgumentException.class)
    public void testEvenlyDivisibleInputSizeOne() {
        rf.evenlyDivisible(new int[]{2});
    }

    @Test(expected= IllegalArgumentException.class)
    public void testEvenlyDivisibleInvalidZero() {
        rf.evenlyDivisible(new int[]{450, 0, 7, 3});
    }

    @Test(expected= IllegalArgumentException.class)
    public void testEvenlyDivisibleInvalidOne() {
        rf.evenlyDivisible(new int[]{5, 9, 11, 1});
    }
}
