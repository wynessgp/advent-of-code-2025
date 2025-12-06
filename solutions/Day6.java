package solutions;

import java.util.ArrayList;

public class Day6 {
    public static void main(String[] args) {
        ArrayList<String> unfilteredLines = Utils.getAllInputLines("day6.txt");

        // So here is some assumptions that SEEM to hold true for the input:
        // We'll have the same number of operands for ALL operations
        // Every pair of operands will have an OPERATOR
        ArrayList<String> operators = getOperatorsFromInput(unfilteredLines);
        ArrayList<ArrayList<Integer>> operands = getOperandsFromInput(unfilteredLines);

        getSolutionPartOne(operators, operands);

        // operators shouldn't matter, since we're doing a sum for the final result
        // the operators chosen are also commutative (thank god) so L -> R reading isn't too important
        // but we do need to change the operands here since the numbers are MUCH different...
        ArrayList<ArrayList<Integer>> cephalopodOperands = getCephalopodOperands(unfilteredLines);
        getSolutionPartTwo(operators, cephalopodOperands);
    }

    private static ArrayList<ArrayList<Integer>> getCephalopodOperands(ArrayList<String> unfilteredLines) {
        ArrayList<String> operandDigits = new ArrayList<>(); // first pass.
        ArrayList<ArrayList<Integer>> operands = new ArrayList<>(); // second pass.
        boolean firstIteration = true;

        // build up the "column" format.
        // idx 0 -> 0th column
        for (String line : unfilteredLines) {
            if (line.contains("*") || line.contains("+"))  { // should be the "last" line.
                continue;
            }

            // Gonna manually loop on these lines. That way we can get an "absolute" column.
            // In theory, adding spaces is OK, since we'll trim those with integer conversion.
            // It's only completely blank lines that we'll need to delete post-processing.
            for (int i = 0; i < line.length(); i++) {
                if (firstIteration) {
                    operandDigits.add(i, "" + line.charAt(i));
                } else {
                    operandDigits.set(i, operandDigits.get(i) + line.charAt(i));
                }
            }

            firstIteration = false;
        }

        int problemIdx = 0;
        for (String digitSequence : operandDigits) {
            if (digitSequence.isEmpty() || digitSequence.isBlank()) {
                // Found the blank line from before, so we're in a new "problem" sequence
                problemIdx++;
                continue;
            } else {
                // Okay, there's a sequence here. Indicate what "problem" we belong to.
                String trimmed = digitSequence.trim();
                int number = Integer.parseInt(trimmed);
                if (problemIdx == operands.size()) { // when we reach a new column, so we don't out-index ourselves.
                    ArrayList<Integer> currentProblem = new ArrayList<>();
                    currentProblem.add(number);
                    operands.add(problemIdx, currentProblem);
                } else {
                    operands.get(problemIdx).add(number);
                }
            }
            firstIteration = false;
        }

        return operands;
    }

    private static ArrayList<ArrayList<Integer>> getOperandsFromInput(ArrayList<String> unfilteredLines) {
        ArrayList<ArrayList<Integer>> operands = new ArrayList<>();
        boolean firstIteration = true;

        // build up the "column" format.
        // idx 0 -> 0th column
        for (String line : unfilteredLines) {
            if (line.contains("*") || line.contains("+"))  { // should be the "last" line.
                continue;
            }

            String[] splitLine = line.split(" ");
            int column = 0;

            for (String operand : splitLine) {
                if (operand.isEmpty() || operand.isBlank()) {
                    continue;
                } else {
                    // the first time we walk through, the AL won't be initialized.
                    int rand = Integer.parseInt(operand);
                    if (firstIteration) {
                        ArrayList<Integer> currentColumn = new ArrayList<>();
                        currentColumn.add(rand);
                        operands.add(column, currentColumn);
                    } else {
                        operands.get(column).add(rand);
                    }

                    column++;
                }
            }

            firstIteration = false;
        }
        return operands;
    }

    private static ArrayList<String> getOperatorsFromInput(ArrayList<String> unfilteredLines) {
        ArrayList<String> operators = new ArrayList<>();

        for (String line : unfilteredLines) {
            if (line.contains("*") || line.contains("+")) {
                // First: split the line on spaces.
                String[] splitLine = line.split(" ");
                // Now, because of weird text justification, this won't exactly be pretty.
                // Ignore the empty bits, only add +s or *s.
                for (String operator : splitLine) {
                    if (operator.isEmpty() || operator.isBlank()) {
                        continue;
                    }
                    operators.add(operator);
                }
            }
        }

        return operators;
    }

    private static void getSolutionPartOne(ArrayList<String> operators, ArrayList<ArrayList<Integer>> operands) {
        long grandTotal = getGrandSum(operators, operands);

        System.out.println("\n\n");
        System.out.println("Part 1: Grand total of problems... | \t\t" + grandTotal + "\t\t |");
        System.out.println("\n\n");
    }

    private static long getGrandSum(ArrayList<String> operators, ArrayList<ArrayList<Integer>> operands) {
        // Using our assumption, we should have a 1-1 mapping of operators -> operands.
        long grandTotal = 0;
        int index = 0;

        for (String operator : operators) {
            if (operator.equals("*")) {
                long product = 1;
                ArrayList<Integer> currentOperands = operands.get(index);
                for (int operand : currentOperands) {
                    product *= operand;
                }
                grandTotal += product;
            } else if (operator.equals("+")) {
                long sum = operands.get(index).stream().mapToInt(Integer::intValue).sum(); // shortcut
                grandTotal += sum;
            } else {
                System.out.println("Shouldn't have this type of operator!");
            }
            index++;
        }
        return grandTotal;
    }

    private static void getSolutionPartTwo(ArrayList<String> operators, ArrayList<ArrayList<Integer>> operands) {
        // Okay, so the nice input parsing I did for part 1? Lol, f**k you Grant.
        // I am writing this at 4:57AM cause why not.
        // Why the hell do cephalopods do math this way?
        long grandTotal = getGrandSum(operators, operands);

        System.out.println("\n\n");
        System.out.println("Part 2: Grand total of problems... | \t\t" + grandTotal + "\t\t |");
        System.out.println("\n\n");
    }
}
