package member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Member {
	private int memberRole;
	private String memberId;
	private String password;
	private String name;
	private int phnum;
	private int reservationInfo;

	@Override
	public String toString() {
		String info = "";

		if (memberRole == 0) {
			info = "관리자 계정 : " + memberId;
		} else {
			info = "일반 계정 " + memberId;
		}
		return info;
	}

}
