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
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nMoves:" + moves);
            stringBuilder.append("\nManhattan:" + board.manhattan());
            stringBuilder.append("\nPriority:" + priority);
            stringBuilder.append("\nBoard:" + board);
            return stringBuilder.toString();
        }
    }
    // If initial board's twin reaches goal, intial board is not solvable
    private boolean isSolvable;
    // moves required for initial board to reach goal
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

        // loop until one of the queues is solved
        while (!searchPriorityQueue.min().board.isGoal()
                && !twinPriorityQueue.min().board.isGoal()) {
            //Then, delete from the priority queue the search node
            // with the minimum priority
            Node min = searchPriorityQueue.delMin();
            Node twinMin = twinPriorityQueue.delMin();
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
            }
            for (Board twinNeighbor : twinNeighbors) {
                if (twinMin.previous != null && twinNeighbor.equals(twinMin.previous.board)) {
                    continue;
                }
                Node twinNext = new Node(twinNeighbor, twinMin.moves + 1, twinMin);
                twinPriorityQueue.insert(twinNext);
            }
        }

        if (searchPriorityQueue.min().board.isGoal()) {
            moves = searchPriorityQueue.min().moves;
            isSolvable = true;

        }
        if (twinPriorityQueue.min().board.isGoal()) {
            moves = -1;
            isSolvable = false;

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
        // if no solution, return null
        if (!isSolvable) return null;
        // create solution stack
        Stack<Board> solution = new Stack<Board>();
        // start with solution node
        Node node = searchPriorityQueue.min();
        // loop our way back to first node, adding boards to solution
        while (node != null) {
            solution.push(node.board);
            node = node.previous;
        }

        return solution;
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
        // board from input blocks
        Board board = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(board);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board eachBoard : solver.solution())
                StdOut.println(eachBoard);
        }
    }
}
