package org.tablefunctions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TableFunctionsTest
{
    RowFunctions rf = new RowFunctions();
    @Test
    public void testRowStringToIntArray() {
        TableFunctions tf = new TableFunctions();
        String input = "5 1 9 5";
        int[] expected = {5,1,9,5};
        int[] parsedTable = tf.rowStringToIntArray(input, " ");
        assertArrayEquals(expected, parsedTable);
    }

    @Test
    public void testChecksum() {
        TableFunctions tf = new TableFunctions();
        ArrayList<String> input = new ArrayList(Arrays.asList("5 1 9 5", "7 5 3", "2 4 6 8"));
        Integer checksum = tf.applyAndSumOverRows(input, this.rf::rowMinMaxDiff);
        assertEquals(Integer.valueOf(18), checksum);
    }

    @Test
    public void testEvenlyDivisible() {
        TableFunctions tf = new TableFunctions();
        ArrayList<String> input = new ArrayList(Arrays.asList("5 9 2 8", "9 4 7 3", "3 8 6 5"));
        Integer checksum = tf.applyAndSumOverRows(input, this.rf::evenlyDivisible);
        assertEquals(Integer.valueOf(9), checksum);
    }
}
