package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants.EEditMenuItems;

public class GEMenuEdit extends JMenu{
	public GEMenuEdit(String label) {
		super(label);
		JMenuItem item;
		for(EEditMenuItems menu : EEditMenuItems.values()){
			item = new JMenuItem(menu.toString());
			this.add(item);
		}
	}
}
