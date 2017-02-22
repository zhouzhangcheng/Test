package com.styao.work.adapter;

import java.util.List;

import com.jock.tbshopcar.utils.ImageLoaderUtil;
import com.jock.tbshopcar.utils.TextViewUtils;
import com.jock.tbshopcar.utils.Urls;
import com.newgame.sdk.yyaost.R;
import com.styao.work.activity.WebActivity;
import com.styao.work.bean.ShopHomeData.ShopGoodInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemShopHomeCuxiaoAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<ShopGoodInfo> data;
	private boolean isLogin;

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
		notifyDataSetChanged();
	}

	public ItemShopHomeCuxiaoAdapter(Context context, List<ShopGoodInfo> data) {
		super();
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data != null) {
			if (data.size() % 2 == 0) {
				return data.size() / 2;
			} else {
				return data.size() / 2 + 1;
			}
		}
		return 0;
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.item_shop_home_cuxiao_good, null);
			viewHolder = new ViewHolder();
			viewHolder.item_shop_home_linear_2 = convertView
					.findViewById(R.id.item_shop_home_linear_2);
			viewHolder.item_shop_home_linear = convertView
					.findViewById(R.id.item_shop_home_linear);
			viewHolder.item_shop_home_image = (ImageView) convertView
					.findViewById(R.id.item_shop_home_image);
			viewHolder.item_shop_home_image_2 = (ImageView) convertView
					.findViewById(R.id.item_shop_home_image_2);
			viewHolder.item_shop_home_price = (TextView) convertView
					.findViewById(R.id.item_shop_home_spe_price);
			viewHolder.item_shop_home_price_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_spe_price_2);
			viewHolder.item_mark_price = (TextView) convertView
					.findViewById(R.id.item_shop_home_old_price);
			viewHolder.item_mark_price2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_old_price_2);
			viewHolder.item_shoutui = convertView
					.findViewById(R.id.item_shop_home_shoutui);
			viewHolder.item_shoutui2 = convertView
					.findViewById(R.id.item_shop_home_shoutui_2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 加载第一行数据
		ShopGoodInfo shopGoodInfo = data.get(position * 2);
		ImageLoaderUtil.getInstance().displayImage(shopGoodInfo.getImageUrl(),
				viewHolder.item_shop_home_image);
		if (shopGoodInfo.getIsKong().equals("True")) {
			viewHolder.item_shoutui.setVisibility(View.VISIBLE);
		} else {
			viewHolder.item_shoutui.setVisibility(View.GONE);
		}
		if (shopGoodInfo.getAddPriceBuyModel() != null) {
			viewHolder.item_mark_price.setVisibility(View.GONE);
			StringBuffer sb = new StringBuffer();
			sb.append("+");
			sb.append(shopGoodInfo.getAddPriceBuyModel().getAddPrice());
			sb.append("元 可换购本品");
			sb.append(TextViewUtils.delPoint(shopGoodInfo.getAddPriceBuyModel().getSecondProudctNum()));
			sb.append(shopGoodInfo.getGoods_Unit());
			viewHolder.item_shop_home_price.setText(sb.toString());
		} else if (shopGoodInfo.getSpecialPriceModel() != null) {
			viewHolder.item_mark_price.setVisibility(View.VISIBLE);
			viewHolder.item_mark_price.setText("原价￥"
					+ shopGoodInfo.getShopprice());
			viewHolder.item_shop_home_price.setText("特价￥"
					+ shopGoodInfo.getSpecialPriceModel().getSpeprice());
			viewHolder.item_mark_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
		}
		viewHolder.item_shop_home_linear.setOnClickListener(this);
		viewHolder.item_shop_home_linear.setTag(shopGoodInfo);
		if (position * 2 + 1 < data.size()) {
			viewHolder.item_shop_home_linear_2.setVisibility(View.VISIBLE);
			// 加载第一行数据
			ShopGoodInfo shopGoodInfo2 = data.get(position * 2 + 1);
			ImageLoaderUtil.getInstance().displayImage(
					shopGoodInfo2.getImageUrl(),
					viewHolder.item_shop_home_image_2);
			if (shopGoodInfo2.getIsKong().equals("True")) {
				viewHolder.item_shoutui2.setVisibility(View.VISIBLE);
			} else {
				viewHolder.item_shoutui2.setVisibility(View.GONE);
			}
			if (shopGoodInfo2.getAddPriceBuyModel() != null) {
				viewHolder.item_mark_price2.setVisibility(View.GONE);
				StringBuffer sb = new StringBuffer();
				sb.append("+");
				sb.append(shopGoodInfo2.getAddPriceBuyModel().getAddPrice());
				sb.append("元 可换购本品");
				sb.append(TextViewUtils.delPoint(shopGoodInfo2.getAddPriceBuyModel().getSecondProudctNum()));
				sb.append(shopGoodInfo2.getGoods_Unit());
				viewHolder.item_shop_home_price_2.setText(sb.toString());
			} else if (shopGoodInfo2.getSpecialPriceModel() != null) {
				viewHolder.item_mark_price2.setVisibility(View.VISIBLE);
				viewHolder.item_mark_price2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
				viewHolder.item_mark_price2.setText("原价￥"
						+ shopGoodInfo2.getShopprice());
				viewHolder.item_shop_home_price_2.setText("特价￥"
						+ shopGoodInfo2.getSpecialPriceModel().getSpeprice());
			}
			viewHolder.item_shop_home_linear_2.setOnClickListener(this);
			viewHolder.item_shop_home_linear_2.setTag(shopGoodInfo2);
		} else {
			viewHolder.item_shop_home_linear_2.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	private class ViewHolder {
		View item_shop_home_linear,item_shop_home_linear_2, item_shoutui, item_shoutui2;
		ImageView item_shop_home_image, item_shop_home_image_2;
		TextView item_shop_home_price, item_shop_home_price_2, item_mark_price,
				item_mark_price2;

	}

	@Override
	public void onClick(View arg0) {
		ShopGoodInfo shopGoodInfo = (ShopGoodInfo) arg0.getTag();
		String url = Urls.ProductBuy + shopGoodInfo.getPid();
		Intent intent = new Intent(context,WebActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

}
