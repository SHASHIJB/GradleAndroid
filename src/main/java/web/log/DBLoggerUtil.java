package web.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DBLoggerUtil {

	/**
	 * Method to make astarted entry in testSuiteReportStatus table once
	 * testsuite starts to run
	 * 
	 * @param testSuiteReportName
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public Long logTestSuiteReport(String testSuiteReportName,
			TestSuiteReportStatus status, String userName) throws SQLException {

		Long generatedID = null;
		String insertTableSQL = " INSERT INTO `test_automation`.`test_suite_report` (`testSuiteReportName`, `testSuiteReportStatus`, "
				+ "`testCasePassed`, `testCaseFailed`, `createdOn`,`lastUpdatedOn`, `createdBy`, `lastUpdatedBy`)"
				+ " VALUES (?,?,?,?,?,?,?,?); ";

		Connection con = DBConnection.getConnect();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = con.prepareStatement(insertTableSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, testSuiteReportName);
			preparedStatement.setString(2, status.toString());
			preparedStatement.setInt(3, 0);
			preparedStatement.setInt(4, 0);
			preparedStatement.setString(5, getDateTime());
			preparedStatement.setString(6, getDateTime());
			preparedStatement.setString(7, userName);
			preparedStatement.setString(8, userName);

			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			while (rs.next()) {
				System.out.println("Key returned from getGeneratedKeys():"
						+ rs.getInt(1));
				generatedID = Long.valueOf(rs.getInt(1));

			}
			rs.close();
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return generatedID;
	}

	/**
	 * Method to Mark a testSuite Completed in testSuiteReportStatus table once
	 * all test cases for that suite are over
	 * 
	 * @param testSuiteID
	 * @throws SQLException
	 */
	public void markTestSuiteCompleted(long testSuiteID, int totalTCPassed,
			int totalTCFailed) throws SQLException {
		String insertTableSQL = "UPDATE `test_automation`.`test_suite_report` SET `testSuiteReportStatus` = ?,`testCasePassed` = ? , testCaseFailed = ?,lastUpdatedOn = ?  WHERE `id` = ?; ";

		Connection con = DBConnection.getConnect();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = con.prepareStatement(insertTableSQL);

			preparedStatement.setString(1,
					TestSuiteReportStatus.COMPLETED.toString());
			preparedStatement.setInt(2, totalTCPassed);
			preparedStatement.setInt(3, totalTCFailed);
			preparedStatement.setString(4, getDateTime());

			preparedStatement.setLong(5, testSuiteID);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}

	/**
	 * Method to save test run data for each test case in
	 * test_suite_test_case_report table
	 * 
	 * @param testCaseName
	 * @param testCaseID
	 * @param testSuiteReportId
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public Long logTestCaseReport(String testCaseName, String testCaseID,
			Long testSuiteReportId, String UserName) throws SQLException {

		Long generatedID = null;
		String insertTableSQL = " INSERT INTO `test_automation`.`test_suite_test_case_report`"
				+ " (`testCaseName`, `testCaseId`, `testSuiteReportId`, `createdOn`,`lastUpdatedOn`, `createdBy`, `lastUpdatedBy`) "
				+ "  VALUES (?,?,?,?,?,?,?); ";

		Connection con = DBConnection.getConnect();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = con.prepareStatement(insertTableSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, testCaseName);
			preparedStatement.setString(2, testCaseID);
			preparedStatement.setLong(3, testSuiteReportId);
			preparedStatement.setString(4, getDateTime());
			preparedStatement.setString(5, getDateTime());
			preparedStatement.setString(6, UserName);
			preparedStatement.setString(7, UserName);
			
			
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			while (rs.next()) {
				System.out.println("Key returned from getGeneratedKeys():"
						+ rs.getInt(1));
				generatedID = Long.valueOf(rs.getInt(1));

			}
			rs.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

		}
		return generatedID;
	}

	public void UpdateTestCaseStatus(long testCaseID,
			TestCaseStatus testCaseStatus) throws SQLException {
		String insertTableSQL = "UPDATE `test_automation`.`test_suite_test_case_report` SET `testCaseStatus` = ?  WHERE `id` = ?; ";

		Connection con = DBConnection.getConnect();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = con.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, testCaseStatus.toString());
			preparedStatement.setLong(2, testCaseID);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}

	/**
	 * @param testItrName
	 * @param testCaseID
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public Long logTestCaseIterationReport(String testItrName, Long testCaseID,
			TestCaseStatus status, String userName) throws SQLException {

		Long generatedID = null;
		String insertTableSQL = " INSERT INTO `test_automation`.`test_suite_test_case_iteration_report` (`iterationName`, `iterationStatus`, `testCaseId`, `createdOn`, `createdBy`) "
				+ "  VALUES (?,?,?,?,?); ";

		Connection con = DBConnection.getConnect();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = con.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, testItrName);
			preparedStatement.setString(2, status.toString());
			preparedStatement.setLong(3, testCaseID);
			preparedStatement.setString(4, getDateTime());
			preparedStatement.setString(5, userName);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

		}
		return generatedID;
	}

	/**
	 * Method to get DATE in MYSQL format
	 * 
	 * @return
	 */
	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/*
	 * public static void main(String[] args) { DBLoggerUtil obj = new
	 * DBLoggerUtil();
	 * 
	 * try {
	 * 
	 * for (int i = 0; i < 3; i++) { Long id = obj.logTestSuiteReport("",
	 * TestSuiteReportStatus.STARTED);
	 * System.out.println("ID generated is >>>>>>>>>>>>" + id);
	 * 
	 * Long testCaseID = obj.logTestCaseReport("TCNAMEFROMJAVA", "TC22", id);
	 * Boolean tcStatus = true;
	 * 
	 * for (int j = 0; j < 3; j++) { Random random = new Random(); Boolean
	 * status = random.nextBoolean(); System.out.println("status >>>" + status);
	 * if (status) { obj.logTestCaseIterationReport("T_ITR"+j, testCaseID,
	 * TestCaseStatus.Pass); } else { obj.logTestCaseIterationReport("T_ITR"+j,
	 * testCaseID, TestCaseStatus.Fail); }
	 * 
	 * if(tcStatus){ tcStatus = status; } }
	 * 
	 * if (tcStatus) { obj.UpdateTestCaseStatus(testCaseID,
	 * TestCaseStatus.Pass); } else { obj.UpdateTestCaseStatus(testCaseID,
	 * TestCaseStatus.Fail); }
	 * 
	 * obj.markTestSuiteCompleted(id);
	 * 
	 * } } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */
}
