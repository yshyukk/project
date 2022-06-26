package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAO {


		private String jdbcDriver;
		private String oracleUrl;
		private String connectedId;
		private String connectedPwd;
		

		protected Connection conn;
		protected Statement stmt;
		protected PreparedStatement pstmt;
		protected ResultSet rs;

		
		public DAO() {
			dbConfig();
		}
		
		public void connect() {
			try {
				Class.forName(jdbcDriver);

				conn = DriverManager.getConnection(oracleUrl, connectedId, connectedPwd);

			} catch (ClassNotFoundException e) {
				System.out.println("jdbc driver 로딩 실패");

			} catch (SQLException e) {
				System.out.println("DB 연결 실패");
			}

		}

		private void dbConfig() {

			String resource = "config/db.properties";
			Properties properties = new Properties();
			
			try {
				String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();
				properties.load(new FileInputStream(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			jdbcDriver = properties.getProperty("driver");
			oracleUrl = properties.getProperty("url");
			connectedId = properties.getProperty("id");
			connectedPwd = properties.getProperty("password");
			
		}

		// DB해제 메서드
		public void disconnect() {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	
}
