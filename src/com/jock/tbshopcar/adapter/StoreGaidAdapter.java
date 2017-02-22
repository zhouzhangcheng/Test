package com.jock.tbshopcar.adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.newgame.sdk.yyaost.R;
import com.styao.model.TopProducts;
import com.styao.work.activity.ShopHomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreGaidAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	private List<TopProducts> data;
	private LayoutInflater mInflater;
	private int StoreId;

	public StoreGaidAdapter(List<TopProducts> data, Context context, String StoreId) {
		this.context = context;
		this.data = data;
		mInflater = LayoutInflater.from(context);
		this.StoreId = Integer.valueOf(StoreId);
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
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = mInflater.inflate(R.layout.adapter_grid, null);
			holder.logoI = (ImageView) v.findViewById(R.id.store_shangping_i3);
			holder.logoI.setOnClickListener(this);
			holder.store_name_i = (TextView) v.findViewById(R.id.store_name_i);
			holder.store_price_i = (TextView) v.findViewById(R.id.store_price_i);
			String urlLogo = data.get(position).getImages();
			ImageRequest(holder.logoI, urlLogo);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if (data.get(position).getShopprice().equals("-1")) {
			holder.store_price_i.setText("登录可见");
		} else {
			holder.store_price_i.setText(data.get(position).getMarketprice());
		}

		holder.store_name_i.setText("" + data.get(position).getDrugsBase_DrugName() + "元");
		return v;
	}

	/* 存放控件 的ViewHolder */
	public static final class ViewHolder {
		public ImageView logoI;
		public TextView store_price_i, store_name_i;
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.store_shangping_i3:
			Toast.makeText(context, "跳 別的地方 ~！~~！~", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}
}
