package com.jock.tbshopcar.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.newgame.sdk.yyaost.R;
import com.styao.model.MedicineListBean;

public class ProductListAdapter extends BaseAdapter {

	private Context context;
	private List<MedicineListBean> data;
	private LayoutInflater mInflater;
	private int TAGE;

	public ProductListAdapter(List<MedicineListBean> data, Context context,
			int tage) {
		this.context = context;
		this.data = data;
		TAGE = tage;
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
	public View getView(int position, View v, ViewGroup parent) {
		if (TAGE == 1) {
			v = yaoping(v, position);
		}
		return v;
	}

	private View yaoping(View v, int position) {
		ViewHolder holder;
		if (v == null) {
			v = mInflater.inflate(R.layout.adapter_product, null);
			holder = new ViewHolder();
			/* 得到各个控件的对象 */
			holder.nameT = (TextView) v.findViewById(R.id.product_name_i);
			holder.iconI = (ImageView) v.findViewById(R.id.product_icon_i);
			// 接受
			holder.introduceT = (TextView) v
					.findViewById(R.id.product_introduce_t);
			// 价格
			holder.priceT = (TextView) v.findViewById(R.id.product_price_t);
			// 建议价格
			holder.retailT = (TextView) v.findViewById(R.id.product_retail_t);
			// 率利
			holder.rateT = (TextView) v.findViewById(R.id.product_rate_t);
			// 促销
			holder.cuxiaoT = (TextView) v.findViewById(R.id.product_tilte_t);
			// 首推标签
			holder.pushLogoT = (TextView) v.findViewById(R.id.product_push_t);

			String iamgeURL = data.get(position).getImageUrl();
			if (null != iamgeURL && !iamgeURL.equals("")
					&& !iamgeURL.equals("/img/nopic.jpg")) {
				ImageRequest(holder.iconI, iamgeURL);
			} else {
				holder.iconI.setImageResource(R.drawable.ic_launcher);
			}
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		String name = data.get(position).getDrugsBase_DrugName();
		holder.nameT.setText(name);
		holder.introduceT.setText(data.get(position).getGoods_Pcs()
				+ data.get(position).getGoods_Unit() + "\n"
				+ data.get(position).getDrugsBase_Manufacturer());
		String ishigh = data.get(position).getIsHighMargin();// 0为非高毛利
		int mini = Integer.valueOf(data.get(position).getMinShopPrice());// 最低價格
		int max = Integer.valueOf(data.get(position).getMaxShopPrice());// 最高價格
		String maxGross = data.get(position).getMaxGrossMargin();// 商品最高的商城价格
		String grossMargin = data.get(position).getMaxGrossMargin();// 商品的最高毛利率
		if (ishigh.equals("1")) {
			if (mini == -1) {
				holder.priceT.setText("登陆可见");
				holder.retailT.setText("登陆可见");
			} else if (mini == max) {
				holder.priceT.setText(mini);
				holder.retailT.setText(grossMargin);
			} else {
				holder.priceT.setText(mini + "~" + max);
				holder.retailT.setText(grossMargin);
			}
			holder.pushLogoT.setVisibility(View.VISIBLE);
		} else {
			holder.pushLogoT.setVisibility(View.GONE);
		}
		holder.rateT.setText("" + maxGross);
		//
		// data.get(position).getImageUrl()
		String cuxiaoS = data.get(position).getPromotionTypes();
		if (null != cuxiaoS) {
			if (cuxiaoS.equals("1")) {
				holder.cuxiaoT.setText("价格购");
			} else if (cuxiaoS.equals("2")) {
				holder.cuxiaoT.setText("有促销");
			} else {
				holder.cuxiaoT.setVisibility(View.GONE);
			}
		} else {
			holder.cuxiaoT.setVisibility(View.GONE);
		}
		return v;
	}

	/* 存放控件 的ViewHolder */
	public static final class ViewHolder {
		public TextView nameT, introduceT, retailT, rateT, priceT, cuxiaoT,
				pushLogoT;
		public ImageView iconI;
	}

	public void ImageRequest(final ImageView myImageView, String url) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		ImageRequest request = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
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
}
