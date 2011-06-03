package menugen;

public class MenuItemLink extends MenuItem {

	public MenuBlock link;
	
	public MenuItemLink(String caption, MenuBlock link) {
		super(caption, MenuItem.TYPE_LINK);
		
		this.link = link;
	}

	
}
