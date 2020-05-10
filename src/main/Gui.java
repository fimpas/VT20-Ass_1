package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class is the start of the whole project.
 * It declares the Gui, and starts to initialise the class Start.
 * The class Start will go through the classes required to calculate the Y-matrix.
 * When finished, Gui will get the finished Y-Matrix and present it in a table.
 */
public class Gui { 

	int xmlchoice;
	boolean start;
	File xmlEQ;
	File xmlSSH;

	public static void main(String[] args) {

		new Gui();
	}

	public Gui(){

		String simple = "Reduced Grid";
		String comp = "Complete Grid";
		String load = "Select own CIM-Files";

		this.xmlchoice = 1;
		this.start = false;

		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setTitle("EH2745 - Assignment 1: Y-matrix"); 

		JLabel emptyLabel = new JLabel("");
		emptyLabel.setPreferredSize(new Dimension(500, 200));
		frame.getContentPane().add(emptyLabel, BorderLayout.NORTH);

		////// RADIOBUTTONS //////////////
		JRadioButton simpleB = new JRadioButton(simple);
		JRadioButton compB = new JRadioButton(comp);
		JRadioButton loadB = new JRadioButton(load);

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(simpleB);
		radioGroup.add(compB);
		radioGroup.add(loadB);

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(simpleB);
		radioPanel.add(compB);
		radioPanel.add(loadB);
		radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select set of CIM-files"));
		frame.add(radioPanel, BorderLayout.WEST);

		////// MENUBAR //////////////
		JMenuBar mBar = new JMenuBar();
		JMenu mBar1Menu = new JMenu("MENU");
		mBar.add(mBar1Menu);
		JMenuItem mBar2Help = new JMenuItem("Help");
		mBar.add(mBar2Help);
		JMenuItem mBarMenuExit = new JMenuItem("Exit");
		mBar1Menu.add(mBarMenuExit);

		////// SETTINGS PARAMETERS //////////////
		JPanel panelEQ = new JPanel(new GridLayout(4, 2, 10, 10)); 
		JLabel labelEQ = new JLabel("Select local EQ file");
		JButton bEQ = new JButton("Browse");
		panelEQ.add(labelEQ);
		panelEQ.add(bEQ);

		JPanel panelSSH = new JPanel(new GridLayout(4, 2, 10, 10)); 
		JLabel labelSSH = new JLabel("Select local SSH file");
		JButton bSSH = new JButton("Browse");
		panelEQ.add(labelSSH);
		panelEQ.add(bSSH);

		JPanel panel = new JPanel(); 
		JLabel sBase = new JLabel("Set S-Base [MVA]");
		JTextField val = new JTextField("100");
		JButton startB = new JButton("Calculate Y-Matrix");
		startB.setFont(new Font("Default", Font.BOLD, 14));
		startB.setForeground(Color.GRAY);

		panelEQ.add(sBase);
		panelEQ.add(val);
		panel.add(startB);
		frame.pack();

		panelEQ.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Choose Parameters"));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));

		// Adding frame componentS
		frame.getContentPane().add(BorderLayout.NORTH, mBar);
		frame.getContentPane().add(BorderLayout.WEST, radioPanel);
		frame.getContentPane().add(BorderLayout.CENTER, panelEQ);
		frame.getContentPane().add(BorderLayout.EAST, panelSSH);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);

		frame.setVisible(true);
		startB.setVisible(true);

		////// ACTION LISTENERS //////////////
		///////// MENUBAR

		mBar2Help.addActionListener((ActionEvent event) -> {

			JOptionPane.showMessageDialog(null, "1. Select which set of XML-files to use. \n" + 
			"\t\t 1.1. Reduced Grid XML-files \n\t\t 1.2. Complete Grid XML-files or \n\t\t 1.3. Browse and choose you own grid XML-files. \n" + 
					"\t\t\t\t 1.3.1. Select your EQ-file \n\t\t\t\t 1.3.2. Select your SSH-file. \n\n " +  
			"2. Set your Sbase, default value is 100 MVA. \n\n" + 
					"3. Press 'Calculate Y-Matrix' and evalute the results.");
		});
		mBarMenuExit.addActionListener((ActionEvent event) -> {

			JOptionPane.showMessageDialog(null, "Good Bye!");
			System.exit(0);
		});
		///////// SELECT XML OPTION 
		bEQ.addActionListener((ActionEvent event) -> {
			this.xmlEQ = select();
			if (this.xmlEQ == null) {
				JOptionPane.showMessageDialog(null, "Please, select a valid EQ-file");
			} 
			else {
				bSSH.setEnabled(true);
			}
		});
		bSSH.addActionListener((ActionEvent event) -> {
			this.xmlSSH = select();
			if (this.xmlSSH == null) {
				JOptionPane.showMessageDialog(null, "Please, select a valid SSH-file");
			} 
			else if (this.xmlSSH == this.xmlEQ) {
				JOptionPane.showMessageDialog(null, "Please, select a valid SSH-file");
			} 
			else {
				this.start=true;
				startB.setForeground(Color.BLACK);

			}
		});
		///////// BASIC XML OPTION  

		simpleB.addActionListener((ActionEvent event) -> {
			this.xmlchoice = 1;
			bEQ.setEnabled(false);
			bSSH.setEnabled(false);
			this.xmlEQ = new File("Assignment_EQ_reduced.xml");
			this.xmlSSH = new File("Assignment_SSH_reduced.xml");
			this.start = true;
			startB.setForeground(Color.BLACK);
		});
		///////// COMPLETE MICROGRID XML OPTION  

		compB.addActionListener((ActionEvent event) -> {
			this.xmlchoice = 2;
			bEQ.setEnabled(false);
			bSSH.setEnabled(false);
			this.xmlEQ = new File("MicroGridTestConfiguration_T1_BE_EQ_V2.xml");
			this.xmlSSH = new File("MicroGridTestConfiguration_T1_BE_SSH_V2.xml");
			//startB.setVisible(true);
			this.start = true;
			startB.setForeground(Color.BLACK);
		});

		///////// SELECT OWN XML OPTION  

		loadB.addActionListener((ActionEvent event) -> {
			this.xmlchoice = 3;
			bEQ.setEnabled(true);
			//startB.setVisible(false);
			this.start = false;
			startB.setForeground(Color.GRAY);

		});

		////// START CALCULATING YMATRIX //////////////

		startB.addActionListener((ActionEvent event) -> {
			if (this.start) {
				String stringVal = val.getText();
				System.out.println("Selected Sbase = "+Integer.parseInt(stringVal)+" MVA");

				Start startY = new Start(this.xmlEQ, this.xmlSSH, Integer.parseInt(stringVal));
				Object [][] yMatrix = startY.getYMatrix();
				String [] columnName = new String[yMatrix.length];
				for (int y = 0; y< yMatrix.length; y++) {
					columnName[y] = "Bus nr: " +(y+1);
				}

				JFrame yframe = new JFrame("Y matrix");
				yframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JTable yTable = new JTable(yMatrix, columnName);
				JScrollPane scrollPane = new JScrollPane(yTable);
				yframe.add(scrollPane, BorderLayout.CENTER);
				yframe.setSize(800, 300);
				yTable.setRowHeight(40);
				yTable.getTableHeader().setFont(new Font("Default", Font.BOLD, 15));
				yTable.setGridColor(Color.BLACK);
				yTable.setFont(new Font("Default", Font.BOLD, 12));
				yframe.setTitle("Y-matrix");
				yframe.setVisible(true);
			}
		});

	}

	private File select() {
		File select = null;
		JFileChooser fC = new JFileChooser();
		fC.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CIM-files", "xml");
		fC.setFileFilter(filter);
		int result = fC.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			select = fC.getSelectedFile();
		}
		return select;
	}
}
