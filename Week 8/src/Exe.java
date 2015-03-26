
public class Exe {
	public static void main(String[] args) {
		In in = new In(args[0]);
		WeightedGraph G = new WeightedGraph(in);
		
		char c = args[1].charAt(0);
		
		int s = c - 'A';
		
		Dijkstra dij = new Dijkstra(G, s);
		double[] distance = dij.distance();
		
		for (int i = 0; i < G.V(); i++) {
			if (distance[i] < Double.POSITIVE_INFINITY) 
				StdOut.printf("%.0f ", distance[i]);
			else 
				StdOut.printf("- ");
		}
		
	}	
}
