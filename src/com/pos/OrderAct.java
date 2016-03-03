package com.pos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class OrderAct extends Activity implements OnItemClickListener 	// 주문 화면
{
	private DBAdapter mDb = null;			// DB
	private ArrayList<Menu> mList = null;	// menu List
	private MenuListAdapter mAdapter = null;	// menu Adapter
	private String table_id = null;		// table id
	
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.order);
	    
	    Intent recvedIntent = getIntent ();							// getIntent 값을 받아옴    
        table_id = recvedIntent.getStringExtra("isNum");	// 테이블 이름을 받을 변수
	    
	    mList = new ArrayList<Menu> ();		
	    
	    mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();		// DB의 menuList 정보 가져옴

	    ListView lv = ( ListView ) findViewById ( R.id.order_menu );		// 테이블의 주문 메뉴 리스트
        
        MenuListAdapter m = new MenuListAdapter ( mList );
        lv.setAdapter( m );
        lv.setOnItemClickListener(this);
	}
	
	class ViewHolder // 리스트상의 이미지와 텍스트 클래스
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
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

			if (convertView == null) // 코드상에서 View가 생성되었는지 확인
			{
				LayoutInflater inflater = LayoutInflater.from(OrderAct.this);
				convertView = inflater.inflate(R.layout.menu, parent, false);
				holder = new ViewHolder();

				holder.mImgView = (ImageView) convertView.findViewById(R.id.main_row_img);
				holder.mTxtName = (TextView) convertView.findViewById(R.id.main_row_txt_name);
				holder.mTxtPrice = (TextView) convertView.findViewById(R.id.main_row_txt_price);

				convertView.setTag(holder);
			} 
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			Menu m = mList.get(position); // position에 따른 menu의 정보
	
			holder.mTxtName.setText( m.getName()); // 리스트에 이름 넣기
			holder.mTxtPrice.setText("가격" + Integer.toString(m.getPrice())); // 리스트에  정보넣기

			return convertView;
		}
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)	// 테이블당 클릭 이벤트
	{
		TableAct mAct = (TableAct) TableAct.tAct;
		Order order = new Order();
		mList = new ArrayList<Menu> ();
		
		mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();
	    
	    Menu menu = mList.get(position);
	    Material material = mDb.getMaterialById(menu.getId());
	    
	    if(material.getNum() == 0)		// 재료가 없을 경우 경고 창 출력
	    {
	    	Toast.makeText(OrderAct.this, "<<" + menu.getName() + "의 재료가 부족합니다.!!>>" + "\n\n" + "-재료를 주문하시오-", Toast.LENGTH_SHORT).show();	 // TOAST 
	    	finish();
	    }
	    else if(material.getNum() != 0)		// 재료가 있을경우
	    {
	    	order.settableId(Integer.parseInt(table_id));
	    	order.setmenuId(menu.getId());
	    	order.setDone(0);
	    
	    	material.setNum(material.getNum() - 1);		// 재료수 감소
	    	
	    	mDb.insertOrder(order);			// order 추가 
	    	mDb.updateMaterial(material);	// DB 재료 정보 수정
	    	
	    	mAct.finish();		// table엑티비티 종료
	    	finish();
	 
	    	Intent intent = new Intent(this, TableAct.class);
	    	intent.putExtra("isNum", table_id);			// 테이블 번호 전달
	    	startActivityForResult(intent, 0);		// intent에 따른 Activity 시작
	    }
	}

}
