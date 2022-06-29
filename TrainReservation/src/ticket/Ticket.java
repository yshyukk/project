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
		return "티켓번호 >" + ticketId + " || 열차시간표 번호 >" + timetableId + " || 출발지 >" + departureLocation + " || 도착지 >" + arriveLocation +" || 출발시간 > " + departureTime + " || 도착시간> " + arriveTime + " || 탑승구역 >" + trainSector
				+ " || 좌석정보 >" + seatNum;
	}

}
