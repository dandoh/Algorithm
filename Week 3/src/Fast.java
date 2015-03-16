import java.util.Arrays;


public class Fast {
	public static void main(String[] args) {
		In in = new In(args[0]);
		
		
		int N = in.readInt();
		Point[] p = new Point[N];
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		
		for (int i = 0; i < N; i++) {
			p[i] = new Point(in.readInt(), in.readInt());
			p[i].draw();
		}
		
		for (int i = 0; i < N; i++) {
			Arrays.sort(p);
			Arrays.sort(p, i, N, p[i].SLOPE_ORDER);
			Arrays.sort(p, 0, i, p[i].SLOPE_ORDER);
			
			boolean hasLeft = false;
			int left = 0, right = 0;
			
			for (int j = i + 1; j < N; j++) {
				
				int lo = -1, hi = i;
				boolean flag = false;
				while (lo + 1 < hi) {
					int mid = (lo + hi) / 2;
					
					if (p[i].slopeTo(p[mid]) == p[i].slopeTo(p[j])) {
						flag = true;
						break;
					} 
					
					if (p[i].slopeTo(p[mid]) > p[i].slopeTo(p[j])) 
						hi = mid;
					else 
						lo = mid;
				}
				
				if (flag) continue;
				
				if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[j - 1])) {
					if (!hasLeft) {
						hasLeft = true;
						left = j - 1;
					}
					
					right = j;
					
					if (hasLeft && j == N - 1 && right - left > 1) {
						System.out.print(p[i]);
						for (int k = left; k <= right; k++) 
							System.out.print(" -> " + p[k]);
						System.out.println();
						p[i].drawTo(p[right]);
					}
				} else {
					if (hasLeft && right - left > 1) {
						System.out.print(p[i]);
						for (int k = left; k <= right; k++) 
							System.out.print(" -> " + p[k]);
						System.out.println();
						p[i].drawTo(p[right]);
					}
					hasLeft = false;
				}
				
			}
		}
		
		
	}

}
