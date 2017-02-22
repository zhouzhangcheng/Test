package com.jock.tbshopcar.adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.InputType;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newgame.sdk.yyaost.CustomDialog;
import com.newgame.sdk.yyaost.ExtendedEditText;
import com.newgame.sdk.yyaost.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.entity.ShoppingCartBean1.StoreCartList.CartProductList;
import com.jock.tbshopcar.listener.OnShoppingCartChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.jock.tbshopcar.view.UIAlertView;

import java.text.SimpleDateFormat;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private List<ShoppingCartBean1> mListGoods1 = new ArrayList<ShoppingCartBean1>();
	private OnShoppingCartChangeListener mChangeListener;
	private boolean isSelectAll = false;
	static Double postnum;
	static String minnum;
	static int grouppos = 0;
	static int childpos = 0;
	static int grouppos1 = 0;
	static int childpos1 = 0;
	static String postpid = "";
	static int poststate = 0;

	static int iskeyinput = 0;
	static int isinput = 0;
	private CustomDialog dialog;

	// 定义变量，记录刷新前获得焦点的EditText所在的位置
	String mCurrentTouchedIndex = "";
	// 判断修改数量是否处理完
	static int ishasfinish = 0;

	public MyExpandableListAdapter(Context context) {
		mContext = context;
		configImageLoader();
	}

	public void setList(List<ShoppingCartBean1> mListGoods) {
		this.mListGoods1 = mListGoods;
		setSettleInfo();
	}

	public void setOnShoppingCartChangeListener(
			OnShoppingCartChangeListener changeListener) {
		this.mChangeListener = changeListener;
	}

	public View.OnClickListener getAdapterListener() {
		return listener;
	}

	public int getGroupCount() {
		// return mListGoods.size();
		// Log.v("getStoreCartList", mListGoods1.size()+"");
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return mListGoods1.get(0).getStoreCartList().size();
	}

	public int getChildrenCount(int groupPosition) {
		// return mListGoods.get(groupPosition).getGoods().size();
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return mListGoods1.get(0).getStoreCartList().get(groupPosition)
				.getCartProductList().size();
	}

	public Object getGroup(int groupPosition) {
		// return mListGoods.get(groupPosition);
		return mListGoods1.get(0).getStoreCartList().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		// return mListGoods.get(groupPosition).getGoods().get(childPosition);
		return mListGoods1.get(0).getStoreCartList().get(groupPosition)
				.getCartProductList().get(childPosition);
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public boolean hasStableIds() {
		return false;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = null;
		if (convertView == null) {
			holder = new GroupViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_elv_group_test, parent, false);
			holder.tvGroup = (TextView) convertView
					.findViewById(R.id.tvShopNameGroup);
			holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
			holder.ivCheckGroup = (ImageView) convertView
					.findViewById(R.id.ivCheckGroup);
			holder.LowestdeliveryAmount = (LinearLayout) convertView
					.findViewById(R.id.LowestdeliveryAmount);
			holder.tvbufahuo = (TextView) convertView
					.findViewById(R.id.tvbufahuo);
			holder.tvLowestFreeShippingAmount = (TextView) convertView
					.findViewById(R.id.tvLowestFreeShippingAmount);
			holder.tvbubaoyou = (TextView) convertView
					.findViewById(R.id.tvbubaoyou);
			holder.tvbaoyou = (TextView) convertView
					.findViewById(R.id.tvbaoyou);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.tvGroup.setText(mListGoods1.get(0).getStoreCartList()
				.get(groupPosition).getStoreInfo().get(0).getName());
		ShoppingCartBiz.checkItem(
				mListGoods1.get(0).getStoreCartList().get(groupPosition)
						.getIsSelected(), holder.ivCheckGroup);
		// 包邮及发货信息处理
		double productAmount = 0;// 店铺内商品总金额
		productAmount = ShoppingCartBiz.GetSelectedProductAmount(mListGoods1,
				false, groupPosition, 0);
		double LowestdeliveryAmount = Double.valueOf(mListGoods1.get(0)
				.getStoreCartList().get(groupPosition).getStoreInfo().get(0)
				.getLowestdeliveryAmount());
		if (productAmount < LowestdeliveryAmount)// 显示不发货标签
		{
			holder.LowestdeliveryAmount.setVisibility(View.VISIBLE);
			String bufahuoinfo = "低于￥" + LowestdeliveryAmount + "，该店不能发货！";
			holder.tvbufahuo.setText(bufahuoinfo);
			holder.tvbufahuo.setVisibility(View.VISIBLE);
		} else {
			holder.LowestdeliveryAmount.setVisibility(View.GONE);
		}
		double LowestFreeShippingAmount = Double.valueOf(mListGoods1.get(0)
				.getStoreCartList().get(groupPosition).getStoreInfo().get(0)
				.getLowestFreeShippingAmount());
		if (productAmount < LowestFreeShippingAmount)// 不包邮
		{
			holder.tvLowestFreeShippingAmount.setVisibility(View.VISIBLE);// 不包邮
			String bubaoyouinfo = "满￥" + LowestFreeShippingAmount + "包邮！";
			holder.tvbubaoyou.setText(bubaoyouinfo);
			holder.tvbubaoyou.setVisibility(View.VISIBLE);
			holder.tvbaoyou.setVisibility(View.GONE);
		} else// 包邮
		{
			holder.tvLowestFreeShippingAmount.setVisibility(View.GONE);
			holder.tvbubaoyou.setVisibility(View.GONE);
			holder.tvbaoyou.setVisibility(View.VISIBLE);
		}

		holder.ivCheckGroup.setTag(groupPosition);
		holder.tvGroup.setTag(groupPosition);
		holder.ivCheckGroup.setOnClickListener(listener);
		holder.tvEdit.setTag(groupPosition);
		holder.tvEdit.setOnClickListener(listener);
		holder.tvGroup.setOnClickListener(listener);
		return convertView;
	}

	/**
	 * child view
	 */

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_elv_child_test, parent, false);
			holder.tvChild = (TextView) convertView
					.findViewById(R.id.tvItemChild);
			holder.ivCheckGood = (ImageView) convertView
					.findViewById(R.id.ivCheckGood);
			holder.llGoodInfo = (LinearLayout) convertView
					.findViewById(R.id.llGoodInfo);
			holder.ivAdd = (ImageView) convertView.findViewById(R.id.numadd);
			holder.ivReduce = (ImageView) convertView
					.findViewById(R.id.numreduce);
			holder.tvGoodsParam = (TextView) convertView
					.findViewById(R.id.tvGoodsParam);
			holder.productimg = (ImageView) convertView
					.findViewById(R.id.ivGoods);
			holder.productnum = (ExtendedEditText) convertView
					.findViewById(R.id.productnum);
			holder.productnum.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
			holder.tvManufacturer = (TextView) convertView
					.findViewById(R.id.tvManufacturer);
			holder.productprice = (TextView) convertView
					.findViewById(R.id.productprice);
			holder.productpriceold = (TextView) convertView
					.findViewById(R.id.productpriceold);
			holder.jiajiagou = (RelativeLayout) convertView
					.findViewById(R.id.jiajiagou);
			holder.tvjiajiagou = (TextView) convertView
					.findViewById(R.id.tvjiajiagou);
			holder.onjiajiagou = (ImageView) convertView
					.findViewById(R.id.onjiajiagou);
			holder.closejiajiagou = (ImageView) convertView
					.findViewById(R.id.closejiajiagou);

			// 加价购商品处理
			holder.addpriceproduct = (RelativeLayout) convertView
					.findViewById(R.id.cishangpin);
			holder.ivciGoods = (ImageView) convertView
					.findViewById(R.id.ivciGoods);
			holder.tvciItemChild = (TextView) convertView
					.findViewById(R.id.tvciItemChild);
			holder.tvciManufacturer = (TextView) convertView
					.findViewById(R.id.tvciManufacturer);
			holder.tvciGoodsParam = (TextView) convertView
					.findViewById(R.id.tvciGoodsParam);
			holder.cishangpinnum = (TextView) convertView
					.findViewById(R.id.cishangpinnum);
			holder.cishangpin = (RelativeLayout) convertView
					.findViewById(R.id.cishangpin);
			// 店铺邮费及合计
			holder.llstorecount = (RelativeLayout) convertView
					.findViewById(R.id.llstorecount);
			holder.tvyoufei = (TextView) convertView
					.findViewById(R.id.tvyoufei);
			holder.tvstorecount = (TextView) convertView
					.findViewById(R.id.tvstorecount);
			// 删除商品按钮
			holder.productdel = (TextView) convertView
					.findViewById(R.id.productdel);
			holder.productunit = (TextView) convertView
					.findViewById(R.id.productunit);
			// 特价信息
			holder.tejia = (RelativeLayout) convertView
					.findViewById(R.id.tejia);
			holder.tvtejiamsg = (TextView) convertView
					.findViewById(R.id.tvtejiamsg);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		ArrayList<CartProductList> productlist = mListGoods1.get(0)
				.getStoreCartList().get(groupPosition).getCartProductList();
		boolean isChildSelected = mListGoods1.get(0).getStoreCartList()
				.get(groupPosition).getCartProductList().get(childPosition)
				.getOrderProductInfo().get(0).getIsSelect();
		String priceNew = "¥"
				+ productlist.get(childPosition).getOrderProductInfo().get(0)
						.getShopPrice();
		String num = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getBuyCount();
		String pdtDesc = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getDrugsBase_Specification();

		String goodName = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getDrugsBase_ProName();
		String Manufacturer = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getDrugsBase_Manufacturer();
		holder.tvChild.setText(goodName);
		holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
		holder.tvGoodsParam.setText(pdtDesc);
		holder.productnum.setText(num);
		holder.productnum.setTag(groupPosition + "," + childPosition);
		holder.tvManufacturer.setText(Manufacturer);
		holder.productprice.setText(priceNew);
		holder.productunit.setText(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getGoods_Unit());
		// 加价购信息
		int pmid = Integer.parseInt(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getpmid());

		if (pmid > 0)// 是加价购活动主商品
		{
			double addstock = Double.valueOf(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getaddproduct().get(0)
					.getstock());
			double stock = Double.valueOf(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getstock());
			// 最低换购数量
			double loweraddnum = 0;
			// 主商品开始数量
			double firstProudctStartNum = Double.valueOf(productlist
					.get(childPosition).getOrderProductInfo().get(0)
					.getaddpricebuymodel().get(0).getfirstProudctStartNum());
			// 主商品每满数量
			double firstProudctPerNum = Double.valueOf(productlist
					.get(childPosition).getOrderProductInfo().get(0)
					.getaddpricebuymodel().get(0).getfirstProudctPerNum());
			// 次商品数量
			double secondProudctNum = Double.valueOf(productlist
					.get(childPosition).getOrderProductInfo().get(0)
					.getaddpricebuymodel().get(0).getsecondProudctNum());
			String addpriceinfo = "";
			addpriceinfo = ShoppingCartBiz.GetAddPriceInfo(productlist,
					childPosition);
			String addproductnuminfo = "";
			addproductnuminfo = ShoppingCartBiz.Getaddproductnuminfo(
					productlist, childPosition);
			loweraddnum = secondProudctNum;

			holder.tvjiajiagou.setText(addpriceinfo);
			holder.cishangpinnum.setText(addproductnuminfo);
			if (addstock > secondProudctNum) {
				holder.jiajiagou.setVisibility(View.VISIBLE);
			} else {
				holder.jiajiagou.setVisibility(View.GONE);
			}
			if (Double.valueOf(num) >= firstProudctStartNum
					+ firstProudctPerNum
					&& addstock > loweraddnum
					&& stock >= firstProudctStartNum + firstProudctPerNum)// 满足开启条件
			{
				if (!productlist.get(childPosition).getOrderProductInfo()
						.get(0).getIsSelect()
						&& productlist.get(childPosition).getOrderProductInfo()
								.get(0).getaddpricebuystate() == 1) {
					productlist.get(childPosition).getOrderProductInfo().get(0)
							.setaddpricebuystate(0);
					ishasfinish++;
					mListGoods1.get(0).setishasfinish(ishasfinish);
					addproducts(
							Integer.parseInt(productlist.get(childPosition)
									.getOrderProductInfo().get(0).getPid()),
							Double.valueOf(productlist.get(childPosition)
									.getOrderProductInfo().get(0).getBuyCount()),
							0);

				} else {
					if (productlist.get(childPosition).getOrderProductInfo()
							.get(0).getaddpricebuystate() == 1) {
						ishasfinish++;
						mListGoods1.get(0).setishasfinish(ishasfinish);
						addproducts(
								Integer.parseInt(productlist.get(childPosition)
										.getOrderProductInfo().get(0).getPid()),
								Double.valueOf(productlist.get(childPosition)
										.getOrderProductInfo().get(0)
										.getBuyCount()), 1);
					}
				}
				if (productlist.get(childPosition).getOrderProductInfo().get(0)
						.getaddpricebuystate() > 0) {
					holder.onjiajiagou.setVisibility(View.VISIBLE);
					holder.closejiajiagou.setVisibility(View.GONE);
					holder.cishangpin.setVisibility(View.VISIBLE);
				} else {
					holder.onjiajiagou.setVisibility(View.GONE);
					holder.closejiajiagou.setVisibility(View.VISIBLE);
					holder.cishangpin.setVisibility(View.GONE);
				}
				holder.jiajiagou.setVisibility(View.VISIBLE);
			} else {
				if (productlist.get(childPosition).getOrderProductInfo().get(0)
						.getaddpricebuystate() == 1) {
					productlist.get(childPosition).getOrderProductInfo().get(0)
							.setaddpricebuystate(0);
					ishasfinish++;
					mListGoods1.get(0).setishasfinish(ishasfinish);
					addproducts(
							Integer.parseInt(productlist.get(childPosition)
									.getOrderProductInfo().get(0).getPid()),
							Double.valueOf(productlist.get(childPosition)
									.getOrderProductInfo().get(0).getBuyCount()),
							0);

				}
				if (stock >= firstProudctStartNum + firstProudctPerNum
						&& addstock > loweraddnum) {
					holder.jiajiagou.setVisibility(View.VISIBLE);
				} else {
					holder.jiajiagou.setVisibility(View.GONE);
				}
				holder.onjiajiagou.setVisibility(View.GONE);
				holder.closejiajiagou.setVisibility(View.GONE);
				holder.cishangpin.setVisibility(View.GONE);
			}
			// 加价购商品
			ImageLoader.getInstance().displayImage(
					productlist.get(childPosition).getOrderProductInfo().get(0)
							.getaddproduct().get(0).getSmallImageUrl(),
					holder.ivciGoods);
			holder.tvciItemChild.setText(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getaddproduct().get(0)
					.getName());
			holder.tvciManufacturer.setText(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getaddproduct().get(0)
					.getDrugsBase_Manufacturer());
			holder.tvciGoodsParam.setText(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getaddproduct().get(0)
					.getDrugsBase_Specification());
		} else// 不是加价购活动，隐藏所有的加价购信息
		{
			holder.jiajiagou.setVisibility(View.GONE);
			holder.cishangpin.setVisibility(View.GONE);
		}

		// 特价商品处理
		holder.tejia.setVisibility(View.GONE);
		holder.productpriceold.setText("");
		holder.productpriceold.getPaint().setFlags(0);
		if (productlist.get(childPosition).getOrderProductInfo().get(0)
				.getspecialpricemodel() != null) {
			if (productlist.get(childPosition).getOrderProductInfo().get(0)
					.getspecialpricemodel().size() > 0
					&& productlist.get(childPosition).getOrderProductInfo()
							.get(0).getspecialpricemodel().get(0).getpid() > 0) {
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				try {
					Date starttime = df.parse(productlist.get(childPosition)
							.getOrderProductInfo().get(0)
							.getspecialpricemodel().get(0).getstarttime());
					Date endtime = df.parse(productlist.get(childPosition)
							.getOrderProductInfo().get(0)
							.getspecialpricemodel().get(0).getendtime());
					if (now.getTime() >= starttime.getTime()
							&& now.getTime() <= endtime.getTime()) {
						int limittype = productlist.get(childPosition)
								.getOrderProductInfo().get(0)
								.getspecialpricemodel().get(0).getlimittype();
						if (limittype == 0) {
							productlist
									.get(childPosition)
									.getOrderProductInfo()
									.get(0)
									.setShopPrice(
											productlist.get(childPosition)
													.getOrderProductInfo()
													.get(0)
													.getspecialpricemodel()
													.get(0).getspeprice());
							holder.productprice.setText(productlist
									.get(childPosition).getOrderProductInfo()
									.get(0).getShopPrice());
							holder.productpriceold.setText(productlist
									.get(childPosition).getOrderProductInfo()
									.get(0).getspecialpricemodel().get(0)
									.getoldprice());
							holder.productpriceold.getPaint().setFlags(
									Paint.STRIKE_THRU_TEXT_FLAG);// 中画线

						} else if (limittype == 1||limittype == 2) {
							if (Double.valueOf(productlist.get(childPosition)
									.getOrderProductInfo().get(0)
									.getspecialpricemodel().get(0).getlimitnumber())-Double.valueOf(productlist.get(childPosition)
									.getOrderProductInfo().get(0)
									.getspecialpricemodel().get(0)
									.gethasbuycount()) < Double
									.valueOf(productlist.get(childPosition)
											.getOrderProductInfo().get(0)
											.getBuyCount())) {
								productlist
								.get(childPosition)
								.getOrderProductInfo()
								.get(0)
								.setShopPrice(
										productlist.get(childPosition)
												.getOrderProductInfo()
												.get(0)
												.getspecialpricemodel()
												.get(0).getoldprice());
								holder.tejia.setVisibility(View.VISIBLE);
								holder.tvtejiamsg.setText("超出限购数量"+productlist.get(childPosition)
										.getOrderProductInfo().get(0)
										.getspecialpricemodel().get(0).getlimitnumber()+",将按原价计算");
								setSettleInfo();
							} else {
								productlist
										.get(childPosition)
										.getOrderProductInfo()
										.get(0)
										.setShopPrice(
												productlist.get(childPosition)
														.getOrderProductInfo()
														.get(0)
														.getspecialpricemodel()
														.get(0).getspeprice());
								holder.productprice.setText(productlist
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getShopPrice());
								holder.productpriceold.setText(productlist
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getspecialpricemodel().get(0)
										.getoldprice());
								holder.productpriceold.getPaint().setFlags(
										Paint.STRIKE_THRU_TEXT_FLAG);// 中画线
								setSettleInfo();
							}

						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// 是否显示合计
		int storeproductcount = productlist.size();// 店铺内商品总数
		if (childPosition == storeproductcount - 1) {
			holder.llstorecount.setVisibility(View.VISIBLE);
			// 店铺商品合计
			double storeacount = 0;
			storeacount = Double.valueOf(ShoppingCartBiz
					.GetStoreCount(productlist));

			DecimalFormat df = new DecimalFormat("######0.00");
			String storeproductacount = "小计：￥" + df.format(storeacount);
			holder.tvstorecount.setText(storeproductacount);
			// 店铺邮费
			double fahuojine = Double.valueOf(mListGoods1.get(0)
					.getStoreCartList().get(groupPosition).getStoreInfo()
					.get(0).getLowestdeliveryAmount());
			double youfei = Double.valueOf(mListGoods1.get(0)
					.getStoreCartList().get(groupPosition).getStoreInfo()
					.get(0).getDefaultShipFee());
			String youfeiinfo = "邮费：￥" + youfei;
			holder.tvyoufei.setText(youfeiinfo);
			if (storeacount < fahuojine)// 不显示邮费
			{
				holder.tvyoufei.setVisibility(View.GONE);
			} else {
				double productAmount = 0;// 店铺内商品总金额
				productAmount = ShoppingCartBiz.GetSelectedProductAmount(
						mListGoods1, false, groupPosition, 0);
				double LowestFreeShippingAmount = Double.valueOf(mListGoods1
						.get(0).getStoreCartList().get(groupPosition)
						.getStoreInfo().get(0).getLowestFreeShippingAmount());
				if (productAmount < LowestFreeShippingAmount) {
					holder.tvyoufei.setVisibility(View.VISIBLE);
				} else {
					holder.tvyoufei.setVisibility(View.GONE);
				}
			}
		} else {
			holder.llstorecount.setVisibility(View.GONE);
		}

		holder.ivAdd.setTag(groupPosition + "," + childPosition);
		holder.ivReduce.setTag(groupPosition + "," + childPosition);

		holder.productdel.setTag(groupPosition + "," + childPosition);
		holder.onjiajiagou.setTag(groupPosition + "," + childPosition);
		holder.closejiajiagou.setTag(groupPosition + "," + childPosition);
		holder.productimg.setTag(groupPosition + "," + childPosition);
		holder.tvChild.setTag(groupPosition + "," + childPosition);

		ShoppingCartBiz.checkItem(isChildSelected, holder.ivCheckGood);

		ImageLoader.getInstance().displayImage(
				productlist.get(childPosition).getOrderProductInfo().get(0)
						.getSmallImageUrl(), holder.productimg);

		holder.ivCheckGood.setOnClickListener(listener);
		holder.productdel.setOnClickListener(listener);
		holder.ivAdd.setOnClickListener(listener);
		holder.ivReduce.setOnClickListener(listener);
		holder.productimg.setOnClickListener(listener);
		holder.onjiajiagou.setOnClickListener(listener);
		holder.closejiajiagou.setOnClickListener(listener);
		holder.tvChild.setOnClickListener(listener);

		holder.productnum.setTag(groupPosition + "," + childPosition);
		holder.productnum.setOnClickListener(listener);
		return convertView;
	}

	public boolean isChildSelectable(int i, int i1) {
		return false;
	}

	View.OnClickListener listener = new View.OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			// main
			case R.id.ivSelectAll:
				isSelectAll = ShoppingCartBiz.selectAll(mListGoods1,
						isSelectAll, (ImageView) v);
				setSettleInfo();
				notifyDataSetChanged();
				String recordidall = "";
				String isselectall = "0";
				if (isSelectAll) {
					isselectall = "1";
				}
				for (int i = 0; i < mListGoods1.get(0).getStoreCartList()
						.size(); i++) {
					for (int j = 0; j < mListGoods1.get(0).getStoreCartList()
							.get(i).getCartProductList().size(); j++) {
						recordidall = recordidall
								+ mListGoods1.get(0).getStoreCartList().get(i)
										.getCartProductList().get(j)
										.getOrderProductInfo().get(0)
										.getRecordId() + ",";
					}
				}
				if (recordidall.length() > 0) {
					recordidall = recordidall.substring(0,
							recordidall.length() - 1);
					selectstoreproduct(recordidall, isselectall);
				}
				break;
			case R.id.productnum:
				String tagchanenum = String.valueOf(v.getTag());
				inputTitleDialog(tagchanenum,
						((TextView) (((View) (v.getParent()))
								.findViewById(R.id.productnum))));
				// group
				break;
			case R.id.tvEdit:// 切换界面，属于特殊处理，假如没打算切换界面，则不需要这块代码
				int groupPositionstore = Integer.parseInt(String.valueOf(v
						.getTag()));
				showStoreDelDialog(groupPositionstore);
				break;
			case R.id.ivCheckGroup:
				int groupPosition3 = Integer
						.parseInt(String.valueOf(v.getTag()));
				isSelectAll = ShoppingCartBiz.selectGroup(mListGoods1,
						groupPosition3);
				selectAll();
				setSettleInfo();
				notifyDataSetChanged();
				String recordids = "";
				String isselects = "";
				for (int i = 0; i < mListGoods1.get(0).getStoreCartList()
						.get(groupPosition3).getCartProductList().size(); i++) {
					recordids = recordids
							+ mListGoods1.get(0).getStoreCartList()
									.get(groupPosition3).getCartProductList()
									.get(i).getOrderProductInfo().get(0)
									.getRecordId() + ",";
					if (mListGoods1.get(0).getStoreCartList()
							.get(groupPosition3).getIsSelected()) {
						isselects = "1,";
					} else {
						isselects = "0,";
					}
				}
				if (recordids.length() > 0 && isselects.length() > 0) {
					recordids = recordids.substring(0, recordids.length() - 1);
					isselects = isselects.substring(0, isselects.length() - 1);
					selectstoreproduct(recordids, isselects);
				}
				break;
			// child
			case R.id.ivCheckGood:
				String tag = String.valueOf(v.getTag());
				if (tag.contains(",")) {
					String s[] = tag.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					isSelectAll = ShoppingCartBiz.selectOne(mListGoods1,
							groupPosition, childPosition);
					selectAll();
					selectoneproduct(
							Integer.parseInt(mListGoods1.get(0)
									.getStoreCartList().get(groupPosition)
									.getCartProductList().get(childPosition)
									.getOrderProductInfo().get(0).getRecordId()),
							mListGoods1.get(0).getStoreCartList()
									.get(groupPosition).getCartProductList()
									.get(childPosition).getOrderProductInfo()
									.get(0).getIsSelect());
					setSettleInfo();
					notifyDataSetChanged();
				}
				break;
			case R.id.tvItemChild:
				String gotosearch1 = String.valueOf(v.getTag());
				if (gotosearch1.contains(",")) {
					String s[] = gotosearch1.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					String pid = mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList()
							.get(childPosition).getOrderProductInfo().get(0)
							.getPid();
					gotoSearch("" + pid);
				} else {
					ToastHelper.getInstance().displayToastWithQuickClose(
							"商品不存在");
				}
				break;
			case R.id.onjiajiagou:
				String tagon = String.valueOf(v.getTag());
				if (tagon.contains(",")) {
					String s[] = tagon.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					mListGoods1.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0)
							.setaddpricebuystate(0);

					ShoppingCartBean1.StoreCartList.CartProductList goods = mListGoods1
							.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition);
					ishasfinish++;
					mListGoods1.get(0).setishasfinish(ishasfinish);
					addproducts(
							Integer.parseInt(goods.getOrderProductInfo().get(0)
									.getPid()),
							Double.valueOf(goods.getOrderProductInfo().get(0)
									.getBuyCount()), 0);

					setSettleInfo();
					notifyDataSetChanged();
				}
				break;
			case R.id.closejiajiagou:
				String tagclose = String.valueOf(v.getTag());
				if (tagclose.contains(",")) {
					String s[] = tagclose.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					mListGoods1.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0)
							.setaddpricebuystate(1);

					ShoppingCartBean1.StoreCartList.CartProductList goods = mListGoods1
							.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition);
					ishasfinish++;
					mListGoods1.get(0).setishasfinish(ishasfinish);
					addproducts(
							Integer.parseInt(goods.getOrderProductInfo().get(0)
									.getPid()),
							Double.valueOf(goods.getOrderProductInfo().get(0)
									.getBuyCount()), 1);

					setSettleInfo();
					notifyDataSetChanged();
				}
				break;
			case R.id.numadd:
				// iskeyinput = 1;
				isinput = 0;
				String tagadd = String.valueOf(v.getTag());
				if (tagadd.contains(",")) {
					String s[] = tagadd.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					iskeyinput = Integer.parseInt(mListGoods1.get(0)
							.getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0).getPid());
					String isnumadd = ShoppingCartBiz.addOrReduceGoodsNum(
							true,
							mListGoods1.get(0).getStoreCartList()
									.get(groupPosition).getCartProductList()
									.get(childPosition),
							((TextView) (((View) (v.getParent()))
									.findViewById(R.id.productnum))));
					postnum = Double.valueOf(mListGoods1.get(0)
							.getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0).getBuyCount());
					ShoppingCartBean1.StoreCartList.CartProductList goods = mListGoods1
							.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition);

					if (!(goods.getOrderProductInfo().get(0).getIsSelect())) {
						isSelectAll = ShoppingCartBiz.selectOne(mListGoods1,
								groupPosition, childPosition);
						selectAll();
						selectoneproduct(
								Integer.parseInt(mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getRecordId()), mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getIsSelect());
					}
					if (isnumadd.length() == 0)// 可以增加数量
					{
						ishasfinish++;
						mListGoods1.get(0).setishasfinish(ishasfinish);
						addproducts(
								Integer.parseInt(goods.getOrderProductInfo()
										.get(0).getPid()),
								Double.valueOf(goods.getOrderProductInfo()
										.get(0).getBuyCount()), goods
										.getOrderProductInfo().get(0)
										.getaddpricebuystate());
					} else {
						ToastHelper.getInstance()._toast(isnumadd);
					}
					setSettleInfo();
					notifyDataSetChanged();

				}
				break;
			case R.id.numreduce:

				isinput = 0;
				String tagreduce = String.valueOf(v.getTag());
				if (tagreduce.contains(",")) {
					String s[] = tagreduce.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					iskeyinput = Integer.parseInt(mListGoods1.get(0)
							.getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0).getPid());
					ShoppingCartBean1.StoreCartList.CartProductList goods = mListGoods1
							.get(0).getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition);
					String isreduce = ShoppingCartBiz.addOrReduceGoodsNum(
							false, goods, ((TextView) (((View) (v.getParent()))
									.findViewById(R.id.productnum))));
					postnum = Double.valueOf(mListGoods1.get(0)
							.getStoreCartList().get(groupPosition)
							.getCartProductList().get(childPosition)
							.getOrderProductInfo().get(0).getBuyCount());
					if (!(goods.getOrderProductInfo().get(0).getIsSelect())) {
						isSelectAll = ShoppingCartBiz.selectOne(mListGoods1,
								groupPosition, childPosition);
						selectAll();
						selectoneproduct(
								Integer.parseInt(mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getRecordId()), mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getIsSelect());
					}
					if (isreduce.length() == 0)// 可以减少数量
					{
						ishasfinish++;
						mListGoods1.get(0).setishasfinish(ishasfinish);
						addproducts(
								Integer.parseInt(goods.getOrderProductInfo()
										.get(0).getPid()),
								Double.valueOf(goods.getOrderProductInfo()
										.get(0).getBuyCount()), goods
										.getOrderProductInfo().get(0)
										.getaddpricebuystate());
					} else {
						ToastHelper.getInstance()._toast(isreduce);
					}
					setSettleInfo();
					notifyDataSetChanged();

				}
				break;
			case R.id.ivGoods:
				String gotosearch = String.valueOf(v.getTag());
				if (gotosearch.contains(",")) {
					String s[] = gotosearch.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					String pid = mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList()
							.get(childPosition).getOrderProductInfo().get(0)
							.getPid();
					// Intent intent = new Intent(mContext,
					// SearchActitity.class);// 界面跳转
					// intent.putExtra("fromcategories", "cartToProductbuy," +
					// pid);
					// mContext.startActivity(intent);
					gotoSearch("" + pid);
				} else {
					ToastHelper.getInstance()._toast("商品不存在");
				}
				break;
			case R.id.tvShopNameGroup:
				int gotostoreindex = Integer
						.parseInt(String.valueOf(v.getTag()));
				String storeid = mListGoods1.get(0).getStoreCartList()
						.get(gotostoreindex).getStoreInfo().get(0).getStoreId();
				// Intent intent = new Intent(mContext, SearchActitity.class);//
				// 界面跳转
				// intent.putExtra("fromcategories", "mycollections," +
				// storeid);
				// mContext.startActivity(intent);
				onOpenStore(storeid + "");
				break;
			case R.id.productdel:// 删除商品
				String tagPos = String.valueOf(v.getTag());
				if (tagPos.contains(",")) {
					String s[] = tagPos.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					showDelDialog(groupPosition, childPosition);
				}
				break;
			}
		}
	};

	private void selectAll() {
		if (mChangeListener != null) {
			mChangeListener.onSelectItem(isSelectAll);
		}
	}

	private void gotoSearch(String pid) {
		if (mChangeListener != null) {
			mChangeListener.onOpenActivity(pid);
		}
	}

	private void onOpenStore(String storeid) {
		if (mChangeListener != null) {
			mChangeListener.onOpenStore(storeid);
		}
	}

	private void setSettleInfo() {
		String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods1);
		// 删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
		if (mChangeListener != null && infos != null) {
			mChangeListener.onDataChange(infos[0], infos[1]);
		}
	}

	// 删除单个品种
	private void showDelDialog(final int groupPosition, final int childPosition) {
		final UIAlertView delDialog = new UIAlertView(mContext, "温馨提示",
				"确认删除该商品吗?", "取消", "确定");
		delDialog.show();

		delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

			public void doLeft() {
				delDialog.dismiss();
			}

			public void doRight() {
				String productID = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList()
						.get(childPosition).getOrderProductInfo().get(0)
						.getPid();
				ShoppingCartBiz.delGood(productID);
				delGoods(groupPosition, childPosition);
				setSettleInfo();
				notifyDataSetChanged();
				delDialog.dismiss();
				deleteproduct(productID);
			}
		});
	}

	// 删除店铺
	private void showStoreDelDialog(final int groupPosition) {
		final UIAlertView delDialog = new UIAlertView(mContext, "温馨提示",
				"确认删除该店铺商品吗?", "取消", "确定");
		delDialog.show();

		delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

			public void doLeft() {
				delDialog.dismiss();
			}

			public void doRight() {
				int size = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList().size();
				int storeid = Integer.parseInt(mListGoods1.get(0)
						.getStoreCartList().get(groupPosition).getStoreInfo()
						.get(0).getStoreId());
				for (int i = 0; i < size; i++) {
					String productID = mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList().get(0)
							.getOrderProductInfo().get(0).getPid();
					ShoppingCartBiz.delGood(productID);
					delGoods(groupPosition, 0);
				}
				setSettleInfo();
				notifyDataSetChanged();
				delDialog.dismiss();
				deletestore(storeid);
			}
		});
	}

	// 增加商品数量
	public void addproducts(final int pid, final double buyCount,
			final int addpricebuystate) {
		new Thread(new Runnable() {
			public void run() {
				try {

					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl + "/webapi/ChangeProductCount?pid="
							+ pid + "&buyCount=" + buyCount
							+ "&addpricebuystate=" + addpricebuystate);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("POST");
					// conn.getResponseCode();
					Log.v("url", url.toString());
					Log.v("time1", new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date()));
					if (conn.getResponseCode() == 200) {
						if (ishasfinish < 1) {
							ishasfinish = 1;
						}
						ishasfinish--;

						mListGoods1.get(0).setishasfinish(ishasfinish);
						Log.v("time2",
								new SimpleDateFormat("yyyyMMddHHmmssSSS")
										.format(new Date()));
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"utf-8"));
						String line = "";
						String str = "";
						while (null != (line = br.readLine())) {
							str += line;
						}
						Log.v("return", str);
						br.close();
						conn.disconnect();
					}

				} catch (Exception e) {
					// TODO: handle exception
					ishasfinish = 0;
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	// 单个商品选中
	public void selectoneproduct(final int recordid, final boolean isselect) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					int check = 0;
					if (isselect) {
						check = 1;
					}
					URL url = new URL(sturl
							+ "webapi/ChangeProducChecked?recordid=" + recordid
							+ "&Checked=" + check);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("POST");
					conn.getResponseCode();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	// 店铺商品选中
	public void selectstoreproduct(final String recordid, final String isselect) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl
							+ "webapi/ChangeProducChecked?recordid=" + recordid
							+ "&Checked=" + isselect);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("POST");
					conn.getResponseCode();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	// 删除单个品种
	public void deleteproduct(final String pid) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl + "webapi/DelProduct?pid=" + pid);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("GET");
					conn.getResponseCode();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	// 删除店铺
	public void deletestore(final int storeid) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl + "webapi/DelProduct?storeid="
							+ storeid);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("GET");
					conn.getResponseCode();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	private void delGoods(int groupPosition, int childPosition) {
		mListGoods1.get(0).getStoreCartList().get(groupPosition)
				.getCartProductList().remove(childPosition);
		if (mListGoods1.get(0).getStoreCartList().get(groupPosition)
				.getCartProductList().size() == 0) {
			mListGoods1.get(0).getStoreCartList().remove(groupPosition);
		}
		notifyDataSetChanged();
	}

	class GroupViewHolder {
		TextView tvGroup;
		TextView tvEdit;
		ImageView ivCheckGroup;
		LinearLayout LowestdeliveryAmount;// 不满发货金额
		TextView tvbufahuo;// 不发货信息
		TextView tvLowestFreeShippingAmount;// 不包邮标签
		TextView tvbubaoyou;// 不包邮信息
		TextView tvbaoyou;// 包邮标签
	}

	class ChildViewHolder {
		/** 商品名称 */
		TextView tvChild;
		/** 商品规格 */
		TextView tvGoodsParam;
		/** 商品数量 */
		ExtendedEditText productnum;
		/** 生产厂家 */
		TextView tvManufacturer;
		/** 商品价格 */
		TextView productprice;
		/** 加价购信息 **/
		RelativeLayout jiajiagou;
		/** 加价购信息 **/
		TextView tvjiajiagou;
		/** 开关 **/
		ImageView onjiajiagou;
		/** 开关 **/
		ImageView closejiajiagou;

		/** 选中 */
		ImageView ivCheckGood;
		/** 非编辑状态 */
		LinearLayout llGoodInfo;
		/** 编辑状态 */
		RelativeLayout rlEditStatus;
		/** +1 */
		ImageView ivAdd;
		/** -1 */
		ImageView ivReduce;
		ImageView productimg;
		/** 删除 */
		TextView productdel;
		/** 旧价格 */
		TextView productpriceold;
		/** 商品状态的数量 */
		TextView tvNum;
		/** 加价购商品布局 */
		RelativeLayout addpriceproduct;
		/** 加价购次商品布局 */
		RelativeLayout cishangpin;
		/** 加价购次商品图片 */
		ImageView ivciGoods;
		/** 加价购次商品名称 */
		TextView tvciItemChild;
		/** 加价购次商品生产厂家 */
		TextView tvciManufacturer;
		/** 加价购次商品规格 */
		TextView tvciGoodsParam;
		/** 加价购次商品数量 */
		TextView cishangpinnum;
		/** 店铺内商品总数 */
		RelativeLayout llstorecount;
		/** 店铺邮费 */
		TextView tvyoufei;
		/** 店铺合计 */
		TextView tvstorecount;
		/** 单位 */
		TextView productunit;

		/** 特价 */
		RelativeLayout tejia;
		/** 特价显示信息 */
		TextView tvtejiamsg;
	}

	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.nopic) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.nopic) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.nopic) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mContext.getApplicationContext())
				.defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	// private void inputTitleDialog(final String str,final TextView tvnum) {
	//
	// final EditText inputServer = new EditText(mContext);
	// inputServer.setFocusable(true);
	// inputServer.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	// builder.setTitle("输入需要购买的数量").setIcon(
	// R.drawable.ic_checked).setView(inputServer).setNegativeButton(
	// "取消", null);
	// builder.setPositiveButton("确定",
	// new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// String inputName = inputServer.getText().toString();
	// String s[] = str.split(",");
	// int groupPosition = Integer.parseInt(s[0]);
	// int childPosition = Integer.parseInt(s[1]);
	// // 此处为失去焦点时的处理内容
	// int SellType = Integer.valueOf(mListGoods1.get(0)
	// .getStoreCartList().get(groupPosition)
	// .getCartProductList().get(childPosition)
	// .getOrderProductInfo().get(0).getSellType());
	// double thisstock = Double.valueOf(mListGoods1.get(0)
	// .getStoreCartList().get(groupPosition)
	// .getCartProductList().get(childPosition)
	// .getOrderProductInfo().get(0).getstock());
	// minnum = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo().get(0)
	// .getMinBuyNum();// 最小销售
	// postpid = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo().get(0)
	// .getPid();
	// poststate = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo().get(0)
	// .getaddpricebuystate();
	// double addminnum = 1;
	// String oldnum = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo().get(0)
	// .getBuyCount();// 输入前数量
	// boolean isbiaopin = true;
	// double bagcount = Double.valueOf(mListGoods1.get(0)
	// .getStoreCartList().get(groupPosition)
	// .getCartProductList().get(childPosition)
	// .getOrderProductInfo().get(0).getBagCount());
	// if (bagcount > 0) {
	// isbiaopin = false;
	// addminnum = bagcount;
	// }
	// if (SellType == 2)// 中包装
	// {
	// minnum = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo()
	// .get(0).getGoods_Pcs_Small();
	// addminnum = Double.valueOf(minnum);
	// } else if (SellType == 3)// 件装
	// {
	// minnum = mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo()
	// .get(0).getGoods_Pcs();
	// addminnum = Double.valueOf(minnum);
	// }
	// String num = inputName;
	// Pattern p = Pattern.compile("[0-9]*");
	// Matcher m = p.matcher(num);
	// if (num.length() > 0) {// 输入的是数字
	// if (Double.valueOf(num) - Double.valueOf(oldnum) > 0.001
	// || Double.valueOf(oldnum) - Double.valueOf(num) > 0.001) {
	//
	// if (SellType == 2) {
	// if (Double.valueOf(num)
	// % Double.valueOf(minnum) > 0.001
	// && Double.valueOf(num) <= thisstock) {
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须是中包装" + minnum
	// + "的整数倍");
	// num = oldnum;
	// }
	// } else if (SellType == 3) {
	// if (Double.valueOf(num)
	// % Double.valueOf(minnum) > 0.001
	// && Double.valueOf(num) <= thisstock) {
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须是件装" + minnum
	// + "的整数倍");
	// num = oldnum;
	//
	// }
	// } else {
	// if (Double.valueOf(num) < Double
	// .valueOf(minnum)
	// && Double.valueOf(num) <= thisstock) {
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须大于最小销售数量" + minnum);
	// num = oldnum;
	//
	// }
	// if (!isbiaopin) {
	// if (Double.valueOf(num)
	// % Double.valueOf(bagcount) > 0.001
	// && Double.valueOf(num) <= thisstock) {
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须是袋装量" + bagcount
	// + "的整数倍");
	// num = oldnum;
	// }
	// } else {
	// if (Double.valueOf(num) % addminnum > 0.001
	// && Double.valueOf(num) <= thisstock) {
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须是1" + "的整数倍");
	// num = oldnum;
	// }
	// }
	//
	// }
	// if (num.substring(0, 1).equals("0")) {
	// try {
	//
	// if (Double.valueOf(num) <= 0) {
	// num = oldnum;
	// ToastHelper
	// .getInstance()
	// .displayToastWithQuickClose(
	// "输入数量必须大于最小购买数" + minnum);
	// }
	// } catch (Exception ex) {
	// //
	// }
	// }
	//
	// // 判断库存
	// if (thisstock < Double.valueOf(num)) {
	// String buycount = mListGoods1.get(0)
	// .getStoreCartList().get(groupPosition)
	// .getCartProductList()
	// .get(childPosition)
	// .getOrderProductInfo().get(0)
	// .getBuyCount();
	// ToastHelper.getInstance()
	// .displayToastWithQuickClose(
	// "库存不足，当前库存为" + thisstock);
	// num = oldnum;
	// } else {
	// postnum = Double.valueOf(num);
	// if (postnum <= 0) {
	// num = oldnum;
	// }
	// }
	// mListGoods1.get(0).getStoreCartList()
	// .get(groupPosition).getCartProductList()
	// .get(childPosition).getOrderProductInfo()
	// .get(0).setBuyCount(num);
	// addproducts(Integer.parseInt(postpid),
	// Double.valueOf(num), poststate);
	// tvnum.setText(num);
	// setSettleInfo();
	// notifyDataSetChanged();
	// }
	// } else// 输入非法
	// {
	// num = oldnum;
	// tvnum.setText(num);
	// }
	// }
	// });
	// builder.show();
	// Timer timer = new Timer();
	// timer.schedule(new TimerTask() {
	//
	// @Override
	// public void run() {
	// if(inputServer!=null){
	// //设置可获得焦点
	// inputServer.setFocusable(true);
	// inputServer.setFocusableInTouchMode(true);
	// //请求获得焦点
	// inputServer.requestFocus();
	// //调用系统输入法
	// InputMethodManager inputManager = (InputMethodManager) inputServer
	// .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	// inputManager.showSoftInput(inputServer, 0);
	// }
	// } }, 300);
	// }
	private void inputTitleDialog(final String str, final TextView tvnum) {

		CustomDialog.Builder customBuilder = new CustomDialog.Builder(mContext);
		customBuilder.setTitle("数量选择").setNegativeButton("确定")
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialog = customBuilder.create();
		Button btnNegative = (Button) dialog.findViewById(R.id.negativeButton);
		ImageView alertreduce = (ImageView) dialog
				.findViewById(R.id.alertreduce);
		ImageView alertadd = (ImageView) dialog.findViewById(R.id.alertadd);
		final EditText etContent = (EditText) dialog
				.findViewById(R.id.et_content);
		TextView stork = (TextView) dialog.findViewById(R.id.stork);
		String s1[] = str.split(",");
		int groupP = Integer.parseInt(s1[0]);
		int childP = Integer.parseInt(s1[1]);
		String storkstr = mListGoods1.get(0).getStoreCartList().get(groupP)
				.getCartProductList().get(childP).getOrderProductInfo().get(0)
				.getstock();

		stork.setText("库存" + storkstr + "   最低购买数"
				+ ShoppingCartBiz.GetMinBuyNum(mListGoods1, groupP, childP));
		etContent.setText(mListGoods1.get(0).getStoreCartList().get(groupP)
				.getCartProductList().get(childP).getOrderProductInfo().get(0)
				.getBuyCount());
		etContent.setSelection(etContent.length());
		etContent.selectAll();
		alertreduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String s[] = str.split(",");
				int groupPosition = Integer.parseInt(s[0]);
				int childPosition = Integer.parseInt(s[1]);
				ShoppingCartBiz.AlertAddOrReduceGoodsNum(false, mListGoods1
						.get(0).getStoreCartList().get(groupPosition)
						.getCartProductList().get(childPosition), etContent);
				etContent.setSelection(etContent.length());
			}
		});
		alertadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String s[] = str.split(",");
				int groupPosition = Integer.parseInt(s[0]);
				int childPosition = Integer.parseInt(s[1]);
				ShoppingCartBiz.AlertAddOrReduceGoodsNum(
						true,
						mListGoods1.get(0).getStoreCartList()
								.get(groupPosition).getCartProductList()
								.get(childPosition), etContent);
				etContent.setSelection(etContent.length());
			}
		});
		btnNegative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String inputName = etContent.getText().toString();
				String s[] = str.split(",");
				int groupPosition = Integer.parseInt(s[0]);
				int childPosition = Integer.parseInt(s[1]);
				// 此处为失去焦点时的处理内容
				int SellType = Integer.valueOf(mListGoods1.get(0)
						.getStoreCartList().get(groupPosition)
						.getCartProductList().get(childPosition)
						.getOrderProductInfo().get(0).getSellType());
				double thisstock = Double.valueOf(mListGoods1.get(0)
						.getStoreCartList().get(groupPosition)
						.getCartProductList().get(childPosition)
						.getOrderProductInfo().get(0).getstock());
				minnum = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList()
						.get(childPosition).getOrderProductInfo().get(0)
						.getMinBuyNum();// 最小销售
				postpid = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList()
						.get(childPosition).getOrderProductInfo().get(0)
						.getPid();
				poststate = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList()
						.get(childPosition).getOrderProductInfo().get(0)
						.getaddpricebuystate();
				double addminnum = 1;
				String oldnum = mListGoods1.get(0).getStoreCartList()
						.get(groupPosition).getCartProductList()
						.get(childPosition).getOrderProductInfo().get(0)
						.getBuyCount();// 输入前数量
				boolean isbiaopin = true;
				double bagcount = Double.valueOf(mListGoods1.get(0)
						.getStoreCartList().get(groupPosition)
						.getCartProductList().get(childPosition)
						.getOrderProductInfo().get(0).getBagCount());
				if (bagcount > 0) {
					isbiaopin = false;
					addminnum = bagcount;
				}
				if (SellType == 2)// 中包装
				{
					minnum = mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList()
							.get(childPosition).getOrderProductInfo().get(0)
							.getGoods_Pcs_Small();
					addminnum = Double.valueOf(minnum);
				} else if (SellType == 3)// 件装
				{
					minnum = mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList()
							.get(childPosition).getOrderProductInfo().get(0)
							.getGoods_Pcs();
					addminnum = Double.valueOf(minnum);
				}
				String num = inputName;
				boolean cannodify = true;
				if (num.length() > 0) {// 输入的是数字
					if (Double.valueOf(num) - Double.valueOf(oldnum) > 0.001
							|| Double.valueOf(oldnum) - Double.valueOf(num) > 0.001) {

						if (SellType == 2) {
							if (Double.valueOf(num) % Double.valueOf(minnum) > 0.001
									&& Double.valueOf(num) <= thisstock) {
								ToastHelper.getInstance()
										.displayToastWithQuickClose(
												"输入数量必须是中包装" + minnum + "的整数倍");
								cannodify = false;
								num = oldnum;
							}
						} else if (SellType == 3) {
							if (Double.valueOf(num) % Double.valueOf(minnum) > 0.001
									&& Double.valueOf(num) <= thisstock) {
								ToastHelper.getInstance()
										.displayToastWithQuickClose(
												"输入数量必须是件装" + minnum + "的整数倍");
								cannodify = false;
								num = oldnum;

							}
						} else {
							if (Double.valueOf(num) < Double.valueOf(minnum)
									&& Double.valueOf(num) <= thisstock) {
								ToastHelper.getInstance()
										.displayToastWithQuickClose(
												"输入数量必须大于最小销售数量" + minnum);
								cannodify = false;
								num = oldnum;

							}
							if (!isbiaopin) {
								if (Double.valueOf(num)
										% Double.valueOf(bagcount) > 0.001
										&& Double.valueOf(num) <= thisstock) {
									ToastHelper.getInstance()
											.displayToastWithQuickClose(
													"输入数量必须是袋装量" + bagcount
															+ "的整数倍");
									cannodify = false;
									num = oldnum;
								}
							} else {
								if (Double.valueOf(num) % addminnum > 0.001
										&& Double.valueOf(num) <= thisstock) {
									ToastHelper.getInstance()
											.displayToastWithQuickClose(
													"输入数量必须是1" + "的整数倍");
									cannodify = false;
									num = oldnum;
								}
							}

						}
						if (num.substring(0, 1).equals("0")) {
							try {

								if (Double.valueOf(num) <= 0) {
									num = oldnum;
									ToastHelper.getInstance()
											.displayToastWithQuickClose(
													"输入数量必须大于最小购买数" + minnum);
									cannodify = false;
								}
							} catch (Exception ex) {
								//
							}
						}

						// 判断库存
						if (thisstock < Double.valueOf(num)) {
							String buycount = mListGoods1.get(0)
									.getStoreCartList().get(groupPosition)
									.getCartProductList().get(childPosition)
									.getOrderProductInfo().get(0).getBuyCount();
							ToastHelper.getInstance()
									.displayToastWithQuickClose(
											"库存不足，当前库存为" + thisstock);
							cannodify = false;
							num = oldnum;
						} else {
							postnum = Double.valueOf(num);
							if (postnum <= 0) {
								num = oldnum;
							}
						}

					} else {
						dialog.dismiss();
					}
					if (cannodify) {
						mListGoods1.get(0).getStoreCartList()
								.get(groupPosition).getCartProductList()
								.get(childPosition).getOrderProductInfo()
								.get(0).setBuyCount(num);
						addproducts(Integer.parseInt(postpid),
								Double.valueOf(num), poststate);
						tvnum.setText(num);
						setSettleInfo();
						notifyDataSetChanged();
						dialog.dismiss();
					}
					if (!(mListGoods1.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList()
							.get(childPosition).getOrderProductInfo().get(0)
							.getIsSelect())) {
						isSelectAll = ShoppingCartBiz.selectOne(mListGoods1,
								groupPosition, childPosition);
						selectAll();
						selectoneproduct(
								Integer.parseInt(mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getRecordId()), mListGoods1.get(0)
										.getStoreCartList().get(groupPosition)
										.getCartProductList()
										.get(childPosition)
										.getOrderProductInfo().get(0)
										.getIsSelect());
					}
				} else// 输入非法
				{
					ToastHelper.getInstance()
							.displayToastWithQuickClose("输入无效");
				}

			}
		});
		dialog.show();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (etContent != null) {
					// 设置可获得焦点
					etContent.setFocusable(true);
					etContent.setFocusableInTouchMode(true);
					// 请求获得焦点
					etContent.requestFocus();
					// 调用系统输入法
					InputMethodManager inputManager = (InputMethodManager) etContent
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(etContent, 0);
				}
			}
		}, 300);
	}
}
