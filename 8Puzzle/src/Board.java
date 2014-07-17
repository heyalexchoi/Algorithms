/**
 * Created by alexchoi1 on 7/17/14.
 */

public class Board {

    // array representation of Board
    private block[] board;
    // array representation of Goal
    private final block[] goal;
    // length/width of Board
    private final int N;

    // represents board block for both Board and Goal
    private class block {
        int x;
        int y;
        int value;
    }

    // returns 1-D array index for 2-D array index pair
    // row i, column j
    private int indexForCoordinates(int i, int j) {
        return (i * N) + j;
    }

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {

        N = blocks.length;

        this.board = new block[N*N];
        this.goal = new block[N*N];

        // for each row i
        for (int i = 0; i < N; i ++) {
            // for each column j
            for (int j = 0; j < N; j++) {

                int index = indexForCoordinates(i,j);

                block boardBlock = new block();
                boardBlock.x = j;
                boardBlock.y = i;
                boardBlock.value = blocks[i][j];
                board[index] = boardBlock;

                block goalBlock = new block();
                goalBlock.x = j;
                goalBlock.y = i;

                if (index < N * N - 1) {
                    goalBlock.value = index+1;
                }
                else {
                    goalBlock.value = 0;
                }

                goal[index] = goalBlock;
            }
        }
  }



    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;

        return count;
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
