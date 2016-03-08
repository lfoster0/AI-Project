/* Used by workers in the worker package to make changes to the program.
 * All methods are synchronized to avoid concurrency issues.
 */
package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.SearchListNode;
import search.SearchableObject;
import neuralnetwork.*;
public class SharedMem {
		//Puzzles solved by Breadth First Search 
		private static HashMap<SearchableObject, SearchListNode<Move>> solvedPuzzles = new HashMap<SearchableObject,SearchListNode<Move>>();
		//The goal state used in Breadth First Search
		private static SearchListNode<Move> goalState = new SearchListNode<Move>(null, new EightPuzzle(), 0.0, Move.START);
		//Current File containing training tuples
		private static File currentFile = null;
		//List of training tuples loaded in from currentFile
		private static ArrayList<EightPuzzleTrainingData> trainingData = new ArrayList<EightPuzzleTrainingData>();
		//The current Neural Network used in the Neural Network tab
		private static EightPuzzleNeuralNetwork neuralNetwork = null;
		//File to save experiment data to
		private static File saveExperimentFile = null;
		
		public synchronized static File getCurrentFile() {
			return currentFile;
		}
		public synchronized static void setCurrentFile(File currentFile) {
			SharedMem.currentFile = currentFile;
		}
		public synchronized static HashMap<SearchableObject, SearchListNode<Move>> getSolvedPuzzles() {
			return solvedPuzzles;
		}
		public synchronized static void setSolvedPuzzles(
				HashMap<SearchableObject, SearchListNode<Move>> solvedPuzzles) {
			SharedMem.solvedPuzzles = solvedPuzzles;
		}
		public synchronized static SearchListNode<Move> getGoalState() {
			return goalState;
		}
		public synchronized static void setGoalState(SearchListNode<Move> goalState) {
			SharedMem.goalState = goalState;
		}
		public synchronized static ArrayList<EightPuzzleTrainingData> getTrainingData() {
			return trainingData;
		}
		public synchronized static void setTrainingData(
				ArrayList<EightPuzzleTrainingData> trainingData) {
			SharedMem.trainingData = trainingData;
		}
		public synchronized static EightPuzzleNeuralNetwork getNeuralNetwork() {
			return neuralNetwork;
		}
		public synchronized static void setNeuralNetwork(EightPuzzleNeuralNetwork neuralNetwork) {
			SharedMem.neuralNetwork = neuralNetwork;
		}
		public static File getSaveExperimentFile() {
			return saveExperimentFile;
		}
		public static void setSaveExperimentFile(File saveExperimentFile) {
			SharedMem.saveExperimentFile = saveExperimentFile;
		} 
		
		
}
