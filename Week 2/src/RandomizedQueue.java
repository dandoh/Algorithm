import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s;
	private int N;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		s = (Item[]) new Object[2];
		N = 0;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	@SuppressWarnings("unchecked")
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (N + 1 == s.length) {
			Item copy[] = (Item[]) new Object[2 * s.length];
			for (int i = 1; i <= N; i++) {
				copy[i] = s[i];
			}
			s = copy;
		}
		s[++N] = item;
		int r = StdRandom.uniform(N) + 1;
		// swap
		Item tmp = s[r];
		s[r] = s[N];
		s[N] = tmp;

	}

	@SuppressWarnings("unchecked")
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = s[N--];
		s[N + 1] = null;
		
		if (N > 0 && N < s.length / 4) {
			Item copy[] = (Item[]) new Object[s.length / 2];
			for (int i = 1; i <= N; i++) {
				copy[i] = s[i];
			}
			s = copy;
		}

		return item;
	}

	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int r = StdRandom.uniform(N) + 1;
		return s[r];
	}

	// ITERATOR and NODE
	@Override
	public Iterator<Item> iterator() {
		return new RandomizeQueueIterator(N, s);
	}

	private class RandomizeQueueIterator implements Iterator<Item> {
		private Item[] ri;
		private int current;
		private int size;

		@SuppressWarnings("unchecked")
		public RandomizeQueueIterator(int N, Item[] item) {
			current = 1;
			size = N;
			if (size > 0) {
				ri = (Item[]) new Object[size + 1];
				for (int i = 1; i <= size; i++) {
					ri[i] = item[i];
					
					//SHUFFLE
					int r = StdRandom.uniform(i) + 1;
					Item tmp = ri[r];
					ri[r] = ri[i];
					ri[i] = tmp;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return current <= size;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return ri[current++];
		}

		@Override
		public void remove() {
			/* Not supported */
			throw new UnsupportedOperationException();
		}

	}

}
