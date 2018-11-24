package ticketing_system;

public class Ticket {
	private String creator;
	private String owner;

	public Ticket(String creator, String owner) {
		this.setCreator(creator);
		this.setOwner(owner);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String toString() {
		return new String("creator:" + this.creator + "," + "owner:" + this.owner);
	}
}
