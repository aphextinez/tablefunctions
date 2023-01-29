package org.tablefunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * TableFunctions is a class of functions that can be applied to a tabular data set
 */
public class TableFunctions {
    /**
     * Applies a function over the rows if a data table and sums over the row results
     * @param lines String list table input with spaces tokenizing rows
     * @param rowVariation Function to be applied to row to retrieve value towards sum
     * @return int sum of the results for all rows
     */
    public int applyAndSumOverRows(ArrayList<String> lines, Function<int[],Integer> rowVariation) {
        int totalResults = 0;
        int result;
        for(int i = 0; i < lines.size(); i++) {
                int[] row = rowStringToIntArray(lines.get(i), " ");
                result = rowVariation.apply(row);
                totalResults = totalResults + result;
        }
        return(totalResults);
    }

    /**
     * Transforms input row from string representation to int array representation
     * @param row input string to transform
     * @return int array representation of data row
     */
    public int[] rowStringToIntArray(String row, String token) {
        return(Arrays.stream(row.split(token))
                     .mapToInt(Integer::parseInt)
                     .toArray());
    }
}
