public class SAP {

	private final Digraph G;
	private final boolean[] vMarked, wMarked;
	private final int[] vDistance, wDistance;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph diG) {
		G = new Digraph(diG.V());
		for (int i = 0; i < diG.V(); i++) {
			for (int j : diG.adj(i)) {
				G.addEdge(i, j);
			}
		}
		vMarked = new boolean[G.V()];
		wMarked = new boolean[G.V()];
		vDistance = new int[G.V()];
		wDistance = new int[G.V()];
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		Queue<Integer> vq = new Queue<Integer>();
		vq.enqueue(v);
		Queue<Integer> wq = new Queue<Integer>();
		wq.enqueue(w);
		
		int[] res = solve(vq, wq);
		
		return res[0];
	}
	
	private void invalidate(Iterable<Integer> vList, Iterable<Integer> wList) {
		if (vList == null || wList == null) {
			throw new NullPointerException();
		}
		for (int i : vList) 
			if (i < 0 || i >= G.V()) 
				throw new IndexOutOfBoundsException();
		for (int i : wList) 
			if (i < 0 || i >= G.V()) 
				throw new IndexOutOfBoundsException();
	}

	private int[] solve(Iterable<Integer> vList, Iterable<Integer> wList) {
		invalidate(vList, wList);
		int[] result = new int[2];
		
		bfs(vList, vMarked, vDistance);
		bfs(wList, wMarked, wDistance);
		
		int minDistance = Integer.MAX_VALUE;
		int ancestral = -1;
		
		for (int i = 0; i < G.V(); i++) {
			if (vMarked[i] && wMarked[i]) {
				int temp = vDistance[i] + wDistance[i];
				if (temp < minDistance) {
					minDistance = temp;
					ancestral = i;
				}
			}
		}
		
		if (minDistance == Integer.MAX_VALUE)
			minDistance = -1;
		
		result[0] = minDistance;
		result[1] = ancestral;
		
		return result;
	}

	private void bfs(Iterable<Integer> list, boolean[] marked, int[] distance) {
		for (int i = 0; i < marked.length; i++) 
			marked[i] = false;
		Queue<Integer> queue = new Queue<Integer>();
		for (int v : list) {
			marked[v] = true;
			distance[v] = 0;
			queue.enqueue(v);
		}
		
		while (!queue.isEmpty()) {
			int v = queue.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					marked[w] = true;
					distance[w] = distance[v] + 1;
					queue.enqueue(w);
				}
			}
		}
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		Queue<Integer> vq = new Queue<Integer>();
		vq.enqueue(v);
		Queue<Integer> wq = new Queue<Integer>();
		wq.enqueue(w);
		
		int[] res = solve(vq, wq);
		
		return res[1];
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int[] res = solve(v, w);
		
		return res[0];
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int[] res = solve(v, w);
		
		return res[1];
	}

	// do unit testing of this class
	public static void main(String[] args) {
		
		Digraph G = new Digraph(new In(args[0]));
		SAP sap = new SAP(G);
		StdOut.println(sap.ancestor(1, 5));
		
	}
}