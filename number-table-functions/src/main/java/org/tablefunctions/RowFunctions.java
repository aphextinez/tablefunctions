package org.tablefunctions;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * RowFunctions is a class of functions that can be applied to inputs representing one row of data
 */
public class RowFunctions {

    /**
     * Determine and return the difference between the minimum and maximum values present in an input row
     * @param row int array to search for max and min
     * @return int that is the max - min
     * @throws IllegalArgumentException
     */
    public int rowMinMaxDiff(int[] row) throws IllegalArgumentException {
        if(!basicRowValidations(row, 1))
            throw new IllegalArgumentException("Input row is invalid");
        int temp = 0;
        if(row.length == 1) {
            return(0);
        }
        //this is a slight optimization, it is still linear complexity but we cut down
        //the number of comparisons we have to make for min and max
        for(int i = 0; i+1 < row.length; i = i + 2) {
            //we are going to essentially do this using 2 stacks in-place in the array
            if(row[i] > row[i+1]) {
                temp = row[i];
                row[i] = row[i+1];
                row[i+1] = temp;
            }
        }
        int max = row[1];
        int min = row[0];
        //iterate over min candidates stack to get potential winning min
        for(int i = 2; i < row.length; i = i+2) {
            if(min > row[i]) {
                min = row[i];
            }
        }
        //iterate over max candidates stack to get potential winning max
        for(int i = 3; i < row.length; i = i+2) {
            if(max < row[i]) {
                max = row[i];
            }
        }
        if(row.length%2 != 0) {
            int straggler = row[row.length-1];
            if(straggler < min) {
                min = straggler;
            } else if (straggler > max) {
                max = straggler;
            }
        }
        return(max - min);
    }

    /**
     * Find one pair in input row where one number evenly divides the other and calculate that division
     * For example in a row  [2  12  13  17], 12/2 = 6
     * @param row int array to search for evenly divisible pair in
     * @return int that is the result of (larger num in evenly divisible pair / smaller num in evenly divisible pair)
     * @throws IllegalArgumentException
     */
    public int evenlyDivisible(int[] row) throws IllegalArgumentException {
        if(!basicRowValidations(row, 2))
            throw new IllegalArgumentException("Input row is invalid");
        int max = Integer.MIN_VALUE;
        int notZeroOrOne = 0;
        int notZero = 0;
        boolean checkSpecialCases = false;
        HashSet<Integer> rowKeys = new HashSet<>();
        for(int i = 0; i < row.length; i++) {
            //next 3 lines used wrt handling cases where 0 and 1 are present in the row
            if(row[i] != 0) { notZero = row[i]; }
            if(row[i] != 0 && row[i] != 1) { notZeroOrOne = row[i]; }
            if(row[i] == 1 || row[i] == 0) { checkSpecialCases = true; }
            //now we handle duplicate edge case i.e. 3/3 = 1.0, when we encounter a key
            //we have already seen and added into the rowKeys HashSet in a previous loop iteration
            if(rowKeys.contains(row[i]) && row[i] != 0)
            {
                return(1);
            }
            else {
                rowKeys.add(row[i]);
            }
            //we want to keep track of the max abs value to decide which algorithm to use
            if(Math.abs(row[i]) > max) { max = Math.abs(row[i]); }
        }
        //we saw a 0 or 1 which requires special handling
        if(checkSpecialCases) {
            return(evenlyDivisibleSpecialCase(rowKeys, notZero, notZeroOrOne));
        }
        //brute force is more efficient in cases where the square root of the max abs value
        //present in the input row is greater than the input length
        if(Math.sqrt(max) > row.length) {
            return(evenlyDivisibleBruteForce(row));
        }
        else {
            return(evenlyDivisibleFactorization(row, rowKeys));
        }
    }

    /**
     * Determine evenly divisible pair division result for input rows containing
     * 0's or 1's and therefore requiring special handling
     * @param rowKeys unique set of values seen in pre-processed row
     * @param notZero int != 0 if anything other than 0 was seen in input row
     * @param notZeroOrOne int != 1 or 0 if anything nonzero or not 1 seen in input row
     * @return 0 in valid 0 special case or numerator in valid 1 special non-duplicate case
     * @throws IllegalArgumentException
     */
    private int evenlyDivisibleSpecialCase(HashSet<Integer> rowKeys, int notZero, int notZeroOrOne) throws IllegalArgumentException {
        if(rowKeys.contains(0) && rowKeys.size() == 2 && notZero != 0) {
           return(0);
       }
       else if(rowKeys.contains(1) && (notZeroOrOne != 0 && rowKeys.size() == 2)) {
           return(notZeroOrOne);
       }
       else {
           throw new IllegalArgumentException("Input row containing 0 or 1 is invalid");
       }
    }

    /**
     * Find one pair in input row where one number evenly divides the other and calculate that division
     * More efficient than factorization method when sqrt(max abs value in input) > input length
     * @param row int array to search for evenly divisible pair in
     * @return int that is the result of (larger num in evenly divisible pair / smaller num in evenly divisible pair)
     * @throws IllegalArgumentException
     */
    public int evenlyDivisibleBruteForce(int[] row) throws IllegalArgumentException {
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row.length; j++ ) {
                if(i == j) {
                    continue;
                }
                if(row[i]%row[j] == 0) {
                    return((row[i]/row[j]));
                }
            }
        }
        throw new IllegalArgumentException("No solution found for input row using brute force");
    }

    /**
     * Find one pair in input row where one number evenly divides the other and calculate that division
     * More efficient than brute force method when sqrt(max abs value in input) < input length
     * @param row row int array to search for evenly divisible pair in
     * @param seenInRow unique set of values seen in pre-processed row
     * @return int that is the result of (larger num in evenly divisible pair / smaller num in evenly divisible pair)
     * @throws IllegalArgumentException
     */
    public int evenlyDivisibleFactorization(int[] row, HashSet<Integer> seenInRow) throws IllegalArgumentException {
        //now we will iterate over the input array, find the factors
        for(int i = 0; i < row.length; i++) {
            ArrayList<Integer> factors = new ArrayList<>();
            for(int j = 2; j <= Math.sqrt(Math.abs(row[i])); j++) {
                if (row[i] % j == 0) {
                    factors.add(j);
                    //don't add a duplicate for square roots
                    if (row[i] / j != j) {
                        factors.add(row[i] / j);
                    }
                }
            }
            // now iterate over the factors and see if row[i] / +-factor is in our lookup Hashset
            for( Integer factor: factors) {
                if(seenInRow.contains(factor)) {
                    return(row[i]/factor);
                } else if (seenInRow.contains(-factor)) {
                    return(-row[i]/factor);
                }
            }
        }
        throw new IllegalArgumentException("No solution found for input using factorization method");
    }

    /**
     * Validation function to check for row not meeting minimum size requirement
     * @param row to perform validation on
     * @param minimumSize requirement for input minimum size
     * @return true if the input meets size requirement, false if it does not
     * @throws IllegalArgumentException
     */
    private boolean basicRowValidations(int[] row, int minimumSize) throws IllegalArgumentException {
        return(row.length < minimumSize ? false : true);
    }
}
