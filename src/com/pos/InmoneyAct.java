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
	private DBAdapter mDb = null;						// DB����
	private ArrayList<Menu> mList = null;				// menuList
	private MenuListAdapter mAdapter = null;			// menuAdapter
	private OrderCtrol orderCtl = null;					// orderCtrol
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inmoney);

		mList = new ArrayList<Menu> ();					// menuList�ʱ�ȭ
	    
	    mDb = new DBAdapter ( this );					// DB �ʱ�ȭ
	    mList = mDb.getMenuList();						// DB�� menu ����Ʈ �߰�
	    
	    orderCtl = new OrderCtrol();
	    orderCtl.listAdd(mDb.getOrderList());			// DB�� order ����Ʈ �߰�
	    
		ListView lv = (ListView) findViewById(R.id.inmoney_id); // ���̺��� �ֹ� �޴�	

		MenuListAdapter m = new MenuListAdapter(mList);		// List ������ adapter��
		lv.setAdapter(m);
	}

	class ViewHolder // ����Ʈ���� �̹����� �ؽ�Ʈ Ŭ����
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtNum;
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
			int count = 0;			// menu�� �ȸ����� ī��Ʈ�ϴ� ����
			
			if (convertView == null) // �ڵ�󿡼� View�� �����Ǿ����� Ȯ��
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

			Menu menu = mList.get(position); 			// menulist�� ��ġ�� ������ ã�� ���� 

			for(int i = 0; i < orderCtl.arrayNum(); i++)		// list ũ�⸹ŭ �ݺ�
			{
				Order order = orderCtl.reArraylist().get(i);	// order ������ ������
				
				if(order.getDone() == 1)				// ���Ǿ����� Ȯ��
				{
					if(order.getmenuId() == menu.getId())		// order �޴� Id�� menu�� Id ��
						count++;
				}		
			}
		
			holder.mTxtName.setText(menu.getName()); // ����Ʈ�� �̸� �ֱ�
			holder.mTxtPrice.setText("�� �Ǹ� ���� : " + Integer.toString(menu.getPrice() * count)); // ����Ʈ��
			holder.mTxtNum.setText("�ȸ� ���� : " + count); // ����Ʈ��  �����ֱ�																	
			
			return convertView;
		}
	}

}
