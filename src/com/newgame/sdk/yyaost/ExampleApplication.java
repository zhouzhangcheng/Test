package com.newgame.sdk.yyaost;

import com.jock.tbshopcar.utils.ImageLoaderUtil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class ExampleApplication extends Application {
	private static final String TAG = "JPush";
	private static Context context;

	@Override
	public void onCreate() {
		Log.d(TAG, "[ExampleApplication] onCreate");
		super.onCreate();
//		JPushInterface.setDebugMode(true);
//		JPushInterface.init(this);
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
