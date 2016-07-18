//
package web.mobile.test;

import static web.mobile.test.DriverScript.APP_LOGS;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import static main.Intial.file_Name;
import static web.mobile.test.DriverScript.OR;
import static web.mobile.test.DriverScript.CurrentKeyword;
import static web.mobile.test.DriverScript.CONFIG;
import static web.mobile.test.DriverScript.keywords_execution_result;
import static main.Intial.read;
import static main.Intial.url;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import static web.mobile.test.DriverScript.currentTestSuite;
import io.appium.java_client.ios.IOSKeyCode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;




















import java.util.logging.Level;

import main.SystemCommandExecutor;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import com.jayway.android.robotium.solo.Solo;

import util.ReportUtil;
import web.mobile.xlsread.Xls_Reader;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;

import static main.Intial.capabilities;
import static main.Intial.driver;
import static main.Intial.webDriver;

public class Keywords {

	public static DesiredCapabilities capabilities;
	public Xls_Reader suitefile;
	public String logs;
	private AppiumDriver Appiumdriver;
	public PrintWriter log_file_writer;
	public StringBuilder stdout,stderr;
	public static String bef_count,aft_cnt;

	public String changeview(String object, String data ) throws InterruptedException, MalformedURLException{
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {				
			if(contextName.contains(object)){
				driver.context(contextName);
			}
			System.out.println(contextNames); 
		}
		return Constants.KEYWORD_PASS;
	}
	
	

	public String openAppTablet(String object, String data ) {



		suitefile = new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/"+currentTestSuite+".xlsx");
		capabilities = new DesiredCapabilities();


		capabilities.setCapability("browserName", "Android");
		capabilities.setCapability("VERSION", "5.0.2"); 
		capabilities.setCapability("deviceName","VX700");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		capabilities.setCapability("deviceType","phone");
		capabilities.setCapability("platformName",Platform.ANDROID);
		capabilities.setCapability("appPackage", "com.vcast.mediamanager");
		capabilities.setCapability("appActivity", "com.newbay.syncdrive.android.ui.nab.NabSplashLogoActivity");
		String host = "127.0.0.1";
		String port = "4723";
		try {
			driver = new AndroidDriver(
					new URL("http://" + host + ":" + port + "/wd/hub"),
					capabilities);

			
			
			//generateLocalyticsLogs(driver,Constants.SettingsLocalyticsFile);
		}
		catch(Exception e) {

		}

		Constants.logDetails="App Launched Successfullly";
		return Constants.KEYWORD_PASS;

	}
	
	public String openApp(String object, String data ) {



		suitefile = new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/"+currentTestSuite+".xlsx");
		capabilities = new DesiredCapabilities();


		capabilities.setCapability(CapabilityType.VERSION,suitefile.getCellData("Environment Variables", "VERSION", 2));
		capabilities.setCapability(CapabilityType.PLATFORM,suitefile.getCellData("Environment Variables", "Platform Name", 2));
		capabilities.setCapability("deviceName" ,suitefile.getCellData("Environment Variables", "Device Name", 2));
		//capabilities.setCapability("App",System.getProperty("user.dir") + "/src/APKFiles/client-16.4.2-prod-phone-release.apk");
		capabilities.setCapability("appPackage" ,suitefile.getCellData("Environment Variables", "appPackage", 2));
		capabilities.setCapability("appActivity",suitefile.getCellData("Environment Variables", "appActivity", 2));
        capabilities.setCapability("newCommandTimeout",60);

		String host = "127.0.0.1";
		String port = "4723";

		try {
			driver = new AndroidDriver(
					new URL("http://" + host + ":" + port + "/wd/hub"),
					capabilities);

			
			
			//generateLocalyticsLogs(driver,Constants.SettingsLocalyticsFile);
		}
		catch(Exception e) {

		}

		Constants.logDetails="App Launched Successfullly";
		return Constants.KEYWORD_PASS;

	}


	public String clearPreferences(String object, String data) throws InterruptedException {
		try {
			// clearing app data
			System.exit(0);
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("pm clear com.vcast.mediamanager");

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}
	public  String verifyListItems(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Verify all items are listed in the drop drown as expected");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allElements =  data.split(",");
		String[] allobjects =  object.split(",");

		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement droplist=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> droplist_cotents = droplist.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int actualCount = droplist_cotents.size();
			System.out.println("Actual contextual menu list items total count: "+actualCount);
			System.out.println("Expected contextual menu list items total count:"+allElements.length);
			int expElemIndex=(allElements.length);
			if(actualCount==allElements.length)
			{				

				for(int i=0;i<expElemIndex;i++)		
				{	
					WebElement mainElement= droplist_cotents.get(i);
					String expDropDownItem=allElements[i];

					String ActDropDownItem=mainElement.findElement(By.className(OR.getProperty(allobjects[2]))).getText();

					System.out.println("Expected List Item "+(i+1)+"="+expDropDownItem);
					System.out.println("Actual List Item "+(i+1)+"="+ActDropDownItem);

					if(expDropDownItem.trim().equalsIgnoreCase(ActDropDownItem))
					{
						System.out.println("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem);
						APP_LOGS.debug("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem);

					}	
					else
						return Constants.KEYWORD_FAIL+ "Contextual menu list item doesnt match as expected";
				}	
			}
			else
				return Constants.KEYWORD_FAIL+ "Contextual menu list items count doesnt match as expected";

		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Object not visible"+ e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public  String selectRadioButton(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Selecting from list");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement droplist=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> radioButton = droplist.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int radioCount = radioButton.size();
			System.out.println("Actual contextual menu list items total count: "+radioCount);


			for(int i=0;i<radioCount;i++)		
			{	
				WebElement mainElement= radioButton.get(i);

				String ActDropDownItem=mainElement.findElement(By.className(OR.getProperty(allobjects[2]))).getText();

				System.out.println("Expected radioItem "+(i+1)+"="+data);
				System.out.println("ActDropDownItem "+(i+1)+"="+ActDropDownItem);
				if(ActDropDownItem.trim().equalsIgnoreCase(data.trim()))
				{
					System.out.println("List Item matches as expected "+ActDropDownItem+" -- "+data);

					mainElement.click();
					break;
				}			}	   
			return Constants.KEYWORD_PASS;
		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from radio button. "+ e.getMessage();
		}
	}

	public  String selectImages(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Selecting images from backed up photo");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		int selectImagesCount=Integer.parseInt(data);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectImages = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int imagesCount = selectImages.size();
			System.out.println("Total Backed Up Photo: "+imagesCount);


			for(int i=0;i<selectImagesCount;i++)		
			{	
				WebElement mainElement= selectImages.get(i);


				mainElement.click();

			}	   
			return Constants.KEYWORD_PASS;
		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from item "+ e.getMessage();
		}
	}
	public  String selectAll_Images(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Selecting images from backed up photo");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");

		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectImages = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int imagesCount = selectImages.size();
			System.out.println("Total Backed Up Photo: "+imagesCount);


			for(int i=0;i<imagesCount;i++)		
			{	
				WebElement mainElement= selectImages.get(i);


				mainElement.click();

			}	   
			return Constants.KEYWORD_PASS;
		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select item "+ e.getMessage();
		}
	}

	public  String selectCheckboxes(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Select checkboxes based on the input data");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		String[] allelements= data.split(",");
		int selectCheckBxCount=allelements.length;
		System.out.println("Total checkbox to be selected based on the Test Data file"+selectCheckBxCount);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{

			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectChkBx = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int checkboxCount = selectChkBx.size();


			System.out.println("Total checkbox Count: "+checkboxCount);


			for(int i=0;i<checkboxCount;i++)		
			{	

				for(int j=0;i<selectCheckBxCount;j++)
				{
					WebElement content= selectChkBx.get(j);

					WebElement checkBox=content.findElement(By.className("android.widget.CheckBox"));
					String isChecked =checkBox.getAttribute("checked");
					WebElement checkBoxTxtObj=content.findElement(By.className("android.widget.TextView"));
					String checkBoxTxt=checkBoxTxtObj.getText();
					if(checkBoxTxt.trim().equals(allelements[i].trim()))
					{

						if(isChecked.equals("false"))
						{
							checkBox.click(); //check it
							break;
						}
						else if(isChecked.equals("true"))

							System.out.println("Checkbox already checked, hence not selecting");
						break;
					}


				}

			}


		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from item "+ e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}
	public  String uncheck_Checkboxes(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Uncheck checkboxes based on the data provided in the test data sheet");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		String[] allelements= data.split(",");
		int selectCheckBxCount=allelements.length;
		System.out.println("Total checkbox to be unchecked based on the Test Data file"+selectCheckBxCount);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{

			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectChkBx = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int checkboxCount = selectChkBx.size();


			System.out.println("Total checkbox Count: "+checkboxCount);


			for(int i=0;i<checkboxCount;i++)		
			{	

				for(int j=0;i<selectCheckBxCount;j++)
				{
					WebElement content= selectChkBx.get(j);

					WebElement checkBox=content.findElement(By.className("android.widget.CheckBox"));
					WebElement checkBoxTxtObj=content.findElement(By.className("android.widget.TextView"));
					String checkBoxTxt=checkBoxTxtObj.getText();
					if(checkBoxTxt.trim().equals(allelements[i].trim()))
					{
						if(checkBox.getAttribute("checked") != null)
						{
							checkBox.click(); //check it
							break;}

						else if(checkBox.getAttribute("checked") ==null)

							System.out.println("Not Checked");
						break;
					}


				}

			}


		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from item "+ e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}
	public  String selectContent(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Selecting Content");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		int selectImagesCount=Integer.parseInt(data);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectImages = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int imagesCount = selectImages.size();


			System.out.println("Total selected content count: "+imagesCount);
			//selectImages.getClass();

			for(int i=0;i<selectImagesCount;i++)		
			{	

				WebElement content= selectImages.get(i+1);

				WebElement mainElement=content.findElement(By.className("android.widget.RelativeLayout"));

				mainElement.click();

			}	   

		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from item "+ e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public  String verifyAddedItem(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Verify item(i.e. album, photo,video etc  has been added and displayed as expected");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		int selectImagesCount=Integer.parseInt(data);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> selectImages = imagesFrame.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int imagesCount = selectImages.size();
			System.out.println("Total list items: "+imagesCount);		

			if(imagesCount==selectImagesCount)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- List items doesnt match as expected-";

		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Object not visible "+ e.getMessage();
		}
	}

	public  String compareListItem(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("compare List Item");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allElements =  data.split(",");
		String[] allobjects =  object.split(",");
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement droplist=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> radioButton = droplist.findElements(By.className(OR.getProperty(allobjects[1]))); 
			int actListCount = radioButton.size();
			int expListCount=allElements.length;
			System.out.println("Actual List count: "+actListCount);
			System.out.println("Exp List count: "+expListCount);

			if(expListCount==actListCount)
			{

				for(int i=0;i<expListCount;i++)		
				{	
					WebElement mainElement= radioButton.get(i);
					String ActListItem=mainElement.findElement(By.className(OR.getProperty(allobjects[2]))).getText();
					String expListItem=allElements[i];
					System.out.println("Expected List Item "+(i+1)+"="+expListItem);
					System.out.println("Actual List Item "+(i+1)+"="+ActListItem);


					if(ActListItem.trim().equals(expListItem.trim()))
					{
						System.out.println("List Item matches as expected "+expListItem+" -- "+ActListItem);
						APP_LOGS.debug("List Item matches as expected "+expListItem+" -- "+ActListItem);
					}
					else
						return Constants.KEYWORD_FAIL+" -- text not verified "+expListItem+" -- "+ActListItem;
				}
			}

			return Constants.KEYWORD_PASS;
		}   


		catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from radio button. "+ e.getMessage();
		}

	}
	public  String compareRadioList(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("compare List Item");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allElements =  data.split(",");
		String[] allobjects =  object.split(",");
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement droplist=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> radioButton = droplist.findElements(By.className(OR.getProperty(allobjects[1]))); 
			int actListCount = radioButton.size();
			int expListCount=allElements.length;
			System.out.println("Actual List count: "+actListCount);
			System.out.println("Exp List count: "+expListCount);

			if(expListCount==actListCount)
			{

				for(int i=0;i<expListCount;i++)		
				{	

					String ActListItem=radioButton.get(i).getText();
					String expListItem=allElements[i];
					System.out.println("Expected List Item "+(i+1)+"="+expListItem);
					System.out.println("Actual List Item "+(i+1)+"="+ActListItem);


					if(ActListItem.trim().equalsIgnoreCase(expListItem.trim()))
					{
						System.out.println("List Item matches as expected "+expListItem+" -- "+ActListItem);
						APP_LOGS.debug("List Item matches as expected "+expListItem+" -- "+ActListItem);
					}
					else
						
					
					return Constants.KEYWORD_FAIL+"****Actual value doesnt match with expected****"+"\n" +"ExpValue=" +expListItem+"\n"+"ActualValue="+ActListItem;
				}
			}

			return Constants.KEYWORD_PASS;
		}   


		catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Could not select from radio button. "+ e.getMessage();
		}

	}
	
	public String verifyElementEnabled(String object, String data) throws InterruptedException{

		wait(object);
		APP_LOGS.debug("Verify element is enabled");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));

		try{

			if(driver.findElement(By.xpath(OR.getProperty(object))).isEnabled()){

				System.out.println("Element is present");
				return Constants.KEYWORD_PASS;	
			}else{
				return Constants.KEYWORD_PASS;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}
	public String verifyText(String object, String data)
	{
		try{
			Thread.sleep(2500);
			APP_LOGS.debug("Verifying the text");
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			actual.trim();
			String expected=data.trim();
			System.out.println("Expect Text:"+expected);
			System.out.println("Actual Text:"+actual);

			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+"****Actual value doesnt match with expected****"+"\n" +"ExpValue=" +expected+"\n"+"ActualValue="+actual;


		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public String verifyData(String object, String data)
	{
		try{
			Thread.sleep(2500);
			APP_LOGS.debug("Verifying the text");
			String actual=driver.findElement(By.id(OR.getProperty(object))).getText();

			int actualData=Integer.parseInt(actual.trim());
			int expected=Integer.parseInt(data.trim());
			System.out.println("Expect Text:"+expected);
			System.out.println("Actual Text:"+actual);

			if(actualData==expected)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- data doesnt match with exp value "+actual+" -- "+expected;
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public String swipe(String object, String data)
	{ 
		try{
			Dimension size = driver.manage().window().getSize(); 
			int startx = (int) (size.width * 0.8); 
			int endx = (int) (size.width * 0.20); 
			int starty = size.height / 2; 
			boolean a =true;

			while (((MobileElement)driver.findElement(By.xpath(OR.getProperty(object)))).isDisplayed() == false) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				HashMap<String, Integer> swipeObject = new HashMap<String, Integer>();
				swipeObject.put("startX", startx);
				swipeObject.put("startY", starty);
				swipeObject.put("endX", endx);
				swipeObject.put("endY", 1);
				swipeObject.put("duration", 1);
				js.executeScript("mobile: swipe", swipeObject);

			}
			return Constants.KEYWORD_PASS;
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}
	
	
	public static void executeShell(String Filename, String data){
		APP_LOGS.debug("Generate Merge Report");
		int iExitValue;
	    String sCommandString;

		try{
			sCommandString = Filename;
	        CommandLine oCmdLine = CommandLine.parse(sCommandString);
	        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
	        oDefaultExecutor.setExitValue(0);
	        try {
	            iExitValue = oDefaultExecutor.execute(oCmdLine);
	        } catch (ExecuteException e) {
	            System.err.println("Execution failed.");
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.err.println("permission denied.");
	            e.printStackTrace();
	        }
	    


		}catch(Exception e){

			System.out.println(e.getMessage());
		}




	}

	public String scrollTo(String object, String data)throws InterruptedException
	{ 
		//wait(object);
		APP_LOGS.debug("scroll to");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);

		try{
			synchronize(null,null);
			driver.scrollTo(data);
			synchronize(null,null);
			System.out.println("Scroll data :"+data);
			//driver.scrollToExact(data);

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}

	}



	public String swipe_name(String object, String data)
	{ 
		try{
			Dimension size = driver.manage().window().getSize(); 
			int startx = (int) (size.width * 0.8); 
			int endx = (int) (size.width * 0.20); 
			int starty = size.height / 2; 
			boolean a =true;

			while (((MobileElement)driver.findElement(By.name(OR.getProperty(object)))).isDisplayed() == false) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				HashMap<String, Integer> swipeObject = new HashMap<String, Integer>();
				swipeObject.put("startX", startx);
				swipeObject.put("startY", starty);
				swipeObject.put("endX", endx);
				swipeObject.put("endY", 1);
				swipeObject.put("duration", 1);
				js.executeScript("mobile: swipe", swipeObject);

			}
			return Constants.KEYWORD_PASS;
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}
	public String swipe_up(String object, String data)
	{ 
		try{

			while (driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed() == false) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				HashMap<String, String> swipeObject = new HashMap<String, String>();
				swipeObject.put("direction","up");

				driver.executeScript("mobile: scroll", swipeObject);
			}
			return Constants.KEYWORD_PASS;
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}


	public String enterText(String object, String data){

		// currently taking the input data from Config properties file
		APP_LOGS.debug("entering the text on ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue"+object);
		APP_LOGS.debug("data value is"+data);

		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}


	
	public String writeInputDone(String object, String data){
		//	wait(object);
		// currently taking the input data from Config properties file
		APP_LOGS.debug("Performing entering the text in text box ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);
		System.out.println("Input parameter ::-> "+data);
		try{
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(5000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			
			Thread.sleep(5000);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}
	/*public String writeInputDone_id(String object, String data){
		//	wait(object);
		// currently taking the input data from Config properties file
		APP_LOGS.debug("Performing entering the text in text box ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);
		System.out.println("Input parameter ::-> "+data);
		try{
			((MobileElement)driver.findElement(By.id(OR.getProperty(object)))).setValue(data);
			((MobileElement)driver.findElement(By.name("Done"))).click();
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}*/
	
	public String writeInput_sendkeys(String object, String data){
		//	wait(object);
		// currently taking the input data from Config properties file
		APP_LOGS.debug("Performing entering the text in text box ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);
		System.out.println("Input parameter ::-> "+data);
		try{
			//((MobileElement)driver.findElement(By.xpath(OR.getProperty(object)))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			//((MobileElement)driver.findElement(By.name("Done"))).click();
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}

	public String writeInput_id(String object, String data){
		//	wait(object);
		// currently taking the input data from Config properties file
		APP_LOGS.debug("Performing entering the text in text box ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);
		System.out.println("Input parameter ::-> "+data);
		try{
			wait(object);
			//driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}
	
	

	public String clickLink(String object, String data){
		wait(object);
		APP_LOGS.debug("clicking the link");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);

		try{
			wait(object);

			((MobileElement)driver.findElement(By.xpath(OR.getProperty(object)))).click();

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		//}
	}

	public String clickLink_name(String object, String data){
		wait(object);
		APP_LOGS.debug("clicking the link");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);

		try{
			((MobileElement)driver.findElement(By.name(OR.getProperty(object)))).click();

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		//}
	}




	public String clickButton(String object, String data){

		APP_LOGS.debug("clicking the button");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{
			wait(object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			//	Thread.sleep(2000L);
			return Constants.KEYWORD_PASS;
		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
	}


	public String switchto(String object, String data) throws InterruptedException{
		APP_LOGS.debug("switching to iframe");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value of frame is ->> "+OR.getProperty(object));
		try{
			driver.switchTo().frame(driver.findElement(By.xpath(OR.getProperty(object))));
			//	Thread.sleep(4000L);
			return Constants.KEYWORD_PASS;
		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}

	public String verifyequal(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Comparing excepted and actual value");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Excepted value is "+data);

		try{
			String actualval=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			APP_LOGS.debug("Actual value is "+actualval);
			if(actualval.equals(data))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+ " Value are not equal.";

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage()+"values do not match Comparision failed";
		}
	}


	public String verifytextlength(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Comparing excepted and actual value");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Excepted value is "+data);

		try{
			String actualval=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			APP_LOGS.debug("Actual value is "+actualval);
			if(actualval.length() > 0)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+ " Value are not equal.";

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage()+"values do not match Comparision failed";
		}
	}

	public String verifyblanktext(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Comparing excepted and actual value");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Excepted value is "+data);

		try{
			String actualval=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("name");
			APP_LOGS.debug("Actual value is "+actualval);
			if(actualval.length() == 0)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+ " Value are not equal.";

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage()+"values do not match Comparision failed";
		}
	}

	public String verifyattr(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Comparing excepted and actual value");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue is  ->> "+OR.getProperty(object));
		//	Thread.sleep(10000l);
		try{
			String actualval=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			if(actualval.contains(data)) 
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage()+"values do not match Comparision failed";
		}
	}	
	public String verifycontains(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("Comparing excepted and actual value");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue is  ->> "+OR.getProperty(object));
		//     Thread.sleep(10000l);
		try{
			String actualval=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			// String actualval=driver.findElement(By.xpath("//UIAStaticText[@name='REGISTRY NUMBER:']")).getText();

			APP_LOGS.debug("Excepted value is  ->> "+data);
			APP_LOGS.debug("Actual value is  ->> "+actualval);
			if(data!="")
			{
				data=data.toUpperCase();
				actualval=actualval.toUpperCase();
				int actualvalLenght=actualval.length();
				int dataLenght=data.length();
				//int result = actualval.compareToIgnoreCase(data);
				if(actualvalLenght<dataLenght)
				{
					if(data.contains(actualval)) 
						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL;
				}
				else
				{
					if(actualval.contains(data)) 
						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL;
				}
			}
			else
				return Constants.KEYWORD_FAIL;
		}catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage()+"values do not match Comparision failed";
		}
	}


	public String closeApp(String object, String data){

		//currently taking the input data from OR properties file
		APP_LOGS.debug("closing the browser");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue is  ->> "+OR.getProperty(object));

		try{
			//flushLocalyticsLogs(null,null);
			driver.quit();

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+" Unable to close the App"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	
	public String flushLocalyticsLogs(String object, String data){

		//currently taking the input data from OR properties file
		APP_LOGS.debug("Flush adb logcat logs");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue is  ->> "+OR.getProperty(object));

		try{
			
			log_file_writer.flush();

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+" Unable to close the App"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public  String verifyTitle(String object, String data){
		APP_LOGS.debug("Verifying title");
		try{
			String actualTitle= driver.getTitle();
			String expectedTitle=data;
			if(actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Error in retrieving title"+e.getMessage();
		}		
	}

	public  String synchronize(String object,String data) throws InterruptedException{
		APP_LOGS.debug("Waiting for page to load");
		Thread.sleep(5000L);
		return Constants.KEYWORD_PASS;
	}
	public  String waitForElementLoad(String object,String data) throws InterruptedException{
		APP_LOGS.debug("Waiting for element to load");
		for(int i=0; i<=10 && driver.findElements(By.xpath(OR.getProperty(object))).size()==0 ; i++){
			Thread.sleep(2000);
		}	
		return Constants.KEYWORD_PASS;
	}

	public  String verifypresent(String object,String data){

		APP_LOGS.debug("Verifying Element is present.");
		try{   	
			wait(object);
			if(driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed())
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Element not present on page.";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();
		}
	}
	public  String verifypresent_ID(String object,String data){
		APP_LOGS.debug("Verifying Element is present.");
		try{   	
			if(driver.findElement(By.id(OR.getProperty(object))).isDisplayed())
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Element not present on page.";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();
		}
	}

	public  String verifypresent_name(String object,String data){
		APP_LOGS.debug("Verifying Element is present.");
		try{   	
			if(((MobileElement)driver.findElement(By.name(OR.getProperty(object)))).isDisplayed())

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- Element not present on page.";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();
		}
	}

	public  String verifyContent(String object,String data){
		APP_LOGS.debug("Verifying Element is present.");
		try{   	

			String ActData=driver.findElement(By.id(OR.getProperty(object))).getText().trim();
			if(ActData.equals(data.trim()))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+"****Actual value doesnt match with expected****"+"\n" +"ExpValue=" +data+"\n"+"ActualValue="+ActData;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();
		}
	}

	public String captureScreenshot(String filename, String keyword_execution) throws IOException{
		String path;
		if(keyword_execution.startsWith(Constants.KEYWORD_PASS) &&CONFIG.getProperty("screenshot_everystep").equals("Y")){

			Constants.file_path=Constants.projectBaseDir +"/screenshots/"+file_Name+"/"+filename+"_"+time()+"_Pass"+".jpg";

			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Constants.file_path));
			APP_LOGS.debug("Screenshot Target File for Pass is - "+Constants.file_path);

		}else if (keyword_execution.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
			Constants.file_path=Constants.projectBaseDir +"/screenshots/"+file_Name+"/"+filename+"_"+time()+"_Fail"+".jpg";
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Constants.file_path));
			APP_LOGS.debug("Screenshot Target File for Fail is - "+Constants.file_path);



		}
		return Constants.file_path;
	}

	public void captureVideo(String filename, String keyword_execution) throws IOException{

		try {

			ProcessBuilder pb = new ProcessBuilder(
					Constants.catureVideoShell);
			Process p = pb.start();     // Start the process.
			p.waitFor();                // Wait for the process to finish.
			System.out.println("Script executed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void PullVideo(String filename, String keyword_execution) throws IOException{

		try {

			ProcessBuilder pb = new ProcessBuilder(
					Constants.pullVideoShell);
			Process p = pb.start();     // Start the process.
			p.waitFor();                // Wait for the process to finish.
			System.out.println("Script executed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public String CheckCheckbox(String object, String data){

		// currently taking the input data from Config properties file
		APP_LOGS.debug("entering the text on ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value"+object);
		APP_LOGS.debug("data value is"+data);

		try{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}



	public static String time(){
		Date obj=new Date();
		SimpleDateFormat time = new SimpleDateFormat ("yyyy_MM_dd_hh_mm_ss");
		System.out.println();
		return time.format(obj);
	}



	public String clickPhoneBackBtn(String object, String data){

		driver.navigate().back();
		APP_LOGS.debug("Tap the phone's Back button");
		return Constants.KEYWORD_PASS;

	}

	public String createtestdata(String object, String data) throws ParseException{
		long millis = System.currentTimeMillis();
		CONFIG.setProperty(object, data+millis);
		APP_LOGS.debug("Data created for new album is "+CONFIG.getProperty(object));
		System.out.println("Data created for new album is "+CONFIG.getProperty(object));
		return Constants.KEYWORD_PASS;
	}
	public String createNewEmail(String object, String data) throws ParseException{
		String domain="@gmail.com";
		long millis = System.currentTimeMillis();
		CONFIG.setProperty(object, data+millis+domain);
		APP_LOGS.debug("Data created for new email address is "+CONFIG.getProperty(object));
		System.out.println("Data created for new address is "+CONFIG.getProperty(object));
		return Constants.KEYWORD_PASS;
	}
	public static void wait(final String object){

		//		try{
		//			Thread.sleep(2000l);
		//		WebDriverWait wait = new WebDriverWait(driver, 15);
		//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty(object)))));
		//		return true;
		//		}catch(Throwable e){
		//			return false;
		//		}
		try{
			FluentWait<AppiumDriver> wait = new FluentWait<AppiumDriver>(driver)
					.withTimeout(2, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class);

			wait.until(new Function<AppiumDriver, WebElement>() {
				public  WebElement apply(AppiumDriver webDriver) {
					return driver.findElement(By.xpath(OR.getProperty(object)));
				}
			});
		}catch(Throwable e){

		}
		//return null;
	}





	public  String implicitWait(String object,String data) throws InterruptedException{
		APP_LOGS.debug("Waiting for page to load");

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;


	}


	public String Verify_BfrAft_Count_DelContent(String object,String data){
		APP_LOGS.debug("Checking count bef after deletion");
		String[] objectsAll =  object.split(",");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		int intCnt,aftCnt;

		try{

			List<WebElement> tabListall_before = driver.findElements(By.id(OR.getProperty(objectsAll[0])));
			intCnt=tabListall_before.size();
			for(int i=0;i<tabListall_before.size();i++)
			{
				System.out.println(tabListall_before.get(i).getText());
			}
			for(int i=1;i<objectsAll.length;i++) 
			{
				clickLink(objectsAll[i],null);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(objectsAll[0]))));
			List<WebElement> tabListall_aft = driver.findElements(By.id(OR.getProperty(objectsAll[0])));
			for(int i=0;i<tabListall_aft.size();i++)
			{
				System.out.println(tabListall_aft.get(i).getText());
			}
			aftCnt=tabListall_aft.size();
			//System.out.println("count value before deletion"+(aftCnt+1)+":" +aftCnt);
			if (aftCnt==(intCnt-1))
			{ 
				System.out.println("Content has been deleted as expected"+intCnt+" -- "+aftCnt);

				APP_LOGS.debug("Content has been deleted as expected "+intCnt+" -- "+aftCnt);
				System.out.println("Content deleted successfully");
			}
			else if (aftCnt!=(intCnt-1))
			{ 
				APP_LOGS.debug("Content has not been deleted as expected "+intCnt+" -- "+aftCnt);
				return Constants.KEYWORD_FAIL+"Content has not been deleted as expected"; }


		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;
	}


	//Function validate music played success/failed, Param 4 objects pass - play,pause,strTime,EndTme,Select Queue
	public  String wait_MusicPlayComplete(String object,String data){


		APP_LOGS.debug("Verifying waiting Element is present.");
		String[] objectsAll1 =  object.split(",");

		try{  
			String Ori_value="0:00";
			int i=0;
			WebDriverWait wait = new WebDriverWait(driver, 16000);
			verifypresent(objectsAll1[2],null);
			verifypresent(objectsAll1[3],null);
			clickLink(objectsAll1[4], null);
			clickLink(objectsAll1[5], null);
			verifypresent(objectsAll1[6],null);
			clickPhoneBackBtn(null,null);
			WebElement ele1=driver.findElement(By.id(OR.getProperty(objectsAll1[2])));
			System.out.println("element "+ele1);
			String init_time=ele1.getText();
			System.out.println("init time -->" +init_time);

			WebElement ele2=driver.findElement(By.id(OR.getProperty(objectsAll1[3])));
			System.out.println("element "+ele2);
			String end_time=ele2.getText();
			String[] max_value1=end_time.split(":");
			int max_value=Integer.parseInt(max_value1[0]);
			System.out.println("max value --> " +max_value1[0]);
			System.out.println("end time --> " +end_time);
			if (init_time!=Ori_value)
				System.out.println("Music play started");
			else
				System.out.println("Music play not started");

			for(i=1;i<max_value;i++)
			{
				wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsAll1[0]))));
				//System.out.println("value -->"+i);
				//wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsAll1[0]))));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(OR.getProperty(objectsAll1[1]))));
			}

			String init_end_time=ele1.getText();
			System.out.println("start_end time--> " +init_end_time);
			if((init_end_time.equals(end_time)))
			{
				System.out.println("Music file played successfuly");
			}
			else
			{
				System.out.println("Music file not played successfuly");
			}




		}catch(Exception e){

			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	//To select first item from list
	public String select_list_first_item(String object, String data){

		APP_LOGS.debug("clicking the button from list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			List<WebElement> tabList = driver.findElements(By.id(OR.getProperty(object)));
			tabList.get(0).click();

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}
	//Function to select all items in the list
	public String select_list_All_item(String object, String data){

		APP_LOGS.debug("clicking the button from list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(object)));
			for(int i=0;i<tabListall.size();i++)
			{
				tabListall.get(i).click();
			}

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}

	//Function to check element absent
	public  String verify_not_present(String object,String data){


		APP_LOGS.debug("Verifying Element is present or not.");

		try{  

			WebElement element=driver.findElement(By.xpath(OR.getProperty(object)));
			//System.out.println("value of element "+element);
			if((element.isDisplayed()))
			{
				return Constants.KEYWORD_FAIL+" -- Element not present on page.";

			}	 
			else
			{
				return Constants.KEYWORD_FAIL+" -- Element not present on page.";
			}

		}catch(Exception e){

			return Constants.KEYWORD_PASS;

		}
	}

	// To Dismiss pop up alert message
	public  String dismissAlertButton(String object,String data){
		APP_LOGS.debug("Clicking on button and dismissing the alert");
		try{
			driver.findElement(By.xpath(object)).click();
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to dismiss alert";
		}
		return Constants.KEYWORD_PASS;
	}

	// Function to Accept the alert message
	public  String acceptAlertButton(String object,String data){
		APP_LOGS.debug("Clicking on button and acccepting the alert");
		try{

			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to accept alert";
		}
		return Constants.KEYWORD_PASS;
	}

	// Function to read all text messages and verify with expected results  
	/*@SuppressWarnings("null")
	public  String alertTextRead(String object,String data){

		APP_LOGS.debug("Verify all items are listed in the drop drown as expected");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try 
		{   
			String[] allElements =  data.split(",");
			String[] ActDropDownItem = new String[20];
			List<WebElement> list = driver.findElements(By.xpath(OR.getProperty(object)));
			int k=list.size();
			System.out.println("value" +k);

			for(int i=0;i<k;i++)
			{
				System.out.println("mes" +i);
				String expDropDownItem=allElements[i];
				//System.out.println(driver.findElements(By.xpath(OR.getProperty(object))).get(i).getText());
				ActDropDownItem[i]=driver.findElements(By.xpath(OR.getProperty(object))).get(i).getText();
				// System.out.println("mes" +ActDropDownItem[i]);
				ActDropDownItem[i]=ActDropDownItem[i].replaceAll("[\n\r]", "");
				System.out.println("Expected List Item "+(i+1)+"="+expDropDownItem);
				System.out.println("Actual List Item "+(i+1)+"="+ActDropDownItem[i]);

				if(expDropDownItem.trim().equalsIgnoreCase(ActDropDownItem[i].trim())||ActDropDownItem[i].trim().contains(expDropDownItem.trim()))
				{
					System.out.println("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem[i]);
					APP_LOGS.debug("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem[i]);
					System.out.println("value "+i);

				}	
				else
					return Constants.KEYWORD_FAIL+ "Contextual menu list item doesnt match as expected";
			}	
			//return Constants.KEYWORD_PASS;   
		}

		catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Object not visible"+ e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}*/
	//Function to verify message with expected results like alert Description,Title
	public  String verifypresentAlertMesId(String object,String data){
		APP_LOGS.debug("Verifying Element is present.");
		System.out.println("verify before");
		String ActualCanceAlert=driver.findElement(By.id(OR.getProperty(object))).getText();
		ActualCanceAlert=ActualCanceAlert.replaceAll("[\n\r]", "");
		String ExpectedalertElements= data;
		try{

			if(ExpectedalertElements.trim().equalsIgnoreCase(ActualCanceAlert.trim())||ActualCanceAlert.trim().contains(ExpectedalertElements.trim()))
			{

				System.out.println("List Item matches as expected "+ExpectedalertElements+" -- "+ActualCanceAlert);
				APP_LOGS.debug("List Item matches as expected "+ExpectedalertElements+" -- "+ActualCanceAlert);
				//System.out.println("value "+i);

			}	
			else
			{
				return Constants.KEYWORD_FAIL+ "Contextual menu list item doesnt match as expected";
			}
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Element not present on page."+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String elementNotExist(String object,String data){
		APP_LOGS.debug("Checking non-existance of element");
		try{
			if(driver.findElements(By.xpath(OR.getProperty(object))).size()==0);
			return Constants.KEYWORD_PASS;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}



	}

	//function to switch different application
	public String SwitchApp(String object,String data){
		APP_LOGS.debug("Switching to new app");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		try{
			driver.startActivity("com.rhmsoft.fm.hd-1\\base","com.rhmsoft.fm.FileManagerHD",null,null );

		}      

		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;	
	}

	public String Get_BfrAft_Count_validate(String object,String data){
		APP_LOGS.debug("Checking count bef after deletion");
		String[] objectsAll =  object.split(",");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		int intCnt,aftCnt;

		try{

			List<WebElement> tabListall_before = driver.findElements(By.id(OR.getProperty(objectsAll[0])));
			intCnt=tabListall_before.size();
			for(int i=1;i<objectsAll.length;i++) 
			{
				clickLink(objectsAll[i],null);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(objectsAll[0]))));
			List<WebElement> tabListall_aft = driver.findElements(By.id(OR.getProperty(objectsAll[0])));
			aftCnt=tabListall_aft.size();
			//System.out.println("count value before deletion"+(aftCnt+1)+":" +aftCnt);
			if (intCnt!=(aftCnt))
			{ 
				System.out.println("List Item matches doesn't as expected "+intCnt+" -- "+aftCnt);

				APP_LOGS.debug("List Item matches as expected "+intCnt+" -- "+aftCnt);
				System.out.println("count value differs");
			}


		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;
	}

	public String Verify_BfrAft_SongsCount_AddPlyLst(String object,String data){
		APP_LOGS.debug("Checking track count bef after add to playlist");
		String[] objectsAll =  object.split(",");
		String[] dataAll =  data.split(",");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		int bfrCnt,aftCnt;

		try{
			int addedSongCount=1;
			createtestdata("newAlbumName",dataAll[2]);
			String songPlaylist=CONFIG.getProperty("newAlbumName");


			System.out.println("Total No of songs to be added to playlist---> "+addedSongCount);
			APP_LOGS.debug("Total No of songs to be added to playlist---> "+addedSongCount);
			for(int i=1;i<objectsAll.length-3;i++) 
			{
				driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
				clickLink(objectsAll[i],null);

			}
			writeInput_sendkeys(objectsAll[4],songPlaylist);
			synchronize(null,null);
			clickLink(objectsAll[5],null);
			synchronize(null,null);
			clickLink(objectsAll[6],null);
			synchronize(null,null);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(objectsAll[0]))));
			String itemDesc=searchListItem(objectsAll[7], songPlaylist);
			System.out.println("Track Description "+itemDesc);
			String[] descList=itemDesc.split(":");
			String trackCount=descList[1].trim();
			int aftCount=Integer.parseInt(trackCount);
			System.out.println("trackCount"+trackCount);

			//System.out.println("count value before deletion"+(aftCnt+1)+":" +aftCnt);
			if (aftCount==addedSongCount)
			{ 
				System.out.println("All selected contents have been added to playlist successfully"+addedSongCount+" -- "+aftCount);

				APP_LOGS.debug("All selected contents have been added to playlist successfully"+addedSongCount+" -- "+aftCount);

			}
			else if (aftCount!=(addedSongCount))
			{ 
				APP_LOGS.debug("Content has not been added to playlist, track count doesnt match"+addedSongCount+" -- "+aftCount);
				return Constants.KEYWORD_FAIL+"Content has not been added to playlist, track count doesnt match"+addedSongCount+" -- "+aftCount;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;
	}
	public String Verify_BfrAft_TrackCount_AddPlyLst(String object,String data){
		APP_LOGS.debug("Checking track count bef after add to playlist");
		String[] objectsAll =  object.split(",");
		String[] dataAll =  data.split(",");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		int bfrCnt,aftCnt;

		try{
			String actText=getText_Split(objectsAll[0],data);
			createtestdata("newAlbumName",dataAll[2]);
			String playlistName=CONFIG.getProperty("newAlbumName");

			bfrCnt=Integer.parseInt(actText.trim());
			System.out.println("Total No of selected tracks to be added to playlist---> "+bfrCnt);
			APP_LOGS.debug("Total No of selected tracks to be added to playlist---> "+bfrCnt);
			for(int i=1;i<objectsAll.length-3;i++) 
			{
				driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
				clickLink(objectsAll[i],null);

			}
			writeInput_sendkeys(objectsAll[4],playlistName);
			synchronize(null,null);
			clickLink(objectsAll[5],null);
			synchronize(null,null);
			clickLink(objectsAll[6],null);
			synchronize(null,null);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(objectsAll[0]))));
			String itemDesc=searchListItem(objectsAll[7], playlistName);
			System.out.println("Track Description "+itemDesc);
			String[] descList=itemDesc.split(":");
			String trackCount=descList[1].trim();
			int aftCount=Integer.parseInt(trackCount);
			System.out.println("trackCount"+trackCount);

			//System.out.println("count value before deletion"+(aftCnt+1)+":" +aftCnt);
			if (aftCount==bfrCnt)
			{ 
				System.out.println("All selected contents have been added to playlist successfully"+bfrCnt+" -- "+aftCount);

				APP_LOGS.debug("All selected contents have been added to playlist successfully"+bfrCnt+" -- "+aftCount);

			}
			else if (aftCount!=(bfrCnt))
			{ 
				APP_LOGS.debug("Content has not been added to playlist, track count doesnt match"+bfrCnt+" -- "+aftCount);
				return Constants.KEYWORD_FAIL+"Content has not been added to playlist, track count doesnt match"+bfrCnt+" -- "+aftCount;
			}

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;
	}


	//Function to retrieve text during runtime and split a string on passing separator
	public  String getText_Split(String object,String data){

		String[] allElements = null;
		APP_LOGS.debug("Retrieve text during runtime and split a string on passing separator");

		try{  

			//	String elementTxt=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String elementTxt=driver.findElement(By.id("com.vcast.mediamanager:id/description")).getText();
			//System.out.println("value of element "+element);
			if(data!=null)
			{
				String[] inputdata=data.split(",");
				String seperator=inputdata[0];
				int pos=Integer.parseInt(inputdata[1]);
				allElements =  elementTxt.split(seperator);
				APP_LOGS.debug("Split string based on the seperator and pos passed on the input test data file");
				return allElements[pos];


			}	 
			else
			{
				APP_LOGS.debug("Retrieve text during runtime without splitting");
				return elementTxt;
			}

		}catch(Exception e){

			return Constants.KEYWORD_FAIL;

		}

	}
	public  String searchListItem(String object, String data) throws InterruptedException{
		//wait(object);
		//Function to select all items in the list

		String searchContent=null;
		String itemDesc=null;
		APP_LOGS.debug("Searching content from a list");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(object)));
			List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
			for(int i=0;i<tabListall.size();i++)
			{
				searchContent=tabListall.get(i).getText();
				System.out.println("listContent "+i+"   " +searchContent);
				if(searchContent.trim().equals(data.trim()))
				{
					System.out.println("Data found");

					itemDesc=ListDesc.get(i).getText();
					break;
				}
				else
					System.out.println("Search data doesn't match with the listed item");
			}

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		return itemDesc;


	}

	public  String verifyAddToFavOperation(String object, String data) throws InterruptedException{
		wait(object);
		APP_LOGS.debug("verify all selected songs have been added to fav and star icon is displayed as expected");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allobjects =  object.split(",");
		int selectedSongsCount=Integer.parseInt(data);
		System.out.println("Total Objects"+allobjects.length);
		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			//List<WebElement> songsList = imagesFrame.findElements(By.className("android.widget.LinearLayout")); 

			List<WebElement> songsList = imagesFrame.findElements(By.className("android.widget.RelativeLayout")); 
			System.out.println("selectedSongsCount: "+selectedSongsCount);	

			for(int i=0;i<songsList.size();i++)		
			{	 
				WebElement songs=songsList.get(i);
				String SongsText=songs.findElement(By.className("android.widget.TextView")).getText();
				System.out.println("text"+SongsText);

			}

		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Object not visible "+ e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String push_Notification_DwnLoad(String object, String data){


		APP_LOGS.debug("clicking on the notification bar ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		WebDriverWait wait = new WebDriverWait(driver, 18000);
		String max_value="100%";
		int value=100;
		String[] OpenNoti=  object.split(",");
		try{

			driver.openNotifications();
			synchronize(null,null);
			driver.findElement(By.id(OR.getProperty(OpenNoti[0]))).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(OR.getProperty(OpenNoti[3]))));
			synchronize(null,null);
			System.out.println("downloaded Sucessful1.. ");
			String result_DownTxt=verifypresent(OpenNoti[1],null);
			System.out.println("present value.. "+result_DownTxt);
			if(result_DownTxt.equals("Pass"))
			{
				System.out.println("Downloaded sucessfull");
			}
			else
				System.out.println("Downloaded failed");

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}	 
	public String select_Download_Yes(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to Download the file by selecting "yes" option for duplicate
		 * 
		 */

		APP_LOGS.debug("clicking on Yes button to download duplicate files");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		String[] ObjDownload =  object.split(",");
		try{
			driver.findElement(By.xpath(OR.getProperty(ObjDownload[0]))).click();
			synchronize(null,null);
			if(driver.findElements(By.id("com.vcast.mediamanager:id/dialog_message") ).size() > 0)
			{
				driver.findElement(By.xpath(OR.getProperty(ObjDownload[1]))).click();
				synchronize(null,null);
			}
			System.out.println("downloaded Started.. ");
		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;		
	}
	public String validate_Created_PlayLst(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Add playing music file to Playlist and validate Playlist is created or not
		 * 
		 */

		APP_LOGS.debug("validate the created playlist and tracks ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		System.out.println("test121");
		String[] objectsPlayLst = object.split(",");
		String[] dataAllPlayLst = data.split(",");
		String searchContent=null;
		String itemDesc=null;
		try
		{
			String NoplylstTxt=verifypresent(objectsPlayLst[0],null);
			System.out.println("value of no txt mes"+NoplylstTxt);
			String NoPlylist=verifyText(objectsPlayLst[0],dataAllPlayLst[0]);
			System.out.println("validate expected txt with actual txt -->"+NoPlylist);
			synchronize(null,null);
			if(NoPlylist.equals("Pass")||NoplylstTxt.equals("Pass"))
			{

				String CreaPlylist=clickLink(objectsPlayLst[1],null);
				System.out.println("user clicked on CreatPlaylistbutton -->"+CreaPlylist);
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(100000);
				String alName=dataAllPlayLst[1];
				String albumName=Integer.toString(randomInt);
				String albFinName=alName.concat(albumName);
				System.out.println("Album name"+albFinName);
				String creatLst=enterText(objectsPlayLst[2],albFinName);
				System.out.println("enter status message -->"+creatLst);
				String createdlst=clickLink(objectsPlayLst[3],null);
				System.out.println("clicked on ok option -->"+createdlst);
				//navigate to Add to Playlist screen print the name 
				synchronize(null,null);
				String albumTxt=clickPhoneBackBtn(null,null);
				System.out.println("navigate to playlist screen -->"+albumTxt);
				//Pass album link to navigate album
				synchronize(null,null);
				String alBNav=clickLink(objectsPlayLst[4],null);
				System.out.println("navigate to playlist screen -->"+alBNav);
				synchronize(null,null);
				//Get album name / campare it 
				// String AddAl=searchListItem(objectsPlayLst[7],albFinName);
				// System.out.println("status of added album-->"+AddAl);
				List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(objectsPlayLst[8])));
				List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
				for(int i=0;i<tabListall.size();i++)
				{
					searchContent=tabListall.get(i).getText();
					System.out.println("listContent "+i+"   " +searchContent);
					if(searchContent.trim().equals(albFinName))
					{
						System.out.println("Data found");

						itemDesc=ListDesc.get(i).getText();
						break;
					}
					else
						System.out.println("Search data doesn't match with the listed item");
				}



			}

			else {
				String AddplyListTxtStatus=verifypresent(objectsPlayLst[6],null);
				System.out.println("value of no txt mes"+AddplyListTxtStatus);
				if (AddplyListTxtStatus.equals("Pass"))
				{

					String ListPlusSymbol=clickLink(objectsPlayLst[6],null);
					System.out.println("user clicked on CreatPlaylistbutton -->"+ListPlusSymbol);
					Random randomGenerator = new Random();
					int randomInt = randomGenerator.nextInt(100000);
					String alName=dataAllPlayLst[1];
					String albumName=Integer.toString(randomInt);
					String albFinName=alName.concat(albumName);
					System.out.println("Album name"+albFinName);
					String creatLst=enterText(objectsPlayLst[2],albFinName);
					System.out.println("enter status message -->"+creatLst);
					String createdlst=clickLink(objectsPlayLst[3],null);
					System.out.println("clicked on ok option -->"+createdlst);
					//navigate to add to playlist screen print the name 
					synchronize(null,null);
					String albumTxt=clickPhoneBackBtn(null,null);
					System.out.println("navigate to playlist screen status -->"+albumTxt);
					//Pass album link to navigate album
					synchronize(null,null);
					String alBNav=clickLink(objectsPlayLst[4],null);
					System.out.println("navigate to album screen status-->"+alBNav);
					// synchronize(null,null);
					String AddAl=searchListItem(objectsPlayLst[7],albFinName);
					System.out.println("status of added album-->"+AddAl);
					List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(objectsPlayLst[7])));
					List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
					for(int i=0;i<tabListall.size();i++)
					{
						searchContent=tabListall.get(i).getText();
						System.out.println("listContent "+i+"   " +searchContent);
						if(searchContent.trim().equals(albFinName))
						{
							System.out.println("Data found");

							itemDesc=ListDesc.get(i).getText();
							break;
						}
						else
							System.out.println("Search data doesn't match with the listed item");
					}


				}

			}
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String getText_info(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to get text info of music file
		 * 
		 */
		APP_LOGS.debug("Print the playing play info: ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{
			//Thread.sleep(2500);
			String[] Info_LabTxt=object.split(",");
			List<WebElement> actualTitle=driver.findElements(By.xpath(OR.getProperty(Info_LabTxt[0])));
			List<WebElement> ExpInfo=driver.findElements(By.xpath(OR.getProperty(Info_LabTxt[1])));


			for (int i=0;i<=(actualTitle.size()-1);i++)
			{
				System.out.println("value i:"+i);
				String actualTitle_val=actualTitle.get(i).getText();
				String ValueTitle_val=ExpInfo.get(i).getText();
				System.out.println("Actual Label Text:"+actualTitle_val);
				System.out.println("Expected value Text:"+ValueTitle_val);
				if(i==5)
					driver.scrollTo("Format");

			}

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String verifyMusic_play(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to validate music file playing or not & user navigate 
		 * from queue to music player screen
		 * 
		 */

		APP_LOGS.debug("Validate Pause button & navigate to main screen ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		String[] MusPlay_Ver =  object.split(",");
		try{
			String PauseBtn=verifypresent(MusPlay_Ver[0],null);
			String CurrtBtn1=verifypresent(MusPlay_Ver[1],null);
			System.out.println("Music file started playing"+PauseBtn);
			if(PauseBtn.equals("Pass") && CurrtBtn1.equals("Pass") )
			{
				System.out.println("Music file started playing123");

				String va1=clickPhoneBackBtn(null,null);
				String pause=verifypresent(MusPlay_Ver[3],null);
				if (pause.equals("Pass"))
				{
					System.out.println("Now we are in music screen ..Start download");
					//clickPhoneBackBtn(null,null);
				}

			}
			else
				System.out.println("cloud app in music play screen");

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}
	public String valida_music_removal(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to validate removal music files from playing music player
		 */
		APP_LOGS.debug("remove all items from playing : ");	
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));

		try{

			WebDriverWait wait = new WebDriverWait(driver, 60);
			System.out.println("entering music removal function");
			String[] objectsRemovLst = object.split(",");
			for (int j=0;j<objectsRemovLst.length;j++)
			{
				System.out.println("list of items"+objectsRemovLst[j]);
			}
			// List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(object)));
			List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
			String TtlCnt=ListDesc.get(0).getText();
			System.out.println("total selected count"+TtlCnt);
			String[] TtlCount=TtlCnt.split(":");
			System.out.println("total selected count"+TtlCount);
			String ttlcount=TtlCount[1].trim();
			System.out.println("total selected count"+ttlcount);
			int count=Integer.parseInt(ttlcount);
			System.out.println("total selected count"+count);
			//commonbar
			clickLink(objectsRemovLst[0],null);
			//Play button
			clickLink(objectsRemovLst[1],null);
			synchronize(null,null);
			//verifyMusic_play(objectsRemovLst[2],null);
			while(count!=0)
			{
				clickLink(objectsRemovLst[0],null);
				clickLink(objectsRemovLst[2],null);
				Thread.sleep(10);
				count=count-1;
				System.out.println("Value of No tracks remaining: "+count);

			}
			Thread.sleep(2);
			String  durNa_indcator=verify_not_present(objectsRemovLst[3],null);
			if(durNa_indcator.trim().equals("Pass"))
			{
				System.out.println("No more tracking in the playing music file");

			}
			else
				System.out.println("Still some more tracking in the playing music file");

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		return Constants.KEYWORD_PASS;	
	}
	public  String verifyListItems_id(String object, String data) throws InterruptedException{
		/* 
		 * Created by Shashi , 
		 * Helps to validate List items displayed for like menu, sub menu items

		 */
		APP_LOGS.debug("Verify all items are listed in the drop drown as expected");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Selected value is "+data);
		String[] allElements =  data.split(",");
		String[] allobjects =  object.split(",");

		// Thread.sleep(1000L);
		try{
			wait(object);
			WebElement droplist=driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> droplist_cotents = droplist.findElements(By.className(OR.getProperty(allobjects[1]))); 

			int actualCount = droplist_cotents.size();
			System.out.println("Actual contextual menu list items total count: "+actualCount);
			System.out.println("Expected contextual menu list items total count:"+allElements.length);
			int expElemIndex=(allElements.length);
			if(actualCount==allElements.length)
			{				

				for(int i=0;i<expElemIndex;i++)		
				{	
					WebElement mainElement= droplist_cotents.get(i);
					String expDropDownItem=allElements[i];

					String ActDropDownItem=mainElement.findElement(By.id(OR.getProperty(allobjects[2]))).getText();

					System.out.println("Expected List Item "+(i+1)+"="+expDropDownItem);
					System.out.println("Actual List Item "+(i+1)+"="+ActDropDownItem);

					if(expDropDownItem.trim().equalsIgnoreCase(ActDropDownItem))
					{
						System.out.println("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem);
						APP_LOGS.debug("List Item matches as expected "+expDropDownItem+" -- "+ActDropDownItem);

					}	
					else
						return Constants.KEYWORD_FAIL+ "Contextual menu list item doesnt match as expected";
				}	
			}
			else
				return Constants.KEYWORD_FAIL+ "Contextual menu list items count doesnt match as expected";

		}catch(Throwable e){
			return Constants.KEYWORD_FAIL +" - Object not visible"+ e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}		
	public String VZLogin(String object,String data){
		/* 
		 * Created by Shashi , 
		 * Helps to validate login into VZcloud app with MDN details
		 */
		APP_LOGS.debug("Logging into VZCloud app");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		String[] objectsVZLoginLst = object.split(",");
		String[] dataVZLoginLst = data.split(",");
		try{
			WebDriverWait wait = new WebDriverWait(driver, 180);
			wait.until(ExpectedConditions.elementToBeClickable(By.name(OR.getProperty(objectsVZLoginLst[0]))));
			clickLink_name(objectsVZLoginLst[0],null);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsVZLoginLst[1]))));
            clickLink(objectsVZLoginLst[2],null);
            driver.findElement(By.xpath(OR.getProperty(objectsVZLoginLst[2]))).clear();
            writeInput_sendkeys(objectsVZLoginLst[2],dataVZLoginLst[0]);
            clickLink(objectsVZLoginLst[3],null);
            writeInput_sendkeys(objectsVZLoginLst[3],dataVZLoginLst[1]);
            clickLink_id(objectsVZLoginLst[1],null);
            
            //Next button 
            wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsVZLoginLst[4]))));
            clickLink_id(objectsVZLoginLst[4],null);
            //email setup
            wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsVZLoginLst[4]))));
            clickLink(objectsVZLoginLst[5],null);
            writeInput_sendkeys(objectsVZLoginLst[5],dataVZLoginLst[2]);
            clickLink_id(objectsVZLoginLst[4],null);
            //Complete Advertisement
            wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(objectsVZLoginLst[7]))));
            //synchronize(null,null);
            //synchronize(null,null);
            clickLink_id(objectsVZLoginLst[7],null);
            clickLink_id(objectsVZLoginLst[7],null);
            clickLink_id(objectsVZLoginLst[7],null);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(objectsVZLoginLst[8]))));    
		}      

		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;	
	}
	public String validate_diffDownload(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to select different files and download duplicate files
		 */
		APP_LOGS.debug("Download the different files: ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{
			//Thread.sleep(2500);
			String[] ObjectAllDownload=object.split(",");
			//String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle=driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1=driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist=(actualTitle.size()-2);

			for (int i=1;i<=countlist;i++)
			{
				System.out.println("value i:"+i);
				String actualTitle_val=ExpInfo1.get(i).getText();
				System.out.println("valueTxt"+actualTitle_val);
				String[] actuDownfile=actualTitle_val.split(".");
				if (actualTitle_val.endsWith(".mp3") ||actualTitle_val.endsWith(".jpg")||actualTitle_val.endsWith(".png") ||actualTitle_val.endsWith(".pdf")||actualTitle_val.endsWith(".doc") )
				{
					System.out.println("mp3/JPG/png/doc/pdf found");
					//System.out.println("valueTxt"+actuDownfile[j]);
					clickLink(ObjectAllDownload[0],null);
					clickLink(ObjectAllDownload[1],null);
					actualTitle.get(i).click();
					clickLink(ObjectAllDownload[0],null);
					//Download
					clickLink(ObjectAllDownload[2],null);
					String statusYesBut=verifypresent_name(ObjectAllDownload[4],null);
					if(statusYesBut.equals("Pass"))
					{
						clickLink(ObjectAllDownload[3],null);

					}
					/*TouchAction action = new TouchAction(driver).longPress(70,162).moveTo(90,162).release();
							action.perform();
							driver.scrollTo("R");
					 */							}

				System.out.println("Actual Label Text:"+actualTitle_val);

			}

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String select_files_list(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to select first file from file module
		 */
		APP_LOGS.debug("clicking the button from list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			List<WebElement> tabList = driver.findElements(By.id(OR.getProperty(object)));
			//verifypresent_name(object)
			//tablist.get(0).
			tabList.get(1).click();

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}
	public String validate_aft_bfrDelCount(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to verify deleted file should not present in the list
		 */
		APP_LOGS.debug("Validate deleted file not present in the list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{
			//Thread.sleep(2500);
			String[] ObjectAllDownload=object.split(",");
			//String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle=driver.findElements(By.id("com.vcast.mediamanager:id/list_item"));
			List<WebElement> ExpInfo1=driver.findElements(By.id("com.vcast.mediamanager:id/title"));

			for (int i=1;i<=1;i++)
			{
				System.out.println("value i:"+i);
				String befDel_FName=ExpInfo1.get(i).getText();
				System.out.println("valueTxt"+befDel_FName);
				clickLink(ObjectAllDownload[0],null);
				clickLink(ObjectAllDownload[1],null);
				actualTitle.get(i).click();
				clickLink(ObjectAllDownload[0],null);
				//Delete
				clickLink(ObjectAllDownload[2],null);
				//Ok button
				clickLink(ObjectAllDownload[3],null);
				String aftDel_FName=ExpInfo1.get(i).getText();
				System.out.println("valueTxt"+aftDel_FName);
				if (befDel_FName.trim().equals(aftDel_FName))
				{
					System.out.println("File not matches, File Not Present in List");
				}
				else
				{
					System.out.println("File match, File deleted from file module");
				}


			}

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String val_fileshare_Email(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to user share JPG/PNG file via email
		 */

		APP_LOGS.debug("Validate jpg and share via email ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			String[] ObjectshareF=object.split(",");
			String[] DatashareF=data.split(",");
			//String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle=driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1=driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist=(actualTitle.size()-2);

			for (int i=1;i<=countlist;i++)
			{
				System.out.println("value i:"+i);
				String actuShre_file_val=ExpInfo1.get(i).getText();
				System.out.println("valueTxt"+actuShre_file_val);
				//String[] actuDownfile=actuShre_file_val.split(".");
				if (actuShre_file_val.endsWith(".jpg")||actuShre_file_val.endsWith(".png") )
				{
					System.out.println("JPG/png found");
					//System.out.println("valueTxt"+actuDownfile[j]);
					clickLink(ObjectshareF[0],null);
					clickLink(ObjectshareF[1],null);
					actualTitle.get(i).click();
					clickLink(ObjectshareF[0],null);
					//copylnk
					clickLink(ObjectshareF[9],null);
					//repeat the steps to select it
					synchronize(null,null);
					clickLink(ObjectshareF[0],null);
					clickLink(ObjectshareF[1],null);
					actualTitle.get(i).click();

					clickLink(ObjectshareF[0],null);
					//view content
					clickLink(ObjectshareF[2],null);
					synchronize(null,null);
					//view full image
					//driver.findElement(By.id("com.vcast.mediamanager:id/flipper_image_view_0")).click();
					clickLink(ObjectshareF[3],null);
					//Share link
					synchronize(null,null);
					clickLink(ObjectshareF[4],null);
					synchronize(null,null);
					//Email Txt
					//driver.swipe(400, startY, startx, endY, 1000);
					List<WebElement> ListItems = driver.findElements(By.id("android:id/text1"));
					for (int j=0;j<=ListItems.size();j++)
					{
						System.out.println("item found");
						String txt=ListItems.get(i).getText();
						if(txt.trim().equals("Gmail"))
						{
							System.out.println("item found");
						}
						else
							driver.scrollTo("Gmail");
					}
					clickLink_name(ObjectshareF[5],null);
					//Email contents
					writeInput_sendkeys(ObjectshareF[6],DatashareF[0]);
					writeInput_sendkeys(ObjectshareF[7],DatashareF[1]);
					String status=clickLink(ObjectshareF[8],null);	
					if(status.equals("Pass"))
					{
						System.out.println("Posted successfully");
						break;

					}

				}
			}

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String clickLink_id(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to click element using id 
		 */
		APP_LOGS.debug("clicking the link");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);

		try{
			((MobileElement)driver.findElement(By.id(OR.getProperty(object)))).click();

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		//}
}
	public String ver_DocsharCpy_Email(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to share selected documents copylnk via Email
		 */
		APP_LOGS.debug("Validate jpg and share via email ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{

			WebDriverWait wait = new WebDriverWait(driver, 60); 
			String[] ObjectDocShare=object.split(",");
			String[] Datashare=data.split(",");
			List<WebElement> actualTitle=driver.findElements(By.id(OR.getProperty(ObjectDocShare[0])));
			List<WebElement> ExpInfo1=driver.findElements(By.id(OR.getProperty(ObjectDocShare[1])));
			clickLink(ObjectDocShare[2],null);
			clickLink(ObjectDocShare[3],null);
			actualTitle.get(0).click();
			String DocName=ExpInfo1.get(0).getText();
			System.out.println("Selected Document to post via Email"+DocName);
			//copy link
			System.out.println(ObjectDocShare[2]);
			clickLink(ObjectDocShare[2],null);
			System.out.println(ObjectDocShare[4]);
			clickLink(ObjectDocShare[4],null);
			synchronize(null,null);
			//select top most
			System.out.println(ObjectDocShare[2]);
			clickLink(ObjectDocShare[2],null);
			System.out.println(ObjectDocShare[3]);
			clickLink(ObjectDocShare[3],null);
			actualTitle.get(0).click();
			//share linkicon
			System.out.println(ObjectDocShare[5]);
			clickLink(ObjectDocShare[5],null);
			/*synchronize(null,null);
					Dimension size = driver.manage().window().getSize();
					int startY = (int)(size.height*0.69);
					int endY = (int)(size.height*0.40);
					int startx = size.width/2;
					System.out.println("Size startY End Y Startx"+size +"\r" +startY+"\r" +endY +"\r" +startx );
					for(int i=1;i<=2;i++)
					{
					//driver.swipe(startx, startY, startx, endY, 1200);
					if(driver.findElements(By.name("Gmail") ).size() > 0)
					{
						System.out.println("Element found");
						break;
					}
					else
					{
						System.out.println("Element not found, so swipe through the list");
						driver.swipe(startx, startY, startx, endY, 1200);
						synchronize(null,null);
						//driver.swipe(630, 710, 800, 500, 400); swipe up
						continue;
					}
					}
					//wait.until(ExpectedConditions.titleContains("Complete action using"));
					//Sample code for Email send
					   //Email name
						clickLink_name(ObjectDocShare[6],null);
						synchronize(null,null);*/
			/*//Email contents
						//clickLink_name(ObjectDocShare[6],null);
						driver.findElement(By.id("com.google.android.gm:id/to")).click();
						driver.findElement(By.id("com.google.android.gm:id/to")).sendKeys("testsyncjb@gmail.com");
						driver.findElement(By.id("com.google.android.gm:id/subject")).click();
						driver.findElement(By.id("com.google.android.gm:id/subject")).sendKeys("documents");
						driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Send']")).click();
						synchronize(null,null);
						writeInputByID(ObjectDocShare[7],Datashare[0]);
						writeInputByID(ObjectDocShare[8],DocName);
						clickLink_id(ObjectDocShare[9],null);
						//writeInput_sendkeys(ObjectDocShare[7],Datashare[0]);
						//clickLink_name(ObjectDocShare[8],null);
						//writeInput_sendkeys(ObjectDocShare[8],DocName);
				       // String status=clickLink(ObjectDocShare[9],null);
				        //synchronize(null,null);

					//Email name
					clickPhoneBackBtn(null,null);
			        String status=clickLink(ObjectDocShare[8],null);	
					if(status.equals("Pass"))
					{
						System.out.println("Posted successfully");

					}
			 */

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}


	public String validate_copyLink_frmList(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to validate not able to copy sharecopy link files
		 */
		APP_LOGS.debug("Validate copylink present in the list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{    
			WebDriverWait wait = new WebDriverWait(driver, 60);
			String[] objectsCopyLnk = object.split(",");

			List<WebElement> tabListallitems = driver.findElements(By.id(OR.getProperty(objectsCopyLnk[0])));
			List<WebElement> LstIcon=driver.findElements(By.id("com.vcast.mediamanager:id/lock_icon"));
			for(int i=0;i<tabListallitems.size();i++)
			{
				clickLink(objectsCopyLnk[2],null);
				clickLink(objectsCopyLnk[6],null);
				tabListallitems.get(i).click();
				//commonactionbar
				clickLink(objectsCopyLnk[2],null);
				//Copysharelink
				clickLink(objectsCopyLnk[3],null);
				synchronize(null,null);
				if(driver.findElements(By.id("com.vcast.mediamanager:id/dialog_message") ).size() > 0)
				{
					String mes_LckDailog=driver.findElement(By.id("com.vcast.mediamanager:id/dialog_message")).getText();
					System.out.println("Lock icon Found -->"+mes_LckDailog);
					System.out.println("value--> ");
					//Ok button
					clickLink(objectsCopyLnk[4],null);
					break;
				}
				else
				{
					System.out.println("Lock icon Not Found");
					continue;
				}
			}	

			return Constants.KEYWORD_PASS;	 
		}	
		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}

	}
	public String Post_link(String object, String data){
		APP_LOGS.debug("Validate copylink by posting through email ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		String[] objectsCopynlnkPost = object.split(",");
		try{    
			//common actionbar
			clickLink(objectsCopynlnkPost[0],null);
			String CpyStatus=verifypresent(objectsCopynlnkPost[1],null);
			if(CpyStatus.trim().equals("Pass"))
			{
				//play  
				clickLink(objectsCopynlnkPost[1],null);
				//copylink
				clickLink(objectsCopynlnkPost[1],null);
				//Postcopyicon
				clickLink(objectsCopynlnkPost[2],null);
				//Postcopyicon
				clickLink_name(objectsCopynlnkPost[3],null);
				writeInput_sendkeys(objectsCopynlnkPost[4],"testsync@gmail.com");
				writeInput_sendkeys(objectsCopynlnkPost[5],"music");
				clickLink(objectsCopynlnkPost[6],null);

			}

		}	
		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;	     
	}            

	public String Open_Document(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to select document and opens the document in cloudviewer .
		 */
		APP_LOGS.debug("Validate user opens using available list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		String[] objectsOpendoc = object.split(",");
		try{    
			List<WebElement> ListDoc=driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			for (int j=0;j<=1;j++)
			{
				String ListNames=ListDoc.get(j).getText();
				if(ListNames.trim().endsWith(".xlsx") ||ListNames.trim().endsWith(".xls") )
				{
					System.out.println("File Name -->" +ListNames);
					synchronize(null,null);
					clickLink(objectsOpendoc[0],null);
					clickLink(objectsOpendoc[1],null);
					ListDoc.get(j).click();
					clickLink(objectsOpendoc[0],null);
					clickLink(objectsOpendoc[2],null);
					synchronize(null,null);
					break;
				}
				else 
				{
					clickLink(objectsOpendoc[0],null);
					clickLink(objectsOpendoc[1],null);
					ListDoc.get(j).click();
					clickLink(objectsOpendoc[0],null);
					clickLink(objectsOpendoc[2],null);
					synchronize(null,null);
				}	
				List<WebElement> tabListallitems = driver.findElements(By.id("android:id/text1"));
				//System.out.println("File Name -->" +tabListallitems);
				for (int i=0;i<=tabListallitems.size();i++)
				{
					String ListFileType=tabListallitems.get(i).getText();
					System.out.println("File Name" +ListFileType);

					if(ListFileType.trim().contains("PDF") ||ListFileType.trim().contains("DOC")||ListFileType.trim().contains("doc") )
					{
						tabListallitems.get(i).click();
						clickLink_id(objectsOpendoc[3],null);
						break;
					}
					else
					{
						Boolean DocViewer = driver.findElement(By.id("id/actionbar_find")).isDisplayed();
						if (DocViewer)
						{
							System.out.println("Doc opened successfully");
							break;
						}
					}

					if (driver.findElement(By.id("android:id/title")).isDisplayed())
					{
						clickLink_id(objectsOpendoc[3],null);
						System.out.println("Already user opened it");
						break;
					}



				}	

			}				
		}	
		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}
		return Constants.KEYWORD_PASS;	     
	}
	public String push_Notibar(String object, String data){
		/* 
		 * Created by Shashi , 
		 * To validate to view the download bar notifications
		 */
		APP_LOGS.debug("clicking on the notification bar ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));

		try{
			String[] DataRestTxt=data.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 24000);
			System.out.println("Entered the Push bar notification function");
			driver.openNotifications();
			System.out.println("Opened the notification bar");
			//synchronize(null,null);
			driver.findElement(By.id("com.vcast.mediamanager:id/upload_download_image")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.vcast.mediamanager:id/upload_download_status_cancel_button")));
			synchronize(null,null);
			List<WebElement> all_TextMes=driver.findElements(By.className("android.widget.TextView"));
			for (int i=0;i<=all_TextMes.size();i++)
			{
				String Txt=all_TextMes.get(i).getText();
				if (Txt.contains(DataRestTxt[0]))
				{
					System.out.println("Downloaded sucessfull"+Txt);
					clickPhoneBackBtn(null,null);
					break;
				}
				else
					System.out.println("Downloaded Failed");
			}

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}	 

	public String push_NotibarCancel(String object, String data){
		/* 
		 * Created by Shashi , 
		 * validate to cancel the download notification bar
		 */
		APP_LOGS.debug("clicking on the notification bar ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));

		try{
			String[] DataRestTxt=data.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 60);
			System.out.println("Entered the Push bar notification function");
			driver.openNotifications();
			synchronize(null,null);
			driver.findElement(By.id("com.vcast.mediamanager:id/upload_download_image")).click();
			synchronize(null,null);
			driver.findElement(By.id("com.vcast.mediamanager:id/upload_download_status_cancel_button")).click();
			synchronize(null,null);
			List<WebElement> all_TextMes=driver.findElements(By.className("android.widget.TextView"));
			for (int i=0;i<=all_TextMes.size();i++)
			{
				String Txt=all_TextMes.get(i).getText();
				if (Txt.contains(DataRestTxt[0]))
				{
					System.out.println("Downloaded Cancelled");
					clickPhoneBackBtn(null,null);
					break;
				}
				else
					System.out.println("Downloaded failed");

			}

		}
		catch (Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}	
		return Constants.KEYWORD_PASS;	
	}	 

	public String content_Restoreclass(String object, String data){

		/* 
		 * Created by Shashi , 
		 * Helps to select which module to be restored from tools section
		 */
		APP_LOGS.debug("Validate which module needs to restored ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));

		try{
			//wait(object);
			String[] objectResAll=object.split(",");
			String[] datarestore=data.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 60);
			//String spaceRem=objectResAll[3];
			synchronize(null,null);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(objectResAll[0]))));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.CheckBox")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.vcast.mediamanager:id/scanner_progress_bar")));
			List<WebElement> droplist_cotents=driver.findElements(By.className(OR.getProperty(objectResAll[1])));
			List<WebElement> Checkbx_Contents = driver.findElements(By.className(OR.getProperty(objectResAll[2])));
			//String resto_size=driver.findElement(By.xpath(OR.getProperty(objectResAll[1]))).getText();
			for (int j=0;j<Checkbx_Contents.size();j++)
			{
				Boolean Checkbox=Checkbx_Contents.get(j).isEnabled();
				if (Checkbox)
				{
					//String checkboxname=Checkbx_Contents.get(j).getTagName();
					//System.out.println("Ticked checkboxes"+checkboxname);
					Checkbx_Contents.get(j).click();
				}

			}
			for(int i=1;i<droplist_cotents.size()-5;i++)		
			{	
				String ActDropDownItem=droplist_cotents.get(i+1).getText();
				System.out.println("Text -->"+ActDropDownItem);
				if (ActDropDownItem.trim().contains(datarestore[0].trim()))
				{
					clickLink_id(datarestore[1],null);
					String buffSize=driver.findElement(By.id("estimate_space_remaining_details")).getAttribute("text");
					String[] buffSize1=buffSize.split(",");
					//check device having sufficient storage space to download it
					if(buffSize1[0].trim().contains("0"))
					{
					   System.out.println("No space is available ");
					   break;
					}
					else
					{
					clickLink_id(objectResAll[0],null);
					// check for Dialog message present or not 
					if (driver.findElements(By.id("dialog_button_negative")).size()> 0)
					{
					//String Status=push_Notibar(datarestore[0],null);
					// System.out.println("Downloaded Status" +Status); 	
				    System.out.println("sms/mms restore notification");		
					driver.findElement(By.xpath("//android.widget.Button[@text='Skip']")).click();
					}
					break;
					}	
				}
			}
	

		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	// to write if id of element is given
	public  String writeInputByID(String object,String data){
		APP_LOGS.debug("Writing in text box");

		try{
			driver.findElement(By.id(OR.getProperty(object))).click();
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}
	public  String writeInputByName(String object,String data){
		APP_LOGS.debug("Writing in text box");

		try{
			driver.findElement(By.name(OR.getProperty(object))).click();
			driver.findElement(By.name(OR.getProperty(object))).clear();
			driver.findElement(By.name(OR.getProperty(object))).sendKeys(data);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}
	public  String emailGmailShare(String object,String data){
		/* 
		 * Created by Shashi , 
		 * To validate selected Document share via Gmail
		 */
		APP_LOGS.debug("Share selected file via gmail");

		try{
			String[] ObjectGmail=object.split(",");
			String[] DataGmail=data.split(",");
			writeInputByID(ObjectGmail[0],DataGmail[0]);
			writeInputByID(ObjectGmail[1],DataGmail[1]);
			clickLink_id(ObjectGmail[2],null);
			synchronize(null,null);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	public  String searchShareApp(String object,String data){
		/* 
		 * Created by Shashi , 
		 * validate to search for the apps given from the list
		 */
		APP_LOGS.debug("Searhing the sharing app from the list");

		try{
			String[] objectShrApp=object.split(",");
			String appName=objectShrApp[0].trim();
			//String[] dataShreApp=data.split(",");

			synchronize(null,null);
			Dimension size = driver.manage().window().getSize();
			int startY = (int)(size.height*0.69);
			int endY = (int)(size.height*0.40);
			int startx = size.width/2;
			System.out.println("Size startY End Y Startx"+size +"\r" +startY+"\r" +endY +"\r" +startx );
			for(int i=1;i<=5;i++)
			{
				synchronize(null,null);
				//driver.swipe(startx, startY, startx, endY, 1200);
				if(driver.findElements(By.name(data)).size() > 0)
				{
					System.out.println("Element found");
					break;
				}
				else
				{
					System.out.println("Element not found, so swipe through the list");
					driver.swipe(startx, startY, startx, endY, 1200);
					synchronize(null,null);
					synchronize(null,null);
					//driver.swipe(630, 710, 800, 500, 400); swipe up
					continue;
				}
			}
			System.out.println("test"+appName);
			clickLink_name(appName,null);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	public  String shareViaMessage(String object,String data){
		/* 
		 * Created by Shashi , 
		 * Helps to share selected documents via Messages
		 */
		APP_LOGS.debug("Share selected file via Messages");

		try{
			String[] objMes=object.split(",");
			//String[] dataMes=data.split(",");
			System.out.println("the input data"+data);
			writeInputByName(objMes[0],data);
			clickLink(objMes[1],null);
			synchronize(null,null);
			acceptAlertButton(objMes[2],null);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	public String validate_MusicCpyLink(String object, String data){
		/* 
		 * Created by Shashi , 
		 * Helps to validate not able to copy sharecopy link files
		 */
		APP_LOGS.debug("Validate copylink present in the list ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		try{    
			WebDriverWait wait = new WebDriverWait(driver, 60);
			String[] objectsCopyLnk = object.split(",");

			List<WebElement> tabListallitems = driver.findElements(By.id(OR.getProperty(objectsCopyLnk[0])));
			List<WebElement> LstIcon=driver.findElements(By.id("com.vcast.mediamanager:id/lock_icon"));
			for(int i=0;i<tabListallitems.size();i++)
			{
				clickLink(objectsCopyLnk[2],null);
				clickLink(objectsCopyLnk[6],null);
				tabListallitems.get(i).click();
				//commonactionbar
				clickLink(objectsCopyLnk[2],null);
				//Copysharelink
				clickLink(objectsCopyLnk[3],null);
				synchronize(null,null);
				if(driver.findElements(By.id("com.vcast.mediamanager:id/dialog_message") ).size() > 0)
				{
					String mes_LckDailog=driver.findElement(By.id("com.vcast.mediamanager:id/dialog_message")).getText();
					System.out.println("Lock icon Found -->"+mes_LckDailog);
					//Ok button
					clickLink(objectsCopyLnk[4],null);
					continue;
				}
				else
				{
					System.out.println("Lock icon Not Found");
					clickLink(objectsCopyLnk[2],null);
					clickLink(objectsCopyLnk[6],null);
					tabListallitems.get(i).click();
					clickLink(objectsCopyLnk[7],null);
					break;
				}
			}

			synchronize(null,null);

			//return Constants.KEYWORD_PASS;	 
		}	
		catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object exist in page"; 
		}    

		return Constants.KEYWORD_PASS;	 
	}

	public  String ValidateLocTagOccurences(String currentTestSuite,String data){

		APP_LOGS.debug("Validate localytics tags are getting fired a s expected");

		try{

			int count=0;
			String[] allElements =  data.split(",");
			// you need a shell to execute a command pipeline
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");

			String Filepath=Constants.adbLogcatFile+"/"+allElements[0];
			
			String matchWord=allElements[1];

			if( commands.add("grep -o" +" "+matchWord+" "+Filepath+"|wc -l"))
			{
				System.out.println("Match found"); 
				
				count=(count+1);
				APP_LOGS.debug("Total no of matched tag occurences"+count);
				
			}
			else
				System.out.println("Searched Tag Not Found"); 

			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			int result = commandExecutor.executeCommand();

			StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
			StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();

			System.out.println(stderr);
			APP_LOGS.debug(stderr);
			APP_LOGS.debug(stdout);

			//APP_LOGS.debug("STDOUT");
			System.out.println(stdout);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public  String ValidateLocalyticsTag(String currentTestSuite,String data){

		APP_LOGS.debug("Validate localytics tags are getting fired a s expected");

		try{
			String[] allElements =  data.split(",");
			// you need a shell to execute a command pipeline
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");

			String Filepath=Constants.adbLogcatFile+"/"+allElements[0];
			String matchWord=allElements[1];
			//if( commands.add("grep -o" +" "+matchWord+" "+Filepath+"|wc -l"))
			if( commands.add("grep -r" +" "+matchWord+" "+Filepath))

				System.out.println("Match found"); 

			else
				System.out.println("Not Found"); 

			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			int result = commandExecutor.executeCommand();

			stdout = commandExecutor.getStandardOutputFromCommand();
			stderr = commandExecutor.getStandardErrorFromCommand();

			System.out.println(stderr);
			APP_LOGS.debug(stderr);
			
			APP_LOGS.debug(stdout);
			System.out.println(stdout);

		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS +stderr;
	}


	public static String generateLocalyticsLogs(AppiumDriver driver, String currentTestSuite){

		APP_LOGS.debug("Take adb logcat and store it in the system ");


		try{
			DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);

			List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);

			File logFile = new File(
					Constants.adbLogcatFile+"/"+currentTestSuite);
			PrintWriter log_file_writer = new PrintWriter(logFile);
			for (int i=0; i < logEntries.size(); ++i) {
				log_file_writer.println(logEntries.get(i).toString() + "\n");
				System.out.println(driver.getSessionId());
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to create logfile for Localytics testings"+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String storageUsed(String object, String data)
	{
		try{
			Thread.sleep(2500);
			APP_LOGS.debug("Add images, videos till it reaches the threshold storage space");
			//String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			//String[] storageUsed=actual.split("(");
			//String[] dataStorageUsed=storageUsed[1].split("%");
			
			double threshold = Double.valueOf(data).doubleValue()/100;
			
			System.out.println("threshold= "+threshold);
			int iTotal = 5*1024*1024*1024;
			int iUsed = 484 * 1024 * 1024;
			
			int iTobeFilled = (int) (iTotal  * .8 - iUsed);
			int iFileSize = 52464391+4389905+1647397+1230057  ;
			
			int iNumIteration = iTobeFilled / iFileSize;
			
			for (int i = 0; i<= 10; ++i)
			{
				ReportUtil.executeShell("sh "+Constants.DataGenerator, "null");
			
				
			}
			
			return Constants.KEYWORD_PASS;
			
			/*int actualData=Integer.parseInt(dataStorageUsed[0].trim());
			System.out.println("actualData"+actualData);
			int expected=Integer.parseInt(data.trim());
			System.out.println("Expect Text:"+expected);
			System.out.println("Actual Text:"+actual);*/
/*
			if(actualData==expected)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- data doesnt match with exp value "+actual+" -- "+expected;*/
		}catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	public String openWebPortal(String object, String data ) {

		try {
			
			webDriver=new FirefoxDriver();
			webDriver.get(data);
			
			webDriver.manage().window().maximize();
			
		}
		catch(Exception e) {
			return Constants.KEYWORD_FAIL+"Unable to launch site "+e.getMessage();
		}

		Constants.logDetails="Site Launched Successfullly";
		return Constants.KEYWORD_PASS;

	}
	
	public String navigateUrl(String object, String data ) {

		try {
			
			webDriver.get(data);
			
		}
		catch(Exception e) {
			return Constants.KEYWORD_FAIL+"Unable to launch site "+e.getMessage();
		}

		Constants.logDetails="Site Launched Successfullly";
		return Constants.KEYWORD_PASS;

	}
	
	public String clickLink_WebDriver(String object, String data){
		wait(object);
		APP_LOGS.debug("clicking the link");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);

		try{
			wait(object);

			webDriver.findElement(By.xpath(OR.getProperty(object))).click();

			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
		//}
	}
	public String writeInput_webDriver(String object, String data){
		//	wait(object);
		// currently taking the input data from Config properties file
		APP_LOGS.debug("Performing entering the text in text box ");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> "+data);
		System.out.println("Input parameter ::-> "+data);
		try{
	
			webDriver.findElement(By.xpath(OR.getProperty(object))).click();
			webDriver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}
	
	
	
	public String QuitWebDriver(String object, String data){

		//currently taking the input data from OR properties file
		APP_LOGS.debug("closing the browser");
		APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
		APP_LOGS.debug("object vaue is  ->> "+OR.getProperty(object));

		try{
		
			webDriver.quit();

		}catch (Exception e){
			return Constants.KEYWORD_FAIL+" Unable to close the App"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public void testUpload(String object, String data) throws InterruptedException, AWTException
	{
		
		//File Need to be imported
		 
		File file = new File("/Users/jsah0003/Desktop/ContactsData.csv");
		 
		StringSelection stringSelection= new StringSelection(file.getAbsolutePath());
		 
		//Copy to clipboard Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		 
		Robot robot = new Robot();
		 
		// Cmd + Tab is needed since it launches a Java app and the browser looses focus
		 
		robot.keyPress(KeyEvent.VK_META);
		 
		robot.keyPress(KeyEvent.VK_TAB);
		 
		robot.keyRelease(KeyEvent.VK_META);
		 
		robot.keyRelease(KeyEvent.VK_TAB);
		 
		robot.delay(500);
		 
		//Open Goto window
		 
		robot.keyPress(KeyEvent.VK_META);
		 
		robot.keyPress(KeyEvent.VK_SHIFT);
		 
		robot.keyPress(KeyEvent.VK_G);
		 
		robot.keyRelease(KeyEvent.VK_META);
		 
		robot.keyRelease(KeyEvent.VK_SHIFT);
		 
		robot.keyRelease(KeyEvent.VK_G);
		 
		//Paste the clipboard value
		 
		robot.keyPress(KeyEvent.VK_META);
		 
		robot.keyPress(KeyEvent.VK_V);
		 
		robot.keyRelease(KeyEvent.VK_META);
		 
		robot.keyRelease(KeyEvent.VK_V);
		 
		//Press Enter key to close the Goto window and Upload window
		 
		robot.keyPress(KeyEvent.VK_ENTER);
		 
		robot.keyRelease(KeyEvent.VK_ENTER);
		 
		robot.delay(500);
		 
		robot.keyPress(KeyEvent.VK_ENTER);
		 
		robot.keyRelease(KeyEvent.VK_ENTER);
		/*setClipboardData(data);

		uploadFile();
		Thread.sleep(2000);*/
	}
	/**
     * This method will set any parameter string to the system's clipboard.
     */
	public static void setClipboardData(String string) {
		//StringSelection is a class that can be used for copy and paste operations.
		   StringSelection stringSelection = new StringSelection(string);
		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		}
	public static void uploadFile() {
        try {
        	//Setting clipboard with file location
          
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();
	
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception exp) {
        	exp.printStackTrace();
        }
    }

public String fileshare_Documents(String object, String data) {
		/*
		 * Created by Shashi , Helps to user share JPG/PNG file via email
		 */

		APP_LOGS.debug("Validate documents present in the list and share via email ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {

			String[] ObjectshareF = object.split(",");
			// String[] DatashareF=data.split(",");
			// String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle = driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1 = driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist = (actualTitle.size() - 2);

			for (int i = 1; i <= countlist; i++) {
				System.out.println("value i:" + i);
				String actuShre_file_val = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + actuShre_file_val);
				// String[] actuDownfile=actuShre_file_val.split(".");
				if (actuShre_file_val.endsWith(".docx") || actuShre_file_val.endsWith(".pdf")
						|| actuShre_file_val.endsWith(".xlsx")) {
					System.out.println("Doc/pdf/xlsx found");
					// System.out.println("valueTxt"+actuDownfile[j]);
					clickLink(ObjectshareF[0], null);
					clickLink(ObjectshareF[1], null);
					actualTitle.get(i).click();
					clickLink(ObjectshareF[0], null);
					// copylnk
					clickLink(ObjectshareF[2], null);
					// repeat the steps to select it
					synchronize(null, null);
					clickLink(ObjectshareF[0], null);
					clickLink(ObjectshareF[1], null);
					actualTitle.get(i).click();
					// Share link
					synchronize(null, null);
					String status = clickLink(ObjectshareF[3], null);
					if (status.trim().equals("Pass")) {
						System.out.println("User clicked on share icon");
						break;
					}

				}
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
public String file_ImgAddFav(String object, String data) {
		/*
		 * Created by Shashi , Validate user able to add Image as Fav and
		 * removes fav icon for the added image.
		 */

		APP_LOGS.debug("Validate jpg and share via email ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {

			String[] ObjectshareF = object.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 16000);
			// String[] DatashareF=data.split(",");
			// String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle = driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1 = driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist = (actualTitle.size() - 2);

			for (int i = 1; i <= countlist; i++) {
				System.out.println("value i:" + i);
				String actuShre_file_val = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + actuShre_file_val);
				// String[] actuDownfile=actuShre_file_val.split(".");
				if (actuShre_file_val.endsWith(".jpg") || actuShre_file_val.endsWith(".png")) {
					System.out.println("JPG/png found");
					if (actualTitle.get(i).findElements(By.id("com.vcast.mediamanager:id/favorite_icon")).size() > 0) {
						System.out.println("Favourite icon present");
						continue;
					} else {
						clickLink(ObjectshareF[0], null);
						clickLink(ObjectshareF[1], null);
						actualTitle.get(i).click();
						// Add to Favoruites
						clickLink(ObjectshareF[0], null);
						clickLink(ObjectshareF[5], null);
						// repeat the steps to select it
						synchronize(null, null);
						Boolean fav_icon1 = actualTitle.get(i)
								.findElement(By.id("com.vcast.mediamanager:id/favorite_icon")).isDisplayed();
						if (fav_icon1) {
							System.out.println("Fav icon present for the selected the file");
							clickLink(ObjectshareF[0], null);
							clickLink(ObjectshareF[1], null);
							actualTitle.get(i).click();
							clickLink(ObjectshareF[0], null);
							// view content
							clickLink(ObjectshareF[2], null);
							wait.until(ExpectedConditions
									.elementToBeClickable(By.id("com.vcast.mediamanager:id/flipper_image_view_0")));
							driver.findElement(By.id("com.vcast.mediamanager:id/flipper_image_view_0")).click();

							synchronize(null, null);
							clickLink(ObjectshareF[7], null);
							// Remove from Favourites
							String status = clickLink(ObjectshareF[6], null);
							synchronize(null, null);
							if (status.equals("Pass")) {
								System.out.println("Fav icon not removed");
								break;
							}
						} else {
							System.out.println("Fav icon not present for the selected file");
						}
					}
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String file_MusicAddFav(String object, String data) {
		/*
		 * Created by Shashi , Validate user able to add Image as Fav and
		 * removes fav icon for the added image.
		 */

		APP_LOGS.debug("Validate jpg and share via email ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {

			String[] ObjectshareF = object.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 16000);
			// String[] DatashareF=data.split(",");
			// String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle = driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1 = driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist = (actualTitle.size() - 2);

			for (int i = 1; i <= countlist; i++) {
				System.out.println("value i:" + i);
				String actuShre_file_val = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + actuShre_file_val);
				// String[] actuDownfile=actuShre_file_val.split(".");
				if (actuShre_file_val.endsWith(".mp3") || actuShre_file_val.endsWith(".mp4")) {
					System.out.println("mp3/mp4 found");
					if (actualTitle.get(i).findElements(By.id("com.vcast.mediamanager:id/favorite_icon")).size() > 0) {
						System.out.println("Favourite icon present");
						continue;
					} else {
						clickLink(ObjectshareF[0], null);
						clickLink(ObjectshareF[1], null);
						actualTitle.get(i).click();
						// Add to Favoruites
						clickLink(ObjectshareF[0], null);
						clickLink(ObjectshareF[5], null);
						// repeat the steps to select it
						synchronize(null, null);
						Boolean fav_icon1 = actualTitle.get(i)
								.findElement(By.id("com.vcast.mediamanager:id/favorite_icon")).isDisplayed();
						if (fav_icon1) {
							System.out.println("Fav icon present for the selected the file");
							break;
							/*
							 * clickLink(ObjectshareF[0],null);
							 * clickLink(ObjectshareF[1],null);
							 * actualTitle.get(i).click();
							 * clickLink(ObjectshareF[0],null); //view content
							 * clickLink(ObjectshareF[2],null);
							 * wait.until(ExpectedConditions.
							 * elementToBeClickable(By.id(
							 * "com.vcast.mediamanager:id/flipper_image_view_0")
							 * )); driver.findElement(By.id(
							 * "com.vcast.mediamanager:id/flipper_image_view_0")
							 * ).click();
							 * 
							 * synchronize(null,null);
							 * clickLink(ObjectshareF[7],null); //Remove from
							 * Favourites String
							 * status=clickLink(ObjectshareF[6],null);
							 * synchronize(null,null); if
							 * (status.equals("Pass")) { System.out.println(
							 * "Fav icon not removed"); break; }
							 */
						} else {
							System.out.println("Fav icon not present for the selected file");
						}
					}
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String fileDoc_open_download(String object, String data) {
		/*
		 * Created by Shashi , Validate user view the document and download the
		 * file
		 */

		APP_LOGS.debug("Validate view/download the document ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {

			String[] ObjectsFilDoc = object.split(",");
			WebDriverWait wait = new WebDriverWait(driver, 16000);
			String[] DatashareF = data.split(",");
			// String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle = driver.findElements(By.id("com.vcast.mediamanager:id/item_wraper"));
			List<WebElement> ExpInfo1 = driver.findElements(By.id("com.vcast.mediamanager:id/title"));
			int countlist = (actualTitle.size() - 2);

			for (int i = 1; i <= countlist; i++) {
				System.out.println("value i:" + i);
				String actuShre_file_val = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + actuShre_file_val);
				// String[] actuDownfile=actuShre_file_val.split(".");
				if (actuShre_file_val.endsWith(DatashareF[0]) || actuShre_file_val.endsWith((DatashareF[1]))) {
					System.out.println("Document found");
					clickLink(ObjectsFilDoc[0], null);
					clickLink(ObjectsFilDoc[1], null);
					actualTitle.get(i).click();
					// Select to download/open
					clickLink(ObjectsFilDoc[0], null);
					clickLink(ObjectsFilDoc[2], null);
					synchronize(null, null);
					if (driver.findElements(By.id("com.vcast.mediamanager:id/dialog_message")).size() > 0) {
						driver.findElement(By.xpath(OR.getProperty(ObjectsFilDoc[4]))).click();
						synchronize(null, null);
					}

					// if
					// (driver.findElements(By.xpath(OR.getProperty(ObjectsFilDoc[3]))).size()
					// > 0)
					else {
						if (driver.findElements(By.id("com.infraware.docmaster:id/actionbar_find")).size() > 0) {
							System.out.println("Document opened successfully ");
							clickPhoneBackBtn(null, null);
						}
					}

					break;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String rotate(String object, String data) {

		String Portrait = driver.getOrientation().toString();
		System.out.println(Portrait + " View");
		// assertEquals(driver,"PORTRAIT", Portrait);
		driver.rotate(ScreenOrientation.LANDSCAPE);
		String Landscape = driver.getOrientation().toString();
		// assertEquals(driver,"LANDSCAPE", Landscape);
		System.out.println(Landscape + " View");
		driver.rotate(ScreenOrientation.PORTRAIT);
		System.out.println("Switched back to " + Portrait + " View");
		driver.navigate().back();
		APP_LOGS.debug("Tap the phone's Back button");
		return Constants.KEYWORD_PASS;

	}

	// function to switch different application
	public String switchToDiffApp(String object, String data) {
		APP_LOGS.debug("Switching to new app");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		try {
			String[] ObjectsAppKeys = object.split(",");
			// driver.startActivity("com.rhmsoft.fm.hd-1\\base","com.rhmsoft.fm.FileManagerHD",null,null
			// );
			System.out.println("entered the switch app function");
			synchronize(null, null);
			// driver.startActivity("com.verizon.messaging.vzmsgs","com.verizon.mms.ui.activity.Provisioning",
			// null, null);
			driver.startActivity(ObjectsAppKeys[0], ObjectsAppKeys[1], null, null);
			synchronize(null, null);
			System.out.println("Navigated to parameterised app");
			// driver.startActivity("com.vcast.mediamanager",
			// "com.newbay.syncdrive.android.ui.nab.NabSplashLogoActivity",
			// null, null);
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object exist in page";
		}
		return Constants.KEYWORD_PASS;
	}

	public String clickLink_Scroll(String object, String data) {
		/*
		 * Created by Shashi , validate scroll and selects element
		 */
		APP_LOGS.debug("clicking the link");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> " + data);

		try {

			synchronize(null, null);
			driver.scrollTo("Help");
			//synchronize(null, null);
			((MobileElement) driver.findElement(By.xpath(OR.getProperty(object)))).click();
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		// }
	}

	public String checkedbox_validtyText(String object, String data) {
		/*
		 * Created by Shashi , validate scroll and selects element
		 */
		APP_LOGS.debug("Validate checkbox is selected ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> " + data);

		try {
			
			boolean enable=driver.findElement(By.id(OR.getProperty(object))).isEnabled();
			boolean disply=driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			boolean selected=driver.findElement(By.id(OR.getProperty(object))).isSelected();
			List<WebElement> checkboxes = driver.findElements(By.className("android.widget.RadioButton"));
			int checksze=checkboxes.size();
			System.out.println("Values  "+enable +disply +selected);
			for (int i=0;i<=checkboxes.size();i++)
			{
			if (checkboxes.get(i).getAttribute("checked")!=null) 
			{
				System.out.println("validated the item --->");
				String actual = driver.findElement(By.id(OR.getProperty(object))).getText();
				actual.trim();
				String expected = data.trim();
				System.out.println("Expect Text:" + expected);
				System.out.println("Actual Text:" + actual);

				if (actual.equals(expected))
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
			}		
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		// }
	}
	public String verifyElementDisabled(String object, String data) throws InterruptedException {
         /* Helps to check element is in disabled state */
		wait(object);
		APP_LOGS.debug("Verify element is enabled");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));

		try {

			if (driver.findElement(By.xpath(OR.getProperty(object))).isEnabled()) {

				System.out.println("Element is present");
				return Constants.KEYWORD_FAIL;
			} else {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}
	public String uncheck_checkboxs(String object, String data) {
		/*
		 * Created by Shashi , validate to pass the selected text
		 * 
		 */
		APP_LOGS.debug("Validate checkbox is selected ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		APP_LOGS.debug("Input parameter ::-> " + data);

		try {
			String expected = data.trim();
			boolean enable=driver.findElement(By.id(OR.getProperty(object))).isEnabled();
			boolean disply=driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			boolean selected=driver.findElement(By.id(OR.getProperty(object))).isSelected();
			List<WebElement> checkboxes = driver.findElements(By.className("android.widget.CheckBox"));
			int checksze=checkboxes.size();
			System.out.println("Values  "+enable +disply +selected);
			for (int i=0;i<checkboxes.size();i++)
			{
		
				//String checked=driver.findElement(By.id(OR.getProperty(object))).getAttribute("checked");
	            //if(checked!=null)
				if ((checkboxes.get(i).getAttribute("checked").equals("true")) || checkboxes.get(i).getAttribute("checked")!=null) 
				{
					checkboxes.get(i).click();
	            String unchecked=checkboxes.get(i).getText();
	            System.out.println("unchecked box : "+unchecked);
	            
				}
				/*else
				{
					checkboxes.get(i).click();
		            String unchecked=checkboxes.get(i).getText();
		            System.out.println("unchecked box : "+unchecked);
				}*/
				}

			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		// }
	}
	
	public  String checkCheckBox1(String object,String data){
        APP_LOGS.debug("Checking checkbox");
        try{
            // true or null
        	List<WebElement> checkboxes = driver.findElements(By.className("android.widget.CheckBox"));
        	String backclss=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			int checksze=checkboxes.size();
			String expected = data.trim();
			for (int i=0;i<checkboxes.size();i++)
			{
				if (backclss.equalsIgnoreCase(expected))
				{
				String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
				System.out.println("Value is :"+checked);
	            if(checked!=null)// checkbox is unchecked
	            	driver.findElement(By.xpath(OR.getProperty(object))).click();
	                break;
			}
        }
        }
        catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbo";
        }
        return Constants.KEYWORD_PASS;
        
    }

	/*public String verifyListItemsOnlyID(String object, String data) throws InterruptedException {
		
		 * Validate to actual with expected elements by passing only One parameter like id
		 * 
		 
		//wait(object);
		APP_LOGS.debug("Verify all items are listed in the drop drown as expected");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		APP_LOGS.debug("Selected value is " + data);
		String[] allElements1 = data.split(",");
		String[] allobjects = object.split(",");
		for (int i=0;i<=allElements1.length-1;i++)
		{
			System.out.println("The drop-down list"+allElements1[i]);
		}

		// Thread.sleep(1000L);
		try {
			//wait(object);
			//WebElement droplist = driver.findElement(By.className(OR.getProperty(allobjects[0])));
			List<WebElement> droplist_cotents = driver.findElements(By.id(OR.getProperty(allobjects[0])));

			int actualCount = droplist_cotents.size();
			System.out.println("Actual contextual menu list items total count: " + actualCount);
			System.out.println("Expected contextual menu list items total count:" + allElements1.length);
			int expElemIndex = (allElements1.length);
			if (actualCount == allElements1.length) {

				for (int i = 0; i <expElemIndex; i++) {
					//WebElement mainElement = droplist_cotents.get(i);
					String expDropDownItem = allElements1[i];
					String ActDropDownItem = driver.findElements(By.id(OR.getProperty(allobjects[0]))).get(i).getText();

					//String ActDropDownItem = mainElement.findElement(By.className(OR.getProperty(allobjects[2])))
					//		.getText();

					System.out.println("Expected List Item " + (i + 1) + "=" + expDropDownItem);
					System.out.println("Actual List Item " + (i + 1) + "=" + ActDropDownItem);

					if (expDropDownItem.trim().equalsIgnoreCase(ActDropDownItem)) {
						System.out
								.println("List Item matches as expected " + expDropDownItem + " -- " + ActDropDownItem);
						APP_LOGS.debug("List Item matches as expected " + expDropDownItem + " -- " + ActDropDownItem);

					} else
						return Constants.KEYWORD_FAIL + "Contextual menu list item doesnt match as expected";
				}
			} else
				return Constants.KEYWORD_FAIL + "Contextual menu list items count doesnt match as expected";

		} catch (Throwable e) {
			return Constants.KEYWORD_FAIL + " - Object not visible" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}*/

	public String waitForElementLoadDisappers(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Waiting for element to load button to disappears");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty(object))));
		
				//invisibility(driver.findElement(By.xpath(OR.getProperty(object)))));
		/*for (int i = 0; i <= 10 && driver.findElements(By.xpath(OR.getProperty(object))).size() == 0; i++) {
			Thread.sleep(2000);
		}*/
		return Constants.KEYWORD_PASS;
	}
	public String verifyTextContains(String object, String data) {
		try {
			Thread.sleep(2500);
			APP_LOGS.debug("Verifying the text");
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			actual.trim();
			String expected = data.trim();
			System.out.println("Expect Text:" + expected);
			System.out.println("Actual Text:" + actual);

			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String validate_aft_bfrDelCount_Tablet(String object, String data) {
		/*
		 * Created by Shashi , Helps to verify deleted file should not present
		 * in the list
		 */
		APP_LOGS.debug("Validate deleted file not present in the list ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {
			// Thread.sleep(2500);
			String[] ObjectAllDownload = object.split(",");
			// String[] DataAllDownload=data.split(",");
			List<WebElement> actualTitle = driver.findElements(By.id("com.vcast.mediamanager:id/icon"));
			List<WebElement> ExpInfo1 = driver.findElements(By.id("com.vcast.mediamanager:id/icon"));

			for (int i = 1; i <= 1; i++) {
				System.out.println("value i:" + i);
				String befDel_FName = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + befDel_FName);
				clickLink(ObjectAllDownload[0], null);
				clickLink(ObjectAllDownload[1], null);
				actualTitle.get(i).click();
				clickLink(ObjectAllDownload[0], null);
				// Delete
				clickLink(ObjectAllDownload[2], null);
				// Ok button
				clickLink(ObjectAllDownload[3], null);
				String aftDel_FName = ExpInfo1.get(i).getText();
				System.out.println("valueTxt" + aftDel_FName);
				if (befDel_FName.trim().equals(aftDel_FName)) {
					System.out.println("File not matches, File Not Present in List");
				} else {
					System.out.println("File match, File deleted from file module");
				}

			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String creatPlyLst(String object, String data) {
		/*
		 * Created by Shashi , Helps to verify deleted file should not present
		 * in the list
		 */
		APP_LOGS.debug("validate the created playlist and tracks ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		String[] objectsPlayLst = object.split(",");
		String[] dataAllPlayLst = data.split(",");
		String searchContent = null;
		String itemDesc = null;
		try {
			String NoplylstTxt = verifypresent(objectsPlayLst[0], null);
			System.out.println("value of no txt mes" + NoplylstTxt);
			String NoPlylist = verifyText(objectsPlayLst[0], dataAllPlayLst[0]);
			System.out.println("validate expected txt with actual txt -->" + NoPlylist);
			synchronize(null, null);
			if (NoPlylist.equals("Pass") || NoplylstTxt.equals("Pass")) {

				String CreaPlylist = clickLink(objectsPlayLst[1], null);
				System.out.println("user clicked on CreatPlaylistbutton -->" + CreaPlylist);
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(100000);
				String alName = dataAllPlayLst[1];
				String albumName = Integer.toString(randomInt);
				String albFinName = alName.concat(albumName);
				System.out.println("Album name" + albFinName);
				String creatLst = enterText(objectsPlayLst[2], albFinName);
				System.out.println("enter status message -->" + creatLst);
				String createdlst = clickLink(objectsPlayLst[3], null);
				System.out.println("clicked on ok option -->" + createdlst);
				// navigate to Add to Playlist screen print the name
				synchronize(null, null);
				String albumTxt = clickPhoneBackBtn(null, null);
				System.out.println("navigate to playlist screen -->" + albumTxt);
				// Pass album link to navigate album
				synchronize(null, null);
				String alBNav = clickLink(objectsPlayLst[4], null);
				System.out.println("navigate to playlist screen -->" + alBNav);
				synchronize(null, null);
				// Get album name / campare it
				// String AddAl=searchListItem(objectsPlayLst[7],albFinName);
				// System.out.println("status of added album-->"+AddAl);
				/*List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(objectsPlayLst[8])));
				List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
				for (int i = 0; i < tabListall.size(); i++) {
					searchContent = tabListall.get(i).getText();
					System.out.println("listContent " + i + "   " + searchContent);
					if (searchContent.trim().equals(albFinName)) {
						System.out.println("Data found");

						itemDesc = ListDesc.get(i).getText();
						break;
					} else
						System.out.println("Search data doesn't match with the listed item");
				}*/

			}

			else {
				String AddplyListTxtStatus = verifypresent(objectsPlayLst[6], null);
				System.out.println("value of no txt mes" + AddplyListTxtStatus);
				if (AddplyListTxtStatus.equals("Pass")) {

					String ListPlusSymbol = clickLink(objectsPlayLst[6], null);
					System.out.println("user clicked on CreatPlaylistbutton -->" + ListPlusSymbol);
					Random randomGenerator = new Random();
					int randomInt = randomGenerator.nextInt(100000);
					String alName = dataAllPlayLst[1];
					String albumName = Integer.toString(randomInt);
					String albFinName = alName.concat(albumName);
					System.out.println("Album name" + albFinName);
					String creatLst = enterText(objectsPlayLst[2], albFinName);
					System.out.println("enter status message -->" + creatLst);
					String createdlst = clickLink(objectsPlayLst[3], null);
					System.out.println("clicked on ok option -->" + createdlst);
					// navigate to add to playlist screen print the name
					synchronize(null, null);
					clickLink(objectsPlayLst[8],null);
					synchronize(null, null);
					List<WebElement> tabListall=driver.findElements(By.id("com.vcast.mediamanager:id/icon"));
					for (int k=1;k<tabListall.size();k++)
					{
						String Text=tabListall.get(k).getText();
						System.out.println("The playlists present in the music module is "+Text);
					}
					/*String albumTxt = clickPhoneBackBtn(null, null);
					System.out.println("navigate to playlist screen status -->" + albumTxt);
					// Pass album link to navigate album
					synchronize(null, null);
					String alBNav = clickLink(objectsPlayLst[4], null);
					System.out.println("navigate to album screen status-->" + alBNav);
					// synchronize(null,null);
					String AddAl = searchListItem(objectsPlayLst[7], albFinName);
					System.out.println("status of added album-->" + AddAl);
					List<WebElement> tabListall = driver.findElements(By.id(OR.getProperty(objectsPlayLst[7])));
					List<WebElement> ListDesc = driver.findElements(By.id("com.vcast.mediamanager:id/description"));
					for (int i = 0; i < tabListall.size(); i++) {
						searchContent = tabListall.get(i).getText();
						System.out.println("listContent " + i + "   " + searchContent);
						if (searchContent.trim().equals(albFinName)) {
							System.out.println("Data found");

							itemDesc = ListDesc.get(i).getText();
							break;
						} else
							System.out.println("Search data doesn't match with the listed item");
					}

				}
*/
			}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String verifyfavicon(String object, String data) {
		/*
		 * Created by Shashi , Helps to verify deleted file should not present
		 * in the list
		 */

		APP_LOGS.debug("To validate Favourite icon present in the list ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {

			List<WebElement> tabList = driver.findElements(By.id(OR.getProperty(object)));
			tabList.get(0).findElement(By.id(("com.vcast.mediamanager:id/favorite_icon")));

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String verifymusic_butns(String object, String data) {
		/*
		 * Created by Shashi , Helps to verify next button/previous button
		 * as Expected
		 */

		APP_LOGS.debug("To validate next button/prev button of music player ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {
     
             String[] musicplaOptio=object.split(","); 
			 String actual_1 = driver.findElement(By.id("com.vcast.mediamanager:id/song_title")).getText();
             actual_1=actual_1.trim();
             clickLink_id(musicplaOptio[0],null);
             synchronize(null,null);
             String actual_2 = driver.findElement(By.id("com.vcast.mediamanager:id/song_title")).getText();
             actual_2=actual_2.trim();
             if(actual_1.equals(actual_2))
            	 System.out.println("Both music file names are match,NEXT btn not working as expected");
             else
            	 System.out.println("Both music file names are not match,NEXT btn working as expected");
		 } 
		
			
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	public String verifymusic_Prevbutns(String object, String data) {
		/*
		 * Created by Shashi , Helps to verify next button/previous button
		 * as Expected
		 */

		APP_LOGS.debug("To validate next button/prev button of music player ");
		APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
		APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
		try {
     
             String[] musicplaOptio=object.split(","); 
			 String actual_1 = driver.findElement(By.id("com.vcast.mediamanager:id/song_title")).getText();
             actual_1=actual_1.trim();
             clickLink_id(musicplaOptio[0],null);
             synchronize(null,null);
             clickLink_id(musicplaOptio[1],null);
             synchronize(null,null);
             String actual_2 = driver.findElement(By.id("com.vcast.mediamanager:id/song_title")).getText();
             actual_2=actual_2.trim();
             if(actual_1.equals(actual_2))
            	 System.out.println("Both music file names are match,PREV btn working as expected");
             else
            	 System.out.println("Both music file names are not match,PREV btn not working as expected");
		 } 
		
			
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}	
	public String after_count(String object, String data) throws InterruptedException {
		
		APP_LOGS.debug("click on the item and get the count of variable");
		String[] CountVal=object.split(",");
		clickLink(CountVal[0], null);
		clickLink(CountVal[1], null);
		WebElement tabList1=driver.findElement(By.xpath("//android.widget.ListView[1]/android.widget.LinearLayout[2]"));
		aft_cnt=tabList1.findElement(By.id("com.vcast.mediamanager:id/text2")).getText();
		System.out.println("Access variable outside the class:" +aft_cnt);
		 return Constants.KEYWORD_PASS;
}
public String cmp_count(String object, String data) throws InterruptedException {
	
	APP_LOGS.debug("compare bfr_cnt and aft_cnt value");
	if(bef_count.equals(aft_cnt))
	{
		System.out.println("Both values are equal items not added into list");
		 return Constants.KEYWORD_FAIL;
	}
		else
		{
		System.out.println("Both values are not equal, so items added successfully items");
		}
	      	
	 return Constants.KEYWORD_PASS;
	}

public String verify_RmvFavlnk(String object, String data) throws InterruptedException {
	
	APP_LOGS.debug("Validate Favourites present and remove fav from the Album");
	String[] favObjects=object.split(",");
	String fav_name="Favorites";
	String name_favtxt=driver.findElement(By.xpath(OR.getProperty(favObjects[0]))).getText();
	if(name_favtxt.equals(fav_name))
	{
		System.out.println("Favourites items present ");
        //commonActionbar
		clickLink(favObjects[1],null);
        clickLink(favObjects[2],null);
        select_list_All_item(favObjects[3],null);
        synchronize(null,null);
        clickLink(favObjects[4],null);
        synchronize(null,null);
        //remove from favoruites
        clickLink(favObjects[5],null);
        synchronize(null,null);
        synchronize(null,null);
        //Validate for No Fav present after removal
        String result=verifypresent(favObjects[6],null);
        if (result.contains("Pass"))
		System.out.println("Fav items removed successfully");
        else 
        {
        	System.out.println("Items not removed successfully");
		 return Constants.KEYWORD_FAIL;
        }
	}
		else
		{
		System.out.println("Favourites items not present in the list");
		}
	      	
	 return Constants.KEYWORD_PASS;
	}

public String select_createalbum(String object, String data) {
	/*
	 * Created by Shashi , Helps to select add label creation in
	 * New Album creation
	 */

	APP_LOGS.debug("To validate Favourite icon present in the list ");
	APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
	APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
	String[] albumList=object.split(",");
	System.out.println("Entered the select create album function");
	try {
		         
		if(driver.findElements(By.id("com.vcast.mediamanager:id/empty_view_image")).size() > 0)
         {
        	 //Inside + symbol
			
        	 driver.findElement(By.xpath(OR.getProperty(albumList[1]))).click();
         }
         else
         {    
        	 //Tap right corner + symbol
        	 driver.findElement(By.xpath(OR.getProperty(albumList[2]))).click();
         }
		

	} catch (Exception e) {
		return Constants.KEYWORD_FAIL + e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}
	
public String clickLink_xpath(String object, String data){
	APP_LOGS.debug("clicking the link");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Input parameter ::-> "+data);

	try{
	

		driver.findElement(By.xpath(OR.getProperty(object))).click();

		return Constants.KEYWORD_PASS;
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}
	//}
}
public String createmusic_album(String object, String data) {
	/*
	 * Created by Shashi , Helps to select add label creation in
	 * New Album creation
	 */

	APP_LOGS.debug("To validate Favourite icon present in the list ");
	APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
	APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
	String[] albumList=object.split(",");
	System.out.println("Entered the select create album function");
	try {
		         
		if(driver.findElements(By.id("com.vcast.mediamanager:id/empty_view_action_link_container")).size() > 0)
         {
        	 //Inside + symbol
			
        	 driver.findElement(By.xpath(OR.getProperty(albumList[1]))).click();
         }
         else
         {    
        	 //Tap right corner + symbol
        	 driver.findElement(By.xpath(OR.getProperty(albumList[2]))).click();
         }
		

	} catch (Exception e) {
		return Constants.KEYWORD_FAIL + e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}
public String longPress(String object, String data) {
	/*
	 * Created by Shashi , Helps to select add label creation in
	 * New Album creation
	 */

	APP_LOGS.debug("Tap on Longpress to select the song ");
	APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
	APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
	
	List<WebElement> tabList=driver.findElements(By.id(OR.getProperty(object)));
	
	try {		
		
		TouchAction action = new TouchAction(driver);
		//TouchAction action1 = new TouchAction(driver);
		//action0 = TouchAction().tap(tabList)
				//action1 = TouchAction().tap(el)
				//MultiAction().add(action0).add(action1).perform()
		action.longPress((WebElement) tabList).release().perform();
		tabList.get(0).click();
	} catch (Exception e) {
		return Constants.KEYWORD_FAIL + e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}
public String clickFirst_Listitem(String object, String data) {
	/*
	 * Created by Shashi , Function to help select first element and click on it
	 * 
	 */

	APP_LOGS.debug("Tap on Longpress to select the song ");
	APP_LOGS.debug("Executing Keyword = " + CurrentKeyword);
	APP_LOGS.debug("object value is  ->> " + OR.getProperty(object));
	//String[] plylist=object.split(",");
	List<WebElement> tabList=driver.findElements(By.id(OR.getProperty(object)));
	
	try {		
		tabList.get(0).click();
	} catch (Exception e) {
		return Constants.KEYWORD_FAIL + e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}	
public String scrollToExact(String object, String data)throws InterruptedException
{ 
	//wait(object);
	APP_LOGS.debug("scroll to exact position");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Input parameter ::-> "+data);

	try{
		synchronize(null,null);
		driver.scrollToExact(data);
		System.out.println("Scroll data :"+data);
		return Constants.KEYWORD_PASS;
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}

}	
	
/*public String hideSoftKeyword(String object, String data)throws InterruptedException
{ 
	//wait(object);
	APP_LOGS.debug("Hide the keyword");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Input parameter ::-> "+data);

	try{
		synchronize(null,null);
		((AndroidDriver) driver).sendKeyEvent(AndroidKeyCode.BACK);
		//driver.hideKeyboard();
		return Constants.KEYWORD_PASS;
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}

}*/

public  String verifyListCount(String object, String data) throws InterruptedException{
	wait(object);
	APP_LOGS.debug("Verify List count matches displayed as expected");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Selected value is "+data);
	String[] allobjects =  object.split(",");
	int selectImagesCount=Integer.parseInt(data);
	System.out.println("Total Objects"+allobjects.length);
	// Thread.sleep(1000L);
	try{

		//WebElement imagesFrame=driver.findElement(By.className(OR.getProperty(allobjects[0])));
		//List<WebElement> selectImages = imagesFrame.findElements(By.id(OR.getProperty(allobjects[1]))); 
		List<WebElement> selectImages = driver.findElements(By.id(OR.getProperty(allobjects[1]))); 
		int imagesCount = selectImages.size();
		System.out.println("Total list items: "+imagesCount);		

		if(imagesCount==selectImagesCount)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL+" -- List items doesnt match as expected-";

	}catch(Throwable e){
		return Constants.KEYWORD_FAIL +" - Object not visible "+ e.getMessage();
	}
}
public  String searchShareAppfromList(String object,String data){
	/* 
	 * Created by Shashi , 
	 * validate to search for the apps given from the list
	 */
	APP_LOGS.debug("Searhing the sharing app from the list");

	try{
		String[] objectShrApp=object.split(",");
		String appName=objectShrApp[0].trim();
		//String[] dataShreApp=data.split(",");

		synchronize(null,null);
		Dimension size = driver.manage().window().getSize();
		int startY = (int)(size.height*0.69);
		int endY = (int)(size.height*0.40);
		int startx = size.width/2;
		System.out.println("Size startY End Y Startx"+size +"\r" +startY+"\r" +endY +"\r" +startx );
		for(int i=1;i<=5;i++)
		{
			synchronize(null,null);
			//driver.swipe(startx, startY, startx, endY, 1200);
			if(driver.findElements(By.name(data)).size() > 0)
			{
				System.out.println("Element found");
				break;
			}
			else
			{
				System.out.println("Element not found, so swipe through the list");
				driver.swipe(startx, startY, startx, endY, 1200);
				synchronize(null,null);
				synchronize(null,null);
				//driver.swipe(630, 710, 800, 500, 400); swipe up
				continue;
			}
		}
		System.out.println("test"+appName);
		//clickLink_name(appName,null);
	}catch(Exception e){
		return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

	}
	return Constants.KEYWORD_PASS;

}

public String clickItem_Present(String object, String data)throws InterruptedException
{ 
	//wait(object);
	APP_LOGS.debug("check item is present and if present click on it");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Input parameter ::-> "+data);

	
		/*if(driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		}*/

	try
	{
	if(driver.findElements(By.xpath(OR.getProperty(object))).size() > 0)
	{
		driver.findElement(By.xpath(OR.getProperty(object))).click();
	}

	}
	catch(Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}

/*public String backToforground(String object, String data)throws InterruptedException
{ 
	//wait(object);
	APP_LOGS.debug("To move app to background bring app to foreground");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	APP_LOGS.debug("Input parameter ::-> "+data);
	String[] dataInfo=data.split(",");
	driver.sendKeyEvent(AndroidKeyCode.HOME);
	
	try{
		driver.runAppInBackground(1);
		}
	catch(Exception e){return Constants.KEYWORD_FAIL+e.getMessage();}
     try {
    	  driver.sendKeyEvent(AndroidKeyCode.HOME);
    	  synchronize(null,null);
    	  driver.startActivity(dataInfo[0],dataInfo[1],null,null );
    	  
     }
	       catch(Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}
	return Constants.KEYWORD_PASS;
}*/
public  String verifyNotPresent_ID(String object,String data){


	APP_LOGS.debug("Verifying Element is present or not.");

	try{  

		//WebElement element=driver.findElement(By.id(OR.getProperty(object)));
		//System.out.println("value of element "+element);
		 if (driver.findElements(By.id(OR.getProperty(object))).size() < 0)
		//if (driver.findElement(By.id(OR.getProperty(object))).isDisplayed())
		{
			return Constants.KEYWORD_FAIL+" -- Element not present on page.";

		}	 
	/*	else
		{
			return Constants.KEYWORD_FAIL+" -- Element not present on page.";
		}*/
		return Constants.KEYWORD_PASS;
	}catch(Exception e){

		return Constants.KEYWORD_PASS;

	}

}

public String select_album_list(String object, String data){

	APP_LOGS.debug("clicking the button from list ");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	try{

		List<WebElement> tabList = driver.findElements(By.id(OR.getProperty(object)));
		tabList.get(1).click();
  
	}
	catch (Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}	
	return Constants.KEYWORD_PASS;	
}	


public String getContextText(String object, String data){

	APP_LOGS.debug("clicking the button from list ");
	APP_LOGS.debug("Executing Keyword = "+CurrentKeyword);
	APP_LOGS.debug("object value is  ->> "+OR.getProperty(object));
	try{

		//String contnt = driver.findElement(By.xpath(OR.getProperty(object))).getText();
		//System.out.println("the content value"+contnt);
		String contnt = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("name");
		//String contnt2 = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
		//String contnt1 = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("content-desc");
		
		System.out.println("the content value"+contnt);
	}
	catch (Exception e){
		return Constants.KEYWORD_FAIL+e.getMessage();
	}	
	return Constants.KEYWORD_PASS;	
}	













}