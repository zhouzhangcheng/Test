package com.styao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newgame.sdk.yyaost.R;

public class MyTreeListView extends LinearLayout implements OnClickListener , OnItemClickListener{
	private LayoutInflater inflater;
	private TextView title_text;
	private ListView list;
	private View view,headView;
	
	private boolean isShowing;
	private String title;
	private ArrayList<String> bodys;
	private MyAdapter adapter;
	
	private OnItemClickCallback callback;
	
	public MyTreeListView(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public MyTreeListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public MyTreeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context){
		inflater = LayoutInflater.from(context);
		isShowing = false;
		//主View
		view = inflate(context, R.layout.activity_childview, null);
		//头部view
		headView = inflate(context, R.layout.activity_childview_head_or_item, null);
		//标题文本
		title_text = (TextView) headView.findViewById(R.id.item_text);
		//
		list = (ListView) view.findViewById(R.id.frag_list);
		list.addHeaderView(headView);
		list.setOnItemClickListener(this);
		
		bodys = new ArrayList<String>();
		
		adapter = new MyAdapter(bodys);
		list.setAdapter(adapter);
		
		headView.setOnClickListener(this);
		headView.setBackgroundColor(Color.BLUE);
		addView(view);
	}
	
	public void setCallback(OnItemClickCallback callback){
		this.callback = callback;
	}
	
	public void setData(String title,ArrayList<String> datas){
		this.title = title;
		bodys.clear();
		bodys.addAll(datas);
		
		title_text.setText(title);
		
		adapter.notifyDataSetChanged();
		
		setListViewHeightBasedOnChildren(list);
		
	}
	
	//ListView 嵌套 ListView 需要重新计算子ListView的高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {    
        ListAdapter listAdapter = listView.getAdapter();     
        if (listAdapter == null) {    
            // pre-condition    
            return;    
        }    
    
        int totalHeight = 0;    
        for (int i = 0; i < listAdapter.getCount(); i++) {    
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);     
            totalHeight += listItem.getMeasuredHeight();    
        }    
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }
	
	class MyAdapter extends BaseAdapter{
		private ArrayList<String> bodys;
		public MyAdapter(ArrayList<String> bs){;
			bodys = new ArrayList<String>();
			if(bs != null){
				bodys.addAll(bs);
			}
		}
		
		public void clear(){
			bodys.clear();
		}
		
		public void setDatas(ArrayList<String> bs){
			bodys = new ArrayList<String>();
			if(bs != null){
				bodys.addAll(bs);
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bodys.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return bodys.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.activity_childview_head_or_item, null);
				tv = (TextView) convertView.findViewById(R.id.item_text);
				convertView.setTag(tv);
			}
			else{
				tv = (TextView) convertView.getTag();
			}

			tv.setText(bodys.get(position));
			return convertView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(isShowing){
			isShowing = false;
			adapter.clear();
		}
		else{
			isShowing = true;
			adapter.setDatas(bodys);
		}
		adapter.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(list);
	}
	
	public interface OnItemClickCallback{
		public void callback(String title,String body);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		callback.callback(title, bodys.get(position-1));
	}
}
