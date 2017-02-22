package com.newgame.sdk.yyaost;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter;
import com.jock.tbshopcar.dao.DBHelper;
import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.listener.OnShoppingCartChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.listener.ShoppingCartHttpBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.lxd.widgets.CustomProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class FragmentPage3 extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	// CustomExpandableListView expandableListView;
	ImageView ivSelectAll;
	TextView btnSettle;
	TextView tvCountMoney;
	TextView tvTitle;
	RelativeLayout rlShoppingCartEmpty;
	RelativeLayout rlBottomBar;
	Context mcontext;
	private CustomProgressDialog progressDialog = null;

	private List<ShoppingCartBean1> mListGoods1 = new ArrayList<ShoppingCartBean1>();
	private MyExpandableListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activity_shopping_cart, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View v = getView();
		mcontext = v.getContext();
		DBHelper.init(v.getContext());
		ToastHelper.getInstance().init(v.getContext());
		initView(v);
		setAdapter(v);
		requestShoppingCartList(v);
	}

	// 按返回键处理方法
	public void OnkeydownF() {
		((MainActivity) getActivity()).gotohome();
	}

	// 得到购物车商品数据
	public void GetCartInfo(String cartJson, String oldCookie) {
		// Log.v("cartdata", cartJson);
		try {
			mListGoods1 = ShoppingCartHttpBiz.handleOrderList(new JSONObject(
					cartJson), 0, oldCookie);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			mListGoods1 = null;
			e.printStackTrace();
		}
		try {
			testAddGood();
			expandableListView.setVisibility(View.VISIBLE);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}

			updateListView();
		} catch (Exception ex) {
			//
		}
	}

	public void hideloading() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView);
		// expandableListView = (CustomExpandableListView) v
		// .findViewById(R.id.expandableListView);
		ivSelectAll = (ImageView) v.findViewById(R.id.ivSelectAll);
		btnSettle = (TextView) v.findViewById(R.id.btnSettle);
		tvCountMoney = (TextView) v.findViewById(R.id.tvCountMoney);
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		rlShoppingCartEmpty = (RelativeLayout) v
				.findViewById(R.id.rlShoppingCartEmpty);
		rlBottomBar = (RelativeLayout) v.findViewById(R.id.rlBottomBar);
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter(v.getContext());
		expandableListView.setAdapter(adapter);
		adapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {

			public void onDataChange(String selectCount, String selectMoney) {
				int goodsCount = ShoppingCartBiz.getGoodsCount();
				// if (!isNetworkOk) {//网络状态判断暂时不显示
				// }
				if (goodsCount == 0) {
					showEmpty(true);
				} else {
					showEmpty(false);// 其实不需要做这个判断，因为没有商品的时候，必须退出去添加商品；
				}
				String countMoney = "";
				DecimalFormat df = new DecimalFormat("######0.00");
				countMoney = "合计：￥" + df.format(Double.valueOf(selectMoney));
				String countGoods = "去结算";
				String title = "购物车";
				tvCountMoney.setText(countMoney);
				btnSettle.setText(countGoods);
				tvTitle.setText(title);
			}

			public void onSelectItem(boolean isSelectedAll) {
				ShoppingCartBiz.checkItem(isSelectedAll, ivSelectAll);
			}

			public void onOpenActivity(String pid) {
				((MainActivity) getActivity()).cartGotoProductBuy(pid);
			}

			public void onOpenStore(String storeid) {
				((MainActivity) getActivity()).JsToLoacleFunction("storeid,"
						+ storeid);
			}
		});
		// 通过监听器关联Activity和Adapter的关系，解耦；
		View.OnClickListener listener = adapter.getAdapterListener();
		if (listener != null) {
			// 即使换了一个新的Adapter，也要将“全选事件”传递给adapter处理；
			ivSelectAll.setOnClickListener(adapter.getAdapterListener());
			// 结算时，一般是需要将数据传给订单界面的
			// btnSettle.setOnClickListener(adapter.getAdapterListener());
			btnSettle.setOnClickListener(this);
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

	public void showEmpty(boolean isEmpty) {
		if (isEmpty) {
			expandableListView.setVisibility(View.GONE);
			rlShoppingCartEmpty.setVisibility(View.VISIBLE);
			rlBottomBar.setVisibility(View.GONE);
		} else {
			expandableListView.setVisibility(View.VISIBLE);
			rlShoppingCartEmpty.setVisibility(View.GONE);
			rlBottomBar.setVisibility(View.VISIBLE);
		}
	}

	/** 获取购物车列表的数据（数据和网络请求也是非通用部分） */
	private void requestShoppingCartList(View v) {
		ShoppingCartBiz.delAllGoods();
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
		((MainActivity) getActivity()).GetGoodsDataJson("/webapi/Cart");
	}

	/** 测试添加数据 ，添加的动作是通用的，但数据上只是添加ID而已，数据非通用 */
	private void testAddGood() {
		if (mListGoods1 != null) {
			for (int i = 0; i < mListGoods1.get(0).getStoreCartList().size(); i++) {
				for (int j = 0; j < mListGoods1.get(0).getStoreCartList()
						.get(i).getCartProductList().size(); j++) {
					ShoppingCartBiz
							.addGoodToCart(mListGoods1.get(0)
									.getStoreCartList().get(i)
									.getCartProductList().get(j)
									.getOrderProductInfo().get(0).getPid(),
									mListGoods1.get(0).getStoreCartList()
											.get(i).getCartProductList().get(j)
											.getOrderProductInfo().get(0)
											.getBuyCount());
				}
			}
		}
	}

	private void updateListView() {
		// 处理全部选中按钮

		boolean isSelectAll = true;
		if (mListGoods1 != null) {
			if (mListGoods1.size() > 0) {
				for (int i = 0; i < mListGoods1.get(0).getStoreCartList()
						.size(); i++) {
					for (int j = 0; j < mListGoods1.get(0).getStoreCartList()
							.get(i).getCartProductList().size(); j++) {
						if (!mListGoods1.get(0).getStoreCartList().get(i)
								.getCartProductList().get(j)
								.getOrderProductInfo().get(0).getIsSelect()) {
							isSelectAll = false;
							break;
						}
					}
				}
			}
			ShoppingCartBiz.checkItem(isSelectAll, ivSelectAll);
			adapter.setList(mListGoods1);
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
		// mListGoods1.get(0).getStoreCartList().size()+"");
		if (mListGoods1 == null) {
			expandableListView.expandGroup(0);
		} else {
			for (int i = 0; i < mListGoods1.get(0).getStoreCartList().size(); i++) {
				// Log.v("getStoreCartListsize",
				// mListGoods1.get(0).getStoreCartList().size()+"");
				expandableListView.expandGroup(i);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 去结算的点击跳转界面，跳转到填写订单界面
		case R.id.btnSettle:
			// 判断是否含有不发货商品
			// if (ShoppingCartBiz.hasCannotSend(mListGoods1)) {
			if (mListGoods1 != null) {
				if (mListGoods1.get(0).getishasfinish() < 1) {
					if (ShoppingCartBiz.hasSelectedGoods(mListGoods1)) {
						String comitorder = ShoppingCartBiz
								.hasfullstock(mListGoods1);
						if (comitorder.length() > 1) {
							ToastHelper.getInstance()._toast(comitorder);
						} else {
							((MainActivity) getActivity())
									.gotosubmitorder(ShoppingCartBiz
											.GetSelectedGoods(mListGoods1));
						}
					} else {
						ToastHelper.getInstance()._toast("亲，先选择商品！");
					}
				} else {
					ToastHelper.getInstance()._toast("数据处理中，请稍后再提交！");
				}
			}
			// } else {
			// ToastHelper.getInstance()._toast("亲，您选的商品不满足店铺发货金额！");
			// }

			break;
		}
	}

}
