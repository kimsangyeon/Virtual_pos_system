package com.pos;

import java.util.ArrayList;

public class OrderCtrol 
{
	private ArrayList<Order> order;			// order List
	
	public int arrayNum() { return order.size(); }		// List ũ��
	public void listAdd(ArrayList<Order> order) { this.order = order; }		// ����Ʈ �߰�
	public ArrayList<Order> reArraylist() { return order; } 		// ����Ʈ ��ȯ
	public void classAdd(Order or) { order.add(or); }		// ����Ʈ�� class �߰� (add)
	
}
