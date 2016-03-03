package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoresaleAct extends Activity 	// ���� ���� ȭ��
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
	    property = mDb.getPropertyById(1);		// DB property ����

	    materialct = new MaterialCtrol();
	    materialct.listAdd(mDb.getMatarialList());		// DB ��� ����Ʈ ����

	    int total_money = 0;
	    
	    TextView property_txt = (TextView) findViewById(R.id.property_id);
	    
	    total_money = property.getMoney(); //  property.getMaterial() - property.getStaff();
	    // ��ᰡ�ݰ� ���� �޿��� ������ ���� ���� 
	    property_txt.setText("���� ��� : " + total_money + " ��");
	    
	}
	
	public void onClickBtnInmoney(View v) // ���� ��ư
	{
		Intent intent = new Intent(this, InmoneyAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnOutmoney(View v) // ���� ��ư
	{
		Intent intent = new Intent(this, OutmoneyAct.class);

		startActivityForResult(intent, 0);
	}

}
