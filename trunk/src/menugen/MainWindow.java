/*
 *  MenuGenerator, a free menu generator for microcontrollers
 *  Copyright (C) 2011  Erik Abrahamsson
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package menugen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	
	public MainPanel mp;
	public SettingsMenu setMenuPanel;
	public SettingsMenuItem setItemPanel;
	public SettingsMenuBlock setBlockPanel;
	public Emulator emu;
	
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
				
		
		//Create main panel for graphics
		mp = new MainPanel();

		//Create panel for configuration
		JPanel lpanel = new JPanel();
		lpanel.setLayout(new BoxLayout(lpanel, BoxLayout.Y_AXIS));
		lpanel.setBackground(Color.BLACK);
		
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
		
		/*
		 * Create "help" menu
		 */
		JMenuItem menuHelpHelp = new JMenuItem("Online help...");
		menuHelpHelp.setMnemonic(KeyEvent.VK_H);
		menuHelpHelp.setActionCommand("menuHelpHelp");
		menuHelpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuHelpHelp.addActionListener(this);
		
		JMenuItem menuHelpAbout = new JMenuItem("About...");
		menuHelpAbout.setMnemonic(KeyEvent.VK_A);
		menuHelpAbout.setActionCommand("menuHelpAbout");
		menuHelpAbout.addActionListener(this);
		
		JMenu menuHelp = new JMenu("Help");
		menuHelp.add(menuHelpHelp);
		menuHelp.add(menuHelpAbout);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		
		/*
		 * Create menu bar and add menus
		 */
		JMenuBar menubar = new JMenuBar();
		menubar.add(menuFile);
		menubar.add(menuGenerate);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(menuHelp);
		
		/*
		 * Add emulator
		 */
		emu = new Emulator();
		lpanel.add(emu);

		/*
		 * Add menu config panel
		 */
		setMenuPanel = new SettingsMenu();
		lpanel.add(setMenuPanel);
		
		/*
		 * Add block config panel
		 */
		setBlockPanel = new SettingsMenuBlock();
		lpanel.add(setBlockPanel);
		//lpanel.add(Box.createVerticalGlue());
		
		/*
		 * Add item config panel
		 */
		setItemPanel = new SettingsMenuItem();
		lpanel.add(setItemPanel);
		//lpanel.add(Box.createVerticalGlue());
		
		/*
		 * Add panels
		 */
		add(lpanel, BorderLayout.WEST);
		add(menubar, BorderLayout.NORTH);
		add(mp, BorderLayout.CENTER);
		
		/*
		 * Add listeners
		 */
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
		else if (e.getActionCommand().equals("menuHelpHelp")) {
			if( !java.awt.Desktop.isDesktopSupported() ) {
				return;
			}

			Desktop desktop = java.awt.Desktop.getDesktop();

			if( !desktop.isSupported( Desktop.Action.BROWSE ) ) {
				return;
			}

			try {
				java.net.URI uri = new java.net.URI("http://code.google.com/p/menugenerator/wiki/Help");
				desktop.browse( uri );
			}
			catch ( Exception ex ) {
				System.err.println( ex.getMessage() );
			}
		}
		else if (e.getActionCommand().equals("menuHelpAbout")) {
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
