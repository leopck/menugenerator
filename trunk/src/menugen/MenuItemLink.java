package menugen;

import java.io.Serializable;

public class MenuItemLink extends MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public MenuBlock link;
	
	public MenuItemLink(String caption, MenuBlock link) {
		super(caption, MenuItem.TYPE_LINK);
		
		this.link = link;
	}

	
}
