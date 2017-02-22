package com.styao.work.adapter;

import java.util.ArrayList;
import java.util.List;

import com.newgame.sdk.yyaost.R;
import com.styao.work.bean.SearchCateBean;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemSearchListAdapter extends BaseAdapter implements
		OnClickListener {
	private Context context;
	private List<SearchCateBean> data;
	public List<Integer> toplist;
	private int num; // 共有多少item
	private int count; // 记录前面的子类item共有count

	private int group, child;// 选中的位置

	private OnItemListener listener;

	private TextView tv;
	
	

	public int getGroup() {
		return group;
	}

	public int getChild() {
		return child;
	}

	public ItemSearchListAdapter(Context context, List<SearchCateBean> data,
			OnItemListener listener, TextView rankinglistTv) {
		super();
		this.context = context;
		this.data = data;
		this.toplist = new ArrayList<Integer>();
		this.listener = listener;
		tv = rankinglistTv;
	}

	public void setSelectLocation(int group, int child) {
		this.group = group;
		this.child = child;
		if (data != null && data.size() > group
				&& data.get(group).getCateList().size() > child) {
			tv.setText("查看"
					+ data.get(group).getCateList().get(child).getCateName()
					+ "排行榜");
		}
		notifyDataSetChanged();
	}

	public void updateData() {
		num = 0;
		count = 0;
		toplist.clear();
		if (data != null && data.size() > 0) {
			num = data.size();
			if (num > 0) {
				toplist.add(0);
			}
			for (int i = 0; i < data.size() - 1; i++) {
				if (data.get(i).getCateList() != null) {
					toplist.add(data.get(i).getCateList().size() + count
							+ toplist.size());
					count += data.get(i).getCateList().size();
					num += data.get(i).getCateList().size();
				}
			}
			if (data.size() > 0) {
				num += data.get(data.size() - 1).getCateList().size();
			}
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return num;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_search_left_main,
					null);
			holder = new ViewHolder();
			holder.itemSearch = convertView.findViewById(R.id.item_search_left);
			holder.itemSearchMain = convertView
					.findViewById(R.id.item_search_left_name);
			holder.itemSearchSubclass = (TextView) convertView
					.findViewById(R.id.item_search_subclass);
			holder.itemSearchSuperClass = (TextView) convertView
					.findViewById(R.id.item_search_superclass);
			holder.item_image = (ImageView) convertView
					.findViewById(R.id.item_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (toplist.contains(position)) {
			holder.itemSearchMain.setVisibility(View.VISIBLE);
			holder.itemSearch.setVisibility(View.GONE);
			int location = toplist.indexOf(position);
			holder.itemSearchSuperClass.setText(data.get(location)
					.getCateName());
			switch (location) {
			case 0:
				holder.item_image.setImageResource(R.drawable.icon_search_1);
				break;
			case 1:
				holder.item_image.setImageResource(R.drawable.icon_search_2);
				break;
			case 2:
				holder.item_image.setImageResource(R.drawable.icon_search_3);
				break;
			case 3:
				holder.item_image.setImageResource(R.drawable.icon_search_4);
				break;
			case 4:
				holder.item_image.setImageResource(R.drawable.icon_search_5);
				break;
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																			default:
				break;
			}
		} else {
			holder.itemSearchMain.setVisibility(View.GONE);
			holder.itemSearch.setVisibility(View.VISIBLE);
			holder.itemSearch.setOnClickListener(this);
			boolean b = false;
			boolean isOpenSelect = false;
			for (int i = 0; i < toplist.size() - 1; i++) {
				if (position > toplist.get(i) && position < toplist.get(i + 1)) {
					int location = position - toplist.get(i) - 1;
					if (group == i && child == location) {
						isOpenSelect = true;
						holder.itemSearch.setSelected(true);
						holder.itemSearchSubclass.setSelected(true);
					}
					holder.itemSearchSubclass.setText(data.get(i).getCateList()
							.get(location).getCateName());
					holder.itemSearch.setTag(i
							+ "="
							+ location
							+ "="
							+ data.get(i).getCateList().get(location)
									.getCateId());
					b = true;
				}
			}
			if (!b) {
				int location = position - toplist.get(toplist.size() - 1) - 1;
				if (group == toplist.size() - 1 && child == location) {
					isOpenSelect = true;
					holder.itemSearch.setSelected(true);
					holder.itemSearchSubclass.setSelected(true);
				}
				holder.itemSearchSubclass.setText(data.get(toplist.size() - 1)
						.getCateList().get(location).getCateName());
				holder.itemSearch.setTag(toplist.size()
						- 1
						+ "="
						+ location
						+ "="
						+ data.get(toplist.size() - 1).getCateList()
								.get(location).getCateId());
			}
			if (!isOpenSelect) {
				holder.itemSearch.setSelected(false);
				holder.itemSearchSubclass.setSelected(false);
			}
		}
		return convertView;
	}

	public class ViewHolder {
		View itemSearchMain, itemSearch;
		TextView itemSearchSubclass, itemSearchSuperClass;
		ImageView item_image;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_search_left:
			if (v.getTag() != null && v.getTag() instanceof String) {
				String str = (String) v.getTag();
				String[] strs = str.split("=");
				group = Integer.valueOf(strs[0]);
				child = Integer.valueOf(strs[1]);
				if (listener != null && strs.length > 2) {
					listener.onSelectItem(strs[2]);
				}
				if (data != null && data.size() > group
						&& data.get(group).getCateList().size() > child) {
					tv.setText("查看"
							+ data.get(group).getCateList().get(child)
									.getCateName() + "排行榜");
				}
				notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	public interface OnItemListener {
		void onSelectItem(String cateId);
	}

}
