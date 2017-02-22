package com.jock.tbshopcar.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Text;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.newgame.sdk.yyaost.ExtendedEditText;
import com.newgame.sdk.yyaost.MainActivity;
import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.SearchActitity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.entity.ShoppingCartBean1.StoreCartList.CartProductList;
import com.jock.tbshopcar.entity.WisdomBean302;
import com.jock.tbshopcar.listener.OnShoppingCartChangeListener;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.utils.DecimalUtil;
import com.jock.tbshopcar.utils.ToastHelper;
import com.jock.tbshopcar.view.UIAlertView;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter302 extends BaseExpandableListAdapter {
	private Context mContext;
	private List<WisdomBean302> mListGoods1 = new ArrayList<WisdomBean302>();
	private OnWisdomChangeListener mChangeListener;
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

	// 定义变量，记录刷新前获得焦点的EditText所在的位置
	String mCurrentTouchedIndex = "";
	// 判断修改数量是否处理完
	static int ishasfinish = 0;

	public MyExpandableListAdapter302(Context context) {
		mContext = context;
		configImageLoader();
	}

	public void setList(List<WisdomBean302> mListGoods) {
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
		return mListGoods1.get(0).getSchemeName().size();
	}

	public int getChildrenCount(int groupPosition) {
		// return mListGoods.get(groupPosition).getGoods().size();
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return 1;
	}

	public Object getGroup(int groupPosition) {
		// return mListGoods.get(groupPosition);
		return mListGoods1.get(0).getSchemeName().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		// return mListGoods.get(groupPosition).getGoods().get(childPosition);
		return 0;
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
					R.layout.item_select_group_test, parent, false);
			holder.tvfangan302=(TextView)convertView.findViewById(R.id.tvfangan302);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.tvfangan302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getSchemeName());
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
					R.layout.item_select_child_test, parent, false);
			holder.moneyinfotv302=(TextView)convertView.findViewById(R.id.moneyinfotv302);
			holder.moneytv302=(TextView)convertView.findViewById(R.id.moneytv302);
			holder.timetv302=(TextView)convertView.findViewById(R.id.timetv302);
			holder.storenumtv302=(TextView)convertView.findViewById(R.id.storenumtv302);
			holder.youfeitv302=(TextView)convertView.findViewById(R.id.youfeitv302);
			holder.hejitv302=(TextView)convertView.findViewById(R.id.hejitv302);
			holder.bufuhetv302=(TextView)convertView.findViewById(R.id.bufuhetv302);
			holder.selecttv302=(Button)convertView.findViewById(R.id.selecttv302);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		holder.moneyinfotv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getSuperiorityNum()+"个品种低于原始采购价，为您节省");
		holder.moneytv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getEconomizeMoney());
		holder.timetv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getEconomizeTime());
		holder.storenumtv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getStoresCount());
		holder.youfeitv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getPostage());
		holder.hejitv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getSurplusMoney());
		holder.bufuhetv302.setText(mListGoods1.get(0).getSchemeName().get(groupPosition).getmismatching());
		holder.selecttv302.setTag(groupPosition + "," + childPosition);
		holder.selecttv302.setOnClickListener(listener);
		return convertView;
	}

	public boolean isChildSelectable(int i, int i1) {
		return false;
	}

	View.OnClickListener listener = new View.OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			// main
			case R.id.selecttv302:
				String gotosearch1 = String.valueOf(v.getTag());
				if (gotosearch1.contains(",")) {
					String s[] = gotosearch1.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					String SchemeName = mListGoods1.get(0).getSchemeName().get(groupPosition).getSchemeName();
					GoWisdomBuy(SchemeName);
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
	private void GoWisdomBuy(String SchemeName) {
		if (mChangeListener != null) {
			mChangeListener.onOpenWisdomBuy(SchemeName);
		}
	}

	private void onOpenStore(String storeid) {
		if (mChangeListener != null) {
			mChangeListener.onOpenStore(storeid);
		}
	}
	public void setOnWisdom302ChangeListener(
			OnWisdomChangeListener changeListener) {
		this.mChangeListener = changeListener;
	}

	private void setSettleInfo() {
//		String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods1);
//		// 删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
//		if (mChangeListener != null && infos != null) {
//			mChangeListener.onDataChange(infos[0], infos[1]);
//		}
	}

	// 增加商品数量
	public void addproducts(final int pid, final double buyCount,
			final int addpricebuystate) {
		new Thread(new Runnable() {
			public void run() {
				try {

//					String sturl = mContext.getResources().getString(
//							R.string.barUrl);
//					URL url = new URL(sturl + "/webapi/ChangeProductCount?pid="
//							+ pid + "&buyCount=" + buyCount
//							+ "&addpricebuystate=" + addpricebuystate);
//					HttpURLConnection conn = (HttpURLConnection) url
//							.openConnection();
//					conn.setRequestProperty("Cookie", mListGoods1.get(0)
//							.getoldCookie());
//					conn.setRequestMethod("POST");
//					// conn.getResponseCode();
//					Log.v("url", url.toString());
//					Log.v("time1", new SimpleDateFormat("yyyyMMddHHmmssSSS")
//							.format(new Date()));
//					if (conn.getResponseCode() == 200) {
//						if (ishasfinish < 1) {
//							ishasfinish = 1;
//						}
//						ishasfinish--;
//
//						mListGoods1.get(0).setishasfinish(ishasfinish);
//						Log.v("time2",
//								new SimpleDateFormat("yyyyMMddHHmmssSSS")
//										.format(new Date()));
//						BufferedReader br = new BufferedReader(
//								new InputStreamReader(conn.getInputStream(),
//										"utf-8"));
//						String line = "";
//						String str = "";
//						while (null != (line = br.readLine())) {
//							str += line;
//						}
//						Log.v("return", str);
//						br.close();
//						conn.disconnect();
//					}

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
//		mListGoods1.get(0).getStoreCartList().get(groupPosition)
//				.getCartProductList().remove(childPosition);
//		if (mListGoods1.get(0).getStoreCartList().get(groupPosition)
//				.getCartProductList().size() == 0) {
//			mListGoods1.get(0).getStoreCartList().remove(groupPosition);
//		}
//		notifyDataSetChanged();
	}

	class GroupViewHolder {
		TextView tvfangan302;
	}

	class ChildViewHolder {
		/** 低于历史采购加的商品数量 */
		TextView moneyinfotv302;
		/** 节省的钱 */
		TextView moneytv302;
		/** 节省的时间 */
		TextView timetv302;
		/** 采购的店铺数量 */
		TextView storenumtv302;
		/** 所需邮费 */
		TextView youfeitv302;
		/** 合计 **/
		TextView hejitv302;
		/** 不符合采购商品 **/
		TextView bufuhetv302;
		/** 方案选择**/
		Button selecttv302;
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
}
