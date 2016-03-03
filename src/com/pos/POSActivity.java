package com.pos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.pos.StaffAct.StaffListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class POSActivity extends Activity implements OnItemClickListener 
{
	private ArrayList<Table> pList = null;			// 테이블 리스트
	private TableAdapter pAdapter = null;			// 테이블 어뎁터
	private StaffCtrol staffct = null;				// StaffCtrol class
	private DBAdapter mDb= null;					// DB
	
	int curYear, curMonth, curDay, curHour, curMinute, curNoon, curSecond;		// 현재 시간 변수
	int cHour, timer;		// 현재시간 변수, 타이머
	Calendar c;				// Calender
	String tag = null;
	String noon = "";
	
	Date curMillis;
	TimerTask second;
	TextView getData;
	
	private final Handler handler = new Handler();		// thread handler
	
	private void Time() 
	{
		getData = (TextView)findViewById(R.id.time_txt);

		second = new TimerTask() 
		{	
			private String tag;

			@Override
			public void run() 
			{
				Update(curSecond);     		// 초당 update
			}
		};     	
		Timer timer = new Timer();
		timer.schedule(second, 0, 1000);	// 1초마다 

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
		if(curNoon == 0)		// 오전, 오후 조건
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
				timer++;
				if(timer == 5)
				{
					staffct = new StaffCtrol();
					staffct.listAdd(mDb.getStaffList());	// DB의 staffList정보 가져옴
					
					for(int i = 1; i<= staffct.arrayNum(); i++)
					{
						Staff s = mDb.getStaffById(i);
						Property property = mDb.getPropertyById(1);		// DB의 재산 정보
						
						if(s.getStarttime() < s.getEndtime())		// 직원의 시작시간과 마감시간에 따른 근무시간 급여
						{
							if(s.getStarttime() <= cHour && cHour < s.getEndtime())
							{
								s.setTotal(s.getTotal() + s.getTimemoney());
							}
						}
						else if(s.getStarttime() > s.getEndtime())
						{
							if(s.getStarttime() <= cHour || cHour < s.getEndtime())
							{
								s.setTotal(s.getTotal() + s.getTimemoney());
							}
						}
						if(s.getFire() == 0)		// 해고가 되지 않은 직원
						{
							property.setStaff(property.getStaff() + s.getTimemoney());			// 직원에게 나가는 지출
							property.setMoney(property.getMoney() - s.getTimemoney());
							mDb.updateProperty(property);
							mDb.updateStaff(s);	
						}
					}
					timer = 0;
				}
			}
		};
		handler.post(updater);
	}
	
	public class Table		// Table class
	{		
		private String table_number;		// table 번호 
	
		public String gettable() { return table_number; }
		public void settable(String table) { this.table_number = table; } 
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Time();		// 현재시간 측정 실행 
		
		mDb = new DBAdapter (this); 
		
		pList = new ArrayList<Table>();			// 리스트 선언

		Table newtable = new Table();

		newtable.settable("1");					// 테이블 추가
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("2");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("3");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("4");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("5");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("6");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("7");
		pList.add(newtable);

		newtable = new Table();
		newtable.settable("8");
		pList.add(newtable);

		ListView lv = (ListView) findViewById(R.id.table_list);		// 테이블 리트스 선언

		pAdapter = new TableAdapter(pList);							// 테이블 추가
		lv.setAdapter(pAdapter);									
		lv.setOnItemClickListener(this);
	}

	class ViewHolder // 리스트 목록
	{
		public ImageView NumImg;			// 리스트 이미지 NUM
		public TextView mkeyword;			// 테이블 리스트 
	}

	class TableAdapter extends BaseAdapter
	{
		private ArrayList<Table> pList = null;	// table의 생성자 리스트

		public TableAdapter(ArrayList<Table> TableList)
		{
			pList = TableList;
		}

		public int getCount() 
		{
			return pList.size();
		}

		public Object getItem(int position) 
		{
			return pList.get(position);
		}

		public long getItemId(int position)
		{
			return 0;
		}

		public View getView(int positon, View convertView, ViewGroup parent) 
		{
			ViewHolder holder;

			if (convertView == null) 
			{
				LayoutInflater inflater = LayoutInflater.from(POSActivity.this);
				convertView = inflater.inflate(R.layout.table_list, parent, false);

				holder = new ViewHolder();

				holder.NumImg = (ImageView) convertView.findViewById(R.id.menu_list_img);
				holder.mkeyword = (TextView) convertView.findViewById(R.id.list_keyword);

				convertView.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) convertView.getTag();
			}

			Table p = pList.get(positon);
			holder.mkeyword.setText("번 테이블");

			switch (positon)		// 테이블 번호에 맞게 이미지 추가
			{
			case 0:
				holder.NumImg.setImageResource(R.drawable.num1);
				break;
			case 1:
				holder.NumImg.setImageResource(R.drawable.num2);
				break;
			case 2:
				holder.NumImg.setImageResource(R.drawable.num3);
				break;
			case 3:
				holder.NumImg.setImageResource(R.drawable.num4);
				break;
			case 4:
				holder.NumImg.setImageResource(R.drawable.num5);
				break;
			case 5:
				holder.NumImg.setImageResource(R.drawable.num6);
				break;
			case 6:
				holder.NumImg.setImageResource(R.drawable.num7);
				break;
			case 7:
				holder.NumImg.setImageResource(R.drawable.num8);
				break;
			case 8:
				holder.NumImg.setImageResource(R.drawable.num9);
				break;
			}
			return convertView;
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 	// 테이블당 클릭 이벤트
	{
		Table t = (Table) pAdapter.getItem(position);

		Toast.makeText(POSActivity.this, "<<" + t.gettable() + ">>", Toast.LENGTH_SHORT).show();	 // TOAST 

		Intent intent = new Intent(this,TableAct.class);		// tableActivity로 이동
		intent.putExtra("isNum", t.gettable());			// 테이블 번호 전달
		
		startActivityForResult(intent, 0);
	}

	public void onClickBtnsale(View v) // 매출 버튼
	{
		Intent intent = new Intent(this, StoresaleAct.class);

		startActivityForResult(intent, 0);
	}

	public void onClickBtnmenu(View v) // 메뉴 버튼
	{
		Intent intent = new Intent(this, MenuControlAct.class);

		startActivityForResult(intent, 0);
	}

	public void onClickBtnstaff(View v) // 직원 버튼
	{
		Intent intent = new Intent(this, StaffAct.class);

		startActivityForResult(intent, 0);
	}
}