/**
 * Created by alexchoi1 on 7/17/14.
 */
import java.util.Arrays;


public class Board {

    // array representation of Board
    private final char[][] board;
    // length/width of Board
    private final char N;
    // cached manhattan distance value. init -1, to mark that distance has not been calculated yet
    private int manhattan = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = (char)blocks.length;
        this.board = new char[N][N];
        // for each row i
        for (char i = 0; i < N; i++) {
            // for each column j
            for (char j = 0; j < N; j++) {
                board[i][j] = (char)blocks[i][j];
                }
            }
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // returns goal value for i,j coordinate pair
    // goal value increments left to right, top to bottom; last value 0
    private char goal(char i, char j) {
        if (i == N - 1 && j == N - 1) {
            return 0;
        } else {
            return (char)((i * N) + j + 1);
        }
    }
    // number of blocks out of place
    public int hamming() {
        // compare board to goal, and count how many blocks have different values
        int count = 0;

        for (char i = 0; i < N; i++) {
            for (char j = 0; j < N; j++) {
                if (board[i][j] != goal(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // class used to store coordinate pairs to calculate manhattan distance
    private class coordinatePair {
        char i;
        char j;
    }
    // function used to calculate manhattan distance
    // returns inverse of goal(): the i,j coordinate pair for a goal value
    private coordinatePair coordinatesForGoalValue(char value) {
        coordinatePair pair = new coordinatePair();
        if (value == 0) {
            pair.i = (char)(N - 1);
            pair.j = (char)(N - 1);
        } else {
            pair.i = (char)((value - 1) / N);
            pair.j = (char)((value - 1) % N);
        }
        return pair;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        // compare each value on board to corresponding value on goal
        // add up horizontal and vertical distance
        // return cached value if available. manhattan inits to -1
        if (manhattan != -1) {
            return manhattan;
        }

        int distance = 0;

        for (char i = 0; i < N; i ++) {
           for (char j = 0; j < N; j++) {
               // compare board coordinates to goal coordinates for each board/goal value
               char boardValue = board[i][j];
               coordinatePair goalCoordinates = coordinatesForGoalValue(boardValue);
               distance += Math.abs(goalCoordinates.i - i) + Math.abs(goalCoordinates.j - j);
           }
        }
        manhattan = distance; // cache distance
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {

        // copy our board
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 2; j < N; j++) {
                blocks[i][j] = board[i][j];
            }
        }
        // switch first two blocks
        blocks[0][0] = board[0][1];
        blocks[0][1] = board[0][0];

        return new Board(blocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())  return false;
        Board that = (Board)y;
        return Arrays.deepEquals(this.board, that.board);
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
