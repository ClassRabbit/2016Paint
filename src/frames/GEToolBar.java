package frames;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.GEConstants;
import constants.GEConstants.EToolBarButtons;

public class GEToolBar extends JToolBar{
	
	private ButtonGroup buttonGroup;
	
	public GEToolBar(String label) {
		super(label);
		buttonGroup = new ButtonGroup();
		JRadioButton rButton;
		for(EToolBarButtons button : EToolBarButtons.values()){
			rButton = new JRadioButton();
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL + button.toString() + GEConstants.TOOLBAR_BTN));
			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL + button.toString() + GEConstants.TOOLBAR_BTN_SLT));
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}
}
