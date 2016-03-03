package com.pos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OutmoneyAct extends Activity		// ���� ȭ��
{
	private DBAdapter mDb= null;		// DB
	private MaterialCtrol materialct = null;	// materialCtrol class
	private StaffCtrol staffct = null;		// StaffCtrol class
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.outmoney);
	    
	    mDb = new DBAdapter(this);
	    
	    materialct = new MaterialCtrol();
	    materialct.listAdd(mDb.getMatarialList());		// DB�� ��� ����Ʈ materialctrol�� �߰� 
	    
	    staffct = new StaffCtrol();
	    staffct.listAdd(mDb.getStaffList());		// DB�� ������ ����Ʈ Staffctrol�� �߰� 
	    
	    TextView materialtxt = (TextView) findViewById(R.id.material_outtxt);
	    TextView stafftxt = (TextView) findViewById(R.id.staff_outtxt);
	    TextView totaltxt = (TextView) findViewById(R.id.total_outtxt);
	    
	    int total_outmoney = 0;		// �� ����
	    int material_money = 0;		// ��� �ֹ����
	    int staff_money = 0;		// ���� �޿�

	    Property property = new Property();
	    property = mDb.getPropertyById(1);
	    
	    material_money = property.getMaterial();
	    
	    for(int j = 1; j <= staffct.arrayNum(); j++)
	    {
	    	Staff staff = mDb.getStaffById(j);
	    	staff_money += staff.getTotal();
	    }
	    
	    total_outmoney = material_money + staff_money;
	    
	    materialtxt.setText("��� �ֹ� ��� : " + material_money);
	    stafftxt.setText("�Ƹ�����Ʈ�� �޿� : " + staff_money);
	    totaltxt.setText("�� ���� : " + total_outmoney);
	}

}
