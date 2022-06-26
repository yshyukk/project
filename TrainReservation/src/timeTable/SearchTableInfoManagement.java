package timeTable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import common.Management;

public class SearchTableInfoManagement extends Management{
	Scanner sc = new Scanner(System.in);
	TimeTableDAO tableDao = TimeTableDAO.getInstance();
	
	public SearchTableInfoManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();

			if (menuNo == 1) {
				// 출발시간 이후 TABLE 정보조회
				searchTime();
			} else if (menuNo == 2) {
				// 출발지-도착지 입력하면 해당 TABLE 정보 조회
				searchLocation();
			} else if (menuNo == 9) {
				back();
			} else {
				showInputError();
			}
		}
	}

	private void menuPrint() {
		System.out.println("============================================================");
		System.out.println("1. 열차조회(시간) | 2. 열차조회 (출발-도착지) | 9.뒤로가기");
		System.out.println("============================================================");

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

	private Date convertDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	// timetable조회
		// 해당 출발지와 도착지 열차정보
		private void searchLocation() {
			// 출발지와 도착지 불러오기
			inputLocation();
			
			List<TimeTable> locList = tableDao.searchLocationInfo();
			
			for(TimeTable locSearchtable : locList) {
				System.out.println(locSearchtable);
			}

		}

		// 출발지 도착지 입력

		private void inputLocation() { // VO값 입력값으로 변경 TimeTable locTable = new
			TimeTable loctable = new TimeTable();
			System.out.println("departure_location");
			loctable.setDepartureLocation(sc.nextLine());
			System.out.println("arrive_location");
			loctable.setArriveLocation(sc.nextLine());
			
		}

		// 출발시간입력하면 그 시간보다 후에 시간 조회하기
		// 원하는 출발시간 입력받고
		// DAO에 넘겨줌
		private void searchTime() {
			TimeTable ttable = new TimeTable();
			System.out.println("yyyy-MM-dd HH:mm 입력해주세요.");
			System.out.println("departure_time");
			// sc.nextLine[string타입 -> Date타입으로]
			try {
				SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				java.util.Date date = dataFormat.parse(sc.nextLine());
				ttable.setDepartureTime(new java.sql.Date(date.getTime()));

				tableDao.searchTimeInfo(ttable);

			} catch (ParseException e) {
				e.printStackTrace();

			}
		}
}
