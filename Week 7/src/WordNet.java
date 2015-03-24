import java.util.ArrayList;

public class WordNet {
	
	
	private final Digraph G;
	private final ST<String, Bag<Integer>> st;
	private final ArrayList<String> idList;
	private final SAP sap;
	
	// constructor takes the name of the two input files
	@SuppressWarnings("unused")
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new NullPointerException();
		st = new ST<String, Bag<Integer>>();
		idList = new ArrayList<String>();
		In in = new In(synsets);
		
		int size = 0;
		while (in.hasNextLine()) {
			String[] a = in.readLine().split(",");
			int index = Integer.parseInt(a[0]);
			
			
			String[] b = a[1].split(" ");
			idList.add(a[1]);
			for (int i = 0; i < b.length; i++) {
				if (!st.contains(b[i])) {
					Bag<Integer> bag = new Bag<Integer>();
					bag.add(index);
					st.put(b[i], bag);
				} else {
					st.get(b[i]).add(index);
				}
			}
			size++;
		}
		
		in = new In(hypernyms);
		G = new Digraph(size);
		
		while (in.hasNextLine()) {
			String[] a = in.readLine().split(",");
			int source = Integer.parseInt(a[0]);
			for (int i = 1; i < a.length; i++) {
				G.addEdge(source, Integer.parseInt(a[i]));
			}
		}
		
		//check for cycle and two root
		DirectedCycle dc = new DirectedCycle(G);
		if (dc.hasCycle()) 
			throw new IllegalArgumentException();
		
		int count = 0;
		for (int i = 0; i < G.V(); i++) {
			int sum = 0;
			for (int j : G.adj(i)) 
				sum++;
			if (sum == 0) 
				count++;
			
			if (count > 1)
				throw new IllegalArgumentException();
		}
		
		sap = new SAP(G);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		Queue<String> queue = new Queue<String>();
		for (String a : st) 
			queue.enqueue(a);
		return queue;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null) 
			throw new NullPointerException();
		return st.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		
		if (!isNoun(nounA) || !isNoun(nounB)) {
			throw new IllegalArgumentException();
		}
		
		return sap.length(st.get(nounA), st.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		return idList.get(sap.ancestor(st.get(nounA), st.get(nounB)));
	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wn = new WordNet(args[0], args[1]);
		for (String noun : wn.nouns()) {
			for (int i : wn.st.get(noun)) 
				StdOut.print(i + " ");
			StdOut.println();
		}
	}
}