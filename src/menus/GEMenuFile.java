package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants;
import constants.GEConstants.EFileMenuItems;

public class GEMenuFile extends JMenu{

	public GEMenuFile(String label){
		super(label);
		for(EFileMenuItems item : EFileMenuItems.values() ){
			JMenuItem menuItem = new JMenuItem(item.toString());
			this.add(menuItem);
		}
	}
}
