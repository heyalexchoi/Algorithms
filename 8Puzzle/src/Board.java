/**
 * Created by alexchoi1 on 7/17/14.
 */

public class Board {

    // array representation of Board
    private int[] blocksArray;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    }

    // board dimension N
    public int dimension() {
        return 0;
    }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        return new Board(new int[0][0]);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Stack<Board>();
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        return "";
    }
}
