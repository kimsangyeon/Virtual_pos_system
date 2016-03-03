package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenupatchAct extends Activity 		// 메뉴 수정 화면
{
	private DBAdapter mDb = null;		// DB
	
    public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menupatch);
	   
	    Intent intent = new Intent(getIntent());		// intent 인자
	   
	    EditText editname = (EditText) findViewById(R.id.patchname_edit);
	    EditText editprice = (EditText) findViewById(R.id.patchprice_edit);
	    
	    Menu m = new Menu();
	    
	    int menuId = intent.getIntExtra("menuId" , 0);		// menuId 정보 get
	  
	    mDb = new DBAdapter(this);
	    m = mDb.getMenuById(menuId);		// DB의 menuID에 따른 menu 
	    
	    editname.setText(m.getName());		// edit text 내용 넣기
	    editprice.setText(Integer.toString(m.getPrice()));
	}

	public void onClickBtnpatchok (View v)		// 수정 버튼
	{	  	
		MenuControlAct mAtc = (MenuControlAct) MenuControlAct.MenuAct;
    	Intent i = new Intent (getIntent());
    	Intent ipatch = new Intent (this, MenuControlAct.class);
    	Menu m = new Menu();
    	Material material = new Material();
    	
    	EditText editname = (EditText) findViewById(R.id.patchname_edit);
        EditText editprice = (EditText) findViewById(R.id.patchprice_edit);
        
        mDb = new DBAdapter(this);
    	
        m = mDb.getMenuById(i.getIntExtra("menuId" , 0));
    	m.setName(editname.getText().toString());
    	m.setPrice(Integer.parseInt(editprice.getText().toString()));
    	
    	material = mDb.getMaterialById(m.getId());
    	material.setPrice(m.getPrice()/10);
    	
    	mDb.updateMenu(m);
    	mDb.updateMaterial(material);
    	
    	mAtc.finish();
    	startActivityForResult(ipatch, 0);
    	finish ();
    	
	}
	
	public void onClickBtnpatchcl (View v)		// 취소버튼
	{	   
    	finish ();
	}
	
	public void onClickBtnmaterialok (View v)	// 재료 주문 버튼
	{	   
		MenuControlAct mAtc = (MenuControlAct) MenuControlAct.MenuAct;
		Intent i = new Intent (getIntent());
		Intent ipatch = new Intent (this, MenuControlAct.class);
		
		EditText editmaterial = (EditText) findViewById(R.id.material_edit);
		Material material = new Material();
		Menu m = new Menu();
		Property  property = new Property();
		
		mDb = new DBAdapter(this);
		
		m = mDb.getMenuById(i.getIntExtra("menuId" , 0));
		property = mDb.getPropertyById(1);
		
		material = mDb.getMaterialById(m.getId());
		material.setNum(Integer.parseInt(editmaterial.getText().toString()) + material.getNum());

		property.setMoney(property.getMoney() - (material.getPrice() * material.getNum()));
		property.setMaterial(property.getMaterial() + material.getPrice() * material.getNum());
		
		mDb.updateMaterial(material);
		mDb.updateProperty(property);
		
		mAtc.finish();
    	startActivityForResult(ipatch, 0);
    	finish ();
	}
}
