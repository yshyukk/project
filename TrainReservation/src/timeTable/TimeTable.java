package timeTable;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

public class TimeTable {
	private int timeTableId;
	private int trainId;
	private String trainName;
	private Date departureTime;
	private Date arriveTime;
	private String departureLocation;
	private String arriveLocation;
	
	
	public int getTimeTableId() {
		return timeTableId;
	}


	public void setTimeTableId(int timeTableId) {
		this.timeTableId = timeTableId;
	}


	public int getTrainId() {
		return trainId;
	}


	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}


	public String getTrainName() {
		return trainName;
	}


	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}


	public Date getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}


	public Date getArriveTime() {
		return arriveTime;
	}


	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}


	public String getDepartureLocation() {
		return departureLocation;
	}


	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}


	public String getArriveLocation() {
		return arriveLocation;
	}


	public void setArriveLocation(String arriveLocation) {
		this.arriveLocation = arriveLocation;
	}


	@Override
	public String toString() {
		return "시간표 번호> " + timeTableId + " 열차번호> " + trainId +  "열차이름> " + trainName
				+ "departureTime> " + departureTime + "arriveTime> " + arriveTime + "departureLocation> "
				+ departureLocation + "arriveLocation> " + arriveLocation ;
	}
	

}
