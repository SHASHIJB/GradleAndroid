package util;
// reads the xls files and generates corresponding html reports

import static main.Intial.driver;
import static web.mobile.test.DriverScript.APP_LOGS;
import static web.mobile.test.DriverScript.CurrentKeyword;
import static web.mobile.test.DriverScript.OR;
import io.appium.java_client.MobileElement;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

//import util.TestUtil;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import web.mobile.test.Constants;
import web.mobile.xlsread.Xls_Reader;

public class ReportUtil {
	public static String result_FolderName=null;
	static List<String> filesListInDir = new ArrayList<String>();

	public static void  createReportDir() throws Exception {
		// read suite.xls
		System.out.println("executing");
		
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		result_FolderName="Reports"+"_"+date;
			
		String reportsDirPath=Constants.projectBaseDir+"/Report/DSR/";
		File directory = new File(reportsDirPath, result_FolderName);
		File sourceFile = new File(
				Constants.projectBaseDir+"/Report");

		File destFile =directory;

		

		FileChannel source = null;
		FileChannel destination = null;

		if (!directory.exists()) {
			System.out.println("creating directory: " + reportsDirPath);
			boolean result = false;

			try{
				directory.mkdir();
				result = true;
			} 
			catch(SecurityException se){
				//handle it
			}        
			if(result) {    
				System.out.println("DIR created"); 
			}
		}
		
		File destinationDir=new File(Constants.sourceFile+"/MergeTestReport");
		File desDSRDir=new File(Constants.sourceFile+"DSR"+result_FolderName+"/");
		File desDSRDirHTML=new File(Constants.sourceFile+"/DSR/"+result_FolderName+"/HTMLReports/");
		File desDSRDirLogs=new File(Constants.sourceFile+"/DSR/"+result_FolderName+"/Logs/");
		File sourceMergeDir=new File(Constants.sourceFile+"/AutomationReports");

		File destMergeDir=new File(Constants.sourceFile+"/MergeTestReport/HTMLReports");
		File sourceLogsDir=new File(Constants.projectBaseDir+"/src/main/java/web/mobile/logs");
		File destLogsDir=new File(Constants.sourceFile+"/MergeTestReport/Logs");

		//	Copy and merge all HTML report files for Execution Summary
		CopyFile(Constants.sourceMergeHTMLDir, Constants.destMergeHTMLDir);

		//	Execution Logs	(Appium logs, analytic logs, Console logs)
		CopyFile(Constants.sourceLogsDir, Constants.destLogsDir);
		System.out.println("desDSRDir="+desDSRDir);

		//	Copy and merge all HTML report files for Execution Summary in daily status report directory
		CopyFile(Constants.sourceMergeHTMLDir, desDSRDirHTML);

		//	Execution Logs	(Appium logs, analytic logs, Console logs) in daily status report directory
		CopyFile(Constants.sourceLogsDir, desDSRDirLogs);
		
//		Copy and merge all HTML report files for Execution Summary
			//CopyFile(Constants.destMergeHTMLDir, desDSRDir);

			//	Execution Logs	(Appium logs, analytic logs, Console logs)
			//CopyFile(Constants.sourceLogsDir,desDSRDir);
			
			
		executeShell("sh"+" "+Constants.mergeHTMLFiles,"null");

		// String zipDirName = reportsDirPath+result_FolderName+"/"+"/AndroidTestReport.zip";
		String zipDirName = Constants.projectBaseDir+"/Report/VZCloudAndroidTestReport.zip";

		zipDirectory(Constants.destinationDir, zipDirName);

		SendMail.sendNotification();

	}
	public static void purgeDirectoryButKeepSubDirectories(File dir) {
	    for (File file: dir.listFiles()) {
	        if (!file.isDirectory()) file.delete();
	    }
	}

	public static void CopyFile(File sourceFile, File destFile) {

		try {
			FileUtils.copyDirectory(sourceFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void deleteFolder(File f2) {
	    File[] files = f2.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    f2.delete();
	}
	private static void doMove(File source, File dest) {



		// Move file to a new directory
		boolean success = source.renameTo(new File(dest, source.getName()));

		if (success) {
			System.out.println("File was successfully moved.\n");
		} else {
			System.out.println("File was not successfully moved.\n");
		}
	}
	/**
	 * This method zips the directory
	 * @param dir
	 * @param zipDirName
	 */
	private static void zipDirectory(File dir, String zipDirName) {
		try {
			populateFilesList(dir);
			//now zip files one by one
			//create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for(String filePath : filesListInDir){
				System.out.println("Zipping "+filePath);
				//for ZipEntry we need to keep only relative file path, so we used substring on absolute path
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
				zos.putNextEntry(ze);
				//read the file and write to ZipOutputStream
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method populates all the files in a directory to a List
	 * @param dir
	 * @throws IOException
	 */
	private static void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for(File file : files){
			if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
			else populateFilesList(file);
		}
	}

	/**
	 * This method compresses the single file to zip format
	 * @param file
	 * @param zipFileName
	 */
	private static void zipSingleFile(File file, String zipFileName) {
		try {
			//create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			//add a new Zip Entry to the ZipOutputStream
			ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);
			//read the file and write to ZipOutputStream
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}

			//Close the zip entry to write to zip file
			zos.closeEntry();
			//Close resources
			zos.close();
			fis.close();
			fos.close();
			System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);

		} catch (IOException e) {
			e.printStackTrace();
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

}