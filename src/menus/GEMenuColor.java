package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants.EColorMenuItems;

public class GEMenuColor extends JMenu{
	public GEMenuColor(String label) {
		super(label);
		JMenuItem item;
		for(EColorMenuItems btn : EColorMenuItems.values()){
			item = new JMenuItem(btn.toString());
			add(item);
		}
	}
}
