package menugen;

import java.io.Serializable;

public class MenuItemText extends MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public MenuItemText(String caption) {
		super(caption, MenuItem.TYPE_TEXT);
	}

}
