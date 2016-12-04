package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;

public class GEMenuBar extends JMenuBar{
	GEMenuFile menuFile;
	public GEMenuBar() {
		super();
		menuFile = new GEMenuFile(GEConstants.TITLE_FILEMENU);
		this.add(menuFile);
	}
}
