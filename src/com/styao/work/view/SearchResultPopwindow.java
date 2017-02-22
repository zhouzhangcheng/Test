package com.styao.work.view;

import java.util.ArrayList;
import java.util.List;

import com.newgame.sdk.yyaost.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

public class SearchResultPopwindow extends PopupWindow implements
		OnItemClickListener {
	private Context context;
	private SearchResultListener listener;
	private int width;
	private ListView listView;
	private List<String> datalist;

	public SearchResultPopwindow(Context context,
			SearchResultListener listener, int width) {
		this.context = context;
		this.listener = listener;
		this.width = width;
		datalist = new ArrayList<String>();
		init();
	}

	private void init() {
		setContentView(View.inflate(context,
				R.layout.view_search_result_popwindow, null));
		setWidth(width);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		setOutsideTouchable(true);
		listView = (ListView) getContentView().findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		listView.setAdapter(new SearchResultAdapter());
	}

	public void show(List<String> data, View view, int x, int y) {
		datalist.clear();
		datalist.addAll(data);
		if (data.size() > 5) {
			setHeight(500);
		} else {
			setHeight(LayoutParams.WRAP_CONTENT);
		}
		if (isShowing()) {
			dismiss();
		}
		showAtLocation(view, Gravity.NO_GRAVITY, x, y);
	}

	public interface SearchResultListener {
		/**
		 * 
		 * @param flag
		 *            1:…Ã∆∑ 2£∫µÍ∆Ã
		 */
		void getName(String name);
	}

	private class SearchResultAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return datalist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = new TextView(context);
				LayoutParams params = new LayoutParams(-1, 100);
				arg1.setLayoutParams(params);
				arg1.setPadding(15, 0, 0, 0);
				((TextView) arg1).setGravity(Gravity.CENTER_VERTICAL);
				((TextView) arg1).setTextColor(Color.parseColor("#333333"));
				((TextView) arg1).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
				arg1.setBackgroundColor(Color.WHITE);
			}
			((TextView) arg1).setText(datalist.get(arg0));
			return arg1;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.getName(datalist.get(arg2));
			dismiss();
		}
	}
}
