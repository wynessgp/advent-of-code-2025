package solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Day5 {

    static class SortByLowerBound implements Comparator<String> {
        @Override
        public int compare(String interval1, String interval2) {

            long lowerBound1 = Long.parseLong(interval1.split("-")[0]);
            long lowerBound2 = Long.parseLong(interval2.split("-")[0]);

            if (lowerBound1 < lowerBound2) return -1;
            if (lowerBound1 > lowerBound2) return 1;
            return 0; // same LB
        }
    }

    static class Interval {
        long lowerBound;
        long upperBound;

        Interval(long lb, long ub) {
            this.lowerBound = lb;
            this.upperBound = ub;
        }
    }
    
    public static void main(String[] args) {
        ArrayList<String> unfilteredLines = Utils.getAllInputLines("day5.txt");
        ArrayList<String> ranges = getRangesFromInput(unfilteredLines);
        ArrayList<String> idsToCheck = getIdsFromInput(unfilteredLines);
        ArrayList<Interval> mergedRanges = mergeRanges(ranges);

        getSolutionPartOne(mergedRanges, idsToCheck);
        getSolutionPartTwo(mergedRanges);
    }

    private static ArrayList<Interval> mergeRanges(ArrayList<String> ranges) {
        ArrayList<Interval> toReturn = new ArrayList<>();
        
        // sort the ranges first, otherwise this will take a REALLY long time.
        Collections.sort(ranges, new SortByLowerBound());

        // Use a stack to merge, then we'll put the results back into the AL
        // Using interval class so we don't have to constantly convert from strings -> numbers later on
        LinkedList<Interval> rangesStack = new LinkedList<>();
        for (String curRange : ranges) {
            long lb = Long.parseLong(curRange.split("-")[0]);
            long ub = Long.parseLong(curRange.split("-")[1]);

            // Since the ranges are sorted by lower bound, if we can't merge with the first thing
            // we pull off the list, then we've found a "new" range.
            if (rangesStack.isEmpty() || rangesStack.getLast().upperBound < lb) {
                rangesStack.add(new Interval(lb, ub));
            } else { // merge the two.
                rangesStack.getLast().upperBound = Math.max(rangesStack.getLast().upperBound, ub);
            }
        }

        toReturn.addAll(rangesStack);
        return toReturn;
    }

    private static ArrayList<String> getRangesFromInput(ArrayList<String> unfilteredLines) {
        ArrayList<String> toReturn = new ArrayList<>();

        for (String s : unfilteredLines) {
            if (s.contains("-")) {
                toReturn.add(s);
            }
        }

        return toReturn;
    }

    private static ArrayList<String> getIdsFromInput(ArrayList<String> unfilteredLines) {
        ArrayList<String> toReturn = new ArrayList<>();

        for (String s : unfilteredLines) {
            if (!s.contains("-") && !s.isEmpty()) {
                toReturn.add(s);
            }
        }

        return toReturn;
    }

    private static void getSolutionPartOne(ArrayList<Interval> ranges, ArrayList<String> idsToCheck) {
        // Count the number of IDs that fall into any given interval, as these are "fresh" ingredients.
        int freshIngredientCount = 0;

        for (String stringId : idsToCheck) {
            Long id = Long.parseLong(stringId);

            for (Interval i : ranges) {
                if (id <= i.upperBound && id >= i.lowerBound) {
                    freshIngredientCount++;
                }
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 1: Fresh ingredient count... | \t\t" + freshIngredientCount + "\t\t |");
        System.out.println("\n\n");
    }

    private static void getSolutionPartTwo(ArrayList<Interval> ranges) {
        // We don't actually need the IDs for this part, we just need to
        // Find the TOTAL number of fresh ingredient IDs.
        // Since we merged the ranges earlier, this will be super easy.
        long totalFreshIDs = 0;

        for (Interval i : ranges) {
            if (i.lowerBound == i.upperBound) {
                totalFreshIDs += 1;
            } else {
                totalFreshIDs += ((i.upperBound - i.lowerBound) + 1); // inclusive range ends.
            }
        }

        System.out.println("\n\n");
        System.out.println("Part 2: Fresh IDs total... | \t\t" + totalFreshIDs + "\t\t |");
        System.out.println("\n\n");
    }
}
