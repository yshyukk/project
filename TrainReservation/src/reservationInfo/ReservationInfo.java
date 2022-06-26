package reservationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationInfo {
	private int reservationId;
	private int ticketId;
	private String memberId;
	@Override
	public String toString() {
		return "ReservationInfo [reservationId=" + reservationId + ", ticketId=" + ticketId + ", memberId=" + memberId
				+ "]";
	}
	
	
}
