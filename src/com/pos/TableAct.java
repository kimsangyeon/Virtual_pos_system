package com.pos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TableAct extends Activity 
{
	private String table_id = null;				// 테이블 id
	private ArrayList<Menu> mList = null;			// 테이블 리스트
	private OrderListAdapter tAdapter = null;			// 테이블 어뎁터
	
	private OrderCtrol orderCtl = null;		// orderctrol class
	private MenuCtrol menuCtl = null; 		// menuctrol class
	private Property property = null;		// property class
	private DBAdapter mDb= null;			//DB
	public static Activity tAct;							// table엑티비티 
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.table);

	    ListView lv = (ListView) findViewById(R.id.table_menu);		// 테이블 리트스 선언
	    Intent recvedIntent = getIntent ();				// getIntent 값을 받아옴    
	    
	    mDb = new DBAdapter (this); 
	    tAct = TableAct.this;
	    table_id = recvedIntent.getStringExtra("isNum");	// 테이블 이름을 받을 변수
	    
	    orderCtl = new OrderCtrol();
	    orderCtl.listAdd(mDb.getOrderList());		// DB 주문 리스트 정보 
	    
        mList = new ArrayList<Menu>();			// 리스트 선언
        mList = mDb.getMenuList();			// DB메뉴 리스트 
        
        menuCtl = new MenuCtrol();
        menuCtl.listAdd(mList);			// menu List 추가 
        
        tAdapter = new OrderListAdapter(mList);							// 테이블 추가
		lv.setAdapter(tAdapter);									
        
	}
	
	class ViewHolder // 리스트상의 이미지와 텍스트 클래스
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtNum;
	}

	class OrderListAdapter extends BaseAdapter {
		private ArrayList<Menu> mList = null; // menu의 생성자 리스트
		
		public OrderListAdapter(ArrayList<Menu> OrderList) { mList = OrderList; }
		public int getCount() { return mList.size(); }
		public long getItemId(int position) { return 0; }
		public Object getItem(int position) { return mList.get(position); }
		
		public View getView(int position, View convertView, ViewGroup parent) // position
		{
			ViewHolder holder;
			int count = 0;
			
			if (convertView == null) // 코드상에서 View가 생성되었는지 확인
			{
				LayoutInflater inflater = LayoutInflater.from(TableAct.this);
				convertView = inflater.inflate(R.layout.order_low, parent, false);
				holder = new ViewHolder();

				holder.mImgView = (ImageView) convertView.findViewById(R.id.main_row_img);
				holder.mTxtName = (TextView) convertView.findViewById(R.id.main_row_txt_name);
				holder.mTxtPrice = (TextView) convertView.findViewById(R.id.main_row_txt_price);
				holder.mTxtNum = (TextView) convertView.findViewById(R.id.main_row_txt_num);

				convertView.setTag(holder);
			} 
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			Menu menu = mList.get(position);	// position에 따른 menu 정보 
			
			for(int i = 0; i < orderCtl.arrayNum(); i++)
			{
				Order order = orderCtl.reArraylist().get(i);
				// order에 저장된 테이블 id와 현재 테이블 id 비교 && 계산된 주문인지 확인
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())	// 주문 menu id와 menu id 비교 
						count++;
				}		
			}
	
			holder.mTxtName.setText(menu.getName()); // 리스트에 이름 넣기
			holder.mTxtPrice.setText("가격" + menu.getPrice()); // 리스트에  정보넣기
			holder.mTxtNum.setText("개수" + count); // 리스트에  정보넣기
			
			return convertView;
		}
	}
	
	
	public void onClickBtnorder(View v) // 주문 화면 버튼
	{
		Intent intent = new Intent(this, OrderAct.class);
		intent.putExtra("isNum", table_id);

		startActivityForResult(intent, 0);
	}

	public void onClickBtncash(View v) // 현금 계산 버튼
	{
		EditText editmoney = (EditText) findViewById(R.id.setmoneyEdit);
		int getmoney = Integer.parseInt(editmoney.getText().toString());
		int gold = 0;			// 계산할 금액
		int survice1 = 0;		// 서비스 1 	(10번째손님 전체 무료)
		int survice2 = 0;		// 서비스 2	(10만원 이상구입 손님 10%할인)
		
		for(int i = 0; i < menuCtl.reArraylist().size(); i++)
		{
			Menu menu = menuCtl.reArraylist().get(i);
			
			for(int j = 0; j < orderCtl.arrayNum(); j++)
			{
				Order order = orderCtl.reArraylist().get(j);
				// order에 저장된 테이블 id와 현재 테이블 id 비교 && 계산된 주문인지 확인
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())	// 주문 menu id와 menu id 비교 
					{
						if(order.getId()%10 == 0 && order.getDone() == 0)	// 10번째 주문 손님일경우 서비스 1
							survice1 = 1;
							
						gold += menu.getPrice();	// 계산금액 
					}
				}		
			}
		}
		
		
		if(gold >= 100000)		// 10만원 이상 구입시
		{
			survice2 = 1;		// 서비스 2
			gold = gold - gold/10;		// 계산금액에서 10% 할인
		}
		
		mDb = new DBAdapter (this);
		
		Property property = new Property();
		property = mDb.getPropertyById(1);
		property.setMoney(property.getMoney() + gold);		// 재산에 계산된금액 더함
		
		if(getmoney < gold || editmoney.getText().toString() == null)	// 지불한 현금이 부족한경우
		{
			Toast.makeText(TableAct.this, "지불하신 금액이 부족합니다", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int remoney = 0;	// 거스름돈

			remoney = getmoney - gold;		// 거스름돈 계산
			
			if(remoney == 0 && survice1 == 0)		// 거스름돈이 없을 경우 
			{
				if(survice2 == 1)		// 서비스 2일경우 
				{
					Toast.makeText(TableAct.this, "계산완료(10%할인됨)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "계산완료", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
			}
			else if(survice1 == 1)		// 서비스 1손님
			{
				Toast.makeText(TableAct.this, "!!!EVENT 10번째 손님입니다!!!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(survice2 == 1)	// 서비스 2일 경우
				{
					Toast.makeText(TableAct.this, "거스름돈(10%할인됨): " + remoney, Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(TableAct.this, "거스름돈: " + remoney, Toast.LENGTH_SHORT).show();
				}
				
			}
			for(int i = 0; i < menuCtl.reArraylist().size(); i++)
			{
				Menu menu = menuCtl.reArraylist().get(i);
				
				for(int j = 0; j < orderCtl.arrayNum(); j++)
				{
					Order order = orderCtl.reArraylist().get(j);
				
					if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
					{
						if(order.getmenuId() == menu.getId())
						{
							order.setDone(1);
							mDb.updateOrder(order);		// 계산된 orderList is_done 여부 변경
						}
					}		
				}
			}
			finish();
		}
	}
	
	public void onClickBtncard(View v) // 카드 계산 버튼
	{
		int gold = 0;
		int survice1 = 0;
		int survice2 = 0;
		
		for(int i = 0; i < menuCtl.reArraylist().size(); i++)
		{
			Menu menu = menuCtl.reArraylist().get(i);
			
			for(int j = 0; j < orderCtl.arrayNum(); j++)
			{
				Order order = orderCtl.reArraylist().get(j);
			
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())
					{
						if(order.getId()%10 == 0)
							survice1 = 1;
						
						gold += menu.getPrice();
					}
				}		
			}
		}
		
		if(gold >= 100000)
		{
			survice2 = 1;
			gold = gold - gold/10;
		}
		
		mDb = new DBAdapter (this);
		
		Property property = new Property();
		property = mDb.getPropertyById(1);
		
		property.setMoney(property.getMoney() + gold);
		
		
		if(survice1 == 1)
		{
			Toast.makeText(TableAct.this, "!!!EVENT 10번째 손님입니다!!!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if(survice2 == 1)
			{
				Toast.makeText(TableAct.this, "계산완료(10%할인됨)", Toast.LENGTH_SHORT).show();
				mDb.updateProperty(property);
			}
			else
			{
				Toast.makeText(TableAct.this, "계산완료", Toast.LENGTH_SHORT).show();
				mDb.updateProperty(property);
			}
		}
		
		for(int i = 0; i < menuCtl.reArraylist().size(); i++)
		{
			Menu menu = menuCtl.reArraylist().get(i);
			
			for(int j = 0; j < orderCtl.arrayNum(); j++)
			{
				Order order = orderCtl.reArraylist().get(j);
			
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())
					{
						order.setDone(1);
						mDb.updateOrder(order);
					}
				}		
			}
		}
		finish();
	}
	
	public void onClickBtnpack(View v) // 포장 계산 버튼
	{
		EditText editmoney = (EditText) findViewById(R.id.setmoneyEdit);
		int getmoney = Integer.parseInt(editmoney.getText().toString());
		int gold = 0;
		int survice1 = 0;
		int survice2 = 0;
		
		for(int i = 0; i < menuCtl.reArraylist().size(); i++)
		{
			Menu menu = menuCtl.reArraylist().get(i);
			
			for(int j = 0; j < orderCtl.arrayNum(); j++)
			{
				Order order = orderCtl.reArraylist().get(j);
			
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())
					{
						if(order.getId()%10 == 0)
							survice1 = 1;
							
						gold += menu.getPrice();
					}
				}		
			}
		}
		
		if(gold >= 100000)
		{
			survice2 = 1;
			gold = gold - gold/10;
		}
		
		mDb = new DBAdapter (this);
		
		gold = gold - 1000;								// 계산 10% 할인
		
		Property property = new Property();
		property = mDb.getPropertyById(1);
		property.setMoney(property.getMoney() + gold);
		
		if(getmoney < gold || editmoney.getText().toString() == null)
		{
			Toast.makeText(TableAct.this, "지불하신 금액이 부족합니다", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int remoney = 0;

			remoney = getmoney - gold;
			if(remoney == 0 && survice1 == 0)
			{
				if(survice2 == 1)
				{
					Toast.makeText(TableAct.this, "계산완료(포장주문 1000원 할인 및 10%할인)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "계산완료(포장주문 1000원 할인)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
			}
			else if(survice1 == 1)
			{
				Toast.makeText(TableAct.this, "!!!EVENT 10번째 손님입니다!!!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(survice2 == 1)
				{
					Toast.makeText(TableAct.this, "거스름돈(포장주문 1000원 할인 및 10%할인)" + remoney, Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "거스름돈(포장주문 1000원할인): " + remoney, Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
			}
			
			for(int i = 0; i < menuCtl.reArraylist().size(); i++)
			{
				Menu menu = menuCtl.reArraylist().get(i);
				
				for(int j = 0; j < orderCtl.arrayNum(); j++)
				{
					Order order = orderCtl.reArraylist().get(j);
				
					if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
					{
						if(order.getmenuId() == menu.getId())
						{
							order.setDone(1);
							mDb.updateOrder(order);
						}
					}		
				}
			}
			finish();
		}
	}
	
}
