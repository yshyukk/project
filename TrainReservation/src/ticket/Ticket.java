package ticket;

import lombok.Getter;
import lombok.Setter;

//VO
@Getter
@Setter
public class Ticket {
	private String memberId;
	private int ticketId;
	private int timetableId;
	private String trainName;
	private int trainSector;
	private String seatNum;
	private int price;
	private String departureTime;
	private String arriveTime;
	private String departureLocation;
	private String arriveLocation;

	@Override
	public String toString() {
		return "||Ticket_id >" + ticketId + " | Timetable_id >" + timetableId + " | Train_Sector >" + trainSector
				+ " || Seat_num >" + seatNum + " || price >" + price + "||";
	}

}
