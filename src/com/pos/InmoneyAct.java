package com.pos;

import java.util.ArrayList;

import com.pos.OrderAct.MenuListAdapter;
import com.pos.OrderAct.ViewHolder;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class InmoneyAct extends Activity
{
	private DBAdapter mDb = null;						// DB선언
	private ArrayList<Menu> mList = null;				// menuList
	private MenuListAdapter mAdapter = null;			// menuAdapter
	private OrderCtrol orderCtl = null;					// orderCtrol
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inmoney);

		mList = new ArrayList<Menu> ();					// menuList초기화
	    
	    mDb = new DBAdapter ( this );					// DB 초기화
	    mList = mDb.getMenuList();						// DB의 menu 리스트 추가
	    
	    orderCtl = new OrderCtrol();
	    orderCtl.listAdd(mDb.getOrderList());			// DB의 order 리스트 추가
	    
		ListView lv = (ListView) findViewById(R.id.inmoney_id); // 테이블의 주문 메뉴	

		MenuListAdapter m = new MenuListAdapter(mList);		// List 정보를 adapter로
		lv.setAdapter(m);
	}

	class ViewHolder // 리스트상의 이미지와 텍스트 클래스
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtNum;
	}

	class MenuListAdapter extends BaseAdapter {
		private ArrayList<Menu> mList = null; // menu의 생성자 리스트

		public MenuListAdapter(ArrayList<Menu> MenuList) {
			mList = MenuList;
		}

		public int getCount() {
			return mList.size();
		}

		public long getItemId(int position) {
			return 0;
		}

		public Object getItem(int position) {
			return mList.get(position);
		}

		public View getView(int position, View convertView, ViewGroup parent) // position
		{
			ViewHolder holder;
			int count = 0;			// menu의 팔린수를 카운트하는 변수
			
			if (convertView == null) // 코드상에서 View가 생성되었는지 확인
			{
				LayoutInflater inflater = LayoutInflater.from(InmoneyAct.this);
				convertView = inflater.inflate(R.layout.order_low, parent, false);
				holder = new ViewHolder();

				holder.mImgView = (ImageView) convertView.findViewById(R.id.main_row_img);
				holder.mTxtName = (TextView) convertView.findViewById(R.id.main_row_txt_name);
				holder.mTxtPrice = (TextView) convertView.findViewById(R.id.main_row_txt_price);
				holder.mTxtNum = (TextView) convertView.findViewById(R.id.main_row_txt_num);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Menu menu = mList.get(position); 			// menulist의 위치로 정보를 찾아 대입 

			for(int i = 0; i < orderCtl.arrayNum(); i++)		// list 크기많큼 반복
			{
				Order order = orderCtl.reArraylist().get(i);	// order 정보를 가져옴
				
				if(order.getDone() == 1)				// 계산되었는지 확인
				{
					if(order.getmenuId() == menu.getId())		// order 메뉴 Id와 menu의 Id 비교
						count++;
				}		
			}
		
			holder.mTxtName.setText(menu.getName()); // 리스트에 이름 넣기
			holder.mTxtPrice.setText("총 판매 가격 : " + Integer.toString(menu.getPrice() * count)); // 리스트에
			holder.mTxtNum.setText("팔린 개수 : " + count); // 리스트에  정보넣기																	
			
			return convertView;
		}
	}

}
