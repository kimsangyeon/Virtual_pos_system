package com.pos;

public class Property		// property class
{
	private int id;		// id
	private int material; 	// 재료
	private int staff; 	// 직원
	private int money; // 총 재산 

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getStaff() { return staff; }
	public void setStaff(int staff) { this.staff = staff; }

	public int getMaterial() { return material; }
	public void setMaterial(int material) { this.material = material; }
	
	public int getMoney() { return money; }
	public void setMoney(int money) { this.money = money; }	
}
