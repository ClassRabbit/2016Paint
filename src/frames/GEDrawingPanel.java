package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;
import shapes.GEPolygon;
import shapes.GERectangle;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GETransformer;
import utils.GECursorManager;

public class GEDrawingPanel extends JPanel{
	
	private GEShape currentShape, selectedShape;
	private EState currentState;
	private ArrayList<GEShape> shapeList;
	private GETransformer transformer;
	private Color fillColor, lineColor;
	private MouseDrawingHandler drawingHandler;
	private GECursorManager cursorManager;
	
	public GEDrawingPanel() {
		super();
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
			repaint();
		} else{
			this.fillColor = fillColor;
		}
	}

	public void setLineColor(Color lineColor) {
		if(selectedShape != null){
			selectedShape.setLineColor(lineColor);
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
		currentShape.setFillColor(fillColor);
		currentShape.setLineColor(lineColor);
	}
	
	private void continueDraw(Point currentP){
		((GEDrawer)transformer).continueDrawing(currentP);
	}
	
	private void finishDraw(){
		shapeList.add(currentShape);
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
	private class MouseDrawingHandler extends MouseAdapter{
		
		@Override
		public void mouseDragged(MouseEvent e){
			if(currentState != EState.Idle){
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e){
			
			if(currentState == EState.Idle){
				if(currentShape != null){
//					System.out.println("mousePressed");
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					transformer.init(e.getPoint());
					if(currentShape instanceof GEPolygon){
						currentState = EState.NPointsDrawing;
					}else {
						currentState = EState.TwoPointsDrawing;
					}
				} else{
//					System.out.println("mousePressed null");
					selectedShape = onShape(e.getPoint());
					clearSelectedShapes();
					if(selectedShape != null){
						selectedShape.setSelected(true);
						if(selectedShape.onAnchor(e.getPoint()) == EAnchorTypes.NONE){
							transformer = new GEMover(selectedShape);
							currentState = EState.Moving;
						} else{
							transformer = new GEResizer(selectedShape);
							currentState = EState.Resizing;
						}
						transformer.init(e.getPoint());
					}
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			if(currentState == EState.TwoPointsDrawing){
				finishDraw();
			} else if(currentState == EState.NPointsDrawing){
				return;
			} else if(currentState == EState.Resizing){
				((GEResizer)transformer).finalize();
			}
			currentState = EState.Idle;
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				if(currentState == EState.NPointsDrawing){
					if(e.getClickCount() == 1){
						continueDraw(e.getPoint());
					}else if(e.getClickCount() == 2){ //double click
						finishDraw();
						currentState = EState.Idle;
						repaint();
					}
				}
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e){
			if(currentState == EState.NPointsDrawing){
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			} else if(currentState == EState.Idle){
				GEShape shape = onShape(e.getPoint());
				if(shape == null){
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} else if(shape.isSelected() == true){
					EAnchorTypes anchorType = shape.onAnchor(e.getPoint());
					setCursor(cursorManager.get(anchorType.ordinal()));
				}
			}
		}
	}
}