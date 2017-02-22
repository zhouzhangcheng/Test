package com.newgame.sdk.yyaost;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter303;
import com.jock.tbshopcar.dao.DBHelperSt303;
import com.jock.tbshopcar.entity.WisdomBean303;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.listener.Wisdom303HttpBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.jock.tbshopcar.view.UIAlertView;
import com.lxd.widgets.CustomProgressDialog;

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
import android.widget.TextView;

public class FragmentPagest303 extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	private Context mcontext;
	private View view;
	private CustomProgressDialog progressDialog = null;
	private MyExpandableListAdapter303 adapter;

	ImageView st303back;
	TextView bufuhetiaojiantv;
	TextView fuhetiaojiantv;
	TextView st30goodfuhetv;
	TextView st303storefuhe;
	TextView st303acounttv;
	TextView st303youfeitv;
	Button st30buy;
	TextView fangan303;

	String SchemeName = "";

	private List<WisdomBean303> mWisdomGoods = new ArrayList<WisdomBean303>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_st303, null);
		Bundle bundle = getArguments();// 从activity传过来的Bundle
		if (bundle != null) {
			SchemeName = bundle.getString("SchemeName");
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getView();
		mcontext = v.getContext();
		DBHelperSt303.init(v.getContext());
		ToastHelper.getInstance().init(v.getContext());
		initView(v);
		setAdapter(v);
		requestWisdomList(v);// 获取智慧采购数据
		super.onActivityCreated(savedInstanceState);
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView303);

		st303back = (ImageView) v.findViewById(R.id.st303back);
		bufuhetiaojiantv = (TextView) v.findViewById(R.id.bufuhetiaojiantv303);
		fuhetiaojiantv = (TextView) v.findViewById(R.id.fuhetiaojiantv303);
		st30goodfuhetv = (TextView) v.findViewById(R.id.st30goodfuhetv303);
		st303storefuhe = (TextView) v.findViewById(R.id.st303storefuhe303);
		st303acounttv = (TextView) v.findViewById(R.id.st303acounttv303);
		st303youfeitv = (TextView) v.findViewById(R.id.st303youfeitv303);
		st30buy = (Button) v.findViewById(R.id.st30buy303);
		fangan303 = (TextView) v.findViewById(R.id.fangan303);
		fangan303.setText(SchemeName);
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter303(v.getContext());
		expandableListView.setAdapter(adapter);
		adapter.setOnWisdom303ChangeListener(new OnWisdomChangeListener() {

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
				// int goodsCount = ShoppingCartBiz.getGoodsCount303();
				// if (!isNetworkOk) {//网络状态判断暂时不显示
				// }
				// if (goodsCount == 0) {
				// showEmpty(true);
				// } else {
				// showEmpty(false);// 其实不需要做这个判断，因为没有商品的时候，必须退出去添加商品；
				// }
				 DecimalFormat df = new DecimalFormat("######0.00");
				 st303acounttv.setText("合计：￥"
				 + df.format(Double.valueOf(selectMoney)));
				 st303youfeitv.setText("邮费：￥"
				 + df.format(Double.valueOf(postageAcount)));
				 st303storefuhe.setText("店铺" + selectStore);
				 st30goodfuhetv.setText("符合采购条件商品" + selectCount);
				 fuhetiaojiantv.setText(selectCount);
				 bufuhetiaojiantv.setText(String.valueOf(Integer
				 .parseInt(allcount) - Integer.parseInt(selectCount)));
			}

			@Override
			public void onOpenWisdomBuy(String SchemeName) {
				((MainActivity) getActivity()).GoSt303(SchemeName);
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
			st303back.setOnClickListener(this);
			st30buy.setOnClickListener(this);
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
			ShoppingCartBiz.delAllGoods303();
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
					.GetGoodsDataJson("/webapi/PurchaseCart?fa=" + SchemeName);// 获取数据接口
		} else {

		}
	}

	// 得到购物车商品数据
	public void GetCartInfo(String cartJson, String oldCookie) {
		// Log.v("cartdata", cartJson);
		try {
			mWisdomGoods = Wisdom303HttpBiz.handleOrderList(new JSONObject(
					cartJson), 0, oldCookie, 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			mWisdomGoods = null;
			e.printStackTrace();
		}
		expandableListView.setVisibility(View.VISIBLE);
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		testAddGood();
		updateListView();
	}

	public void showEmpty(boolean isEmpty) {
		if (isEmpty) {
			expandableListView.setVisibility(View.GONE);
		} else {
			expandableListView.setVisibility(View.VISIBLE);
		}
	}

	/** 测试添加数据 ，添加的动作是通用的，但数据上只是添加ID而已，数据非通用 */
	private void testAddGood() {
		if (mWisdomGoods != null) {
			for (int i = 0; i < mWisdomGoods.get(0).getStoreList().size(); i++) {
				for (int j = 0; j < mWisdomGoods.get(0).getStoreList().get(i)
						.getProductsList().size(); j++) {
					ShoppingCartBiz.addGoodToCart303(
							String.valueOf(mWisdomGoods.get(0).getStoreList()
									.get(i).getProductsList().get(j).getpid()),
							mWisdomGoods.get(0).getStoreList().get(i)
									.getProductsList().get(j).getbuyCount());
				}
			}
		}
	}

	private void updateListView() {
		// 处理全部选中按钮
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
			for (int i = 0; i < mWisdomGoods.get(0).getStoreList().size(); i++) {
				// Log.v("getStoreCartListsize",
				expandableListView.expandGroup(i);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 生成采购计划
		case R.id.st303back:
			((MainActivity) getActivity()).GoSt302();
			break;
		case R.id.st30buy303:
			// 跳转到结算页面
			if (mWisdomGoods != null) {
				if (mWisdomGoods.get(0).getishasfinish() < 1) {
					String[] infos = ShoppingCartBiz
							.getWisdomSelectGoods(mWisdomGoods);
					if (Integer.parseInt(infos[1]) > 0) {
						showSubmitDialog(Integer.parseInt(infos[1]), infos[0]);
					} else {
						if (infos[0].length() > 1) {

							((MainActivity) getActivity())
									.gotosubmitorder303(infos[0] + "&"
											+ SchemeName);
						} else {
							ToastHelper.getInstance()._toast("亲，没有符合采购条件的商品！");
						}
					}
				} else {
					ToastHelper.getInstance()._toast("数据处理中，请稍后再提交！");
				}
			}
			break;
		}
	}

	// 提交订单
	public void showSubmitDialog(final int goodsnum, final String infos) {
		final UIAlertView delDialog = new UIAlertView(mcontext, "温馨提示", "你还有"
				+ goodsnum + "个商品不符合购买条件，将不能提交订单。你确定不购买这些商品吗？", "再看看", "确定");
		delDialog.show();

		delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

			public void doLeft() {
				delDialog.dismiss();
			}

			public void doRight() {
				delDialog.dismiss();
				((MainActivity) getActivity()).gotosubmitorder303(infos + "&"
						+ SchemeName);
			}
		});
	}
}
