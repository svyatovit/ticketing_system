package pqueue;

public class PQueue<T> {

	private PQueueItem<T> head;

	public static enum ORDER {
		ASC, DESC;
	}

	public static ORDER DEFAULT_ORDER = ORDER.DESC;
	private ORDER order;

	/**
	 * The default constructor for a PQueue, with the default order for priorities
	 */
	public PQueue() {
		this.order = DEFAULT_ORDER;
		head = null;
	}

	/**
	 * The constructor to make a new PQueue with a given order
	 * 
	 * @param order
	 */
	public PQueue(ORDER order) {
		this.order = order;
		head = null;
	}

	/**
	 * Remove and return data from the item at the front of the queue
	 * 
	 * @return
	 */
	public T pop() {

		T data = head.getData();
		PQueueItem<T> next = head.getNext();
		head = next;

		return data;
	}

	/**
	 * Return the data from the item at the front of the queue, without removing the
	 * item itself
	 * 
	 * @return
	 */
	public T peek() {
		return head.getData();
	}

	/**
	 * Remove and return the item at the front of the queue
	 * 
	 * @return
	 */
	public PQueueItem<T> popItem() {

		PQueueItem<T> prev_head = head;
		head = head.getNext();

		return prev_head;

	}

	/**
	 * Return the item at the front of the queue without removing it
	 * 
	 * @return
	 */
	public PQueueItem<T> peekItem() {
		// throw new UnsupportedOperationException("Method not implemented");

		return head;
	}

	/**
	 * Insert a new item into the queue, which should be put in the right place
	 * according to its priority. That is, is order == ASC, then the new item should
	 * appear in the queue before all items with a HIGHER priority. If order ==
	 * DESC, then the new item should appear in the queue before all items with a
	 * LOWER priority.
	 * 
	 * @param data
	 * @param priority
	 */
	public void insert(T data, int priority) {
		if (head == null) {
			head = new PQueueItem<T>(data, priority);
			head.setNext(null);
			return;
		}

		PQueueItem<T> current = head;
		PQueueItem<T> prev = null;

		// Searching the right place in the ascending order
		if (this.order == ORDER.ASC) {
			while (current != null && current.getPriority() < priority) {
				prev = current;
				current = current.getNext();
			}
		}

		// Searching the right place in the descending order
		if (this.order == ORDER.DESC) {
			while (current != null && current.getPriority() > priority) {
				prev = current;
				current = current.getNext();
			}
		}
		if (prev != null) {
			// Insert between prev and current
			prev.setNext(new PQueueItem<T>(data, priority));
			prev.getNext().setNext(current);
		} else {
			// Insert at the beginning
			head = new PQueueItem<T>(data, priority);
			head.setNext(current);
		}

	}

	/**
	 * Return the length of the queue
	 * 
	 * @return
	 */
	public int length() {

		if (head == null)
			return 0;

		int len = 1;
		PQueueItem<T> item = head;
		while (item.getNext() != null) {
			item = item.getNext();
			len++;
		}

		return len;
	}

	public String toString() {
		int i = length();
		PQueueItem<T> current = head;
		StringBuffer sb = new StringBuffer();
		while (i > 0) {
			sb.append(current.toString());
			if (i > 1)
				sb.append(": ");
			current = current.getNext();
			i--;
		}
		return sb.toString();
	}

}
