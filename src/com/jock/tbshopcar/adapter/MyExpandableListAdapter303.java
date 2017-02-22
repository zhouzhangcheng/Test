package com.jock.tbshopcar.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newgame.sdk.yyaost.ExtendedEditText;
import com.newgame.sdk.yyaost.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jock.tbshopcar.entity.WisdomBean303;
import com.jock.tbshopcar.entity.WisdomBean303.StoreList.ProductsList;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.jock.tbshopcar.view.UIAlertView;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter303 extends BaseExpandableListAdapter {
	private Context mContext;
	private List<WisdomBean303> mListGoods1 = new ArrayList<WisdomBean303>();
	private OnWisdomChangeListener mChangeListener;
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

	// 定义变量，记录刷新前获得焦点的EditText所在的位置
	String mCurrentTouchedIndex = "";
	// 判断修改数量是否处理完
	static int ishasfinish = 0;

	public MyExpandableListAdapter303(Context context) {
		mContext = context;
		configImageLoader();
	}

	public void setList(List<WisdomBean303> mListGoods) {
		this.mListGoods1 = mListGoods;
		setSettleInfo();
	}

	public void setOnWisdom301ChangeListener(
			OnWisdomChangeListener changeListener) {
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
		return mListGoods1.get(0).getStoreList().size();
	}

	public int getChildrenCount(int groupPosition) {
		// return mListGoods.get(groupPosition).getGoods().size();
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return mListGoods1.get(0).getStoreList().get(groupPosition)
				.getProductsList().size();
	}

	public Object getGroup(int groupPosition) {
		// return mListGoods.get(groupPosition);
		return mListGoods1.get(0).getStoreList().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		// return mListGoods.get(groupPosition).getGoods().get(childPosition);
		return mListGoods1.get(0).getStoreList().get(groupPosition)
				.getProductsList().get(childPosition);
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
					R.layout.item_wisdombuy_group_test, parent, false);
			holder.tvStoreName303 = (TextView) convertView
					.findViewById(R.id.tvStoreName303);
			holder.st30fabuotv303 = (TextView) convertView
					.findViewById(R.id.st30fabuotv303);
			holder.st30baoyoutv303 = (TextView) convertView
					.findViewById(R.id.st30baoyoutv303);
			holder.storedeleteimg303 = (ImageView) convertView
					.findViewById(R.id.storedeleteimg303);
			holder.rlred = (RelativeLayout) convertView
					.findViewById(R.id.rlred);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.tvStoreName303.setText(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getstore_Name());
		holder.storedeleteimg303.setTag(groupPosition);
		holder.storedeleteimg303.setOnClickListener(listener);
		holder.st30fabuotv303.setText("满"
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getMoneySend() + "发货");
		holder.st30baoyoutv303.setText("满"
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getMoneyFreePostage() + "包邮");
		// 判断发货及包邮
		double storeacount = GetStoreAcount(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getProductsList());
		double MoneySend = Double.valueOf(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getMoneySend());
		double MoneyFreePostage = Double.valueOf(mListGoods1.get(0)
				.getStoreList().get(groupPosition).getMoneyFreePostage());
		if (storeacount >= MoneySend) {
			holder.st30fabuotv303.setBackgroundColor(0xff99cc00);// ff99cc00
			holder.rlred.setBackgroundColor(0xff669900);
		} else {
			holder.st30fabuotv303.setBackgroundColor(0xffff4444);// 0xffff4444
			holder.rlred.setBackgroundColor(0xffff4444);
		}
		if (storeacount >= MoneyFreePostage) {
			holder.st30baoyoutv303.setBackgroundColor(0xff99cc00);// ff99cc00
		} else {
			holder.st30baoyoutv303.setBackgroundColor(0xffff4444);// 0xffff4444
		}
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
					R.layout.item_wisdombuy_child_test, parent, false);
			holder.st30goodname303 = (TextView) convertView
					.findViewById(R.id.st30goodname303);
			holder.st30guige303 = (TextView) convertView
					.findViewById(R.id.st30guige303);
			holder.st30productnum303 = (ExtendedEditText) convertView
					.findViewById(R.id.st30productnum303);
			holder.st30changjia303 = (TextView) convertView
					.findViewById(R.id.st30changjia303);
			holder.st30xiaoqi303 = (TextView) convertView
					.findViewById(R.id.st30xiaoqi303);
			holder.zuidinum303 = (TextView) convertView
					.findViewById(R.id.zuidinum303);
			holder.goodacount303 = (TextView) convertView
					.findViewById(R.id.goodacount303);
			holder.st30storeerror303 = (TextView) convertView
					.findViewById(R.id.st30storeerror303);
			holder.st30storeyoufei303 = (TextView) convertView
					.findViewById(R.id.st30storeyoufei303);
			holder.st30storeheji303 = (TextView) convertView
					.findViewById(R.id.st30storeheji303);
			holder.st30numreduce303 = (ImageView) convertView
					.findViewById(R.id.st30numreduce303);
			holder.st30numadd303 = (ImageView) convertView
					.findViewById(R.id.st30numadd303);
			holder.gooddeleteimg303 = (ImageView) convertView
					.findViewById(R.id.gooddeleteimg303);
			holder.rlredgood = (RelativeLayout) convertView
					.findViewById(R.id.rlredgood);
			holder.st30hejirl303 = (RelativeLayout) convertView
					.findViewById(R.id.st30hejirl303);
			holder.st30goodnobuy303 = (TextView) convertView
					.findViewById(R.id.st30goodnobuy303);
			holder.st30rl1303 = (RelativeLayout) convertView
					.findViewById(R.id.st30rl1303);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}

		holder.st30goodname303.setText(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getProductsList().get(childPosition)
				.getDrugsBase_DrugName());
		holder.st30guige303.setText(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getProductsList().get(childPosition)
				.getDrugsBase_Specification());
		holder.st30productnum303.setText(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getProductsList().get(childPosition)
				.getbuyCount());
		holder.st30changjia303.setText(mListGoods1.get(0).getStoreList()
				.get(groupPosition).getProductsList().get(childPosition)
				.getDrugsBase_Manufacturer());
		holder.st30xiaoqi303.setText("效期"
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getProductsList().get(childPosition).getsxrq());
		holder.zuidinum303.setText("最低购买数量"
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getProductsList().get(childPosition).getminBuy());
		holder.goodacount303.setText("￥ "
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getProductsList().get(childPosition).getPrice());
		holder.st30storeyoufei303.setText("邮费："
				+ mListGoods1.get(0).getStoreList().get(groupPosition)
						.getPostage());
		// 显示店铺合计
		ArrayList<WisdomBean303.StoreList.ProductsList> productlist = mListGoods1
				.get(0).getStoreList().get(groupPosition).getProductsList();
		int storeproductcount = productlist.size();// 店铺内商品总数
		int dpnum = 103;// 商品正常布局大小
		String errormsg = GetErrorMsg(productlist, childPosition);
		// 是否显示不符合条件商品提示
		if (errormsg.length() > 1)// 不符合条件
		{
			dpnum = dpnum + 30;
			holder.st30goodnobuy303.setText(errormsg);
			holder.st30rl1303.setVisibility(View.VISIBLE);
			holder.rlredgood.setBackgroundColor(0xffff4444);// 0xFF000000
		} else {
			double storeacount = GetStoreAcount(productlist);
			double MoneySend = Double.valueOf(mListGoods1.get(0).getStoreList()
					.get(groupPosition).getMoneySend());
			if (MoneySend > storeacount) {
				holder.rlredgood.setBackgroundColor(0xffff4444);
			} else {
				holder.rlredgood.setBackgroundColor(0xff669900);
			}
			holder.st30rl1303.setVisibility(View.GONE);
		}
		// 是否显示合计金额
		if (childPosition == storeproductcount - 1) {
			dpnum = dpnum + 50;
			DecimalFormat df = new DecimalFormat("######0.00");
			// 计算店铺的合计金额
			double storeacount = GetStoreAcount(productlist);
			double MoneySend = Double.valueOf(mListGoods1.get(0).getStoreList()
					.get(groupPosition).getMoneySend());
			double MoneyFreePostage=Double.valueOf(mListGoods1.get(0).getStoreList().get(groupPosition).getMoneyFreePostage());
			if (MoneySend > storeacount)// 不发货
			{
				holder.st30storeerror303.setText("不满最低发货金额，还差"
						+ df.format(MoneySend - storeacount));
				holder.st30storeerror303.setVisibility(View.VISIBLE);
				
			} else {
				holder.st30storeerror303.setVisibility(View.GONE);
			}
			if(storeacount>=MoneyFreePostage)
			{
				holder.st30storeyoufei303.setText("邮费：0");
			}
			holder.st30storeheji303.setText("合计：" + df.format(storeacount));
			holder.st30hejirl303.setVisibility(View.VISIBLE);
//			holder.rlredgood.setLayoutParams(new RelativeLayout.LayoutParams(
//					getPixelsFromDp(5), getPixelsFromDp(dpnum)));
		} else {
			holder.st30hejirl303.setVisibility(View.GONE);
//			holder.rlredgood.setLayoutParams(new RelativeLayout.LayoutParams(
//					getPixelsFromDp(5), getPixelsFromDp(dpnum)));
		}

		holder.st30numreduce303.setTag(groupPosition + "," + childPosition);
		holder.st30numreduce303.setOnClickListener(listener);
		holder.st30numadd303.setTag(groupPosition + "," + childPosition);
		holder.st30numadd303.setOnClickListener(listener);
		holder.gooddeleteimg303.setTag(groupPosition + "," + childPosition);
		holder.gooddeleteimg303.setOnClickListener(listener);
		holder.st30productnum303.setTag(groupPosition + "," + childPosition);
		holder.st30productnum303.setOnClickListener(listener);
		return convertView;
	}

	// 店铺内可以购买的商品合计
	public double GetStoreAcount(ArrayList<ProductsList> productlist) {
		// TODO Auto-generated method stub
		double storeacount = 0;
		for (int i = 0; i < productlist.size(); i++) {
			// 判断是否可以采购
			String errormsg = GetErrorMsg(productlist, i);
			if (errormsg.length() < 1)// 可以采购
			{
				storeacount = storeacount
						+ Double.valueOf(productlist.get(i).getbuyCount())
						* Double.valueOf(productlist.get(i).getPrice());
			}
		}
		return storeacount;
	}

	public String GetErrorMsg(ArrayList<ProductsList> productlist,
			int childPosition) {
		// TODO Auto-generated method stub
		double minbuy = Double.valueOf(productlist.get(childPosition)
				.getminBuy());
		double buycount = Double.valueOf(productlist.get(childPosition)
				.getbuyCount());
		int sellertype = Integer.parseInt(productlist.get(childPosition)
				.getsellType());
		double Product_Pcs_Small = Double.valueOf(productlist
				.get(childPosition).getProduct_Pcs_Small());
		double Product_Pcs = Double.valueOf(productlist.get(childPosition)
				.getProduct_Pcs());
		double stock = Double
				.valueOf(productlist.get(childPosition).getstock());
		String msg = "";
		if (minbuy > buycount) {
			msg = "不满最低采购数量，最低采购数量为" + minbuy;
		} else {
			if (sellertype == 2) {
				if (buycount % Product_Pcs_Small > 0.001) {
					msg = "购买数量不符合中包装" + Product_Pcs_Small + "的整数倍";
				}
			} else if (sellertype == 3) {
				if (buycount % Product_Pcs > 0.001) {
					msg = "购买数量不符合件装" + Product_Pcs + "的整数倍";
				}
			}
		}
		if (msg.length() < 1)// 需要判断库存
		{
			if (buycount > stock) {
				msg = "购买数量超库存，最大可购买数量为" + stock;
			}
		}
		return msg;
	}

	// dp转像素
	public int getPixelsFromDp(int size) {

		DisplayMetrics metrics = new DisplayMetrics();

		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

	}

	public boolean isChildSelectable(int i, int i1) {
		return false;
	}

	View.OnClickListener listener = new View.OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			// main
			case R.id.ivSelectAll:
				break;
			case R.id.btnSettle:

				// group
				break;
			case R.id.storedeleteimg303:// 删除店铺
				break;
			case R.id.ivCheckGroup:
				break;
			// child
			case R.id.ivCheckGood:
				break;
			case R.id.tvItemChild:
				break;
			case R.id.onjiajiagou:
				break;
			case R.id.st30productnum303:
				String tagchanenum = String.valueOf(v.getTag());
				inputTitleDialog(tagchanenum,((TextView) (((View) (v.getParent()))
						.findViewById(R.id.st30productnum303))));
				break;
			case R.id.st30numadd303:// 增加商品数量
				String tagadd = String.valueOf(v.getTag());
				if (tagadd.contains(",")) {
					String s[] = tagadd.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					WisdomBean303.StoreList.ProductsList goods = mListGoods1
							.get(0).getStoreList().get(groupPosition)
							.getProductsList().get(childPosition);
					String isreduce = ShoppingCartBiz.addOrReduceGoodsNum303(
							true, goods, ((TextView) (((View) (v.getParent()))
									.findViewById(R.id.st30productnum303))));
					setSettleInfo();
					notifyDataSetChanged();
					if (isreduce.length() == 0)// 可以增加数量
					{
						addproducts(Integer.parseInt(goods.getpid()),
								Double.valueOf(goods.getbuyCount()), 0);
					} else {
						ToastHelper.getInstance()._toast(isreduce);
					}
				}
				break;
			case R.id.st30numreduce303:// 减少商品数量
				String tagreduce = String.valueOf(v.getTag());
				if (tagreduce.contains(",")) {
					String s[] = tagreduce.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					WisdomBean303.StoreList.ProductsList goods = mListGoods1
							.get(0).getStoreList().get(groupPosition)
							.getProductsList().get(childPosition);
					String isreduce = ShoppingCartBiz.addOrReduceGoodsNum303(
							false, goods, ((TextView) (((View) (v.getParent()))
									.findViewById(R.id.st30productnum303))));
					setSettleInfo();
					notifyDataSetChanged();
					if (isreduce.length() == 0)// 可以减少数量
					{
						addproducts(Integer.parseInt(goods.getpid()),
								Double.valueOf(goods.getbuyCount()), 0);
					} else {
						ToastHelper.getInstance()._toast(isreduce);
					}
				}
				break;
			case R.id.ivGoods:
				break;
			case R.id.tvShopNameGroup:
				break;
			case R.id.gooddeleteimg303:// 删除商品
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

	public void setOnWisdom303ChangeListener(
			OnWisdomChangeListener changeListener) {
		this.mChangeListener = changeListener;
	}

	private void setSettleInfo() {
		String[] infos = ShoppingCartBiz.getWisdomCount(mListGoods1);
		// 删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
		if (mChangeListener != null && infos != null) {
			mChangeListener.onDataChange(infos[0], infos[1], infos[2],
					infos[3], infos[4]);
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
				String productID = mListGoods1.get(0).getStoreList()
						.get(groupPosition).getProductsList()
						.get(childPosition).getpid();
				ShoppingCartBiz.delGood303(productID);
				delGoods(groupPosition, childPosition);
				setSettleInfo();
				notifyDataSetChanged();
				delDialog.dismiss();
				deleteproduct(productID);
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
					URL url = new URL(sturl
							+ "/webapi/ChangePurchaseCount?pid=" + pid
							+ "&num=" + buyCount);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("POST");
					ishasfinish++;
					mListGoods1.get(0).setishasfinish(ishasfinish);
					// conn.getResponseCode();
					if (conn.getResponseCode() == 200) {
						if (ishasfinish < 1) {
							ishasfinish = 1;
						}
						ishasfinish--;

						mListGoods1.get(0).setishasfinish(ishasfinish);
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
					URL url = new URL(sturl + "/webapi/DeletePurchaseForPid?pid="
							+ pid);
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
		mListGoods1.get(0).getStoreList().get(groupPosition).getProductsList()
				.remove(childPosition);
		if (mListGoods1.get(0).getStoreList().get(groupPosition)
				.getProductsList().size() == 0) {
			mListGoods1.get(0).getStoreList().remove(groupPosition);
		}
		notifyDataSetChanged();
	}

	class GroupViewHolder {
		ImageView storeimg303;
		TextView tvStoreName303;
		TextView st30fabuotv303;
		TextView st30baoyoutv303;
		ImageView storedeleteimg303;
		RelativeLayout rlred;
	}

	class ChildViewHolder {
		/** 商品名称 */
		TextView st30goodname303;
		/** 商品规格 */
		TextView st30guige303;
		/** 商品数量 */
		ExtendedEditText st30productnum303;
		/** 生产厂家 */
		TextView st30changjia303;
		/** 效期 */
		TextView st30xiaoqi303;
		/** 最低采购数 **/
		TextView zuidinum303;
		/** 价格 */
		TextView goodacount303;
		/** 不满店铺发货 */
		TextView st30storeerror303;
		/** 店铺邮费 */
		TextView st30storeyoufei303;
		/** 店铺商品合计 */
		TextView st30storeheji303;
		/** 减少商品数量 */
		ImageView st30numreduce303;
		/** 增加商品数量 */
		ImageView st30numadd303;
		/** 删除商品 */
		ImageView gooddeleteimg303;
		/** 右侧线条 */
		RelativeLayout rlredgood;
		/** 合计布局 */
		RelativeLayout st30hejirl303;
		/** 商品不符合购买条件 */
		TextView st30goodnobuy303;
		/** 商品不符合购买条件 */
		RelativeLayout st30rl1303;
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
	
	private void inputTitleDialog(final String str,final TextView tvnum) {

        final EditText inputServer = new EditText(mContext);
        inputServer.setFocusable(true);
        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("输入需要购买的数量").setIcon(
                R.drawable.ic_checked).setView(inputServer).setNegativeButton(
                "取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        String s[] = str.split(",");
        				int groupPosition = Integer.parseInt(s[0]);
        				int childPosition = Integer.parseInt(s[1]);
        				int SellType = Integer
    							.valueOf(mListGoods1.get(0).getStoreList()
    									.get(groupPosition).getProductsList()
    									.get(childPosition).getsellType());
    					double thisstock = Double.valueOf(mListGoods1.get(0)
    							.getStoreList().get(groupPosition)
    							.getProductsList().get(childPosition).getstock());
    					minnum = mListGoods1.get(0).getStoreList()
    							.get(groupPosition).getProductsList()
    							.get(childPosition).getminBuy();// 最小销售
    					postpid = mListGoods1.get(0).getStoreList()
    							.get(groupPosition).getProductsList()
    							.get(childPosition).getpid();
    					double addminnum = 1;
    					String oldnum = mListGoods1.get(0).getStoreList()
    							.get(groupPosition).getProductsList()
    							.get(childPosition).getbuyCount();// 输入前数量
    					if (SellType == 2)// 中包装
    					{
    						minnum = mListGoods1.get(0).getStoreList()
    								.get(groupPosition).getProductsList()
    								.get(childPosition).getProduct_Pcs_Small();
    						addminnum = Double.valueOf(minnum);
    					} else if (SellType == 3)// 件装
    					{
    						minnum = mListGoods1.get(0).getStoreList()
    								.get(groupPosition).getProductsList()
    								.get(childPosition).getProduct_Pcs();
    						addminnum = Double.valueOf(minnum);
    					}
    					if (inputName.length() > 0) {// 输入的是数字
    						if (Double.valueOf(inputName) - Double.valueOf(oldnum) > 0.001
    								|| Double.valueOf(oldnum) - Double.valueOf(inputName) > 0.001) {

    							if (SellType == 2) {
    								if (Double.valueOf(inputName)
    										% Double.valueOf(minnum) > 0.001
    										&& Double.valueOf(inputName) <= thisstock) {
    									ToastHelper.getInstance()
    											.displayToastWithQuickClose(
    													"输入数量必须是中包装" + minnum
    															+ "的整数倍");
    									inputName = oldnum;
    								}
    							} else if (SellType == 3) {
    								if (Double.valueOf(inputName)
    										% Double.valueOf(minnum) > 0.001
    										&& Double.valueOf(inputName) <= thisstock) {
    									ToastHelper.getInstance()
    											.displayToastWithQuickClose(
    													"输入数量必须是件装" + minnum
    															+ "的整数倍");
    									inputName = oldnum;

    								}
    							} else {
    								if (Double.valueOf(inputName) < Double
    										.valueOf(minnum)
    										&& Double.valueOf(inputName) <= thisstock) {
    									ToastHelper.getInstance()
    											.displayToastWithQuickClose(
    													"输入数量必须大于最小销售数量" + minnum);
    									inputName = oldnum;

    								}

    								if (Double.valueOf(inputName) % addminnum > 0.001
    										&& Double.valueOf(inputName) <= thisstock) {
    									ToastHelper.getInstance()
    											.displayToastWithQuickClose(
    													"输入数量必须是1" + "的整数倍");
    									inputName = oldnum;
    								}

    							}
    							if (inputName.substring(0, 1).equals("0")) {
    								try {

    									if (Double.valueOf(inputName) <= 0) {
    										inputName = oldnum;
    										ToastHelper.getInstance()
    												.displayToastWithQuickClose(
    														"输入数量必须大于最小购买数"
    																+ minnum);
    									}
    								} catch (Exception ex) {
    									//
    								}
    							}

    							// 判断库存
    							if (thisstock < Double.valueOf(inputName)) {
    								ToastHelper.getInstance()
    										.displayToastWithQuickClose(
    												"库存不足，当前库存为" + thisstock);
    								inputName = oldnum;
    							} else {
    								postnum = Double.valueOf(inputName);
    								if (postnum <= 0) {
    									inputName = oldnum;
    								}
    							}
    							mListGoods1.get(0).getStoreList()
    									.get(groupPosition).getProductsList()
    									.get(childPosition).setbuyCount(inputName);
    							addproducts(Integer.parseInt(postpid),
    									Double.valueOf(inputName), poststate);
    							tvnum.setText(inputName);
    							setSettleInfo();
    							notifyDataSetChanged();
    						}
    					} else// 输入非法
    					{
    						inputName = oldnum;
    						tvnum.setText(inputName);
    					}
                    }
                });
        builder.show();
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
          
            @Override  
            public void run() {  
            	if(inputServer!=null){  
                    //设置可获得焦点  
            		inputServer.setFocusable(true);  
            		inputServer.setFocusableInTouchMode(true);  
                    //请求获得焦点  
            		inputServer.requestFocus();  
                    //调用系统输入法  
                    InputMethodManager inputManager = (InputMethodManager) inputServer  
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
                    inputManager.showSoftInput(inputServer, 0);  
                }
            }  }, 300);
    }
}
