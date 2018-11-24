package test;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import ticketing_system.TicketSystem;
import ticketing_system.Ticket;

public class TestTicketSystem {
	public TicketSystem ts;
	// Tickets
	public int security0;
	public int security1;
	public int network0;
	public int network1;
	public int software0;
	public int software1;
	public int computer0;
	public int computer1;

	@Before
	public void setUp() throws Exception {
		ts = TicketSystem.start();

		// add some tickets of different priority types
		security0 = ts.add(new Ticket("Smith", "Jones"), TicketSystem.SECURITY);
		security1 = ts.add(new Ticket("Williams", "Jones"), TicketSystem.SECURITY);
		network0 = ts.add(new Ticket("Davies", "Taylor"), TicketSystem.NETWORK);
		network1 = ts.add(new Ticket("Evans", "Taylor"), TicketSystem.NETWORK);
		software0 = ts.add(new Ticket("Thomas", "Taylor"), TicketSystem.SOFTWARE);
		software1 = ts.add(new Ticket("Johnson", "Taylor"), TicketSystem.SOFTWARE);
		computer0 = ts.add(new Ticket("Walker", "Roberts"), TicketSystem.COMPUTER);
		computer1 = ts.add(new Ticket("Wright", "Roberts"), TicketSystem.COMPUTER);
	}

	@Test
	public void testPrintAll() {
		System.out.println("===== testPrintAll =====");
		System.out.println(ts.toString());
	}

	@Test
	public void testSearch() {
		System.out.println("===== testSearch =====");
		// Search for ticket
		// 16777218:NETWORK:2,creator:Evans,owner:Taylor
		int ticket_id = 16777218;
		Ticket t = ts.search(ticket_id);
		assertEquals(t.getOwner(), "Taylor");
		assertEquals(t.getCreator(), "Evans");
	}

	@Test
	public void testRemove() {
		System.out.println("===== testRemove =====");
		// Remove ticket
		// 16777218:NETWORK:2,creator:Evans,owner:Taylor
		int ticket_id = 16777218;
		ts.remove(ticket_id);

		// Search for the ticket, search show throw
		try {
			Ticket t = ts.search(ticket_id);
			// we should not reach this point!!!
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println("Ticket id : " + ticket_id + " has been deleted!");
			System.out.println(ts.toString());
		}
	}

	@Test
	public void testUpgrade() {
		System.out.println("===== testUpgrade =====");
		// Ticket
		// 50331650:COMPUTER:2,creator:Wright,owner:Roberts
		// is going to be upgraded to the SECURITY
		int ticket_id = 50331650;
		int new_ticket_id = ts.upgrade(ticket_id, ts.SECURITY);

		// Make sure that a new ticket id exists and the data
		// remains the same
		Ticket t = ts.search(new_ticket_id);
		assertEquals(t.getOwner(), "Roberts");
		assertEquals(t.getCreator(), "Wright");

		// Make sure that an old ticket has been deleted
		// Search for the ticket, search show throw
		try {
			t = ts.search(ticket_id);
			// we should not reach this point!!!
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println("Ticket id : " + ticket_id + " has been been upgraded to SECURITY with new ticket id: "
					+ new_ticket_id);
			System.out.println(ts.toString());
		}
	}
}
