package timeTable;

import java.sql.Date;
//import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTable {
	
	private int timeTableId;
	private int trainId;
	private String trainName;
	private String departureTime;
	private String arriveTime;
	private String departureLocation;
	private String arriveLocation;

	@Override
	public String toString() {
		return timeTableId + "| 열차 >" + trainName + " | 출발시간 >" + departureTime + " | 도착시간 >" + arriveTime + " | 출발지 >"
				+ departureLocation + " | 도착지 >" + arriveLocation;
	}

}
