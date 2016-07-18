package main;

import static web.mobile.test.DriverScript.OR;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import main.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.ReportUtil;
import web.mobile.test.Constants;
import web.mobile.test.Keywords;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.relevantcodes.extentmerge.model.Customizer;
import com.relevantcodes.extentmerge.model.Report;
import com.sun.jna.platform.win32.WinUser.INPUT;

import web.mobile.test.DriverScript;
import web.mobile.xlsread.Xls_Reader;

public class Intial extends CopySheets{
	
	private static final String Input = null;

	public static AndroidDriver driver;
	public static WebDriver webDriver;
	public static String flash_ObjectId;

	public static Xls_Reader read;
	public static String file_Name;
	public static DesiredCapabilities capabilities;
	public static String url;
	
	public static String ScreencastVideoPath;
	
	public static void main(String[] args) throws Exception {
		Constants.verizonCloudLogs=Constants.projectBaseDir+"/src/main/java/web/mobile/logs/VerizonCloud.log";
		Constants.AppiumLogs=Constants.projectBaseDir+"/src/main/java/web/mobile/logs/appiumLogs.txt";
		ScreencastVideoPath=Constants.projectBaseDir+"/src/main/java/web/mobile/logs/ScreenCast/movie.mp4";
		startAppiumServer();
		Thread.sleep(5000); 
		ReportUtil.purgeDirectoryButKeepSubDirectories(Constants.sourceMergeHTMLDir);
		ReportUtil.purgeDirectoryButKeepSubDirectories(Constants.destMergeHTMLDir);
		//objKWDS.executeShell("sh"+" "+Constants.captureVideo,"null");
		createfolder();
		DriverScript obj= new DriverScript();
	
		obj.start(); 
		//objKWDS.executeShell("sh"+" "+Constants.pullVideo,"null");
		stopAppiumServer();
		ReportUtil.createReportDir();
	}   

	public static void createfolder(){
		Date dNow1 = new Date();
		SimpleDateFormat date = new SimpleDateFormat ("MM_dd_yyyy");
		file_Name=date.format(dNow1);
		File file = new File(Constants.projectBaseDir+"/screenshots/"+file_Name);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	public static  void startAppiumServer() throws IOException, InterruptedException { 

		/* * Start Appium server **/
		System.out.println("****************setUP Starts****************");
		String[] killNode ={"/usr/bin/killall","-KILL","node"};  
		Runtime.getRuntime().exec(killNode);
		CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
		command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/build/lib/main.js",false);
		command.addArgument("--address");
		command.addArgument("127.0.0.1");
		command.addArgument("--bootstrap-port");

		command.addArgument("4242");
		command.addArgument("--no-reset");
		//command.addArgument("--full-reset");


		command.addArgument("--log");
		command.addArgument(Constants.AppiumLogs);

		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();  
		DefaultExecutor executor = new DefaultExecutor();  
		executor.setExitValue(1);  
		executor.execute(command, resultHandler);  
		Thread.sleep(5000);  
		System.out.println("Appium server started");  

	}	


	public static  void stopAppiumServer() throws IOException { 

		/*	
		 * Stopping the Appium server.
		 **/
		String[] command ={"/usr/bin/killall","-KILL","node"};  
		Runtime.getRuntime().exec(command);  
		System.out.println("Appium server stop");	
	}


}
