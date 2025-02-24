import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private WeightedQuickUnionUF VbUf;//with virtual bottom
    private WeightedQuickUnionUF noVbUf;//without virtual bottom
    private boolean[][] grid;
    private int size;
    private int vTop;
    private int vBot;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        grid = new boolean[N][N];
        VbUf = new WeightedQuickUnionUF(N * N + 2);
        noVbUf = new WeightedQuickUnionUF(N * N + 1);
        size = N;
        vTop = N * N;
        vBot = N * N + 1;
    }

    //conv cords of 2d arr to pos for Uf
    private int convertToPosition(int row, int col) {
        return row * size + col;
    }

    //connect touching positions
    private void connect(int p1, int p2) {
        noVbUf.union(p1, p2);
        VbUf.union(p1, p2);
    }

    //check if connecting pos possible and conn
    private void merge(int row, int col) {
        int pos =  convertToPosition(row, col);
        if (row == 0) { //if first row connect virtual top with cur pos
            connect(pos, vTop);
        }
        if (row == size - 1) {//if last row connect with virtual bot
            VbUf.union(pos, vBot);
        }
        if (row > 0 && isOpen(row - 1, col)) {//check if we can conn to top pos
            connect(pos, convertToPosition(row - 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {//check if we can conn to left pos
            connect(pos, convertToPosition(row, col - 1));
        }
        if (row < size - 1 && isOpen(row + 1, col)) {//check if we can conn to bott pos
            connect(pos, convertToPosition(row + 1, col));
        }
        if (col < size - 1 && isOpen(row, col + 1)) {//check if we can conn to right pos
            connect(pos, convertToPosition(row, col + 1));
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
        grid[row][col] = true;
        merge(row, col);//connect if possible
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
        return noVbUf.connected(convertToPosition(row, col), vTop);// size is == to virtual top
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
