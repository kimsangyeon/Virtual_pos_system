package com.pos;

public class Material 
{
	private int id;		//id
	private int menu_id;	// menu�� id
	private String name; // �̸�
	private int price; // ����
	private int num;		// ����

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getmenuId() { return menu_id; }
	public void setmenuId(int menuid) { menu_id = menuid; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
	public int getNum() { return num; }
	public void setNum(int num) { this.num = num; }
}
