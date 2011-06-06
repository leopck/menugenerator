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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SettingsMenuBlock extends SettingsPanel implements ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;

	JTextField blockCaptionText;
	JTextField blockNameText;
	Box vBox;
	
	MenuBlock activeBlock = null;
	
	public SettingsMenuBlock() {
		super();
		heading = "Block settings";
		
		blockCaptionText = new JTextField(14);
		blockCaptionText.getDocument().addDocumentListener(this);
		
		blockNameText = new JTextField(14);
		
		JLabel textLabel = new JLabel("Text:");
		textLabel.setForeground(Color.WHITE);
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setForeground(Color.WHITE);

		Box captionBox = Box.createHorizontalBox();
		captionBox.add(textLabel);
		captionBox.add(Box.createRigidArea(new Dimension(8,1)));
		captionBox.add(blockCaptionText);
		
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(Box.createRigidArea(new Dimension(8,1)));
		nameBox.add(blockNameText);
		
		vBox = Box.createVerticalBox();
		vBox.add(captionBox);
		vBox.add(Box.createRigidArea(new Dimension(1,5)));
		vBox.add(nameBox);
		//vBox.setBorder(BorderFactory.createLineBorder(Color.red));

		add(vBox);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void setVisible(boolean hide_enabled) {
		vBox.setVisible(hide_enabled);
	}
	
	public void setActiveBlock(MenuBlock selectedBlock) {
		if (selectedBlock != null) {
			activeBlock = selectedBlock;
			blockCaptionText.setText(activeBlock.header);
			blockNameText.setText(activeBlock.name);
			setVisible(true);
		}
		else {
			setVisible(false);
		}
	}

	public void blockCaptionTextChanged() {
		if (activeBlock != null) {
			activeBlock.setCaption(blockCaptionText.getText());
		}
		//System.out.println("text change!");
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		blockCaptionTextChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		blockCaptionTextChanged();
	}
}
