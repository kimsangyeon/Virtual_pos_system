package com.pos;

import java.util.ArrayList;

public class StaffCtrol
{
	private ArrayList<Staff> staff;		// staff List

	public void listAdd(ArrayList<Staff> staff) { this.staff = staff; }	// ����Ʈ �߰�
	public ArrayList<Staff> reArraylist() { return staff; }		// ����Ʈ ��ȯ
	public int arrayNum() { return staff.size(); }		// ����Ʈ ũ�� 
}
