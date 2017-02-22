package com.newgame.sdk.yyaost;

import org.json.JSONException;
import org.json.JSONObject;

import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.yyaost.FragmentPage1.JsInteration;
import com.newgame.sdk.yyaost.MainActivity.OnMyOnClickListener;
import com.newgame.sdk.yyaost.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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

public class FragmentPage5 extends Fragment {
	private WebView webview;
	private Context context;
	private View view;
	public static int reloadnum = 0;
	PayReq req;
	IWXAPI msgApi = null;
	LocalBroadcastManager broadcastManager;
	private CustomProgressDialog progressDialog = null;
	private String pid303 = "";
	private String SchemeName = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_5,
				null);
		Bundle bundle = getArguments();// 从activity传过来的Bundle
		if (bundle != null) {
			pid303 = bundle.getString("submitoder303").split("&")[0];
			SchemeName = bundle.getString("submitoder303").split("&")[1];
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setView(view);
		setListener();
		setView(view);
		setListener();
		context = view.getContext();
		msgApi = WXAPIFactory.createWXAPI(view.getContext(), null);
		req = new PayReq();
		IntentFilter filter = new IntentFilter(WXPayEntryActivity.action);
		broadcastManager = LocalBroadcastManager.getInstance(getActivity());
		broadcastManager.registerReceiver(broadcastReceiver, filter);
		// AndroidBug5497Workaround.assistActivity(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.barUrl);
		if (pid303.length() > 1) {
			url = url + "/webapi/SubmitToConfirmorder?selectedcart=" + pid303;
		} else {
			url = url + "/order/confirmorder?selectedcartitemkeylist=";
			url += ((MainActivity) getActivity()).GetProducts();
		}
		webview.loadUrl(url);

		webview.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				if (url.toLowerCase().contains("ucenter")) {
					((MainActivity) getActivity()).PayGotoMine(url);
				} else {
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

			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("alipays:") || url.startsWith("alipay")) {
					try {
						context.startActivity(new Intent(
								"android.intent.action.VIEW", Uri.parse(url)));
					} catch (Exception e) {
						new AlertDialog.Builder(context)
								.setMessage("未检测到支付宝客户端，请安装后重试。")
								.setPositiveButton("立即安装",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												Uri alipayUrl = Uri
														.parse("https://d.alipay.com");
												context.startActivity(new Intent(
														"android.intent.action.VIEW",
														alipayUrl));
											}
										}).setNegativeButton("取消", null).show();
					}
					return true;
				}
				if (url.toLowerCase().contains("ucenter")) {
					((MainActivity) getActivity()).PayGotoMine(url);
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				if (url.toLowerCase().contains("confirmorder")) {
					syncCookie(view.getContext(), url);
				}
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
		webview = (WebView) getView().findViewById(R.id.submitOrder);
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
		// String[] urlSpit = nowUrl.split(sturl + "/");
		if (nowUrl.toLowerCase().contains("mycollections")
				|| nowUrl.toLowerCase().contains("certificateusercenter")
				|| nowUrl.toLowerCase().contains("mycoupons")
				|| nowUrl.toLowerCase().contains("mycontrols")
				|| nowUrl.toLowerCase().contains("walletinfo")
				|| nowUrl.toLowerCase().contains("mycollections")
				|| nowUrl.toLowerCase().contains("ordervoucherlistwait")
				|| nowUrl.toLowerCase().contains("ordervoucherlist")) {
			webview.loadUrl("javascript:OnKeyDownBack()");// Android调用js方法
		} else if (!nowUrl.toLowerCase().contains("order/")) {
			String url = getResources().getString(R.string.barUrl);
			url = url + "/ucenter/orderlist";
			webview.loadUrl(url);
		} else {
			if (pid303.length() > 1) {
				((MainActivity) getActivity()).GoSt303(SchemeName);
			} else {
				((MainActivity) getActivity()).Gotobuy();
			}
		}
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
		if (Parameters.contains("buytocart")&&pid303.length() > 1) {
			((MainActivity) getActivity()).GoSt303(SchemeName);
		} else {
			((MainActivity) getActivity()).JsToLoacleFunction(Parameters);
		}
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

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void CateSearch(String strurl) {
		((MainActivity) getActivity()).CateSearch(strurl);
	}

	public void getcoupons(String SelectedCartItemKeyList,
			String selectedCouponItemKeyList) {
		((MainActivity) getActivity()).getcoupons(SelectedCartItemKeyList,
				selectedCouponItemKeyList);
	}

	public void GetCouponsList(String CouponsList) {
		webview.loadUrl("javascript:GetCouponsList('" + CouponsList + "')");// Android调用js方法
	}

	public void syncCookie(Context context, String url) {
		try {
			CookieSyncManager.createInstance(context);

			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			String oldCookie = cookieManager.getCookie(url);
			((MainActivity) getActivity()).GetCookie(oldCookie);
			;
		} catch (Exception e) {
			Log.e("Nat: webView.syncCookie failed", e.toString());
		}
	}

	public void wxPay(String json) {
		((MainActivity) getActivity()).wxPay(json);
	}

	// public void onResp(BaseResp resp) {
	// ((MainActivity) getActivity()).onResp(resp);
	// }
	// 测试微信支付接口
	// public void wxPay(String json) {
	// // JSONTokener jsonParser=new JSONTokener(json);
	// try {
	// JSONObject jsons = new JSONObject(json);
	// req.appId = jsons.getString("appid");
	// req.partnerId = jsons.getString("partnerid");
	// req.prepayId = jsons.getString("prepayid");
	// req.packageValue = jsons.getString("package");
	// req.nonceStr = jsons.getString("noncestr");
	// req.timeStamp = jsons.getString("timestamp");
	// req.sign = jsons.getString("sign");
	// } catch (JSONException ex) {
	// // json数据解析异常
	// }
	// sendPayReq();
	// }

	private void sendPayReq() {
		msgApi.registerApp(req.appId);
		boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
				&& msgApi.isWXAppSupportAPI();
		if (sIsWXAppInstalledAndSupported) {
			msgApi.sendReq(req);
		} else {
			new AlertDialog.Builder(context)
					.setTitle("系统提示")
					// 设置对话框标题

					.setMessage("请确认手机已经安装微信客户端！")
					// 设置显示的内容

					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 确定按钮的响应事件

									// TODO Auto-generated method stub

									// finish();

								}

							})
					.setNegativeButton("返回",
							new DialogInterface.OnClickListener() {// 添加返回按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 响应事件

									// TODO Auto-generated method stub

									// Log.i("alertdialog"," 请保存数据！");

								}

							}).show();// 在按键响应事件中显示此对话框
		}
	}

	public void onResp(BaseResp resp) {
		// Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("");
			builder.setMessage(getString(R.string.pay_result_callback_msg,
					resp.errStr + ";code=" + String.valueOf(resp.errCode)));
			builder.show();
		}
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String str = intent.getExtras().getString("data");
			// if (mainwebview == null) {
			// mainwebview = (WebView) findViewById(R.id.webView);
			// }
			String url = getResources().getString(R.string.barUrl);
			if ("1".equals(str)) {
				// webview.loadUrl(url + "/ucenter/orderlist");
				((MainActivity) getActivity()).PayGotoMine(url
						+ "/ucenter/orderlist");
			} else {
				// webview.loadUrl(url + "/ucenter/orderlist");
				((MainActivity) getActivity()).PayGotoMine(url
						+ "/ucenter/orderlist");
			}
		}
	};

//	public void onDestroy() {
//		broadcastManager.unregisterReceiver(broadcastReceiver);
//	};
}
