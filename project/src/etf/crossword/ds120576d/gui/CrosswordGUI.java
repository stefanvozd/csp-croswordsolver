package etf.crossword.ds120576d.gui;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import etf.crossword.ds120576d.crossword.CrosswordGame;
import etf.crossword.ds120576d.crossword.FullSpeedPlayer;
import etf.crossword.ds120576d.crossword.StepByStepPlayer;
import etf.crossword.ds120576d.crossword.core.Assignment;
import etf.crossword.ds120576d.crossword.core.Player;
import etf.crossword.ds120576d.crossword.core.Variable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CrosswordGUI.
 */
public class CrosswordGUI {
	
	/** The Constant rows. */
	final static int rows = 4;

	/** The cwframe. */
	private JFrame cwframe;
	
	/** The ctrlrame. */
	private JFrame ctrlrame;

	/** The initcw. */
	private JButton initcw;
	
	/** The nextstep. */
	private JButton nextstep;

	/** The player. */
	Player player;

	/** The vars. */
	private List<Variable> vars = new ArrayList<Variable>();
	
	/** The domains. */
	private List<String> domains = new ArrayList<String>();

	/** The game. */
	private CrosswordGame game;

	/** The stopstep. */
	private JButton stopstep;

	/** The autostep. */
	private JButton autostep;

	/** The fasterautostep. */
	private JButton fasterautostep;

	/** The fullplay. */
	private JButton fullplay;

	/** The chooser. */
	JFileChooser chooser;

	/** The dic_path. */
	private String dic_path;

	/** The slowerautostep. */
	private JButton slowerautostep;

	/** The btn_browse. */
	private JButton btn_browse;

	/**
	 * The Class CWJPanel.
	 */
	public class CWJPanel extends JPanel {
		
		/** The h label. */
		public JLabel hLabel;
		
		/** The v label. */
		public JLabel vLabel;
		
		/** The letter. */
		public JLabel letter = new JLabel("-");
		
		/** The disabled. */
		public boolean disabled = false;
	}

	/** The field. */
	public static CWJPanel[][] field = new CWJPanel[rows][rows];

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					CrosswordGUI window = new CrosswordGUI();
					window.cwframe.setVisible(true);
					window.ctrlrame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public CrosswordGUI() throws FileNotFoundException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void initialize() throws FileNotFoundException, IOException {
		cwframe = new JFrame();
		cwframe.setBounds(200, 0, 500, 500);
		cwframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cwframe.getContentPane().setLayout(new GridLayout(rows, rows, 3, 3));
		cwframe.getContentPane().setBackground(new Color(210, 210, 210));

		ctrlrame = new JFrame();
		ctrlrame.setBounds(700, 0, 700, 70);

		ctrlrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ctrlrame.getContentPane().setLayout(new GridLayout(1, 10));

		fullplay = new JButton("Speed Mode");
		fullplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				step2();
				step3();

				Thread t = new Thread() {
					public void run() {

						btn_browse.setEnabled(false);
						fullplay.setEnabled(false);
						initcw.setEnabled(false);

						player = new FullSpeedPlayer();
						game = new CrosswordGame(vars, domains, player);
						game.start();
					}
				};
				t.start();
			}

		});
		ctrlrame.getContentPane().add(fullplay);
		fullplay.setEnabled(false);

		initcw = new JButton("Story Mode");
		initcw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				step2();
				step3();

				Thread t = new Thread() {
					public void run() {
						fullplay.setEnabled(false);
						initcw.setEnabled(false);
						autostep.setEnabled(true);
						fasterautostep.setEnabled(true);
						slowerautostep.setEnabled(true);
						nextstep.setEnabled(true);
						stopstep.setEnabled(true);
						btn_browse.setEnabled(false);
						player = new StepByStepPlayer();
						game = new CrosswordGame(vars, domains, player);
						game.start();
					}
				};
				t.start();

			}
		});
		initcw.setEnabled(false);

		ctrlrame.getContentPane().add(initcw);

		autostep = new JButton("Auto");
		autostep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread() {
					public void run() {
						player.setDelay(750);

					}
				};
				t.start();

			}
		});
		ctrlrame.getContentPane().add(autostep);
		autostep.setEnabled(false);

		nextstep = new JButton("\u2265");
		nextstep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread() {
					public void run() {
						player.forceNextStep();
					}
				};
				t.start();

			}
		});
		ctrlrame.getContentPane().add(nextstep);
		nextstep.setEnabled(false);

		stopstep = new JButton("||");
		stopstep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread() {
					public void run() {
						player.setDelay(0);
					}
				};
				t.start();

			}
		});
		ctrlrame.getContentPane().add(stopstep);
		stopstep.setEnabled(false);

		slowerautostep = new JButton("<<");
		slowerautostep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread() {
					public void run() {

						player.playSlower();

					}
				};
				t.start();

			}
		});
		ctrlrame.getContentPane().add(slowerautostep);
		slowerautostep.setEnabled(false);

		fasterautostep = new JButton(">>");
		fasterautostep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread() {
					public void run() {

						player.playFaster();

					}
				};
				t.start();

			}
		});
		ctrlrame.getContentPane().add(fasterautostep);
		fasterautostep.setEnabled(false);

		btn_browse = new JButton("Browse");
		btn_browse.setBounds(317, 63, 89, 23);
		ctrlrame.getContentPane().add(btn_browse);

		btn_browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(System
						.getProperty("user.home")));
				chooser.setDialogTitle("Select dictionary");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				chooser.setAcceptAllFileFilterUsed(true);
				//
				if (chooser.showOpenDialog(ctrlrame) == JFileChooser.APPROVE_OPTION) {

					dic_path = chooser.getSelectedFile().getAbsolutePath();
					System.out.println(chooser.getSelectedFile()
							.getAbsolutePath());
					try {
						scanDomains(dic_path);
						fullplay.setEnabled(true);
						initcw.setEnabled(true);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chooser.setEnabled(false);
				} else {
					// System.out.println("No Selection ");
				}

			}
		});

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j] = new CWJPanel();
				field[i][j].setBackground(Color.white);
				field[i][j].setLayout(new GridLayout(3, 3));

				cwframe.getContentPane().add(field[i][j]);
			}
		}

		step0();
		step1();

	}

	/**
	 * Step0.
	 * Draws basic GUI table.
	 */
	private void step0() {

		// INIT GRID
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {

				for (int _i = 0; _i < 3; _i++) {
					for (int _j = 0; _j < 3; _j++) {
						if (field[i][j].disabled)
							continue;

						if (_i == 0 && _j == 2) {
							field[i][j].vLabel = new JLabel(" ", JLabel.RIGHT);
							field[i][j].vLabel.setFont(new Font("Calibri",
									Font.PLAIN, 10));
							field[i][j].vLabel.setForeground(Color.DARK_GRAY);
							field[i][j].add(field[i][j].vLabel);
						}

						if (_i == 1 && _j == 1) {
							field[i][j].letter = new JLabel(" ", JLabel.CENTER);
							field[i][j].letter.setForeground(Color.DARK_GRAY);
							field[i][j].letter.setFont(new Font("Calibri",
									Font.BOLD, 16));
							field[i][j].add(field[i][j].letter);
						}

						if (_i == 2 && _j == 0) {
							field[i][j].hLabel = new JLabel(" ", JLabel.LEFT);
							field[i][j].hLabel.setFont(new Font("Calibri",
									Font.PLAIN, 10));
							field[i][j].hLabel.setForeground(Color.DARK_GRAY);
							field[i][j].add(field[i][j].hLabel);
						}

					}
				}

			}
		}
		cwframe.validate();
		cwframe.repaint();
	}

	/**
	 * Step1.
	 * Disable table squares and paints them to black
	 */
	private void step1() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						((CWJPanel) evt.getSource()).setBackground(Color.black);
						((CWJPanel) evt.getSource()).disabled = true;
					}
				});
			}
		}
	}

	/**
	 * Draws vars names at table.
	 */
	private void step3() {
		int i = 0;
		for (Variable cw : vars) {
			i++;
			if (cw.type == 'H') {
				field[cw.start.y][cw.start.x].hLabel.setText(" "+i);
				cw.setName(i);
			} else if (cw.type == 'V') {
				field[cw.start.y][cw.start.x].vLabel.setText(i+" ");
				cw.setName(i);
			}
		}

		cwframe.validate();
		cwframe.repaint();

	}

	/**
	 * Step2.
	 * Scan horizontal and then vertical vars from GUI table
	 */
	private void step2() {
		int num = 1;
		Variable cw = new Variable();

		// Scan horizontal VARS
		for (int i = 0; i < rows; i++) {
			boolean started = false;
			for (int j = 0; j < rows; j++) {

				if (!field[i][j].disabled && started == false) {
					started = true;
					num = 1;
					cw.start.x = j;
					cw.start.y = i;

				}
				if (started == true
						&& ((j == rows - 1) || field[i][j + 1].disabled)) {

					cw.end.x = j;
					cw.end.y = i;
					cw.type = 'H';
					cw.setLenght(num);
					vars.add(cw);
					started = false;
					cw = new Variable();
				}

				num++;
			}
		}

		// Scan vertical VARS
		for (int j = 0; j < rows; j++) {
			boolean started = false;
			for (int i = 0; i < rows; i++) {

				if (!field[i][j].disabled && started == false) {
					started = true;
					num = 1;
					cw.start.x = j;
					cw.start.y = i;

				}
				if (started == true
						&& ((i == rows - 1) || field[i + 1][j].disabled)) {

					cw.end.x = j;
					cw.end.y = i;
					cw.type = 'V';
					cw.setLenght(num);

					vars.add(cw);

					started = false;
					cw = new Variable();
				}
				num++;
			}
		}

		@SuppressWarnings("unused")
		int test = 0;
	}

	/**
	 * Scan domains from file and loads them.
	 *
	 * @param path the path
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void scanDomains(String path) throws FileNotFoundException,
			IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				domains.add(line);
				// System.out.println(line);
			}
		}
	}



	/**
	 * Draw assignment GUI table.
	 *
	 * @param asm the asm
	 */
	public static void drawAssignment(Assignment asm) {
		drawVars(asm.complete_vars);
	}
	
	
	/**
	 * Draw vars on GUI table.
	 *
	 * @param list the list
	 */
	public static void drawVars(LinkedList<Variable> list) {
		// Clear table
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j].letter.setText("");
			}
		}

		// Draw table
		for (Variable cw : list) drawVar(cw);
	}
	
	
	/**
	 * Draw var on GUI table.
	 *
	 * @param cw the cw
	 */
	public static void drawVar(Variable cw) {
		if (cw.type == 'H') {

			for (int j = cw.start.x, ind = 0; j <= cw.end.x; j++, ind++) {
				CrosswordGUI.field[cw.start.y][j].letter.setText(""
						+ Character.toLowerCase(cw.getValue().charAt(ind)));
				CrosswordGUI.field[cw.start.y][j].letter.validate();
				CrosswordGUI.field[cw.start.y][j].letter.repaint();
			}

		} else {
			for (int i = cw.start.y, ind = 0; i <= cw.end.y; i++, ind++) {
				CrosswordGUI.field[i][cw.start.x].letter.setText(""
						+ Character.toLowerCase(cw.getValue().charAt(ind)));
				CrosswordGUI.field[i][cw.start.x].letter.validate();
				CrosswordGUI.field[i][cw.start.x].letter.repaint();
			}

		}
	}
	
	
}






