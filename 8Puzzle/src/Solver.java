/**
 * Created by alexchoi1 on 7/17/14.
 */


public class Solver {

    // search node
    private class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final Node previous;
        final int priority;
        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + board.manhattan();
        }
        public int compareTo(Node that) {
            if (this.priority > that.priority) {
                return 1;
            }
            if (that.priority > this.priority) {
                return -1;
            }
            return 0;
        }
    }

    private boolean isSolvable;
    private int moves;
    // priority queue of search nodes
    private MinPQ<Node> searchPriorityQueue;
    // priority queue for searching twin of initial board
    // to determine solvability of initial
    private MinPQ<Node> twinPriorityQueue;






    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        searchPriorityQueue = new MinPQ<Node>();
        twinPriorityQueue = new MinPQ<Node>();
        //First, insert the initial search node into a priority queue.
        searchPriorityQueue.insert(new Node(initial, 0, null));
        twinPriorityQueue.insert(new Node(initial.twin(), 0, null));
        /*
        // loop until one of the queues is solved
        while (!searchPriorityQueue.min().board.isGoal()
                && !twinPriorityQueue.min().board.isGoal()) {
                */
        for (int i = 0; i < 5; i++) {
            //Then, delete from the priority queue the search node
            // with the minimum priority
            Node min = searchPriorityQueue.delMin();
            Node twinMin = twinPriorityQueue.delMin();
            StdOut.println("--------------------------------------------------" +
                    "MIN\n" + "PRIORITY: " + min.priority
                    + "\nMOVES: " + min.moves
                    + "\nMANHATTAN: " + min.board.manhattan()
                    + "\n" + min.board.toString());
            // , and insert onto the priority queue all neighboring search nodes
            Iterable<Board> neighbors = min.board.neighbors();
            Iterable<Board> twinNeighbors = twinMin.board.neighbors();
            for (Board neighbor : neighbors) {
                // don't enqueue a neighbor if its board is the
                // same as the board of the previous search node.
                if (min.previous != null && neighbor.equals(min.previous.board)) {
                    continue;
                }
                Node next = new Node(neighbor, min.moves + 1, min);
                searchPriorityQueue.insert(next);
                StdOut.println("INSERT NODE\n" + "PRIORITY: " + next.priority
                        + "\nMOVES: " + next.moves
                        + "\nMANHATTAN: " + next.board.manhattan()
                        + "\n" + next.board.toString());
            }
            for (Board twinNeighbor : twinNeighbors) {
                if (twinMin.previous != null && twinNeighbor.equals(twinMin.previous.board)) {
                    continue;
                }
                Node twinNext = new Node(twinNeighbor, twinMin.moves + 1, twinMin);
                twinPriorityQueue.insert(twinNext);
            }
        }
        StdOut.println("FINISHED SOLVING");
        if (searchPriorityQueue.min().board.isGoal()) {
            StdOut.println("board was solved");
        }
        if (twinPriorityQueue.min().board.isGoal()) {
            StdOut.println("twin board was solved");
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
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
        /*
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
        */


        // solve the puzzle


        Solver solver = new Solver(board);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board eachBoard : solver.solution())
                StdOut.println(board);
        }




    }
}
