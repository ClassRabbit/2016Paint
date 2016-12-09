package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import shapes.GEGroup;
import shapes.GEShape;


public class GERotater extends GETransformer{
	private ArrayList<GEShape> shapelist;
	private Point2D.Double ROrigin;
	private double theta; //세타. 각도.

	public GERotater(GEShape shape) {
		super(shape);
		if(shape instanceof GEGroup){
			shapelist = new ArrayList<GEShape>();
			for(GEShape childshape : ((GEGroup) shape).getChildList()){
				shapelist.add(childshape);
			}
		}
	}

	@Override
	public void transformer(Graphics2D g2d, Point p) {
		// TODO Auto-generated method stub
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(dashedLineStroke);
		//마우스가 이동한 각도 구하기.
		double theta2 = theta - Math.atan2(ROrigin.y- p.getY(), p.getX() - ROrigin.x);
		if(shape instanceof GEGroup){
			GEShape temp;
			for(int i = 0; i < shapelist.size(); i++){
				temp = shapelist.get(i);
				temp.draw(g2d);
				temp.rotaterCoordinate(theta2, ROrigin);
				temp.draw(g2d);
				
			}
		}else{
			shape.draw(g2d);
			shape.rotaterCoordinate(theta2, ROrigin);
			shape.draw(g2d);
		}
		//AffineTransform으로 변환
		theta = Math.atan2(ROrigin.y - p.getY(), p.getX() - ROrigin.x); // 이동한 각 저장
		
		

	}
	

	@Override
	public void init(Point p) {
		// TODO Auto-generated method stub
		//회전할 도형의 중심을 구해라.
		ROrigin = new Point2D.Double(shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
		theta = Math.atan2(ROrigin.y - p.getY(), ROrigin.x - p.getX());

	}
}
