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

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class MenuBlock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static final int WIDTH = 160;
	static final int HEADER_HEIGHT = 23;
	
	public int x, y;
	public String header;
	public String name;
	public ArrayList<MenuItem> items;
	//public boolean selected = false; 
	
	public MenuBlock() {
		items = new ArrayList<MenuItem>();
		x = 60;
		y = 60;
		header = "default";
	}
	
	public void setCaption(String cap) {
		this.header = cap;
		MainWindow.mw.mp.repaint();
	}

	public int getHeight() {
		return (HEADER_HEIGHT + 3 + items.size() * MenuItem.HEIGHT);
	}
	
	public boolean isInside(Point p) {
		if (p.getX() > x && p.getX() < (x + WIDTH) &&
				p.getY() > y && p.getY() < (y + HEADER_HEIGHT + 3 + items.size() * MenuItem.HEIGHT)) 
		{
			return true;
		}
		else {
			return false;
		}
	}

	public MenuItem hitItem(int item_x, int item_y) {
		if (item_x > (x + 3) && item_y > (y + HEADER_HEIGHT) && ((item_y - (y + HEADER_HEIGHT)) / MenuItem.HEIGHT) < items.size()) {
			return items.get((item_y - (y + HEADER_HEIGHT)) / MenuItem.HEIGHT);
		}
		return null;
	}
}
