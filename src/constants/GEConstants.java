package constants;

import java.awt.Color;

public class GEConstants {
	///GEMainFrame
	public static final int WIDTH_MAINFRAME = 400;
	public static final int HEIGHT_MAINFRAME = 600;
	public static final String TITLE_MAINFRAME = "GraphicEditorTest";
	
	//GEMenu
	public static final String TITLE_FILEMENU = "����";
	public static final String TITLE_EDITMENU = "����";
	public static final String TITLE_COLORMENU = "�÷�";
	
	//GEmenuItems
	public static enum EFileMenuItems{���θ����, ����, ����, �ٸ��̸���������, ����};
	public static enum EEditMenuItems{Undo, Redo, ����, �߶󳻱�, ����, ���̱�, Group, Ungroup};
	public static enum EColorMenuItems{SetLineColor, ClearLineColor, SetFileColor, ClearFileColor};
	
	//GEDrawingPanel
	public static Color FOREGROUND_COLOR = Color.black;
	public static Color BACKGROUND_COLOR = Color.white;
}
