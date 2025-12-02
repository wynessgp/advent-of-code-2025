package solutions;

import java.util.ArrayList;

public class Day2 {
    public static void main(String[] args) {
        ArrayList<String> inputLines = Utils.getAllLinesSeperatedByComma("day2.txt");
        getSolutionPartOne(inputLines);
        getSolutionPartTwo(inputLines);
    }
    

    private static void getSolutionPartOne(ArrayList<String> inputLines) {
        // Find all of the invalid IDs in a given range, sum the total of the ranges.
        // These invalid IDs are IDs with some sequence of digits repeated twice
        // i.e. from 11-22 -> 11 is invalid, 22 is invalid. No nums with leading 0s.
        
        // Numbers with an odd number of digits don't exhibit this property
        // Other skips?
        long invalidIdSum = 0; 

        for (String range : inputLines) {
            String[] splitRange = range.split("-");
            long lowerBound = Long.parseLong(splitRange[0]);
            long upperBound = Long.parseLong(splitRange[1]);

            // Unoptimized idea: loop from [lb, ub]
            // Split the number in half (string conversion) and compare the two halves.

            // This was fast enough. In theory, other skips include adding in specific
            // digits to skip (i.e. tens once we've satisfied a ones match)
            for (long i = lowerBound; i <= upperBound; i++) {
                String numAsString = Long.toString(i);
                String firstHalf = numAsString.substring(0, numAsString.length() / 2);
                String secondHalf = numAsString.substring(numAsString.length() / 2);
                if (firstHalf.equals(secondHalf)) {
                    invalidIdSum += i;
                }
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 1: Invalid ID sum is... | \t\t" + invalidIdSum + "\t\t |");
        System.out.println("\n\n");
    }

    private static void getSolutionPartTwo(ArrayList<String> inputLines) {
        // Second part: if a pattern is repeated greater than 2 times, it's invalid.
        // i.e. 111 is now invalid -> 1 1 1 
        long invalidIdSum = 0; 

        for (String range : inputLines) {
            String[] splitRange = range.split("-");
            long lowerBound = Long.parseLong(splitRange[0]);
            long upperBound = Long.parseLong(splitRange[1]);

            // Unoptimized idea: loop from [lb, ub]
            // For any given number, we in theory have to check [1, length / 2] digits for patterns

            for (long i = lowerBound; i <= upperBound; i++) {
                String numAsString = Long.toString(i);
                
                // So we need to slide down our check as we go. If we find a segment that doesn't match
                // Then we can skip the current length. If we also overshoot the end, skip.
                // If we reach the end NICELY and we matched a pattern, then it's an invalid ID. We're done.

                for (int length = 1; length <= numAsString.length() / 2; length++) { // Start on single digits. Expand to "half" the string length.
                    // Edge case 1: string matching doesn't nicely terminate.
                    // Ex: Checking "1234" in "123412345" -> last check slips off.
                    if (numAsString.length() % length != 0) {
                        continue; // No need to check this length.
                    }

                    // We don't walk off the edge.
                    // Then, the pattern must appear successively UNTIL the end of the string.
                    String patternToCheck = numAsString.substring(0, length);
                    int startIdx = length;

                    for (; startIdx < numAsString.length(); startIdx += length) {
                        String nextPattern = numAsString.substring(startIdx, startIdx + length);
                        if (!patternToCheck.equals(nextPattern)) { // we've found a pattern that doesn't match. move to next digit.
                            break;
                        }

                    }

                    if (startIdx != numAsString.length()) {
                        continue;
                    }

                    // Otherwise this WAS an invalid idx.
                    invalidIdSum += i;
                    break; // No need to check the other lengths.
                }
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 2: Invalid ID sum is... | \t\t" + invalidIdSum + "\t\t |");
        System.out.println("\n\n");
    }
}
