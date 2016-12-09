package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import utils.GEAnchorList;

public class GEGroup extends GEShape{
	private ArrayList<GEShape> shapeList;
	private BasicStroke dashedLineStroke;
	public GEGroup(){
		super(new Rectangle());
		shapeList = new ArrayList<GEShape>();
		float dashes[] = {GEConstants.DEFAULT_DASH_OFFSET};
		dashedLineStroke = new BasicStroke(GEConstants.DEFAULT_DASHEDLINE_WIDTH,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,10,dashes , 0);
	}
	
	
	
	@Override
	public void initDraw(Point startP) {
		for(GEShape shape : shapeList){
			shape.initDraw(startP);
		}
		
	}
	@Override
	public void setCoordinate(Point currentP) {
		for(GEShape shape : shapeList){
			shape.setCoordinate(currentP);
		}
	}
	
	public void addShape(GEShape shape){
		shapeList.add(0,shape);
		if(shapeList.size()==1){
			myShape = shape.getBounds();
		}else{
			myShape = myShape.getBounds().createUnion(shape.getBounds());
		}
	}
	
	public ArrayList<GEShape> getChildList(){
		return shapeList;
	}
	
	public void setLineColor(Color lineColor){
		for(GEShape shape : shapeList){
			shape.setLineColor(lineColor);
		}
	}
	
	public void setFillColor(Color fillColor){
		for(GEShape shape : shapeList){
			shape.setFillColor(fillColor);
		}
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
		
		if(selected){
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}else{
			anchorList = null;
		}
	}
	
	public boolean onShape(Point p){
		if(anchorList != null){
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorTypes.NONE){
				return true;
			}
		}
		for(GEShape shape : shapeList){
			if(shape.onShape(p)){
				return true;
			}
		}
		return false;
	}
	public void draw(Graphics2D g2D){
		for(GEShape shape : shapeList){
			shape.draw(g2D);
		}
		if(this.isSelected()){
			g2D.setColor(GEConstants.LINE_COLOR_DEFAULT);
			g2D.setStroke(dashedLineStroke);
			g2D.draw(myShape);
			g2D.setStroke(new BasicStroke());
			this.getAnchorList().setPosition(this.getBounds());
			this.getAnchorList().draw(g2D);
		}
	}
	public void moveCoordinate(Point tempP){
		super.moveCoordinate(tempP);
		for(GEShape shape : shapeList){
			shape.moveCoordinate(tempP);
		}
	}
	public void resizeCoordinate(Point2D resizeFactor){
		super.resizeCoordinate(resizeFactor);
		for(GEShape shape : shapeList){
			shape.resizeCoordinate(resizeFactor);
		}
	}
	
	public void moveReverse(Point2D resizeAnchor){
		super.moveReverse(resizeAnchor);
		for(GEShape shape : shapeList){
			shape.moveReverse(resizeAnchor);
		}
	}
	
	public void move(Point2D resizeAnchor){
		super.move(resizeAnchor);
		for(GEShape shape : shapeList){
			shape.move(resizeAnchor);
		}
	}
	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public GEShape deepCopy(){
		GEGroup returnShape = new GEGroup();
		for(GEShape shape :shapeList){
			returnShape.addShape(shape.deepCopy());
		}
		return returnShape;
	}
}
