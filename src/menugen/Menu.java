package menugen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	static Menu active = new Menu();
	static File file = null; //The open file
	
	public ArrayList<MenuBlock> blocks = new ArrayList<MenuBlock>();
	public MenuBlock selectedBlock = null;
	public MenuBlock startBlock = null;
	public MenuItem selectedItem = null;
	public double zoom = 1; //Zoom factor when drawn
	
	public Menu() {
		blocks.add(new MenuBlock());
		startBlock = blocks.get(0);
	}
	
	/*
	 * Save to file
	 */
	public static boolean save(boolean saveAs) {
		
		if (file == null || saveAs) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);
			
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return false; //Save not complete
			}
			file = fc.getSelectedFile();
		}
		
		try {
			FileOutputStream fstream = new FileOutputStream(file);
			ObjectOutputStream ostream = new ObjectOutputStream (fstream);
			ostream.writeObject(active);
			MainWindow.mw.updateTitle();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * Read from file
	 */
	public static void open() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		file = fc.getSelectedFile();
		Object obj = null;
		
		try {
			FileInputStream f_in = new FileInputStream(file);
			ObjectInputStream obj_in = new ObjectInputStream (f_in);
			obj = obj_in.readObject();
			MainWindow.mw.updateTitle();
			
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (obj instanceof Menu)
		{
			active = (Menu) obj;
		}
		else {
			JOptionPane.showMessageDialog(null, "Wrong filetype!");
		}
	}

	public static void create_new() {
		//Show save dialog if a document is open
		if (active != null && active.blocks.size() > 0) {
			int c = JOptionPane.showConfirmDialog(null, "Save changes?");
			
			if (c == JOptionPane.YES_OPTION) {
				if (!Menu.save(false))
					return;
			}
			else if (c == JOptionPane.NO_OPTION) {
				//Do nothing
			}
			else if (c == JOptionPane.CANCEL_OPTION) {
				return;
			}
			
		}

		file = null;
		active = new Menu();
	}

	public static Menu getInstance() {
		return active;
	}
}
