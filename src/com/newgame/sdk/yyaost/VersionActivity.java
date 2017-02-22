package com.newgame.sdk.yyaost;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VersionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.method3);
		setTranslucentStatus();
	}

	// 设置系统状�?�栏
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setTranslucentStatus() {
		// 判断版本�?4.4以上
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);

			SystemStatusManager tintManager = new SystemStatusManager(this);
			// 打开系统状�?�栏控制
			tintManager.setStatusBarTintEnabled(true);
			tintManager
					.setStatusBarTintResource(R.drawable.chat_title_bg_repeat);// 设置背景

			View layoutAll = findViewById(R.id.layoutVersion);
			// 设置系统栏需要的内偏�?
			layoutAll.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
		}
	}
}
