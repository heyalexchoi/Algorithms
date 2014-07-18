/**
 * Created by alexchoi1 on 7/17/14.
 */
import java.util.Arrays;


public class Board {
    // class used to store coordinate pairs
    private class CoordinatePair {
        char i;
        char j;
    }
    // array representation of Board
    private final char[][] board;
    // length/width of Board
    private final char N;
    // cached manhattan distance value. init -1, to mark that distance has not been calculated yet
    private int manhattan;
    // cached location of the blank square
    private CoordinatePair blankSquare;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = (char)blocks.length;
        board = new char[N][N];
        manhattan = -1;
        blankSquare = new CoordinatePair();

        // for each row i
        for (char i = 0; i < N; i++) {
            // for each column j
            for (char j = 0; j < N; j++) {
                board[i][j] = (char)blocks[i][j];
                // cache blank square location
                if (blocks[i][j] == 0) {
                    blankSquare.i = i;
                    blankSquare.j = j;
                }
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
                if (board[i][j] != 0 && board[i][j] != goal(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // function used to calculate manhattan distance
    // returns inverse of goal(): the i,j coordinate pair for a goal value
    private CoordinatePair coordinatesForGoalValue(char value) {
        CoordinatePair pair = new CoordinatePair();
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
                if (boardValue == 0) { continue; } // don't do it for zero
                CoordinatePair goalCoordinates = coordinatesForGoalValue(boardValue);
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
    // (BLANK DOES NOT COUNT)
    public Board twin() {
        // copy our board
        int[][] blocks = copyBoard();
        // if neither of the first two blocks are blank,
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            // switch first two blocks
            blocks[0][0] = board[0][1];
            blocks[0][1] = board[0][0];
        } else {
            // otherwise, switch first two blocks on second row
            blocks[1][0] = board[1][1];
            blocks[1][1] = board[1][0];
        }
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

    private int[][] copyBoard() {
        int[][] boardCopy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        // copy our board as int arrays

        // blank square not left edge
        if (blankSquare.j > 0) {
            // create board with blank switched with left neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.i][blankSquare.j] = board[blankSquare.i][blankSquare.j-1];
            blocks[blankSquare.i][blankSquare.j-1] = board[blankSquare.i][blankSquare.j];
            stack.push(new Board(blocks));
        }
        // blank square not right edge
        if (blankSquare.j < N-1) {
            // create board with blank switched with right neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.i][blankSquare.j] = board[blankSquare.i][blankSquare.j+1];
            blocks[blankSquare.i][blankSquare.j+1] = board[blankSquare.i][blankSquare.j];
            stack.push(new Board(blocks));
        }
        // blank square not top edge
        if (blankSquare.i > 0) {
            // create board with blank switched with top neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.i][blankSquare.j] = board[blankSquare.i-1][blankSquare.j];
            blocks[blankSquare.i-1][blankSquare.j] = board[blankSquare.i][blankSquare.j];
            stack.push(new Board(blocks));
        }
        // blank square not bottom edge
        if (blankSquare.i < N-1) {
            // create board with blank switched with bottom neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.i][blankSquare.j] = board[blankSquare.i+1][blankSquare.j];
            blocks[blankSquare.i+1][blankSquare.j] = board[blankSquare.i][blankSquare.j];
            stack.push(new Board(blocks));
        }

        return stack;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int)N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringBuilder.append(String.format("%d ", (int)board[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
