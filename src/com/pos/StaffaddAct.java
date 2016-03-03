package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StaffaddAct extends Activity 		// staff 추가 화면
{
	private DBAdapter mDb = null;		// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.staffadd);
	}
	
	public void onClickBtnstaffaddok (View v)		// 추가 버틈
	{	
    	EditText editname = (EditText) findViewById (R.id.staffaddname_edit);
    	EditText editmoney = (EditText) findViewById (R.id.staffaddmoney_edit);	
    	EditText editstime = (EditText) findViewById (R.id.startime_edit);
    	EditText editetime = (EditText) findViewById (R.id.endtime_edit);
    	
    	Staff staff = new Staff();
    	
    	staff.setName(editname.getText().toString());		// staff 이름
    	staff.setTimemoney(Integer.parseInt(editmoney.getText().toString()));	// staff 시급
    	staff.setStarttime(Integer.parseInt(editstime.getText().toString()));	// 시작시간
    	staff.setEndtime(Integer.parseInt(editetime.getText().toString()));		// 마감시간
    	staff.setFire(0);		// 해고 여부
    	
    	mDb = new DBAdapter(this);
    	mDb.insertStaff(staff);		// DB 직원 추가 
    	
    	finish ();
        
	}
	
	public void onClickBtnstaffcancel (View v)		// 취소 버튼
	{	   
    	finish ();
	}
}
