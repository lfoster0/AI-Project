/* Runs when the user presses the SAVE button
 * on the Training Data tab.  Encodes all solved
 * puzzles in memory and saves them to the selected file.
 */

package worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.SearchListNode;
import encoder.EightPuzzleBinaryEncoder;
import encoder.EncoderFileWriter;

import main.*;
public class SaveFileWorker extends SwingWorker<Void, Void>{

	@Override
	protected Void doInBackground() throws Exception {
		if(SharedMem.getSolvedPuzzles().values().size() == 0){
			Main.window.output("No solutions in memory, run tests before saving");
			return null;
		}
		final JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		File f = fc.getSelectedFile();
		if(f != null)
		{
			EncoderFileWriter writer = new EncoderFileWriter(f);
			EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
			
			for(SearchListNode node : SharedMem.getSolvedPuzzles().values())
			{	
				while(node.getParent() != null){
					writer.append(encoder.encode((EightPuzzle) node.getParent().getState(),(Move)node.getMove()));
					node = node.getParent();
				}
			}
			
			Main.window.output("Finished Writing To File");
		}

		
		return null;
	}

}

