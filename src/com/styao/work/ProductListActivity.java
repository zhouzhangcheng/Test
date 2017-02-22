package com.styao.work;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.jock.tbshopcar.adapter.ProductListAdapter;
import com.jock.tbshopcar.adapter.StoreListAdapter;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.R;
import com.styao.model.BaseInfoList;
import com.styao.model.MedicineBean;
import com.styao.model.MedicineListBean;
import com.styao.model.PageModel;
import com.styao.model.StoreBean;
import com.styao.work.adapter.ItemSearchListAdapter.OnItemListener;
import com.styao.work.view.SearchPopwindow;
import com.styao.work.view.SearchPopwindow.SearchPopListener;
import com.styao.work.view.SearchResultPopwindow;
import com.styao.work.view.SearchResultPopwindow.SearchResultListener;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ProductListActivity extends FragmentActivity
		implements OnClickListener, SearchPopListener, SearchResultListener, OnItemListener {
	private DrawerLayout mDrawerLayout;
	private RelativeLayout right_drawer;
	private boolean isDirection_right = false;
	private ListView listView = null;
	private List<MedicineListBean> data;
	private List<BaseInfoList> data2;
	private ProductListAdapter productAd;
	private StoreListAdapter storeAd;
	private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15, rb16, rb17,
			rb18, rb19;
	private Map<String, String> values;
	private RadioButton[] arrayBut1, arrayBut2, arrayBut3, arrayBut4, arrayBut5, arrayBut6, arrayBut7;
	private TextView priceT, zongheT, reteT, rightT, product_screen_t;
	private boolean ispirce = true;
	private VolleyTools volley;
	private int FLAG_SHOP_GOOD = 1;
	private TextView search_title_name;
	private String lastStr = "", cateId = "";
	private SearchPopwindow searchGoodAndShopPop;
	private SearchResultPopwindow searchResultPop;
	private EditText editText;
	private LinearLayout search_top_linear_bar;
	private String keyWords = "";
	private Map<String, String> maps = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String str = (String) msg.obj;
			Gson gson = new Gson();
			switch (msg.what) {
			case -1:
				Toast.makeText(ProductListActivity.this, "解析错误！", Toast.LENGTH_LONG).show();
				break;
			// 药品
			case 1:
				MedicineBean jsonBean = gson.fromJson(str, MedicineBean.class);
				List<MedicineListBean> lsitValues = jsonBean.getList();
				data.clear();
				for (int i = 0; i < lsitValues.size(); i++) {
					data.add(lsitValues.get(i));
				}
				listView.setAdapter(productAd);
				productAd.notifyDataSetChanged();
				break;
			// 商店
			case 2:
				StoreBean storeBean = gson.fromJson(str, StoreBean.class);
				PageModel page = storeBean.getPageModel();
				List<BaseInfoList> baseList = storeBean.getBaseInfoList();
				data2.clear();
				for (int i = 0; i < baseList.size(); i++) {
					data2.add(baseList.get(i));
				}
				listView.setAdapter(storeAd);
				storeAd.notifyDataSetChanged();
				break;
			case 3:// 搜索框
				try {
					str = (String) msg.obj;
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
			default:
				break;
			}

		};
	};

	private void showResultPop(List<String> data) {
		if (searchResultPop == null) {
			searchResultPop = new SearchResultPopwindow(this, this, search_top_linear_bar.getWidth());
		}
		int[] location2 = new int[2];
		search_top_linear_bar.getLocationInWindow(location2);
		searchResultPop.show(data, search_top_linear_bar, location2[0],
				location2[1] + search_top_linear_bar.getHeight() + 5);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		keyWords = getIntent().getStringExtra("keyword");
		if (keyWords != null) {
			try {
				keyWords = subStrings(keyWords);
				keyWords = URLEncoder.encode(keyWords, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cateId = getIntent().getStringExtra("cateId");
		if (null != cateId) {
			cateId = cateId;
		}
		int shopOrgood = getIntent().getIntExtra("shopOrgood", 1);
		FLAG_SHOP_GOOD = shopOrgood;
		// intent.putExtra("keyword", name);
		// intent.putExtra("shopOrgood", FLAG_SHOP_GOOD);
		init();

		maps = new HashMap<String, String>();
		if (FLAG_SHOP_GOOD == 1) {
			maps.put("Tag_PharmAttribute_fullPath", "&Tag_PharmAttribute_fullPath=0");
			maps.put("producttype", "&producttype=0");
			maps.put("cuxiao", "&cuxiao=0");
			maps.put("GrossMarginRange", "&GrossMarginRange=不限");
			maps.put("PriceRange", "&PriceRange=不限");
		} else {
			maps.put("sortwhere", "&sortwhere=0");
		}
		values = new HashMap<String, String>();
		volley = new VolleyTools(handler);
		netWork(FLAG_SHOP_GOOD);
		setTitle();
	}

	/**
	 * title
	 */
	private void setTitle() {
		findViewById(R.id.search_bt1).setOnClickListener(this);
		findViewById(R.id.search_name_bt1).setOnClickListener(this);
		findViewById(R.id.search_back).setOnClickListener(this);
		findViewById(R.id.search_scanf).setOnClickListener(this);
		search_title_name = (TextView) findViewById(R.id.search_title_name1);
		search_top_linear_bar = (LinearLayout) findViewById(R.id.search_top_linear_bar1);
		editText = (EditText) findViewById(R.id.search_edit1);
		// 监听文字变化
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (!arg0.toString().trim().equals("") && !lastStr.equals(arg0.toString().trim())) {
					// String url = FLAG_SHOP_GOOD == 1 ? Urls.mquery
					// : Urls.mqueryForStore;
					String value = arg0.toString();
					try {
						keyWords = subStrings(value);
						keyWords = URLEncoder.encode(keyWords, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					netWork(3);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		// 监听搜索按键
		editText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {

					return true;
				}
				return false;
			}
		});
	}

	private void init() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());
		product_screen_t = (TextView) findViewById(R.id.product_screen_t);
		product_screen_t.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (FLAG_SHOP_GOOD == 1) {
					if (!isDirection_right) {
						mDrawerLayout.openDrawer(right_drawer);
					} else {
						mDrawerLayout.closeDrawer(right_drawer);
					}
				} else {
					Toast.makeText(ProductListActivity.this, "包郵~~", Toast.LENGTH_SHORT).show();
				}

			}
		});
		listView = (ListView) findViewById(R.id.product_content_list);
		data = new ArrayList<MedicineListBean>();
		productAd = new ProductListAdapter(data, this, FLAG_SHOP_GOOD);
		data2 = new ArrayList<BaseInfoList>();
		storeAd = new StoreListAdapter(data2, this);
		listView.setAdapter(productAd);
		onClickB();
		// diyi~~~~~~~~~~~~~~~~~~~~~~
		rb1 = (RadioButton) findViewById(R.id.radio_rght_b1);
		rb2 = (RadioButton) findViewById(R.id.radio_rght_b2);
		rb3 = (RadioButton) findViewById(R.id.radio_rght_b3);

		// diyi~~~~~~~~~~~~~~~~~~~~~~
		rb4 = (RadioButton) findViewById(R.id.radio_rght_b4);
		rb5 = (RadioButton) findViewById(R.id.radio_rght_b5);
		rb6 = (RadioButton) findViewById(R.id.radio_rght_b6);
		// diyi~~~~~~~~~~~~~~~~~~~~~~
		rb7 = (RadioButton) findViewById(R.id.radio_rght_b7);
		rb8 = (RadioButton) findViewById(R.id.radio_rght_b8);
		rb9 = (RadioButton) findViewById(R.id.radio_rght_b9);
		rb10 = (RadioButton) findViewById(R.id.radio_rght_b10);
		rb11 = (RadioButton) findViewById(R.id.radio_rght_b11);
		rb12 = (RadioButton) findViewById(R.id.radio_rght_b12);
		// diyi~~~~~~~~~~~~~~~~~~~~~~
		rb13 = (RadioButton) findViewById(R.id.radio_rght_b13);
		rb14 = (RadioButton) findViewById(R.id.radio_rght_b14);
		rb15 = (RadioButton) findViewById(R.id.radio_rght_b15);
		rb16 = (RadioButton) findViewById(R.id.radio_rght_b16);
		rb17 = (RadioButton) findViewById(R.id.radio_rght_b17);
		rb18 = (RadioButton) findViewById(R.id.radio_rght_b18);
		rb19 = (RadioButton) findViewById(R.id.radio_rght_b19);
		arrayBut1 = new RadioButton[] { rb1, rb2, rb3 };
		arrayBut2 = new RadioButton[] { rb4, rb5, rb6 };
		arrayBut3 = new RadioButton[] { rb7, rb8, rb9 };
		arrayBut4 = new RadioButton[] { rb10, rb11, rb12 };
		arrayBut5 = new RadioButton[] { rb13, rb14, rb15 };
		arrayBut6 = new RadioButton[] { rb16, rb17, rb18 };
		arrayBut7 = new RadioButton[] { rb19 };
		RadioGroup r1 = (RadioGroup) findViewById(R.id.radio_rght_r1);
		r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut1);
			}
		});
		RadioGroup r2 = (RadioGroup) findViewById(R.id.radio_rght_r2);
		r2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut2);
			}
		});
		RadioGroup r3 = (RadioGroup) findViewById(R.id.radio_rght_r3);
		r3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut3);
				cleanGB(arrayBut4);
			}
		});
		RadioGroup r4 = (RadioGroup) findViewById(R.id.radio_rght_r4);
		r4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut4);
				cleanGB(arrayBut3);
			}
		});
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		RadioGroup r5 = (RadioGroup) findViewById(R.id.radio_rght_r5);
		r5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut5);
				cleanGB(arrayBut6);
				cleanGB(arrayBut7);
			}
		});

		RadioGroup r6 = (RadioGroup) findViewById(R.id.radio_rght_r6);
		r6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changeGB(arrayBut6);
				cleanGB(arrayBut5);
				cleanGB(arrayBut7);
			}
		});

		RadioGroup r7 = (RadioGroup) findViewById(R.id.radio_rght_r7);
		r7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				rb19.setBackgroundResource(R.drawable.select_but_orange);
				rb19.setTextColor(ProductListActivity.this.getResources().getColor(R.color.orange));
				cleanGB(arrayBut5);
				cleanGB(arrayBut6);
			}
		});
	}

	private void onClickB() {
		zongheT = (TextView) findViewById(R.id.product_zonghe_t);
		zongheT.setOnClickListener(this);
		priceT = (TextView) findViewById(R.id.product_pirce_title_t);
		priceT.setOnClickListener(this);
		reteT = (TextView) findViewById(R.id.product_rete_title_t);
		reteT.setOnClickListener(this);
		rightT = (TextView) findViewById(R.id.right_soure_b);
		if (FLAG_SHOP_GOOD == 1) {
			rightT.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					mDrawerLayout.closeDrawer(right_drawer);
					/**
					 * keyWords:搜索关键字 Tag_PharmAttribute_fullPath:产品分类
					 * producttype：:商品类型 (2，首推联盟，1，普药，0，不限)
					 * cuxiao:促销类型(1,加价购，2，包邮,0,不限) GrossMarginRange:毛利率区间
					 * (不限,0_20,20_40,40_60,60_80,80_100)BaseInfoList PriceRange
					 * :价格区间(不限,0_20,20_40,40_60,60_80,80_100,100_100)
					 */
					updateLists();
					// 商品类型
					for (int i = 0; i < arrayBut1.length; i++) {
						if (arrayBut1[i].isChecked()) {
							maps.put("producttype", "&producttype=" + i);
						}
					}
					// 促销
					for (int i = 0; i < arrayBut2.length; i++) {
						if (arrayBut2[i].isChecked()) {
							maps.put("cuxiao", "&cuxiao=" + i);
						}
					}
					// 率利
					for (int i = 1; i < arrayBut3.length; i++) {
						if (arrayBut3[i].isChecked()) {
							maps.put("GrossMarginRange", etherPirce("&GrossMarginRange", i));
						}
					}
					for (int i = 0; i < arrayBut4.length; i++) {
						maps.put("GrossMarginRange", etherPirce2("&GrossMarginRange", i));

					}
					// 价格
					for (int i = 0; i < arrayBut5.length; i++) {
						if (arrayBut5[i].isChecked()) {
							maps.put("PriceRange", etherPirce("&PriceRange", i));
						}
					}
					for (int i = 0; i < arrayBut6.length; i++) {
						if (arrayBut6[i].isChecked()) {
							maps.put("PriceRange", etherPirce2("&PriceRange", i));
						}
					}
					netWork(1);
				}
			});
		} else {
			showIstitle();
		}
		showIstitle();
	}

	private void showIstitle() {
		if (FLAG_SHOP_GOOD == 1) {
			priceT.setText("价格");
			reteT.setText("毛率利");
			product_screen_t.setText("筛选");
		} else {
			priceT.setText("热卖");
			reteT.setText("最新");
			product_screen_t.setText("免邮");
		}

	}

	private class DrawerLayoutStateListener extends DrawerLayout.SimpleDrawerListener {
		@Override
		public void onDrawerOpened(android.view.View drawerView) {
			if (drawerView == right_drawer) {
				isDirection_right = true;
			}
		}

		@Override
		public void onDrawerClosed(android.view.View drawerView) {
			if (drawerView == right_drawer) {
				isDirection_right = false;
			}
		}
	}

	private void changeGB(RadioButton[] but) {
		for (int i = 0; i < but.length; i++) {
			if (but[i].isChecked()) {
				but[i].setBackgroundResource(R.drawable.select_but_orange);
				but[i].setTextColor(this.getResources().getColor(R.color.orange));
			} else {
				but[i].setBackgroundResource(R.drawable.select_but_black);
				but[i].setTextColor(this.getResources().getColor(R.color.hui_zxy));
			}
		}
	}

	private void cleanGB(RadioButton[] but) {
		for (int i = 0; i < but.length; i++) {
			but[i].setBackgroundResource(R.drawable.select_but_black);
			but[i].setTextColor(this.getResources().getColor(R.color.hui_zxy));
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.product_zonghe_t:
			updateLists();
			reteT.setCompoundDrawables(null, null, null, null);
			priceT.setCompoundDrawables(null, null, null, null);
			netWork(1);
			break;
		case R.id.product_pirce_title_t:
			updateLists();
			if (ispirce) {
				maps.put("sort", "&sort=3");
				reteT.setCompoundDrawables(null, null, null, null);
				priceT.setCompoundDrawables(null, null, getDrawableS(R.drawable.icon_up), null);
				ispirce = false;
			} else {
				maps.put("sort", "&sort=4");
				reteT.setCompoundDrawables(null, null, null, null);
				priceT.setCompoundDrawables(null, null, getDrawableS(R.drawable.icon_down), null);
				ispirce = true;
			}
			netWork(1);
			break;
		case R.id.product_rete_title_t:
			updateLists();
			if (ispirce) {
				maps.put("sort", "&sort=5");
				priceT.setCompoundDrawables(null, null, null, null);
				reteT.setCompoundDrawables(null, null, getDrawableS(R.drawable.icon_up), null);
				ispirce = false;
			} else {
				maps.put("sort", "&sort=6");
				priceT.setCompoundDrawables(null, null, null, null);
				reteT.setCompoundDrawables(null, null, getDrawableS(R.drawable.icon_down), null);
				ispirce = true;
			}
			netWork(1);
			break;
		case R.id.search_bt1:
			int[] location = new int[2];
			arg0.getLocationInWindow(location);
			if (searchGoodAndShopPop == null) {
				searchGoodAndShopPop = new SearchPopwindow(this, this);
			}
			searchGoodAndShopPop.showAtLocation(arg0, Gravity.NO_GRAVITY, location[0], location[1] + arg0.getHeight());
			break;
		case R.id.search_name_bt1:
			try {
				System.out.println("" + keyWords);
				keyWords = subStrings(keyWords);
				keyWords = URLEncoder.encode(keyWords, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			netWork(FLAG_SHOP_GOOD);
			editText.setText("");
			showIstitle();
			searchResultPop.dismiss();
			break;
		case R.id.search_back:
			finish();
			break;
		case R.id.search_scanf:
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param type
	 *            1 调用药品 2 调用商家
	 */
	private void netWork(int type) {
		String url = null;
		if (keyWords == null) {
			keyWords = "";
		}
		switch (type) {
		case 1:
			// url = Urls.searchDetail + "?" + urlApend.toString();
			String Tag_PharmAttribute_fullPath = maps.get("Tag_PharmAttribute_fullPath");
			String producttype = maps.get("producttype");
			String cuxiao = maps.get("cuxiao");
			String GrossMarginRange = maps.get("GrossMarginRange");
			String PriceRange = maps.get("PriceRange");
			url = Urls.searchDetail + "?" + "keyWords=" + keyWords + Tag_PharmAttribute_fullPath + producttype + cuxiao
					+ cuxiao + GrossMarginRange + PriceRange;
			break;
		case 2:
			String sortwhere = maps.get("sortwhere");
			if (sortwhere == null) {
				sortwhere = "&sortwhere=0";
			}
			url = Urls.StoreSearchResult + "?" + "keyWords=" + keyWords + sortwhere;
			break;
		case 3:
			String values = "";
			try {

				keyWords = subStrings(editText.getText().toString());
				values = URLEncoder.encode(keyWords, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (FLAG_SHOP_GOOD == 1) {
				url = Urls.mquery + values;
			} else {
				url = Urls.mqueryForStore + values;
			}
			break;
		}
		volley.StringRequest(url, this, 1, values, type);
	}

	private Drawable getDrawableS(int icon) {
		Drawable drawable = ProductListActivity.this.getResources().getDrawable(icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		return drawable;
	}

	private void updateLists() {
		data.removeAll(data);
		listView.setAdapter(null);
		productAd.notifyDataSetChanged();
	}

	private String etherPirce(String key, int index) {
		String value = key + "=0_20";
		switch (index) {
		case 1:
			value = key + "=20_40";
			break;
		case 2:
			value = key + "=40_60";

			break;
		case 3:
			value = key + "=60_80";
			break;
		case 4:
			value = key + "=80_100";
			break;
		default:
			break;
		}
		return value;
	}

	private String etherPirce2(String key, int index) {
		String value = key + "=0_20";
		switch (index) {
		case 1:
			value = key + "=40_60";
			break;
		case 2:
			value = key + "=60_80";
			break;
		case 3:
			value = key + "=80_100";
			break;
		default:
			break;
		}
		return value;
	}

	@Override
	public void onSelectItem(String cateId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getName(int flag) {
		lastStr = "";
		FLAG_SHOP_GOOD = flag;
		if (flag == 1) {
			search_title_name.setText("商品");
		} else if (flag == 2) {
			search_title_name.setText("店铺");
		}
		showIstitle();
	}

	@Override
	public void getName(String name) {
		editText.setText(name);
		keyWords = name;
	}

	private String subStrings(String str) {
		boolean res = str.contains("(");
		if (res) {
			String[] sArray = str.split("\\(", str.length());
			return sArray[0];
		} else {
			return str;
		}
	}
}
