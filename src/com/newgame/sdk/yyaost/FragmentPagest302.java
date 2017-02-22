package com.newgame.sdk.yyaost;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter;
import com.jock.tbshopcar.adapter.MyExpandableListAdapter302;
import com.jock.tbshopcar.dao.DBHelperSt301;
import com.jock.tbshopcar.dao.DBHelperSt302;
import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.WisdomBean302;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.listener.Wisdom301HttpBiz;
import com.jock.tbshopcar.listener.Wisdom302HttpBiz;
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
import android.widget.TextView;

public class FragmentPagest302 extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	private Context mcontext;
	private View view;
	private CustomProgressDialog progressDialog = null;
	private MyExpandableListAdapter302 adapter;

	ImageView st302back;
	TextView st302riqitb;

	private List<WisdomBean302> mWisdomGoods = new ArrayList<WisdomBean302>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_st302, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View v = getView();
		mcontext = v.getContext();
		DBHelperSt302.init(v.getContext());
		ToastHelper.getInstance().init(v.getContext());
		initView(v);
		setAdapter(v);
		requestWisdomList(v);// 获取智慧采购数据
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView302);

		st302back = (ImageView) v.findViewById(R.id.st302back);
		st302riqitb = (TextView) v.findViewById(R.id.st302riqitb);
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter302(v.getContext());
		expandableListView.setAdapter(adapter);
		adapter.setOnWisdom302ChangeListener(new OnWisdomChangeListener() {

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
			public void onDataChange(String selectCount, String selectMoney,String allcount,String selectStore,String postageAcount) {
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
			public void onOpenWisdomBuy(String SchemeName)
			{
				((MainActivity)getActivity()).GoSt303(SchemeName);
			}
			@Override
			public void onChangeTitle() {

			}
		});

		// 通过监听器关联Activity和Adapter的关系，解耦；
		View.OnClickListener listener = adapter.getAdapterListener();
		if (listener != null) {
			// 结算时，一般是需要将数据传给订单界面的
			// btnSettle.setOnClickListener(adapter.getAdapterListener());
			st302back.setOnClickListener(this);
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
			ShoppingCartBiz.delAllGoods302();
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
			((MainActivity) getActivity())
					.GetGoodsDataJson("/webapi/PurchaseScheme");// 获取数据接口
		} else {

		}
	}

	// 得到购物车商品数据
	public void GetCartInfo(String cartJson, String oldCookie) {
		// Log.v("cartdata", cartJson);
		try {
			mWisdomGoods = Wisdom302HttpBiz.handleOrderList(new JSONObject(
					cartJson), 0, oldCookie, 0);
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
			testAddGood();
			updateListView();
		} catch (Exception ex) {
			//
		}
	}

	/** 测试添加数据 ，添加的动作是通用的，但数据上只是添加ID而已，数据非通用 */
	private void testAddGood() {
		if (mWisdomGoods != null) {
			for (int i = 0; i < mWisdomGoods.get(0).getSchemeName().size(); i++) {

				ShoppingCartBiz.addGoodToCart302(
						String.valueOf(mWisdomGoods.get(0).getSchemeName()
								.get(i).getID()),
						String.valueOf(mWisdomGoods.get(0).getSchemeName()
								.get(i).getID()));

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
			for (int i = 0; i < mWisdomGoods.get(0).getSchemeName().size(); i++) {
				// Log.v("getStoreCartListsize",
				expandableListView.expandGroup(i);
			}
		}
	}

	public void showEmpty(boolean isEmpty) {
		if (isEmpty) {
			expandableListView.setVisibility(View.GONE);
		} else {
			expandableListView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 生成采购计划
		case R.id.st302back:
			((MainActivity) getActivity()).GoSt30();
			break;
		}
	}
}