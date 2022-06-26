package reservationInfo;

import java.sql.SQLException;

import common.DAO;

public class ResDAO extends DAO {
	private static ResDAO resDAO = null;

	ResDAO() {
	}

	public static ResDAO getInstance() {
		if (resDAO == null) {
			resDAO = new ResDAO();
		}
		return resDAO;
	}
	
	//등록
	 //예약정보 등록
	public void insert(ReservationInfo res) {
		try {
			connect();
			String sql = "SELECT m.member_id =?, t.ticket_id =?, r.reservation_id =?"
					+ "FROM MEMBER m JOIN reservation "
					+ "ON  (m.member_id =? = r.member_id =?)"
					+ "JOIN ticket "
					+ "ON (r.ticket_id =? = t.ticket_id =?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, res.getMemberId());
			pstmt.setInt(2, res.getTicketId());
			pstmt.setInt(3,res.getReservationId());
			pstmt.setString(4,res.getMemberId());
			pstmt.setInt(5, res.getTicketId());
			pstmt.setInt(6, res.getReservationId());
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
	}
		
	//수정
	
	//삭제
	
	//예약 id 입력 시 티켓정보 조회
	public ReservationInfo searchResId(int reservationId) {
		ReservationInfo res = new ReservationInfo();
		
		try {
			connect();
			
			String sql = "SELECT * FROM reservation WHERE reservation_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reservationId);
			
			rs = pstmt.executeQuery();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return res;
	}
	
	
}
