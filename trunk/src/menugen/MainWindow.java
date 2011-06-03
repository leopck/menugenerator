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
import javax.swing.plaf.IconUIResource;

public class MainWindow extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static final String VERSION = "v0.1";
	
	
	public MainWindow() {
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Use the native L&F
        	UIManager.put("TabbedPane.contentAreaColor ",ColorUIResource.GREEN);

        } catch (Exception cnf) {
        	System.out.println("UIManager error!");
        }
		setSize(900,700); //Set window size
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Close application on window close
		setLocationRelativeTo(null); //Center window when opened
		setTitle("MenuGenerator " + VERSION);
		setLayout(new BorderLayout());
		
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		
		MenuBlock.blocks.get(0).header = "Main";
		MenuBlock.blocks.get(0).x = 20;
		MenuBlock.blocks.get(0).y = 20;
		MenuBlock.blocks.get(0).items.add(new MenuItemText("item1"));
		MenuBlock.blocks.get(0).items.add(new MenuItemLink("link1", MenuBlock.blocks.get(1)));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("this is nr2"));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("lalala"));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("Reboot"));
		
		MenuBlock.blocks.get(1).header = "Menu2";
		MenuBlock.blocks.get(1).x = 220;
		MenuBlock.blocks.get(1).y = 130;
		MenuBlock.blocks.get(1).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link1", MenuBlock.blocks.get(2)));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link2", MenuBlock.blocks.get(3)));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link3", MenuBlock.blocks.get(4)));
		MenuBlock.blocks.get(1).items.add(new MenuItemText("this is nr2"));
		
		MenuBlock.blocks.get(2).header = "Menu3";
		MenuBlock.blocks.get(2).x = 420;
		MenuBlock.blocks.get(2).y = 80;
		MenuBlock.blocks.get(2).items.add(new MenuItemText("hmm"));
		
		MenuBlock.blocks.get(3).header = "Menu4";
		MenuBlock.blocks.get(3).x = 420;
		MenuBlock.blocks.get(3).y = 200;
		MenuBlock.blocks.get(3).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(3).items.add(new MenuItemText("ok"));
		
		MenuBlock.blocks.get(4).header = "Menu5";
		MenuBlock.blocks.get(4).x = 420;
		MenuBlock.blocks.get(4).y = 320;
		MenuBlock.blocks.get(4).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item2"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item3"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item4"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item5"));

		//Create main panel for graphics
		MainPanel mp = new MainPanel();

		//Create panel for configuration
		JPanel lpanel = new JPanel();
		lpanel.setPreferredSize(new Dimension(200,200));
		lpanel.setBackground(Color.DARK_GRAY);
		
		/*
		 * Create "File" menu and items
		 */
		JMenuItem menuFileNew = new JMenuItem("New");
		menuFileNew.setMnemonic(KeyEvent.VK_N);
		menuFileNew.setActionCommand("menuFileNew");
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		JMenuItem menuFileOpen = new JMenuItem("Open");
		menuFileOpen.setMnemonic(KeyEvent.VK_O);
		menuFileOpen.setActionCommand("menuFileOpen");
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

		JMenuItem menuFileSave = new JMenuItem("Save");
		menuFileSave.setMnemonic(KeyEvent.VK_S);
		menuFileSave.setActionCommand("menuFileSave");
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFileExit.setMnemonic(KeyEvent.VK_X);
		menuFileExit.setActionCommand("menuFileExit");

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuFile.add(menuFileNew);
		menuFile.add(menuFileOpen);
		menuFile.add(menuFileSave);
		menuFile.add(new JSeparator());
		menuFile.add(menuFileExit);

		//Create menu bar
		JMenuBar menubar = new JMenuBar();
		menubar.add(menuFile);
		
		//Add panels
		add(lpanel, BorderLayout.WEST);
		add(menubar, BorderLayout.NORTH);
		add(mp, BorderLayout.CENTER);
		
		//Add listeners
		addWindowListener(this);
		
		//Make window visible
		setVisible(true);
	}
	
	
	/*
	 * Quits application
	 */
	public void quit() {
		int c = JOptionPane.showConfirmDialog(this, "Save changes?");
		
		if (c == JOptionPane.YES_OPTION) {
			//Save and exit
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
		new MainWindow();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("menuFileExit")) {
			quit();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		quit();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
