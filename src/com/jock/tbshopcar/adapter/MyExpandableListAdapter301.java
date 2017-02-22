package com.jock.tbshopcar.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newgame.sdk.yyaost.ExtendedEditText;
import com.newgame.sdk.yyaost.R;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.entity.WisdomBean.FirstZiMu.Goods;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.jock.tbshopcar.view.DragView;
import com.jock.tbshopcar.view.DragView.onSlideListener;
import com.jock.tbshopcar.view.SelectNumDialog;
import com.jock.tbshopcar.view.SelectNumDialog.OnClickListener;
import com.jock.tbshopcar.view.UIAlertView;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter301 extends BaseExpandableListAdapter {
	private Context mContext;
	// private List<ShoppingCartBean> mListGoods = new
	// ArrayList<ShoppingCartBean>();
	private List<WisdomBean> mListGoods1 = new ArrayList<WisdomBean>();
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
	private ExpandableListView listView;
	/** 当前滑动的View */
	private DragView slideView = null;

	public MyExpandableListAdapter301(Context context,
			final ExpandableListView listView) {
		mContext = context;
		this.listView = listView;
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				if (slideView != null) {
					slideView.revert();
					slideView = null;
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setList(List<WisdomBean> mListGoods) {
		this.mListGoods1 = mListGoods;
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
		return mListGoods1.get(0).getFirstZiMu().size();
	}

	public int getChildrenCount(int groupPosition) {
		// return mListGoods.get(groupPosition).getGoods().size();
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.size();
	}

	public Object getGroup(int groupPosition) {
		// return mListGoods.get(groupPosition);
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		// return mListGoods.get(groupPosition).getGoods().get(childPosition);
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.get(childPosition);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		int k = -1;
		if (mListGoods1 != null && mListGoods1.size() > 0
				&& mListGoods1.get(0).getFirstZiMu() != null) {
			for (int i = 0; i < mListGoods1.get(0).getFirstZiMu().size(); i++) {
				String sortStr = mListGoods1.get(0).getFirstZiMu().get(i)
						.getPreChar();
				int firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
					k = i;
					break;
				}
			}
		}

		return k;
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
					R.layout.item_wisdom_group_test, parent, false);
			holder.tvzimupaiixu = (TextView) convertView
					.findViewById(R.id.tvzimupaiixu);
			holder.storeinfo = (RelativeLayout) convertView
					.findViewById(R.id.storeinfo);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.tvzimupaiixu.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getPreChar());
		if (mListGoods1.get(0).getFirstZiMu().get(groupPosition).getPreChar()
				.contains("jinji")
				| mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getPreChar().contains("stock")) {
			holder.storeinfo.setVisibility(View.GONE);
		} else {
			holder.storeinfo.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	/**
	 * child view
	 */

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_wisdom_child_test, parent, false);
			holder.tvItemChild301 = (TextView) convertView
					.findViewById(R.id.tvItemChild301);
			holder.tvGoodsParam301 = (TextView) convertView
					.findViewById(R.id.tvGoodsParam301);
			holder.historyprice301 = (TextView) convertView
					.findViewById(R.id.historyprice301);
			holder.tvManufacturer301 = (TextView) convertView
					.findViewById(R.id.tvManufacturer301);
			holder.prermb = (TextView) convertView.findViewById(R.id.prermb);
			holder.minprice301 = (TextView) convertView
					.findViewById(R.id.minprice301);
			holder.maxprice301 = (TextView) convertView
					.findViewById(R.id.maxprice301);
			holder.kucunnum301 = (TextView) convertView
					.findViewById(R.id.kucunnum301);
			holder.xiaoliangnum301 = (TextView) convertView
					.findViewById(R.id.xiaoliangnum301);
			holder.caigouriqi301 = (TextView) convertView
					.findViewById(R.id.caigouriqi301);
			holder.numreduce301 = (ImageView) convertView
					.findViewById(R.id.numreduce301);
			holder.numadd301 = (ImageView) convertView
					.findViewById(R.id.numadd301);
			holder.productnum301 = (ExtendedEditText) convertView
					.findViewById(R.id.productnum301);
			// holder.productnum301 = (TextView) convertView
			// .findViewById(R.id.productnum301);
			holder.delItemChild301 = (TextView) convertView
					.findViewById(R.id.delItemChild301);
			holder.numaddmore301 = (ImageView) convertView
					.findViewById(R.id.numaddmore301);
			holder.dragView = (DragView) convertView
					.findViewById(R.id.swipelayout);

			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		holder.tvItemChild301.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getDrugsBase_DrugName());
		holder.historyprice301.setText("￥ "
				+ mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getHistoryPrice());
		double minprice = Double
				.valueOf(mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getminPrice());
		double maxprice = Double
				.valueOf(mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getmaxPrice());
		double hostoryprice = Double.valueOf(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getHistoryPrice());
		if (hostoryprice < maxprice) {
			holder.prermb.setTextColor(Color.rgb(255, 128, 0));
			// holder.minprice301.setTextColor(Color.rgb(255, 128, 0));
			holder.maxprice301.setTextColor(Color.rgb(255, 128, 0));
		} else {
			holder.prermb.setTextColor(Color.rgb(34, 139, 34));
			holder.maxprice301.setTextColor(Color.rgb(34, 139, 32));
		}
		if (hostoryprice < minprice) {
			holder.minprice301.setTextColor(Color.rgb(255, 128, 0));
		} else {
			holder.minprice301.setTextColor(Color.rgb(34, 139, 32));
		}
		if (maxprice - minprice > 0.001) {
			holder.maxprice301.setText(" - "
					+ mListGoods1.get(0).getFirstZiMu().get(groupPosition)
							.getGoods().get(childPosition).getmaxPrice());
			// holder.maxprice301.setVisibility(View.VISIBLE);
		} else {
			holder.maxprice301.setText("");
		}
		holder.minprice301
				.setText(mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getminPrice());

		holder.tvGoodsParam301.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getDrugsBase_Specification());
		holder.tvManufacturer301.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getDrugsBase_Manufacturer());
		holder.kucunnum301.setText(String.valueOf(mListGoods1.get(0)
				.getFirstZiMu().get(groupPosition).getGoods()
				.get(childPosition).getstock()));
		holder.xiaoliangnum301.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getSalesVolume());
		holder.caigouriqi301.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getLastTimeString());
		holder.productnum301
				.setText(mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getbuyCount());
		// 添加数量加减按钮事件
		holder.delItemChild301.setTag(groupPosition + "," + childPosition);
		holder.delItemChild301.setOnClickListener(listener);

		holder.numreduce301.setTag(groupPosition + "," + childPosition);
		holder.numreduce301.setOnClickListener(listener);
		holder.numadd301.setTag(groupPosition + "," + childPosition);
		holder.numadd301.setOnClickListener(listener);
		holder.productnum301.setTag(groupPosition + "," + childPosition);
		holder.productnum301.setOnClickListener(listener);
		holder.numaddmore301.setOnClickListener(listener);
		if (holder.numaddmore301.getTag() != null) {
			SelectNumAndText tag = (SelectNumAndText) holder.numaddmore301
					.getTag();
			tag.editText = holder.productnum301;
			tag.good = mListGoods1.get(0).getFirstZiMu().get(groupPosition)
					.getGoods().get(childPosition);
			holder.numaddmore301.setTag(tag);
		} else {
			SelectNumAndText tag = new SelectNumAndText();
			tag.editText = holder.productnum301;
			tag.good = mListGoods1.get(0).getFirstZiMu().get(groupPosition)
					.getGoods().get(childPosition);
			holder.numaddmore301.setTag(tag);
		}
		holder.dragView.setTag(groupPosition + "," + childPosition);
		holder.dragView.setOnSlide(new onSlideListener() {

			@Override
			public void onSlided(boolean isSlide, DragView dragView) {
				// TODO Auto-generated method stub
				if (isSlide) {// 是否滑动成功（包括侧滑之后的返回滑动）
					if (slideView != null) {
						slideView.revert();
					}
				}
				slideView = dragView;
			}

			@Override
			public void onClick(DragView v) {
				// TODO Auto-generated method stub
				String tagPos = String.valueOf(v.getTag());
				if (tagPos.contains(",")) {
					String s[] = tagPos.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					showDelDialog(groupPosition, childPosition);
				}
			}
		});

		return convertView;
	}

	View.OnClickListener listener = new View.OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.numadd301:
				String tagadd = String.valueOf(v.getTag());
				if (tagadd.contains(",")) {
					String s[] = tagadd.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					WisdomBean.FirstZiMu.Goods goods = mListGoods1.get(0)
							.getFirstZiMu().get(groupPosition).getGoods()
							.get(childPosition);
					String isreduce = ShoppingCartBiz.addOrReduceGoodsNum301(
							true, goods, ((TextView) (((View) (v.getParent()))
									.findViewById(R.id.productnum301))));
					// notifyDataSetChanged();
					if (isreduce.length() == 0)// 可以增加数量
					{
						addproducts(goods.getGoods_Package_ID(),
								Double.valueOf(goods.getbuyCount()), 0);
					} else {
						ToastHelper.getInstance()._toast(isreduce);
					}
				}
				break;
			case R.id.numreduce301:
				// inputTitleDialog();
				String tagreduce = String.valueOf(v.getTag());
				if (tagreduce.contains(",")) {
					String s[] = tagreduce.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					WisdomBean.FirstZiMu.Goods goods = mListGoods1.get(0)
							.getFirstZiMu().get(groupPosition).getGoods()
							.get(childPosition);
					String isreduce = ShoppingCartBiz.addOrReduceGoodsNum301(
							false, goods, ((TextView) (((View) (v.getParent()))
									.findViewById(R.id.productnum301))));
					if (isreduce.length() == 0)// 可以增加数量
					{
						addproducts(goods.getGoods_Package_ID(),
								Double.valueOf(goods.getbuyCount()), 0);
					} else {
						ToastHelper.getInstance()._toast(isreduce);
					}
				}
				break;
			case R.id.delItemChild301:// 删除商品
				String tagPos = String.valueOf(v.getTag());
				if (tagPos.contains(",")) {
					String s[] = tagPos.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					showDelDialog(groupPosition, childPosition);
				}
				break;
			case R.id.productnum301:
				String tagchanenum = String.valueOf(v.getTag());
				inputTitleDialog(tagchanenum,
						((TextView) (((View) (v.getParent()))
								.findViewById(R.id.productnum301))));
				break;
			// 增加数量选择
			case R.id.numaddmore301:
				int[] location = new int[2];
				v.getLocationInWindow(location);
				final SelectNumAndText selectNumAndText = (SelectNumAndText) v
						.getTag();
				new SelectNumDialog(mContext, selectNumAndText.good,
						new OnClickListener() {
							@Override
							public void getNum(String num) {
								selectNumAndText.editText.setText(num);
								selectNumAndText.good.setbuyCount(num);
								addproducts(selectNumAndText.good
										.getGoods_Package_ID(), Double
										.valueOf(num), 0);
							}
						}).show(v, location);
				break;
			}
		}
	};

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
				String id = String.valueOf(mListGoods1.get(0).getFirstZiMu()
						.get(groupPosition).getGoods().get(childPosition)
						.getid());
				ShoppingCartBiz.delGood301(String.valueOf(mListGoods1.get(0)
						.getFirstZiMu().get(groupPosition).getGoods()
						.get(childPosition).getGoods_Package_ID()));
				int waitbuynum=Integer.parseInt(mListGoods1.get(0).getmatchingCount());
				int nobuynum=Integer.parseInt(mListGoods1.get(0).getnotPurchaseCount());
				mListGoods1.get(0).setmatchingCount(String.valueOf(waitbuynum-1));
				mListGoods1.get(0).setnotPurchaseCount(String.valueOf(nobuynum+1));
				delGoods(groupPosition, childPosition);
				mChangeListener.onChangeTitle();
				notifyDataSetChanged();
				delDialog.dismiss();
				deleteproduct(id);
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
					URL url = new URL(
							sturl
									+ "/webapi/ChangePurchaseCountForGoods_Package_ID?Goods_Package_ID="
									+ pid + "&num=" + buyCount);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", mListGoods1.get(0)
							.getoldCookie());
					conn.setRequestMethod("POST");
					if (conn.getResponseCode() == 200) {
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

	// 删除单个品种
	public void deleteproduct(final String pid) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl + "/webapi/DeletePurchase?id="
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

	private void delGoods(int groupPosition, int childPosition) {
		mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.remove(childPosition);
		if (mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.size() == 0) {
			mListGoods1.get(0).getFirstZiMu().remove(groupPosition);
		}
		notifyDataSetChanged();
	}

	class GroupViewHolder {
		TextView tvzimupaiixu;
		RelativeLayout storeinfo;
	}

	class ChildViewHolder {
		/** 商品名称 */
		TextView tvItemChild301;
		/** 商品规格 */
		TextView tvGoodsParam301;
		/** 历史价格 */
		TextView historyprice301;
		/** 生产厂家 */
		TextView tvManufacturer301;
		/** 设置人民币符号颜色 */
		TextView prermb;
		/** 商品价格 */
		TextView minprice301;
		/** 商品价格 */
		TextView maxprice301;
		/** 库存 **/
		TextView kucunnum301;
		/** 半月销量 */
		TextView xiaoliangnum301;
		/** 采购日期 */
		TextView caigouriqi301;
		/** 减少数量 */
		ImageView numreduce301;
		/** 增加数量 */
		ImageView numadd301;
		/** 购买数量 */
		ExtendedEditText productnum301;

		/** 删除商品 */
		TextView delItemChild301;

		/** 增加数量弹窗 */
		ImageView numaddmore301;
		/**
		 * 侧滑删除
		 */
		DragView dragView;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	private void inputTitleDialog(final String str, final TextView tvnum) {

		final EditText inputServer = new EditText(mContext);
		inputServer.setFocusable(true);
		inputServer.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("输入需要购买的数量").setIcon(R.drawable.ic_checked)
				.setView(inputServer).setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				String inputName = inputServer.getText().toString();
				String s[] = str.split(",");
				int groupPosition = Integer.parseInt(s[0]);
				int childPosition = Integer.parseInt(s[1]);
				String oldnum = mListGoods1.get(0).getFirstZiMu()
						.get(groupPosition).getGoods().get(childPosition)
						.getbuyCount();// 输入前数量
				if (inputName.length() > 0) {// 输入的是数字
					if (Double.valueOf(inputName) - Double.valueOf(oldnum) > 0.001
							|| Double.valueOf(oldnum)
									- Double.valueOf(inputName) > 0.001) {
						if (inputName.substring(0, 1).equals("0")) {
							try {

								if (Double.valueOf(inputName) <= 0) {
									inputName = oldnum;
									ToastHelper.getInstance()
											.displayToastWithQuickClose(
													"输入数量必须大于0");
								}
							} catch (Exception ex) {
								//
							}
						}
						mListGoods1.get(0).getFirstZiMu().get(groupPosition)
								.getGoods().get(childPosition)
								.setbuyCount(inputName);
						addproducts(
								mListGoods1.get(0).getFirstZiMu()
										.get(groupPosition).getGoods()
										.get(childPosition)
										.getGoods_Package_ID(),
								Double.valueOf(inputName), 0);
						tvnum.setText(inputName);
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
				if (inputServer != null) {
					// 设置可获得焦点
					inputServer.setFocusable(true);
					inputServer.setFocusableInTouchMode(true);
					// 请求获得焦点
					inputServer.requestFocus();
					// 调用系统输入法
					InputMethodManager inputManager = (InputMethodManager) inputServer
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(inputServer, 0);
				}
			}
		}, 300);
	}

	private class SelectNumAndText {
		ExtendedEditText editText;
		WisdomBean.FirstZiMu.Goods good;
	}
}
