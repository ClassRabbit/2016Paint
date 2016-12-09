package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;
import shapes.GEGroup;
import shapes.GEShape;

public class GEMenuEdit extends JMenu{
	
	private GEDrawingPanel drawingPanel;
	private EditMenuHandler editMenuHandler;
	
	public GEMenuEdit(String label) {
		super(label);
		editMenuHandler = new EditMenuHandler();
		for(EEditMenuItems btn : EEditMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);
		}
	}
	
	public void init(GEDrawingPanel drawingPanel){
		this.drawingPanel = drawingPanel;
	}
	
	private class EditMenuHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(EEditMenuItems.valueOf(e.getActionCommand())){
			case Group : drawingPanel.group(new GEGroup()); break;
			case Ungroup : drawingPanel.unGroup(); break;
			case Redo : break;
			case Undo : break;
			case 복사 : drawingPanel.copy(); break;
			case 붙이기 : drawingPanel.paste();break;
			case 삭제 : drawingPanel.delete(); break;
			case 잘라내기 : drawingPanel.cut(); break;
			}
		}
	}
}
