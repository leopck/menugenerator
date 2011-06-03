package menugen;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	
	
	public MainWindow() {
		setSize(900,700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		//setLocation(900, 300);
		setLayout(new BorderLayout());
		
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		MenuBlock.blocks.add(new MenuBlock());
		
		MenuBlock.blocks.get(0).header = "Main";
		MenuBlock.blocks.get(0).x = 20;
		MenuBlock.blocks.get(0).y = 20;
		MenuBlock.blocks.get(0).items.add(new MenuItemText("item1"));
		MenuBlock.blocks.get(0).items.add(new MenuItemLink("link1", MenuBlock.blocks.get(1)));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("this is nr2"));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("lalala"));
		MenuBlock.blocks.get(0).items.add(new MenuItemText("Reboot"));
		
		MenuBlock.blocks.get(1).header = "Menu2";
		MenuBlock.blocks.get(1).x = 220;
		MenuBlock.blocks.get(1).y = 130;
		MenuBlock.blocks.get(1).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link1", MenuBlock.blocks.get(2)));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link2", MenuBlock.blocks.get(3)));
		MenuBlock.blocks.get(1).items.add(new MenuItemLink("link3", MenuBlock.blocks.get(4)));
		MenuBlock.blocks.get(1).items.add(new MenuItemText("this is nr2"));
		
		MenuBlock.blocks.get(2).header = "Menu3";
		MenuBlock.blocks.get(2).x = 420;
		MenuBlock.blocks.get(2).y = 80;
		MenuBlock.blocks.get(2).items.add(new MenuItemText("hmm"));
		
		MenuBlock.blocks.get(3).header = "Menu4";
		MenuBlock.blocks.get(3).x = 420;
		MenuBlock.blocks.get(3).y = 200;
		MenuBlock.blocks.get(3).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(3).items.add(new MenuItemText("ok"));
		
		MenuBlock.blocks.get(4).header = "Menu5";
		MenuBlock.blocks.get(4).x = 420;
		MenuBlock.blocks.get(4).y = 320;
		MenuBlock.blocks.get(4).items.add(new MenuItemText("hmm"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item2"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item3"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item4"));
		MenuBlock.blocks.get(4).items.add(new MenuItemText("item5"));

		MainPanel mp = new MainPanel();

		add(mp, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainWindow();
	}
}
