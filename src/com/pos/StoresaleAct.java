package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoresaleAct extends Activity 	// 매출 정보 화면
{
	private Property property = null;	// property class
	private StaffCtrol staffct = null;	// staffCtrol class
	private MaterialCtrol materialct = null;   // materilctrol class
	private DBAdapter mDb= null;		// DB
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sale);
	    
	    mDb = new DBAdapter(this);
	   
	    property = new Property();
	    property = mDb.getPropertyById(1);		// DB property 정보

	    materialct = new MaterialCtrol();
	    materialct.listAdd(mDb.getMatarialList());		// DB 재료 리스트 정보

	    int total_money = 0;
	    
	    TextView property_txt = (TextView) findViewById(R.id.property_id);
	    
	    total_money = property.getMoney(); //  property.getMaterial() - property.getStaff();
	    // 재료가격과 직원 급여를 제외한 가게 개산 
	    property_txt.setText("가게 재산 : " + total_money + " 원");
	    
	}
	
	public void onClickBtnInmoney(View v) // 수입 버튼
	{
		Intent intent = new Intent(this, InmoneyAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnOutmoney(View v) // 지출 버튼
	{
		Intent intent = new Intent(this, OutmoneyAct.class);

		startActivityForResult(intent, 0);
	}

}
