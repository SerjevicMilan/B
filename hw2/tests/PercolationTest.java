import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;import edu.princeton.cs.algs4.Stopwatch;



public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    // TODO: Using the given tests above as a template,
    //       write some more tests and delete the fail() line
    @Test
    public void openTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(1, 1);
        p.open(2, 2);
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.OPEN}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);

        try {
            p.open(-1, 3);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }

        N = 5;
        p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 0},
                {0, 1},
                {1, 4},
                {2, 0},
                {3, 0},
                {3, 1},
                {4, 1},
                {4, 4},
                {1, 0},
                {1, 1}
        };
        Cell[][] eState = {
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.OPEN},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.OPEN}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(eState);
        assertThat(p.percolates()).isTrue();

    }

    @Test
    public void testExceptionOnInvalidOpen() {
        int N = 5;
        Percolation p = new Percolation(N);
        assertThrows(IllegalArgumentException.class, () -> p.open(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> p.open(0, -1));
        assertThrows(IllegalArgumentException.class, () -> p.open(N, N));
    }

    @Test
    public void testEmptyGrid() {
        assertThrows(IllegalArgumentException.class, () -> new Percolation(0));
    }

    @Test
    public void testNegativeGridSize() {
        assertThrows(IllegalArgumentException.class, () -> new Percolation(-5));
    }

    @Test
    public void testBoundary() {
        int N = 5;
        Percolation p = new Percolation(N);
        p.open(0, 0); // Top-left corner
        p.open(4, 4); // Bottom-right corner
        assertThat(p.isOpen(0, 0)).isTrue();
        assertThat(p.isOpen(4, 4)).isTrue();
        assertThat(p.isFull(0, 0)).isTrue();
        assertThat(p.isFull(4, 4)).isFalse();
    }

    @Test
    public void stressTestLargeGrid() {
        int N = 1000;
        Percolation p = new Percolation(N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                p.open(i, j);
            }
        }
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void performanceTestWithStopwatch() {
        int N = 1000;
        Stopwatch stopwatch = new Stopwatch();
        Percolation p = new Percolation(N);
        while (!p.percolates()) {
            int row = (int) (Math.random() * N);
            int col = (int) (Math.random() * N);
            p.open(row, col);
        }
        double elapsed = stopwatch.elapsedTime();
        System.out.printf("Percolation on a %dx%d grid took %.2f seconds.%n", N, N, elapsed);
        assertThat(p.percolates()).isTrue();
    }

}
