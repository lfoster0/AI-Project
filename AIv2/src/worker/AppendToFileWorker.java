/* Worker used to append solved puzzles to the currently
 * selected file in memory.  Occurs when the user presses the
 * APPEND button on the Training Data tab.  If no file is selected
 * the worker exits.  After appending the file is reread
 * so that the new encoded training tuples are put into memory.
 */
package worker;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingWorker;

import neuralnetwork.EightPuzzleTrainingData;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.SearchListNode;
import encoder.EightPuzzleBinaryEncoder;
import encoder.EncoderFileWriter;

import main.Main;
import main.SharedMem;

public class AppendToFileWorker extends SwingWorker<Void, Void>{
	  
	@Override
	protected Void doInBackground() throws Exception {
		if(SharedMem.getSolvedPuzzles().values().size() == 0){
			Main.window.output("No solutions in memory, run tests before saving");
			return null;
		}
		if(SharedMem.getCurrentFile() != null)
		{
			EncoderFileWriter writer = new EncoderFileWriter(SharedMem.getCurrentFile());
			EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
			
			for(SearchListNode node : SharedMem.getSolvedPuzzles().values())
			{	
				while(node.getParent() != null){
					writer.append(encoder.encode((EightPuzzle) node.getParent().getState(),(Move)node.getMove()));
					node = node.getParent();
				}
			}
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void done()
	{
			Main.window.lblNumPuzzles.setText(SharedMem.getTrainingData().size() + "");
			Main.window.output("Finished Writing To File");
	}
}
