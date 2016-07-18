package web.mobile.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static main.Intial.url;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;
//Added
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ITest;

import main.Intial;
import web.log.DBLoggerUtil;
import web.log.TestCaseStatus;
import web.log.TestSuiteReportStatus;
import web.mobile.xlsread.Xls_Reader;

public class DriverScript {

	public static Logger APP_LOGS;
	public Xls_Reader suiteXLS;
	public static Xls_Reader masterSuiteXLS;
	public Xls_Reader metadata;
	public int currentTestCaseID;
	public int currentTestStepID;
	public String currentTestCaseName;
	public String inputTestCaseName;
	public int currentTestDataSetID;
	public int currentSuiteID;
	public static String CurrentKeyword;
	public static String currentTestSuite;
	public Keywords keywords;
	public Method method[];
	public static String keywords_execution_result;
	public ArrayList<String> resultset;
	public String object;
	public String optional1;
	public String optional2;
	public static Properties OR;
	public String data;
	public static Properties CONFIG;
	public static Method capturescreenShot_method;
	public static String keyword_execution_result;
	public String testCaseID_fromXLS;
	public String suiteName_db;
	public String suiteID_db;
	public String userName_db;

	public DriverScript() throws NoSuchMethodException, SecurityException, IOException{
		keywords = new Keywords(); 
		method=keywords.getClass().getMethods();
		capturescreenShot_method =keywords.getClass().getMethod("captureScreenshot",String.class,String.class);
		OR = new Properties();
		FileInputStream file = new FileInputStream(Constants.projectBaseDir+"/src/main/java/web/mobile/config/or.properties");
		OR.load(file);
		CONFIG = new Properties();
		FileInputStream file1 = new FileInputStream(Constants.projectBaseDir+"/src/main/java/web/mobile/config/config.properties");
		CONFIG.load(file1);
	}


	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException, InterruptedException, SQLException {
		DriverScript test = new DriverScript();
		test.start();
	}

	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, SQLException{

		masterSuiteXLS=new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/MasterSuite.xlsx");
		System.setProperty("logfile.name",Constants.projectBaseDir+"/src/main/java/web/mobile/logs/VerizonCloud.log");
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		//Starting Logging in DB

		DBLoggerUtil dbLoggerUtil = new DBLoggerUtil();
	
		
		
		int totalTCPassed=0,totalTCFailed=0;
		Long testSuiteID = null;
		int a=masterSuiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
		for(currentSuiteID=2;currentSuiteID<=a;currentSuiteID++){
			currentTestSuite=masterSuiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID);
			if(masterSuiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID).equals(Constants.RUNMODE_YES)){
				metadata=new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/"+currentTestSuite+".xlsx");

				suiteName_db=metadata.getCellData("metadata", "Test Suite Name", 2);
				suiteID_db=metadata.getCellData("metadata", "Test Suite Id", 2);
				userName_db=metadata.getCellData("metadata", "User Name", 2);
				

				ExtentReports extent = new ExtentReports(Constants.projectBaseDir+"/Report/AutomationReports/"+currentTestSuite+".html", true);
				String css1 = ".report-name { padding-left: 10px; } .report-name > img { float: left;height: 90%;margin-left: 30px;margin-top: 2px;width: auto; }";
				String css2 = ".report-name { padding: 2cm; } .report-name > img { float: left;height: 90%;margin-left: 30px;margin-top: 2px;width: auto; }";
				
				extent.config().documentTitle(currentTestSuite);
				String SyncLogo=Constants.projectBaseDir+"/src/main/java/util/SynLogo.png";
				extent.config().reportName("<img src="+"'"+SyncLogo+"'"+"/>").insertCustomStyles(css1);
				//extent.config().reportHeadline(currentTestSuite).insertCustomStyles(css2);
				//extent.config().reportName(currentTestSuite).reportHeadline("Execution Result");
				Map sysInfo = new HashMap();
				String Environment= masterSuiteXLS.getCellData("EnvDetails", "Environment", 2);
				String BuildNo= masterSuiteXLS.getCellData("EnvDetails", "BuildNo.", 2);
				String TesterName=masterSuiteXLS.getCellData("EnvDetails", "TesterName", 2);
				sysInfo.put("Environment", Environment);
				sysInfo.put("BuildNo", BuildNo);
				sysInfo.put("TesterName", TesterName);
				extent.addSystemInfo(sysInfo);

				APP_LOGS.debug("Executing Test Suite - "+currentTestSuite);
				suiteXLS = new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/"+currentTestSuite+".xlsx");
				
				for(currentTestCaseID=2;currentTestCaseID<=suiteXLS.getRowCount(Constants.TEST_CASE_SHEET);currentTestCaseID++){
					APP_LOGS.debug("Started Test Case :-> " +suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.Test_Case_Name, currentTestCaseID)+" and RunMode is "
							+suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.RUNMODE, currentTestCaseID));
					String testcasename = suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.Test_Case_Name, currentTestCaseID);
					String testcasedesc = suiteXLS.getCellData(Constants.TEST_CASE_SHEET,"Description", currentTestCaseID);
					System.out.println("testcasename="+testcasename+"\n"+"testcasedesc="+testcasedesc);
					
					// starting test
					Constants.test = extent.startTest(testcasename, testcasedesc);
					resultset=new ArrayList<String>();
					// Create object of SimpleDateFormat class and decide the format
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


					Calendar sTime = Calendar.getInstance();
					Constants.StartTime=dateFormat.format(sTime.getTime());

					//System.out.println("startTime="+ Constants.StartTime);

					if(suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.RUNMODE, currentTestCaseID).equalsIgnoreCase(Constants.RUNMODE_NO)){
						Constants.test.log(LogStatus.SKIP, "Run Mode has been set to No");
						
					}

					else if(suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.RUNMODE, currentTestCaseID).equalsIgnoreCase(Constants.RUNMODE_YES)){

						currentTestCaseName=suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.Test_Case_Name, currentTestCaseID);
						testCaseID_fromXLS=suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.Test_CaseID, currentTestCaseID);
						
						
						if(currentTestSuite.startsWith("New")){

							if(currentTestCaseName.contains("_")){
								String filename[]=currentTestCaseName.split("_");
								inputTestCaseName= filename[0];
							}else
								inputTestCaseName= currentTestCaseName;
						}else 
							inputTestCaseName= currentTestCaseName;

						if(suiteXLS.isSheetExist(inputTestCaseName)){
							Boolean tcStatus = true;
							for(currentTestDataSetID=2;currentTestDataSetID<=suiteXLS.getRowCount(inputTestCaseName);currentTestDataSetID++){

								APP_LOGS.debug("Iteration Number "+(currentTestDataSetID-1)+" Started.");

								if(suiteXLS.getCellData(inputTestCaseName, Constants.RUNMODE, currentTestDataSetID).equalsIgnoreCase(Constants.RUNMODE_YES)){
									
									Boolean status = executekeywords();
									if (status) {
										//dbLoggerUtil.logTestCaseIterationReport(currentTestCaseName + "_" + (currentTestDataSetID-1), testCaseID, TestCaseStatus.Pass, userName_db);
									} else {
										//dbLoggerUtil.logTestCaseIterationReport(currentTestCaseName + "_" + (currentTestDataSetID-1), testCaseID, TestCaseStatus.Fail, userName_db);
									}
									if(tcStatus){
										tcStatus = status;
									}

									//createXLSReport();
									
								}else{
									
									
									resultset=new ArrayList<String>();
									//createXLSReport();

									APP_LOGS.debug("Iteration Number "+(currentTestDataSetID-1)+" Ended.");	
									for (String s : Reporter.getOutput()) { 
										extent.setTestRunnerOutput(s); 
									}
									
								}


							}
							if (tcStatus) {
								//						
								totalTCPassed++;
								//System.out.println("totalTCPassed"+totalTCPassed);
								
							} else {
								//						
								totalTCFailed++;
								//System.out.println("totalTCFailed"+totalTCFailed);
							}
						}else{
							try{
								executekeywords();
								//createXLSReport();
							}catch(Throwable e){
								APP_LOGS.debug("No Method Found.");
							}
						}
						

					}
					Calendar eTime = Calendar.getInstance();
					Constants.EndTime=dateFormat.format(eTime.getTime());
					
					//System.out.println(Constants.EndTime);
					//masterSuiteXLS.setCellData(Constants.TEST_SUITE_SHEET, Constants.endTimeCol, currentSuiteID,  Constants.EndTime);
				/*	if(suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.RUNMODE, currentTestCaseID).equalsIgnoreCase(Constants.RUNMODE_NO))
					{	Constants.test.log(LogStatus.SKIP, "Run Mode has been set to No");
					extent.endTest(Constants.test);
					
					
					}*/
					extent.flush();
					APP_LOGS.debug("Ended Test Case :-> " +suiteXLS.getCellData(Constants.TEST_CASE_SHEET,Constants.Test_Case_ID, currentTestCaseID));
					for (String s : Reporter.getOutput()) { 
						extent.setTestRunnerOutput(s); 
					}
					extent.endTest(Constants.test);
					
				}
				Constants.test.log(LogStatus.INFO, "Screencast below: " + Constants.test.addScreencast(Constants.ScreencastVideoPath));
				extent.flush();
				APP_LOGS.debug("Completed Test Suite - "+currentTestSuite);
				Constants.test.log(LogStatus.INFO, "VZ Cloud Android Test Execution Logs:-->  " + Constants.verizonCloudLogs);
				Constants.test.log(LogStatus.INFO, "VZ Cloud Android Appium Logs:-->  " + Constants.AppiumLogs);
				for (String s : Reporter.getOutput()) { 
					extent.setTestRunnerOutput(s); 
					
				}
				//Constants.test.log(LogStatus.INFO, "Screencast below: " + Constants.test.addScreencast(Constants.ScreencastVideoPath));

				//extent.config().insertCustomStyles(css);
				//extent.endTest(Constants.test);
				new Keywords().capabilities = null;
				
			
			}else
			{
				APP_LOGS.debug("Test Suite not Executed- "+currentTestSuite);
				
			}
			
		}
		
	}


	public Boolean executekeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Boolean result = false;

		for(currentTestStepID=2;currentTestStepID<=suiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);currentTestStepID++){


			data=suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.Data, currentTestStepID);
			if(data.startsWith("Col")){
				data=suiteXLS.getCellData(inputTestCaseName, data.split("\\|")[1], currentTestDataSetID);
			}else if(data.startsWith("Config")){
				data=CONFIG.getProperty(data.split("\\|")[1]);
			}else if(data.startsWith("Var")){
				data=CONFIG.getProperty(data.split("\\|")[1]);
			}
			else{
				data=CONFIG.getProperty(data);//suiteXLS.getCellData(currentTestCaseName, Constants.Data, currentTestDataSetID);
			}

			object=suiteXLS.getCellData(Constants.TEST_STEPS_SHEET,Constants.Object, currentTestStepID);


			if(suiteXLS.getCellData(Constants.TEST_STEPS_SHEET,Constants.Test_Case_ID, currentTestStepID).contains(currentTestCaseName)){

				CurrentKeyword=suiteXLS.getCellData(Constants.TEST_STEPS_SHEET,Constants.KEYWORD, currentTestStepID);
				String CurrentTestStepDesc=suiteXLS.getCellData(Constants.TEST_STEPS_SHEET,Constants.TSDescription, currentTestStepID);

				APP_LOGS.debug("Running Keyword is : "+CurrentKeyword);

				System.out.println("Test Step Desc: "+CurrentTestStepDesc);

				for(int i=0;i<method.length;i++){
					try{
						if(method[i].getName().equals(CurrentKeyword)){
							keywords_execution_result=(String)method[i].invoke(keywords,object,data);
							APP_LOGS.debug(keywords_execution_result);
							if(keywords_execution_result.contains("Pass"))
								Constants.test.log(LogStatus.PASS,CurrentTestStepDesc);	
							else if (keywords_execution_result.contains("Fail"))
							{
								resultset.add(keywords_execution_result);
								Constants.test.log(LogStatus.FAIL,CurrentTestStepDesc+"\n\n"+"*1Error Desc--->" +keywords_execution_result );
							
							capturescreenShot_method.invoke(keywords,currentTestCaseName+"_TS"+currentTestStepID+"_"+(currentTestDataSetID-1),	keywords_execution_result);	
							Constants.test.log(LogStatus.INFO, "Snapshot below: " + Constants.test.addScreenCapture(Constants.file_path));
							break;
							
							}
						}
						
					}catch(Throwable e){
							keywords_execution_result="Fail";
							//Added
							System.out.println("Logging");
							Constants.test.log(LogStatus.FAIL,CurrentTestStepDesc+"\n\n"+"*2Error Desc--->" +keywords_execution_result );
							
							APP_LOGS.debug(keywords_execution_result);
							resultset.add(keywords_execution_result);
							capturescreenShot_method.invoke(keywords,currentTestCaseName+"_TS"+currentTestStepID+"_"+(currentTestDataSetID-1),	keywords_execution_result);	
							Constants.test.log(LogStatus.INFO, "Snapshot below: " + Constants.test.addScreenCapture(Constants.file_path));
						}
					
				}	
				
			}	
			
		}

		for (String str : resultset) {
			if(str.contains("Fail")){
				
				result = false;
				
				break;
			}else{
				result = true;
			}
			
		}
		
		return result;		
		
	}


	public void createXLSReport(){
		try{

			if(!suiteXLS.isSheetExist(inputTestCaseName)){

				int index=0;
				for(int i=2;i<=suiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
					//1234
					if(suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.Test_Case_ID, i).contains(currentTestCaseName)){
						if(resultset.size()==0)
							suiteXLS.setCellData(Constants.TEST_STEPS_SHEET, suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, 6, 1), i, Constants.KEYWORD_SKIP);


						else	
							suiteXLS.setCellData(Constants.TEST_STEPS_SHEET, suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, 6, 1), i, resultset.get(index));
						System.out.println(resultset.get(index));
						index++;
					}
				}
			}else{

				String colName=Constants.RESULT +(currentTestDataSetID-1);

				boolean isColExist=false;

				for(int c=0;c<suiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);c++){
					if(suiteXLS.getCellData(Constants.TEST_STEPS_SHEET,c , 1).equals(colName)){
						isColExist=true;
						break;
					}
				}
				int startcounter=0;
				int endcounter=0;
				if(!isColExist)
					suiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
				int index=0;

				for(int i=2;i<=suiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
					if(suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.Test_Case_ID, i).contains(currentTestCaseName)){
						startcounter=i;
						break;
					}
				}


				for(int i=startcounter;i<=resultset.size()+startcounter;i++){
					//1234
					if(suiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.Test_Case_ID, i).contains(currentTestCaseName)){
						if(resultset.size()==0)
							suiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);


						else	
							suiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultset.get(index));
						index++;
					}
				}

				if(resultset.size()==0){
					// skip means the keywords of the test are not executed so mark as SKIP
					suiteXLS.setCellData(inputTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
					return;
				}else{
					for(int i=0;i<resultset.size();i++){
						if(!resultset.get(i).equals(Constants.KEYWORD_PASS)){
							//	currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, resultSet.get(i));
							suiteXLS.setCellData(inputTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_FAIL);


							return;
						}
					}
				}
				suiteXLS.setCellData(inputTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);

			}
			//System.out.println(Constants.StartTime);
			//masterSuiteXLS.setCellData(Constants.TEST_SUITE_SHEET, Constants.StartTimeCol, currentSuiteID, Constants.StartTime);


		}catch(Throwable e){
			System.out.println(e.getMessage());
		}

	}
}
