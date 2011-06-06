/*
 *  MenuGenerator, a free menu generator for microcontrollers
 *  Copyright (C) 2011  Erik Abrahamsson
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package menugen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
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
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class MainPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;
	
	boolean canDrag = false;
	Point downPoint = new Point();
	Point origin = new Point();
	PopupMenu popup;
	
	public MainPanel() {
		setBackground(new Color(40,40,40));

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
			
			RoundRectangle2D r = new RoundRectangle2D.Double(origin.getX() + b.x, origin.getY() + b.y, MenuBlock.WIDTH, MenuBlock.HEADER_HEIGHT + 3 +b.items.size()*MenuItem.HEIGHT, 13, 13);			
			g2d.setColor(Color.BLACK);
			g2d.fill(r);

			Stroke old_stroke = g2d.getStroke();
			Font old_font = g2d.getFont();
			
			//Bold stroke if it's the start-block
			if (Menu.getInstance().startBlock == b)
				g2d.setStroke(new BasicStroke(4));
			else 
				g2d.setStroke(new BasicStroke(2));
			
			//Gradient colors
			Color g1, g2;
			
			if (Menu.getInstance().selectedBlock == b) {
				g1 = new Color(0,255,0);
				g2 = new Color(0,120,20);
			}
			else {
				g1 = new Color(255,110,0);
				g2 = new Color(140,0,0);
			}

			GradientPaint gp = new GradientPaint(
					(float) ((origin.getX() + b.x)), 
					(float) ((origin.getY() + b.y)), g1, 
					(float) ((origin.getX() + b.x)), 
					(float) ((origin.getY() + b.y+b.getHeight())), g2);

			g2d.setPaint(gp);
			g2d.draw(r);
			
			g2d.setStroke(old_stroke);
			
			//Make font bigger and draw header
			g2d.setFont(new Font(old_font.getFontName(), Font.BOLD, 12));
			g2d.drawString(b.header, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y+14));

			//Restore font and draw name
			g2d.setFont(old_font);
			g2d.setColor(new Color(155,155,155));
			g2d.drawString(b.name, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y+29));

			int j;
			for (j = 0; j < b.items.size(); j++) {
				if (Menu.getInstance().selectedItem != b.items.get(j)) {
					g1 = new Color(50,50,50);
					g2 = new Color(80,80,80);
				}
				else {
					g1 = new Color(30,70,30);
					g2 = new Color(30,140,30);
				}
				
				gp = new GradientPaint(
						(float) ((origin.getX() + b.x)), 
						(float) ((origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + j*MenuItem.HEIGHT)), g1, 
						(float) ((origin.getX() + b.x)), 
						(float) ((origin.getY() + b.y+ MenuBlock.HEADER_HEIGHT + j*MenuItem.HEIGHT + MenuItem.HEIGHT)), g2);

				g2d.setPaint(gp);
				g2d.fillRect((int)(origin.getX() + b.x+2), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + j*MenuItem.HEIGHT), MenuBlock.WIDTH - 4, MenuItem.HEIGHT-2);

				g2d.setColor(Color.WHITE);
				g2d.drawString(b.items.get(j).caption, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + 12 + j*MenuItem.HEIGHT));

				g2d.setColor(new Color(155,155,155));
				g2d.drawString(b.items.get(j).name, (int)(origin.getX() + b.x+6), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + 26 + j*MenuItem.HEIGHT));

				g2d.setColor(Color.WHITE);
				
				if (b.items.get(j).getType() == MenuItem.TYPE_LINK) {

					g2d.fillOval((int)(origin.getX() + b.x + MenuBlock.WIDTH - 3), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + MenuItem.HEIGHT/2 + j*MenuItem.HEIGHT)-3, 6, 6);
					
					if ((b.items.get(j)).link != null) {
						int dist_x = b.x + MenuBlock.WIDTH - b.items.get(j).link.x;
						int dist_y = b.y+35+j*MenuItem.HEIGHT - (b.items.get(j).link.y + MenuBlock.HEADER_HEIGHT/2);
								
						int ctrl_x = (int) Math.abs((dist_x / 4)) + 30;
						int ctrl_y = (int) (dist_y / 8);
						
						//g2d.drawOval(b.x+100+ctrl_x-4, b.y+35+j*20 - ctrl_y-4, 8, 8);
						
						CubicCurve2D c = new CubicCurve2D.Double();
						c.setCurve(
								(int)(origin.getX() + b.x + MenuBlock.WIDTH), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + MenuItem.HEIGHT/2 + j*MenuItem.HEIGHT), 
								(int)(origin.getX() + b.x + MenuBlock.WIDTH + ctrl_x), (int)(origin.getY() + b.y + MenuBlock.HEADER_HEIGHT + MenuItem.HEIGHT/2 + j*MenuItem.HEIGHT - ctrl_y), 
								(int)(origin.getX() + b.items.get(j).link.x-ctrl_x), (int)(origin.getY() + b.items.get(j).link.y + MenuBlock.HEADER_HEIGHT / 2 + ctrl_y), 
								(int)(origin.getX() + b.items.get(j).link.x), (int)(origin.getY() + b.items.get(j).link.y + MenuBlock.HEADER_HEIGHT / 2));
						g2d.draw(c);						
					}
				}
				else if (b.items.get(j).getType() == MenuItem.TYPE_VALUE) {
					FontMetrics fm = g2d.getFontMetrics();

					String str = "Min: " + String.valueOf(b.items.get(j).minValue);
					Rectangle2D rect = fm.getStringBounds(str, null);
					g2d.drawString(str, (int)(origin.getX() + b.x+MenuBlock.WIDTH - rect.getWidth() - 6), (int)(origin.getY() + b.y+ MenuBlock.HEADER_HEIGHT + 12 + j*MenuItem.HEIGHT));

					str = "Max: " + String.valueOf(b.items.get(j).maxValue);
					rect = fm.getStringBounds(str, null);
					g2d.drawString(str, (int)(origin.getX() + b.x+MenuBlock.WIDTH - rect.getWidth() - 6), (int)(origin.getY() + b.y+ MenuBlock.HEADER_HEIGHT + 24 + j*MenuItem.HEIGHT));

					str = "Default: " + String.valueOf(b.items.get(j).defaultValue);
					rect = fm.getStringBounds(str, null);
					g2d.drawString(str, (int)(origin.getX() + b.x+MenuBlock.WIDTH - rect.getWidth() - 6), (int)(origin.getY() + b.y+ MenuBlock.HEADER_HEIGHT + 36 + j*MenuItem.HEIGHT));
				}
				else if (b.items.get(j).getType() == MenuItem.TYPE_FUNCTION) {
					FontMetrics fm = g2d.getFontMetrics();

					String str = b.items.get(j).functionName;
					Rectangle2D rect = fm.getStringBounds(str, null);
					g2d.drawString(str, (int)(origin.getX() + b.x+MenuBlock.WIDTH - rect.getWidth() - 6), (int)(origin.getY() + b.y+ MenuBlock.HEADER_HEIGHT + MenuItem.HEIGHT - 6 + j*MenuItem.HEIGHT));
				}
			}
		}
		
		g2d.dispose();
	}

	private void paintBorderShadow(Graphics2D g2, int shadowWidth) {
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                        RenderingHints.VALUE_ANTIALIAS_ON);
	    int sw = shadowWidth*2;
	    for (int i=sw; i >= 2; i-=2) {
	        float pct = (float)(sw - i) / (sw - 1);
	        g2.setColor(Color.GRAY);
	        g2.setStroke(new BasicStroke(i));
	        //g2.draw(clipShape);
	    }
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

		if (e.getButton() == MouseEvent.BUTTON2) {
			Menu.getInstance().zoom = 1;
			origin.setLocation(0, 0);
			repaint();
			return;
		}
		
		Menu.getInstance().selectedBlock = null;
		Menu.getInstance().selectedItem = null;
		MainWindow.mw.setItemPanel.setVisible(false);
		MainWindow.mw.setBlockPanel.setVisible(false);
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
					MainWindow.mw.setItemPanel.setActiveItem(Menu.getInstance().selectedItem);	
				}

				MainWindow.mw.setBlockPanel.setActiveBlock(Menu.getInstance().selectedBlock);
				
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
				MainWindow.mw.setBlockPanel.setActiveBlock(null);
				repaint();
			}
		}
		else if (e.getActionCommand().equals("removeitem")) {
			Menu.getInstance().selectedBlock.items.remove(Menu.getInstance().selectedItem);
			Menu.getInstance().selectedItem = null;
			MainWindow.mw.setItemPanel.setActiveItem(null);
			repaint();
		}
		else if (e.getActionCommand().equals("additem")) {
			Menu.getInstance().selectedBlock.items.add(new MenuItem("default", MenuItem.TYPE_TEXT));
			repaint();
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
}
