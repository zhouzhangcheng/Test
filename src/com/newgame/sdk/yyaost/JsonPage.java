/*
 * 官网地站:http://www.ShareSDK.cn
 * �?术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第�?时间通过微信将版本更新内容推送给您�?�如果使用过程中有任何问题，也可以�?�过微信与我们取得联系，我们将会�?24小时内给予回复）
 *
 * Copyright (c) 2013�? ShareSDK.cn. All rights reserved.
 */
package com.newgame.sdk.yyaost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.newgame.sdk.yyaost.R;

import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.utils.UIHandler;

public class JsonPage extends FakeActivity implements OnClickListener, Callback{
	private HashMap<String, Object> bigData;
	private String title;
	private TitleLayout llTitle;
	private TextView tvJson;

	public void setData(String title, HashMap<String, Object> data) {
		this.title = title;
		bigData = data;
	}

	public void onCreate() {
		activity.setContentView(R.layout.page_show_user_info);
		llTitle = (TitleLayout) activity.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		if (TextUtils.isEmpty(title)) {
			llTitle.getTvTitle().setText(R.string.app_name);
		} else {
			llTitle.getTvTitle().setText(title);
		}

		tvJson = (TextView) activity.findViewById(R.id.tvJson);
		new Thread() {
			public void run() {
				Message msg = new Message();
				msg.obj = format("", bigData);
				bigData = null;
				UIHandler.sendMessage(msg, JsonPage.this);
			}
		}.start();
	}

	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			finish();
		}
	}

	public boolean handleMessage(Message msg) {
		tvJson.setText(msg.obj == null ? "" : (String) msg.obj);
		return false;
	}

	@SuppressWarnings("unchecked")
	private String format(String sepStr, HashMap<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		String mySepStr = sepStr + "\t";
		int i = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (i > 0) {
				sb.append(",\n");
			}
			sb.append(mySepStr).append('\"').append(entry.getKey()).append("\":");
			Object value = entry.getValue();
			if (value instanceof HashMap<?, ?>) {
				sb.append(format(mySepStr, (HashMap<String, Object>)value));
			}
			else if (value instanceof ArrayList<?>) {
				sb.append(format(mySepStr, (ArrayList<Object>)value));
			}
			else if (value instanceof String) {
				sb.append('\"').append(value).append('\"');
			}
			else {
				sb.append(value);
			}
			i++;
		}
		sb.append('\n').append(sepStr).append('}');
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private String format(String sepStr, ArrayList<Object> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("[\n");
		String mySepStr = sepStr + "\t";
		int i = 0;
		for (Object value : list) {
			if (i > 0) {
				sb.append(",\n");
			}
			sb.append(mySepStr);
			if (value instanceof HashMap<?, ?>) {
				sb.append(format(mySepStr, (HashMap<String, Object>)value));
			}
			else if (value instanceof ArrayList<?>) {
				sb.append(format(mySepStr, (ArrayList<Object>)value));
			}
			else if (value instanceof String) {
				sb.append('\"').append(value).append('\"');
			}
			else {
				sb.append(value);
			}
			i++;
		}
		sb.append('\n').append(sepStr).append(']');
		return sb.toString();
	}

}
