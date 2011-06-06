package menugen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	String heading;
	int space_left = 3;
	int space_right = 3;
	int space_top = 3;
	int space_bottom = 3;
	
	public SettingsPanel() {
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr.create();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(space_left, space_top, getWidth()-space_left-space_right, getHeight()-space_top-space_bottom, 15, 15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(space_left, space_top, getWidth()-space_left-space_right, getHeight()-space_top-space_bottom, 15, 15);
		
		g.setColor(Color.WHITE);
		Font font = g.getFont().deriveFont(Font.BOLD, 12);
		g.setFont(font);
		g.drawString(heading, space_left + 7, space_top + 17);

		g.dispose();
	}

}
