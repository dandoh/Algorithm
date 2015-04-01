
public class WeightedGraph {
	private int V;
	private Bag<DirectedEdge>[] adj;
	private Bag<DirectedEdge> edges;
	
	@SuppressWarnings("unchecked")
	public WeightedGraph(int V) {
		this.V = V;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		
		for (int i = 0; i < V; i++) {
			adj[i] = new Bag<DirectedEdge>();
		}
		
		edges = new Bag<DirectedEdge>();
	}
	
	public WeightedGraph(In in) {
		this(in.readInt());
		
		int E = in.readInt();
		for (int i = 0; i < E; i++) {
			String a = in.readString();
			char temp = a.charAt(0);
			int v = temp - 'A';
			
			String b = in.readString();
			temp = b.charAt(0);
			int w = temp - 'A';
			
			double weight = in.readDouble();
			DirectedEdge e = new DirectedEdge(v, w, weight);
			addEdge(v, e);
		}
	}
	
	public int V() { return V; }
	
	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}
	
	public void addEdge(int v, DirectedEdge e) {
		adj[v].add(e);
	}
	
	public Iterable<DirectedEdge> edges() {
		return edges;
	}
}
