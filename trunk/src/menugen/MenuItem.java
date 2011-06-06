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

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final static int HEIGHT = 45;

	final static public int TYPE_LINK = 0;
	final static public int TYPE_VALUE = 1;
	final static public int TYPE_TEXT = 2;
	final static public int TYPE_FUNCTION = 3;
	
	public int type;
	public String caption;
	public String name = "name1";
	
	//Link settings
	public MenuBlock link = null;
	
	//Value settings
	public long minValue = -50; 
	public long maxValue = 50; 
	public long defaultValue = 0;
	public long stepSize = 1;
	
	//Function settings
	public String functionName = "runThis()";
	
	public MenuItem(String caption, int type) {
		this.type = type;
		this.caption = caption;
	}
	
	public void setCaption(String cap) {
		this.caption = cap;
		MainWindow.mw.mp.repaint();
	}

	public void setType(int type) {
		this.type = type;
		MainWindow.mw.mp.repaint();
	}

	public int getType() {
		return type;
	}
}
