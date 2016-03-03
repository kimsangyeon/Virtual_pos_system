package com.pos;

import java.util.ArrayList;

public class StaffCtrol
{
	private ArrayList<Staff> staff;		// staff List

	public void listAdd(ArrayList<Staff> staff) { this.staff = staff; }	// 리스트 추가
	public ArrayList<Staff> reArraylist() { return staff; }		// 리스트 반환
	public int arrayNum() { return staff.size(); }		// 리스트 크기 
}
