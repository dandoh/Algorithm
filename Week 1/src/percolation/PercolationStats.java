public class PercolationStats {
	private int size, times;
	private double[] res;
	private int p, q;
	private double count;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) 
			throw new IllegalArgumentException();
		
		size = N;
		times = T;
		
		res = new double[T];
		
		for (int i = 0; i < T; i++) {
			count = 0;

			Percolation percolation = new Percolation(N);

			while (!percolation.percolates()) {
				p = StdRandom.uniform(size) + 1;
				q = StdRandom.uniform(size) + 1;

				if (!percolation.isOpen(p, q)) {
					percolation.open(p, q);
					count++;
				}
			}

			res[i] = count / (double) (N * N);

		}
	}

	public double mean() {
		return StdStats.mean(res);
	}

	public double stddev() {
		return StdStats.stddev(res);
	}

	public double confidenceLo() {
		return mean() - (1.96 * stddev()) / Math.sqrt(times);
	}

	public double confidenceHi() {
		return mean() + (1.96 * stddev()) / Math.sqrt(times);
	}

	public static void main(String[] args) {
		
	}

}
