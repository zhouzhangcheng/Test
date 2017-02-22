package com.jock.tbshopcar.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.newgame.sdk.yyaost.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class MyExpandableListAdapter301error extends BaseExpandableListAdapter {
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

	public MyExpandableListAdapter301error(Context context) {
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
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition)
				.getGoods().size();
	}

	public Object getGroup(int groupPosition) {
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mListGoods1.get(0).getFirstZiMu().get(groupPosition)
				.getGoods().get(childPosition);
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
		int k=-1;
		if (mListGoods1 != null && mListGoods1.size()>0 && mListGoods1.get(0).getFirstZiMu() != null) {
			for (int i = 0; i < mListGoods1.get(0).getFirstZiMu().size(); i++) {
				String sortStr = mListGoods1.get(0).getFirstZiMu().get(i).getPreChar();
				int firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
					k= i;
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
					R.layout.item_wisdomerror_group_test, parent, false);
			holder.tvzimuerror=(TextView)convertView.findViewById(R.id.tvzimuerror);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.tvzimuerror.setText(mListGoods1.get(0).getFirstZiMu().get(groupPosition).getPreChar());
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
					R.layout.item_wisdomerror_child_test, parent, false);
			holder.tvItemChild301error=(TextView)convertView.findViewById(R.id.tvItemChild301error);
			holder.tvGoodsParam301error=(TextView)convertView.findViewById(R.id.tvGoodsParam301error);
			holder.tvManufacturer301error=(TextView)convertView.findViewById(R.id.tvManufacturer301error);
			holder.st301errormsg=(TextView)convertView.findViewById(R.id.st301errormsg);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		WisdomBean.FirstZiMu.Goods goods=mListGoods1.get(0).getFirstZiMu().get(groupPosition).getGoods().get(childPosition);
		holder.tvItemChild301error.setText(goods.getDrugsBase_DrugName());
		holder.tvGoodsParam301error.setText(goods.getDrugsBase_Specification());
		holder.tvManufacturer301error.setText(goods.getDrugsBase_Manufacturer());
		String errormsg="";
		if(goods.getBarcode().length()<2)
		{
			errormsg="条码为空";
		}else
		{
			if(goods.getGoods_Package_ID()>0)
			{
				errormsg="未找到该商品";
			}else
			{
				errormsg="未匹配";
			}
		}
		holder.st301errormsg.setText(errormsg);
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
			case R.id.btnSettle:

				// group
				break;
			case R.id.tvEdit:// 切换界面，属于特殊处理，假如没打算切换界面，则不需要这块代码
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
			case R.id.closejiajiagou:
				break;
			case R.id.numadd:
				break;
			case R.id.numreduce:
				break;
			case R.id.ivGoods:
				break;
			case R.id.tvShopNameGroup:
				break;
			case R.id.productdel:// 删除商品
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
//		String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods1);
		// 删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
//		if (mChangeListener != null && infos != null) {
//			mChangeListener.onDataChange(infos[0], infos[1]);
//		}
	}
	class GroupViewHolder {
		//字母显示
		TextView tvzimuerror;
	}

	class ChildViewHolder {
		/** 商品名称 */
		TextView tvItemChild301error;
		/** 商品规格 */
		TextView tvGoodsParam301error;
		/** 生产厂家 */
		TextView tvManufacturer301error;
		/** 错误信息 */
		TextView st301errormsg;
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
