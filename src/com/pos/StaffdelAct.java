package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StaffdelAct extends Activity 		// ���� �ذ� ȭ��
{
	private DBAdapter mDb = null;	// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.staffdel);

	    mDb = new DBAdapter(this);
	}

	public void onClickBtnstaffoutok (View v)
	{	
		StaffAct sAtc = (StaffAct) StaffAct.staffAct;		// ���� ���� ȭ��
    	EditText editname = (EditText) findViewById (R.id.staffoutname_edit);	
    	  	
    	Intent i = new Intent (this, StaffAct.class);
    	Staff s = new Staff();
    	
    	s = mDb.getStaffByName(editname.getText().toString());		// �����̸��� ���� DB����
    	
    	s.setFire(1);		// �ذ� ����

    	mDb = new DBAdapter(this);
    	mDb.updateStaff(s);		// DB ���� ������Ʈ
    	
    	sAtc.finish();		// staff��Ƽ��Ƽ ����
    	startActivityForResult(i, 0);		// intent�� ���� Activity ����
    	finish ();
    	
	}
	
	public void onClickBtnstaffoutcancel (View v)	// ��� ��ư
	{	   
    	finish ();
	}
}
