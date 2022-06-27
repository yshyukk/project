package member;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;

public class MemberDAO extends DAO {
	private static MemberDAO mDao = null;

	MemberDAO() {
	}

	public static MemberDAO getInstance() {
		if (mDao == null) {
			mDao = new MemberDAO();
		}
		return mDao;
	}

	// CRUD
//insert ( 정보입력 ) 
	public void insert(Member member) {
		try {
			connect();
			String sql = "INSERT INTO member " + "(member_id, password, name, ph_num ) " + " VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
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
			String sql = "UPDATE member SET password = ? ph_num =?"
						+ " WHERE member_id =?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setInt(2, member.getPhnum());
			pstmt.setString(3, member.getMemberId());
			
			int result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}
	
	
	

	public void delete(Member member) {
		// 회원정보삭제
		try {
			connect();
			String sql = "DELETE member WHERE member_id =?, password = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(1, member.getPassword());
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
				signup.setMemberId(rs.getString("member_id"));
				signup.setPassword(rs.getString("password"));
				signup.setPhnum(rs.getInt("ph_Num"));
				signup.setName(rs.getString("name"));
				signup.setReservationInfo(rs.getInt("reservation_info"));

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
	public Member selectOne(Member member) {

		Member loginInfo = null;
		// 1. 회원가입 이력이 있는지

		try {
			connect();
			String sql = "SELECT * FROM member WHERE member_id = '" + member.getMemberId() + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				if (rs.getString("password").equals(member.getPassword())) {
					member.setMemberRole(rs.getInt("role"));

					loginInfo = new Member();
					loginInfo.setMemberId(rs.getString("member_id"));
					loginInfo.setPassword(rs.getString("password"));
					loginInfo.setMemberRole(rs.getInt("role"));
					System.out.println(rs.getInt("role"));
					System.out.println(loginInfo);
					System.out.println(loginInfo.getMemberRole());
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
			} else {
				System.out.println("아이디가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return loginInfo;
	}

	public Member selectPw(String memberId) {
		//memberId 입력 -> password 가져오기
		
		Member member = null;
		try {
			connect();
			
			String sql = "SELECT password FROM member WHERE member_id = ?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member();
				
				member.setPassword(rs.getString("password"));
				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return member;
	}
}
