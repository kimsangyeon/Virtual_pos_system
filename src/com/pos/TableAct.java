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
	private String table_id = null;				// ���̺� id
	private ArrayList<Menu> mList = null;			// ���̺� ����Ʈ
	private OrderListAdapter tAdapter = null;			// ���̺� ���
	
	private OrderCtrol orderCtl = null;		// orderctrol class
	private MenuCtrol menuCtl = null; 		// menuctrol class
	private Property property = null;		// property class
	private DBAdapter mDb= null;			//DB
	public static Activity tAct;							// table��Ƽ��Ƽ 
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.table);

	    ListView lv = (ListView) findViewById(R.id.table_menu);		// ���̺� ��Ʈ�� ����
	    Intent recvedIntent = getIntent ();				// getIntent ���� �޾ƿ�    
	    
	    mDb = new DBAdapter (this); 
	    tAct = TableAct.this;
	    table_id = recvedIntent.getStringExtra("isNum");	// ���̺� �̸��� ���� ����
	    
	    orderCtl = new OrderCtrol();
	    orderCtl.listAdd(mDb.getOrderList());		// DB �ֹ� ����Ʈ ���� 
	    
        mList = new ArrayList<Menu>();			// ����Ʈ ����
        mList = mDb.getMenuList();			// DB�޴� ����Ʈ 
        
        menuCtl = new MenuCtrol();
        menuCtl.listAdd(mList);			// menu List �߰� 
        
        tAdapter = new OrderListAdapter(mList);							// ���̺� �߰�
		lv.setAdapter(tAdapter);									
        
	}
	
	class ViewHolder // ����Ʈ���� �̹����� �ؽ�Ʈ Ŭ����
	{
		public ImageView mImgView;
		public TextView mTxtName;
		public TextView mTxtPrice;
		public TextView mTxtNum;
	}

	class OrderListAdapter extends BaseAdapter {
		private ArrayList<Menu> mList = null; // menu�� ������ ����Ʈ
		
		public OrderListAdapter(ArrayList<Menu> OrderList) { mList = OrderList; }
		public int getCount() { return mList.size(); }
		public long getItemId(int position) { return 0; }
		public Object getItem(int position) { return mList.get(position); }
		
		public View getView(int position, View convertView, ViewGroup parent) // position
		{
			ViewHolder holder;
			int count = 0;
			
			if (convertView == null) // �ڵ�󿡼� View�� �����Ǿ����� Ȯ��
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
			
			Menu menu = mList.get(position);	// position�� ���� menu ���� 
			
			for(int i = 0; i < orderCtl.arrayNum(); i++)
			{
				Order order = orderCtl.reArraylist().get(i);
				// order�� ����� ���̺� id�� ���� ���̺� id �� && ���� �ֹ����� Ȯ��
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())	// �ֹ� menu id�� menu id �� 
						count++;
				}		
			}
	
			holder.mTxtName.setText(menu.getName()); // ����Ʈ�� �̸� �ֱ�
			holder.mTxtPrice.setText("����" + menu.getPrice()); // ����Ʈ��  �����ֱ�
			holder.mTxtNum.setText("����" + count); // ����Ʈ��  �����ֱ�
			
			return convertView;
		}
	}
	
	
	public void onClickBtnorder(View v) // �ֹ� ȭ�� ��ư
	{
		Intent intent = new Intent(this, OrderAct.class);
		intent.putExtra("isNum", table_id);

		startActivityForResult(intent, 0);
	}

	public void onClickBtncash(View v) // ���� ��� ��ư
	{
		EditText editmoney = (EditText) findViewById(R.id.setmoneyEdit);
		int getmoney = Integer.parseInt(editmoney.getText().toString());
		int gold = 0;			// ����� �ݾ�
		int survice1 = 0;		// ���� 1 	(10��°�մ� ��ü ����)
		int survice2 = 0;		// ���� 2	(10���� �̻��� �մ� 10%����)
		
		for(int i = 0; i < menuCtl.reArraylist().size(); i++)
		{
			Menu menu = menuCtl.reArraylist().get(i);
			
			for(int j = 0; j < orderCtl.arrayNum(); j++)
			{
				Order order = orderCtl.reArraylist().get(j);
				// order�� ����� ���̺� id�� ���� ���̺� id �� && ���� �ֹ����� Ȯ��
				if((Integer.parseInt(table_id) == order.gettableId()) && (order.getDone() == 0) )
				{
					if(order.getmenuId() == menu.getId())	// �ֹ� menu id�� menu id �� 
					{
						if(order.getId()%10 == 0 && order.getDone() == 0)	// 10��° �ֹ� �մ��ϰ�� ���� 1
							survice1 = 1;
							
						gold += menu.getPrice();	// ���ݾ� 
					}
				}		
			}
		}
		
		
		if(gold >= 100000)		// 10���� �̻� ���Խ�
		{
			survice2 = 1;		// ���� 2
			gold = gold - gold/10;		// ���ݾ׿��� 10% ����
		}
		
		mDb = new DBAdapter (this);
		
		Property property = new Property();
		property = mDb.getPropertyById(1);
		property.setMoney(property.getMoney() + gold);		// ��꿡 ���ȱݾ� ����
		
		if(getmoney < gold || editmoney.getText().toString() == null)	// ������ ������ �����Ѱ��
		{
			Toast.makeText(TableAct.this, "�����Ͻ� �ݾ��� �����մϴ�", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int remoney = 0;	// �Ž�����

			remoney = getmoney - gold;		// �Ž����� ���
			
			if(remoney == 0 && survice1 == 0)		// �Ž������� ���� ��� 
			{
				if(survice2 == 1)		// ���� 2�ϰ�� 
				{
					Toast.makeText(TableAct.this, "���Ϸ�(10%���ε�)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "���Ϸ�", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
			}
			else if(survice1 == 1)		// ���� 1�մ�
			{
				Toast.makeText(TableAct.this, "!!!EVENT 10��° �մ��Դϴ�!!!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(survice2 == 1)	// ���� 2�� ���
				{
					Toast.makeText(TableAct.this, "�Ž�����(10%���ε�): " + remoney, Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(TableAct.this, "�Ž�����: " + remoney, Toast.LENGTH_SHORT).show();
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
							mDb.updateOrder(order);		// ���� orderList is_done ���� ����
						}
					}		
				}
			}
			finish();
		}
	}
	
	public void onClickBtncard(View v) // ī�� ��� ��ư
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
			Toast.makeText(TableAct.this, "!!!EVENT 10��° �մ��Դϴ�!!!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if(survice2 == 1)
			{
				Toast.makeText(TableAct.this, "���Ϸ�(10%���ε�)", Toast.LENGTH_SHORT).show();
				mDb.updateProperty(property);
			}
			else
			{
				Toast.makeText(TableAct.this, "���Ϸ�", Toast.LENGTH_SHORT).show();
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
	
	public void onClickBtnpack(View v) // ���� ��� ��ư
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
		
		gold = gold - 1000;								// ��� 10% ����
		
		Property property = new Property();
		property = mDb.getPropertyById(1);
		property.setMoney(property.getMoney() + gold);
		
		if(getmoney < gold || editmoney.getText().toString() == null)
		{
			Toast.makeText(TableAct.this, "�����Ͻ� �ݾ��� �����մϴ�", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int remoney = 0;

			remoney = getmoney - gold;
			if(remoney == 0 && survice1 == 0)
			{
				if(survice2 == 1)
				{
					Toast.makeText(TableAct.this, "���Ϸ�(�����ֹ� 1000�� ���� �� 10%����)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "���Ϸ�(�����ֹ� 1000�� ����)", Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
			}
			else if(survice1 == 1)
			{
				Toast.makeText(TableAct.this, "!!!EVENT 10��° �մ��Դϴ�!!!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(survice2 == 1)
				{
					Toast.makeText(TableAct.this, "�Ž�����(�����ֹ� 1000�� ���� �� 10%����)" + remoney, Toast.LENGTH_SHORT).show();
					mDb.updateProperty(property);
				}
				else
				{
					Toast.makeText(TableAct.this, "�Ž�����(�����ֹ� 1000������): " + remoney, Toast.LENGTH_SHORT).show();
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
