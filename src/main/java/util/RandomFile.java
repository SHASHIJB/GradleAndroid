package util;

import java.io.RandomAccessFile;
import java.util.concurrent.ThreadLocalRandom;
import web.mobile.test.Constants;

public class RandomFile {


	public static void main(String[]args) {		
		long MB = 1024 * 1024;
		long GB = 1024 * MB;
		String fileExt=".pdf,.txt,.doc,.mp3,."
				+ "mp4";
		String[] fileType=fileExt.split(",");
		int fileTypeCount=fileType.length;
		System.out.println(fileTypeCount);
		
		for (int i = 0 ; i < 2; ++i){
			
			for(int j=0; j<fileTypeCount;j++)
			{
			
			genfile(MB,fileType[j]);
			genfile(GB,fileType[j]);
			}
		}
	}
	
	public static void genfile(long lUnit, String fileType) {
		
		try {
			String result = System.currentTimeMillis() + Integer.toString(ThreadLocalRandom.current().nextInt(1, 99));
			System.out.println(result);
			
			String name = Constants.docDir+result + fileType;
			RandomAccessFile f = new RandomAccessFile(name,"rw");
				
			f.writeChars(result);
			f.setLength(ThreadLocalRandom.current().nextInt(1, 5) * lUnit);
		}	
		catch (Exception e){
			System.out.println("\nError in making your file ! " + e);
		}
	}
}
