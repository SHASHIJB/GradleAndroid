package web.mobile.test;

import java.io.File;

import com.relevantcodes.extentreports.ExtentTest;

public class Constants {

	public static String TEST_CASE_SHEET="Test Cases";
	public static String RUNMODE_YES="Y";
	public static String RUNMODE_NO="N";
	public static String Test_Case_ID="TCID";
	public static String Test_Case_Name="Test Case";
	public static String Test_CaseID="Test ID";
	public static String RUNMODE="Run Mode";
	public static String EmailNotification="Email_Notification";
	public static String TEST_STEPS_SHEET="Test Steps";
	public static String KEYWORD="Keyword";
	public static String TSDescription="Description";
	public static String KEYWORD_PASS="Pass";
	public static String KEYWORD_FAIL="Fail";
	public static String RESULT="Result";
	public static String KEYWORD_SKIP="Skip";
	public static String Object="Object";
	public static String Data="Data";
	public static Object RANDOM_VALUE="Random_Value";
	public static String TEST_SUITE_SHEET="Test Suite";
	public static String Test_Suite_ID="TSID";
	public static String StartTimeCol="Start_Time";
	public static String endTimeCol="End_Time";
	public static String execResultTimeCol="Execution_Result";
	public static String StartTime=null;
	public static String EndTime=null;
	public static ExtentTest logger; 
	public static ExtentTest test=null;
	//Added
	//public static ExtentTest test=new ExtentTest("result","CurrentTestStepDesc") ;
	public static String logDetails;
	public static String file_path;
	public static String verizonCloudLogs=null;
	public static String AppiumLogs=null;
	public static final String ENVIRONMENT = "Env";
	public static String[] SnapshotList=null;
	public static String teststepdesc=null;
	public static String androidTestReport=null;
	public static ExtentTest child; 
	public static String catureVideoShell=Constants.projectBaseDir+"/src/main/java/util/captureVideo.sh";
	public static String pullVideoShell=Constants.projectBaseDir+"/src/main/java/util/pullVideo.sh";
	public static String projectBaseDir=System.getProperty("user.dir");
	public static String imageDir=Constants.projectBaseDir+"/src/main/java/TestData/Images/";
	public static String docDir=Constants.projectBaseDir+"/src/main/java/TestData/MediaFiles/";
	public static String log4J=Constants.projectBaseDir+"/src/main/java/web/mobile/logs/VerizonCloud.log";
	
//#+++++++++++++++++++++++++++++++++++ //Report Directories+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++#//
	public static File sourceFile = new File(Constants.projectBaseDir+"/Report/");
	public static File sourceDirDSR = new File(sourceFile+"/DSR/");
	public static File destinationDir=new File(sourceFile+"/MergeTestReport/");
	public static File destMergeHTMLDir=new File(sourceFile+"/MergeTestReport/HTMLReports/");
	public static File sourceMergeHTMLDir=new File(sourceFile+"/AutomationReports/");
	public static File sourceLogsDir=new File(Constants.projectBaseDir+"/src/main/java/web/mobile/logs/");
	public static File destLogsDir=new File(sourceFile+"/MergeTestReport/Logs/");
	//#+++++++++++++++++++++++++++++++++++ //Localytic logs+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++#//
	
	
	public static File adbLogcatFile = new File(Constants.projectBaseDir+"/src/main/java/web/mobile/logs/LocalyticsLogs/");
	//public static File adbLogcatFile = new File(Constants.projectBaseDir+"/src/web/mobile/logs/Localytics/");
	public static String SettingsLocalyticsFile="Settings_LocalyticsLogs";
	//public static String TestLocalyticsFile="Localytics_LocalyticsLogs";
	//#+++++++++++++++++++++++++++++++++++ //ShellScriptsFiles+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++#//
	
	public static String mergeHTMLFiles = Constants.projectBaseDir+"/src/main/java/util/htmlMerge.sh";
	public static String pullVideo = Constants.projectBaseDir+ "/src/main/java/util/pullVideo.sh";
	public static String captureVideo = Constants.projectBaseDir+ "/src/main/java/util/captureVideo.sh";
	public static String MusicGenerator = Constants.projectBaseDir+ "/src/main/java/util/MusicGenerator.sh";
	public static String ImageGenerator = Constants.projectBaseDir+ "/src/main/java/util/ImageGenerator.sh";
	public static String DataGenerator = Constants.projectBaseDir+ "/src/main/java/util/DataGeneratorNew.sh";
	public static String DocGenerator = Constants.projectBaseDir+ "/src/main/java/util/DocGenerator.sh";
	public static String ScreencastVideoPath=Constants.projectBaseDir+"/ScreenCast";
	
	//#+++++++++++++++++++++++++++++++++++ //Localytics Files+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++#//
	
	
	

	
}
