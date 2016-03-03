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
import android.widget.AdapterView.OnItemClickListener;

public class MenuControlAct extends Activity implements OnItemClickListener 	// 메뉴 관리 화면
{
	private DBAdapter mDb = null;				// DB 
	private ArrayList<Menu> mList = null;		// menu 리스트
	private MenuListAdapter mAdapter = null;	// menu Adapter
	public static Activity MenuAct;							// Menu엑티비티 
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menucontrol);
	    
	    MenuAct = MenuControlAct.this;			// 현재 menu엑티비티 
	    
	    mList = new ArrayList<Menu> ();
	    
	    mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();			// DB menu List 정보를 가져옴

	    ListView lv = ( ListView ) findViewById ( R.id.menucon_list );		// 테이블의 주문 메뉴 리스트
        
        MenuListAdapter m = new MenuListAdapter ( mList );
        lv.setAdapter( m );
        lv.setOnItemClickListener(this);
	}
	
	class ViewHolder // 리스트상의 이미지와 텍스트 클래스
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtMaterial;
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
				LayoutInflater inflater = LayoutInflater.from(MenuControlAct.this);
				convertView = inflater.inflate(R.layout.menucontrollist, parent, false);
				holder = new ViewHolder();

				holder.mImgView = (ImageView) convertView.findViewById(R.id.main_row_img);
				holder.mTxtName = (TextView) convertView.findViewById(R.id.main_row_txt_name);
				holder.mTxtPrice = (TextView) convertView.findViewById(R.id.main_row_txt_price);
				holder.mTxtMaterial = (TextView) convertView.findViewById(R.id.main_row_txt_material);
				
				convertView.setTag(holder);
			} 
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			Menu m = mList.get(position); // position에 따른 메뉴 정보
			Material material = mDb.getMaterialById(m.getId());		// menu id에 따른 재료의 정보
			
			holder.mTxtName.setText( m.getName()); // 리스트에 이름 넣기
			holder.mTxtPrice.setText("가격 " + Integer.toString(m.getPrice())); // 리스트에  정보넣기
			holder.mTxtMaterial.setText("재료수 " + Integer.toString(material.getNum())); // 리스트에  정보넣기
			
			return convertView;
		}
	}
	
	public void onClickBtnmenuadd(View v) // 메뉴 추가 버튼
	{
		Intent intent = new Intent(this, MenuaddAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnmenudel(View v) // 메뉴 삭제 버튼
	{
		Intent intent = new Intent(this, MenudelAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)	// menuList 클릭 이벤트
	{
		Intent intent = new Intent(this, MenupatchAct.class);
		
		mList = new ArrayList<Menu> ();
		
		mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();
	    
	    Menu menu = mList.get(position);
	    intent.putExtra("menuId", menu.getId());
		
		startActivityForResult(intent, 0);
	}
	
	public void onActivityResult (int requestCode, int resultCode, Intent data)  // setResult에서 저장된 Intent
    {
    	if (resultCode == Activity.RESULT_OK)
    	{
    		Menu m = new Menu ();
    		
    		String menuName = data.getStringExtra("menuName");
    		String menuPrice = data.getStringExtra("menuPrice");
    		
    		m.setName(menuName);
    		m.setPrice(Integer.parseInt(menuPrice));
    	    
    	    mList.add(m);
    	   
    	}
    }
}
