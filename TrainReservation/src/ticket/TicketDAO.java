package ticket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;

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

	// 관리자가 티켓정보를 입력 (timetable , price, setNum반복)
	public void insertTicketInfo(Ticket ticket) {
		try {
			connect();
			String sql = "INSERT INTO ticket VALUES (ticket_seq.nextval, ?, ?, ?, ?, default)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ticket.getTimetableId());
			pstmt.setInt(2, ticket.getTrainSector());
			pstmt.setInt(4, ticket.getPrice());
			for (int j = 65; j <= 70; j++) {
				for (int i = 1; i < 21; i++) {
					pstmt.setString(3, (char) j + "" + i);
					int result = pstmt.executeUpdate();

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 예매! DB에 mamberId값만 넘겨주기
	// 출발 -> 목적지를 입력하면 해당 타임테이블의 정보가 출력된다
	// 내가 예매하고자하는 Timetable값을 입력하고 내 memberid를 입력한다.

	public void reservation(Ticket ticket) {

		try {
			connect();
			String sql = "UPDATE ticket set member_id = ? WHERE timetable_id =? AND seat_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ticket.getMemberId());
			pstmt.setInt(2, ticket.getTimetableId());
			pstmt.setString(3, ticket.getSeatNum());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public List<Ticket> remainSeat(int timetableId) {
		List<Ticket> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT  k.*, t.departure_time, t.arrive_time, t.departure_location, t.arrive_location"
					+ " FROM ticket k JOIN timetable t" + " ON t.timetable_id = k.timetable_id"
					+ " WHERE member_id is null AND t.timetable_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, timetableId);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				Ticket ticket = new Ticket();

				ticket.setTicketId(rs.getInt("ticket_id"));

				ticket.setDepartureLocation(rs.getString("departure_location"));
				ticket.setArriveLocation(rs.getString("arrive_location"));
				ticket.setDepartureTime(rs.getString("departure_time"));
				ticket.setArriveTime(rs.getString("arrive_time"));
				ticket.setTimetableId(rs.getInt("timetable_id"));
				ticket.setTrainSector(rs.getInt("train_sector"));
				ticket.setSeatNum(rs.getString("seat_num"));
				ticket.setPrice(rs.getInt("price"));
				ticket.setMemberId(rs.getString("member_id"));

				list.add(ticket);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnect();
		}
		return list;
	}

	// 예매한 내 티켓정보 조회
	// memberId비교해서 ticket정보 출력
	public List<Ticket> searchMyTicket(String memberId) {
		List<Ticket> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT r.train_name, t.departure_location, t.arrive_location, t.departure_time, t.arrive_time, k.train_sector, k.seat_num, k.ticket_id"
					+ " FROM timetable t JOIN train r" + " ON (t.train_id = r.train_id)" + " join ticket k"
					+ " on(t.timetable_id = k.timetable_id)" + " WHERE k.member_id =  ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Ticket tticket = new Ticket();

				tticket.setTicketId(rs.getInt("ticket_id"));
				tticket.setTrainName(rs.getString("train_name"));
				tticket.setDepartureLocation(rs.getString("departure_location"));
				tticket.setArriveLocation(rs.getString("arrive_location"));
				tticket.setDepartureTime(rs.getString("departure_time"));
				tticket.setArriveTime(rs.getString("arrive_time"));
				tticket.setTrainSector(rs.getInt("train_sector"));
				tticket.setSeatNum(rs.getString("seat_num"));

				list.add(tticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public void delete(int ticketId) {
		// 발권취소
		try {
			connect();
			String sql = "UPDATE ticket" + " set member_id = null" + " WHERE ticket_id =?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ticketId);
			;

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println(ticketId + "가 취소되었습니다.");
			} else {
				System.out.println("ticketId를 확인해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public Ticket selectOne(int ticketId) {
		Ticket ticket = null;
		try {
			connect();
			String sql = "SELECT * FROM ticket where ticket_id =" + ticketId;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				ticket = new Ticket();

				ticket.setMemberId(rs.getString("member_id"));
				ticket.setTicketId(rs.getInt("ticket_Id"));
				ticket.setTimetableId(rs.getInt("timetable_id"));
				ticket.setTrainName(rs.getString("train_name"));
				ticket.setTrainSector(rs.getInt("train_sector"));
				ticket.setSeatNum(rs.getString("seat_num"));
				ticket.setPrice(rs.getInt("price"));
				ticket.setDepartureTime(rs.getString("departure_time"));
				ticket.setArriveTime(rs.getString("arrive_time"));
				ticket.setDepartureLocation(rs.getString("departure_location"));
				ticket.setArriveLocation(rs.getString("arrive_location"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return ticket;
	}

}
