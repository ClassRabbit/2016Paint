package utils;

import java.util.ArrayList;
import java.util.Vector;

import shapes.GEShape;

public class GEStack {

	//도형이 담겨져 있는 리스트를 다시 리스트 생성.
	private Vector<ArrayList<GEShape>> history;
	private int num; //스택 갯수.
	
	public GEStack(){
		history = new Vector<ArrayList<GEShape>>();
		num = 0;
	}
	
	//스택에 넣기.
	public void push(ArrayList<GEShape> shapeList){
		//임시 어레이 리스트 생성.
		//
		System.out.println("stack push");
		ArrayList<GEShape> temp = new ArrayList<GEShape>();
		if(num < history.size()){
			for(int i = history.size() -1; num <= i; i--){
				history.remove(i);
			}
		}
		
		for(int i =0; i<shapeList.size(); i++){
			temp.add(shapeList.get(i).deepCopy());
		}
		history.add(temp);
		num++; 
		System.out.println("history num : " + num);
	}
	
	//스택에서 꺼내오기. index +1 : redo, index -1 : undo
//	public ArrayList<GEShape> seek(int index){
//		if(num < history.size()){
//			num = num + index;
//		}
//		ArrayList<GEShape> temp = null;
//		
//		if(num > 0 && num <= history.size()){
//			temp = history.get(num -1);
//		}else if(num == history.size()){
//			temp = history.lastElement();
//		}
//		return temp;
//	}
	
	public ArrayList<GEShape> undo(){
		if(0 > num - 1){
			System.out.println("history 에러");
			return new ArrayList<GEShape>();
		}
		num = num-1;
		System.out.println("history num : " + num);
		if(num == 0){
			return new ArrayList<GEShape>();
		}
		else {
			ArrayList<GEShape> temp = history.get(num);
			System.out.println("temp length : " + temp.size());
			return history.get(num-1);
		}
	}
	
//	public int getStackSize(){
//		return num;
//	}
	
	public int getLayerNum(){
		return num;
	}
	
	public void clear(){
		history.clear();
	}

}
