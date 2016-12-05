package shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import constants.GEConstants.EAnchorTypes;

public class GEShape {
	protected Point startP;
	protected Shape shape;
	protected Color lineColor, fillColor;
	protected boolean selected;
//	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected AffineTransform affineTransform;
	
	public GEShape(Shape shape){
		this.shape = shape;
		selected = false;
		
	}
}
