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
			int TaxPolicy = Integer.valueOf(data.get(position).getTaxPolicy()); // ����˵Ʊ����
			int DeliveryType = Integer.valueOf(data.get(position).getDeliveryType());// ����
			String values = data.get(position).getName();
			String acount = data.get(position).getProductsCount();
			// shopprice -1 δ��¼
			holder.store_baoyou.setText(getMTaxPolicy(TaxPolicy) + "");
			holder.store_kaipiao.setText(getMDeliveryType(DeliveryType) + "");
			holder.store_name.setText(values);
			holder.store_porNmber_t.setText("��Ʒ���� : " + acount);
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
			value = "ͳһ����";
			break;
		case 1:
			value = "��������";
			break;
		default:
			value = "ͳһ����";
			break;
		}
		return value;
	}

	private String getMDeliveryType(int index) {
		String value = null;
		switch (index) {
		case 0:
			value = "��Ʊͬ��";
			break;
		case 1:
			value = "��������Ʊ";
			break;
		case 2:
			value = "�µ׿�Ʊ";
			break;
		case 3:
			value = "���ӷ�Ʊ";
			break;

		default:
			value = "��Ʊͬ��";
			break;
		}
		return value;
	}

	/* ��ſؼ� ��ViewHolder */
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
				// ��imageView����ͼƬ
				myImageView.setImageBitmap(response);
			}
		}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// ����һ�Ŵ����ͼƬ����ʱ��ic_launcher����
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
