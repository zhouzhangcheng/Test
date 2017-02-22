package com.jock.tbshopcar.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newgame.sdk.yyaost.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter301nobuy extends BaseExpandableListAdapter {
	private Context mContext;
	private List<WisdomBean> mListGoods1 = new ArrayList<WisdomBean>();
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

	public MyExpandableListAdapter301nobuy(Context context) {
		mContext = context;
		configImageLoader();
	}

	public void setList(List<WisdomBean> mListGoods) {
		this.mListGoods1 = mListGoods;
	}

	public void setOnWisdom301errorChangeListener(
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
		if (mListGoods1 == null) {
			return 0;
		} else if (mListGoods1.size() == 0) {
			return 0;
		}
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.size();
	}

	public Object getGroup(int groupPosition) {
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods()
				.get(childPosition);
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

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = null;
		if (convertView == null) {
			holder = new GroupViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_wisdomnobuy_group_test, parent, false);
			holder.tvzimunobuy = (TextView) convertView
					.findViewById(R.id.tvzimunobuy);
			holder.nobuystoreinfo = (RelativeLayout) convertView
					.findViewById(R.id.nobuystoreinfo);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.nobuystoreinfo.setVisibility(View.GONE);
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
					R.layout.item_wisdomnobuy_child_test, parent, false);
			holder.tvItemChildnobuy = (TextView) convertView
					.findViewById(R.id.tvItemChildnobuy);
			holder.tvGoodsParamnobuy = (TextView) convertView
					.findViewById(R.id.tvGoodsParamnobuy);
			holder.tvManufacturernobuy = (TextView) convertView
					.findViewById(R.id.tvManufacturernobuy);
			holder.kucunnumnobuy = (TextView) convertView
					.findViewById(R.id.kucunnumnobuy);
			holder.xiaoliangnumnobuy = (TextView) convertView
					.findViewById(R.id.xiaoliangnumnobuy);
			holder.caigouriqinobuy = (TextView) convertView
					.findViewById(R.id.caigouriqinobuy);
			holder.historypricenobuy = (TextView) convertView
					.findViewById(R.id.historypricenobuy);
			holder.prermbnobuy = (TextView) convertView
					.findViewById(R.id.prermbnobuy);
			holder.minpricenobuy = (TextView) convertView
					.findViewById(R.id.minpricenobuy);
			holder.maxpricenobuy = (TextView) convertView
					.findViewById(R.id.maxpricenobuy);
			holder.addplannobuy = (Button) convertView
					.findViewById(R.id.addplannobuy);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		WisdomBean.FirstZiMu.Goods goods = mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition);
		holder.tvItemChildnobuy.setText(goods.getDrugsBase_DrugName());
		holder.tvGoodsParamnobuy.setText(goods.getDrugsBase_Specification());
		holder.tvManufacturernobuy.setText(goods.getDrugsBase_Manufacturer());
		holder.kucunnumnobuy.setText(String.valueOf(goods.getstock()));
		holder.xiaoliangnumnobuy.setText(goods.getSalesVolume());
		holder.caigouriqinobuy.setText(mListGoods1.get(0).getFirstZiMu()
				.get(groupPosition).getGoods().get(childPosition)
				.getLastTimeString());
		holder.historypricenobuy.setText("￥ "
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
			holder.prermbnobuy.setTextColor(Color.rgb(255, 128, 0));
			// holder.minprice301.setTextColor(Color.rgb(255, 128, 0));
			holder.maxpricenobuy.setTextColor(Color.rgb(255, 128, 0));
		} else {
			holder.prermbnobuy.setTextColor(Color.rgb(34, 139, 34));
			holder.maxpricenobuy.setTextColor(Color.rgb(34, 139, 32));
		}
		if (hostoryprice < minprice) {
			holder.minpricenobuy.setTextColor(Color.rgb(255, 128, 0));
		} else {
			holder.minpricenobuy.setTextColor(Color.rgb(34, 139, 32));
		}
		if (maxprice - minprice > 0.001) {
			holder.maxpricenobuy.setText(" - "
					+ mListGoods1.get(0).getFirstZiMu().get(groupPosition)
							.getGoods().get(childPosition).getmaxPrice());
			// holder.maxprice301.setVisibility(View.VISIBLE);
		} else {
			holder.maxpricenobuy.setText("");
		}
		holder.minpricenobuy
				.setText(mListGoods1.get(0).getFirstZiMu().get(groupPosition)
						.getGoods().get(childPosition).getminPrice());

		holder.addplannobuy.setTag(groupPosition + "," + childPosition);
		holder.addplannobuy.setOnClickListener(listener);
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
				break;
			case R.id.addplannobuy:
				String tagreduce = String.valueOf(v.getTag());
				if (tagreduce.contains(",")) {
					String s[] = tagreduce.split(",");
					int groupPosition = Integer.parseInt(s[0]);
					int childPosition = Integer.parseInt(s[1]);
					addproduct(mListGoods1.get(0).getFirstZiMu()
							.get(groupPosition).getGoods().get(childPosition)
							.getpsn());
					int goodnumnobuy = Integer.parseInt(mListGoods1.get(0)
							.getnotPurchaseCount());
					int goodsnum = Integer.parseInt(mListGoods1.get(0)
							.getmatchingCount());
					mListGoods1.get(0).getFirstZiMu().get(groupPosition)
							.getGoods().remove(childPosition);
					if (mListGoods1.get(0).getFirstZiMu().get(groupPosition)
							.getGoods().size() == 0) {
						mListGoods1.get(0).getFirstZiMu().remove(groupPosition);
					}
					mListGoods1.get(0).setnotPurchaseCount(
							String.valueOf(goodnumnobuy - 1));
					mListGoods1.get(0).setmatchingCount(
							String.valueOf(goodsnum + 1));
					setSettleInfo();
					notifyDataSetChanged();
				}
				// group
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
		// String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods1);
		// 删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
		// if (mChangeListener != null && infos != null) {
		// mChangeListener.onDataChange(infos[0], infos[1]);
		// }
		mChangeListener.onChangeTitle();
	}

	class GroupViewHolder {
		// 字母显示
		TextView tvzimunobuy;
		RelativeLayout nobuystoreinfo;
	}

	class ChildViewHolder {
		/** 商品名称 */
		TextView tvItemChildnobuy;
		/** 商品规格 */
		TextView tvGoodsParamnobuy;
		/** 生产厂家 */
		TextView tvManufacturernobuy;
		/** 库存 */
		TextView kucunnumnobuy;
		/** 半月销量 */
		TextView xiaoliangnumnobuy;
		/** 最近采购日期 */
		TextView caigouriqinobuy;
		/** 历史采购价 */
		TextView historypricenobuy;
		TextView minpricenobuy;
		TextView maxpricenobuy;
		Button addplannobuy;
		TextView prermbnobuy;
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

	// 添加采购计划
	public void addproduct(final String pid) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = mContext.getResources().getString(
							R.string.barUrl);
					URL url = new URL(sturl
							+ "/webapi/CancelDeletePurchaseForPsn?psn=" + pid);
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
}
