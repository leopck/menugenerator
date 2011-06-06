package menugen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SettingsMenuItem extends SettingsPanel implements ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;

	JTextField itemCaptionText;
	JTextField itemNameText;
	JComboBox itemTypeCombo;
	Box vBox;
	
	MenuItem activeItem = null;
	
	public SettingsMenuItem() {
		super();
		heading = "Item settings";
		
		itemCaptionText = new JTextField(14);
		itemCaptionText.getDocument().addDocumentListener(this);
		
		itemNameText = new JTextField(14);
		
		String[] options = {"Link", "Variable value", "Text only", "Run function"};
		itemTypeCombo = new JComboBox(options);
		itemTypeCombo.addActionListener(this);
		itemTypeCombo.setActionCommand("itemTypeCombo");

		JLabel textLabel = new JLabel("Text:");
		textLabel.setForeground(Color.WHITE);
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setForeground(Color.WHITE);
		JLabel typeLabel = new JLabel("Type:");
		typeLabel.setForeground(Color.WHITE);


		Box captionBox = Box.createHorizontalBox();
		captionBox.add(textLabel);
		captionBox.add(Box.createRigidArea(new Dimension(8,1)));
		captionBox.add(itemCaptionText);
		
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(Box.createRigidArea(new Dimension(8,1)));
		nameBox.add(itemNameText);
		
		Box typeBox = Box.createHorizontalBox();
		typeBox.add(typeLabel);
		typeBox.add(Box.createRigidArea(new Dimension(8,1)));
		typeBox.add(itemTypeCombo);
		
		vBox = Box.createVerticalBox();
		vBox.add(Box.createRigidArea(new Dimension(1,40)));
		vBox.add(captionBox);
		vBox.add(Box.createRigidArea(new Dimension(1,5)));
		vBox.add(nameBox);
		vBox.add(Box.createRigidArea(new Dimension(1,5)));
		vBox.add(typeBox);
		//vBox.setBorder(BorderFactory.createLineBorder(Color.red));

		add(vBox);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("itemTypeCombo")) {
			//Don't do anything if type is not changed
			if (activeItem == null || activeItem.getType() == itemTypeCombo.getSelectedIndex())
				return;
			
			//Change type of item
			activeItem.setType(itemTypeCombo.getSelectedIndex());
		}
	}

	@Override
	public void setVisible(boolean hide_enabled) {
		vBox.setVisible(hide_enabled);
	}
	
	public void setActiveItem(MenuItem selectedItem) {
		if (selectedItem != null) {
			activeItem = selectedItem;
			itemCaptionText.setText(activeItem.caption);
			itemNameText.setText(activeItem.name);
			itemTypeCombo.setSelectedIndex(activeItem.getType());
			setVisible(true);
		}
		else {
			setVisible(false);
		}
	}

	public void itemCaptionTextChanged() {
		if (activeItem != null) {
			activeItem.setCaption(itemCaptionText.getText());
		}
		//System.out.println("text change!");
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		itemCaptionTextChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		itemCaptionTextChanged();
	}


}
