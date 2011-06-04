package menugen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class MainPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;
	public static final int ITEM_H = 30;
	
	boolean canDrag = false;
	Point downPoint = new Point();
	Point origin = new Point();
	PopupMenu popup;
	
	public MainPanel() {
		setBackground(Color.BLACK);

		popup = new PopupMenu(this);

		/*
		 * Add listeners
		 */
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.scale(Menu.getInstance().zoom, Menu.getInstance().zoom);
		
		g2d.setColor(Color.GRAY);
		g2d.drawLine((int)origin.getX()-8, (int)origin.getY(), (int)origin.getX()+8, (int)origin.getY());
		g2d.drawLine((int)origin.getX(), (int)origin.getY()-8, (int)origin.getX(), (int)origin.getY()+8);

		for (int i = 0; i < Menu.getInstance().blocks.size(); i++) {
			MenuBlock b = Menu.getInstance().blocks.get(i);
			
			RoundRectangle2D r = new RoundRectangle2D.Double(origin.getX() + b.x, origin.getY() + b.y, 100, 26+b.items.size()*ITEM_H, 8, 8);			
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(r);

			Stroke old_stroke = g2d.getStroke();
			
			//Bold stroke if it's the start-block
			if (Menu.getInstance().startBlock == b)
				g2d.setStroke(new BasicStroke(3));
			
			g2d.setColor((Menu.getInstance().selectedBlock == b) ? Color.RED : new Color(100,255,130));
			g2d.draw(r);
			
			g2d.setStroke(old_stroke);
			g2d.drawString(b.header, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y+14));

			int j;
			for (j = 0; j < b.items.size(); j++) {
				g2d.setColor((Menu.getInstance().selectedItem == b.items.get(j)) ? Color.GRAY : Color.DARK_GRAY);
				g2d.fillRect((int)(origin.getX() + b.x+2), (int)(origin.getY() + b.y + 23 + j*ITEM_H), 96, ITEM_H-2);
				g2d.setColor((Menu.getInstance().selectedItem == b.items.get(j)) ? Color.RED : Color.BLACK);
				g2d.drawRect((int)(origin.getX() + b.x+2), (int)(origin.getY() + b.y + 23 + j*ITEM_H), 96, ITEM_H-2);
				g2d.setColor(Color.WHITE);
				g2d.drawString(b.items.get(j).caption, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y+38+j*30));
				
				if (b.items.get(j).getType() == MenuItem.TYPE_LINK) {

					g2d.fillOval((int)(origin.getX() + b.x+97), (int)(origin.getY() + b.y+32+j*ITEM_H), 6, 6);
					
					if ((b.items.get(j)).link != null) {
						int dist_x = b.x+100 - b.items.get(j).link.x;
						int dist_y = b.y+35+j*ITEM_H - (b.items.get(j).link.y+b.items.get(j).link.getHeight()/2);
								
						int ctrl_x = (int) Math.abs((dist_x / 4)) + 30;
						int ctrl_y = (int) (dist_y / 8);
						
						//g2d.drawOval(b.x+100+ctrl_x-4, b.y+35+j*20 - ctrl_y-4, 8, 8);
						
						CubicCurve2D c = new CubicCurve2D.Double();
						c.setCurve(
								(int)(origin.getX() + b.x+100), (int)(origin.getY() + b.y + 35 + j*ITEM_H), 
								(int)(origin.getX() + b.x+100+ctrl_x), (int)(origin.getY() + b.y + 35 + j*ITEM_H - ctrl_y), 
								(int)(origin.getX() + b.items.get(j).link.x-ctrl_x), (int)(origin.getY() + b.items.get(j).link.y + b.items.get(j).link.getHeight() / 2 + ctrl_y), 
								(int)(origin.getX() + b.items.get(j).link.x), (int)(origin.getY() + b.items.get(j).link.y + b.items.get(j).link.getHeight() / 2));
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
			Menu.getInstance().selectedBlock.x = (int) (e.getX()/Menu.getInstance().zoom - origin.getX() - downPoint.getX());
			Menu.getInstance().selectedBlock.y = (int) (e.getY()/Menu.getInstance().zoom - origin.getY() - downPoint.getY());
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

		Menu.getInstance().selectedBlock = null;
		Menu.getInstance().selectedItem = null;
		MainWindow.mw.setitempanel.setVisible(false);
		this.repaint();

		for (int i = Menu.getInstance().blocks.size()-1; i >= 0 ; i--) {
			MenuBlock b = Menu.getInstance().blocks.get(i);

			Point scaled = new Point();
			scaled.setLocation(
					0-origin.getX() + e.getPoint().getX() / Menu.getInstance().zoom, 
					0-origin.getY() + e.getPoint().getY() / Menu.getInstance().zoom);
			
			if (b.isInside(scaled)) 
			{
				canDrag = true;
				Menu.getInstance().selectedBlock = b;
				Menu.getInstance().selectedItem = Menu.getInstance().selectedBlock.hitItem((int)scaled.getX(), (int)scaled.getY());
				
				if (Menu.getInstance().selectedItem != null) {
					MainWindow.mw.setitempanel.setActiveItem(Menu.getInstance().selectedItem);					
				}
				
				downPoint = new Point(
						(int)((e.getX() / Menu.getInstance().zoom) - b.x - origin.getX()), 
						(int)((e.getY()/ Menu.getInstance().zoom) - b.y - origin.getY()));
				
				this.repaint();
				break;
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
			Menu.getInstance().blocks.add(new MenuBlock());
			Menu.getInstance().blocks.get(Menu.getInstance().blocks.size()-1).x = (int) (this.getMousePosition().getX() / Menu.getInstance().zoom);
			Menu.getInstance().blocks.get(Menu.getInstance().blocks.size()-1).y = (int) (this.getMousePosition().getY() / Menu.getInstance().zoom);
			repaint();
		}
		else if (e.getActionCommand().equals("removeblock")) {
			
			if (Menu.getInstance().selectedBlock != null) {
				
				//Remove all links to this block
				for (int i = 0; i < Menu.getInstance().blocks.size(); i++) {
					MenuBlock b = Menu.getInstance().blocks.get(i);
					
					for (int j = 0; j < b.items.size(); j++) {
						if (b.items.get(j).type == MenuItem.TYPE_LINK &&
								b.items.get(j).link != null) {
							
							if (b.items.get(j).link == Menu.getInstance().selectedBlock) {
								b.items.get(j).link = null;
							}
						}
					}
				}
				
				Menu.getInstance().selectedItem = null;
				Menu.getInstance().blocks.remove(Menu.getInstance().selectedBlock);
				Menu.getInstance().selectedBlock = null;
				repaint();
			}
		}
		else if (e.getActionCommand().equals("removeitem")) {
			Menu.getInstance().selectedBlock.items.remove(Menu.getInstance().selectedItem);
			Menu.getInstance().selectedItem = null;
			MainWindow.mw.setitempanel.setActiveItem(null);
			repaint();
		}
		else if (e.getActionCommand().equals("additem")) {
			Menu.getInstance().selectedBlock.items.add(new MenuItem("default", MenuItem.TYPE_TEXT));
			repaint();
		}
	}
	
	class PopupMenu extends JPopupMenu {

		private static final long serialVersionUID = 1L;
		
		JMenuItem item_addblock;
		JMenuItem item_additem;
		JMenuItem item_removeblock;
		JMenuItem item_removeitem;
		
		public PopupMenu(MainPanel mp) {
			item_additem = new JMenuItem("Add item");
			item_additem.setActionCommand("additem");
			item_additem.addActionListener(mp);
			add(item_additem);

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
			item_additem.setVisible(false);
			item_removeitem.setVisible(false);
			item_removeblock.setVisible(false);
			
			if (Menu.getInstance().selectedBlock != null) {
				if (Menu.getInstance().selectedItem != null) {
					item_removeitem.setVisible(true);
				}
				item_additem.setVisible(true);
				item_removeblock.setVisible(true);
			}
			else {
				item_addblock.setVisible(true);
			}
			this.show(invoker, x, y);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		double zoom = Menu.getInstance().zoom;

		//Scaled e
		Point se = new Point((int)(e.getX()/zoom), (int)(e.getY()/zoom));

		if (e.getWheelRotation() >= 0) {
			double factor = 0.8;
			
			if (zoom > 0.3) {
				Menu.getInstance().zoom *= factor;
				
				int newx = (int) ((se.getX() - (se.getX() - origin.getX())*factor)/factor);
				int newy = (int) ((se.getY() - (se.getY() - origin.getY())*factor)/factor);

				origin.setLocation(newx, newy);
			}
		}
		else {
			double factor = 1.25;
			
			if (zoom < 20) {
				Menu.getInstance().zoom *= factor;

				int newx = (int) ((se.getX() - (se.getX() - origin.getX())*factor)/factor);
				int newy = (int) ((se.getY() - (se.getY() - origin.getY())*factor)/factor);

				origin.setLocation(newx, newy);
			}
		}
		
		this.repaint();
	}
}
