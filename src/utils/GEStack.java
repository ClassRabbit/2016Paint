package utils;

import java.util.ArrayList;
import java.util.Vector;

import shapes.GEShape;

public class GEStack {

	//도형이 담겨져 있는 리스트를 다시 리스트 생성.
	private Vector<ArrayList<GEShape>> data;
	private int num; //스택 갯수.
	
	public GEStack(){
		data = new Vector<ArrayList<GEShape>>();
		num = 0;
	}
	
	//스택에 넣기.
	public void push(ArrayList<GEShape> shapes){
		//임시 어레이 리스트 생성.
		ArrayList<GEShape> temp = new ArrayList<GEShape>();
		if(num < data.size()){
			for(int i = data.size() -1; num <= i; i--){
				data.remove(i);
			}
		}
		
		for(int i =0; i<shapes.size(); i++){
			temp.add(shapes.get(i).deepCopy());
		}
		data.add(temp);
		num++; 
		
	}
	
	//스택에서 꺼내오기. index +1 : redo, index -1 : undo
	public ArrayList<GEShape> pop(int index){
		if(num < data.size()){
			num = num + index;
		}
		ArrayList<GEShape> temp = null;
		
		if(num > 0 && num <= data.size()){
			temp = data.get(num -1);
		}else if(num == data.size()){
			temp = data.lastElement();
		}
		return temp;
	}
	public int getStackSize(){
		return num;
	}
	public void clear(){
		data.clear();
	}

}
