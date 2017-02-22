package com.styao.work.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jock.tbshopcar.utils.ImageLoaderUtil;
import com.jock.tbshopcar.utils.LoadingUtils;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.ExampleApplication;
import com.newgame.sdk.yyaost.MainActivity;
import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.SearchActitity;
import com.styao.work.BaseActivity;
import com.styao.work.ProductList2Activity;
import com.styao.work.ProductListActivity;
import com.styao.work.adapter.ItemShopHomeAdapter;
import com.styao.work.adapter.ItemShopHomeCuxiaoAdapter;
import com.styao.work.bean.ShopHomeData;
import com.styao.work.fragment.SearchFragment;
import com.styao.work.view.SearchResultPopwindow;
import com.styao.work.view.SearchResultPopwindow.SearchResultListener;
import com.zxing.activity.CaptureActivity;

public class ShopHomeActivity extends BaseActivity implements OnClickListener,
		SearchResultListener {
	// type 1�ؼ� 2����
	private ListView listview;
	private View item_shop_home_select_bar, item_shop_home_select_bar2,
			shop_home_visvit_more, shop_home_best_more, shop_home_other_linear;
	private View lastView, lastView2;// �ϴ�ѡ��view

	private TextView shop_home_name, shop_home_name_2, shop_home_collection_tv,
			shop_company_tax, shop_company_deliver, shop_company_free,
			shop_home_tv, shop_home_promotion_tv, shop_home_hot_tv,
			shop_home_new_tv, shop_home_tv2, shop_home_promotion_tv2,
			shop_home_hot_tv2, shop_home_new_tv2;

	private ImageView shop_home_image;

	private ListView headListView;
	private View shop_home_collection, shop_home_search_linear;
	private EditText shopEdit;

	private List<ShopHomeData.ShopGoodInfo> bestProductList = new ArrayList<ShopHomeData.ShopGoodInfo>();
	private List<ShopHomeData.ShopGoodInfo> allList = new ArrayList<ShopHomeData.ShopGoodInfo>();

	private int adapterViewTop, viewTop;
	private int storeId = 1;

	private VolleyTools volleyTools;
	private Gson gson;
	private ShopHomeData shopHomeData;
	private ItemShopHomeAdapter adapter;
	private ItemShopHomeCuxiaoAdapter cuxiaoAdapter;
	private String lastStr = "";
	private SearchResultPopwindow searchResultPop;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				try {
					String str = (String) msg.obj;
					ShopHomeData shopHomeData = gson.fromJson(str,
							ShopHomeData.class);
					ShopHomeActivity.this.shopHomeData = shopHomeData;
					loadData(shopHomeData);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					LoadingUtils.closeLoading(ShopHomeActivity.this);
				}
				break;

			case 4:
				try {
					String str = (String) msg.obj;
					JSONArray jsonArray = new JSONArray(str);
					ArrayList<String> list = new ArrayList<String>();
					for (int i = 0; i < jsonArray.length(); i++) {
						list.add(jsonArray.getString(i));
					}
					if (list.size() > 0) {
						showResultPop(list);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case -1:

				break;
			default:
				break;
			}
		};
	};

	private void showResultPop(List<String> data) {
		if (searchResultPop == null) {
			searchResultPop = new SearchResultPopwindow(this, this,
					shop_home_search_linear.getWidth());
		}
		int[] location2 = new int[2];
		shop_home_search_linear.getLocationInWindow(location2);
		searchResultPop.show(data, shop_home_search_linear, location2[0],
				location2[1] + shop_home_search_linear.getHeight() + 5);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_home);
		initView();
		storeId = getIntent().getIntExtra("storeId", 1);
		volleyTools = new VolleyTools(handler);
		gson = new Gson();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (shopHomeData == null) {
			LoadingUtils.showInitLoading(this);
		} else {
			LoadingUtils.showLoading(this);
		}
		volleyTools.StringRequest(Urls.StoreIndex + storeId, this, 2, null, 1);
	}

	protected void loadData(ShopHomeData shopHomeData) {
		// ���ص�������
		if (shopHomeData != null && shopHomeData.getBaseInfo() != null) {
			shop_home_name.setText(shopHomeData.getBaseInfo().getName());
			shop_home_name_2.setText(shopHomeData.getBaseInfo()
					.getManagerName());
			// 0��Ʊͬ�� 1��������Ʊ2�µ׿�Ʊ3���ӷ�Ʊ
			switch (shopHomeData.getBaseInfo().getTaxPolicy()) {
			case 0:
				shop_company_tax.setText("��Ʊͬ��");
				break;
			case 1:
				shop_company_tax.setText("��������Ʊ");
				break;
			case 2:
				shop_company_tax.setText("�µ׿�Ʊ");
				break;
			case 3:
				shop_company_tax.setText("���ӷ�Ʊ");
				break;
			default:
				break;
			}
			shop_company_deliver.setText("��"
					+ shopHomeData.getBaseInfo().getLowestdeliveryAmount());
			shop_company_free.setText("��"
					+ shopHomeData.getBaseInfo().getLowestFreeShippingAmount());
			shop_home_collection_tv
					.setText(shopHomeData.isFavoriteStore() ? "�ѹ�ע" : "δ��ע");
			shop_home_collection.setSelected(shopHomeData.isFavoriteStore());
			shop_home_collection_tv.setSelected(shopHomeData.isFavoriteStore());
			ImageLoaderUtil.getInstance().displayImage(
					shopHomeData.getBaseInfo().getLogo(), shop_home_image);
		}

		// ���ش�����Ϣ
		if (shopHomeData != null
				&& shopHomeData.getAddPriceBuyProductsList() != null) {
			if (shopHomeData.getSpecialPriceList() != null) {
				int num = shopHomeData.getAddPriceBuyProductsList().size()
					+ shopHomeData.getSpecialPriceList().size();
					shop_home_promotion_tv.setText("������" + num + "��");
			shop_home_promotion_tv2.setText("������" + num + "��");
			}else{
				shop_home_promotion_tv.setText("������" +  shopHomeData.getAddPriceBuyProductsList().size() + "��");
			shop_home_promotion_tv2.setText("������" +  shopHomeData.getAddPriceBuyProductsList().size() + "��");
			}
			
			
		}

		// ����������Ϣ
		if (shopHomeData != null && shopHomeData.getHotProductList() != null) {
			shop_home_hot_tv.setText("������"
					+ shopHomeData.getHotProductList().size() + "��");
			shop_home_hot_tv2.setText("������"
					+ shopHomeData.getHotProductList().size() + "��");
		}

		// ����������Ϣ
		if (shopHomeData != null && shopHomeData.getNewProductList() != null) {
			shop_home_new_tv.setText("���£�"
					+ shopHomeData.getNewProductList().size() + "��");
			shop_home_new_tv2.setText("���£�"
					+ shopHomeData.getNewProductList().size() + "��");
		}
		// ���ؾ�Ʒ��Ϣ
		if (shopHomeData != null && shopHomeData.getBestProductList() != null) {
			bestProductList.clear();
			bestProductList.addAll(shopHomeData.getBestProductList());
			((ItemShopHomeAdapter) headListView.getAdapter())
					.setLogin(shopHomeData.isIsLogin());
		}
		// ��Ʒ�����Ƿ񳬹�6��
		if (shopHomeData.getBestProductListRecordCount() > 6) {
			shop_home_best_more.setVisibility(View.VISIBLE);
			shop_home_best_more.setOnClickListener(this);
		}
		// ���������Ƿ񳬹�6��
		if (shopHomeData.getVisitProductRecordCount() > 6) {
			shop_home_visvit_more.setVisibility(View.VISIBLE);
			shop_home_visvit_more.setOnClickListener(this);
		}
		// ����������Ϣ
		adapter.setLogin(shopHomeData.isIsLogin());
		switch (lastView.getId()) {
		case R.id.shop_home_promotion_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getAddPriceBuyProductsList() != null) {
				allList.clear();
				if(shopHomeData.getSpecialPriceList() != null){
					allList.addAll(shopHomeData.getSpecialPriceList());
				}
				allList.addAll(shopHomeData.getAddPriceBuyProductsList());
				if (cuxiaoAdapter == null) {
					cuxiaoAdapter = new ItemShopHomeCuxiaoAdapter(this, allList);
				}
				listview.setAdapter(cuxiaoAdapter);
				cuxiaoAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.shop_home_hot_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getHotProductList() != null) {
				allList.clear();
				allList.addAll(shopHomeData.getHotProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.shop_home_new_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getNewProductList() != null) {
				allList.clear();
				allList.addAll(shopHomeData.getNewProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}

			break;
		case R.id.shop_home_tv: // ��ҳ
			if (shopHomeData != null) {
				allList.clear();
				allList.addAll(shopHomeData.getVisitProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listview);
		item_shop_home_select_bar = findViewById(R.id.item_shop_home_select_bar);
		item_shop_home_select_bar.setVisibility(View.INVISIBLE);
		shop_home_promotion_tv = (TextView) findViewById(R.id.shop_home_promotion_tv);
		shop_home_hot_tv = (TextView) findViewById(R.id.shop_home_hot_tv);
		shop_home_new_tv = (TextView) findViewById(R.id.shop_home_new_tv);
		shop_home_tv = (TextView) findViewById(R.id.shop_home_tv);
		shopEdit = (EditText) findViewById(R.id.shop_home_edit);
		shop_home_search_linear = findViewById(R.id.shop_home_search_linear);
		shop_home_promotion_tv.setOnClickListener(this);
		shop_home_hot_tv.setOnClickListener(this);
		shop_home_new_tv.setOnClickListener(this);
		shop_home_tv.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.shop_home_search_bt).setOnClickListener(this);
		findViewById(R.id.shop_home_san_bt).setOnClickListener(this);
		findViewById(R.id.shop_kefu).setOnClickListener(this);
		findViewById(R.id.shop_good_class).setOnClickListener(this);
		findViewById(R.id.shop_all_good).setOnClickListener(this);

		shopEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int start, int before,
					int count) {
				if (!arg0.toString().trim().equals("")
						&& !lastStr.equals(arg0.toString().trim())) {
					String url = Urls.mquery;
					try {
						url = url + URLEncoder.encode(arg0.toString(), "utf-8")
								+ "&storeid=" + storeId;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					volleyTools.StringRequest(url, ShopHomeActivity.this, 2,
							null, 4);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		View view = View.inflate(this, R.layout.item_shop_home_head, null);
		item_shop_home_select_bar2 = view
				.findViewById(R.id.item_shop_home_select_bar);
		listview.addHeaderView(view);
		shop_home_collection = view.findViewById(R.id.shop_home_collection);
		shop_home_image = (ImageView) view.findViewById(R.id.shop_home_image);
		shop_home_other_linear = view.findViewById(R.id.shop_home_other_linear);
		shop_home_best_more = view.findViewById(R.id.shop_home_best_more);
		shop_home_visvit_more = view.findViewById(R.id.shop_home_visvit_more);
		shop_home_name = (TextView) view.findViewById(R.id.shop_home_name);
		shop_home_name_2 = (TextView) view.findViewById(R.id.shop_home_name_2);
		shop_home_collection_tv = (TextView) view
				.findViewById(R.id.shop_home_collection_tv);
		shop_company_tax = (TextView) view.findViewById(R.id.shop_company_tax);
		shop_company_deliver = (TextView) view
				.findViewById(R.id.shop_company_deliver);
		shop_company_free = (TextView) view
				.findViewById(R.id.shop_company_free);
		shop_home_tv2 = (TextView) view.findViewById(R.id.shop_home_tv);
		shop_home_promotion_tv2 = (TextView) view
				.findViewById(R.id.shop_home_promotion_tv);
		shop_home_hot_tv2 = (TextView) view.findViewById(R.id.shop_home_hot_tv);
		shop_home_new_tv2 = (TextView) view.findViewById(R.id.shop_home_new_tv);
		headListView = (ListView) view.findViewById(R.id.head_listview);
		shop_home_promotion_tv2.setOnClickListener(this);
		shop_home_hot_tv2.setOnClickListener(this);
		shop_home_new_tv2.setOnClickListener(this);
		shop_home_tv2.setOnClickListener(this);
		view.findViewById(R.id.shop_home_collection_linear).setOnClickListener(
				this);
		;

		headListView.setAdapter(new ItemShopHomeAdapter(this, bestProductList));
		// Ĭ��ѡ�е�����ҳ
		lastView = shop_home_tv;
		lastView2 = shop_home_tv2;
		lastView.setSelected(true);
		lastView2.setSelected(true);
		// ���������¼�
		adapter = new ItemShopHomeAdapter(this, allList);
		listview.setAdapter(adapter);

		listview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					View oneView = listview.getChildAt(0);
					if (oneView != null) {
						int top = oneView.getTop();
						if (adapterViewTop + top >= viewTop) {
							item_shop_home_select_bar
									.setVisibility(View.INVISIBLE);
						} else {
							item_shop_home_select_bar
									.setVisibility(View.VISIBLE);
						}
					} else {
						item_shop_home_select_bar.setVisibility(View.INVISIBLE);
					}
				} else {
					item_shop_home_select_bar.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && adapterViewTop == 0) {
			int[] location = new int[2];
			item_shop_home_select_bar2.getLocationInWindow(location);
			adapterViewTop = location[1];
			item_shop_home_select_bar.getLocationInWindow(location);
			viewTop = location[1];
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shop_home_promotion_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getAddPriceBuyProductsList() != null) {
				selectBar(shop_home_promotion_tv, shop_home_promotion_tv2);
				shop_home_other_linear.setVisibility(View.GONE);
				allList.clear();
				if(shopHomeData.getSpecialPriceList() != null){
					allList.addAll(shopHomeData.getSpecialPriceList());
				}
				allList.addAll(shopHomeData.getAddPriceBuyProductsList());
				if (cuxiaoAdapter == null) {
					cuxiaoAdapter = new ItemShopHomeCuxiaoAdapter(this, allList);
				}
				listview.setAdapter(cuxiaoAdapter);
				cuxiaoAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.shop_home_hot_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getHotProductList() != null) {
				selectBar(shop_home_hot_tv, shop_home_hot_tv2);
				shop_home_other_linear.setVisibility(View.GONE);
				allList.clear();
				allList.addAll(shopHomeData.getHotProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.shop_home_new_tv:// ����
			if (shopHomeData != null
					&& shopHomeData.getNewProductList() != null) {
				selectBar(shop_home_new_tv, shop_home_new_tv2);
				shop_home_other_linear.setVisibility(View.GONE);
				allList.clear();
				allList.addAll(shopHomeData.getNewProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}

			break;
		case R.id.shop_home_tv: // ��ҳ
			if (shopHomeData != null) {
				selectBar(shop_home_tv, shop_home_tv2);
				shop_home_other_linear.setVisibility(View.VISIBLE);
				allList.clear();
				allList.addAll(shopHomeData.getVisitProductList());
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.shop_home_collection_linear:// ����ղذ�ť
			if (shopHomeData != null && shopHomeData.isIsLogin()) {
				if (shopHomeData.isFavoriteStore()) { // ȡ���ղ�
					volleyTools.StringRequest(Urls.DelFavoriteStore + storeId,
							this, 2, null, 2);
					shop_home_collection.setSelected(false);
					shop_home_collection_tv.setSelected(false);
					shop_home_collection_tv.setText("δ��ע");
					shopHomeData.setFavoriteStore(false);
				} else { // �ղ�
					volleyTools
							.StringRequest(Urls.AddStoreToFavorite + storeId,
									this, 2, null, 3);
					shop_home_collection.setSelected(true);
					shop_home_collection_tv.setSelected(true);
					shop_home_collection_tv.setText("�ѹ�ע");
					shopHomeData.setFavoriteStore(true);
				}
			} else {
				isGoToLogin();
			}
			break;
		case R.id.back:
			finish();
			break;
		case R.id.shop_home_search_bt:
			String str = shopEdit.getText().toString();
			Intent intent = new Intent(this, ProductList2Activity.class);
			intent.putExtra("keyword", str);
			startActivity(intent);
			break;
		case R.id.shop_home_san_bt:
			intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.shop_kefu:
			intent = new Intent(this, ShopHomeKefuActivity.class);
			intent.putExtra("storeid", storeId);
			startActivity(intent);
			break;
		case R.id.shop_good_class:
			intent = new Intent(this, ShopClassActivity.class);
			intent.putExtra("storeid", storeId);
			startActivity(intent);
			break;
		case R.id.shop_all_good:
			intent = new Intent(this, ProductList2Activity.class);
			intent.putExtra("storeid", storeId);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void isGoToLogin() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this); // �ȵõ�������
//		builder.setTitle("��ʾ"); // ���ñ���
//		builder.setMessage("�Ƿ�ȥ��¼?"); // ��������
//		builder.setIcon(R.drawable.ic_launcher);// ����ͼ�꣬ͼƬid����
//		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { // ����ȷ����ť
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss(); // �ر�dialog
//						
//					}
//				});
//		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { // ����ȡ����ť
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//		// ��������������ˣ���������ʾ����
//		builder.create().show();
		Toast.makeText(ExampleApplication.getContext(), "���¼", Toast.LENGTH_SHORT).show();
	}

	private void selectBar(View v, View v2) {
		lastView.setSelected(false);
		lastView2.setSelected(false);
		v.setSelected(true);
		v2.setSelected(true);
		lastView = v;
		lastView2 = v2;
	}

	@Override
	public void getName(String name) {
		Intent intent = new Intent(this, ProductList2Activity.class);
		intent.putExtra("keyword", name);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int arg0, int resultCode, Intent data) {
		if (resultCode == 1) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result"); // ��ͻ�ȡ��ɨ�������
			Intent intent = new Intent();
			intent.putExtra("fromcategories", "scanResult," + scanResult);
			intent.setClass(this, SearchActitity.class);
			startActivityForResult(intent, 10);
		}
	}
}
