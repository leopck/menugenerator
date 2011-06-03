package menugen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class MainWindow extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static final String VERSION = "v0.3";
	
	public static MainWindow mw;
	
	MainPanel mp;
	
	public MainWindow() {
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Use the native L&F
        	UIManager.put("TabbedPane.contentAreaColor ",ColorUIResource.GREEN);

        } catch (Exception cnf) {
        	System.out.println("UIManager error!");
        }
		setSize(1000,700); //Set window size
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Close application on window close
		setLocationRelativeTo(null); //Center window when opened
		updateTitle();
		setLayout(new BorderLayout());
		
		/*
		Menu.getInstance().blocks.add(new MenuBlock());
		Menu.getInstance().blocks.add(new MenuBlock());
		Menu.getInstance().blocks.add(new MenuBlock());
		Menu.getInstance().blocks.add(new MenuBlock());
		Menu.getInstance().blocks.add(new MenuBlock());
		
		Menu.getInstance().blocks.get(0).header = "Main";
		Menu.getInstance().blocks.get(0).x = 20;
		Menu.getInstance().blocks.get(0).y = 20;
		Menu.getInstance().blocks.get(0).items.add(new MenuItemText("item1"));
		Menu.getInstance().blocks.get(0).items.add(new MenuItemLink("link1", Menu.getInstance().blocks.get(1)));
		Menu.getInstance().blocks.get(0).items.add(new MenuItemText("this is nr2"));
		Menu.getInstance().blocks.get(0).items.add(new MenuItemText("lalala"));
		Menu.getInstance().blocks.get(0).items.add(new MenuItemText("Reboot"));
		
		Menu.getInstance().blocks.get(1).header = "Menu2";
		Menu.getInstance().blocks.get(1).x = 220;
		Menu.getInstance().blocks.get(1).y = 130;
		Menu.getInstance().blocks.get(1).items.add(new MenuItemText("hmm"));
		Menu.getInstance().blocks.get(1).items.add(new MenuItemLink("link1", Menu.getInstance().blocks.get(2)));
		Menu.getInstance().blocks.get(1).items.add(new MenuItemLink("link2", Menu.getInstance().blocks.get(3)));
		Menu.getInstance().blocks.get(1).items.add(new MenuItemLink("link3", Menu.getInstance().blocks.get(4)));
		Menu.getInstance().blocks.get(1).items.add(new MenuItemText("this is nr2"));
		
		Menu.getInstance().blocks.get(2).header = "Menu3";
		Menu.getInstance().blocks.get(2).x = 420;
		Menu.getInstance().blocks.get(2).y = 80;
		Menu.getInstance().blocks.get(2).items.add(new MenuItemText("hmm"));
		
		Menu.getInstance().blocks.get(3).header = "Menu4";
		Menu.getInstance().blocks.get(3).x = 420;
		Menu.getInstance().blocks.get(3).y = 200;
		Menu.getInstance().blocks.get(3).items.add(new MenuItemText("hmm"));
		Menu.getInstance().blocks.get(3).items.add(new MenuItemText("ok"));
		*/
		
		//Create main panel for graphics
		mp = new MainPanel();

		//Create panel for configuration
		JPanel lpanel = new JPanel();
		//lpanel.setPreferredSize(new Dimension(250,200));
		lpanel.setBackground(Color.DARK_GRAY);
		
		/*
		 * Create "File" menu and items
		 */
		JMenuItem menuFileNew = new JMenuItem("New");
		menuFileNew.setMnemonic(KeyEvent.VK_N);
		menuFileNew.setActionCommand("menuFileNew");
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuFileNew.addActionListener(this);

		JMenuItem menuFileOpen = new JMenuItem("Open...");
		menuFileOpen.setMnemonic(KeyEvent.VK_O);
		menuFileOpen.setActionCommand("menuFileOpen");
		menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuFileOpen.addActionListener(this);

		JMenuItem menuFileSave = new JMenuItem("Save");
		menuFileSave.setMnemonic(KeyEvent.VK_S);
		menuFileSave.setActionCommand("menuFileSave");
		menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuFileSave.addActionListener(this);
		
		JMenuItem menuFileSaveAs = new JMenuItem("Save As...");
		menuFileSaveAs.setMnemonic(KeyEvent.VK_A);
		menuFileSaveAs.setActionCommand("menuFileSaveAs");
		menuFileSaveAs.addActionListener(this);
		
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFileExit.setMnemonic(KeyEvent.VK_X);
		menuFileExit.setActionCommand("menuFileExit");
		menuFileExit.addActionListener(this);

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuFile.add(menuFileNew);
		menuFile.add(menuFileOpen);
		menuFile.add(menuFileSave);
		menuFile.add(menuFileSaveAs);
		menuFile.add(new JSeparator());
		menuFile.add(menuFileExit);

		/*
		 * Create "Generate" menu and items
		 */
		JMenuItem menuGenerateCode = new JMenuItem("Generate code");
		menuGenerateCode.setMnemonic(KeyEvent.VK_G);
		menuGenerateCode.setActionCommand("menuGenerateCode");
		menuGenerateCode.addActionListener(this);
		
		JMenu menuGenerate = new JMenu("Generate");
		menuGenerate.add(menuGenerateCode);
		menuGenerate.setMnemonic(KeyEvent.VK_G);
		
		//Create menu bar
		JMenuBar menubar = new JMenuBar();
		menubar.add(menuFile);
		menubar.add(menuGenerate);
		
		Emulator emu = new Emulator();
		lpanel.add(emu);
		
		//Add panels
		add(lpanel, BorderLayout.WEST);
		add(menubar, BorderLayout.NORTH);
		add(mp, BorderLayout.CENTER);
		
		//Add listeners
		addWindowListener(this);
		
		//Make window visible
		setVisible(true);
	}
	
	public void updateTitle() {
		String filename = "";

		if (Menu.file != null) {
			filename = Menu.file.getName();			
		}
		
		setTitle("MenuGenerator " + VERSION + " - " + filename);
	}
	
	/*
	 * Quits application
	 */
	public void quit() {
		if (Menu.active.blocks.size() == 0)
			System.exit(0);
		
		int c = JOptionPane.showConfirmDialog(this, "Save changes?");
		
		if (c == JOptionPane.YES_OPTION) {
			if (Menu.save(false))
				System.exit(0);
		}
		else if (c == JOptionPane.NO_OPTION) {
			//Just exit
			System.exit(0);
		}
		else if (c == JOptionPane.CANCEL_OPTION) {
			//Do nothing
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mw = new MainWindow();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("menuFileExit")) {
			quit();
		}
		else if (e.getActionCommand().equals("menuFileNew")) {
			Menu.create_new();
			mp.repaint();
		}
		else if (e.getActionCommand().equals("menuFileOpen")) {
			Menu.open();
			mp.repaint();
		}
		else if (e.getActionCommand().equals("menuFileSave")) {
			Menu.save(false);
		}
		else if (e.getActionCommand().equals("menuFileSaveAs")) {
			Menu.save(true);
		}
		else if (e.getActionCommand().equals("menuGenerateCode")) {
			JOptionPane.showMessageDialog(this, "Function not implemented yet!");
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		quit();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}
