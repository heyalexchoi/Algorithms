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
        //StdOut.println("fileName"+fileName);
        int N = in.readInt();
        //StdOut.println("N"+N);

        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j=0; j < N; j++) {
                blocks[i][j] = in.readInt();
                //StdOut.println("\ni: "+i+"\nj: "+j+"\nvalue: "+blocks[i][j]);
            }
        }

        Board board = new Board(blocks);
        StdOut.print(board.toString());
    }
}
