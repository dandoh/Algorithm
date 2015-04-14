import java.util.Arrays;

public class Brute {
	public static void main(String[] args) {

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] d = new Point[N];
		

		for (int i = 0; i < N; i++) {
			d[i] = new Point(in.readInt(), in.readInt());
			d[i].draw();
		}
		
		Arrays.sort(d);

		
		for (int p = 0; p < N - 3; p++) {
			for (int q = p + 1; q < N - 2; q++) {
				for (int r = q + 1; r < N - 1; r++) {
					for (int s = r + 1; s < N; s++) {
						double slope1 = d[p].slopeTo(d[q]);
						double slope2 = d[q].slopeTo(d[r]);
						double slope3 = d[r].slopeTo(d[s]);
						if (slope1 == slope2 && slope2 == slope3) {
							System.out.println(d[p] + " -> " + d[q] + " -> " + d[r] + " -> " + d[s]);
							d[p].drawTo(d[s]);
						}
					}
				}
			}
		}

	}
}
