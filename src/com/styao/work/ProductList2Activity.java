package com.styao.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jock.tbshopcar.adapter.ProductList2Adapter;
import com.jock.tbshopcar.adapter.ProductListAdapter;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.R;
import com.styao.model.MedicineBean;
import com.styao.model.MedicineListBean;
import com.styao.work.bean.ShopDescData;
import com.styao.work.bean.ShopGoodData;

public class ProductList2Activity extends FragmentActivity implements
		OnClickListener {
	private DrawerLayout mDrawerLayout;
	private RelativeLayout right_drawer;
	private boolean isDirection_right = false;
	private ListView listView = null;
	private List<ShopGoodData.StoreProduct> data;
	private ProductList2Adapter productAd;
	private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10,
			rb11, rb12, rb13, rb14, rb15, rb16, rb17, rb18, rb19;
	private Map<String, String> values;
	private RadioButton[] arrayBut1, arrayBut2, arrayBut3, arrayBut4,
			arrayBut5, arrayBut6, arrayBut7;
	private TextView priceT, zongheT, reteT;
	private boolean ispirce = true;
	private int storeid = 0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String str = (String) msg.obj;
				Gson gson = new Gson();
				ShopGoodData shopGood = gson.fromJson(str, ShopGoodData.class);
				if (shopGood != null
						&& shopGood.getStoreOlineProductList() != null
						&& shopGood.getStoreOlineProductList().size() > 0) {
					data.addAll(shopGood.getStoreOlineProductList());
					productAd.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
			listView.setAdapter(productAd);
			productAd.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		storeid = getIntent().getIntExtra("storeid", 0);
		init();
		values = new HashMap<String, String>();
		netWork();
	}

	private void netWork() {
		VolleyTools volley = new VolleyTools(handler);
		String url = Urls.InStoreSearchProduct + storeid;
		StringBuffer sb = new StringBuffer(url);
		if (values.size() > 0) {
			Iterator iter = values.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				sb.append("&");
				sb.append(key);
				sb.append("=");
				sb.append(val);
			}
		}
		volley.StringRequest(sb.toString(), this, 2, values, 1);
	}

	private void init() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
		findViewById(R.id.search_bt1).setVisibility(View.GONE);
		((TextView)findViewById(R.id.search_edit1)).setHint("店内搜：药品通用名、商品名、厂家");
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());
		findViewById(R.id.product_screen_t).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!isDirection_right) {
							mDrawerLayout.openDrawer(right_drawer);
						} else {
							mDrawerLayout.closeDrawer(right_drawer);
						}
					}
				});
		listView = (ListView) findViewById(R.id.product_content_list);
		data = new ArrayList<ShopGoodData.StoreProduct>();
		productAd = new ProductList2Adapter(data, this, storeid);
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
				rb19.setTextColor(ProductList2Activity.this.getResources()
						.getColor(R.color.orange));
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
		findViewById(R.id.right_soure_b).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						mDrawerLayout.closeDrawer(right_drawer);
						/**
						 * keyWords:搜索关键字 Tag_PharmAttribute_fullPath:产品分类
						 * producttype：:商品类型 (2，首推联盟，1，普药，0，不限)
						 * cuxiao:促销类型(1,加价购，2，包邮,0,不限) GrossMarginRange:毛利率区间
						 * (不限,0_20,20_40,40_60,60_80,80_100)BaseInfoList
						 * PriceRange
						 * :价格区间(不限,0_20,20_40,40_60,60_80,80_100,100_100)
						 */
						updateLists();
						// 商品类型
						for (int i = 0; i < arrayBut1.length; i++) {
							if (arrayBut1[i].isChecked()) {
								values.put("producttype", String.valueOf(i));
							}
						}
						// 促销
						for (int i = 0; i < arrayBut2.length; i++) {
							if (arrayBut2[i].isChecked()) {
								values.put("cuxiao", String.valueOf(i));
							}
						}
						// 率利
						for (int i = 1; i < arrayBut3.length; i++) {
							if (arrayBut3[i].isChecked()) {
								values.put("GrossMarginRange", etherPirce(i));
							}
						}
						for (int i = 0; i < arrayBut4.length; i++) {
							if (arrayBut4[i].isChecked()) {
								values.put("GrossMarginRange", etherPirce2(i));
							}
						}
						// 价格
						for (int i = 0; i < arrayBut5.length; i++) {
							if (arrayBut5[i].isChecked()) {
								values.put("PriceRange", etherPirce(i));
							}
						}
						for (int i = 0; i < arrayBut6.length; i++) {
							if (arrayBut6[i].isChecked()) {
								values.put("PriceRange", etherPirce2(i));
							}
						}
						netWork();
					}
				});
	}

	private class DrawerLayoutStateListener extends
			DrawerLayout.SimpleDrawerListener {
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
				but[i].setTextColor(this.getResources()
						.getColor(R.color.orange));
			} else {
				but[i].setBackgroundResource(R.drawable.select_but_black);
				but[i].setTextColor(this.getResources().getColor(
						R.color.hui_zxy));
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
			netWork();
			break;
		case R.id.product_pirce_title_t:
			updateLists();
			if (ispirce) {
				values.put("sort", "3");
				reteT.setCompoundDrawables(null, null, null, null);
				priceT.setCompoundDrawables(null, null,
						getDrawableS(R.drawable.icon_up), null);
				ispirce = false;
			} else {
				values.put("sort", "4");
				reteT.setCompoundDrawables(null, null, null, null);
				priceT.setCompoundDrawables(null, null,
						getDrawableS(R.drawable.icon_down), null);
				ispirce = true;
			}
			netWork();
			break;
		case R.id.product_rete_title_t:
			updateLists();
			if (ispirce) {
				values.put("sort", "5");
				priceT.setCompoundDrawables(null, null, null, null);
				reteT.setCompoundDrawables(null, null,
						getDrawableS(R.drawable.icon_up), null);
				ispirce = false;
			} else {
				values.put("sort", "6");
				priceT.setCompoundDrawables(null, null, null, null);
				reteT.setCompoundDrawables(null, null,
						getDrawableS(R.drawable.icon_down), null);
				ispirce = true;
			}
			netWork();
			break;
		default:
			break;
		}
	}

	private Drawable getDrawableS(int icon) {
		Drawable drawable = ProductList2Activity.this.getResources()
				.getDrawable(icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		System.out.println("" + drawable);
		return drawable;
	}

	private void updateLists() {
		data.removeAll(data);
		for (int i = 0; i < values.size(); i++) {
			values.remove(i);
		}
		listView.setAdapter(null);
		productAd.notifyDataSetChanged();
	}

	private String etherPirce(int index) {
		String value = "0_20";
		switch (index) {
		case 1:
			value = "20_40";
			break;
		case 2:
			value = "40_60";
			break;
		case 3:
			value = "60_80";
			break;
		case 4:
			value = "80_100";
			break;
		default:
			break;
		}
		return value;
	}

	private String etherPirce2(int index) {
		String value = "0_20";
		switch (index) {
		case 1:
			value = "40_60";
			break;
		case 2:
			value = "60_80";
			break;
		case 3:
			value = "80_100";
			break;
		default:
			break;
		}
		return value;
	}
}
