package menugen;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class MenuBlock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int x, y;
	public String header;
	public ArrayList<MenuItem> items;
	//public boolean selected = false; 
	
	public MenuBlock() {
		items = new ArrayList<MenuItem>();
		x = 60;
		y = 60;
		header = "default";
	}
	
	public int getHeight() {
		return (26 + items.size() * MainPanel.ITEM_H);
	}
	
	public boolean isInside(Point p) {
		if (p.getX() > x && p.getX() < (x + 100) &&
				p.getY() > y && p.getY() < (y + 26 + items.size() * MainPanel.ITEM_H)) 
		{
			return true;
		}
		else {
			return false;
		}
	}

	public MenuItem hitItem(int item_x, int item_y) {
		if (item_x > (x + 3) && item_y > (y + 26)) {
			System.out.println(item_y - (y + 26));
			return items.get((item_y - (y + 26)) / MainPanel.ITEM_H);
			
			
		}
		return null;
	}
}
