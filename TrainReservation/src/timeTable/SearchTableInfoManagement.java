package timeTable;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class SearchTableInfoManagement {
	Scanner sc = new Scanner(System.in);
	TimeTableDAO tableDao = TimeTableDAO.getInstance();

	public SearchTableInfoManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();

			if (menuNo == 1) {
				// 출발-도착지-시간 입력
				searchTimeinput();
			} else if (menuNo == 2) {
				// 출발지-도착지 -열차이름 입력
				searchNameTable();
			} else if (menuNo == 9) {
				back();
				break;
			} else {
				showInputError();
			}
		}
	}

	private void menuPrint() {
		System.out.println("============================================");
		System.out.println("1. 열차조회(시간) | 2. 열차조회 (열차이름) | 9.뒤로가기");
		System.out.println("============================================");

	}

	private int selectMenu() {
		int menu = 0;
		try {
			menu = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해주세요.");
		}
		return menu;
	}

	protected void back() {
		System.out.println("이전 메뉴로 돌아갑니다.");
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}

	// timetable조회
	// 해당 출발지와 도착지 열차정보
	public void searchLocation() {
		// 출발지와 도착지 불러오기

		List<TimeTable> locList = tableDao.searchLocationInfo(inputLocation());

		for (TimeTable locSearchtable : locList) {
			System.out.println(locSearchtable);
		}
	}
	
	public TimeTable inputLocation() { 
		TimeTable loctable = new TimeTable();
		System.out.println("출발지를 입력해주세요!");
		System.out.println("출발지 > ");
		loctable.setDepartureLocation(sc.nextLine());
		System.out.println("도착지를 입력해주세요!");
		System.out.println("도착지 >");
		loctable.setArriveLocation(sc.nextLine());
		return loctable;
	}
	// 출발지 도착지 시간입력  해서 테이블 정보
	public void searchTimeinput() {
		

		List<TimeTable> List = tableDao.searchLocationInfo(searchTimeTable());

		for (TimeTable timetable : List) {
			System.out.println(timetable);
		}
	}
	
	
		 // 출발지와 도착지 불러오기
	public TimeTable searchTimeTable() { 
		TimeTable loctable = new TimeTable();
		System.out.println("출발지를 입력해주세요!");
		System.out.println("출발지 >");
		loctable.setDepartureLocation(sc.nextLine());
		System.out.println("도착지를 입력해주세요!");
		System.out.println("도착지 >");
		loctable.setArriveLocation(sc.nextLine());
		System.out.println("yyyy-MM-dd HH:mm 입력해주세요.");
		System.out.println("출발시간을 입력해주세요!");
		System.out.println("출발시간 >");
		loctable.setDepartureTime(sc.nextLine());
		return loctable;
		
	}

	
	private void searchNameTable() {

		// searchLocation();
		List<TimeTable> tList = tableDao.searchTimeInfo(inputTrianName());
		
		for (TimeTable timeTable : tList) {
			System.out.println(timeTable);
		}
	}
	

	private TimeTable inputTrianName() {
		TimeTable nTable = new TimeTable();
		System.out.println("출발지를 입력해주세요!");
		System.out.println("출발지 >");
		nTable.setDepartureLocation(sc.nextLine());
		System.out.println("도착지를 입력해주세요!");
		System.out.println("도착지 >");
		nTable.setArriveLocation(sc.nextLine());
		System.out.println("열차이름을 입력해주세요!");
		System.out.println("열차이름 >");
		nTable.setTrainName(sc.nextLine());
		System.out.println("yyyy-MM-dd HH:mm 입력해주세요.");
		System.out.println("출발시간을 입력해주세요!");
		System.out.println("출발시간 >");
		nTable.setDepartureTime(sc.nextLine());
		return nTable;
		
		

	}
}
