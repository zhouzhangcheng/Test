package com.newgame.sdk.yyaost;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.adapter.MyExpandableListAdapter301error;
import com.jock.tbshopcar.adapter.MyExpandableListAdapter301nobuy;
import com.jock.tbshopcar.dao.DBHelperSt301error;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentPagest301nobuy extends Fragment implements OnClickListener {
	ExpandableListView expandableListView;
	private Context mcontext;
	private View view;
	private CustomProgressDialog progressDialog = null;
	private MyExpandableListAdapter301nobuy adapter;

	ImageView back;
	RelativeLayout waitbuyrl;
	RelativeLayout nobuyrl;
	
	TextView waitbuynumnobuy;
	TextView nobuynum2;
	TextView nobuynum;
	
	TextView alladd;
	

	
	private List<WisdomBean> mWisdomGoods = new ArrayList<WisdomBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_st301nobuy, null);
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
		requestWisdomList(v);
	}

	private void initView(View v) {
		expandableListView = (ExpandableListView) v
				.findViewById(R.id.expandableListView);

		back = (ImageView) v.findViewById(R.id.back);
		waitbuyrl = (RelativeLayout) v.findViewById(R.id.waitbuyrl);
		nobuyrl = (RelativeLayout) v.findViewById(R.id.nobuyrl);
		
		waitbuynumnobuy=(TextView)v.findViewById(R.id.waitbuynumnobuy);
		nobuynum2=(TextView)v.findViewById(R.id.nobuynum2);
		nobuynum=(TextView)v.findViewById(R.id.nobuynum);
		alladd=(TextView)v.findViewById(R.id.alladd);
		alladd.setOnClickListener(this);
		nobuyrl.setOnClickListener(this);
	}

	private void setAdapter(View v) {
		adapter = new MyExpandableListAdapter301nobuy(v.getContext());
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
				if (goodsCount == 0) {
					showEmpty(true);
				} else {
					showEmpty(false);
				}
			}

			@Override
			public void onOpenWisdomBuy(String SchemeName) {

			}
			@Override
			public void onChangeTitle() {
				waitbuynumnobuy.setText(mWisdomGoods.get(0).getmatchingCount());
				nobuynum2.setText(mWisdomGoods.get(0).getnotPurchaseCount());
			}
		});
		
		View.OnClickListener listener = adapter.getAdapterListener();
		if (listener != null) {
			// btnSettle.setOnClickListener(adapter.getAdapterListener());
			back.setOnClickListener(this);
			waitbuyrl.setOnClickListener(this);
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

	private void requestWisdomList(View v) {
		if (mWisdomGoods.size() == 0) {
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
			((MainActivity) getActivity()).GetGoodsDataJson("/webapi/PurchaseHome?tab=2&order=0");
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
	
		public void GetCartInfo(String cartJson, String oldCookie) {
			// Log.v("cartdata", cartJson);
			try {
				mWisdomGoods = Wisdom301HttpBiz.handleOrderList(new JSONObject(
						cartJson), 0, oldCookie, 1);
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
				waitbuynumnobuy.setText(mWisdomGoods.get(0).getmatchingCount());
				nobuynum.setText(mWisdomGoods.get(0).getnotMatchingCount());
				nobuynum2.setText(mWisdomGoods.get(0).getnotPurchaseCount());
				testAddGood();
				updateListView();
			} catch (Exception ex) {
				//
			}
		}
		
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
		}
		
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
		case R.id.back:
			 ((MainActivity)getActivity()).GoSt30();
			break;
		case R.id.waitbuyrl:
			((MainActivity) getActivity()).GoSt30();
			break;
		case R.id.nobuyrl:
			((MainActivity) getActivity()).GoSt30error();
			break;
		case R.id.alladd:
			if(mWisdomGoods!=null)
			{
				if(mWisdomGoods.get(0).getFirstZiMu()!=null)
				{
					addproductall("",mWisdomGoods.get(0).getoldCookie());
					int goodnumnobuy=Integer.parseInt(mWisdomGoods.get(0).getnotPurchaseCount());
					int goodsnum=Integer.parseInt(mWisdomGoods.get(0).getmatchingCount());
					for(int i=0;i<mWisdomGoods.get(0).getFirstZiMu().size();i++)
					{
						mWisdomGoods.get(0).getFirstZiMu().remove(i);
					}
					mWisdomGoods.get(0).setnotPurchaseCount("0");
					mWisdomGoods.get(0).setmatchingCount(String.valueOf(goodnumnobuy+goodsnum));
					waitbuynumnobuy.setText(mWisdomGoods.get(0).getmatchingCount());
					nobuynum2.setText(mWisdomGoods.get(0).getnotPurchaseCount());
					adapter.notifyDataSetChanged();
				}else
				{
					ToastHelper.getInstance().displayToastWithQuickClose(
							"未采购商品为空");
				}
			}else
			{
				ToastHelper.getInstance().displayToastWithQuickClose(
						"未采购商品为空");
			}
			break;
		}
	}
	
	// 添加采购计划
			public void addproductall(final String pid,final String cookie) {
				new Thread(new Runnable() {
					public void run() {
						try {
							String sturl = mcontext.getResources().getString(
									R.string.barUrl);
							URL url = new URL(sturl + "/webapi/CancelDeletePurchaseForPsn?psn="
									+ pid);
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestProperty("Cookie", cookie);
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
