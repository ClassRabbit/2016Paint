package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GELine extends GEShape{
	
	public GELine() {
		super(new Line2D.Double());
	}

	@Override
	public void initDraw(Point startP) {
		this.startP = startP;
	}

	@Override
	public void setCoordinate(Point currentP) {
		Line2D line = (Line2D)shape;
		line.setLine(startP.x, startP.y, currentP.x, currentP.y);
		if(anchorList != null){
			anchorList.setPosition(shape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		return new GELine();
	}

}
