package com.styao.work.view;

import com.newgame.sdk.yyaost.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.AbsListView.LayoutParams;

public class SearchPopwindow extends PopupWindow implements OnClickListener {
	private Context context;
	private SearchPopListener listener;
	
	public SearchPopwindow(Context context,SearchPopListener listener) {
		this.context = context;
		this.listener = listener;
		init();
	}

	private void init() {
		setContentView(View.inflate(context, R.layout.view_search_popwindow,
				null));
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		setOutsideTouchable(true);
		getContentView().findViewById(R.id.search_good_bt).setOnClickListener(
				this);
		getContentView().findViewById(R.id.search_shop_bt).setOnClickListener(
				this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_good_bt:
			if (listener != null) {
				listener.getName(1);
			}
			dismiss();
			break;
		case R.id.search_shop_bt:
			if (listener != null) {
				listener.getName(2);
			}
			dismiss();
			break;
		default:
			break;
		}
	}

	public interface SearchPopListener {
		/**
		 * 
		 * @param flag
		 *            1:…Ã∆∑ 2£∫µÍ∆Ã
		 */
		void getName(int flag);
	}
}
