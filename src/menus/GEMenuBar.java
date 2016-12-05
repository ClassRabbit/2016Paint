package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;

public class GEMenuBar extends JMenuBar{
	GEMenuFile menuFile;
	GEMenuEdit menuEdit;
	public GEMenuBar() {
		super();
		menuFile = new GEMenuFile(GEConstants.TITLE_FILEMENU);
		menuEdit = new GEMenuEdit(GEConstants.TITLE_EDITMENU);
		this.add(menuFile);
		this.add(menuEdit);
	}
}
