package com.newgame.sdk.yyaost;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter301error;
import com.jock.tbshopcar.dao.DBHelperSt301error;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.listener.OnWisdomChangeListener;
import com.jock.tbshopcar.listener.ShoppingCartBiz;
import com.jock.tbshopcar.listener.Wisdom301HttpBiz;
import com.jock.tbshopcar.utils.ToastHelper;
import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.yyaost.SideBar.OnTouchingLetterChangedListener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class FragmentPagest301error extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	private Context mcontext;
	private View view;
	private CustomProgressDialog progressDialog = null;
	private MyExpandableListAdapter301error adapter;

	ImageView back;
	RelativeLayout waitbuyrl;
	RelativeLayout nobuyrl,nobuyrl2;
	TextView nobuymsg;
	
	Button call;

	TextView nobuygoodsnum;
	private SideBar sideBar;
	private TextView dialog;
	
	
	TextView waitbuynum;
	TextView nobuynum;
	TextView nobuynum2;
	
	private List<WisdomBean> mWisdomGoods = new ArrayList<WisdomBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_st301error, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View v = getView();
		mcontext = v.getContext();
		DBHelperSt301error.init(v.getContext());
		ToastHelper.getInstance().init(v.getContext());
		initView(v);
		setAdapter(v);
		requestWisdomList(v);// 获取智慧采购数据
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView);

		back = (ImageView) v.findViewById(R.id.back);
		waitbuyrl = (RelativeLayout) v.findViewById(R.id.waitbuyrl);
		nobuyrl = (RelativeLayout) v.findViewById(R.id.nobuyrl);
		nobuyrl2 = (RelativeLayout) v.findViewById(R.id.nobuyrl2);
		nobuymsg = (TextView) v.findViewById(R.id.nobuymsg);
		call = (Button) v.findViewById(R.id.call);
		nobuygoodsnum = (TextView) v.findViewById(R.id.nobuygoodsnum);
		nobuymsg.setText(getResources().getString(R.string.st30cannotbuymsg));
		
		sideBar = (SideBar) v.findViewById(R.id.sidrbar);
		dialog = (TextView) v.findViewById(R.id.dialog);
		
		waitbuynum= (TextView) v.findViewById(R.id.waitbuynum);
		nobuynum= (TextView) v.findViewById(R.id.nobuynum);
		nobuynum2= (TextView) v.findViewById(R.id.nobuynum2);
//		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					expandableListView.setSelectedGroup(position);
				}
				
			}
		});
		call.setOnClickListener(this);
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter301error(v.getContext());
		expandableListView.setAdapter(adapter);
		
		adapter.setOnWisdom301errorChangeListener(new OnWisdomChangeListener() {

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

			}
		});
		
		// 通过监听器关联Activity和Adapter的关系，解耦；
		View.OnClickListener listener = adapter.getAdapterListener();
		if (listener != null) {
			// 结算时，一般是需要将数据传给订单界面的
			// btnSettle.setOnClickListener(adapter.getAdapterListener());
			back.setOnClickListener(this);
			waitbuyrl.setOnClickListener(this);
			nobuyrl2.setOnClickListener(this);
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
		if (mWisdomGoods.size() == 0) {//获取数据
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
			((MainActivity) getActivity()).GetGoodsDataJson("/webapi/PurchaseHome?tab=1&order=0");//获取数据接口,未匹配
		}else
		{
			
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
			try {
				mWisdomGoods = Wisdom301HttpBiz.handleOrderList(new JSONObject(
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
			
			String[] b=new String[mWisdomGoods.get(0).getFirstZiMu().size()];
			for(int i=0;i<b.length;i++)
			{
				b[i]=mWisdomGoods.get(0).getFirstZiMu().get(i).getPreChar();
			}
			sideBar.setListZiMu(b);
			nobuygoodsnum.setText("共"+mWisdomGoods.get(0).getnotMatchingCount()+"个商品");
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 生成采购计划
		case R.id.back:
			 ((MainActivity)getActivity()).GoSt30();
			break;
		case R.id.waitbuyrl:
			((MainActivity) getActivity()).GoSt30();
			break;
		case R.id.nobuyrl2:
			((MainActivity) getActivity()).GoSt30noBuy();
			break;
		case R.id.call:
			Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+mWisdomGoods.get(0).getmobile()));
			startActivity(in);
			break;
		}
	}
}
