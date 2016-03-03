package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StaffaddAct extends Activity 		// staff �߰� ȭ��
{
	private DBAdapter mDb = null;		// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.staffadd);
	}
	
	public void onClickBtnstaffaddok (View v)		// �߰� ��ƴ
	{	
    	EditText editname = (EditText) findViewById (R.id.staffaddname_edit);
    	EditText editmoney = (EditText) findViewById (R.id.staffaddmoney_edit);	
    	EditText editstime = (EditText) findViewById (R.id.startime_edit);
    	EditText editetime = (EditText) findViewById (R.id.endtime_edit);
    	
    	Staff staff = new Staff();
    	
    	staff.setName(editname.getText().toString());		// staff �̸�
    	staff.setTimemoney(Integer.parseInt(editmoney.getText().toString()));	// staff �ñ�
    	staff.setStarttime(Integer.parseInt(editstime.getText().toString()));	// ���۽ð�
    	staff.setEndtime(Integer.parseInt(editetime.getText().toString()));		// �����ð�
    	staff.setFire(0);		// �ذ� ����
    	
    	mDb = new DBAdapter(this);
    	mDb.insertStaff(staff);		// DB ���� �߰� 
    	
    	finish ();
        
	}
	
	public void onClickBtnstaffcancel (View v)		// ��� ��ư
	{	   
    	finish ();
	}
}
