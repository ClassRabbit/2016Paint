package utils;

import java.util.ArrayList;
import java.util.Vector;

import shapes.GEShape;

public class GEHistory {
	private Vector<ArrayList<GEShape>> history;
	private int layerNum; //현재 레이어
	
	public GEHistory(){
		history = new Vector<ArrayList<GEShape>>();
		layerNum = 0;
	}
	
	//스택에 넣기.
	public void push(ArrayList<GEShape> shapeList){
		//임시 어레이 리스트 생성.
		//
		System.out.println("history push");
		
		ArrayList<GEShape> temp = new ArrayList<GEShape>();
		if(layerNum < history.size()){
			for(int i = history.size() -1; layerNum <= i; i--){
				history.remove(i);
			}
		}
		
		for(int i =0; i<shapeList.size(); i++){
			temp.add(shapeList.get(i).deepCopy());
		}
		history.add(temp);
		layerNum++; 
		System.out.println("history size : " + history.size() + ", num : " + layerNum);
	}
	
	public ArrayList<GEShape> undo(){
		if(0 > layerNum-1){
			return new ArrayList<GEShape>();
		}
		layerNum = layerNum-1;
		System.out.println("history size : " + history.size() + ", num : " + layerNum);
		if(layerNum == 0){
			return new ArrayList<GEShape>();
		}
		else {
			return history.get(layerNum-1);
		}
	}
	
	public ArrayList<GEShape> redo(){
		if(history.size() < layerNum+1){
			System.out.println("redo error");
			if(history.size() == 0){
				return new ArrayList<GEShape>();
			}
			else {
				return history.lastElement();
			}
		}
		layerNum = layerNum+1;
		System.out.println("history size : " + history.size() + ", num : " + layerNum);
		return history.get(layerNum-1);
	}
	
	public int getLayerNum(){
		return layerNum;
	}
	
	public void clear(){
		history.clear();
	}

}