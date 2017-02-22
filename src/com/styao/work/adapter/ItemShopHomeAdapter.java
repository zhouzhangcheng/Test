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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemShopHomeAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<ShopGoodInfo> data;
	private boolean isLogin;
	private boolean isCuxiao;

	public void setCuxiao(boolean isCuxiao) {
		this.isCuxiao = isCuxiao;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
		notifyDataSetChanged();
	}

	public ItemShopHomeAdapter(Context context, List<ShopGoodInfo> data) {
		super();
		this.context = context;
		this.data = data;
	}
	public ItemShopHomeAdapter(Context context, List<ShopGoodInfo> data,boolean isCuxiao) {
		super();
		this.context = context;
		this.data = data;
		this.isCuxiao = isCuxiao;
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
			convertView = View.inflate(context, R.layout.item_shop_home_good,
					null);
			viewHolder = new ViewHolder();
			viewHolder.item_shop_home_company = (TextView) convertView
					.findViewById(R.id.item_shop_home_company);
			viewHolder.item_shop_home_company_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_company_2);
			viewHolder.item_shop_home_linear = convertView
					.findViewById(R.id.item_shop_home_linear);
			viewHolder.item_shop_home_linear_2 = convertView
					.findViewById(R.id.item_shop_home_linear_2);
			viewHolder.item_shop_home_image = (ImageView) convertView
					.findViewById(R.id.item_shop_home_image);
			viewHolder.item_shop_home_image_2 = (ImageView) convertView
					.findViewById(R.id.item_shop_home_image_2);
			viewHolder.item_shop_home_name = (TextView) convertView
					.findViewById(R.id.item_shop_home_name);
			viewHolder.item_shop_home_name_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_name_2);
			viewHolder.item_shop_home_type = (TextView) convertView
					.findViewById(R.id.item_shop_home_type);
			viewHolder.item_shop_home_type_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_type_2);
			viewHolder.item_shop_home_price = (TextView) convertView
					.findViewById(R.id.item_shop_home_price);
			viewHolder.item_shop_home_price_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_price_2);
			viewHolder.item_shop_home_grossmargin = (TextView) convertView
					.findViewById(R.id.item_shop_home_grossmargin);
			viewHolder.item_shop_home_grossmargin_2 = (TextView) convertView
					.findViewById(R.id.item_shop_home_grossmargin_2);
			viewHolder.item_shop_home_grossmargin_linear = convertView
					.findViewById(R.id.item_shop_home_grossmargin_linear);
			viewHolder.item_shop_home_grossmargin_linear2 = convertView
					.findViewById(R.id.item_shop_home_grossmargin_linear_2);
			viewHolder.item_shoutui = convertView
					.findViewById(R.id.item_shop_home_shoutui);
			viewHolder.item_shoutui2 = convertView
					.findViewById(R.id.item_shop_home_shoutui_2);
			viewHolder.item_cuxiao = convertView
					.findViewById(R.id.item_shop_home_cuxiao);
			viewHolder.item_cuxiao2 = convertView
					.findViewById(R.id.item_shop_home_cuxiao_2);
			viewHolder.item_shop_home_add = convertView
					.findViewById(R.id.item_shop_home_add);
			viewHolder.item_shop_home_add2 = convertView
					.findViewById(R.id.item_shop_home_add_2);
			viewHolder.item_shop_home_normal = convertView
					.findViewById(R.id.item_shop_home_normal_1);
			viewHolder.item_shop_home_normal2 = convertView
					.findViewById(R.id.item_shop_home_normal_2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 加载第一行数据
		ShopGoodInfo shopGoodInfo = data.get(position * 2);
		ImageLoaderUtil.getInstance().displayImage(shopGoodInfo.getImageUrl(),
				viewHolder.item_shop_home_image);
		if (!isCuxiao) {
			viewHolder.item_shop_home_normal.setVisibility(View.VISIBLE);
			viewHolder.item_shop_home_add.setVisibility(View.GONE);
			TextViewUtils.setText(viewHolder.item_shop_home_name,
					shopGoodInfo.getDrugsBase_DrugName());
			TextViewUtils.setText(viewHolder.item_shop_home_type,
					shopGoodInfo.getDrugsBase_Specification());
			TextViewUtils.setText(viewHolder.item_shop_home_company,
					shopGoodInfo.getDrugsBase_Manufacturer());
			if (isLogin) {
				TextViewUtils.setText(viewHolder.item_shop_home_price,
						shopGoodInfo.getShopprice(), "￥");
			} else {
				viewHolder.item_shop_home_price.setText("登录可见");
			}
			if (shopGoodInfo.getIsKong().equals("True")) {
				viewHolder.item_shoutui.setVisibility(View.VISIBLE);
				viewHolder.item_shop_home_grossmargin_linear
						.setVisibility(View.VISIBLE);
			} else {
				viewHolder.item_shoutui.setVisibility(View.GONE);
				viewHolder.item_shop_home_grossmargin_linear
						.setVisibility(View.GONE);
			}

			if (shopGoodInfo.isIsSalesPromotion()) {
				viewHolder.item_cuxiao.setVisibility(View.VISIBLE);
			} else {
				viewHolder.item_cuxiao.setVisibility(View.GONE);
			}

			TextViewUtils.setText(viewHolder.item_shop_home_grossmargin,
					shopGoodInfo.getGrossMargin(), 2, "%");
			viewHolder.item_shop_home_linear.setOnClickListener(this);
			viewHolder.item_shop_home_linear.setTag(shopGoodInfo);
		} else {
			viewHolder.item_shop_home_normal.setVisibility(View.GONE);
			viewHolder.item_shop_home_add.setVisibility(View.VISIBLE);
		}

		if (position * 2 + 1 < data.size()) {
			viewHolder.item_shop_home_linear_2.setVisibility(View.VISIBLE);
			// 加载第一行数据
			ShopGoodInfo shopGoodInfo2 = data.get(position * 2 + 1);
			if (!isCuxiao) {
				viewHolder.item_shop_home_normal2.setVisibility(View.VISIBLE);
				viewHolder.item_shop_home_add2.setVisibility(View.GONE);
				TextViewUtils.setText(viewHolder.item_shop_home_name_2,
						shopGoodInfo2.getDrugsBase_DrugName());
				TextViewUtils.setText(viewHolder.item_shop_home_type_2,
						shopGoodInfo2.getDrugsBase_Specification());
				TextViewUtils.setText(viewHolder.item_shop_home_company_2,
						shopGoodInfo2.getDrugsBase_Manufacturer());
				ImageLoaderUtil.getInstance().displayImage(
						shopGoodInfo2.getImageUrl(),
						viewHolder.item_shop_home_image_2);
				if (isLogin) {
					TextViewUtils.setText(viewHolder.item_shop_home_price_2,
							shopGoodInfo2.getShopprice(), "￥");
				} else {
					viewHolder.item_shop_home_price_2.setText("登录可见");
				}
				TextViewUtils.setText(viewHolder.item_shop_home_grossmargin_2,
						shopGoodInfo2.getGrossMargin(), 2, "%");
				if (shopGoodInfo2.getIsKong().equals("True")) {
					viewHolder.item_shoutui2.setVisibility(View.VISIBLE);
					viewHolder.item_shop_home_grossmargin_linear2
							.setVisibility(View.VISIBLE);
				} else {
					viewHolder.item_shoutui2.setVisibility(View.GONE);
					viewHolder.item_shop_home_grossmargin_linear2
							.setVisibility(View.GONE);
				}
				if (shopGoodInfo2.isIsSalesPromotion()) {
					viewHolder.item_cuxiao2.setVisibility(View.VISIBLE);
				} else {
					viewHolder.item_cuxiao2.setVisibility(View.GONE);
				}
				viewHolder.item_shop_home_linear_2.setOnClickListener(this);
				viewHolder.item_shop_home_linear_2.setTag(shopGoodInfo2);
			} else {
				viewHolder.item_shop_home_normal2.setVisibility(View.GONE);
				viewHolder.item_shop_home_add2.setVisibility(View.VISIBLE);
			}

		} else {
			viewHolder.item_shop_home_linear_2.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	private class ViewHolder {
		View item_shop_home_linear, item_shop_home_linear_2,
				item_shop_home_grossmargin_linear,
				item_shop_home_grossmargin_linear2, item_shoutui,
				item_shoutui2, item_cuxiao, item_cuxiao2,
				item_shop_home_normal, item_shop_home_normal2,
				item_shop_home_add, item_shop_home_add2;
		ImageView item_shop_home_image, item_shop_home_image_2;
		TextView item_shop_home_name, item_shop_home_name_2,
				item_shop_home_type, item_shop_home_type_2,
				item_shop_home_company, item_shop_home_company_2,
				item_shop_home_price, item_shop_home_price_2,
				item_shop_home_grossmargin, item_shop_home_grossmargin_2;

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
