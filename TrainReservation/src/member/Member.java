package member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Member {

	private String memberId;
	private String password;
	private String name;
	private int phnum;
	private int reservationInfo;
	@Override
	public String toString() {
		return "아이디 > " + memberId + "비밀번호 > " + password + "이름 > " + name + "전화번호 > " + phnum + "예약정보 > " + reservationInfo;
	}
	
	
	
}
