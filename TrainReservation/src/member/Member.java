package member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Member {

	private String id;
	private String password;
	private String name;
	private int phnum;
	private int reservation;
	@Override
	public String toString() {
		return "아이디 > " + id + "비밀번호 > " + password + "이름 > " + name + "전화번호 > " + phnum + "예약정보 > " + reservation;
	}
	
	
	
}
