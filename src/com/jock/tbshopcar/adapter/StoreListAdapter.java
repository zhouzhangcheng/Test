package com.jock.tbshopcar.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.newgame.sdk.yyaost.R;
import com.styao.model.BaseInfoList;
import com.styao.model.TopProducts;
import com.styao.work.activity.ShopHomeActivity;

public class StoreListAdapter extends BaseAdapter {
	private Context context;
	private List<BaseInfoList> data;
	private LayoutInflater mInflater;
	private String StoreId;

	public StoreListAdapter(List<BaseInfoList> data, Context context) {
		this.context = context;
		this.data = data;
		mInflater = LayoutInflater.from(context);
	};

	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		return this.data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = mInflater.inflate(R.layout.adapter_store, null);
			holder.logoI = (ImageView) v.findViewById(R.id.store_loge_i);
			holder.store_baoyou = (TextView) v.findViewById(R.id.store_baoyou);
			holder.store_kaipiao = (TextView) v.findViewById(R.id.store_kaipiao);
			holder.store_name = (TextView) v.findViewById(R.id.store_name);
			holder.store_porNmber_t = (TextView) v.findViewById(R.id.store_porNmber_t);
			String urlLogo = data.get(position).getLogo();
			ImageRequest(holder.logoI, urlLogo);
			holder.gridView = (GridView) v.findViewById(R.id.store_grid);
			List<TopProducts> top = data.get(position).getTopProducts();
			holder.gridView.setAdapter(new StoreGaidAdapter(top, context, data.get(position).getStoreId()));
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if (data.size() > 0) {
			int TaxPolicy = Integer.valueOf(data.get(position).getTaxPolicy()); // 店铺说票政策
			int DeliveryType = Integer.valueOf(data.get(position).getDeliveryType());// 配送
			String values = data.get(position).getName();
			String acount = data.get(position).getProductsCount();
			// shopprice -1 未登录
			holder.store_baoyou.setText(getMTaxPolicy(TaxPolicy) + "");
			holder.store_kaipiao.setText(getMDeliveryType(DeliveryType) + "");
			holder.store_name.setText(values);
			holder.store_porNmber_t.setText("商品數量 : " + acount);
			holder.store_baoyou.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, ShopHomeActivity.class);
					intent.putExtra("storeId", StoreId);
					context.startActivity(intent);
					StoreId = data.get(position).getStoreId();
					IntentS(StoreId);
				}
			});
			holder.store_kaipiao.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopHomeActivity.class);
					intent.putExtra("storeId", StoreId);
					context.startActivity(intent);
					StoreId = data.get(position).getStoreId();
					IntentS(StoreId);
				}
			});
			holder.store_name.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ShopHomeActivity.class);
					intent.putExtra("storeId", StoreId);
					context.startActivity(intent);
					StoreId = data.get(position).getStoreId();
					IntentS(StoreId);
				}
			});
		}
		return v;
	}

	private String getMTaxPolicy(int index) {
		String value = null;
		switch (index) {
		case 0:
			value = "统一物流";
			break;
		case 1:
			value = "自主物流";
			break;
		default:
			value = "统一物流";
			break;
		}
		return value;
	}

	private String getMDeliveryType(int index) {
		String value = null;
		switch (index) {
		case 0:
			value = "货票同行";
			break;
		case 1:
			value = "下批货开票";
			break;
		case 2:
			value = "月底开票";
			break;
		case 3:
			value = "电子发票";
			break;

		default:
			value = "货票同行";
			break;
		}
		return value;
	}

	/* 存放控件 的ViewHolder */
	public static final class ViewHolder {
		public ImageView logoI;
		public TextView store_name, store_kaipiao, store_baoyou, store_porNmber_t;
		public GridView gridView;
	}

	public void ImageRequest(final ImageView myImageView, String url) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				// 给imageView设置图片
				myImageView.setImageBitmap(response);
			}
		}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// 设置一张错误的图片，临时用ic_launcher代替
				myImageView.setImageResource(R.drawable.ic_launcher);
			}
		});
		requestQueue.add(request);
	}

	private void IntentS(String store) {
		Intent intent = new Intent(context, ShopHomeActivity.class);
		intent.putExtra("storeId", Integer.valueOf(store));
		context.startActivity(intent);
	}
}
