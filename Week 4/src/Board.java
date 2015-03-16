import java.util.ArrayList;

public class Board {
	private final int[][] a;
	private final int N;
	
	public Board(int[][] blocks) {
		N = blocks.length;
		a = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a[i][j] = blocks[i][j];
			}
		}
	}
	public int dimension() {
		return N;
	}
	public int hamming() { 
		int hamming = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (a[i][j] == 0) continue;
				
				if (a[i][j] != i * N + j + 1)
					hamming++;
			}
		}
		return hamming;
	}
	public int manhattan() { 
		int mht = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (a[i][j] == 0) continue;
				
				int righti = (a[i][j] + N - 1) / N - 1;
				int rightj = a[i][j] - righti * N - 1;
				
				mht += Math.abs(i - righti) + Math.abs(j - rightj);
			}
		}
		
		return mht;
	}
	
	public boolean isGoal() { 
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N - 1 && j == N - 1) continue;
				
				if (a[i][j] != i * N + j + 1)
					return false;
			}
		}
		
		return true;
	}
	public Board twin() {
		int[][] twin = new int[N][N];
		for (int i = 0; i < N; i++) 
			for (int j = 0; j < N; j++) 
				twin[i][j] = a[i][j];
		
		if (twin[0][1] == 0 || twin[0][0] == 0) {
			twin[1][0] = a[1][1];
			twin[1][1] = a[1][0];
		} else {
			twin[0][1] = a[0][0];
			twin[0][0] = a[0][1];
		}
		
		return new Board(twin);
	}
	
	public boolean equals(Object y) {
		if (y == null) return false;
		if (y.getClass() != getClass()) 
			return false;
		
		Board that = (Board) y;
		if (this.dimension() != that.dimension()) 
			return false;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) 
				if (a[i][j] != that.a[i][j])
					return false;
		}
		
		return true;
	}
	public Iterable<Board> neighbors() {
		ArrayList<Board> nb = new ArrayList<Board>();
		int i, j = 0;
		
		outter:
		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) {
				if (a[i][j] == 0)
					break outter;
			}
		}
		
		int[] dr = {1, 0, 0, -1};
		int[] dc = {0, -1, 1, 0};
		
		for (int k = 0; k < 4; k++) {
			int r = i + dr[k];
			int c = j + dc[k];
			
			if (r < 0 || r == N) continue;
			if (c < 0 || c == N) continue;
			
			int[][] newa = new int[N][N];
			for (int p = 0; p < N; p++)
				for (int q = 0; q < N; q++)
					newa[p][q] = a[p][q];
			newa[i][j] = newa[r][c];
			newa[r][c] = 0;
			
			nb.add(new Board(newa));
		}
		
		return nb;
	}
	public String toString() {
		String s = String.valueOf(N);
		s += "\n";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s += " " + a[i][j];
			}
			s += "\n";
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] a = new int[N][N];
		
		for (int i = 0; i < N; i++) 
			for (int j = 0; j < N; j++) 
				a[i][j] = in.readInt();
		
		Board x = new Board(a);
		StdOut.println(x);
		for (Board y : x.neighbors())
			StdOut.println(y);
	}
	
	
	
}
