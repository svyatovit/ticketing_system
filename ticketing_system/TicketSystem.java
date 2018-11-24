package ticketing_system;
import pqueue.PQueue;
import pqueue.PQueueItem;

public class TicketSystem extends PQueue<Ticket> {
	public static int SECURITY = 0;
	public static int NETWORK = 1;
	public static int SOFTWARE = 2;
	public static int COMPUTER = 3;
	public static int MAX_TICKET_TYPE = 4;
	
	//0x00FFFFFF ~ 16 million tickets for each type
	public static int MAX_TICKET_ID = (1<<24) - 1; 

	public static int[] ticket_id;

	// TicketSystem factory function 
	public static TicketSystem start() {
		ticket_id = new int[MAX_TICKET_TYPE];
		for (int type = 0; type < MAX_TICKET_TYPE; type++)
		{
			ticket_id[type] = 0;
		}
		
		return new TicketSystem(); 
	}

	private static int get_ticket_id(int priority) {
		if (ticket_id[priority] + 1 > MAX_TICKET_ID)
		{
			throw new RuntimeException("Exceed the maximum number of tickets " + ticket_id[priority] + 
					"for priority" + priority);
		}
		return (priority << 24) | ++ticket_id[priority];
	}
	
	public TicketSystem() {
		super(ORDER.ASC);
	}
	
	public int add(Ticket data, int priority)
	{
		int ticket_id = get_ticket_id(priority);
		super.insert(data, ticket_id);
		return ticket_id;
	}
	
	/**
	 * O(N) search algorithm within the queue
	 */
	private PQueueItem<Ticket> search_previous(int ticket_id) {

		if (head == null)
			return null;

		if (head.getPriority() == ticket_id)
			return null;

		PQueueItem<Ticket> item = head;
		PQueueItem<Ticket> prev = null;
		PQueueItem<Ticket> next = head.getNext();

		while (item.getNext() != null && item.getNext().getPriority() != ticket_id) {
			prev = item;
			item = item.getNext();
			next = item.getNext();
		}

		if (item.getNext() != null)
			return item;
		else
			throw new IllegalArgumentException("The ticket id " + String.valueOf(ticket_id) + " not found");
	}

	public Ticket search(int ticket_id) {
		PQueueItem<Ticket> prev = search_previous(ticket_id);
		if (prev == null)
			return head.getData();
		
		return prev.getNext().getData();
	}

	/**
	 * Remove an element from the queue
	 */
	public void remove(int ticket_id) {
		PQueueItem<Ticket> prev = search_previous(ticket_id);
		if (prev == null) {
			// Removing head!
			head = head.getNext();
			return;
		}

		PQueueItem<Ticket> item = prev.getNext();
		PQueueItem<Ticket> next = item.getNext();

		// Now removing item object from the list
		prev.setNext(next);
		item = null;
	}

	public int upgrade(int ticket_id, int updated_priority) {
		
		PQueueItem<Ticket> prev = search_previous(ticket_id);
		Ticket data;
		if (prev == null) {
			// Updating head
			data = head.getData();
			head = head.getNext();
		} else {
			PQueueItem<Ticket> item = prev.getNext();
			PQueueItem<Ticket> next = item.getNext();

			// Store current item data
			data = item.getData();
			// Remove current item from the list
			prev.setNext(next);
			item = null;
		}

		// Insert data with the updated priority
		return add(data, updated_priority);
	}
	
	public int priority(int ticket_id)
	{
		return (ticket_id & 0xFF000000) >> 24;
	}
	
	public String priorityString(int ticket_id)
	{
		switch(priority(ticket_id)) 
		{
		case 0:
			return new String("SECURITY");
		case 1:
			return new String("NETWORK");
		case 2:
			return new String("SOFTWARE");
		case 3:
			return new String("COMPUTER");
		}
		return new String("Unknown");
	}
	public int id(int ticket_id)
	{
		return (ticket_id & 0x00FFFFFF);
	}
	
	public String toString() {
		int i = length();
		PQueueItem<Ticket> current = head;
		StringBuffer sb = new StringBuffer();
		while (i > 0) {
			sb.append(
					current.getPriority() + ":" +
					priorityString(current.getPriority()) + ":" + id(current.getPriority()) + ","
							+ current.getData().toString());
			if (i > 1)
				sb.append("\n");
			current = current.getNext();
			i--;
		}
		return sb.toString();
	}

}
