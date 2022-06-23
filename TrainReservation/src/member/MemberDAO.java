package member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;

public class MemberDAO extends DAO {
	private static MemberDAO sDao = null;

	MemberDAO() {
	}

	public static MemberDAO getInstance() {
		if (sDao == null) {
			sDao = new MemberDAO();
		}
		return sDao;
	}

	// CRUD
//insert ( 정보입력 ) 
	public void insert(Member member) {
		try {
			connect();
			String sql = "INSERT INTO member " + "(id, password, name, phnum ) " + " VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setInt(4, member.getPhnum());
			
			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void update(Member member) {
		// 비밀번호, 휴대폰번호 변경
		try {
			connect();
			String sql = "UPDATE member set password =?, phnum =? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getPassword());
			pstmt.setInt(2, member.getPhnum());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void delete(Member member) {
		// 회원정보삭제
		try {
			connect();
			String sql = "DELETE member WHERE id =? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getId());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 전체조회
	public List<Member> selectAll() {
		List<Member> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM member ORDER BY name";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member signup = new Member();
				signup.setId(rs.getString("id"));
				signup.setPassword(rs.getString("password"));
				signup.setPhnum(rs.getInt("phNum"));
				signup.setName(rs.getString("name"));
				signup.setReservation(rs.getInt("reservation"));

				list.add(signup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 회원검색
	public List<Member> searchId(String id) {
		
		List<Member> list = new ArrayList<>();
		// 1. 회원가입 이력이 있는지
		
		try {
			connect();
			String sql = "SELECT * FROM member WHERE id =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member member = new Member();
				
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhnum(rs.getInt("phnum"));
				member.setReservation(rs.getInt("reservation"));
				
				list.add(member);
					
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return list;
	}

}
