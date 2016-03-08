/** Handles writing to file and loading a new file to append to.
 * 
 * TODO
 * Optimize this better.  
 * Do unique checking as you write to the file.
 * Don't write values already in memory like currently happening
 */
package encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class EncoderFileWriter {

	private File file;
	
	public EncoderFileWriter(File f){
		this.file = f;
	}

	/*Appends the given integer array to the end of the file
	  given when constructing the EncoderFileWriter.  Removes
	  duplicates from the file after appending
	*/
	public void append(int[] encodedData) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
			for(int i = 0; i<encodedData.length;i++){
				if(i == encodedData.length-1){
					out.write(""+encodedData[i]);
				}
				else{
					out.write(encodedData[i] + ",");
				}
			}
			out.write(""+System.getProperty("line.separator"));
			out.close();
			removeDuplicatesFromFile();
		} catch (Exception e) {
			System.out.println("Error writing to file try again");
			e.printStackTrace();
		}
		
	}

	/*Appends the given integer array to the end of the file
	  given when constructing the EncoderFileWriter.  Removes
	  duplicates from the file after appending
	*/
	public void append(double[] encodedData) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
			for(int i = 0; i<encodedData.length;i++){
				if(i == encodedData.length-1){
					out.write(""+encodedData[i]);
				}
				else{
					out.write(encodedData[i] + ",");
				}
			}
			out.write(""+System.getProperty("line.separator"));
			out.close();
			removeDuplicatesFromFile();
		} catch (Exception e) {
			System.out.println("Error writing to file try again");
			e.printStackTrace();
		}
		
	}
	
	/* Removes duplicates from a file.  
	 * Reads in all distinct lines, then rewrites the file only with those lines.
	 */
	private void removeDuplicatesFromFile() throws FileNotFoundException, IOException{
		BufferedReader fileReader = new BufferedReader(new FileReader(file));
		HashMap<String, String> uniqueLines = new HashMap<String,String>();
		String s = "";
		while((s = fileReader.readLine()) != null)
		{
			uniqueLines.put(s, s);
		}
		
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(String tmp : uniqueLines.keySet())
		{
			out.write(tmp);
			out.write(""+System.getProperty("line.separator"));
		}
		
		out.close();		
	}

}
