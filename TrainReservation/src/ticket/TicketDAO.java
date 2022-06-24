package ticket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;
import member.Member;

public class TicketDAO extends DAO {

	private static TicketDAO tDao = null;

	TicketDAO() {
	}

	public static TicketDAO getInstance() {
		if (tDao == null) {
			tDao = new TicketDAO();
		}
		return tDao;
	}
	
	//isert(정보입력)
	public void insert(Ticket ticket) {
		try {
			connect();
			String sql = "INSERT INTO ticket " + "(ticket_id, timetable_id, train_sector, seat_num, price ) " + " VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ticket.getTicketId());
			pstmt.setInt(2, ticket.getTimetableId());
			pstmt.setInt(3, ticket.getTrainSector());
			pstmt.setInt(4, ticket.getSeatNum());
			pstmt.setInt(5, ticket.getPrice());
			
			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	public void Update(Ticket ticket) {
		//좌석번호, 가격 변경
		try {
			connect();
			String sql = "UPDATE member set seat_num =?, price =? WHERE ticket_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ticket.getSeatNum());
			pstmt.setInt(2, ticket.getPrice());
			pstmt.setInt(3, ticket.getTicketId());
			
			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void delete(Ticket ticket) {
		// 발권취소
		try {
			connect();
			String sql = "DELETE ticket WHERE ticket_id =? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ticket.getTicketId());;

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	// 에약된 티켓 전체 조회
	public List<Ticket> selectAll() {
		List<Ticket> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM ticket ORDER BY ticket_id";

			stmt = conn.createStatement();
			rs = pstmt.executeQuery(sql);
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setTicketId(rs.getInt("ticket_id"));
				ticket.setTimetableId(rs.getInt("timetable_id"));
				ticket.setTrainSector(rs.getInt("train_sector"));
				ticket.setSeatNum(rs.getInt("seat_num"));
				ticket.setPrice(rs.getInt("price"));
				
				list.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
public List<Ticket> searchTicket(int ticketId) {
		
		List<Ticket> list = new ArrayList<>();
		// 1. 티켓ID로 티켓정보확인
		
		try {
			connect();
			String sql = "SELECT * FROM ticket WHERE ticket_id =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,ticketId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Ticket ticket = new Ticket();
				
				ticket.setTicketId(rs.getInt("ticket_id"));
				ticket.setTimetableId(rs.getInt("timetable_id"));
				ticket.setTrainSector(rs.getInt("train_sector"));
				ticket.setSeatNum(rs.getInt("seat_num"));
				ticket.setPrice(rs.getInt("price"));
				
				list.add(ticket);
									
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return list;
	}
	
}
