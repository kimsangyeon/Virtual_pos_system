package com.pos;

import java.util.ArrayList;

public class MenuCtrol 
{
	private ArrayList<Menu> menu;		// menu List
	
	public void listAdd(ArrayList<Menu> menu) { this.menu = menu; }		// menu List �߰�
	public ArrayList<Menu> reArraylist() { return menu; } 			// menu List ��ȯ
	public int arrayNum() { return menu.size(); }			// menu List ũ��
	
}
