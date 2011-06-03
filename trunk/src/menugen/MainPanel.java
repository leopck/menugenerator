package menugen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class MainPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static final int ITEM_H = 30;
	
	boolean canDrag = false;
	MenuBlock selectedBlock = null;
	MenuItem selectedItem = null;
	Point downPoint = new Point();
	PopupMenu popup;
	
	public MainPanel() {
		setBackground(Color.BLACK);
		
		popup = new PopupMenu(this);

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < MenuBlock.blocks.size(); i++) {
			MenuBlock b = MenuBlock.blocks.get(i);
			
			RoundRectangle2D r = new RoundRectangle2D.Double(b.x, b.y, 100, 26+b.items.size()*ITEM_H, 8, 8);			
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(r);

			g2d.setColor((selectedBlock == b) ? Color.RED : new Color(100,255,130));
			g2d.draw(r);
			g2d.drawString(b.header, b.x+6, b.y+14);

			int j;
			for (j = 0; j < b.items.size(); j++) {
				g2d.setColor((selectedItem == b.items.get(j)) ? Color.GRAY : Color.DARK_GRAY);
				g2d.fillRect(b.x+2, b.y + 23 + j*ITEM_H, 96, ITEM_H-2);
				g2d.setColor((selectedItem == b.items.get(j)) ? Color.RED : Color.BLACK);
				g2d.drawRect(b.x+2, b.y + 23 + j*ITEM_H, 96, ITEM_H-2);
				g2d.setColor(Color.WHITE);
				g2d.drawString(b.items.get(j).caption, b.x+6, b.y+38+j*30);
				
				if (b.items.get(j) instanceof MenuItemLink) {

					g2d.fillOval(b.x+97, b.y+32+j*ITEM_H, 6, 6);
					
					if (((MenuItemLink)b.items.get(j)).link != null) {
						int dist_x = b.x+100 - ((MenuItemLink)b.items.get(j)).link.x;
						int dist_y = b.y+35+j*ITEM_H - (((MenuItemLink)b.items.get(j)).link.y+((MenuItemLink)b.items.get(j)).link.getHeight()/2);
								
						int ctrl_x = (int) Math.abs((dist_x / 4)) + 30;
						int ctrl_y = (int) (dist_y / 8);
						
						//g2d.drawOval(b.x+100+ctrl_x-4, b.y+35+j*20 - ctrl_y-4, 8, 8);
						
						CubicCurve2D c = new CubicCurve2D.Double();
						c.setCurve(
								b.x+100, b.y + 35 + j*ITEM_H, 
								b.x+100+ctrl_x, b.y + 35 + j*ITEM_H - ctrl_y, 
								((MenuItemLink)b.items.get(j)).link.x-ctrl_x, ((MenuItemLink)b.items.get(j)).link.y+((MenuItemLink)b.items.get(j)).link.getHeight()/2 + ctrl_y, 
								((MenuItemLink)b.items.get(j)).link.x, ((MenuItemLink)b.items.get(j)).link.y+((MenuItemLink)b.items.get(j)).link.getHeight()/2);
						g2d.draw(c);						
					}
				}
			}
		}
		
		g2d.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (canDrag) {
			selectedBlock.x = (int) (e.getX() - downPoint.getX());
			selectedBlock.y = (int) (e.getY() - downPoint.getY());
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
			this.repaint();
		}
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		selectedBlock = null;
		selectedItem = null;
		this.repaint();

		for (int i = 0; i < MenuBlock.blocks.size(); i++) {
			MenuBlock b = MenuBlock.blocks.get(i);
						
			if (b.isInside(e.getPoint())) 
			{
				canDrag = true;
				selectedBlock = b;
				selectedItem = selectedBlock.hitItem((int)e.getPoint().getX(), (int)e.getPoint().getY());
				downPoint = new Point(e.getX() - b.x, e.getY() - b.y);
				
				this.repaint();
				System.out.println("Selected " + b.header);
			}
		}
		
        if (e.isPopupTrigger()) {
            popup.showMenu(e.getComponent(),
                       e.getX(), e.getY());
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		canDrag = false;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
        if (e.isPopupTrigger()) {
            popup.showMenu(e.getComponent(),
                       e.getX(), e.getY());
        }
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("addblock")) {
			MenuBlock.blocks.add(new MenuBlock());
			MenuBlock.blocks.get(MenuBlock.blocks.size()-1).x = (int) this.getMousePosition().getX();
			MenuBlock.blocks.get(MenuBlock.blocks.size()-1).y = (int) this.getMousePosition().getY();
			repaint();
		}
		else if (e.getActionCommand().equals("removeblock")) {
			
			if (selectedBlock != null) {
				
				//Remove all links to this block
				for (int i = 0; i < MenuBlock.blocks.size(); i++) {
					MenuBlock b = MenuBlock.blocks.get(i);
					
					for (int j = 0; j < b.items.size(); j++) {
						if (b.items.get(j).type == MenuItem.TYPE_LINK &&
								((MenuItemLink)b.items.get(j)).link != null) {
							
							if (((MenuItemLink)b.items.get(j)).link == selectedBlock) {
								((MenuItemLink)b.items.get(j)).link = null;
							}
						}
					}
				}
				
				selectedItem = null;
				MenuBlock.blocks.remove(selectedBlock);
				selectedBlock = null;
				repaint();
			}
		}
		else if (e.getActionCommand().equals("removeitem")) {
			selectedBlock.items.remove(selectedItem);
			selectedItem = null;
			repaint();
		}
		else if (e.getActionCommand().equals("addvalueitem")) {
			selectedBlock.items.add(new MenuItemValue("default"));
			repaint();
		}
	}
	
	class PopupMenu extends JPopupMenu {

		private static final long serialVersionUID = 1L;
		
		JMenuItem item_addblock;
		JMenuItem item_addvalueitem;
		JMenuItem item_addlinkitem;
		JMenuItem item_removeblock;
		JMenuItem item_removeitem;
		
		public PopupMenu(MainPanel mp) {
			item_addvalueitem = new JMenuItem("Add value item");
			item_addvalueitem.setActionCommand("addvalueitem");
			item_addvalueitem.addActionListener(mp);
			add(item_addvalueitem);

			item_addlinkitem = new JMenuItem("Add link item");
			item_addlinkitem.setActionCommand("addlinkitem");
			item_addlinkitem.addActionListener(mp);
			add(item_addlinkitem);

			item_removeitem = new JMenuItem("Remove item");
			item_removeitem.setActionCommand("removeitem");
			item_removeitem.addActionListener(mp);
			add(item_removeitem);

			addSeparator();
			
			item_addblock = new JMenuItem("Add block");
			item_addblock.setActionCommand("addblock");
			item_addblock.addActionListener(mp);
			add(item_addblock);

			item_removeblock = new JMenuItem("Remove block");
			item_removeblock.setActionCommand("removeblock");
			item_removeblock.addActionListener(mp);
			add(item_removeblock);
		}
		
		public void showMenu(Component invoker, int x, int y) {
			item_addblock.setVisible(false);
			item_addvalueitem.setVisible(false);
			item_addlinkitem.setVisible(false);
			item_removeitem.setVisible(false);
			item_removeblock.setVisible(false);
			
			if (selectedBlock != null) {
				if (selectedItem != null) {
					item_removeitem.setVisible(true);
				}
				item_addvalueitem.setVisible(true);
				item_addlinkitem.setVisible(true);
				item_removeblock.setVisible(true);
			}
			else {
				item_addblock.setVisible(true);
			}
			this.show(invoker, x, y);
		}
	}
}
