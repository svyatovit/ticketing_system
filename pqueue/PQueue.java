package pqueue;

import static org.junit.Assert.assertTrue;

public class PQueue<T> {

	protected PQueueItem<T> head;

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
		if (head == null)
			return null;

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
		if (head == null)
			return null;
		return head.getData();
	}

	/**
	 * Remove and return the item at the front of the queue
	 * 
	 * @return
	 */
	public PQueueItem<T> popItem() {
		if (head == null)
			return null;

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
	 * O(N) search algorithm within the queue
	 */
	private PQueueItem<T> search_previous(int priority) {

		if (head == null)
			return null;

		if (head.getPriority() == priority)
			return null;

		PQueueItem<T> item = head;
		PQueueItem<T> prev = null;
		PQueueItem<T> next = head.getNext();

		while (item.getNext() != null && item.getNext().getPriority() != priority) {
			prev = item;
			item = item.getNext();
			next = item.getNext();
		}

		if (item.getNext() != null)
			return item;
		else
			throw new IllegalArgumentException("The priority " + String.valueOf(priority) + " not found");
	}

	public T search(int priority) {
		PQueueItem<T> prev = search_previous(priority);
		if (prev == null)
			return head.getData();

		return prev.getNext().getData();
	}

	/**
	 * Remove an element from the queue
	 */
	public void remove(int priority) {
		PQueueItem<T> prev = search_previous(priority);
		if (prev == null) {
			// Removing head!
			head = head.getNext();
			return;
		}

		PQueueItem<T> item = prev.getNext();
		PQueueItem<T> next = item.getNext();

		// Now removing item object from the list
		prev.setNext(next);
		item = null;
	}

	public void update(int priority, int updated_priority) {
		if (priority == updated_priority) {
			// It's the same priority, do nothing!
			return;
		}

		try {
			T found_element = search(updated_priority);
			// We should not be here!
			assertTrue(false);
		} catch (IllegalArgumentException e) {
		}

		PQueueItem<T> prev = search_previous(priority);
		T data;
		if (prev == null) {
			// Updating head
			data = head.getData();
			head = head.getNext();

		} else {
			PQueueItem<T> item = prev.getNext();
			PQueueItem<T> next = item.getNext();

			// Store current item data
			data = item.getData();
			// Remove current item from the list
			prev.setNext(next);
			item = null;
		}

		// Insert data with the updated priority
		insert(data, updated_priority);
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
