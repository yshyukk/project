package train;

import lombok.Getter;
import lombok.Setter;

//VO
@Getter
@Setter
public class Train {

	int trainId;
	String trainName;
	@Override
	public String toString() {
		return "열차번호 : " + trainId + "열차이름 : " + trainName;
	}
	
	
}
