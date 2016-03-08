/* Ran when the user presses the OPEN FILE button
 * on the side bar.  Opens the file selected by the user
 * and reads in all training data into memory. Overwrites any
 * training data already in memory when a new file is loaded.
 */
package worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import main.Main;
import main.SharedMem;
import neuralnetwork.EightPuzzleTrainingData;

public class OpenFileWorker extends SwingWorker<Void, Void>{

	@Override
	protected Void doInBackground() throws Exception {
		final JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File f = fc.getSelectedFile();
		if(f != null)
		{
			SharedMem.setCurrentFile(f);
			readInTrainingData();
		}

		return null;
	}
	
	protected Void readInTrainingData() throws Exception {
		SharedMem.setTrainingData(new ArrayList<EightPuzzleTrainingData>());
		try {
			Scanner s = new Scanner(SharedMem.getCurrentFile());
			String input = "";
			while((input = s.nextLine()) != null)
			{
				SharedMem.getTrainingData().add(new EightPuzzleTrainingData(input));
			}
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void done()
	{
		if(SharedMem.getCurrentFile() != null){
			int size = SharedMem.getTrainingData().size();
			Main.window.lblNumPuzzles.setText(size + "");
			Main.window.lblFileName.setText(SharedMem.getCurrentFile().getName());
			Main.window.lbl_AppendTo.setText("Append Training Data To: " + SharedMem.getCurrentFile().getName());
			Main.window.output("File Opened Successfully");
		}
		
	}
}
