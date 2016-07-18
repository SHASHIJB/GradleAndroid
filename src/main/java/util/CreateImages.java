package util;
	
	
	import java.util.Random;
	import java.awt.Color;
	import java.awt.Graphics2D;
	import java.awt.image.BufferedImage;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.OutputStreamWriter;

	import javax.imageio.IIOImage;
	import javax.imageio.ImageIO;
	import javax.imageio.ImageWriteParam;
	import javax.imageio.ImageWriter;
	import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
	import javax.imageio.stream.ImageOutputStream;


	import web.mobile.test.Constants;
	/** Demo class that showcases creation of JPEG / JPG files from Java

	 *  

	 * @author Jaya Sahani

	 */

	public class CreateImages{





	    /* Creates a randomly generated two color JPEG and writes it to a file

	     *

	     */

	    public static String generate( String prefix ) throws Exception{

	        //Random random = new Random( 56743793 );

	        Random rand = new Random();
			 
		    int redValue = rand.nextInt(255);
		    int greenValue = rand.nextInt(255);
		    int blueValue = rand.nextInt(255);
		    System.out.println("Red: " + redValue +", Green: " + greenValue + ", Blue: " + blueValue);
		    Color clr = new Color(redValue, greenValue, blueValue);
	        int x,y = 0;

	       

	        //image block size in pixels, 1 is 1px, use smaller values for 

	        //greater granularity

	        int PIX_SIZE = 50;



	        //image size in pixel blocks

	        int X = 100;

	        int Y = 100;



	        BufferedImage bi = new BufferedImage( PIX_SIZE * X, PIX_SIZE * Y, 

	                                              BufferedImage.TYPE_3BYTE_BGR );

	        Graphics2D g=(Graphics2D)bi.getGraphics();

	       // String filename =  Constants.imageDir+prefix + ".jpg";

	        String filename =  "/Users/jsah0003/Desktop/Synchronoss/vzcloud_android/src/TestData/Images/"+prefix + ".jpg";

System.out.println();

	        for( int i =0; i < X; i++ ){



	            for( int j =0; j < Y; j++ ){



	                x = i * PIX_SIZE;

	                y = j * PIX_SIZE;



	               //this is a writing condition, my choice here is purely random

	               // just to generate some pattern

	               // this condition  

	               if ( ( i * j ) % 6 == 0 ){

	               

	                   g.setColor( clr );
	                 
	                   



	               }else if ( ( i + j ) % 5 == 0 ){

	               

	                   g.setColor( Color.RED);
	                   g.create(y, x, PIX_SIZE, PIX_SIZE);
	                   g.drawOval(x, y, PIX_SIZE, PIX_SIZE);

	               

	               }else{

	               

	                   g.setColor( clr );
	                   g.create();
	               

	               }//end else



	               //fil the rectangles with the pixel blocks in chosen color

	               g.fillRect( y, x , PIX_SIZE , PIX_SIZE );

	        

	            }//end for j

	        

	        }//end for i

	        

	        g.dispose();



	        saveToFile( bi, new File( filename ) ); 

	        return filename;



	    }//end method  





	    /** Saves jpeg to file

	     *

	     */

	    public static void saveToFile( BufferedImage img, File file ) throws IOException {

	        

	        ImageWriter writer = null;

	        java.util.Iterator iter = ImageIO.getImageWritersByFormatName("jpg");

	        

	        if( iter.hasNext() ){

	            writer = (ImageWriter)iter.next();

	        }

	        

	        ImageOutputStream ios = ImageIO.createImageOutputStream( file );

	        writer.setOutput(ios);



	        ImageWriteParam param = new JPEGImageWriteParam( java.util.Locale.getDefault() );

	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;

	        param.setCompressionQuality(0.98f);



	        writer.write(null, new IIOImage( img, null, null ), param);

	    

	    }//end method 





	    public static void main( String args[] ) throws Exception {

	    	for(int imgcount=0; imgcount<21000;imgcount++){
	    		
	    	
	    	String result = "";
	    	int maxlength=6;
	        int i = 0, n = 0, min = 33, max = 122;
	        while(i < maxlength)
	        {
	            n = (int)(Math.random() * (max - min) + min);
	            if(n >= 33 && n < 123)
	            {
	                result += (int)n;
	                ++i;
	            }
	        }
	        System.out.println(result);
	        String ab =result;

	    	CreateImages.generate( ab );
	    	
	    	}
	       System.out.println( "Image created." );
	    }//end main



	}//end class 




