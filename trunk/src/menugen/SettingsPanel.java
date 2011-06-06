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
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	String heading;
	int space_left = 3;
	int space_right = 3;
	int space_top = 3;
	int space_bottom = 3;
	
	public SettingsPanel() {
		setBackground(new Color(40,40,40));
		setForeground(Color.WHITE);
		setBorder(new EmptyBorder(33, 5, 5, 5));
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr.create();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.DARK_GRAY);
		
		GradientPaint gp = new GradientPaint(
				0, 0, new Color(50,50,50), 
				0, getHeight(), new Color(80,80,80));

		g.setPaint(gp);
		
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
