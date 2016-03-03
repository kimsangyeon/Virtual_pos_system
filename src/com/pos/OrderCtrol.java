package com.pos;

import java.util.ArrayList;

public class OrderCtrol 
{
	private ArrayList<Order> order;			// order List
	
	public int arrayNum() { return order.size(); }		// List 크기
	public void listAdd(ArrayList<Order> order) { this.order = order; }		// 리스트 추가
	public ArrayList<Order> reArraylist() { return order; } 		// 리스트 반환
	public void classAdd(Order or) { order.add(or); }		// 리스트에 class 추가 (add)
	
}
