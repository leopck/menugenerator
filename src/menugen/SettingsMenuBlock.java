package menugen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		vBox.add(Box.createRigidArea(new Dimension(1,40)));
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
			System.out.println(activeBlock.header);
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
