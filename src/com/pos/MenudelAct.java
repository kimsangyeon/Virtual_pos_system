package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenudelAct extends Activity 		// �޴� ���� ȭ��
{
	private DBAdapter mDb = null;		// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menudel);
	    
	    mDb = new DBAdapter(this);
	}

	public void onClickBtnminuok (View v)		// ���� ��ư
	{	
		MenuControlAct mAtc = (MenuControlAct) MenuControlAct.MenuAct;		// menu��Ƽ��Ƽ 
    	EditText editname = (EditText) findViewById (R.id.delname_edit);	
    	  	
    	Intent i = new Intent (this, MenuControlAct.class);
    	Menu m = new Menu();
    	
    	m.setName(editname.getText().toString());
    	
    	mDb.deleteMenu(m);		// menu ���� 
    	
    	mAtc.finish();			// menu��Ƽ��Ƽ �ݱ� 
    	startActivityForResult(i, 0);		// intent ������ ���� Activity ����
    	finish ();
    	
	}
	
	public void onClickBtndelcancel (View v)	// ��� ��ư
	{	   
    	finish ();
	}
}
