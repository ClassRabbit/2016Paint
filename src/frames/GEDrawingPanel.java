package frames;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import constants.GEConstants;
import shapes.GERectangle;

public class GEDrawingPanel extends JPanel{
	private GERectangle rectangle;
	private DrawingHandler drawingHandler;
	
	public GEDrawingPanel() {
		drawingHandler = new DrawingHandler();
		this.addMouseListener(drawingHandler);
		this.addMouseMotionListener(drawingHandler);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
	}
	
	public void initDraw(Point p){
		rectangle = new GERectangle();
		rectangle.initDraw(p);
	}
	
	public void animateDraw(Point p){
//		rectangle.setCoordinate(p);
		Graphics2D g2d = (Graphics2D)this.getGraphics();
		g2d.setXORMode(GEConstants.BACKGROUND_COLOR);
		g2d.draw(rectangle.getRectangle());
		rectangle.setCoordinate(p);
		g2d.draw(rectangle.getRectangle());
	}
	
	private class DrawingHandler extends MouseAdapter{
		@Override
		public void mouseDragged(MouseEvent e){
			animateDraw(e.getPoint());
		}
		
		@Override
		public void mousePressed(MouseEvent e){
			initDraw(e.getPoint());
		}
	}
}
