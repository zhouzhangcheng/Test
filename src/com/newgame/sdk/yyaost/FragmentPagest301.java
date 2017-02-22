package com.newgame.sdk.yyaost;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter301;
import com.jock.tbshopcar.dao.DBHelperSt301;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.listener.Wisdom301HttpBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.yyaost.SideBar.OnTouchingLetterChangedListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentPagest301 extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	private Context mcontext;
	private View view;
	private CustomProgressDialog progressDialog = null;
	private MyExpandableListAdapter301 adapter;

	ImageView back;
	RelativeLayout waitbuyrl;
	RelativeLayout nobuyrl;
	RelativeLayout zimurl;
	RelativeLayout stockrl;
	RelativeLayout jinjicdrl;
	Button buyplan;

	TextView waitbuynum;
	TextView nobuynum;
	TextView nobuynum2;

	TextView zimu;
	TextView stock;
	TextView jinjicd;

	private SideBar sidrbarst301;

	static String cartJsonSort = "";
	static String oldCookieSort = "";


	static int what = 2;

	private List<WisdomBean> mWisdomGoods = new ArrayList<WisdomBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_st301, null);
		return view;
	}

	public int[] getBuyPlanLocation() {
		int[] location = new int[2];
		buyplan.getLocationInWindow(location);
		return location;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View v = getView();
		mcontext = v.getContext();
		DBHelperSt301.init(v.getContext());
		ToastHelper.getInstance().init(v.getContext());
		initView(v);
		setAdapter(v);
		requestWisdomList(v);// 获取智慧采购数据
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView302);

		back = (ImageView) v.findViewById(R.id.back);
		waitbuyrl = (RelativeLayout) v.findViewById(R.id.waitbuyrl);
		nobuyrl = (RelativeLayout) v.findViewById(R.id.nobuyrl);
		zimurl = (RelativeLayout) v.findViewById(R.id.zimurl);
		stockrl = (RelativeLayout) v.findViewById(R.id.stockrl);
		jinjicdrl = (RelativeLayout) v.findViewById(R.id.jinjicdrl);
		buyplan = (Button) v.findViewById(R.id.buyplan);
		waitbuynum = (TextView) v.findViewById(R.id.waitbuynum);
		nobuynum = (TextView) v.findViewById(R.id.nobuynum);
		nobuynum2=(TextView)v.findViewById(R.id.nobuynum2);

		zimu = (TextView) v.findViewById(R.id.zimu);
		stock = (TextView) v.findViewById(R.id.stock);
		jinjicd = (TextView) v.findViewById(R.id.jinjicd);

		sidrbarst301 = (SideBar) v.findViewById(R.id.sidrbarst301);
		stockrl.setOnClickListener(this);
		zimurl.setOnClickListener(this);
		jinjicdrl.setOnClickListener(this);

		v.findViewById(R.id.nobuyrl2).setOnClickListener(this);
		;

		// 设置右侧触摸监听
		sidrbarst301
				.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

					@Override
					public void onTouchingLetterChanged(String s) {
						// 该字母首次出现的位置
						int position = adapter.getPositionForSection(s
								.charAt(0));
						if (position != -1) {
							expandableListView.setSelectedGroup(position);
						}

					}
				});
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter301(v.getContext(),
				expandableListView);
		expandableListView.setAdapter(adapter);

		adapter.setOnWisdom301ChangeListener(new OnWisdomChangeListener() {

			@Override
			public void onSelectItem(boolean isSelectedAll) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onOpenStore(String storeid) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onOpenActivity(String pid) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(String selectCount, String selectMoney,
					String allcount, String selectStore, String postageAcount) {
				// TODO Auto-generated method stub
				int goodsCount = ShoppingCartBiz.getGoodsCount();
				// if (!isNetworkOk) {//网络状态判断暂时不显示
				// }
				if (goodsCount == 0) {
					showEmpty(true);
				} else {
					showEmpty(false);// 其实不需要做这个判断，因为没有商品的时候，必须退出去添加商品；
				}
			}

			@Override
			public void onOpenWisdomBuy(String SchemeName) {

			}
			@Override
			public void onChangeTitle() {
				waitbuynum.setText(mWisdomGoods.get(0).getmatchingCount());
				nobuynum2.setText(mWisdomGoods.get(0).getnotPurchaseCount());
			}
		});

		// 通过监听器关联Activity和Adapter的关系，解耦；
		View.OnClickListener listener = adapter.getAdapterListener();
		if (listener != null) {
			// 结算时，一般是需要将数据传给订单界面的
			// btnSettle.setOnClickListener(adapter.getAdapterListener());
			buyplan.setOnClickListener(this);
			nobuyrl.setOnClickListener(this);
			back.setOnClickListener(this);
		}
		expandableListView.setGroupIndicator(null);
		expandableListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					public boolean onGroupClick(
							ExpandableListView expandableListView, View view,
							int i, long l) {
						return true;
					}
				});
	}

	/** 获取购物车列表的数据（数据和网络请求也是非通用部分） */
	private void requestWisdomList(View v) {
		if (mWisdomGoods.size() == 0) {// 获取数据
			ShoppingCartBiz.delAllGoods301();
			expandableListView.setVisibility(View.GONE);
			if (progressDialog == null) {
				progressDialog = CustomProgressDialog.createDialog(mcontext);
				progressDialog.setMessage(getResources().getString(
						R.string.data_load));
				progressDialog.show();
			} else {
				progressDialog.setMessage(getResources().getString(
						R.string.data_load));
				progressDialog.show();
			}
			int isfirst=((MainActivity) getActivity()).GoSt30first(false);
			if(isfirst==1)
			{
				((MainActivity) getActivity()).GoSt30first(true);
			}
			((MainActivity) getActivity())
					.GetGoodsDataJson("/webapi/PurchaseHome?tab=0&order=2&init="+isfirst);// 获取数据接口
		} else {

		}
	}

	public void showEmpty(boolean isEmpty) {
		if (isEmpty) {
			expandableListView.setVisibility(View.GONE);
		} else {
			expandableListView.setVisibility(View.VISIBLE);
		}
	}

	public void hideloading() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	// 得到购物车商品数据
	public void GetCartInfo(String cartJson, String oldCookie) {
		// Log.v("cartdata", cartJson);
		cartJsonSort = cartJson;
		oldCookieSort = oldCookie;
		try {
			mWisdomGoods = Wisdom301HttpBiz.handleOrderList(new JSONObject(
					cartJson), 0, oldCookie, what);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			mWisdomGoods = null;
			e.printStackTrace();
		}
		try {

			expandableListView.setVisibility(View.VISIBLE);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}

			waitbuynum.setText(mWisdomGoods.get(0).getmatchingCount());
			nobuynum.setText(mWisdomGoods.get(0).getnotMatchingCount());
			nobuynum2.setText(mWisdomGoods.get(0).getnotPurchaseCount());

			testAddGood();
			updateListView();
		} catch (Exception ex) {
			//
		}
	}

	/** 测试添加数据 ，添加的动作是通用的，但数据上只是添加ID而已，数据非通用 */
	private void testAddGood() {
		if (mWisdomGoods != null) {
			for (int i = 0; i < mWisdomGoods.get(0).getFirstZiMu().size(); i++) {
				for (int j = 0; j < mWisdomGoods.get(0).getFirstZiMu().get(i)
						.getGoods().size(); j++) {
					ShoppingCartBiz.addGoodToCart301(
							String.valueOf(mWisdomGoods.get(0).getFirstZiMu()
									.get(i).getGoods().get(j)
									.getGoods_Package_ID()),
							String.valueOf(mWisdomGoods.get(0).getFirstZiMu()
									.get(i).getGoods().get(j).getbuyCount()));
				}
			}
		}
	}

	private void updateListView() {
		// 处理全部选中按钮

		boolean isSelectAll = true;
		if (mWisdomGoods != null) {
			adapter.setList(mWisdomGoods);
			adapter.notifyDataSetChanged();
			expandAllGroup();
		} else {
			adapter.notifyDataSetChanged();
			expandAllGroup();
		}
	}

	/**
	 * 展开所有组
	 */
	private void expandAllGroup() {
		// Log.v("getStoreCartListsize",
		if (mWisdomGoods == null) {
			expandableListView.expandGroup(0);
		} else {
			for (int i = 0; i < mWisdomGoods.get(0).getFirstZiMu().size(); i++) {
				// Log.v("getStoreCartListsize",
				expandableListView.expandGroup(i);
			}
		}
	}

	// 排序
	public void GetCartInfoSort(String cartJson, String oldCookie, int sort) {
		// Log.v("cartdata", cartJson);
		try {
			mWisdomGoods = Wisdom301HttpBiz.handleOrderList(new JSONObject(
					cartJson), 0, oldCookie, sort);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			mWisdomGoods = null;
			e.printStackTrace();
		}
		try {

			expandableListView.setVisibility(View.VISIBLE);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			String[] b = new String[mWisdomGoods.get(0).getFirstZiMu().size()];
			for (int i = 0; i < b.length; i++) {
				b[i] = mWisdomGoods.get(0).getFirstZiMu().get(i).getPreChar();
			}
			sidrbarst301.setListZiMu(b);
			testAddGood();
			updateListView();
		} catch (Exception ex) {
			//
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 生成采购计划
		case R.id.buyplan:
			if (mWisdomGoods != null && mWisdomGoods.size() > 0) {
				if (mWisdomGoods.get(0) == null
						|| mWisdomGoods.get(0).getFirstZiMu() == null
						|| mWisdomGoods.get(0).getFirstZiMu().size() == 0) {
					ToastHelper.getInstance()._toast("你没有可生成采购计划的商品！");
				} else {
					((MainActivity) getActivity()).GoSt302();
				}
			}
			break;
			
			
		case R.id.nobuyrl:
			((MainActivity) getActivity()).GoSt30error();
			break;
		case R.id.nobuyrl2:
			((MainActivity) getActivity()).GoSt30noBuy();
			break;
		case R.id.back:
			((MainActivity) getActivity()).GotoMine();
			break;
		case R.id.stockrl:
			zimu.setTextColor(this.getResources().getColor(R.color.bardefault));
			jinjicd.setTextColor(this.getResources().getColor(
					R.color.bardefault));
			stock.setTextColor(this.getResources().getColor(R.color.black));
			sidrbarst301.setVisibility(view.GONE);
			what = 1;
//			GetCartInfoSort(cartJsonSort, oldCookieSort, 1);
			((MainActivity) getActivity())
			.GetGoodsDataJson("/webapi/PurchaseHome?tab=0&order=1");// 获取数据接口
			break;
		case R.id.zimurl:
			zimu.setTextColor(this.getResources().getColor(R.color.black));
			jinjicd.setTextColor(this.getResources().getColor(
					R.color.bardefault));
			stock.setTextColor(this.getResources().getColor(R.color.bardefault));
			sidrbarst301.setVisibility(view.VISIBLE);
			what = 0;
//			GetCartInfoSort(cartJsonSort, oldCookieSort, 0);
			((MainActivity) getActivity())
			.GetGoodsDataJson("/webapi/PurchaseHome?tab=0&order=0");// 获取数据接口
			break;
		case R.id.jinjicdrl:
			zimu.setTextColor(this.getResources().getColor(R.color.bardefault));
			jinjicd.setTextColor(this.getResources().getColor(R.color.black));
			stock.setTextColor(this.getResources().getColor(R.color.bardefault));
			sidrbarst301.setVisibility(view.GONE);
			what = 2;
//			GetCartInfoSort(cartJsonSort, oldCookieSort, 2);
			((MainActivity) getActivity())
			.GetGoodsDataJson("/webapi/PurchaseHome?tab=0&order=2");// 获取数据接口
			break;
		}
	}
}
