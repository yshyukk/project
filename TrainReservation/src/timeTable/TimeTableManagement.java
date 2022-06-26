package timeTable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import common.Management;

public class TimeTableManagement extends TimeTable {

	Scanner sc = new Scanner(System.in);

	TimeTableDAO tableDao = TimeTableDAO.getInstance();

	public TimeTableManagement() {
		boolean role = new Management().selectRole();
		
		while (true) {
			// 메뉴 출력
			menuPrint(role);
			// 메뉴 입력
			int menuNo = selectMenu();

			if (menuNo == 1 && role) {
				// TIMETABLE 정보 입력
				insertInfo();
			} else if (menuNo == 2 && role) {
				// TIMETABLE 등록된 정보 수정
				updateTableInfo();
			} else if (menuNo == 3) {
				// 출발시간 이후 TABLE 정보조회
				searchTime();
			} else if (menuNo == 4) {
				// 출발지-도착지 입력하면 해당 TABLE 정보 조회
				searchLocation();
			} else if (menuNo == 5 && role) {
				deleteTableInfo();
			} else if (menuNo == 9) {
				back();
				break;
			}
		}
	}

	protected void menuPrint(boolean role) {
		System.out.println(
				"=================================================================================================");
		System.out.println("1. 열차시간표 정보입력 | 2. 열차시간표 정보수정 | 3.열차조회(시간) | 4. 열차조회(출발-도착) | 5. 열차정보 삭제 | 9.종료");
		System.out.println(
				"=================================================================================================");

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

	private void back() {
		System.out.println("이전 메뉴로 돌아갑니다.");
	}

	private Date convertDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	// time table에 열차정보 입력
	public void insertInfo() {
		TimeTable insertTable = new TimeTable();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		//System.out.println("timetable_id > ");
		//insertTable.setTimeTableId(Integer.parseInt(sc.nextLine()));
		System.out.println("train_id > ");
		insertTable.setTrainId(Integer.parseInt(sc.nextLine()));
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("departure_time > ");
		// sc.nextLine[string타입 -> Date타입으로]
		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			insertTable.setDepartureTime(new java.sql.Date(date.getTime()));

		} catch (ParseException e) {
			e.printStackTrace();

		}
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("arrive_time > ");
		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			insertTable.setArriveTime(new java.sql.Date(date.getTime()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("departure_location > ");
		insertTable.setDepartureLocation(sc.nextLine());
		System.out.println("arrive_location > ");
		insertTable.setArriveLocation(sc.nextLine());

		tableDao.insert(insertTable);
	}

	public void updateTableInfo() {
		// 열차시간표 정보수정 - 출발시간, 도착시간
		// 바꾸고자 하는 table_id찾기
		int tableId = inputTableId();
		// 새로 초기화된 vo에 찾은 table_id입력
		TimeTable currentTable = tableDao.selectOne(tableId);

		// table 정보 불러옴

		if (currentTable == null) {
			System.out.println("등록된 열차정보가 없습니다.");

		} else {
			// 해당하는 table_id의 출발시간-도착시간을 수정

			TimeTable updateTable = insertUpdate(currentTable);

			// DB에 업데이트
			tableDao.update(updateTable);

		}
	}

	// update할 시간찾기
	private TimeTable insertUpdate(TimeTable table) {
		// System.out.println("기존 열차정보 > " + tableDao.selectOne(getTimeTableId()));
		System.out.println("<<열차정보 수정 [수정하지 않을경우  O를입력해주세요.]>>");

		// dataformat을 입력 -> 내가 바꾸고자하는 날짜를 형식대로 입력->
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("yyyy-MM-dd a(p) HH:mm 형식으로 입력해주세요.");
		System.out.println("departure_time");
		// sc.nextLine[string타입 -> Date타입으로]
		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			table.setDepartureTime(convertDate(date));

		} catch (ParseException e) {
			e.printStackTrace();

		}
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("arrive_time");
		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			table.setArriveTime(convertDate(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return table;

	}

	// tableId 찾기
	private int inputTableId() {
		System.out.println("Table_id > ");
		return Integer.parseInt(sc.nextLine());

	}

	private void deleteTableInfo() {
		// tableId찾고
		int tableId = inputTableId();
		TimeTable currentTable = tableDao.selectOne(tableId);
		// tableDao에 찾은 id값 넣기
		if (currentTable == null) {
			System.out.println("등록된 열차정보가 없습니다.");
		} else {
			tableDao.delete(currentTable);
		}

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
