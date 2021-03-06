package timeTable;

import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import common.DAO;

public class TimeTableDAO extends DAO {
	private static TimeTableDAO tableDao = null;
	TimeTable table = new TimeTable();

	TimeTableDAO() {
	}

	public static TimeTableDAO getInstance() {
		if (tableDao == null) {
			tableDao = new TimeTableDAO();
		}
		return tableDao;
	}

	// timetable에 열차정보 입력
	public void insert(TimeTable table) {
		try {
			connect();
			String sql = "INSERT INTO timetable "
					+ "( timetable_id , train_id, departure_time, arrive_time, departure_location, arrive_location) "
					+ " VALUES (timetable_seq.NEXTVAL,?,TO_DATE(?,'YYYY-MM-DD hh24:mi'),TO_DATE(?,'YYYY-MM-DD hh24:mi'),?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, table.getTrainId());
			pstmt.setString(2, table.getDepartureTime());
			pstmt.setString(3, table.getArriveTime());
			pstmt.setString(4, table.getDepartureLocation());
			pstmt.setString(5, table.getArriveLocation());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void update(TimeTable table) {
		// Timetable에 출발시간 도착시간 변경
		try {
			connect();
			String sql = "UPDATE timetable set departure_time = TO_DATE(?,'YYYY-MM-DD hh24:mi'), arrive_time = TO_DATE(?,'YYYY-MM-DD hh24:mi') WHERE timetable_id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, table.getDepartureTime());
			pstmt.setString(2, table.getArriveTime());
			pstmt.setInt(3, table.getTimeTableId());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	public void delete(TimeTable table) {
		// 해당 timetable_id 정보 삭제
		try {
			connect();
			String sql = "DELETE timetable WHERE timetable_id =? CASCADE";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, table.getTimeTableId());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 전체 TIMETABLE 조회
	public List<TimeTable> selectAll() {
		List<TimeTable> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM timetable ORDER BY departure_time";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TimeTable table = new TimeTable();

				table.setTimeTableId(rs.getInt("timetable_id"));
				table.setTrainId(rs.getInt("train_id"));
				table.setDepartureTime(rs.getString("departure_time"));
				table.setArriveTime(rs.getString("arrive_time"));
				table.setDepartureLocation(rs.getString("departure_location"));
				table.setArriveLocation(rs.getString("arrive_location"));

				list.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public List<TimeTable> searchLocation(TimeTable table) {

		List<TimeTable> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT t.timetable_id, n.train_name, t.departure_time, t.arrive_time, t.departure_location, t.arrive_location"
					+ " FROM timetable t JOIN train n" + " ON t.train_id = n.train_id "
					+ " WHERE departure_location= ? AND arrive_location = ? " + " ORDER BY departure_time";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, table.getDepartureLocation());
			pstmt.setString(2, table.getArriveLocation());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				TimeTable ntable = new TimeTable();

				ntable.setTimeTableId(rs.getInt("timetable_id"));
				ntable.setTrainName(rs.getString("train_name"));
				ntable.setDepartureTime(rs.getString("departure_time"));
				ntable.setArriveTime(rs.getString("arrive_time"));
				ntable.setDepartureLocation(rs.getString("departure_location"));
				ntable.setArriveLocation(rs.getString("arrive_location"));

				list.add(ntable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;

	}

	// 조건별 조회
	// -1. 출발지_도착지_시간 입력시 해당하는 열차정보
	public List<TimeTable> searchLocationInfo(TimeTable table) {

		List<TimeTable> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT t.timetable_id, n.train_name, t.departure_time, t.arrive_time, t.departure_location, t.arrive_location"
					+ " FROM timetable t JOIN train n" + " ON t.train_id = n.train_id "
					+ " WHERE departure_location= ? AND arrive_location = ? "
					+ " AND departure_time >= TO_DATE(?,'YYYY-MM-DD hh24:mi')" + " ORDER BY departure_time";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, table.getDepartureLocation());
			pstmt.setString(2, table.getArriveLocation());
			pstmt.setString(3, table.getDepartureTime());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				TimeTable ntable = new TimeTable();

				ntable.setTimeTableId(rs.getInt("timetable_id"));
				ntable.setTrainName(rs.getString("train_name"));
				ntable.setDepartureTime(rs.getString("departure_time"));
				ntable.setArriveTime(rs.getString("arrive_time"));
				ntable.setDepartureLocation(rs.getString("departure_location"));
				ntable.setArriveLocation(rs.getString("arrive_location"));

				list.add(ntable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;

	}

	// -2.출발지_도착지_열차이름 입력하고 그 이후시간 열차정보 검색

	public List<TimeTable> searchTimeInfo(TimeTable table) {

		List<TimeTable> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT t.timetable_id, n.train_name, t.departure_time, t.arrive_time, t.departure_location, t.arrive_location"
					+ " FROM timetable t JOIN train n" + " ON t.train_id = n.train_id" + " WHERE departure_location= ?"
					+ " AND arrive_location = ?" + " AND train_name =?"
					+ " AND departure_time >= TO_DATE(?,'YYYY-MM-DD hh24:mi')" + " ORDER BY departure_time";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, table.getDepartureLocation());
			pstmt.setString(2, table.getArriveLocation());
			pstmt.setString(3, table.getTrainName());
			pstmt.setString(4, table.getDepartureTime());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				TimeTable ttable = new TimeTable();

				ttable.setTimeTableId(rs.getInt("timetable_id"));
				ttable.setTrainName(rs.getString("train_name"));
				ttable.setDepartureTime(rs.getString("departure_time"));
				ttable.setArriveTime(rs.getString("arrive_time"));
				ttable.setDepartureLocation(rs.getString("departure_location"));
				ttable.setArriveLocation(rs.getString("arrive_location"));

				list.add(ttable);
				System.out.println(ttable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 테이블id값으로 해당 정보 찾기
	public TimeTable selectOne(int timeTableId) {
		// TimeTable table = null;
		try {
			connect();
			String sql = "SELECT * FROM timetable WHERE timetable_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, timeTableId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				table.setTimeTableId(rs.getInt("timetable_id"));
				table.setTrainId(rs.getInt("train_id"));
				table.setDepartureTime(rs.getString("departure_time"));
				table.setArriveTime(rs.getString("arrive_time"));
				table.setDepartureLocation(rs.getString("departure_location"));
				table.setArriveLocation(rs.getString("arrive_location"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return table;
	}

	public static String convertStr(java.util.Date depTime) {
		String dateTostr = depTime.toInstant().atOffset(ZoneOffset.UTC)
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH24:mm"));
		System.out.println(dateTostr);
		return dateTostr;
	}

}
