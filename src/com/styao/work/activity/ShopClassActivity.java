package com.styao.work.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jock.tbshopcar.utils.LoadingUtils;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.R;
import com.styao.work.BaseActivity;
import com.styao.work.ProductList2Activity;
import com.styao.work.ProductListActivity;
import com.styao.work.bean.SearchCateBean;
import com.styao.work.bean.ShopClassData;

public class ShopClassActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private ListView listView;
	private List<ShopClassData> data = new ArrayList<ShopClassData>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				try {
					String str = (String) msg.obj;
					data.clear();
					JsonArray jsonArray = new JsonParser().parse(str)
							.getAsJsonArray();
					Gson gson = new Gson();
					for (JsonElement user : jsonArray) {
						// 使用GSON，直接转成Bean对象
						ShopClassData userBean = gson.fromJson(user,
								ShopClassData.class);
						data.add(userBean);
					}
					listView.setAdapter(new ShopClassAdapter());
				} catch (Exception e) {
				} finally {
					LoadingUtils.closeLoading(ShopClassActivity.this);
				}
				break;
			case -1:
				LoadingUtils.closeLoading(ShopClassActivity.this);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_shop_class);
		initView();
		VolleyTools volleyTools = new VolleyTools(handler);
		LoadingUtils.showInitLoading(this);
		volleyTools.StringRequest(Urls.StoreProductAssort
				+ getIntent().getIntExtra("storeid", 0), this, 2, null, 1);
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	private class ShopClassAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(ShopClassActivity.this);
				((TextView) convertView).setTextColor(Color
						.parseColor("#333333"));
				((TextView) convertView).setTextSize(
						TypedValue.COMPLEX_UNIT_DIP, 12);
				LayoutParams layoutParams = new LayoutParams(-1, 100);
				convertView.setLayoutParams(layoutParams);
				
				((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
				convertView.setPadding(30, 0, 0, 0);
			}

			if (position == 0) {
				if (data.size() > 0) {
					((TextView) convertView).setText("全部分类("
							+ data.get(0).getAllStoreCount() + ")");
				} else {
					((TextView) convertView).setText("全部分类");
				}
			} else {
				((TextView) convertView).setText(data.get(position - 1)
						.getName()
						+ "("
						+ data.get(position - 1).getStoreCount() + ")");
			}

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, ProductList2Activity.class);
		if (position == 0) {
			intent.putExtra("cateId", "0");
		} else {
			intent.putExtra("cateId",
					String.valueOf(data.get(position - 1).getStoreCid()));
		}
		startActivity(intent);
	}
}
