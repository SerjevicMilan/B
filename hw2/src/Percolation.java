import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private WeightedQuickUnionUF VbUf;//with virtual bottom
    private WeightedQuickUnionUF noVbUf;//without virtual bottom
    private boolean[][] grid;
    private int size;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        grid = new boolean[N][N];
        VbUf = new WeightedQuickUnionUF(N * N + 2);
        noVbUf = new WeightedQuickUnionUF(N * N + 1);
        size = N;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
        grid[row][col] = true;
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
            return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return 0;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
