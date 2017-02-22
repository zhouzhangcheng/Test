package com.newgame.sdk.yyaost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.cookie.BasicClientCookie;
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
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
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

public class FragmentPage4 extends Fragment {
	private WebView webview;
	private Context context;
	private View view;
	public static int reloadnum = 0;
	LocalBroadcastManager broadcastManager;
	private CustomProgressDialog progressDialog = null;

	private String bundleurl = "";

	PayReq req;
	IWXAPI msgApi = null;// = WXAPIFactory.createWXAPI(context, null);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_4,
				null);
		Bundle bundle = getArguments();// 从activity传过来的Bundle
		if (bundle != null) {
			bundleurl = bundle.getString("paygotomine");
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setView(view);
		setListener();
		context = view.getContext();
		// msgApi = WXAPIFactory.createWXAPI(view.getContext(), null);
		// req = new PayReq();
		// IntentFilter filter = new IntentFilter(WXPayEntryActivity.action);
		// broadcastManager = LocalBroadcastManager.getInstance(getActivity());
		// broadcastManager.registerReceiver(broadcastReceiver, filter);
		super.onActivityCreated(savedInstanceState);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.barUrl);
		url = url + "/ucenter/userinfo";
		if (bundleurl.length() > 1) {
			url = bundleurl;
		}

		webview.loadUrl(url);

		webview.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				if (url.toLowerCase().contains("ucenter/orderlist")) {
					setCookie(url);
				}
				if (url.toLowerCase().contains("login")) {
					((MainActivity) getActivity()).GetCookie("");
					setCookie(url);
				}
				if (url.toLowerCase().contains("/account/logout")) {
					PushAgent mPushAgent = PushAgent.getInstance(context);
					String oldCookie = Getcookie();
					 String uid=GetCookieUid(oldCookie);
					 if(uid!=null&&uid!="")
					 {
							mPushAgent.removeAlias(uid,"Android", new UTrack.ICallBack(){
							    @Override
							    public void onMessage(boolean isSuccess, String message) {
							    	if(isSuccess)
							    	{
							    		 Log.d("消息推送移除","成功");
							    	}
							    	else
							    	{
							   		 Log.d("消息推送移除", "失败");
							    	}
							    	
							    	
							    }
							});
						 
					 }
					((MainActivity) getActivity()).GetCookie("");
					setCookie1(url);
					// webview.loadUrl(url);
				}
				if (url.toLowerCase().contains("ucenter/userinfo")) {
					syncCookie(view.getContext(), url);
				}
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
				// view.loadUrl(url);
				// final Activity context = context;

				// ------ 对alipays:相关的scheme处理 -------
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
				if (url.startsWith("tel:")) {// 电话监听
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					startActivity(intent);
				} else if (url.startsWith("http:") || url.startsWith("https:")) {
					view.loadUrl(url);
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);

				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				// if (reloadnum <10) {
				// reloadnum++;
				// webview.loadUrl(failingUrl);
				// } else {
				// reloadnum = 0;
				// // 这里进行无网络或错误处理，具体可以根据errorCode的�?�进行判断，做跟详细的处�?
				// webview.loadUrl("file:///android_asset/error.html");
				// // mainwebview.loadUrl("javascript:fromAndroid()");
				// }
			}
		});

	}

	public void LogOut() {
		((MainActivity) getActivity()).GetCookie("");
		String url = getResources().getString(R.string.barUrl);
		url = url + "/ucenter/userinfo";
		setCookie(url);
		webview.loadUrl(url);
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		webview = (WebView) getView().findViewById(R.id.webViewUcenter);
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

	public void reload() {
		webview.reload();
	}

	public void reload(String url) {
		webview.loadUrl(url);
	}

	public void ConfirmRevieve(String oid) {
		((MainActivity) getActivity()).ConfirmRevieve(oid);
	}

	public void GoSt30() {
		((MainActivity) getActivity()).GoSt30first(true);
		((MainActivity) getActivity()).GoSt30();
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
		} else if (nowUrl.toLowerCase().contains("ordervoucher")) {
			webview.loadUrl("javascript:android()");// Android调用js方法
		} else if (nowUrl.toLowerCase().contains("shipinfo")) {
			String url = getResources().getString(R.string.barUrl);
			if (nowUrl.toLowerCase().contains("from")) {
				webview.loadUrl(url + "/ucenter/OrderWaitReceiptList");
			} else {
				String[] oidstr = nowUrl.split("&");
				String oid = "";
				for (int i = 0; i < oidstr.length; i++) {
					if (oidstr[i].toLowerCase().contains("oid")) {
						oid = oidstr[i];
					}
				}
				webview.loadUrl(url + "/OrderComfirm/index?" + oid);
			}
		} else if (nowUrl.toLowerCase().contains("orderlist")
				|| nowUrl.toLowerCase().contains("ordernopay")
				|| nowUrl.toLowerCase().contains("orderwaitreceiptlist")
				|| nowUrl.toLowerCase().contains("hotorderlist")
				|| nowUrl.toLowerCase().contains("settinguserinfo")) {
			String url = getResources().getString(R.string.barUrl);
			url = url + "/ucenter/userinfo";
			webview.loadUrl(url);
		} else if (nowUrl.toLowerCase().contains("address")
				&& nowUrl.toLowerCase().contains("mystsetting")) {// 收货地址列表，返回我的页�?
			String url = getResources().getString(R.string.barUrl);

		} else if (nowUrl.toLowerCase().contains("alipay")
				|| !nowUrl.toLowerCase().contains("ucenter/userinfo")) {
			String url = getResources().getString(R.string.barUrl);
			url = url + "/ucenter/orderlist";
			webview.loadUrl(url);
		} else {
			((MainActivity) getActivity()).gotohome();
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

	public void searchBack(String str) {
		((MainActivity) getActivity()).searchBack(str);
	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void CateSearch(String strurl) {
		((MainActivity) getActivity()).CateSearch(strurl);
	}

	public void syncCookie(Context context, String url) {
		try {
			CookieSyncManager.createInstance(context);

			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			String oldCookie = cookieManager.getCookie(url);
			PushAgent mPushAgent = PushAgent.getInstance(context);
			mPushAgent.setDebugMode(true);
			//注册推送服务，每次调用register方法都会回调该接口
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
			
			 String uid=GetCookieUid(oldCookie);
			 if(uid!=null&&uid!="")
			 {
					mPushAgent.addAlias(uid,"Android", new UTrack.ICallBack() {
					    @Override
					    public void onMessage(boolean isSuccess, String message) {
					    	 Log.d("addAlias", message);
					    }
					}); 
				 
			 }
			((MainActivity) getActivity()).GetCookie(oldCookie);
			;
		} catch (Exception e) {
			Log.e("Nat: webView.syncCookie failed", e.toString());
		}
	}
	//获取cookie中的uid
		public static String GetCookieUid(String cookie)
		{
			String uid="";
			if(cookie.toLowerCase().contains("uid"))
			{
				String uidstr1=cookie.split("uid")[1].split("&")[0].split("=")[1];
				if(!uidstr1.contains("-"))
				{
					uid=uidstr1;
				}
			}
			return uid;
		}

	public void CheckUpgrade() {
		((MainActivity) getActivity()).CheckUpgrade();
	}

	public void ShareAPP() {
		((MainActivity) getActivity()).ShareAPP();
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
			Log.v("back", str);
			String url = getResources().getString(R.string.barUrl);
			if ("1".equals(str)) {
				webview.loadUrl(url + "/ucenter/orderlist");
			} else {
				webview.loadUrl(url + "/ucenter/orderlist");
			}
		}
	};
//	@Override
//	public void onDestroy() {
//		if (broadcastReceiver != null) {
//			try {
//				broadcastManager.unregisterReceiver(broadcastReceiver);
//			} catch (Exception ex) {
//				try {
//					throw ex;
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	};

	public String Getcookie() {
		return ((MainActivity) getActivity()).GetCookiesFrammat();
	}

	/* 让webviewloadurl的时候带上cookie信息 */
	private void setCookie(String url) {
		CookieSyncManager.createInstance(context);

		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);

		String oldCookie = Getcookie();
		if (oldCookie != null) {
			Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
		}

		StringBuilder sbCookie = new StringBuilder();
		String networktype = GetNetworkType(context);
		sbCookie.append(String.format(";networktype=%s", networktype));
		String cookieValue = oldCookie + sbCookie.toString();
		// cookieManager.removeAllCookie();
		cookieManager.setCookie(url, cookieValue);
		CookieSyncManager.getInstance().sync();
	}

	private void setCookie1(String url) {
		CookieSyncManager.createInstance(context);

		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);

		String oldCookie = Getcookie();
		if (oldCookie != null) {
			Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
		}

		StringBuilder sbCookie = new StringBuilder();
		String networktype = GetNetworkType(context);
		sbCookie.append(String.format(";networktype=%s", networktype));
		String cookieValue = oldCookie + sbCookie.toString();
		cookieManager.removeAllCookie();
		cookieManager.setCookie(url, cookieValue);
		CookieSyncManager.getInstance().sync();
	}

	// 获取手机当前网络类型
	public static String GetNetworkType(Context context) {
		String strNetworkType = "";
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				strNetworkType = "WIFI";
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				String _strSubTypeName = networkInfo.getSubtypeName();

				Log.e("cocos2d-x", "Network getSubtypeName : "
						+ _strSubTypeName);

				// TD-SCDMA networkType is 17
				int networkType = networkInfo.getSubtype();
				switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
															// 11
					strNetworkType = "2G";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
															// 14
				case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
															// 12
				case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
															// 15
					strNetworkType = "3G";
					break;
				case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
														// 13
					strNetworkType = "4G";
					break;
				default:
					// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
					if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
							|| _strSubTypeName.equalsIgnoreCase("WCDMA")
							|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
						strNetworkType = "3G";
					} else {
						strNetworkType = _strSubTypeName;
					}

					break;
				}

				Log.e("cocos2d-x",
						"Network getSubtype : "
								+ Integer.valueOf(networkType).toString());
			}
		}

		Log.e("cocos2d-x", "Network Type : " + strNetworkType);

		return strNetworkType;
	}

}
