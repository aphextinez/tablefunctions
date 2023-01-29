package org.tablefunctions;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * App to allow basic user I/O for TableFunctions
 */
public class App 
{
    private static void checkForExit(String line) {
        if(line.contains("exit")) {
            System.out.println("Not calling table functions");
            System.exit(0);
        }
    }
    public static void main( String[] args ) {
        TableFunctions tf = new TableFunctions();
        RowFunctions rf = new RowFunctions();
        ArrayList<String> in;
        String line = "go";
        String rowVariation;
        Scanner sc = new Scanner(System.in);
        while (true) {
            rowVariation = null;
            in = new ArrayList<>();
            System.out.println("Enter row variation to apply: <checksum> | <evenlydivisible>\nEnter \"exit\" to terminate");
            while (sc.hasNextLine()) {
                rowVariation = sc.next();
                line = rowVariation;
                if (rowVariation.contains("checksum")
                        || rowVariation.contains("evenlydivisible")
                        || line.contains("exit")) {
                    break;
                }
            }
            checkForExit(line);
            System.out.println("Enter the table input and press enter to execute: \"```<input table>```\"\nEnter \"exit\" to terminate");
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                checkForExit(line);
                if ((line.contains("```") && !in.isEmpty())) {
                    break;
                } else if (line.matches("[0-9 ]+")) {
                    in.add(line);
                }
            }
            checkForExit(line);
            System.out.println();
            if (rowVariation.equals("checksum")) {
                System.out.print("Solution is: ");
                System.out.println(tf.applyAndSumOverRows(in, rf::rowMinMaxDiff));
            } else if (rowVariation.equals("evenlydivisible")) {
                System.out.print("Solution is: ");
                System.out.println(tf.applyAndSumOverRows(in, rf::evenlyDivisible));
            }
        }
    }
}
