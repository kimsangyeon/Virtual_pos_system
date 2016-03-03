package com.pos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OutmoneyAct extends Activity		// 지출 화면
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
	    materialct.listAdd(mDb.getMatarialList());		// DB의 재료 리스트 materialctrol에 추가 
	    
	    staffct = new StaffCtrol();
	    staffct.listAdd(mDb.getStaffList());		// DB의 스태프 리스트 Staffctrol에 추가 
	    
	    TextView materialtxt = (TextView) findViewById(R.id.material_outtxt);
	    TextView stafftxt = (TextView) findViewById(R.id.staff_outtxt);
	    TextView totaltxt = (TextView) findViewById(R.id.total_outtxt);
	    
	    int total_outmoney = 0;		// 총 지출
	    int material_money = 0;		// 재료 주문비용
	    int staff_money = 0;		// 직원 급여

	    Property property = new Property();
	    property = mDb.getPropertyById(1);
	    
	    material_money = property.getMaterial();
	    
	    for(int j = 1; j <= staffct.arrayNum(); j++)
	    {
	    	Staff staff = mDb.getStaffById(j);
	    	staff_money += staff.getTotal();
	    }
	    
	    total_outmoney = material_money + staff_money;
	    
	    materialtxt.setText("재료 주문 비용 : " + material_money);
	    stafftxt.setText("아르바이트생 급여 : " + staff_money);
	    totaltxt.setText("총 지출 : " + total_outmoney);
	}

}
