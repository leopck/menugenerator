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
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	static Menu active = new Menu();
	static File file = null; //The open file
	
	static String[] menu_types_list = {
			"3 buttons (<-, ->, enter)", 
			"4 buttons (<-, ->, enter, back)"};

	static Integer[] lcd_rows_list = {1,2,4};
	static Integer[] lcd_cols_list = {16,20,40};
	
	public ArrayList<MenuBlock> blocks = new ArrayList<MenuBlock>();
	public MenuBlock selectedBlock = null;
	public MenuBlock startBlock = null;
	public MenuItem selectedItem = null;
	public double zoom = 1; //Zoom factor when drawn
	
	public int lcd_rows_index = 1; //2 rows
	public int lcd_cols_index = 1; //20 cols
	public int menu_type_index = 0; //3 buttons (<-, ->, enter)
	
	public Menu() {
		
		/*
		 * Generate sample data
		 */
		blocks.add(new MenuBlock());
		blocks.add(new MenuBlock());
		blocks.add(new MenuBlock());
		blocks.add(new MenuBlock());
		
		startBlock = blocks.get(0);

		blocks.get(0).header = "Main menu";
		blocks.get(0).x = 40;
		blocks.get(0).y = 70;
		blocks.get(0).items.add(new MenuItem("Settings", MenuItem.TYPE_LINK));
		blocks.get(0).items.get(blocks.get(0).items.size()-1).link = blocks.get(1);
		blocks.get(0).items.add(new MenuItem("Start", MenuItem.TYPE_FUNCTION));
		blocks.get(0).items.get(blocks.get(0).items.size()-1).functionName = "start()";
		blocks.get(0).items.add(new MenuItem("Reset", MenuItem.TYPE_FUNCTION));
		
		blocks.get(1).header = "Settings";
		blocks.get(1).x = 260;
		blocks.get(1).y = 130;
		blocks.get(1).items.add(new MenuItem("Settings 1", MenuItem.TYPE_LINK));
		blocks.get(1).items.get(blocks.get(1).items.size()-1).link = blocks.get(2);
		blocks.get(1).items.add(new MenuItem("Settings 2", MenuItem.TYPE_LINK));
		blocks.get(1).items.get(blocks.get(1).items.size()-1).link = blocks.get(2);
		blocks.get(1).items.add(new MenuItem("Settings 3", MenuItem.TYPE_LINK));
		blocks.get(1).items.get(blocks.get(1).items.size()-1).link = blocks.get(3);
		blocks.get(1).items.add(new MenuItem("Random value", MenuItem.TYPE_VALUE));
		
		blocks.get(2).header = "Menu3";
		blocks.get(2).x = 480;
		blocks.get(2).y = 120;
		blocks.get(2).items.add(new MenuItem("X", MenuItem.TYPE_VALUE));
		
		blocks.get(3).header = "Menu4";
		blocks.get(3).x = 480;
		blocks.get(3).y = 250;
		blocks.get(3).items.add(new MenuItem("Y", MenuItem.TYPE_VALUE));
		blocks.get(3).items.add(new MenuItem("Z", MenuItem.TYPE_VALUE));
}
	
	/*
	 * Save to file
	 */
	public static boolean save(boolean saveAs) {
		
		if (file == null || saveAs) {
			JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(new FileNameExtensionFilter("MenuGenerator file (*.mnu)", "mnu"));
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
		fc.addChoosableFileFilter(new FileNameExtensionFilter("MenuGenerator file (*.mnu)", "mnu"));
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
			MainWindow.mw.setMenuPanel.updateControls();
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
		MainWindow.mw.setMenuPanel.updateControls();
	}

	public static Menu getInstance() {
		return active;
	}

	public int getLCDRows() {
		return lcd_rows_list[lcd_rows_index];
	}
	public int getLCDCols() {
		return lcd_cols_list[lcd_cols_index];
	}
}
