package menugen;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final static public int TYPE_LINK = 0;
	final static public int TYPE_VALUE = 1;
	final static public int TYPE_TEXT = 2;
	final static public int TYPE_FUNCTION = 3;
	
	public int type;
	public String caption;
	public String name = "";
	public MenuBlock link = null;
	
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
