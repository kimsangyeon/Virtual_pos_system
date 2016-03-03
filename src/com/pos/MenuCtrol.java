package com.pos;

import java.util.ArrayList;

public class MenuCtrol 
{
	private ArrayList<Menu> menu;		// menu List
	
	public void listAdd(ArrayList<Menu> menu) { this.menu = menu; }		// menu List 추가
	public ArrayList<Menu> reArraylist() { return menu; } 			// menu List 반환
	public int arrayNum() { return menu.size(); }			// menu List 크기
	
}
