package menugen;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final static public int TYPE_LINK = 0;
	final static public int TYPE_VALUE = 1;
	final static public int TYPE_TEXT = 2;
	
	public int type;
	public String caption;
	
	public MenuItem(String caption, int type) {
		this.type = type;
		this.caption = caption;
	}
}
