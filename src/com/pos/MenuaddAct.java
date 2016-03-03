package com.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenuaddAct extends Activity		// �޴� �߰� ȭ��
{	
	private DBAdapter mDb = null;			// DB ����
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menuadd);
	}
	
	public void onClickBtnaddok (View v)		// �޴� �߰� ��ư
	{	
    	EditText editname = (EditText) findViewById (R.id.addname_edit);
    	EditText editprice = (EditText) findViewById (R.id.addprice_edit);	
    	  	
    	Intent i = new Intent ();			// intent ���� ����
    	Menu m = new Menu();			// menu class
    	Material material = new Material();		// material class
    	
    	m.setName(editname.getText().toString());		// editname�� �Էµ� �̸� menu class ����
    	m.setPrice(Integer.parseInt(editprice.getText().toString()));	// editprice�� �Էµ� ���� menu class ����
    	
    	mDb = new DBAdapter(this);
    	mDb.insertMenu(m);			// DB�� menu �߰�
    	
    	String m_name = editname.getText().toString().concat("m");		// menu ��� �̸� ǥ��
    	int m_price = Integer.parseInt(editprice.getText().toString())/10;		// material�� ������ menu������ 10%
    
    	m = mDb.getMenuByName(m.getName());			// DB�� menu �ҷ���
    	
    	material.setmenuId(m.getId());		// material class�� menu id ����
    	material.setName(m_name);			// material class ��� �̸� ����
    	material.setPrice(m_price);			// material class ��� ���� ����
    	material.setNum(0);					// material class ��� ���� ����
	
    	mDb.insertMaterial(material);		// material DB �߰�
    	
    	i.putExtra("menuName", editname.getText().toString());		// �߰��� menu�̸� ������ intent����
    	i.putExtra("menuPrice", editprice.getText().toString());	// �߰��� menu�̸� ������ intent����
    	
    	setResult (Activity.RESULT_OK, i);   	// Activity result_ok�� intent ���ڸ� �ѱ�

    	finish ();
        
	}
	
	public void onClickBtncancel (View v)		// ��ҹ�ư
	{	   
    	finish ();
	}
}
