package constants;

import java.awt.Color;

public class GEConstants {
	///GEMainFrame
	public static final int WIDTH_MAINFRAME = 400;
	public static final int HEIGHT_MAINFRAME = 600;
	public static final String TITLE_MAINFRAME = "GraphicEditorTest";
	
	//GEMenu
	public static final String TITLE_FILEMENU = "파일";
	public static final String TITLE_EDITMENU = "편집";
	public static final String TITLE_COLORMENU = "컬러";
	
	//GEmenuItems
	public static enum EFileMenuItems{새로만들기, 열기, 저장, 다른이름으로저장, 종료};
	public static enum EEditMenuItems{Undo, Redo, 삭제, 잘라내기, 복사, 붙이기, Group, Ungroup};
	public static enum EColorMenuItems{SetLineColor, ClearLineColor, SetFileColor, ClearFileColor};
	
	//GEDrawingPanel
	public static Color FOREGROUND_COLOR = Color.black;
	public static Color BACKGROUND_COLOR = Color.white;
}
