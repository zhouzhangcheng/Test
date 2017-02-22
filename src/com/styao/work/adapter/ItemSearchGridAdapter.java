package com.styao.work.adapter;

import java.util.List;

import com.jock.tbshopcar.utils.ImageLoaderUtil;
import com.newgame.sdk.yyaost.R;
import com.styao.work.ProductListActivity;
import com.styao.work.bean.SearchCateBean;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemSearchGridAdapter extends BaseAdapter implements
		OnClickListener {
	private Context context;
	private List<SearchCateBean> list;

	public ItemSearchGridAdapter(Context context, List<SearchCateBean> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list.size() % 2 == 0) {
			return list.size() / 2;
		} else {
			return list.size() / 2 + 1;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.item_search_good, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_image);
			holder.tv = (TextView) convertView.findViewById(R.id.item_text);
			holder.image2 = (ImageView) convertView
					.findViewById(R.id.item_image_2);
			holder.tv2 = (TextView) convertView.findViewById(R.id.item_text_2);
			holder.view = convertView.findViewById(R.id.linear1);
			holder.view2 = convertView.findViewById(R.id.linear2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int location = position * 2;
		holder.tv.setText(list.get(location).getCateName());
		ImageLoaderUtil.getInstance().displayImage(
				list.get(location).getImagesUrl(), holder.image);
		holder.view.setOnClickListener(this);
		holder.view.setTag(list.get(location));
		if (position * 2 + 1 < list.size()) {
			holder.view2.setVisibility(View.VISIBLE);
			holder.view2.setOnClickListener(this);
			holder.view2.setTag(list.get(position * 2 + 1));
			holder.tv2.setText(list.get(position * 2 + 1).getCateName());
			ImageLoaderUtil.getInstance().displayImage(
					list.get(position * 2 + 1).getImagesUrl(), holder.image2);
		} else {
			holder.view2.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public class ViewHolder {
		ImageView image, image2;
		TextView tv, tv2;
		View view, view2;
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(context, ProductListActivity.class);
		intent.putExtra("cateId", ((SearchCateBean) arg0.getTag()).getCateId());
		context.startActivity(intent);
	}

}
