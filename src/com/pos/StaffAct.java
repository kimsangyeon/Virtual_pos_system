package com.pos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.pos.OrderAct.MenuListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class StaffAct extends Activity 
{
	private DBAdapter sDb = null;
	private ArrayList<Staff> sList = null;
	private StaffListAdapter sAdapter = null;
	public static Activity staffAct;							// table��Ƽ��Ƽ 
	
	int curYear, curMonth, curDay, curHour, curMinute, curNoon, curSecond;
	int cHour;
	Calendar c;
	String tag = null;
	String noon = "";
	
	Date curMillis;
	TimerTask second;
	TextView getData;
	
	private final Handler handler = new Handler();
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.staff);
	   
	    staffAct = StaffAct.this;
	    
	    sDb = new DBAdapter ( this );
	    
	    Time();					// �ð� ���
	}
	
	private void Time() 
	{
		getData = (TextView)findViewById(R.id.time_txt);

		second = new TimerTask() 
		{	
			private String tag;

			@Override
			public void run() 
			{
				//Log.d(tag, curYear+"."+curMonth+"."+curDay+"."+curHour+":"+curMinute+"."+curSecond); 
				Update(curSecond);     			
			}
		};     	
		Timer timer = new Timer();
		timer.schedule(second, 0, 1000);

	}


	protected void Update(final int i1) 
	{			
		c = Calendar.getInstance();
		curMillis = c.getTime();
		curYear = c.get(Calendar.YEAR);
		curMonth = c.get(Calendar.MONTH)+1;
		curDay = c.get(Calendar.DAY_OF_MONTH);		
		curHour = c.get(Calendar.HOUR_OF_DAY);
		curNoon = c.get(Calendar.AM_PM);
		
		cHour = curHour;
		if(curNoon == 0)
		{
			noon = "����";
		}
		else 
		{
			noon = "����";
			curHour -= 12;
		}
		curMinute = c.get(Calendar.MINUTE);		 
		curSecond = c.get(Calendar.SECOND);

		Runnable updater = new Runnable() 
		{	        
			public void run() 
			{  				
				getData.setText(""+curYear+"�� "+curMonth+"�� "+curDay+"�� "+						
						noon+curHour+"�� "+curMinute+"�� "+curSecond+"�� �Դϴ�. ");				

				sList = new ArrayList<Staff>();
			    sList = sDb.getStaffList();
					
			    ListView lv = ( ListView ) findViewById ( R.id.staffcon_list );		// ���̺��� �ֹ� �޴� ����Ʈ
		
				StaffListAdapter m = new StaffListAdapter ( sList );
				lv.setAdapter( m );
					
			}
			
		};
		handler.post(updater);
	}
	class ViewHolder // ����Ʈ���� �̹����� �ؽ�Ʈ Ŭ����
	{
		public ImageView sImgView;
		public TextView sTxtName;
		public TextView sTxtTimemoney;
		public TextView sTxtMoney;
	}

	class StaffListAdapter extends BaseAdapter {
		private ArrayList<Staff> sList = null; // staff�� ������ ����Ʈ

		public StaffListAdapter(ArrayList<Staff> StaffList) {
			sList = StaffList;
		}

		public int getCount() {
			return sList.size();
		}

		public long getItemId(int position) {
			return 0;
		}

		public Object getItem(int position) {
			return sList.get(position);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) // position
		{
			ViewHolder holder;
			
			if (convertView == null) // �ڵ�󿡼� View�� �����Ǿ����� Ȯ��
			{
				LayoutInflater inflater = LayoutInflater.from(StaffAct.this);
				convertView = inflater.inflate(R.layout.staffcontrollist, parent, false);
				holder = new ViewHolder();

				holder.sImgView = (ImageView) convertView.findViewById(R.id.main_row_img);
				holder.sTxtName = (TextView) convertView.findViewById(R.id.main_row_txt_name);
				holder.sTxtTimemoney = (TextView) convertView.findViewById(R.id.main_row_txt_price);
				holder.sTxtMoney = (TextView) convertView.findViewById(R.id.main_row_txt_material);
			
				convertView.setTag(holder);
				
			} 
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			Staff s = sList.get(position); // position�� ���� staff
			
			holder.sTxtName.setText( s.getName()); // ����Ʈ�� �̸� �ֱ�
			holder.sTxtTimemoney.setText("�ñ�" + Integer.toString(s.getTimemoney())); // ����Ʈ��  �����ֱ�
			holder.sTxtMoney.setText("���� " + Integer.toString(s.getTotal())); // ����Ʈ��  �����ֱ�
			
			return convertView;
		}
	}
	
	public void onClickBtnstaffadd(View v) // ���� ��ư
	{
		Intent intent = new Intent(this, StaffaddAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnstaffdel(View v) // ���� ��ư
	{
		Intent intent = new Intent(this, StaffdelAct.class);

		startActivityForResult(intent, 0);
	}
}
