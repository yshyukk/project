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
		return "Member [id=" + id + ", password=" + password + ", name=" + name + ", phnum=" + phnum + ", reservation="
				+ reservation + ", getId()=" + getId() + ", getPassword()=" + getPassword() + ", getName()=" + getName()
				+ ", getPhnum()=" + getPhnum() + ", getReservation()=" + getReservation();
	}
	
	
	
}
