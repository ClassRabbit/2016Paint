package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import utils.GEAnchorList;

public abstract class GEShape {
	protected Point startP;
	protected Shape shape;
	protected Color lineColor, fillColor;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected AffineTransform affineTransform;
	
	public GEShape(Shape shape){
		this.shape = shape;
		anchorList = null;
		selected = false;
		affineTransform = new AffineTransform();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public GEAnchorList getAnchorList(){
		return anchorList;
	}
	
	public EAnchorTypes getSelectedAnchor() {
		return selectedAnchor;
	}
	
	public Rectangle getBounds(){
		return shape.getBounds();
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
		if(selected == true){
			anchorList = new GEAnchorList();
			anchorList.setPosition(shape.getBounds());
		} 
		else {
			anchorList = null;
		}
	}
	
	public boolean onShape(Point p){
		if(anchorList != null){		//선택된 상태 
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != GEConstants.EAnchorTypes.NONE){
				return true;
			}
		}
		return shape.intersects(p.x, p.y, 2 ,2);
	}
	
	public void draw(Graphics2D g2d){
		if(fillColor != null){
			g2d.setColor(fillColor);
			g2d.fill(shape);
		}
		if(lineColor != null){
			g2d.setColor(lineColor);
			g2d.draw(shape);
		}
		if(selected){
			anchorList.setPosition(shape.getBounds());
			anchorList.draw(g2d);
		}
	}
	
	public void move(Point resizeAnchor){
		affineTransform.setToTranslation(resizeAnchor.x, resizeAnchor.y);
		shape = affineTransform.createTransformedShape(shape);
	}
	
	public void moveReverse(Point resizeAnchor){
		affineTransform.setToTranslation(-resizeAnchor.x, -resizeAnchor.y);
		shape = affineTransform.createTransformedShape(shape);
	}
	
	public void resizeCoordinate(Point2D resizeFactor){
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		shape= affineTransform.createTransformedShape(shape);
	}
	
	public void moveCoordinate(Point moveP){
		affineTransform.setToTranslation(moveP.x, moveP.y);
		shape = affineTransform.createTransformedShape(shape);
	}
	
	abstract public void initDraw(Point startP);
	abstract public void setCoordinate(Point currentP);
	abstract public GEShape clone();
}
