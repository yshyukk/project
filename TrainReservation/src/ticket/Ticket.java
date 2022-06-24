package ticket;

import lombok.Getter;
import lombok.Setter;

//VO
@Getter
@Setter
public class Ticket {
	private int ticketId;
	private int timetableId;
	private int trainSector;
	private int seatNum;
	private int price;
	@Override
	public String toString() {
		return "ticketId> " + ticketId + "timetableId> " + timetableId + "trainSector> " + trainSector
				+ "seatNum> " + seatNum + "price> " + price;
	}
	
	
	
}
