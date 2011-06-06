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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class SettingsMenu extends SettingsPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	Box vBox;
	JComboBox menutypeCombo;
	JComboBox lcdrowsCombo;
	JComboBox lcdcolsCombo;
	
	public SettingsMenu() {
		super();
		heading = "Menu settings";
		
		JLabel typeLabel = new JLabel("Menu type:");
		typeLabel.setForeground(Color.WHITE);
		
		menutypeCombo = new JComboBox(Menu.menu_types_list);
		menutypeCombo.setActionCommand("menutypeCombo");
		menutypeCombo.addActionListener(this);
		
		JLabel sizeLabel = new JLabel("LCD size:");
		sizeLabel.setForeground(Color.WHITE);		
		JLabel xLabel = new JLabel("X");
		xLabel.setForeground(Color.WHITE);
		
		lcdrowsCombo = new JComboBox(Menu.lcd_rows_list);
		lcdrowsCombo.setActionCommand("lcdrowsCombo");
		lcdrowsCombo.addActionListener(this);

		lcdcolsCombo = new JComboBox(Menu.lcd_cols_list);
		lcdcolsCombo.setActionCommand("lcdcolsCombo");
		lcdcolsCombo.addActionListener(this);

		Box typeBox = Box.createHorizontalBox();
		typeBox.add(typeLabel);
		typeBox.add(Box.createRigidArea(new Dimension(8,1)));
		typeBox.add(menutypeCombo);
		
		Box sizeBox = Box.createHorizontalBox();
		sizeBox.add(sizeLabel);
		sizeBox.add(Box.createRigidArea(new Dimension(8,1)));
		sizeBox.add(lcdrowsCombo);
		sizeBox.add(Box.createRigidArea(new Dimension(4,1)));
		sizeBox.add(xLabel);
		sizeBox.add(Box.createRigidArea(new Dimension(4,1)));
		sizeBox.add(lcdcolsCombo);
		
		vBox = Box.createVerticalBox();
		vBox.add(typeBox);
		vBox.add(Box.createRigidArea(new Dimension(1,6)));
		vBox.add(sizeBox);

		add(vBox);
		
		updateControls();
	}
	
	public void updateControls() {
		menutypeCombo.removeActionListener(this);
		lcdcolsCombo.removeActionListener(this);
		lcdrowsCombo.removeActionListener(this);
		
		menutypeCombo.setSelectedIndex(Menu.getInstance().menu_type_index);
		lcdcolsCombo.setSelectedIndex(Menu.getInstance().lcd_cols_index);
		lcdrowsCombo.setSelectedIndex(Menu.getInstance().lcd_rows_index);

		menutypeCombo.addActionListener(this);
		lcdcolsCombo.addActionListener(this);
		lcdrowsCombo.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("menutypeCombo")) {
			Menu.getInstance().menu_type_index = ((JComboBox)e.getSource()).getSelectedIndex();
			MainWindow.mw.emu.repaint();				
		}
		else if (e.getActionCommand().equals("lcdcolsCombo")) {
			Menu.getInstance().lcd_cols_index = ((JComboBox)e.getSource()).getSelectedIndex();
			MainWindow.mw.emu.repaint();				
		}
		else if (e.getActionCommand().equals("lcdrowsCombo")) {
			Menu.getInstance().lcd_rows_index = ((JComboBox)e.getSource()).getSelectedIndex();
			MainWindow.mw.emu.repaint();				
		}
	}
}
