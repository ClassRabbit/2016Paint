package shapes;

import java.awt.Point;
import java.awt.Polygon;

public class GEPolygon extends GEShape {
	public GEPolygon(){
		super(new Polygon());
	}

	@Override
	public void initDraw(Point startP) {
		((Polygon)shape).addPoint(startP.x, startP.y);
	}
	
	public void continueDraw(Point currentP){
		((Polygon)shape).addPoint(currentP.x, currentP.y);
	}

	@Override
	public void setCoordinate(Point currentP) {
		Polygon tempPolygon = (Polygon)shape;
		tempPolygon.xpoints[tempPolygon.npoints - 1] = currentP.x;
		tempPolygon.ypoints[tempPolygon.npoints - 1] = currentP.y;
		if(anchorList != null){
			anchorList.setPosition(shape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		return new GEPolygon();
	}

}
