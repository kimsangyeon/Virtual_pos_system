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

public class OrderAct extends Activity implements OnItemClickListener 	// �ֹ� ȭ��
{
	private DBAdapter mDb = null;			// DB
	private ArrayList<Menu> mList = null;	// menu List
	private MenuListAdapter mAdapter = null;	// menu Adapter
	private String table_id = null;		// table id
	
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.order);
	    
	    Intent recvedIntent = getIntent ();							// getIntent ���� �޾ƿ�    
        table_id = recvedIntent.getStringExtra("isNum");	// ���̺� �̸��� ���� ����
	    
	    mList = new ArrayList<Menu> ();		
	    
	    mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();		// DB�� menuList ���� ������

	    ListView lv = ( ListView ) findViewById ( R.id.order_menu );		// ���̺��� �ֹ� �޴� ����Ʈ
        
        MenuListAdapter m = new MenuListAdapter ( mList );
        lv.setAdapter( m );
        lv.setOnItemClickListener(this);
	}
	
	class ViewHolder // ����Ʈ���� �̹����� �ؽ�Ʈ Ŭ����
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
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

			Menu m = mList.get(position); // position�� ���� menu�� ����
	
			holder.mTxtName.setText( m.getName()); // ����Ʈ�� �̸� �ֱ�
			holder.mTxtPrice.setText("����" + Integer.toString(m.getPrice())); // ����Ʈ��  �����ֱ�

			return convertView;
		}
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)	// ���̺�� Ŭ�� �̺�Ʈ
	{
		TableAct mAct = (TableAct) TableAct.tAct;
		Order order = new Order();
		mList = new ArrayList<Menu> ();
		
		mDb = new DBAdapter ( this );
	    mList = mDb.getMenuList();
	    
	    Menu menu = mList.get(position);
	    Material material = mDb.getMaterialById(menu.getId());
	    
	    if(material.getNum() == 0)		// ��ᰡ ���� ��� ��� â ���
	    {
	    	Toast.makeText(OrderAct.this, "<<" + menu.getName() + "�� ��ᰡ �����մϴ�.!!>>" + "\n\n" + "-��Ḧ �ֹ��Ͻÿ�-", Toast.LENGTH_SHORT).show();	 // TOAST 
	    	finish();
	    }
	    else if(material.getNum() != 0)		// ��ᰡ �������
	    {
	    	order.settableId(Integer.parseInt(table_id));
	    	order.setmenuId(menu.getId());
	    	order.setDone(0);
	    
	    	material.setNum(material.getNum() - 1);		// ���� ����
	    	
	    	mDb.insertOrder(order);			// order �߰� 
	    	mDb.updateMaterial(material);	// DB ��� ���� ����
	    	
	    	mAct.finish();		// table��Ƽ��Ƽ ����
	    	finish();
	 
	    	Intent intent = new Intent(this, TableAct.class);
	    	intent.putExtra("isNum", table_id);			// ���̺� ��ȣ ����
	    	startActivityForResult(intent, 0);		// intent�� ���� Activity ����
	    }
	}

}
