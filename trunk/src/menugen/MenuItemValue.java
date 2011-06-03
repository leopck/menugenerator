package menugen;

public class MenuItemValue extends MenuItem {

	public int type;
	public String caption;
	
	public MenuItemValue(String caption) {
		super(caption, MenuItem.TYPE_VALUE);
	}
}
