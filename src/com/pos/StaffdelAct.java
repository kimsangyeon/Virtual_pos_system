package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StaffdelAct extends Activity 		// 직원 해고 화면
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
		StaffAct sAtc = (StaffAct) StaffAct.staffAct;		// 직원 관리 화면
    	EditText editname = (EditText) findViewById (R.id.staffoutname_edit);	
    	  	
    	Intent i = new Intent (this, StaffAct.class);
    	Staff s = new Staff();
    	
    	s = mDb.getStaffByName(editname.getText().toString());		// 직원이름에 따른 DB정보
    	
    	s.setFire(1);		// 해고 여부

    	mDb = new DBAdapter(this);
    	mDb.updateStaff(s);		// DB 직원 업데이트
    	
    	sAtc.finish();		// staff엑티비티 종료
    	startActivityForResult(i, 0);		// intent에 따른 Activity 실행
    	finish ();
    	
	}
	
	public void onClickBtnstaffoutcancel (View v)	// 취소 버튼
	{	   
    	finish ();
	}
}
