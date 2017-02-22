package com.styao.work.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jock.tbshopcar.utils.LoadingUtils;
import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.yyaost.MainActivity;
import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.FragmentPage1.JsInteration;
import com.styao.work.BaseActivity;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

public class WebActivity extends BaseActivity {
	private WebView webview;
	private String url;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_web);
		initView();
	}

	private void initView() {
		url = getIntent().getStringExtra("url");
		// TODO Auto-generated method stub
		webview = (WebView) findViewById(R.id.webViewHome);
		// 设置允许访问文件
		webview.getSettings().setAllowFileAccess(true);
		// 设置与js交互
		webview.getSettings().setJavaScriptEnabled(true);
		// mainwebview.getSettings().setUserAgentString(MyApplication.getUserAgent());
		webview.getSettings().setAllowFileAccess(true);
		webview.getSettings().setDomStorageEnabled(true);
		// webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		String appCacheDir = this.getDir("cache", Context.MODE_PRIVATE)
				.getPath();
		webview.getSettings().setAppCachePath(appCacheDir);
		webview.getSettings().setDatabaseEnabled(true);
		// webview.getSettings().setAppCacheEnabled(true);
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.getSettings().setSavePassword(false);
		// js调用接口
		webview.addJavascriptInterface(this, "Toyun");
		webview.addJavascriptInterface(this, "history");
		webview.loadUrl(url);
		webview.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				LoadingUtils.showLoading(WebActivity.this);
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				syncCookie(view.getContext(), url);
				LoadingUtils.closeLoading(WebActivity.this);
				// if (progressDialog != null && progressDialog.isShowing()) {
				// progressDialog.dismiss();
				// progressDialog = null;
				// }
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				// if (reloadnum == 0) {
				// reloadnum++;
				// webview.loadUrl(failingUrl);
				// } else {
				// reloadnum = 0;
				// webview.loadUrl("file:///android_asset/error.html");
				// // mainwebview.loadUrl("javascript:fromAndroid()");
				// }
			}
		});

	}

	public void searchBack(String str) {
		finish();
	}

	public void go(Object i) {
		if (webview.canGoBack()) {
			webview.goBack();// 返回前一个页面
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
			webview.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void syncCookie(Context context, String url) {
		try {
			CookieSyncManager.createInstance(context);

			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			String oldCookie = cookieManager.getCookie(url);
			MainActivity.oldCookie = oldCookie;
			;
			PushAgent mPushAgent = PushAgent.getInstance(context);
			mPushAgent.setDebugMode(true);
			// 注册推送服务，每次调用register方法都会回调该接口
			mPushAgent.register(new IUmengRegisterCallback() {

				@Override
				public void onSuccess(String deviceToken) {
					Log.d("mytocken", deviceToken);
				}

				@Override
				public void onFailure(String s, String s1) {
					Log.d(s, s1);
				}
			});

			String uid = GetCookieUid(oldCookie);
			if (uid != null && uid != "") {
				mPushAgent.addAlias(uid, "Android", new UTrack.ICallBack() {
					@Override
					public void onMessage(boolean isSuccess, String message) {
						Log.d("addAlias", message);
					}
				});

			}
		} catch (Exception e) {
			Log.e("Nat: webView.syncCookie failed", e.toString());
		}
	}

	// 获取cookie中的uid
	public static String GetCookieUid(String cookie) {
		String uid = "";
		if (cookie.toLowerCase().contains("uid")) {
			String uidstr1 = cookie.split("uid")[1].split("&")[0].split("=")[1];
			if (!uidstr1.contains("-")) {
				uid = uidstr1;
			}
		}
		return uid;
	}

}
