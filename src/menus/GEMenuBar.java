package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;
import frames.GEDrawingPanel;

public class GEMenuBar extends JMenuBar{
	private GEMenuFile menuFile;
	private GEMenuEdit menuEdit;
	private GEMenuColor menuColor;
	public GEMenuBar() {
		super();
		menuFile = new GEMenuFile(GEConstants.TITLE_FILEMENU);
		menuEdit = new GEMenuEdit(GEConstants.TITLE_EDITMENU);
		menuColor = new GEMenuColor(GEConstants.TITLE_COLORMENU);
		this.add(menuFile);
		this.add(menuEdit);
		this.add(menuColor);
	}
	
	public void init(GEDrawingPanel drawingPanel){
		menuColor.init(drawingPanel);
		menuEdit.init(drawingPanel);
	}
}
