package worker;

import javax.swing.SwingWorker;

import main.Main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import tests.BreadthFirstSearchTest;
import tests.EightPuzzleBinaryEncoderTest;
import tests.EightPuzzleTest;
import tests.EncoderFileWriterTest;
import tests.GeneticAlgorithmTest;
import tests.PopulationMemberTest;
import tests.SearchListNodeTest;
import tests.XORNeuralNetworkTest;

public class UnitTestWorker extends SwingWorker<Void, Void>{

	@Override
	protected Void doInBackground() throws Exception {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(BreadthFirstSearchTest.class);
		Main.window.output("BreadthFirstSearchTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());	

		result = junit.run(EightPuzzleBinaryEncoderTest.class);
		Main.window.output("EightPuzzleBinaryEncoderTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(EightPuzzleTest.class);
		Main.window.output("EightPuzzleTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(EncoderFileWriterTest.class);
		Main.window.output("EncoderFileWriterTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(SearchListNodeTest.class);
		Main.window.output("SearchListNodeTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(XORNeuralNetworkTest.class);
		Main.window.output("XORNeuralNetworkTest: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(GeneticAlgorithmTest.class);
		Main.window.output("Genetic Algorithm Test: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		
		result = junit.run(PopulationMemberTest.class);
		Main.window.output("Population Member Test: \n \tPassed: " + result.wasSuccessful() + "\n" + "\tSuccessful Tests: " + result.getRunCount() + "\n" + "\tUnsuccessful Tests: " + result.getFailureCount());
		return null;
	}

}
