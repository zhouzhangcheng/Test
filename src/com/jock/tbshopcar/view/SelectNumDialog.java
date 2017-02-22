package com.jock.tbshopcar.view;

import java.util.List;

import com.jock.tbshopcar.entity.WisdomBean;
import com.newgame.sdk.yyaost.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SelectNumDialog extends PopupWindow implements OnItemClickListener {
	private Context context;
	private GridView gridView;
	private List<String> list;
	private OnClickListener listener;

	public SelectNumDialog(Context context,WisdomBean.FirstZiMu.Goods good,OnClickListener listener) {
		this.context = context;
		this.listener = listener;
		list = good.getBuyNumList();
		init();
	}

	private void init() {
		setContentView(View.inflate(context, R.layout.view_selectnum_dialog,
				null));
		gridView = (GridView) getContentView().findViewById(R.id.gridview);
		gridView.setAdapter(new SelectNumAdapter(list));
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		setWidth(300);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		setOutsideTouchable(true);
		gridView.setOnItemClickListener(this);
	}

	public void show(View v, int[] location) {
		showAtLocation(v, Gravity.NO_GRAVITY, location[0] - 220, location[1]
				+ v.getHeight());
	}

	private class SelectNumAdapter extends BaseAdapter {
		private List<String> list;

		public SelectNumAdapter(List<String> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list != null ? list.size() : 0;
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
			// TODO Auto-generated method stub
			View view = View
					.inflate(context, R.layout.item_select_num_tv, null);
			TextView tx = (TextView) view.findViewById(R.id.textView);
			tx.setText(list.get(arg0));
			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (listener != null) {
			listener.getNum(list.get(arg2));
			this.dismiss();
		}
	}
	
	public interface OnClickListener{
		void getNum(String num);
	}
}
