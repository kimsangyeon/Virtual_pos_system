package com.pos;

import java.util.ArrayList;

public class MaterialCtrol 
{
	private ArrayList<Material> materials;		// ��� ����Ʈ
	
	public void listAdd(ArrayList<Material> materials) { this.materials= materials; } // ����Ʈ ����
	public ArrayList<Material> reArraylist() { return materials; } 		// ����Ʈ ��ȯ
	public int arrayNum() { return materials.size(); }		// ����Ʈ�� ũ��
}
