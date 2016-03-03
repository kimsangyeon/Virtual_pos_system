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
	public static Activity staffAct;							// table엑티비티 
	
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
	    
	    Time();					// 시간 출력
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
			noon = "오전";
		}
		else 
		{
			noon = "오후";
			curHour -= 12;
		}
		curMinute = c.get(Calendar.MINUTE);		 
		curSecond = c.get(Calendar.SECOND);

		Runnable updater = new Runnable() 
		{	        
			public void run() 
			{  				
				getData.setText(""+curYear+"년 "+curMonth+"월 "+curDay+"일 "+						
						noon+curHour+"시 "+curMinute+"분 "+curSecond+"초 입니다. ");				

				sList = new ArrayList<Staff>();
			    sList = sDb.getStaffList();
					
			    ListView lv = ( ListView ) findViewById ( R.id.staffcon_list );		// 테이블의 주문 메뉴 리스트
		
				StaffListAdapter m = new StaffListAdapter ( sList );
				lv.setAdapter( m );
					
			}
			
		};
		handler.post(updater);
	}
	class ViewHolder // 리스트상의 이미지와 텍스트 클래스
	{
		public ImageView sImgView;
		public TextView sTxtName;
		public TextView sTxtTimemoney;
		public TextView sTxtMoney;
	}

	class StaffListAdapter extends BaseAdapter {
		private ArrayList<Staff> sList = null; // staff의 생성자 리스트

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
			
			if (convertView == null) // 코드상에서 View가 생성되었는지 확인
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
			
			Staff s = sList.get(position); // position에 따른 staff
			
			holder.sTxtName.setText( s.getName()); // 리스트에 이름 넣기
			holder.sTxtTimemoney.setText("시급" + Integer.toString(s.getTimemoney())); // 리스트에  정보넣기
			holder.sTxtMoney.setText("번돈 " + Integer.toString(s.getTotal())); // 리스트에  정보넣기
			
			return convertView;
		}
	}
	
	public void onClickBtnstaffadd(View v) // 매출 버튼
	{
		Intent intent = new Intent(this, StaffaddAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnstaffdel(View v) // 매출 버튼
	{
		Intent intent = new Intent(this, StaffdelAct.class);

		startActivityForResult(intent, 0);
	}
}
