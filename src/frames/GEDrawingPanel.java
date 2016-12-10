package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;
import shapes.GEPolygon;
import shapes.GERectangle;
import shapes.GESelect;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEGrouper;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GERotater;
import transformer.GETransformer;
import utils.GEClipBoard;
import utils.GECursorManager;
import utils.GEHistory;
import utils.GETextRotater;

public class GEDrawingPanel extends JPanel{
	
	private GEDrawingPanel instance;
	
	private GEShape currentShape, selectedShape;
	private EState currentState;
	private ArrayList<GEShape> shapeList;
	private GETransformer transformer;
	private Color fillColor, lineColor;
	private MouseDrawingHandler drawingHandler;
	private GECursorManager cursorManager;
	
	private GEClipBoard clipboard;
	private GEHistory history;
	private GETextRotater textRotater;
	
	public GEDrawingPanel() {
		super();
		instance = this;
		cursorManager = new GECursorManager();
		currentState = EState.Idle;
		shapeList = new ArrayList<GEShape>();
		drawingHandler = new MouseDrawingHandler();
		fillColor = GEConstants.FILL_COLOR_DEFAULT;
		lineColor = GEConstants.LINE_COLOR_DEFAULT;
		this.addMouseListener(drawingHandler);
		this.addMouseMotionListener(drawingHandler);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
		clipboard = new GEClipBoard();
		history = new GEHistory();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for(GEShape shape : shapeList){
			shape.draw(g2D);
		}
	}
	
	public void setFillColor(Color fillColor) {
		if(selectedShape != null){
			selectedShape.setFillColor(fillColor);
			history.push(shapeList);
			repaint();
		} else{
			this.fillColor = fillColor;
		}
	}

	public void setLineColor(Color lineColor) {
		if(selectedShape != null){
			selectedShape.setLineColor(lineColor);
			history.push(shapeList);
			repaint();
		} else{
			this.lineColor = lineColor;
		}
	}
	
	public void setCurrentShape(GEShape currentShape) {
//		System.out.println("setCurrentShape");
		this.currentShape = currentShape;
	}

	private void initDraw(Point startP){
		currentShape = currentShape.clone();
		if(currentShape instanceof GESelect){
			currentShape.setLineColor(GEConstants.LINE_COLOR_DEFAULT);
		}
		else {
			currentShape.setFillColor(fillColor);
			currentShape.setLineColor(lineColor);
		}
	}
	
	private void continueDraw(Point currentP){
		((GEDrawer)transformer).continueDrawing(currentP);
	}
	
	private void finishDraw(){
		shapeList.add(currentShape);
		history.push(shapeList);
	}
	
	private GEShape onShape(Point p){
		for(int i = shapeList.size() - 1; i >= 0; i--){
			GEShape shape = shapeList.get(i);
			if(shape.onShape(p)){
				return shape;
			}
		}
		return null;
	}
	
	private void clearSelectedShapes(){
		for(GEShape shape : shapeList){
			shape.setSelected(false);
		}
	}
	
	public void setCurrentState(EState currentState){
		this.currentState = currentState;
	}
	
	
//	public void group(GEGroup group){   ///////
//		boolean check = false;
//		for(int i = shapeList.size(); i>0 ; i--){
//			GEShape shape = shapeList.get(i-1);
//			if(shape.isSelected()){
//				shape.setSelected(false);
//				group.addShape(shape);
//				shapeList.remove(shape);
//				check = true;
//			}
//		}
//		if(check){
//			group.setSelected(true);
//			shapeList.add(group);
//			history.push(shapeList);
//			selectedShape = group;
//		}
//		repaint();
//	}
//	
//	public void unGroup(){       
//		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
//		
////		for(int i = 0 ; i<shapeList.size() ;i++){
////			GEShape shape = shapeList.get(i);
////			if(shape instanceof GEGroup && shape.isSelected()){
////				for(GEShape childShape : ((GEGroup)shape).getChildList()){
////					childShape.setSelected(true);
////					tempList.add(childShape);
////				}
////				shapeList.remove(shape);
////			}
////		}
//		for(int i = shapeList.size() ; i>0 ;i--){
//			GEShape shape = shapeList.get(i-1);
//			if(shape instanceof GEGroup && shape.isSelected()){
//				for(GEShape childShape : ((GEGroup)shape).getChildList()){
//					childShape.setSelected(true);
//					tempList.add(childShape);
//				}
//				shapeList.remove(shape);
//			}
//		}
//		shapeList.addAll(tempList);
//		history.push(shapeList);
//		repaint();
//	}
	
	//paste
	public void paste(){
		ArrayList<GEShape> pasteShapes = clipboard.paste();
		for(int i=pasteShapes.size()-1; i>=0; i--){
			shapeList.add(pasteShapes.get(i).deepCopy());
		}
//		for(GEShape shape: clipboard.paste()){ //붙이려고 하는 도형.
//			shapeList.add(shape.deepCopy());
//		}
		history.push(shapeList);
		repaint();
	}
	
	//copy
	public void copy(){
		clipboard.copy(shapeList);
		
	}
	
	public void cut(){
		clipboard.cut(shapeList);
		history.push(shapeList);
		repaint();
	}
	
	public void delete(){
		for(int i = shapeList.size(); i > 0 ; i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.isSelected()){
				shape.setSelected(false);
				shapeList.remove(shape);
			}
		}
		history.push(shapeList);
		repaint();
	}
	
	public void undo(){
		shapeList = history.undo();
		selectedShape = null;
		repaint();
	}
	
	public void redo(){
		shapeList = history.redo();
		selectedShape = null;
		repaint();
	}
	
	public void freshTextRotater(){
		if(textRotater != null){
			remove(textRotater.getTextField());
			textRotater = null;
		}
	}
	
	private class MouseDrawingHandler extends MouseAdapter{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentState !=EState.Idle){
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
				if(transformer instanceof GEMover){
					((GEMover)transformer).setMove(true);
				}else if(transformer instanceof GERotater){
					((GERotater)transformer).setMove(true);
				}
			}
		}
	
		@Override
		public void mousePressed(MouseEvent e){
			freshTextRotater();
			if(currentState == EState.Idle){
				if(currentShape instanceof GESelect){
					selectedShape = onShape(e.getPoint());
					if(selectedShape != null){
						clearSelectedShapes();
						selectedShape.setSelected(true);
						selectedShape.onAnchor(e.getPoint());
						if(selectedShape.getSelectedAnchor() == EAnchorTypes.NONE){
							transformer = new GEMover(selectedShape);
							((GEMover)transformer).init(e.getPoint());
							setCurrentState(EState.Moving);
						}else if(selectedShape.getSelectedAnchor() == EAnchorTypes.RR){ //회전일때 생성.
							transformer = new GERotater(selectedShape);
							((GERotater)transformer).init(e.getPoint());
							setCurrentState(EState.Rotater);
						}else{
							transformer  = new GEResizer(selectedShape);
							((GEResizer)transformer).init(e.getPoint());
							setCurrentState(EState.Resizing);
						}
					}else{
						setCurrentState(EState.Selecting);
						clearSelectedShapes();
						initDraw(e.getPoint());
						transformer = new GEGrouper(currentShape);
						((GEGrouper)transformer).init(e.getPoint());
					}
				}else{
					clearSelectedShapes();
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					((GEDrawer)transformer).init(e.getPoint());
					if(currentShape instanceof GEPolygon){
						setCurrentState(EState.NPointsDrawing);
					}else{
						setCurrentState(EState.TwoPointsDrawing);
					}
				}
			}
		}
		
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(currentState == EState.TwoPointsDrawing){
				finishDraw();
			}else if(currentState == EState.NPointsDrawing){
				return;
			}else if(currentState == EState.Resizing){
				((GEResizer)transformer).finalize();
				history.push(shapeList);
			}else if(currentState == EState.Selecting){
				((GEGrouper)transformer).finalize(shapeList);
			}else if(currentState == EState.Moving){
				if(((GEMover)transformer).isMoved()){
					history.push(shapeList);
				}
			}else if(currentState == EState.Rotater){
				if(((GERotater)transformer).isMoved()){
					history.push(shapeList);
				} else{
					System.out.println("로테이션 클릭");
					textRotater = new GETextRotater();
					textRotater.init(selectedShape, instance);
					add(textRotater.getTextField());
					textRotater.requestFocus();
				}
			}
			currentState = EState.Idle;
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				if(currentState == EState.NPointsDrawing){
					if(e.getClickCount() == 1){
						continueDraw(e.getPoint());
					}else if(e.getClickCount()==2){
						finishDraw();
						setCurrentState(EState.Idle);
//						currentState = EState.Idle;
						repaint();
					}
				}
			}
		}
		
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(currentState == EState.NPointsDrawing){
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}else if(currentState == EState.Idle){
				GEShape shape = onShape(e.getPoint());
				if(shape == null){
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}else if(shape.isSelected() == true){
					EAnchorTypes anchorType = shape.onAnchor(e.getPoint());
					setCursor(cursorManager.get(anchorType.ordinal()));
				}
			}
		}
	}
}