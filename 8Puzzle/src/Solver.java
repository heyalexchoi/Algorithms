/**
 * Created by alexchoi1 on 7/17/14.
 */


public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return new Stack<Board>();
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j=0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board board = new Board(blocks);
        StdOut.print(board.toString());
        StdOut.println("dimension: "+board.dimension());
        StdOut.println("hamming: "+board.hamming());
        StdOut.println("manhattan: "+board.manhattan());
        StdOut.println("isgoal: "+board.isGoal());
        StdOut.println("twin: "+board.twin().toString());
        StdOut.println("equals twin: "+board.equals(board.twin()));
        StdOut.println("neighbors: "+board.neighbors());
        Iterable<Board> neighbors = board.neighbors();
        for (Board eachBoard : neighbors) {
            StdOut.println("neighbor equals twin?"+ eachBoard.equals(board.twin()));
        }




    }
}
