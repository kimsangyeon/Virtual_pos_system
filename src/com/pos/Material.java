package com.pos;

public class Material 
{
	private int id;		//id
	private int menu_id;	// menu의 id
	private String name; // 이름
	private int price; // 가격
	private int num;		// 개수

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
