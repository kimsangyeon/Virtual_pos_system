package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenudelAct extends Activity 		// 메뉴 삭제 화면
{
	private DBAdapter mDb = null;		// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menudel);
	    
	    mDb = new DBAdapter(this);
	}

	public void onClickBtnminuok (View v)		// 삭제 버튼
	{	
		MenuControlAct mAtc = (MenuControlAct) MenuControlAct.MenuAct;		// menu엑티비티 
    	EditText editname = (EditText) findViewById (R.id.delname_edit);	
    	  	
    	Intent i = new Intent (this, MenuControlAct.class);
    	Menu m = new Menu();
    	
    	m.setName(editname.getText().toString());
    	
    	mDb.deleteMenu(m);		// menu 삭제 
    	
    	mAtc.finish();			// menu엑티비티 닫기 
    	startActivityForResult(i, 0);		// intent 정보에 따른 Activity 실행
    	finish ();
    	
	}
	
	public void onClickBtndelcancel (View v)	// 취소 버튼
	{	   
    	finish ();
	}
}
