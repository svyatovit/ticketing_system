package test;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import pqueue.PQueue;
import pqueue.PQueueItem;

public class TestPQueue {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLength() {
		PQueue<String> pq = new PQueue<String>();
		assertEquals(pq.length(), 0);
		pq.insert("Bananas", 1);
		pq.insert("More Bananas", 2);
		pq.insert("Fewer Bananas", 0);
		pq.insert("Avocadoes", 1);
		assertEquals(pq.length(), 4);
		pq.insert("Mangoes", 1);
		pq.insert("Kiwis", 1);
		pq.insert("Peaches", 3);
		assertEquals(pq.length(), 7);
	}

	@Test
	public void testPop() {
		PQueue<String> pq = new PQueue<String>();
		pq.insert("Bananas", 1);
		String head = pq.pop();
		assertEquals(head, "Bananas");
		assertEquals(pq.length(), 0);
		pq.insert("More Bananas", 2);
		pq.insert("Fewer Bananas", 0);
		pq.insert("Avocadoes", 1);
		assertEquals(pq.length(), 3);
		pq.insert("Kiwis", 1);
		pq.insert("Peaches", 3);
		pq.insert("Mangoes", 1);
		head = pq.pop();
		assertEquals(head, "Peaches");
		assertEquals(pq.length(), 5);
	}

	@Test
	public void testPeek() {
		PQueue<String> pq = new PQueue<String>();
		pq.insert("Bananas", 1);
		String head = pq.peek();
		assertEquals(head, "Bananas");
		assertEquals(pq.length(), 1);
		pq.insert("More Bananas", 2);
		pq.insert("Fewer Bananas", 0);
		pq.insert("Avocadoes", 1);
		assertEquals(pq.length(), 4);
		pq.insert("Kiwis", 1);
		pq.insert("Peaches", 3);
		pq.insert("Mangoes", 1);
		head = pq.peek();
		assertEquals(head, "Peaches");
		assertEquals(pq.length(), 7);
	}

	@Test
	public void testInsertDefault() {
		int size = 99;
		int pqi;
		PQueue<Integer> pq = new PQueue<Integer>();
		for (int i = 0; i < size; i++) {
			pqi = (new Random()).nextInt(50);
			pq.insert(pqi, pqi);
		}
		System.out.println(pq);
		int l = pq.length();
		assertEquals(l, size);
		PQueueItem<Integer> lastItem = null, nextItem = null;
		for (int i = 0; i < l; i++) {
			nextItem = pq.popItem();
			if (lastItem != null) {
				assertTrue(lastItem.getPriority() >= nextItem.getPriority());
				lastItem = nextItem;
			}
		}
	}

	@Test
	public void testInsertAsc() {
		int size = 99;
		int pqi;
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi = (new Random()).nextInt(10);
			pq.insert(pqi, pqi);
		}
		System.out.println(pq);
		int l = pq.length();
		assertEquals(l, size);
		PQueueItem<Integer> lastItem = null, nextItem = null;
		for (int i = 0; i < l; i++) {
			nextItem = pq.popItem();
			if (lastItem != null) {
				assertTrue(lastItem.getPriority() <= nextItem.getPriority());
				lastItem = nextItem;
			}
		}
	}

	@Test
	public void testInsertDesc() {
		int size = 99;
		int pqi;
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.DESC);
		for (int i = 0; i < size; i++) {
			pqi = (new Random()).nextInt(99);
			pq.insert(pqi, pqi);
		}
		System.out.println(pq);
		int l = pq.length();
		assertEquals(l, size);
		PQueueItem<Integer> lastItem = null, nextItem = null;
		for (int i = 0; i < l; i++) {
			nextItem = pq.popItem();
			if (lastItem != null) {
				assertTrue(lastItem.getPriority() >= nextItem.getPriority());
				lastItem = nextItem;
			}
		}
	}

	@Test
	public void testSearchvHead() {
		int size = 99;
		int[] pqi = new int[size];
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi[i] = i;
			pq.insert(pqi[i], pqi[i]);
		}

		assertTrue(pq.search(pq.peekItem().getPriority()) == pq.peek());
	}

	@Test
	public void testSearchRandom() {
		int size = 99;
		int[] pqi = new int[size];
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi[i] = i;
			pq.insert(pqi[i], pqi[i]);
		}
		for (int i = 0; i < size; i++) {
			int e = (new Random()).nextInt(99);
			assertTrue(pq.search(e) == Integer.valueOf(pqi[e]));
		}
	}

	@Test
	public void testSearchLast() {
		int size = 99;
		int[] pqi = new int[size];
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi[i] = i;
			pq.insert(pqi[i], pqi[i]);
		}
		assertTrue(pq.search(pqi[size - 1]) == pqi[size - 1]);
	}

	@Test
	public void testRemove() {
		int size = 99;
		int[] pqi = new int[size];
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi[i] = i;
			pq.insert(pqi[i], pqi[i]);
		}
		int element = (new Random()).nextInt(99);
		pq.remove(element);
		try {
			int found_element = pq.search(element);
			System.out.println(found_element);
			// We should not be here!
			assertTrue(false);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testUpdate() {
		int size = 99;
		int[] pqi = new int[size];
		PQueue<Integer> pq = new PQueue<Integer>(PQueue.ORDER.ASC);
		for (int i = 0; i < size; i++) {
			pqi[i] = i;
			pq.insert(pqi[i], pqi[i]);
		}
		int priority = (new Random()).nextInt(99);
		// New priority!
		int updated_priority = size + 1;
		pq.update(priority, updated_priority);

		try {
			int found_element = pq.search(priority);
			// We should not be here!
			assertTrue(false);
		} catch (IllegalArgumentException e) {
		}

		try {
			int found_element = pq.search(updated_priority);
		} catch (IllegalArgumentException e) {
			// We should not be here!
			assertTrue(false);
		}
	}

}
