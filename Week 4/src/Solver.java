import java.util.LinkedList;



public class Solver {
	private Node solution;
	private int solve;
	private int moves;
	public Solver(Board initial) {
		if (initial == null) {
			throw new NullPointerException();
		}
		
		MinPQ<Node> pq1 = new MinPQ<Node>();
		MinPQ<Node> pq2 = new MinPQ<Node>();
		pq1.insert(new Node(initial, 0, null));
		pq2.insert(new Node(initial.twin(), 0, null));
		
		while (true) {
			Node n1 = pq1.delMin();
			if (n1.board.isGoal()) {
				solve = 1;
				moves = n1.count;
				solution = n1;
				break;
			}
			else for (Board nb : n1.board.neighbors()) {
				boolean flag = true;
				Node x = n1;
				while (x != null) {
					if (nb.equals(x.board)) {
						flag = false;
						break;
					}
					x = x.previous;
				}
				if (flag)
					pq1.insert(new Node(nb, n1.count + 1, n1));
			}
			
			Node n2 = pq2.delMin();
			if (n2.board.isGoal()) {
				solve = 2;
				moves = -1;
				solution = null;
				break;
			}
			else for (Board nb : n2.board.neighbors()) {
				boolean flag = true;
				Node x = n2;
				while (x != null) {
					if (nb.equals(x.board)) {
						flag = false;
						break;
					}
					x = x.previous;
				}
				if (flag)
					pq2.insert(new Node(nb, n2.count + 1, n2));
			}
			
			
		}
		
	}
	public boolean isSolvable() { return solve == 1; }
	public int moves() { return moves; }
	public Iterable<Board> solution() {
		if (solution == null)
			return null;
		LinkedList<Board> so = new LinkedList<Board>();
		Node x = solution;
		
		while (x != null) {
			so.addFirst(x.board);
			x = x.previous;
		}
		
		return so;
	}
	
	private class Node implements Comparable<Node> {
		private int count;
		private Board board;
		private int prior;
		private Node previous;
		
		public Node(Board board, int count, Node pre) {
			this.board = board;
			this.count = count;
			this.prior = board.manhattan() + count;
			this.previous = pre;
		}
		
		@Override
		public int compareTo(Node that) {
			if (prior < that.prior) return -1;
			else if (prior > that.prior) return 1;
			else return 0;
		}
		
	}
	public static void main(String[] args) {
		// create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	    
	}
}
