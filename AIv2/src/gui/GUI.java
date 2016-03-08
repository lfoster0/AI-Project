/* Swing Gui generated from windowbuilder in eclipse
 * Uses SwingWorkers to do various tasks.  
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.SharedMem;
import puzzle.Move;
import search.SearchListNode;
import search.SearchableObject;
import worker.AppendToFileWorker;
import worker.BFSPuzzleSolverWorker;
import worker.GeneticAlgorithmWorker;
import worker.OpenFileWorker;
import worker.SaveFileWorker;
import worker.UnitTestWorker;

public class GUI {

	public JFrame frame;
	public JTextField textField_PuzzlesToSolve;
	private JTextArea output;
	private JLabel lblSolvedPuzzlesInMem;
	public JLabel lblFileName;
	public JLabel lbl_AppendTo;
	public JLabel lblNumPuzzles;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JTextField PopulationTextField;
	public JRadioButton XORRadioButton;
	public JRadioButton EightPuzzleRadioButton;
	public JSlider PercentToMutateBy;
	public JSlider PercentToMutate;
	public JSlider CrossoverMutationSlider;
	public JSlider PercentEliteSlider;
	public JButton btnTrain;
	public JTextField generationsTextField;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 675, 585);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOutput.setBounds(10, 394, 56, 16);
		frame.getContentPane().add(lblOutput);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.7);
		splitPane.setBounds(0, 0, 674, 394);
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JButton btnOpen = new JButton("Open File");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenFileWorker worker = new OpenFileWorker();
				worker.execute();	
			}
		});
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOpen.setBounds(10, 11, 135, 33);
		panel.add(btnOpen);
		
		JLabel lblUsing = new JLabel("Using:");
		lblUsing.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsing.setBounds(12, 80, 133, 16);
		panel.add(lblUsing);
		
		JLabel lblPuzzlesInFile = new JLabel("Tuples In File:");
		lblPuzzlesInFile.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPuzzlesInFile.setBounds(12, 138, 133, 16);
		panel.add(lblPuzzlesInFile);
		
		lblFileName = new JLabel("None");
		lblFileName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFileName.setForeground(Color.GRAY);
		lblFileName.setBounds(12, 109, 133, 16);
		panel.add(lblFileName);
		
		lblNumPuzzles = new JLabel("0");
		lblNumPuzzles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNumPuzzles.setForeground(Color.GRAY);
		lblNumPuzzles.setBounds(12, 167, 133, 16);
		panel.add(lblNumPuzzles);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 194, 155, 2);
		panel.add(separator_2);
		
		JLabel lblSolvedPuzzlesIn = new JLabel("<html>Solved Puzzles In Memory:</html>");
		lblSolvedPuzzlesIn.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSolvedPuzzlesIn.setBounds(10, 202, 133, 44);
		panel.add(lblSolvedPuzzlesIn);
		
		lblSolvedPuzzlesInMem = new JLabel("0");
		lblSolvedPuzzlesInMem.setForeground(Color.GRAY);
		lblSolvedPuzzlesInMem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSolvedPuzzlesInMem.setBounds(10, 245, 133, 16);
		panel.add(lblSolvedPuzzlesInMem);
		
		JButton btnClear = new JButton("Clear Memory");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SharedMem.setSolvedPuzzles(new HashMap<SearchableObject,SearchListNode<Move>>());
				lblSolvedPuzzlesInMem.setText("0");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setBounds(12, 286, 133, 23);
		panel.add(btnClear);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		splitPane.setLeftComponent(tabbedPane);
		
		JPanel panelTrainingData = new JPanel();
		tabbedPane.addTab("Training Data", null, panelTrainingData, null);
		panelTrainingData.setLayout(null);
		
		JLabel lblPuzzlesToSolve = new JLabel("Puzzles To Solve:");
		lblPuzzlesToSolve.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPuzzlesToSolve.setBounds(12, 13, 158, 41);
		panelTrainingData.add(lblPuzzlesToSolve);
		
		textField_PuzzlesToSolve = new JTextField();
		textField_PuzzlesToSolve.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_PuzzlesToSolve.setBounds(185, 13, 116, 41);
		panelTrainingData.add(textField_PuzzlesToSolve);
		textField_PuzzlesToSolve.setColumns(10);
		
		final JLabel lblError_PuzzlesToSolve = new JLabel("");
		lblError_PuzzlesToSolve.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblError_PuzzlesToSolve.setForeground(Color.RED);
		lblError_PuzzlesToSolve.setBounds(12, 65, 289, 14);
		panelTrainingData.add(lblError_PuzzlesToSolve);
		
		JButton btnNewButton = new JButton("Solve");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					BFSPuzzleSolverWorker worker = new BFSPuzzleSolverWorker();
					worker.execute();
			}
		});
		btnNewButton.setBounds(333, 13, 164, 41);
		panelTrainingData.add(btnNewButton);
		
		lbl_AppendTo = new JLabel("Append Training Data To: ");
		lbl_AppendTo.setForeground(Color.GRAY);
		lbl_AppendTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_AppendTo.setBounds(236, 201, 261, 32);
		panelTrainingData.add(lbl_AppendTo);
		
		JButton btn_SaveTrainingData = new JButton("Save");
		btn_SaveTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveFileWorker worker = new SaveFileWorker();
				worker.execute();
				
			}
		});
		btn_SaveTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_SaveTrainingData.setBounds(12, 153, 171, 41);
		panelTrainingData.add(btn_SaveTrainingData);
		
		JButton btn_AppendTrainingData = new JButton("Append");
		btn_AppendTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppendToFileWorker worker = new AppendToFileWorker();
				worker.execute();
			}
		});
		btn_AppendTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_AppendTrainingData.setBounds(236, 153, 171, 41);
		panelTrainingData.add(btn_AppendTrainingData);
		
		JLabel label = new JLabel("Save Training Data");
		label.setForeground(Color.GRAY);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(12, 201, 171, 32);
		panelTrainingData.add(label);
		

		
		JPanel panelGeneticAlgorithm = new JPanel();
		tabbedPane.addTab("Genetic Algorithm", null, panelGeneticAlgorithm, null);
		panelGeneticAlgorithm.setLayout(null);
		
		btnTrain = new JButton("Train");
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneticAlgorithmWorker ga = new GeneticAlgorithmWorker();
				ga.execute();
			}
		});
		btnTrain.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTrain.setBounds(385, 309, 116, 31);
		panelGeneticAlgorithm.add(btnTrain);
		
		XORRadioButton = new JRadioButton("XOR");
		buttonGroup.add(XORRadioButton);
		XORRadioButton.setBounds(181, 315, 68, 25);
		panelGeneticAlgorithm.add(XORRadioButton);
		
		EightPuzzleRadioButton = new JRadioButton("Eight Puzzle");
		buttonGroup.add(EightPuzzleRadioButton);
		EightPuzzleRadioButton.setBounds(250, 315, 127, 25);
		panelGeneticAlgorithm.add(EightPuzzleRadioButton);
		
		JLabel lblNewLabel_1 = new JLabel("% Elite");
		lblNewLabel_1.setToolTipText("Percentage of Population to make elite");
		lblNewLabel_1.setBounds(12, 53, 96, 16);
		panelGeneticAlgorithm.add(lblNewLabel_1);
		
		PercentEliteSlider = new JSlider();
		PercentEliteSlider.setValue(20);
		PercentEliteSlider.setPaintLabels(true);
		PercentEliteSlider.setPaintTicks(true);
		PercentEliteSlider.setMajorTickSpacing(10);
		PercentEliteSlider.setBounds(82, 44, 354, 52);
		panelGeneticAlgorithm.add(PercentEliteSlider);
		
		JLabel lblPopulationSize = new JLabel("Population Size");
		lblPopulationSize.setBounds(12, 13, 96, 16);
		panelGeneticAlgorithm.add(lblPopulationSize);
		
		PopulationTextField = new JTextField();
		PopulationTextField.setBounds(132, 13, 96, 22);
		panelGeneticAlgorithm.add(PopulationTextField);
		PopulationTextField.setColumns(10);
		
		JLabel lblCrossover = new JLabel("% Crossover");
		lblCrossover.setBounds(12, 123, 96, 16);
		panelGeneticAlgorithm.add(lblCrossover);
		
		CrossoverMutationSlider = new JSlider();
		CrossoverMutationSlider.setEnabled(false);
		CrossoverMutationSlider.setValue(100);
		CrossoverMutationSlider.setPaintTicks(true);
		CrossoverMutationSlider.setPaintLabels(true);
		CrossoverMutationSlider.setMajorTickSpacing(10);
		CrossoverMutationSlider.setBounds(82, 109, 354, 52);
		panelGeneticAlgorithm.add(CrossoverMutationSlider);
		
		JLabel lblMutation = new JLabel("% Mutation");
		lblMutation.setBounds(437, 123, 66, 16);
		panelGeneticAlgorithm.add(lblMutation);
		
		JLabel lblPercentageOfGenes = new JLabel("<html> % Of Genes <br> To Mutate </html>");
		lblPercentageOfGenes.setBounds(12, 174, 81, 52);
		panelGeneticAlgorithm.add(lblPercentageOfGenes);
		
		PercentToMutate = new JSlider();
		PercentToMutate.setValue(20);
		PercentToMutate.setPaintTicks(true);
		PercentToMutate.setPaintLabels(true);
		PercentToMutate.setMajorTickSpacing(10);
		PercentToMutate.setBounds(82, 174, 354, 52);
		panelGeneticAlgorithm.add(PercentToMutate);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(-9, 353, 761, 2);
		panelGeneticAlgorithm.add(separator);
		
		JLabel lblToMutate = new JLabel("<html> % To <br> Mutate By </html>");
		lblToMutate.setBounds(12, 238, 81, 52);
		panelGeneticAlgorithm.add(lblToMutate);
		
		PercentToMutateBy = new JSlider();
		PercentToMutateBy.setPaintTicks(true);
		PercentToMutateBy.setPaintLabels(true);
		PercentToMutateBy.setMajorTickSpacing(10);
		PercentToMutateBy.setBounds(82, 238, 354, 52);
		panelGeneticAlgorithm.add(PercentToMutateBy);
		
		JLabel lblNumberOfGenerations = new JLabel("Generations");
		lblNumberOfGenerations.setBounds(242, 9, 96, 16);
		panelGeneticAlgorithm.add(lblNumberOfGenerations);
		
		generationsTextField = new JTextField();
		generationsTextField.setColumns(10);
		generationsTextField.setBounds(322, 9, 96, 22);
		panelGeneticAlgorithm.add(generationsTextField);
		
		JPanel panelOther = new JPanel();
		tabbedPane.addTab("Other", null, panelOther, null);
		panelOther.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Run Unit Tests");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UnitTestWorker worker = new UnitTestWorker();
				worker.execute();
			}
		});
		btnNewButton_1.setBounds(10, 44, 133, 40);
		panelOther.add(btnNewButton_1);
		
		output = new JTextArea();
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		output.setBounds(10, 358, 649, 124);
		frame.getContentPane().add(output);
		
		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 413, 649, 124);
		frame.getContentPane().add(scrollPane);
	}
	
	public void output(String s)
	{
		Document doc = output.getDocument();
		try {
			doc.insertString(doc.getLength(), s + "\n", null);
		} catch (BadLocationException e) {
			//TODO
		}
	}
	
	public void setNumSolvedPuzzlesLabel(String s)
	{
		lblSolvedPuzzlesInMem.setText(s);
	}
}
