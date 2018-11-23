import pqueue.PQueue;
import pqueue.PQueueItem;

public class TicketSystem extends PQueue<Ticket> {
	public static int SECURITY = 0;
	public static int NETWORK = 1;
	public static int SOFTWARE = 2;
	public static int COMPUTER = 3;
	public static int MAX_TICKET_TYPE = 4;
	
	//0x00FFFFFF ~ 16 million tickets for each type
	public static int MAX_TICKET_ID = (1<<24 - 1); 

	public static int[] ticket_id;

	// TicketSystem factory function 
	public static TicketSystem start() {
		ticket_id = new int[MAX_TICKET_TYPE];
		
		return new TicketSystem(); 
	}

	public TicketSystem() {
		super(ORDER.ASC);
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
			throw new IllegalArgumentException("The priority " + String.valueOf(ticket_id) + " not found");
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

	public void update(int ticket_id, int updated_priority) {
		
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
		insert(data, updated_priority);
	}

}
