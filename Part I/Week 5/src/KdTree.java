
public class KdTree {
	private static boolean X = true;
	private int size;
	private Node root;
	
	public KdTree() {
		root = null;
		size = 0;
	}
	public boolean isEmpty() { return size == 0; }
	public int size() { return size; }
	public void insert(Point2D p) {
		if (p == null) {
			throw new NullPointerException(); 
		}
		
		root = insert(null, root, p, X, true);
	}
	
	private boolean goLeft(Point2D stay, Point2D key, boolean align) {
		if (align == X) {
			return key.x() < stay.x();
		} else {
			return key.y() < stay.y();
		}
	}
	
	private Node insert(Node parent, Node node, Point2D p, boolean align, boolean less) {
		if (node == null) {
			size++;
			return new Node(parent, p, align, less);
		}
		
		if (p.equals(node.p)) return node;
		
		if (goLeft(node.p, p, align)) 
			node.left = insert(node, node.left, p, !align, true);
		else
			node.right = insert(node, node.right, p, !align, false);
		
		return node;
	}
	public boolean contains(Point2D p) {
		if (p == null) 
			throw new NullPointerException();
		return contains(root, p);
	}
	
	private boolean contains(Node node, Point2D p) {
		if (node == null) return false;
		
		if (p.equals(node.p)) return true;
		
		if (goLeft(node.p, p, node.align)) {
			return contains(node.left, p);
		} else {
			return contains(node.right, p);
		}
		
	}
	
	private Iterable<Node> set() {
		Queue<Node> queue = new Queue<Node>();
		add(root, queue);
		
		return queue;
	}
	
	private void add(Node node, Queue<Node> queue) {
		if (node == null) return;
		queue.enqueue(node);
		add(node.left, queue);
		add(node.right, queue);
	}
	
	public void draw() {
		for (Node node : set()) {
			System.out.println(node.p);
			StdDraw.show(0);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			node.p.draw();
			StdDraw.setPenRadius();
			if (node.align == X) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
			} else {
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
			}
			StdDraw.show(0);
		}
	}
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		Queue<Point2D> queue = new Queue<Point2D>();
		range(root, rect, queue);
		
		return queue;
	}
	
	private void range(Node node, RectHV rect, Queue<Point2D> queue) {
		if (node == null) return;
		if (!rect.intersects(node.rect)) return;
		if (rect.contains(node.p))
			queue.enqueue(node.p);
		range(node.left, rect, queue);
		range(node.right, rect, queue);
	}
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}
		Point2D cl = null;
		if (root != null) 
			cl = root.p;
		cl = nearest(p, cl, root);
		
		return cl;
	}
	
	private Point2D nearest(Point2D p, Point2D closest, Node node)  {
		if (node == null || closest == null) return closest;
		
		if (node.rect.distanceTo(p) > closest.distanceTo(p)) return closest;
		
		if (node.p.distanceTo(p) < closest.distanceTo(p)) {
			closest = node.p;
		}
		
		if (node.left != null && node.left.rect.contains(p)) {
			closest = nearest(p, closest, node.left);
			closest = nearest(p, closest, node.right);
		} else {
			closest = nearest(p, closest, node.right);
			closest = nearest(p, closest, node.left);
		}
		
		return closest;
		
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		KdTree tree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			
			tree.insert(new Point2D(x, y));
		}
		tree.draw();
	}
	
	private static class Node {
		private Point2D p;
		private boolean align;
		private Node left;
		private Node right;
		private RectHV rect;
		
		public Node(Node parent, Point2D p, boolean align, boolean less) {
			this.p = p;
			this.align = align;
			if (parent == null)
				rect = new RectHV(0, 0, 1, 1);
			
			else if (align == X) {
				if (less) {
					rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
				} else {
					rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
				}
			} else {
				if (less) {
					rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
				} else {
					rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
				}
			}
			
		}
	}
}
