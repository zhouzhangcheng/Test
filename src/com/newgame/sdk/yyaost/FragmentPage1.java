package com.newgame.sdk.yyaost;

import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.yyaost.MainActivity.OnMyOnClickListener;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FragmentPage1 extends Fragment {
	private WebView webview;
	private Context context;
	private View view;
	public static int reloadnum = 0;
	private CustomProgressDialog progressDialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_1,
				null);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setView(view);
		setListener();
		super.onActivityCreated(savedInstanceState);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.barUrl);
		webview.loadUrl(url);

		webview.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog.createDialog(view
							.getContext());
					progressDialog.setMessage(getResources().getString(
							R.string.data_load));
					progressDialog.show();
				} else {
					progressDialog.setMessage(getResources().getString(
							R.string.data_load));
					progressDialog.show();
				}
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				syncCookie(view.getContext(), url);
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				if (reloadnum == 0) {
					reloadnum++;
					webview.loadUrl(failingUrl);
				} else {
					reloadnum = 0;
					// 这里进行无网络或错误处理，具体可以根据errorCode的�?�进行判断，做跟详细的处�?
					webview.loadUrl("file:///android_asset/error.html");
					// mainwebview.loadUrl("javascript:fromAndroid()");
				}
			}
		});

	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		webview = (WebView) getView().findViewById(R.id.webViewHome);
		// 设置允许访问文件
		webview.getSettings().setAllowFileAccess(true);
		// 设置与js交互
		webview.getSettings().setJavaScriptEnabled(true);
		// mainwebview.getSettings().setUserAgentString(MyApplication.getUserAgent());
		webview.getSettings().setAllowFileAccess(true);
		webview.getSettings().setDomStorageEnabled(true);
		// webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		String appCacheDir = view.getContext()
				.getDir("cache", Context.MODE_PRIVATE).getPath();
		webview.getSettings().setAppCachePath(appCacheDir);
		webview.getSettings().setDatabaseEnabled(true);
		// webview.getSettings().setAppCacheEnabled(true);
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.getSettings().setSavePassword(false);
		webview.addJavascriptInterface(new JsInteration(), "control");
		// js调用接口
		webview.addJavascriptInterface(this, "Toyun");
	}

	public class JsInteration {

		@JavascriptInterface
		public void toastMessage(String message) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}

		@JavascriptInterface
		public void onSumResult(int result) {
			Log.i("langrenchengxu", "onSumResult result=" + result);
		}
	}

	// 按返回键处理方法
	public void OnkeydownF() {
		String nowUrl = webview.getUrl();// 获取当前网址
	}

	// 按返回键处理方法
	public void updateVersionInfo(String str) {

	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void callCamera(String str) {
		((MainActivity) getActivity()).callCamera(str);
	}

	/**
	 * js调用原生方法 购物车跳转到商品详情�? parm:商品id
	 * 
	 * @param str
	 */
	public void cartGotoProductBuy(String pid) {
		((MainActivity) getActivity()).cartGotoProductBuy(pid);
	}

	/**
	 * js调用原生方法 统一调用接口
	 * 
	 * @param str
	 */
	public void JsToLoacleFunction(String Parameters) {

		((MainActivity) getActivity()).JsToLoacleFunction(Parameters);
	}

	public void JsToAdroidUrl(String url) {
		
		((MainActivity) getActivity()).JsToAdroidUrl(url);
	}

	public void JsToAndroidProdRank(String cateid) {
		((MainActivity) getActivity()).JsToAndroidProdRank(cateid);
	}

	public void IndexToProductDetail(String goodsPakageId) {
		((MainActivity) getActivity()).IndexToProductDetail(goodsPakageId);
	}

	public void CateSearch(String strurl) {
		((MainActivity) getActivity()).CateSearch(strurl);
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

	public void syncCookie(Context context, String url) {
		try {
			CookieSyncManager.createInstance(context);

			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			String oldCookie = cookieManager.getCookie(url);
			((MainActivity) getActivity()).GetCookie(oldCookie);
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
}
