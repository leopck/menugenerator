package menugen;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class MenuBlock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static final int WIDTH = 120;
	
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
		return (26 + items.size() * MenuItem.HEIGHT);
	}
	
	public boolean isInside(Point p) {
		if (p.getX() > x && p.getX() < (x + WIDTH) &&
				p.getY() > y && p.getY() < (y + 26 + items.size() * MenuItem.HEIGHT)) 
		{
			return true;
		}
		else {
			return false;
		}
	}

	public MenuItem hitItem(int item_x, int item_y) {
		if (item_x > (x + 3) && item_y > (y + 26)) {
			return items.get((item_y - (y + 26)) / MenuItem.HEIGHT);
			
			
		}
		return null;
	}
}
