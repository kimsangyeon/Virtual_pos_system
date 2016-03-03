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

public class MenuControlAct extends Activity implements OnItemClickListener 	// �޴� ���� ȭ��
{
	private DBAdapter mDb = null;				// DB 
	private ArrayList<Menu> mList = null;		// menu ����Ʈ
	private MenuListAdapter mAdapter = null;	// menu Adapter
	public static Activity MenuAct;							// Menu��Ƽ��Ƽ 
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menucontrol);
	    
	    MenuAct = MenuControlAct.this;			// ���� menu��Ƽ��Ƽ 
	    
	    mList = new ArrayList<Menu> ();
	    
	    mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();			// DB menu List ������ ������

	    ListView lv = ( ListView ) findViewById ( R.id.menucon_list );		// ���̺��� �ֹ� �޴� ����Ʈ
        
        MenuListAdapter m = new MenuListAdapter ( mList );
        lv.setAdapter( m );
        lv.setOnItemClickListener(this);
	}
	
	class ViewHolder // ����Ʈ���� �̹����� �ؽ�Ʈ Ŭ����
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtMaterial;
	}

	class MenuListAdapter extends BaseAdapter {
		private ArrayList<Menu> mList = null; // menu�� ������ ����Ʈ

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

			if (convertView == null) // �ڵ�󿡼� View�� �����Ǿ����� Ȯ��
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

			Menu m = mList.get(position); // position�� ���� �޴� ����
			Material material = mDb.getMaterialById(m.getId());		// menu id�� ���� ����� ����
			
			holder.mTxtName.setText( m.getName()); // ����Ʈ�� �̸� �ֱ�
			holder.mTxtPrice.setText("���� " + Integer.toString(m.getPrice())); // ����Ʈ��  �����ֱ�
			holder.mTxtMaterial.setText("���� " + Integer.toString(material.getNum())); // ����Ʈ��  �����ֱ�
			
			return convertView;
		}
	}
	
	public void onClickBtnmenuadd(View v) // �޴� �߰� ��ư
	{
		Intent intent = new Intent(this, MenuaddAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onClickBtnmenudel(View v) // �޴� ���� ��ư
	{
		Intent intent = new Intent(this, MenudelAct.class);

		startActivityForResult(intent, 0);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)	// menuList Ŭ�� �̺�Ʈ
	{
		Intent intent = new Intent(this, MenupatchAct.class);
		
		mList = new ArrayList<Menu> ();
		
		mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();
	    
	    Menu menu = mList.get(position);
	    intent.putExtra("menuId", menu.getId());
		
		startActivityForResult(intent, 0);
	}
	
	public void onActivityResult (int requestCode, int resultCode, Intent data)  // setResult���� ����� Intent
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
