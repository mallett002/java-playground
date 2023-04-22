package playground.algorithms.graph;

import java.util.*;

public class PacificAtlantic {
    int ROWS;
    int COLS;
    int[][] heights;

    // going from ocean inward
    private void dfs(Integer row, Integer col, Set<List<Integer>> visit, int previous) {
        if (
                visit.contains(Arrays.asList(row, col)) ||
                        row < 0 || col < 0 || row == ROWS || col == COLS ||
                        this.heights[row][col] < previous
        ) {
            return;
        }

        visit.add(Arrays.asList(row, col));
        dfs(row + 1, col, visit, this.heights[row][col]);
        dfs(row - 1, col, visit, this.heights[row][col]);
        dfs(row, col + 1, visit, this.heights[row][col]);
        dfs(row, col - 1, visit, this.heights[row][col]);
    }

    /*
    Explanation:
    Can make to neighbor (and eventually ocean) if next cell is <= cur cell
      - So opposite is true. Can move from ocean inward if next cell is greater than curr
    Make visit sets for pac & atl (put cells that can make to ocean in here)

    Check top row for pac (bc touching ocean) & look at all neighbors
    Check bottom row for atl (bc touching ocean) & look at all neighbors
    Check left col for pac (bc touching ocean) & look at all neighbors
    Check right row for atl (bc touching ocean) & look at all neighbors

    Check if each coordinate is in both pac & atl. If so add to final list
    return final list
    */

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        this.ROWS = heights.length;
        this.COLS = heights[0].length;
        this.heights = heights;
        Set<List<Integer>> pac = new HashSet<>();
        Set<List<Integer>> atl = new HashSet<>();

        // row one is pac & last row is atl
        for (int c = 0; c < COLS; c++) {
            dfs(0, c, pac, heights[0][c]);
            dfs(ROWS - 1, c, atl, heights[ROWS - 1][c]);
        }

        // left & right. left is pac & right is atl
        for (int r = 0; r < ROWS; r++) {
            dfs(r, 0, pac, heights[r][0]);
            dfs(r, COLS - 1, atl, heights[r][COLS - 1]);
        }

        List<List<Integer>> bothList = new ArrayList<>();
        // loop through each cell, if in both, add to bothSet

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                List<Integer> coords = Arrays.asList(r, c);

                if (pac.contains(coords) && atl.contains(coords)) bothList.add(coords);
            }
        }

        return bothList;
    }
}