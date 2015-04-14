import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	public Deque() {
		size = 0;
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		size++;

		Node newNode = new Node();
		newNode.item = item;

		if (isEmpty()) {
			first = newNode;
			last = first;
		} else {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
		}
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();
		size++;

		Node newNode = new Node();
		newNode.item = item;

		if (isEmpty()) {
			last = newNode;
			first = last;
		} else {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		}

	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		size--;

		Item item = first.item;
		first = first.next;
		if (first != null)
			first.previous = null;
		else
			last = null;

		return item;

	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		size--;

		Item item = last.item;
		last = last.previous;

		if (last != null)
			last.next = null;
		else
			first = null;

		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator(first);
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current;

		public DequeIterator(Node first) {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;

			return item;
		}

		@Override
		public void remove() {
			/* Not supported */
			throw new UnsupportedOperationException();
		}

	}

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

}
