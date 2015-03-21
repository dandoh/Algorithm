
public class Bipartite {	
	private Graph G;
	private boolean[] marked;
	private int[] group;
	private boolean flag;
	
	public Bipartite(Graph G) {
		this.G = G;
		this.marked = new boolean[G.V()];
		this.flag = false;
		for (int i = 0; i < G.V(); i++) {
			if (flag) break;
			if (!marked[i]) 
				dfs(i);
		}
	}
	
	public void dfs(int s) {
		if (flag) return;
		marked[s] = true;
		for (int v : G.adj(s)) {
			if (marked[v]) {
				if (group[v] == group[s])  {
					flag = true;
					return;
				}
			} else {
				group[v] = 1 - group[s];
				dfs(v);
			}
		}
	}
	
	public boolean isBipartite() {
		return !flag;
	}
	public static void main(String[] args) {
		
	}
}
