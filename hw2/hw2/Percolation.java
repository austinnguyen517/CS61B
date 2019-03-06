package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int dim;
    private int[][] grid;
    private int open;
    private WeightedQuickUnionUF trackTwoSent;
    private WeightedQuickUnionUF trackOneSent;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }
        dim = N;
        grid = new int[N][N];
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                grid[i][j] = -1;
            }
        }

        trackTwoSent = new WeightedQuickUnionUF(dim*dim + 2);
        trackOneSent = new WeightedQuickUnionUF(dim*dim + 1);
        for (int i = 0; i < dim; i += 1) {
            trackTwoSent.union(0, i + 1);
            trackOneSent.union(0, i + 1);
            trackTwoSent.union(dim*dim + 1, dim*dim - i);
        }
        /*Each element is represented by taking 4 * row + column + 1 in trackers*/
        open = 0;
    }

    public void open(int row, int col) {
        isValid(row, col);
        if (grid[row][col] != 1) {
            grid[row][col] = 1;
            open += 1;
            checkThenConnect(row, col, row - 1, col);
            checkThenConnect(row, col, row + 1, col);
            checkThenConnect(row, col, row, col - 1);
            checkThenConnect(row, col, row, col + 1);
        }
    }

    private boolean checkThenConnect (int rowOrig, int colOrig, int rowOther, int colOther) {
        if (rowOther < 0 || rowOther >= dim || colOther < 0 || colOther >= dim) {
            return true;
        }
        if (isOpen(rowOther, colOther)) {
            trackTwoSent.union(rowOrig * dim + colOrig + 1, rowOther * dim + colOther + 1);
            trackOneSent.union(rowOrig * dim + colOrig + 1, rowOther * dim + colOther + 1);
        }
        return true;
    }

    private void isValid(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim) {
            throw new IndexOutOfBoundsException("Invalid row or column");
        }
    }

    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        return trackOneSent.connected(0, row * dim + col + 1) && grid[row][col] == 1;
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates(){
        return trackTwoSent.connected(0, dim*dim + 1);
    }

    public static void main(String[] args) {

    }
}
