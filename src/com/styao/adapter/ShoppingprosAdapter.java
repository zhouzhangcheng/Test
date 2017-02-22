package com.styao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newgame.sdk.yyaost.R;
import com.styao.model.*;

/**
 * 璐墿杞dapter
 * 
 * @author
 * 
 */
public class ShoppingprosAdapter extends BaseAdapter {

	ArrayList<shoppingcartModel> list;
	Context mContext;
	private ListView baoyoulistv;

	public ShoppingprosAdapter(ArrayList<shoppingcartModel> list,
			Context mContext) {
		this.list = list;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Myshopping myshopping;
		if (convertView == null) {
			myshopping = new Myshopping();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.activity_shoppingcart_item, null);

			// myshopping.img_queding=(ImageView)
			// convertView.findViewById(R.id.img_queding);
			// myshopping.img_shitu=(ImageView)
			// convertView.findViewById(R.id.img_shitu);
			// myshopping.jia_img=(ImageView)
			// convertView.findViewById(R.id.jia_img);
			// myshopping.jishao=(TextView)
			// convertView.findViewById(R.id.jishao);
			// myshopping.list_jiage=(TextView)
			// convertView.findViewById(R.id.list_jiage);
			convertView.setTag(myshopping);
		} else {
			myshopping = (Myshopping) convertView.getTag();
		}
		// myshopping.img_queding.setBackgroundResource(list.get(position).getImg_queding());
		// myshopping.img_shitu.setBackgroundResource(list.get(position).getImg_shitu());
		// myshopping.jia_img.setBackgroundResource(list.get(position).getJia_img());
		// myshopping.jishao.setText(list.get(position).getJishao());
		// myshopping.list_jiage.setText(list.get(position).getList_jiage());
		return convertView;
	}

	public class Myshopping {
		private ImageView img_queding, img_shitu, jia_img;
		private TextView jishao, list_jiage;
	}

	// ListView 嵌套 ListView 需要重新计算子ListView的高度
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
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
