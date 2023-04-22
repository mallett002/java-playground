package playground.algorithms.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InsertInterval {
    private int[][] turnIntoArr(List<int[]> intervals) {
        int[][] result = new int[intervals.size()][];

        for (int i = 0; i < intervals.size(); i++) {
            result[i] = intervals.get(i);
        }

        return result;
    }

    private void comparePrint(int[] curr, int[] newInterval) {
        System.out.println("Comparing starts: " + "curr: " + curr[0] + " new: " + newInterval[0]);
        System.out.println("Comparing ends: " + "curr: " + curr[1] + " new: " + newInterval[1]);
    }

    private void print(int[][] intervals) {
        System.out.println("interval length: " + intervals.length);
        for (int[] interval : intervals) {
            System.out.println("copy: " + "[" + interval[0] + ", " + interval[1] + "]");
        }
    }

    private int[] createNewInterval(int[] first, int[] second) {
        int[] newInterval = new int[2];

        newInterval[0] = first[0] < second[0] ? first[0] : second[0];
        newInterval[1] = first[1] > second[1] ? first[1] : second[1];

        return newInterval;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        // otherwise goes somewhere in middle:
        for (int i = 0; i < intervals.length; i++) {
            int[] curr = intervals[i];
            System.out.println("newInterval: " + "[" + newInterval[0] + ", " + newInterval[1] + "]" + "; index: " + i);

            if (newInterval[1] < curr[0]) { // comes completely before curr. add it, then the rest
                result.add(newInterval);
                int[][] rest = Arrays.copyOfRange(intervals, i, intervals.length);
                Collections.addAll(result, rest);

                return turnIntoArr(result);
            }

            if (curr[1] < newInterval[0]) { // comes completely after curr?
                result.add(curr);
            } else { // doesn't come after or before, needs to merge with this one, reassign newInterval
                comparePrint(curr, newInterval);
                newInterval = new int[2];
                newInterval[0] = Math.min(curr[0], newInterval[0]);
                newInterval[1] = Math.max(curr[1], newInterval[1]);
            }
        }

        result.add(newInterval);

        return turnIntoArr(result);
    }
}
