package web.log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection connect;

	static {
		String driverClassName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverClassName);
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_automation", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnect() {
		return connect;
	}
}
