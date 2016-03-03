package com.pos;

public class Order
{
	private int id;		// id
	private int table_id;	// table_id
	private int menu_id;	// menu_id
	private int is_done;	// id_done
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int gettableId() { return table_id; }
	public void settableId(int tableid) { table_id = tableid;}
	
	public int getmenuId() { return menu_id; }
	public void setmenuId(int menuid) { menu_id = menuid; }
	
	public int getDone() { return is_done; }
	public void setDone(int done) { is_done = done; }
}
