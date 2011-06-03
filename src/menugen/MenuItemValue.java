package menugen;

import java.io.Serializable;

public class MenuItemValue extends MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int type;
	public String caption;
	
	public MenuItemValue(String caption) {
		super(caption, MenuItem.TYPE_VALUE);
	}
}
