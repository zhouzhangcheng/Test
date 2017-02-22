package com.styao.work.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jock.tbshopcar.utils.KeyBoardInputUtil;
import com.jock.tbshopcar.utils.LoadingUtils;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.MainActivity;
import com.newgame.sdk.yyaost.R;
import com.styao.work.ProductListActivity;
import com.styao.work.activity.ShopHomeActivity;
import com.styao.work.activity.WebActivity;
import com.styao.work.adapter.ItemSearchGridAdapter;
import com.styao.work.adapter.ItemSearchListAdapter;
import com.styao.work.adapter.ItemSearchListAdapter.OnItemListener;
import com.styao.work.bean.SearchCateBean;
import com.styao.work.view.SearchPopwindow;
import com.styao.work.view.SearchPopwindow.SearchPopListener;
import com.styao.work.view.SearchResultPopwindow;
import com.styao.work.view.SearchResultPopwindow.SearchResultListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchFragment extends Fragment implements OnClickListener,
		SearchPopListener, SearchResultListener, OnItemListener {
	// 控件
	private ListView listview;
	private ListView gridview;
	private ImageView image;
	private TextView rankinglistTv, search_title_name, item_search_superclass;
	private LinearLayout leftHeadView, search_top_linear_bar;
	private EditText editText;

	private SearchPopwindow searchGoodAndShopPop;
	private SearchResultPopwindow searchResultPop;
	private int FLAG_SHOP_GOOD = 1;// 默认为1，商品
	private VolleyTools volleyTools;
	private String lastStr = "";
	private List<SearchCateBean> cateData = new ArrayList<SearchCateBean>();
	private List<SearchCateBean> cateDetailData = new ArrayList<SearchCateBean>();
	private ItemSearchListAdapter adapter;
	private ItemSearchGridAdapter gridAdapter;
	private View baseView;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String str;
			switch (msg.what) {
			case 1: // 访问 商品搜索下拉提示 // 访问 店铺搜索下拉提示
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
			case 2: // 访问探索分类
				try {
					str = (String) msg.obj;
					JSONArray jsonArray = new JSONArray(str);
					cateData.clear();
					LoadingUtils.closeLoading(baseView);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						SearchCateBean cate = new SearchCateBean();
						cate.setCateName(obj.getString("CateName"));
						cate.setCateId(obj.getString("CateId"));
						cate.setImagesUrl(obj.getString("ImagesUrl"));
						JSONArray jsonArr2 = obj.getJSONArray("CateList");
						ArrayList<SearchCateBean> list2 = new ArrayList<SearchCateBean>();
						for (int j = 0; j < jsonArr2.length(); j++) {
							JSONObject obj2 = jsonArr2.getJSONObject(j);
							SearchCateBean cate2 = new SearchCateBean();
							cate2.setCateName(obj2.getString("CateName"));
							cate2.setCateId(obj2.getString("CateId"));
							list2.add(cate2);
						}
						cate.setCateList(list2);
						cateData.add(cate);
					}
					adapter.updateData();
					if (cateData.size() > 0) {
						for (int j = 0; j < cateData.size(); j++) {
							if (cateData.get(j) != null
									&& cateData.get(j).getCateList() != null
									&& cateData.get(j).getCateList().size() > 0) {
								getNetData(cateData.get(j).getCateList().get(0)
										.getCateId());
								adapter.setSelectLocation(j, 0);
								break;
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 3: // 访问探索分类详情
				try {
					str = (String) msg.obj;
					JSONArray jsonArray = new JSONArray(str);
					cateDetailData.clear();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						SearchCateBean cate = new SearchCateBean();
						cate.setCateName(obj.getString("CateName"));
						cate.setCateId(obj.getString("CateId"));
						cate.setImagesUrl(obj.getString("ImagesUrl"));
						cateDetailData.add(cate);
					}
					gridAdapter.notifyDataSetChanged();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case -1:

				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_search, null);
		initView(baseView);
		volleyTools = new VolleyTools(handler);
		return baseView;
	}

	private void showResultPop(List<String> data) {
		if (searchResultPop == null) {
			searchResultPop = new SearchResultPopwindow(getActivity(), this,
					search_top_linear_bar.getWidth());
		}
		int[] location2 = new int[2];
		search_top_linear_bar.getLocationInWindow(location2);
		searchResultPop.show(data, search_top_linear_bar, location2[0],
				location2[1] + search_top_linear_bar.getHeight() + 5);
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.search_listview);
		gridview = (ListView) view.findViewById(R.id.search_gridview);
		image = (ImageView) view.findViewById(R.id.item_image);
		search_title_name = (TextView) view
				.findViewById(R.id.search_title_name);
		leftHeadView = (LinearLayout) view
				.findViewById(R.id.item_search_left_name);
		search_top_linear_bar = (LinearLayout) view
				.findViewById(R.id.search_top_linear_bar);
		editText = (EditText) view.findViewById(R.id.search_edit);
		item_search_superclass = (TextView) view
				.findViewById(R.id.item_search_superclass);
		view.findViewById(R.id.search_bt).setOnClickListener(this);
		view.findViewById(R.id.search_name_bt).setOnClickListener(this);
		leftHeadView.setVisibility(View.VISIBLE);

		// 监听文字变化
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!arg0.toString().trim().equals("")
						&& !lastStr.equals(arg0.toString().trim())) {
					String url = FLAG_SHOP_GOOD == 1 ? Urls.mquery
							: Urls.mqueryForStore;
					try {
						url = url + URLEncoder.encode(arg0.toString(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					volleyTools.StringRequest(url,
							SearchFragment.this.getActivity(), 2, null, 1);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		// 监听搜索按键
		editText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| actionId == EditorInfo.IME_ACTION_DONE
						|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					Intent intent = new Intent(getActivity(),
							ProductListActivity.class);
					intent.putExtra("keyword", v.getText().toString());
					intent.putExtra("shopOrgood", FLAG_SHOP_GOOD);
					startActivity(intent);
					KeyBoardInputUtil.closeInput(
							SearchFragment.this.getActivity(), editText);
					return true;
				}
				return false;
			}
		});
		View headView = View.inflate(this.getActivity(),
				R.layout.item_search_gridview_head, null);
		gridview.addHeaderView(headView);
		rankinglistTv = (TextView) headView.findViewById(R.id.ranking_list_tv);
		headView.findViewById(R.id.goto_ranking_list).setOnClickListener(this);
		gridAdapter = new ItemSearchGridAdapter(
				SearchFragment.this.getActivity(), cateDetailData);
		gridview.setAdapter(gridAdapter);
		adapter = new ItemSearchListAdapter(this.getActivity(), cateData, this,
				rankinglistTv);
		listview.setAdapter(adapter);
		// listview 滚动监听
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				setScrollTitle(firstVisibleItem);
				if (adapter.toplist.contains(firstVisibleItem + 1)
						&& firstVisibleItem != 0) {
					View headView = listview.getChildAt(0);
					int top = headView.getTop();
					leftHeadView.setPadding(0, top, 0, 0);
				} else {
					leftHeadView.setPadding(0, 0, 0, 0);

				}
			}
		});
	}

	private void setScrollTitle(int position) {
		if (adapter != null && adapter.toplist != null
				&& adapter.toplist.size() > 0 && cateData != null
				&& cateData.size() > 0) {
			boolean b = false;
			for (int j = 0; j < adapter.toplist.size() - 1; j++) {
				if (position >= adapter.toplist.get(j)
						&& position <= adapter.toplist.get(j + 1)) {
					item_search_superclass.setText(cateData.get(j)
							.getCateName());
					setImage(j);
					b = true;
				}
			}
			if (!b) {
				item_search_superclass.setText(cateData
						.get(cateData.size() - 1).getCateName());
				setImage(cateData.size() - 1);
			}
		}
	}

	private void setImage(int i) {
		switch (i) {
		case 0:
			image.setImageResource(R.drawable.icon_search_1);
			break;
		case 1:
			image.setImageResource(R.drawable.icon_search_2);
			break;
		case 2:
			image.setImageResource(R.drawable.icon_search_3);
			break;
		case 3:
			image.setImageResource(R.drawable.icon_search_4);
			break;
		case 4:
			image.setImageResource(R.drawable.icon_search_5);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (cateData.size() > 0) {
			LoadingUtils.showLoading(baseView);
		} else {
			LoadingUtils.showInitLoading(baseView);
		}
		getNetData("-1");
	}

	private void getNetData(String cateId) {
		if (cateId.equals("-1")) {
			volleyTools.StringRequest(Urls.CateList, this.getActivity(), 2,
					null, 2);
		} else {
			volleyTools.StringRequest(Urls.GetCateProducts + cateId,
					this.getActivity(), 2, null, 3);
		}
	}

	// 按返回键处理方法
	public void OnkeydownF() {
		((MainActivity) getActivity()).gotohome();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_bt:
			int[] location = new int[2];
			v.getLocationInWindow(location);
			if (searchGoodAndShopPop == null) {
				searchGoodAndShopPop = new SearchPopwindow(getActivity(), this);
			}
			searchGoodAndShopPop.showAtLocation(v, Gravity.NO_GRAVITY,
					location[0], location[1] + v.getHeight());
			break;
		case R.id.search_name_bt:
			String str = editText.getText().toString();
			Intent intent = new Intent(getActivity(), ProductListActivity.class);
			intent.putExtra("keyword", str);
			intent.putExtra("shopOrgood", FLAG_SHOP_GOOD);
			startActivity(intent);
			break;
		case R.id.goto_ranking_list:
			intent = new Intent(getActivity(), WebActivity.class);
			if (adapter != null && cateData != null) {
				String cateId = cateData.get(adapter.getGroup()).getCateList()
						.get(adapter.getChild()).getCateId();
				intent.putExtra("url", Urls.searchDetailWeb + cateId);
				startActivity(intent);
			}

			break;
		default:
			break;
		}
	}

	@Override
	public void getName(int flag) {
		lastStr = "";
		FLAG_SHOP_GOOD = flag;
		if (flag == 1) {
			search_title_name.setText("商品");
			editText.setHint("药品通用名，商品名");
		} else if (flag == 2) {
			search_title_name.setText("店铺");
			editText.setHint("请输入商铺名");
		}
	}

	@Override
	public void onSelectItem(String cateId) {
		// TODO Auto-generated method stub
		getNetData(cateId);
	}

	@Override
	public void getName(String name) {
		Intent intent = new Intent(getActivity(), ProductListActivity.class);
		intent.putExtra("keyword", name);
		intent.putExtra("shopOrgood", FLAG_SHOP_GOOD);
		startActivity(intent);
	}

}
