package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenuaddAct extends Activity		// 메뉴 추가 화면
{	
	private DBAdapter mDb = null;			// DB 선언
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menuadd);
	}
	
	public void onClickBtnaddok (View v)		// 메뉴 추가 버튼
	{	
    	EditText editname = (EditText) findViewById (R.id.addname_edit);
    	EditText editprice = (EditText) findViewById (R.id.addprice_edit);	
    	  	
    	Intent i = new Intent ();			// intent 인자 생성
    	Menu m = new Menu();			// menu class
    	Material material = new Material();		// material class
    	
    	m.setName(editname.getText().toString());		// editname의 입력된 이름 menu class 대입
    	m.setPrice(Integer.parseInt(editprice.getText().toString()));	// editprice에 입력된 가격 menu class 대입
    	
    	mDb = new DBAdapter(this);
    	mDb.insertMenu(m);			// DB에 menu 추가
    	
    	String m_name = editname.getText().toString().concat("m");		// menu 재료 이름 표시
    	int m_price = Integer.parseInt(editprice.getText().toString())/10;		// material의 가격은 menu가격의 10%
    
    	m = mDb.getMenuByName(m.getName());			// DB의 menu 불러옴
    	
    	material.setmenuId(m.getId());		// material class에 menu id 대입
    	material.setName(m_name);			// material class 재료 이름 대입
    	material.setPrice(m_price);			// material class 재료 가격 대입
    	material.setNum(0);					// material class 재료 개수 대입
	
    	mDb.insertMaterial(material);		// material DB 추가
    	
    	i.putExtra("menuName", editname.getText().toString());		// 추가된 menu이름 정보를 intent저장
    	i.putExtra("menuPrice", editprice.getText().toString());	// 추가된 menu이름 정보를 intent저장
    	
    	setResult (Activity.RESULT_OK, i);   	// Activity result_ok로 intent 인자를 넘김

    	finish ();
        
	}
	
	public void onClickBtncancel (View v)		// 취소버튼
	{	   
    	finish ();
	}
}
