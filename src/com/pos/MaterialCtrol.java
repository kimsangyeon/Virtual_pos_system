package com.pos;

import java.util.ArrayList;

public class MaterialCtrol 
{
	private ArrayList<Material> materials;		// 재료 리스트
	
	public void listAdd(ArrayList<Material> materials) { this.materials= materials; } // 리스트 대입
	public ArrayList<Material> reArraylist() { return materials; } 		// 리스트 반환
	public int arrayNum() { return materials.size(); }		// 리스트의 크기
}
