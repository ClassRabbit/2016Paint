package utils;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;

public class GEAnchorList {
	private ArrayList<Ellipse2D.Double> anchors;
	
	public GEAnchorList(){
		anchors = new ArrayList<Ellipse2D.Double>();
		for(int i=0;i<GEConstants.EAnchorTypes.values().length - 1; i++){
			anchors.add(new Ellipse2D.Double(0,0,0,0));
		}
	}
	
	public ArrayList<Ellipse2D.Double> getAnchors(){
		return anchors;
	}
	
	public void setPosition(Rectangle r){
		int x = r.x;
		int y = r.y;
		int w = r.width;
		int h = r.height;
		int dx = GEConstants.ANCHOR_W/2;
		int dy = GEConstants.ANCHOR_H/2;
		
		anchors.get(EAnchorTypes.NW.ordinal()).setFrame(x-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.NN.ordinal()).setFrame(x+w/2-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.NE.ordinal()).setFrame(x+w-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.WW.ordinal()).setFrame(x-dx, y+h/2-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.EE.ordinal()).setFrame(x+w-dx, y+h/2-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.SW.ordinal()).setFrame(x-dx, y+h-dy,  GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.SS.ordinal()).setFrame(x+w/2-dx, y+h-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.SE.ordinal()).setFrame(x+w-dx, y+h-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(EAnchorTypes.RR.ordinal()).setFrame(x+w/2-dx, y-GEConstants.RR_OFFSET, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
	
	}
	
}
