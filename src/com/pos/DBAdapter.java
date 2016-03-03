package com.pos;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

// SQLite3 데이터베이스에 연결하기 위한 어댑터 클래스
public class DBAdapter extends SQLiteOpenHelper 
{
	private SQLiteDatabase db;
	
	public static final int ALL = -1;

	// DB이름
	private static final String DB_NAME = "pos.db";
	private static final int VERSION = 1;
	
	public DBAdapter ( Context context )
	{
		super ( context , DB_NAME , null , VERSION );
		db = this.getWritableDatabase();
	}
	
	@Override
	public synchronized void close ()
	{
		db.close ();
		super.close ();
	}
	
	@Override
	protected void finalize () throws Throwable
	{
		close ();
		super.finalize();
	}

	@Override
	public void onCreate ( SQLiteDatabase db )
	{
		String menuQuery = 
				" CREATE TABLE menu " + 								//menu table 생성
						" (id INTEGER PRIMARY KEY AUTOINCREMENT , " + 
						" 	name VARCHAR(30) , " + 
						" 	price INTEGER ); ";
		
		String orderQuery =
				" CREATE TABLE torder " +								// order table 생성
						" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						" table_id INTEGER ," +
						" menu_id INTEGER, " +
						" is_done INTEGER );";
		
		String propertyQuery =
				" CREATE TABLE property " +								// property table 생성
						" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						" material INTEGER , " +
						" staff INTEGER , " +
						" money INTEGER );";
		
		String materialQuery = 
				" CREATE TABLE material " + 							// material table 생성
						" (id INTEGER PRIMARY KEY AUTOINCREMENT , " + 
						" 	menu_id INTEGER, " +
						" 	name VARCHAR(30) , " + 
						" 	price INTEGER ," +
						"   num INTEGER ); ";
		
		String staffQuery = 
				" CREATE TABLE staff " + 								//staff table 생성
						" (id INTEGER PRIMARY KEY AUTOINCREMENT , " + 
						" 	name VARCHAR(30) , " + 
						" 	money INTEGER , " +
						"   starttime INTEGER , " +
						"   endtime INTEGER , " +
						"   total INTEGER , " +
						"   fire INTEGER ); ";								
		
		db.execSQL( menuQuery );
		db.execSQL( orderQuery );
		db.execSQL( propertyQuery );
		db.execSQL( materialQuery );
		db.execSQL( staffQuery );
	}

	public Menu getMenuById ( int id )						// menu에 관한 정보 반환
	{
			Menu ret = new Menu();
			String query = "select * from menu WHERE id =" + 
					        Integer.toString(id) + ";" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
				Menu m = new Menu ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxPrice = c.getColumnIndex("price");
				
				m.setId(c.getInt(idxId));
				m.setName( c.getString(idxName));
				m.setPrice(c.getInt(idxPrice));
				
				ret = m;
			}

		c.close();
		
		return ret;
	}
	
	public Menu getMenuByName (String name)		// menu에 관한 정보 반환
	{
			Menu ret = new Menu();
			String query = "select * from menu WHERE name = " + 
					       " '" + name + "';" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
				Menu m = new Menu ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxPrice = c.getColumnIndex("price");
				
				m.setId(c.getInt(idxId));
				m.setName( c.getString(idxName));
				m.setPrice(c.getInt(idxPrice));
				
				ret = m;
			}

		c.close();
		
		return ret;
	}
	
	public Staff getStaffById ( int id )		// staff에 관한 정보 반환
	{
		Staff ret = new Staff();
			String query = "select * from staff WHERE id =" + 
					        Integer.toString(id) + ";" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
				Staff s = new Staff ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxMoney = c.getColumnIndex("money");
				final int idxStarttime = c.getColumnIndex("starttime");
				final int idxEndtime = c.getColumnIndex("endtime");
				final int idxTotal = c.getColumnIndex("total");
				final int idxFire = c.getColumnIndex("fire");
				
				s.setId(c.getInt(idxId));
				s.setName( c.getString(idxName));
				s.setTimemoney(c.getInt(idxMoney));
				s.setStarttime( c.getInt(idxStarttime));
				s.setEndtime( c.getInt(idxEndtime));
				s.setTotal( c.getInt(idxTotal));
				s.setFire( c.getInt(idxFire));
				
				ret = s;
			}

		c.close();
		
		return ret;
	}
	
	public Staff getStaffByName ( String name )			// staff에 관한 정보 반환
	{
			Staff ret = new Staff();
			String query = "select * from staff WHERE name = " + 
						   " '" + name + "';" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
				Staff s = new Staff ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxMoney = c.getColumnIndex("money");
				final int idxStarttime = c.getColumnIndex("starttime");
				final int idxEndtime = c.getColumnIndex("endtime");
				final int idxTotal = c.getColumnIndex("total");
				final int idxFire = c.getColumnIndex("fire");
				
				s.setId(c.getInt(idxId));
				s.setName( c.getString(idxName));
				s.setTimemoney(c.getInt(idxMoney));
				s.setStarttime( c.getInt(idxStarttime));
				s.setEndtime( c.getInt(idxEndtime));
				s.setTotal( c.getInt(idxTotal));
				s.setFire( c.getInt(idxFire));
				
				ret = s;
			}

		c.close();
		
		return ret;
	}
	
	
	public Order getOrderById ( int id )				// order에 관한 정보 반환
	{
			Order ret = new Order();
			String query = "select * from torder WHERE id =" + 
							Integer.toString(id) + ";" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
					Order or = new Order();
	
					final int idxId = c.getColumnIndex("id");
					final int idxtableId = c.getColumnIndex("table_id");
					final int idxmenuId = c.getColumnIndex("menu_id");
					final int idxisDone = c.getColumnIndex("is_done");

					or.setId( c.getInt(idxId) );
					or.settableId( c.getInt(idxtableId));
					or.setmenuId(c.getInt(idxmenuId));
					or.setDone(c.getInt(idxisDone));
					
					ret = or;
			}

		c.close();
		
		return ret;
	}
	
	public Property getPropertyById ( int id )					// property에 관한 정보 반환
	{
			Property ret = new Property();
			String query = "select * from property WHERE id =" + 
					        id + ";" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
					Property p = new Property();
	
					final int idxId = c.getColumnIndex("id");
					final int idxMaterial = c.getColumnIndex("material");
					final int idxStaff = c.getColumnIndex("staff");
					final int idxProperty = c.getColumnIndex("money");

					p.setId( c.getInt(idxId) );
					p.setMaterial(c.getInt(idxMaterial));
					p.setStaff(c.getInt(idxStaff));
					p.setMoney(c.getInt(idxProperty));
					
					ret = p;
			}

		c.close();
		
		return ret;
	}
	
	public Material getMaterialById ( int id )				// material에 관한 정보 반환
	{
		Material ret = new Material();
			String query = "select * from material WHERE menu_id =" + 
					        Integer.toString(id) + ";" ;
			
			Cursor c = db.rawQuery(query, null);
			
			if (c.moveToFirst())
			{
				Material m = new Material ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxmenuId = c.getColumnIndex("menu_id");
				final int idxName = c.getColumnIndex("name");
				final int idxPrice = c.getColumnIndex("price");
				final int idxNum = c.getColumnIndex("num");
				
				m.setId(c.getInt(idxId));
				m.setmenuId(c.getInt(idxmenuId));
				m.setName( c.getString(idxName));
				m.setPrice(c.getInt(idxPrice));
				m.setNum(c.getInt(idxNum));
				
				ret = m;
			}

		c.close();
		
		return ret;
	}
	
	public ArrayList<Menu> getMenuList ()					// menuList에 관한 정보 반환
	{
		ArrayList<Menu> ret = new ArrayList<Menu> ();
		String sql = "select * from menu;";
		Cursor c = db.rawQuery(sql, null);
		
		if (c.moveToFirst())
		{
			do
			{
				Menu m = new Menu ();
	
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxPrice = c.getColumnIndex("price");
				
				m.setId(c.getInt(idxId));
				m.setName( c.getString(idxName));
				m.setPrice(c.getInt(idxPrice));
				
				ret.add(m);
			}
			while(c.moveToNext());
		}
		c.close();

		return ret;
	}
	
	public ArrayList<Staff> getStaffList ()			// staffList에 관한 정보 반환
	{
		
		ArrayList<Staff> ret = new ArrayList<Staff> ();
		
		String sql = "select * from staff;";
		Cursor c = db.rawQuery(sql, null);
		
		if (c.moveToFirst())
		{
			do
			{
				Staff s = new Staff ();
				
				final int idxId = c.getColumnIndex("id");
				final int idxName = c.getColumnIndex("name");
				final int idxMoney = c.getColumnIndex("money");
				final int idxStarttime = c.getColumnIndex("starttime");
				final int idxEndtime = c.getColumnIndex("endtime");
				final int idxTotal = c.getColumnIndex("total");
				final int idxFire = c.getColumnIndex("fire");
				
				s.setId(c.getInt(idxId));
				s.setName( c.getString(idxName));
				s.setTimemoney(c.getInt(idxMoney));
				s.setStarttime( c.getInt(idxStarttime));
				s.setEndtime( c.getInt(idxEndtime));
				s.setTotal( c.getInt(idxTotal));
				s.setFire( c.getInt(idxFire));
				
				ret.add(s);

			}
			while(c.moveToNext());
		}

		c.close();

		return ret;
	}
	
	public ArrayList<Material> getMatarialList ()			// matrialList에 관한 정보 반환
	{
		
		ArrayList<Material> ret = new ArrayList<Material> ();
		
		String sql = "select * from material;";
		Cursor c = db.rawQuery(sql, null);
		
		if (c.moveToFirst())
		{
			do
			{
				Material m = new Material();
	
				final int idxId = c.getColumnIndex("id");
				final int idxmenuId = c.getColumnIndex("menu_id");
				final int idxName = c.getColumnIndex("name");
				final int idxPrice = c.getColumnIndex("price");
				final int idxNum = c.getColumnIndex("num");
				
				m.setId(c.getInt(idxId));
				m.setmenuId(c.getInt(idxmenuId));
				m.setName( c.getString(idxName));
				m.setPrice(c.getInt(idxPrice));
				m.setNum(c.getInt(idxNum));
				
				ret.add(m);
				

			}
			while(c.moveToNext());
		}

		c.close();

		return ret;
	}
	
	public ArrayList<Order> getOrderList ()					// orderList에 관한 정보 반환
	{
		
		ArrayList<Order> ret = new ArrayList<Order> ();
		
		String sql = "select * from torder;";
		Cursor c = db.rawQuery(sql, null);
		
		if (c.moveToFirst())
		{
			do
			{
				Order or = new Order();
				
				final int idxId = c.getColumnIndex("id");
				final int idxtableId = c.getColumnIndex("table_id");
				final int idxmenuId = c.getColumnIndex("menu_id");
				final int idxisDone = c.getColumnIndex("is_done");

				or.setId( c.getInt(idxId) );
				or.settableId( c.getInt(idxtableId));
				or.setmenuId(c.getInt(idxmenuId));
				or.setDone(c.getInt(idxisDone));
				
				ret.add(or);
			}
			while(c.moveToNext());
		}

		c.close();

		return ret;
	}
		
	public void insertOrder (Order or)				// order 정보 추가
	{
		String insertQuery = "" +
				"INSERT INTO torder" +
				"(table_id , menu_id , is_done)" +
				"VALUES" +
				"( " + Integer.toString(or.gettableId()) + " , " +
				" " + Integer.toString(or.getmenuId()) + " , " + 
				"  " + Integer.toString(or.getDone()) + ");";

		db.execSQL(insertQuery);
	}
	
	public void insertMenu (Menu m)					// menu 정보 추가
	{
		String insertQuery = "" +
				"INSERT INTO menu" +
				"(name, price)" +
				"VALUES" +
				"( '" + m.getName() + "' , " +
				" " + Integer.toString(m.getPrice()) + ");";

		db.execSQL(insertQuery);
	}
	
	public void insertStaff (Staff s)				// staff 정보 추가
	{
		String insertQuery = "" +
				"INSERT INTO staff" +
				"(name, money, starttime, endtime, total)" +
				"VALUES" +
				"( '" + s.getName() + "' , " +
				" " + Integer.toString(s.getTimemoney()) + " , " + 
				" " + Integer.toString(s.getStarttime()) + " , " + 
				" " + Integer.toString(s.getEndtime()) + " , " + 
				" " + Integer.toString(s.getTimemoney()) + ");";

		db.execSQL(insertQuery);
	}
	
	public void insertMaterial (Material ma)		// material 정보 추가
	{
		String insertQuery = "" +
				"INSERT INTO material" +
				"(menu_id, name, price, num)" +
				"VALUES" +
				"( " + Integer.toString(ma.getmenuId()) + ", " +
				" '" + ma.getName() + "', " +
				" " + Integer.toString(ma.getPrice()) +  ", " +
				" " + Integer.toString(ma.getNum()) + ");";

		db.execSQL(insertQuery);
	}
	
	public void deleteMenu(Menu m) 		// menu 정보 삭제
	{
		String deleteQuery = "" +
				" DELETE FROM menu " +
				" WHERE name = " +
				" '" + m.getName() + "' ;";
		
		db.execSQL(deleteQuery);
		
	}
	
	public void updateMenu (Menu m)			// menu 정보 수정
	{
		String menuQuery = "" +
				" update menu set" +
				" name = '" + m.getName() + "' , " +
				" price = " + Integer.toString(m.getPrice()) + " " + 
				" where id = " + "'" +  m.getId() + "'" + ";";

		db.execSQL(menuQuery);
	}	
	
	public void updateStaff (Staff s)		// staff 정보 수정	
	{
		String staffQuery = "" +
				" update staff set" +
				" name = '" + s.getName() + "' , " +
				" money = " + Integer.toString(s.getTimemoney()) + " , " + 
				" starttime = " + Integer.toString(s.getStarttime()) + " , " + 
				" endtime = " + Integer.toString(s.getEndtime()) + " , " + 
				" total = " + Integer.toString(s.getTotal()) + ", " + 
				" fire = " + Integer.toString(s.getFire()) + " " + 
				" where id = " + "'" +  s.getId() + "'" + ";";

		db.execSQL(staffQuery);
	}	
	
	public void updateOrder (Order or)			// order 정보 수정
	{
		String orderQuery = "" +
				" update torder set" +
				" is_done = " + or.getDone() + 
				" where id = " + "'" +  or.getId() + "'" + ";";

		db.execSQL(orderQuery);
	}	
	
	public void updateProperty (Property p)		// property 정보 수정
	{
		String propertyQuery = "" +
				" update property set" +
				" material = " + p.getMaterial() + " , " +
				" staff = " + p.getStaff() + " , " +
				" money = " + p.getMoney() + 
				" where id = " + "'" +  p.getId() + "'" + ";";

		db.execSQL(propertyQuery);
	}	
	
	public void updateMaterial (Material m)		// material 정보 수정
	{
		String materialQuery = "" +
				" update material set" +
				" price = " + Integer.toString(m.getPrice()) + ", " +
				" num = " + Integer.toString(m.getNum()) + " " +
				" where id = " + "'" +  m.getId() + "'" + ";";

		db.execSQL(materialQuery);
	}	
	@Override
	public void onUpgrade ( SQLiteDatabase db , int oldVersion , int newVersion )
	{
		// TABLE DELETION QUERY HERE!!
		//db.execSQL( "drop table if exists people;");
		onCreate (db);
	}

	
	
}
