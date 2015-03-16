public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> rqueue = new RandomizedQueue<String>();

		int N = 0;
		String[] item = new String[1];

		while (!StdIn.isEmpty()) {
			if (N == item.length) {
				String[] copy = new String[2 * item.length];
				for (int i = 0; i < N; i++)
					copy[i] = item[i];
				item = copy;
			}
			item[N++] = StdIn.readString();
			
			int r = StdRandom.uniform(N);
			String temp = item[N - 1];
			item[N - 1] = item[r];
			item[r] = temp;
		}
				
		int k = Integer.valueOf(args[0]);
		
		for (int i = 0; i < k; i++) {
			int r = StdRandom.uniform(N);
			rqueue.enqueue(item[r]);
			
			item[r] = item[N - 1];
			item[N - 1] = null;
			N--;
		}
		
		for (String s : rqueue)
			StdOut.println(s);
	}
}
