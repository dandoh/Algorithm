public class Percolation {
	private int size;
	private int TOP, BOTTOM;
	private int[] dx = {-1, 0, 0, 1};
	private int[] dy = {0, 1, -1, 0};
	private boolean[][] opened;
	private WeightedQuickUnionUF uf;

	public Percolation(int N) {
		if (N <= 0)
			throw new IllegalArgumentException();
		
		size = N;
		TOP = 0;
		BOTTOM = N * N + 1;
		opened = new boolean[N + 1][N + 1];
		
		uf = new WeightedQuickUnionUF(N * N + 2);

	}

	public void open(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		opened[i][j] = true;
		
		
		
		for (int k = 0; k < 4; k++) {
			int i1 = i + dx[k];
			int j1 = j + dy[k];
			
			if (i1 <= 0 || i1 > size || j1 <= 0 || j1 > size)
				continue;
			
			int oldp = size * (i - 1) + j;
			int newp = size * (i1 - 1) + j1;
			if (isOpen(i1, j1))
				uf.union(oldp, newp);
		}
		
		if (i == 1) 
			uf.union(j, TOP);
		if (i == size)
			uf.union(size * (size - 1) + j, BOTTOM);
		

	}

	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		return opened[i][j];
	}

	public boolean isFull(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		return (uf.connected(TOP, size * (i - 1) + j) && isOpen(i, j));
	}

	public boolean percolates() {
		return uf.connected(TOP, BOTTOM);
	}

}
