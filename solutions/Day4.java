package solutions;

import java.util.ArrayList;

public class Day4 {
    public static void main(String[] args) {
        ArrayList<String> input = Utils.getAllInputLines("day4.txt");
        char[][] inputLines = new char[input.size()][input.get(0).length()];
        
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                inputLines[i][j] = input.get(i).charAt(j);
            }
        }

        getSolutionPartOne(inputLines);
        getSolutionPartTwo(inputLines);
    }

    private static void getSolutionPartOne(char[][] inputLines) {
        // We don't mutate the input here, so i'm ok with passing it in both places. part 2 does the mutation.
        // Honestly, would bother trying a DP solution at some point, but not now.
        int rollsTotal = 0;
        for (int row = 0; row < inputLines.length; row++) {
            for (int col = 0; col < inputLines[0].length; col++) {
                if (inputLines[row][col] == '.') { // no roll to reach.
                    continue;
                } else {
                    // count the number of paper neighbors.
                    int paperNeighbors = getNeighborCount(row, col, inputLines);
                    if (paperNeighbors < 4) { // accessible.
                        rollsTotal++;
                    }
                }
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 1: Number of accessible rolls of paper... | \t\t" + rollsTotal + "\t\t |");
        System.out.println("\n\n");
    }

    private static void getSolutionPartTwo(char[][] inputLines) {
        // So now we keep going until we can't remove any more rolls... cool.
        // This means that the input is mutable, and we keep iterating until the total count changes by 0.
        // now... convert to a temporary character (just use 'x' like the example)
        // when we remove on a current iteration, the next one can clean up for us.
        int totalConverted = 0;
        int convertedThisRound = 0;
        while (true) {
            convertedThisRound = 0;
            for (int row = 0; row < inputLines.length; row++) {
                for (int col = 0; col < inputLines[0].length; col++) {
                    if (inputLines[row][col] == 'x') { // cleanup
                        inputLines[row][col] = '.';
                        continue;
                    } else if (inputLines[row][col] == '.') { // no roll to reach.
                        continue;
                    } else {
                        // count the number of paper neighbors.
                        int paperNeighbors = getNeighborCount(row, col, inputLines);
                        if (paperNeighbors < 4) { // accessible.
                            convertedThisRound++;
                            inputLines[row][col] = 'x'; // temporary mark, so we don't try and count it again.
                        }
                    }
                }
            }

            totalConverted += convertedThisRound;
            if (convertedThisRound == 0) {
                break;
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 2: Total number of converted rolls... | \t\t" + totalConverted + "\t\t |");
        System.out.println("\n\n");
    }

    private static int getNeighborCount(int row, int col, char[][] inputLines) {
        int leftwardsMod = (col - 1 < 0) ? 0 : 1; // AKA, are we in col 0?
        int upwardsMod = (row - 1 < 0) ? 0 : 1; // AKA, are we in row 0?
        int downwardsMod = (row + 1 >= inputLines.length) ? 0 : 1; // AKA, are we in the last row?
        int rightwardsMod = (col + 1 >= inputLines[0].length) ? 0 : 1;
        
        int paperSum = 0;
        for (int r = row - upwardsMod; r <= row + downwardsMod; r++) { // up & down
            for (int c = col - leftwardsMod; c <= col + rightwardsMod; c++) { // left & right
                paperSum += (inputLines[r][c] == '@' ? 1 : 0); 
            }
        }

        if (inputLines[row][col] == '@') {
            paperSum--; // technically we run over this in counting, remove it.
        }

        return paperSum;
    }
}
