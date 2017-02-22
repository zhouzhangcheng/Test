package com.newgame.sdk.yyaost;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.newgame.sdk.yyaost.R;
import com.styao.work.activity.ShopClassActivity;
import com.styao.work.activity.ShopHomeActivity;
import com.zxing.activity.CaptureActivity;
import com.lxd.widgets.CustomProgressDialog;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SearchActitity extends Activity {
	private WebView webview;
	// private SwipeRefreshLayout swipeLayout;
	private CustomProgressDialog progressDialog = null;
	private HandlerTest5 mHandlerTest5;
	private HandlerGoToMone mHandlerGoToMone;
	private HandlerGoToBalance mHandlerGoToBalance;
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 2;
	private static final int SCALE = 1;// 图片压缩比例
	public static String oid = "";// 订单id
	public static String productid = "";// 商品id
	private returnurl Returnurl;
	private Context mContext;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 默认软键盘不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// 软键盘遮�?
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// 网址
		String url = getResources().getString(R.string.barUrl);
		// 获取上一activity传入的�??
		Intent intent = getIntent();
		String value = intent.getStringExtra("fromcategories");
		String FromUrl = intent.getStringExtra("url");
		String ProdRankCateID = intent.getStringExtra("ProdRank");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		// AndroidBug5497Workaround.assistActivity(this);//解决软键盘覆盖输入框的问�?
		// 设置状�?�栏颜色
		setTranslucentStatus();

		webview = (WebView) findViewById(R.id.wv);
		webview.getSettings().setJavaScriptEnabled(true);
		// 设置允许访问文件
		webview.getSettings().setAllowFileAccess(true);
		// 设置与js交互
		webview.getSettings().setJavaScriptEnabled(true);
		// mainwebview.getSettings().setUserAgentString(MyApplication.getUserAgent());
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.getSettings().setAllowFileAccess(true);
		webview.getSettings().setAppCacheEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		String appCacheDir = this.getApplicationContext()
				.getDir("cache", Context.MODE_PRIVATE).getPath();
		webview.getSettings().setAppCachePath(appCacheDir);
		webview.getSettings().setDatabaseEnabled(true);
		webview.addJavascriptInterface(new JsInteration(), "control");
		// js调用接口
		webview.addJavascriptInterface(this, "Toyun");
		webview.setWebChromeClient(new WebChromeClient() {
			// 扩充缓存的容量
			@Override
			public void onReachedMaxAppCacheSize(long spaceNeeded,
					long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
				quotaUpdater.updateQuota(spaceNeeded * 2);
			}
		});
		webview.setWebViewClient(new myWebClient());
		// 传入参数
		if (value != null && value.length() > 0) {
			String[] valuestr = value.split(",");
			if (valuestr[0].equals("keyWords")) {// 分类搜索进入
				url = url + "/Search/SearchDetail?keyWords="
						+ valuestr[1].split("@")[0] + "&fromcategories=1";
			} else if (valuestr[0].equals("storekeyWords")) {
				url = url + "/Store/StoreSearchResult?keyWords=" + valuestr[1];
			} else if (valuestr[0].equals("Tag_PharmAttribute_fullPath"))// 分类适应症进�?
			{
				url = url + "/Search/SearchDetail?Tag_PharmAttribute_fullPath="
						+ valuestr[1] + "&fromcategories=1";
			} else if (valuestr[0].equals("cartToProductbuy"))// 购物车点击商品进入
			{
				url = url + "/product/ProductBuy?pid=" + valuestr[1]
						+ "&fromcategories=2";// 表示来自购物车
			} else if (valuestr[0].equals("oid"))// 确认收货入口
			{
				url = url + "/OrderComfirm?oid=" + valuestr[1];
			} else if (valuestr[0].equals("SelectedCartItemKeyList"))// 选择使用的优惠券
			{
				// SelectedCartItemKeyList已选购物车商品参数
				url = url + "/Coupons/GetValidCouponList"
						+ value.replace("SelectedCartItemKeyList,", "");

			} else if (valuestr[0].equals("goodsPakageId")) {
				url = url + "/product/productdetail?goodsPackageId="
						+ valuestr[1] + "&fromcategories=3";// 表示来自首页
			} else if (valuestr[0].equals("scanResult")) {
				url = url + "/barcode?barnum=" + valuestr[1];
			} else if (valuestr[0].equals("mycollections")) {
				url = url + "/Store?storeid=" + valuestr[1] + "&from=ucenter";
			}
		} else {
			if (FromUrl != null && FromUrl.length() > 0) {
				url = url + FromUrl;
			} else if (ProdRankCateID != null && ProdRankCateID.length() > 0) {
				url = url + "/Search/SUI_prod_rank?cateid=" + ProdRankCateID
						+ "&iskong=1&nearest=0";
			} else {
				url = url + "/Search";
			}
		}
		webview.loadUrl(url);
		// 下拉刷新页面
		// swipeLayout = (SwipeRefreshLayout)
		// findViewById(R.id.swipe_container);
		// swipeLayout
		// .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// // 重新刷新页面
		// webview.loadUrl(webview.getUrl());
		// }
		// });
		// swipeLayout.setColorScheme(R.color.holo_blue_bright,
		// R.color.holo_green_light, R.color.holo_orange_light,
		// R.color.holo_red_light);
		// swipeLayout.setRefreshing(false);

		// 初始化分�?
		ShareSDK.initSDK(this);
		ShareSDK.registerPlatform(Laiwang.class);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			String nowUrl = webview.getUrl();// 获取当前网址
			if (nowUrl.toLowerCase().contains("store")) {
				webview.loadUrl("javascript:OnKeyDownBack()");// Android调用js方法
			} else if (nowUrl.toLowerCase().contains("fromcategories=3")) {
				finish();
			} else if (nowUrl.toLowerCase().contains("ordercomfirm")) {
				Intent intent = new Intent();
				setResult(101, intent);
				finish();
			} else if (nowUrl.toLowerCase().contains("productbuy")
					|| nowUrl.toLowerCase().contains("productcommentlist")
					|| nowUrl.toLowerCase().contains("productdisp")) {
				webview.loadUrl("javascript:fromAndroid()");
			} else if (nowUrl.toLowerCase().contains("productdetail")
					|| nowUrl.toLowerCase().contains("productdetailtab")) {
				if (nowUrl.toLowerCase().contains("returnurl")) {
					String[] str = nowUrl.toLowerCase().split("returnurl=");
					webview.loadUrl("javascript:fromAndroid('" + str[1] + "')");
				} else {
					webview.loadUrl("javascript:fromAndroid()");
				}
			} else if (nowUrl.toLowerCase().contains("shipinfo")) {
				String url = getResources().getString(R.string.barUrl);
				if (nowUrl.toLowerCase().contains("from")) {
					webview.loadUrl(url + "/ucenter/OrderWaitReceiptList");
				} else {
					String[] oidstr = nowUrl.split("oid");
					String oid = "";

					oid = oidstr[1];

					webview.loadUrl(url + "/OrderComfirm/index?oid" + oid);
				}
			} else if (nowUrl.toLowerCase().contains("fromprod_rank")) {
				webview.loadUrl("javascript:fromAndroid()");
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// 结束ShareSDK的统计功能并释放资源
		ShareSDK.stopSDK(this);
		super.onDestroy();
	};

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void searchBack(String str) {
		String nowurl = webview.getUrl();
		if (nowurl.toLowerCase().contains("ordercomfirm")) {
			Intent intent = new Intent();
			setResult(101, intent);
		} else if (str.toLowerCase().contains("buytocart")) {
			Intent intent = new Intent();
			setResult(103, intent);// 跳转到购物车
		} else if (str.toLowerCase().contains("buytomycontrol")) {
			Intent intent = new Intent();
			setResult(104, intent);// go to mycontrol
		}
		finish();
	}

	/**
	 * js调用原生方法 上传图片
	 * 
	 * @param str
	 */
	public void androidUploadFile(String confirmoid, String confirmproductid) {
		oid = confirmoid;
		productid = confirmproductid;
		// 选择上传的图�?
		new AlertDialog.Builder(SearchActitity.this)
				.setTitle("图片选项")
				.setItems(new String[] { "手机拍照", "手机相册", "取消" },
						new OnMyOnClickListener()).show();
	}

	/**
	 * js调用原生方法 分享app
	 */
	public void ShareProduct(String productUrl) {
		// 收到消息后可再发消息到主线程
		mHandlerTest5 = new HandlerTest5(getMainLooper());
		Message message = new Message();
		message.obj = productUrl;
		mHandlerTest5.sendMessage(message);
	}

	/**
	 * js调用原生方法 返回优惠券列�?
	 */
	public void returnCouponList(String CouponList) {
		mHandlerGoToBalance = new HandlerGoToBalance(getMainLooper());
		Message message = new Message();
		message.obj = CouponList;
		mHandlerGoToBalance.sendMessage(message);
	}

	/**
	 * js调用原生方法 统一调用接口
	 * 
	 * @param str
	 */
	public void JsToLoacleFunction(String Parameters) {
		if (Parameters.toLowerCase().contains("scan")) {
			Intent intent = new Intent(SearchActitity.this,
					CaptureActivity.class);
			startActivityForResult(intent, 0);
		}
		// Intent intent = new Intent();
		// intent.putExtra("fromcategories", "goodsPakageId," + goodsPakageId);
		// intent.setClass(MainActivity.this, SearchActitity.class);
		//
		// startActivityForResult(intent, 10);
	}

	public class JsInteration {

		@JavascriptInterface
		public void toastMessage(String message) {
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
					.show();
		}

		@JavascriptInterface
		public void onSumResult(int result) {
			Log.i("langrenchengxu", "onSumResult result=" + result);
		}
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

			View layoutAll = findViewById(R.id.layoutSearch);
			// 设置系统栏需要的内偏�?
			layoutAll.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
		}
	}

	/**
	 * 处理页面的加�? onPageStarted页面的加载过�? onPageFinished页面加载完成
	 * shouldOverrideUrlLoading对网页中超链接按钮的响应，当按下某个连接时WebViewClient会调用这个方法，并传递参�?
	 */
	public class myWebClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub

			if (progressDialog == null) {
				progressDialog = CustomProgressDialog
						.createDialog(SearchActitity.this);
				progressDialog.setMessage(getResources().getString(
						R.string.data_load));
				try {
					progressDialog.show();
				} catch (Exception ex) {
					//
				}
				webview.setEnabled(false);// 当加载网页时将网页进行隐�?
			} else {
				progressDialog.setMessage(getResources().getString(
						R.string.data_load));
				progressDialog.show();
				webview.setEnabled(false);// 当加载网页的时�?�，将网页进行隐�?
			}
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			if (url.startsWith("mqqwpa")) {
				webview.stopLoading();
				Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
			} else if (url.startsWith("tel:")) {
				webview.stopLoading();
				Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
			} else if (url.contains("store?storeid=")) {
				webview.stopLoading();
				Intent in = new Intent(SearchActitity.this, ShopHomeActivity.class);
				String[] str = url.split("storeid=",2);
				String[] strs = str[1].split("&");
				String storeid = strs[0];
				in.putExtra("storeId", Integer.valueOf(storeid));
				startActivity(in);
			} else {
				view.loadUrl(url);
			}
			return true;

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			// swipeLayout.setRefreshing(false);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
				webview.setEnabled(true);
			}
			super.onPageFinished(view, url);
		}

	}

	/**
	 * 分享主线�?(分享app)
	 * 
	 * @author Administrator
	 * 
	 */
	private class HandlerTest5 extends Handler {

		private HandlerTest5(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			// AndroidShare as = new AndroidShare(SearchActitity.this, str,
			// str);
			// as.show();
			showShare(false, str);
		}
	}

	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc�?
	/**
	 * ShareSDK集成方法有两�?</br>
	 * 1、第�?种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs�?</br>
	 * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br> 请看“ShareSDK
	 * 使用说明文档”，SDK下载目录�? </br> 或�?�看网络集成文档
	 * http://wiki.sharesdk.cn/Android_%E5%BF%AB
	 * %E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
	 * 3、混淆时，把sample或�?�本例子的混淆代码copy过去，在proguard-project.txt文件�?
	 * 
	 * 
	 * 平台配置信息有三种方式： 1、在我们后台配置各个微博平台的key
	 * 2、在代码中配置各个微博平台的key，http://sharesdk.cn/androidDoc
	 * /cn/sharesdk/framework/ShareSDK.html
	 * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
	 */
	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		String[] shareStr = platform.split(",");
		String url = getResources().getString(R.string.barUrl);
		oks.setNotification(R.drawable.ic_launcher,
				getBaseContext().getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setTitle(shareStr[2]);
		oks.setTitleUrl(shareStr[0].split("&")[0]);
		oks.setText(shareStr[3]);// 分享商品介绍
		oks.setImageUrl(shareStr[1]);
		oks.setUrl(shareStr[0].split("&")[0]);
		oks.setFilePath(MainActivity.TEST_IMAGE);
		oks.setComment(getBaseContext().getString(R.string.share));
		oks.setSite(getBaseContext().getString(R.string.app_name));
		oks.setSiteUrl(shareStr[0].split("&")[0]);
		oks.setVenueName(shareStr[0].split("&")[0]);
		oks.setVenueDescription("This is a beautiful place!");
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.setSilent(silent);

		// if (platform != null) {
		// oks.setPlatform(platform);
		// }

		// 取消注释，可以实现对具体的View进行截屏分享
		// oks.setViewToShare(getPage());

		// 去除注释，可令编辑页面显示为Dialog模式
		// oks.setDialogMode();

		// 去除注释，在自动授权时可以禁用SSO方式
		// oks.disableSSOWhenAuthorize();

		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		// oks.setCallback(new OneKeyShareCallback());
		// oks.setShareContentCustomizeCallback(new
		// ShareContentCustomizeDemo());

		// 去除注释，演示在九宫格设置自定义的图�?
		// Bitmap logo = BitmapFactory.decodeResource(menu.getResources(),
		// R.drawable.ic_launcher);
		// String label = menu.getResources().getString(R.string.app_name);
		// OnClickListener listener = new OnClickListener() {
		// public void onClick(View v) {
		// String text = "Customer Logo -- ShareSDK " +
		// ShareSDK.getSDKVersionName();
		// Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		// oks.finish();
		// }
		// };
		// oks.setCustomerLogo(logo, label, listener);

		// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
		// oks.addHiddenPlatform(SinaWeibo.NAME);
		// oks.addHiddenPlatform(TencentWeibo.NAME);

		oks.show(getBaseContext());
	}

	/**
	 * js调用原生方法 跳转到登录界�?
	 */
	public void goHomeLogin() {
		mHandlerGoToMone = new HandlerGoToMone(getMainLooper());
		Message message = new Message();
		message.obj = "gotomone";
		mHandlerGoToMone.sendMessage(message);
	}

	/**
	 * 主线程跳转到登录界面
	 * 
	 * @author Administrator
	 * 
	 */
	private class HandlerGoToMone extends Handler {

		private HandlerGoToMone(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
			Intent intent = new Intent();
			intent.putExtra("result", result);
			/*
			 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity
			 * 这样就可以在onActivityResult方法中得到Intent对象�?
			 */
			setResult(1001, intent);
			// 结束当前这个Activity对象的生�?
			finish();
		}
	}

	/**
	 * 主线程跳转结算界面，并传递优惠券列表
	 * 
	 * @author Administrator
	 * 
	 */
	private class HandlerGoToBalance extends Handler {

		private HandlerGoToBalance(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
			Intent intent = new Intent();
			intent.putExtra("result", result);
			/*
			 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity
			 * 这样就可以在onActivityResult方法中得到Intent对象�?
			 */
			setResult(102, intent);
			// 结束当前这个Activity对象的生�?
			finish();
		}
	}

	/** 图片来源菜单响应监听 */
	protected class OnMyOnClickListener implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case TAKE_PICTURE:
				Intent openCameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				Uri imageUri = Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "image.jpg"));
				// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(openCameraIntent, TAKE_PICTURE);
				break;

			case 1:
				boolean isKitKatO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
				Intent getAlbum;

				getAlbum = new Intent(Intent.ACTION_PICK);
				getAlbum.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 使用以上这种模式，并添加以上两句
				getAlbum.setType("image/*");

				startActivityForResult(getAlbum, CHOOSE_PICTURE);

				// Intent openAlbumIntent = new
				// Intent(Intent.ACTION_GET_CONTENT);
				// openAlbumIntent.setType("image/*");
				// startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 当转跳的目标页面,结束以后,会回调这个方�? 第一个参数就是startActivityForResult(intent, 0)
	 * 中第二个int形参�? 第二个参数就是CaptureActivity类中,setResult(RESULT_OK,intent)
	 * 的第�?个参�?,也是用来区分谁调用参�? 第三个是Intent对象,数据就是用此对象来传递的
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) { // 此处就是用result来区�?,是谁返回的数�?,此处为扫�?扫返�?
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result"); // 这就获取了扫描的内容

			// 网址
			String url = getResources().getString(R.string.barUrl);
			url = url + "/barcode?barnum=" + scanResult;
			webview.loadUrl(url);
		}
		if (resultCode == RESULT_OK) {// 图片数据返回正确
			// try {
			if (requestCode == TAKE_PICTURE)// 从相机获取图�?
			{
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog
							.createDialog(SearchActitity.this);
					progressDialog.setMessage(getResources().getString(
							R.string.upload_img));
					progressDialog.show();
					webview.setEnabled(false);// 当加载网页的时，将网页进行隐�?
				} else {
					progressDialog.setMessage(getResources().getString(
							R.string.upload_img));
					progressDialog.show();
					webview.setEnabled(false);// 当加载网页的时�?�，将网页进行隐�?
				}
				// 将保存在本地的图片取出并缩小后显示在界面�?
				// Bitmap bitmap = BitmapFactory.decodeFile(Environment
				// .getExternalStorageDirectory() + "/image.jpg");
				String path = Environment.getExternalStorageDirectory()
						.toString();
				// Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
				// bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				String url = getResources().getString(R.string.barUrl);
				startFunction(path + "/image.jpg", url, oid, productid);
				// bitmap.recycle();
			} else if (requestCode == 2)// 得到照片的原始地�?
			{
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog
							.createDialog(SearchActitity.this);
					progressDialog.setMessage(getResources().getString(
							R.string.upload_img));
					progressDialog.show();
					webview.setEnabled(false);// 当加载网页的时，将网页进行隐�?
				} else {
					progressDialog.setMessage(getResources().getString(
							R.string.upload_img));
					progressDialog.show();
					webview.setEnabled(false);// 当加载网页的时�?�，将网页进行隐�?
				}
				ContentResolver resolver = getContentResolver();
				// 照片的原始资源地�?
				Uri originalUri = data.getData();
				String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路�?
				String img_url = "";
				if (originalUri.toString().toLowerCase().contains("file://")) {
					String path = originalUri.toString();
					img_url = path.substring(7, path.length());
				} else {
					Cursor cursor = getContentResolver().query(originalUri,
							imgs, null, null, null);
					cursor.moveToFirst();
					int index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

					img_url = cursor.getString(index);

				}
				//
				// if(android.os.Build.VERSION.SDK_INT >=
				// android.os.Build.VERSION_CODES.KITKAT){//4.4及以上
				// String wholeID =
				// DocumentsContract.getDocumentId(originalUri);
				// String id = wholeID.split(":")[1];
				// String[] column = { MediaStore.Images.Media.DATA };
				// String sel = MediaStore.Images.Media._ID +="?";
				// Cursor cursor =
				// this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				// column,
				// sel, new String[] { id }, null);
				// int columnIndex = cursor.getColumnIndex(column[0]);
				// if (cursor.moveToFirst()) {
				// img_url = cursor.getString(columnIndex);
				// }
				// cursor.close();
				// }else{//4.4以下，即4.4以上获取路径的方法
				// String[] projection = { MediaStore.Images.Media.DATA };
				// Cursor cursor = this.getContentResolver().query(originalUri,
				// projection, null, null, null);
				// int column_index =
				// cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// cursor.moveToFirst();
				// img_url = cursor.getString(column_index);
				// }
				String url = getResources().getString(R.string.barUrl);
				startFunction(img_url, url, oid, productid);
			}
		}
	}

	/**
	 * 将从相机或相册里的图片上传到服务�?
	 * 
	 * @param img_str
	 *            图片地址
	 * @param sturl
	 *            上传服务器地�?
	 * @param uidqidstr
	 *            uid和qid的拼�?
	 */
	public void startFunction(final String img_str, final String sturl,
			final String confirmoid, final String confirmproductid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Bitmap bitmap = BitmapFactory.decodeResource(
					// MainActivity.this.getApplicationContext()
					// .getResources(), R.drawable.ic_launcher);
					Bitmap bitmap = BitmapFactory.decodeFile(img_str);
					String http = sturl + "/UploadCer/UploadOrderRed";

					HttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(http);
					MultipartEntity reqEntity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					// 压缩图片
					int bitmapWidth = bitmap.getWidth();
					int bitmapHeight = bitmap.getHeight();
					if (bitmapWidth > 800) {
						// 创建操作图片用的matrix对象
						Matrix matrix = new Matrix();
						// 计算宽高缩放率
						float scaleWidth = ((float) 800) / bitmapWidth;
						// 缩放图片动作
						matrix.postScale(scaleWidth, scaleWidth);
						bitmap = Bitmap.createBitmap(bitmap, 0, 0,
								(int) bitmapWidth, (int) bitmapHeight, matrix,
								true);
					}

					bitmap.compress(CompressFormat.JPEG, 100, bos);
					byte[] data = bos.toByteArray();
					String datastr = confirmoid + "&" + confirmproductid
							+ ".jpg";
					ByteArrayBody bab = new ByteArrayBody(data, datastr);

					// 这里的第�?个参数要和服务器的参数名相同
					reqEntity.addPart("file", bab);

					postRequest.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(postRequest);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					String sResponse;
					StringBuilder s = new StringBuilder();

					while ((sResponse = reader.readLine()) != null) {
						s = s.append(sResponse);
						Returnurl = new returnurl(getMainLooper());
						Message message = new Message();
						message.obj = s;
						Returnurl.sendMessage(message);
					}
					Log.e("sss", s.toString());
				} catch (Exception e) {
					Log.e("Exception in Image", "" + e);

				}
			}
		}).start();
	}

	// 返回给页面url连接
	private class returnurl extends Handler {

		private returnurl(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
				webview.setEnabled(true);
			}
			try {
				JSONTokener jsonParser = new JSONTokener(str);
				JSONObject strjson = (JSONObject) jsonParser.nextValue();
				if (strjson.getBoolean("result")) {
					String imgurl = strjson.getString("src");
					// 调用js
					webview.loadUrl("javascript:NativeCallJs('" + imgurl + "')");// Android调用js方法
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
