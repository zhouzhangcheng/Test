package com.newgame.sdk.yyaost;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.lxd.widgets.CustomProgressDialog;
import com.newgame.sdk.AndroidShare;
import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.R.string;
import com.newgame.sdk.yyaost.wxapi.WXPayEntryActivity;
import com.oneapm.agent.android.OneApmAgent;
import com.st.LauncherGuide.splash.*;
import com.zhangyx.MyLauncherGuide.activity.viewPage.*;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.zxing.activity.CaptureActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebStorage;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.st.untill.*;
import com.styao.work.ProductListActivity;
import com.styao.work.fragment.SearchFragment;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.umeng.message.entity.UMessage;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout ll_home;
	private LinearLayout ll_search;
	private LinearLayout ll_scan;
	private LinearLayout ll_buy;
	private LinearLayout ll_mone;
	private ImageView image_home;
	private ImageView image_friends;
	private ImageView image_scan;
	private ImageView image_buy;
	private ImageView image_mone;

	public static String seturl = "";
	// private WebView mainwebview;
	public static String oldCookie = "";
	public static String submitoder = "";
	public static int isfirst = 0;

	// private ProgressDialog progressDialog = null;
	private CustomProgressDialog progressDialog = null;

	public static String uidAndQid = "";

	public static int reloadnum = 0;

	// 拍照上传
	protected static final int REQUEST_CODE_IMAGE_CAPTURE = 0;
	protected static final int REQUEST_CODE_IMAGE_SELECTE = 1;
	protected static final int REQUEST_CODE_IMAGE_CROP = 2;
	/* 拍照的照片存储位�? */
	private static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera");
	// 缓存图片URI
	Uri imageTempUri = Uri.fromFile(new File(PHOTO_DIR,
			"temp_photo_headimg.jpg"));
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 2;

	/** SharedPreferences */
	private SharedPreferences mSharedPreferences;
	private static final String SAVE_FILE_NAME = "save_spref";
	public static Double version = 0.0;
	public static String versionurl = "";
	private HandlerTest2 mHandlerTest2;
	private HandlerTest3 mHandlerTest3;
	private HandlerTest4 mHandlerTest4;

	private reload Reload;

	public static String TEST_IMAGE;
	public static String TEST_IMAGE_URL;
	public static String androidId = null;

	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

	// Fragment管理器
	private FragmentManager fm = this.getSupportFragmentManager();
	private FragmentTransaction ft;
	private FragmentPage1 fragmentPage1;
	// private FragmentPage2 fragmentPage2;
	private SearchFragment fragmentPage2;
	private FragmentPage3 fragmentPage3;
	private FragmentPage4 fragmentPage4;
	private FragmentPage5 fragmentPage5;

	private FragmentPagest301 fragmentPagest301;
	private FragmentPagest302 fragmentPagest302;
	private FragmentPagest303 fragmentPagest303;
	private FragmentPagest301error fragmentPagest301error;
	private FragmentPagest301nobuy fragmentPagest301nobuy;

	private int isucenter = 0;
	private int ishome = 0;
	private int issearch = 0;
	private int iscart = 0;
	private int issubmit = 0;
	private int isst301 = 0;
	private int isst302 = 0;
	private int isst303 = 0;
	private int isst301error = 0;
	private int isst301noBuy = 0;

	private Fragment randomname;
	public static boolean isForeground = false;
	private static final String TAG = MainActivity.class.getName();
	private PushAgent mPushAgent;

	// private WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		startUmeng();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		req = new PayReq();
		//
		IntentFilter filter = new IntentFilter(WXPayEntryActivity.action);
		registerReceiver(broadcastReceiver, filter);
		// qqopen count
		StatConfig.setDebugEnable(true);
		StatService.trackCustomEvent(this, "onCreate", "");
		// AndroidBug5497Workaround.assistActivity(this);// 解决软键盘覆盖输入框的问�?
		// 默认软键盘不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		// 设置状�?�栏颜色
		setTranslucentStatus();

		mSharedPreferences = getSharedPreferences(SAVE_FILE_NAME, MODE_PRIVATE);
		mSharedPreferences.edit();
		initView();
		ft = fm.beginTransaction();
		home();
		// 提交事务
		ft.commit();
		gowait();

		// 获取xml
		try {
			getXMLData();
			// postAdroidID();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int versionCode = getVersionCode(this);
		String isFirstRun = "isFirstRun" + versionCode;
		SharedPreferences setting = getSharedPreferences("share", 0);
		Boolean user_first = setting.getBoolean(isFirstRun, true);
		if (user_first) {// first start
			setting.edit().putBoolean(isFirstRun, false).commit();
			new Thread(new Runnable() {
				public void run() {
					startActivity(new Intent(MainActivity.this,
							ViewPagerActivity.class));
				}
			}).start();
		} else {
			new Thread(new Runnable() {
				public void run() {
					startActivity(new Intent(MainActivity.this,
							SplashActivity.class));
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
			}).start();
		}
		OneApmAgent.init(this.getApplicationContext())
				.setToken("1CD40F5D462F9823EA693EA791FCB27C21").start();
	}

	private void initView() {
		ll_home = (LinearLayout) findViewById(R.id.ll_home);
		ll_search = (LinearLayout) findViewById(R.id.ll_search);
		ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
		ll_buy = (LinearLayout) findViewById(R.id.ll_buy);
		ll_mone = (LinearLayout) findViewById(R.id.ll_mone);

		image_home = (ImageView) findViewById(R.id.image_home);
		image_friends = (ImageView) findViewById(R.id.image_search);
		image_scan = (ImageView) findViewById(R.id.image_scan);
		image_buy = (ImageView) findViewById(R.id.image_buy);
		image_mone = (ImageView) findViewById(R.id.image_mone);

		// webview = (WebView)findViewById(R.id.webView1);
		ll_home.setOnClickListener(this);
		ll_search.setOnClickListener(this);
		ll_scan.setOnClickListener(this);
		ll_buy.setOnClickListener(this);
		ll_mone.setOnClickListener(this);
		ll_home.setSelected(true);
		image_home.setSelected(true);
		// 点击扫一扫跳转到扫一扫界�?
		image_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		ll_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		// 初始化分�?
		ShareSDK.initSDK(this);
		ShareSDK.registerPlatform(Laiwang.class);
		// 腾讯信鸽推送注册
		// XGPushConfig.enableDebug(this, true);
		// Context context = getApplicationContext();
		// XGPushManager.registerPush(context);

		// 2.36（不包括）之前的版本需要调用以下2行代码
		// Intent service = new Intent(context, XGPushService.class);
		// context.startService(service);
		init();
	}

	public void startUmeng() {
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.setDebugMode(true);

		/**
		 * 该Handler是在BroadcastReceiver中被调用，故
		 * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
		 * */
		UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
			@Override
			public void launchApp(Context context, UMessage msg) {
				Intent intent = new Intent();
				intent.setClass(context, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("msgType", "00234");
				startActivity(intent);
			}
		};
		mPushAgent.setNotificationClickHandler(notificationClickHandler);

	}

	private void gowait() {
		Intent intent = getIntent();
		if (null != intent) {
			Bundle bundle = getIntent().getExtras();
			String msgType = intent.getStringExtra("msgType");
			String str = "00234";
			if (str.equals(msgType)) {
				ft = fm.beginTransaction();
				// 把显示的Fragment隐藏
				setSelected();
				// mone();
				ll_mone.setSelected(true);
				image_mone.setSelected(true);
				isucenter = 1;
				ishome = 0;
				issearch = 0;
				iscart = 0;
				issubmit = 0;
				if (fragmentPage4 != null) {
					fragmentPage4 = null;
				}
				fragmentPage4 = new FragmentPage4();
				Bundle bundle1 = new Bundle();
				String url = getResources().getString(R.string.barUrl)
						+ "ucenter/orderwaitreceiptlist";
				bundle1.putString("paygotomine", url);
				fragmentPage4.setArguments(bundle1);
				ft.add(R.id.fl_content, fragmentPage4);
				ft.commitAllowingStateLoss();
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		// 每次点击时都需要重新开始事务
		ft = fm.beginTransaction();
		// 把显示的Fragment隐藏
		setSelected();
		switch (v.getId()) {
		case R.id.ll_home:
			home();
			home();
			break;
		case R.id.ll_search:
			search();
			break;
		case R.id.ll_buy:
			buy();
			break;
		case R.id.ll_mone:
			mone();
			break;
		}
		ft.commit();// 生效
	}

	private void setSelected() {
		ll_home.setSelected(false);
		ll_search.setSelected(false);
		// ll_scan.setSelected(false);
		ll_buy.setSelected(false);
		ll_mone.setSelected(false);
		image_home.setSelected(false);
		image_friends.setSelected(false);
		// image_scan.setSelected(false);
		image_buy.setSelected(false);
		image_mone.setSelected(false);
		if (fragmentPage1 != null) {
			// 隐藏Fragment
			ft.hide(fragmentPage1);
		}
		if (fragmentPage2 != null) {
			ft.hide(fragmentPage2);
		}
		if (fragmentPage3 != null) {
			ft.hide(fragmentPage3);
		}
		if (fragmentPage4 != null) {
			ft.hide(fragmentPage4);
		}
		if (fragmentPage5 != null) {
			ft.hide(fragmentPage5);
		}
	}

	private void home() {
		ll_home.setSelected(true);
		image_home.setSelected(true);
		isucenter = 0;
		ishome = 1;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPage1 != null) {
			fragmentPage1 = null;
		}
		fragmentPage1 = new FragmentPage1();
		ft.add(R.id.fl_content, fragmentPage1);
	}

	private void search() {
		ll_search.setSelected(true);
		image_friends.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 1;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		// if (fragmentPage2 == null) {
		// fragmentPage2 = new FragmentPage2();
		//
		// ft.add(R.id.fl_content, fragmentPage2);
		//
		// } else {
		// // 显示Fragment
		// ft.show(fragmentPage2);
		//
		// }
		if (fragmentPage2 != null) {
			fragmentPage2 = null;
		}
		fragmentPage2 = new SearchFragment();

		ft.add(R.id.fl_content, fragmentPage2);
	}

	private void buy() {
		ll_buy.setSelected(true);
		image_buy.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 1;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPage3 != null) {
			fragmentPage3 = null;
		}
		fragmentPage3 = new FragmentPage3();

		ft.add(R.id.fl_content, fragmentPage3);

	}

	private void submitorder() {
		ll_buy.setSelected(true);
		image_buy.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 1;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPage5 != null) {
			fragmentPage5 = null;
		}
		fragmentPage5 = new FragmentPage5();

		ft.add(R.id.fl_content, fragmentPage5);
	}

	private void mone() {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		String url = getResources().getString(R.string.barUrl);
		isucenter = 1;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPage4 != null) {
			fragmentPage4 = null;
		}
		fragmentPage4 = new FragmentPage4();
		ft.add(R.id.fl_content, fragmentPage4);
	}

	private void st301() {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 1;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		// if (fragmentPagest301 == null) {
		// fragmentPagest301 = new FragmentPagest301();
		//
		// ft.add(R.id.fl_content, fragmentPagest301);
		//
		// } else {
		// // 显示Fragment
		// ft.show(fragmentPagest301);
		//
		// }

		if (fragmentPagest301 != null) {
			fragmentPagest301 = null;
		}
		fragmentPagest301 = new FragmentPagest301();
		// ft.add(R.id.fl_content, fragmentPagest301);
		ft.replace(R.id.fl_content, fragmentPagest301);
	}

	private void st301noBuy() {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 1;
		// if (fragmentPagest302 == null) {
		// fragmentPagest302 = new FragmentPagest302();
		//
		// ft.add(R.id.fl_content, fragmentPagest302);
		//
		// } else {
		// // 显示Fragment
		// ft.show(fragmentPagest302);
		//
		// }
		if (fragmentPagest301nobuy != null) {
			fragmentPagest301nobuy = null;
		}
		fragmentPagest301nobuy = new FragmentPagest301nobuy();
		// ft.add(R.id.fl_content, fragmentPagest302);
		ft.replace(R.id.fl_content, fragmentPagest301nobuy);
	}

	private void st301error() {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 1;
		isst301noBuy = 0;
		// if (fragmentPagest301error == null) {
		// fragmentPagest301error = new FragmentPagest301error();
		//
		// ft.add(R.id.fl_content, fragmentPagest301error);
		//
		// } else {
		// // 显示Fragment
		// ft.show(fragmentPagest301error);
		//
		// }
		if (fragmentPagest301error != null) {
			fragmentPagest301error = null;
		}
		fragmentPagest301error = new FragmentPagest301error();
		// ft.add(R.id.fl_content, fragmentPagest301error);
		ft.replace(R.id.fl_content, fragmentPagest301error);
	}

	private void st302() {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 1;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		// if (fragmentPagest302 == null) {
		// fragmentPagest302 = new FragmentPagest302();
		//
		// ft.add(R.id.fl_content, fragmentPagest302);
		//
		// } else {
		// // 显示Fragment
		// ft.show(fragmentPagest302);
		//
		// }
		if (fragmentPagest302 != null) {
			fragmentPagest302 = null;
		}
		fragmentPagest302 = new FragmentPagest302();
		// ft.add(R.id.fl_content, fragmentPagest302);
		ft.replace(R.id.fl_content, fragmentPagest302);
	}

	private void st303(String SchemeName) {
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 0;
		isst301 = 0;
		isst302 = 0;
		isst303 = 1;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPagest303 != null) {
			fragmentPagest303 = null;
		}
		fragmentPagest303 = new FragmentPagest303();
		Bundle bundle = new Bundle();
		bundle.putString("SchemeName", SchemeName);
		fragmentPagest303.setArguments(bundle);
		// ft.add(R.id.fl_content, fragmentPagest303);
		ft.replace(R.id.fl_content, fragmentPagest303);
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
			Intent intent = new Intent();
			intent.putExtra("fromcategories", "scanResult," + scanResult);
			intent.setClass(MainActivity.this, SearchActitity.class);

			startActivityForResult(intent, 10);
		}
		if (resultCode == 1001)// 未登录时加购物车，将跳转到登录界�?
		{
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "indextomine";
			handler.sendMessage(message);
		} else if (resultCode == 101)// 从确认收货返�?,刷新列表
		{
			fragmentPage4.reload();
		} else if (resultCode == 102) {
			Bundle bundle = data.getExtras();
			String CouponsList = bundle.getString("result"); // 这就获取了扫描的内容
			fragmentPage5.GetCouponsList(CouponsList);
		} else if (resultCode == 103)// 从购买页跳转
		{
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "buytocart";
			handler.sendMessage(message);
		} else if (resultCode == 90)// 支付宝支�?
		{

		} else if (resultCode == 104) {
			// setSelected();
			// ll_mone.setSelected(true);
			// image_mone.setSelected(true);
			// String url = getResources().getString(R.string.barUrl);
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			String url = getResources().getString(R.string.barUrl);
			url += "ucenter/MyControls";
			message.obj = "buytomycontrol," + url;
			handler.sendMessage(message);
		}

		if (resultCode == RESULT_OK) {// 图片数据返回正确
			// try {
			if (requestCode == TAKE_PICTURE)// 从相机获取图�?
			{
				String path = Environment.getExternalStorageDirectory()
						.toString();
				// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				String url = getResources().getString(R.string.barUrl);
				startFunction(path + "/image.jpg", url, uidAndQid);

			} else if (requestCode == 2)// 得到照片的原始地�?
			{
				// 照片的原始资源地�?
				Uri originalUri = data.getData();
				String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路�?
				String img_url = "";
				if (originalUri.toString().toLowerCase().contains("file://")) {
					String path = originalUri.toString();
					img_url = path.substring(7, path.length());
				} else {
					Cursor cursor = this.getContentResolver().query(
							originalUri, imgs, null, null, null);
					int index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					img_url = cursor.getString(index);
				}
				String url = getResources().getString(R.string.barUrl);
				startFunction(img_url, url, uidAndQid);
			}
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

			View layoutAll = findViewById(R.id.layoutAll);
			// 设置系统栏需要的内偏�?
			layoutAll.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
		}
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

	public void JsToAdroidUrl(String url) {
		if (url.contains("/Search/SearchDetail?producttype=2")) {// 首推联盟
			Intent intent = new Intent();
			intent.putExtra("producttype", 2);
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else if (url.contains("/Search/SearchDetail?cuxiao=1")) {// 加价购
			Intent intent = new Intent();
			intent.putExtra("cuxiao", 1);
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else if (url.contains("/Search/SearchDetail?cuxiao=2")) {// 包邮
			Intent intent = new Intent();
			intent.putExtra("cuxiao", 2);
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else if (url.contains("/Search/SearchDetail?cuxiao=3")) {// 特价
			Intent intent = new Intent();
			intent.putExtra("cuxiao", 3);
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else if (url.contains("/Store/StoreSearchResult")) {// 品牌搜索
			Intent intent = new Intent();
			intent.putExtra("shopOrgood", 2);
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.putExtra("url", url);
			intent.setClass(MainActivity.this, SearchActitity.class);
			startActivityForResult(intent, 10);
		}
	}

	public void JsToAndroidProdRank(String cateid) {
		Intent intent = new Intent();
		intent.putExtra("ProdRank", cateid);
		intent.setClass(MainActivity.this, SearchActitity.class);
		startActivityForResult(intent, 10);
	}

	/**
	 * js调用原生方法 统一调用接口
	 * 
	 * @param str
	 */
	public void JsToLoacleFunction(String Parameters) {
		if (Parameters.toLowerCase().contains("indextomine")) {
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "indextomine";
			handler.sendMessage(message);
		} else if (Parameters.toLowerCase().contains("scan")) {
			Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
			startActivityForResult(intent, 0);
		} else if (Parameters.toLowerCase().contains("reload"))// 刷新界面
		{
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "indextomine";
			handler.sendMessage(message);
		} else if (Parameters.toLowerCase().contains("buytocart")) {
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "buytocart";
			handler.sendMessage(message);
		} else if (Parameters.toLowerCase().contains("indextosearch")) {
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "indextosearch";
			handler.sendMessage(message);
		} else if (Parameters.toLowerCase().contains("storeid")) {
			Intent intent = new Intent();
			intent.putExtra("fromcategories",
					"mycollections," + Parameters.split(",")[1]);
			intent.setClass(MainActivity.this, SearchActitity.class);

			startActivityForResult(intent, 10);
		} else if (Parameters.toLowerCase().contains("checkversioninfo")) {
			try {
				postAdroidID();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void searchBack(String str) {
		if (str.toLowerCase().contains("buytocart")) {
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "buytocart";
			handler.sendMessage(message);
		}
	}

	/**
	 * js调用原生方法 从首页跳转到产品详情�?
	 * 
	 * @param str
	 */
	public void IndexToProductDetail(String goodsPakageId) {
		Intent intent = new Intent();
		intent.putExtra("fromcategories", "goodsPakageId," + goodsPakageId);
		intent.setClass(MainActivity.this, SearchActitity.class);

		startActivityForResult(intent, 10);
	}

	/**
	 * js调用原生方法 alipay
	 * 
	 * @param str
	 */
	public void alipay(String getsignurl) {
		Intent intent = new Intent();
		intent.putExtra("getsignurl", getResources().getString(R.string.barUrl)
				+ getsignurl);
		intent.setClass(MainActivity.this, PayDemoActivity.class);

		startActivityForResult(intent, 90);// 支付宝支�?
	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void callCamera(String str) {
		uidAndQid = str;
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("图片选项")
				.setItems(new String[] { "手机拍照", "手机相册", "取消" },
						new OnMyOnClickListener()).show();
	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void searchResult(String str) {
		Intent intent = new Intent(MainActivity.this, SearchActitity.class);
		startActivityForResult(intent, 10);
	}

	/**
	 * js调用原生方法
	 * 
	 * @param str
	 */
	public void CateSearch(String strurl) {
		if (strurl.contains("keyWords")) {
			Intent intent = new Intent();
			try {
				intent.putExtra("keyword", strurl.split(",", 2)[1]);
			} catch (Exception e) {
			}
			intent.setClass(MainActivity.this, ProductListActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.putExtra("fromcategories", strurl);
			intent.setClass(MainActivity.this, SearchActitity.class);
			startActivityForResult(intent, 10);
		}
	}

	/**
	 * js调用原生方法 确认收货
	 * 
	 * @param str
	 */
	public void ConfirmRevieve(String oid) {
		Intent intent = new Intent();
		intent.putExtra("fromcategories", oid);
		intent.setClass(MainActivity.this, SearchActitity.class);

		startActivityForResult(intent, 10);
	}

	/**
	 * js调用原生方法 购物车跳转到商品详情�? parm:商品id
	 * 
	 * @param str
	 */
	public void cartGotoProductBuy(String pid) {
		Intent intent = new Intent();
		intent.putExtra("fromcategories", "cartToProductbuy," + pid);
		intent.setClass(MainActivity.this, SearchActitity.class);

		startActivityForResult(intent, 10);
	}

	// 测试微信支付接口
	public void wxPay(String json) {
		// JSONTokener jsonParser=new JSONTokener(json);
		try {
			JSONObject jsons = new JSONObject(json);
			req.appId = jsons.getString("appid");
			req.partnerId = jsons.getString("partnerid");
			req.prepayId = jsons.getString("prepayid");
			req.packageValue = jsons.getString("package");
			req.nonceStr = jsons.getString("noncestr");
			req.timeStamp = jsons.getString("timestamp");
			req.sign = jsons.getString("sign");
		} catch (JSONException ex) {
			// json数据解析异常
		}
		sendPayReq();
	}

	private void sendPayReq() {
		msgApi.registerApp(req.appId);
		boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
				&& msgApi.isWXAppSupportAPI();
		if (sIsWXAppInstalledAndSupported) {
			msgApi.sendReq(req);
		} else {
			new AlertDialog.Builder(MainActivity.this)
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
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("");
			builder.setMessage(getString(R.string.pay_result_callback_msg,
					resp.errStr + ";code=" + String.valueOf(resp.errCode)));
			builder.show();
		}
	}

	/**
	 * js调用原生方法捡测版本�?
	 * 
	 * @param str
	 */
	public void CheckUpgrade() {
		// 获取xml
		try {
			getStXMLData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * js调用原生方法 分享app
	 */
	public void ShareAPP() {
		// 收到消息后可再发消息到主线程
		mHandlerTest4 = new HandlerTest4(getMainLooper());
		Message message = new Message();
		String url = getResources().getString(R.string.barUrl)
				+ "/Download/Download";
		message.obj = url;
		mHandlerTest4.sendMessage(message);
	}

	/**
	 * js调用原生方法 选择使用的优惠券
	 */
	public void getcoupons(String SelectedCartItemKeyList,
			String selectedCouponItemKeyList) {
		Intent intent = new Intent();
		intent.putExtra("fromcategories",
				"SelectedCartItemKeyList,?SelectedCartItemKeyList="
						+ SelectedCartItemKeyList
						+ "&selectedCouponItemKeyList="
						+ selectedCouponItemKeyList);
		intent.setClass(MainActivity.this, SearchActitity.class);

		startActivityForResult(intent, 10);
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
				Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
				openAlbumIntent.setType("image/*");
				startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
				break;

			default:
				break;
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
			final String uidqidstr) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Bitmap bitmap = BitmapFactory.decodeFile(img_str);
					String http = sturl + "/UploadCer/UploadCertificate";

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
					String[] str1 = uidqidstr.split(",");
					int uid = Integer.parseInt(str1[0]);
					int Qid = Integer.parseInt(str1[1]);
					String datastr = "";
					if (str1.length == 3) {
						int stcomment = Integer.parseInt(str1[2]);
						datastr = String.valueOf(uid) + "&"
								+ String.valueOf(Qid) + "&"
								+ String.valueOf(stcomment) + ".jpg";
					} else {
						datastr = String.valueOf(uid) + "&"
								+ String.valueOf(Qid) + ".jpg";
					}
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
						Reload = new reload(getMainLooper());
						Message message = new Message();
						message.obj = "reload";
						if (s.toString().toLowerCase()
								.contains("/upload/stcommentimg")) {
							message.obj = s.toString().toLowerCase();
						}
						Reload.sendMessage(message);
					}
					Log.e("sss", s.toString());
				} catch (Exception e) {
					Log.e("Exception in Image", "" + e);
				}
			}
		}).start();
	}

	public void postAdroidID() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String sturl = getResources().getString(R.string.barUrl);
					// 手机唯一标识号
					androidId = Secure.getString(getContentResolver(),
							Secure.ANDROID_ID);
					URL url = new URL(sturl + "/api/androidId?id=" + androidId);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("POST");
					if (conn.getResponseCode() == 200) {
						InputStream in = conn.getInputStream();
						StringBuffer out = new StringBuffer();
						byte[] b = new byte[4096];
						int n;
						// while ((n = in.read(b)) != -1) {
						// out.append(new String(b, 0, n));
						// }
						// String str = out.toString();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"utf-8"));
						String line = "";
						String str = "";
						while (null != (line = br.readLine())) {
							str += line;
						}

						// 2 收到消息后可再发消息到主线程
						Reload = new reload(getMainLooper());
						Message message = new Message();
						message.obj = str;
						Reload.sendMessage(message);
					}
				} catch (Exception ex) {
					Log.e("sss", ex.toString());
				}
			}
		}).start();
	}

	// 读取XML数据并转为List<Person>
	public void getXMLData() {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = getResources().getString(R.string.barUrl);
					URL url = new URL(sturl + "/Upgrade/AndroidUpgrade");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == 200) {
						InputStream in = conn.getInputStream();
						StringBuffer out = new StringBuffer();
						byte[] b = new byte[4096];
						int n;
						while ((n = in.read(b)) != -1) {
							out.append(new String(b, 0, n));
						}
						String str = out.toString();

						// 2 收到消息后可再发消息到主线程
						mHandlerTest2 = new HandlerTest2(getMainLooper());
						Message message = new Message();
						message.obj = str;
						mHandlerTest2.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	// 读取XML数据并转为List<Person>
	public void getStXMLData() {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = getResources().getString(R.string.barUrl);
					URL url = new URL(sturl + "/Upgrade/AndroidUpgrade");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == 200) {
						InputStream in = conn.getInputStream();
						StringBuffer out = new StringBuffer();
						byte[] b = new byte[4096];
						int n;
						while ((n = in.read(b)) != -1) {
							out.append(new String(b, 0, n));
						}
						String str = out.toString();

						// 2 收到消息后可再发消息到主线程
						mHandlerTest3 = new HandlerTest3(getMainLooper());
						Message message = new Message();
						message.obj = str;
						mHandlerTest3.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	private class HandlerTest2 extends Handler {

		private HandlerTest2(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			if (str.length() > 2) {
				UpdateManager manager = new UpdateManager(MainActivity.this);
				// check app version
				manager.checkUpdate(str);
			}
		}
	}

	/**
	 * 分享主线�?(分享app)
	 * 
	 * @author Administrator
	 * 
	 */
	private class HandlerTest4 extends Handler {

		private HandlerTest4(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			// AndroidShare as = new AndroidShare(MainActivity.this,
			// "首推App" + str, str);
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
		// String[] shareStr=platform.split(",");
		String url = getResources().getString(R.string.barUrl);
		oks.setNotification(R.drawable.ic_launcher,
				getBaseContext().getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setTitle("首推app");
		oks.setTitleUrl(platform);
		oks.setText("首推app为您提供一站式的药品采购平台");// 分享商品介绍
		// oks.setImagePath(MainActivity.TEST_IMAGE);//本地图片地址
		oks.setImageUrl(url + "/img/ic_launcher.png");
		oks.setUrl(platform);
		oks.setFilePath(MainActivity.TEST_IMAGE);
		oks.setComment(getBaseContext().getString(R.string.share));
		oks.setSite(getBaseContext().getString(R.string.app_name));
		oks.setSiteUrl(platform);
		oks.setVenueName("101商城app");
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

	private class HandlerTest3 extends Handler {

		private HandlerTest3(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			if (str.length() > 2) {
				UpdateManager manager = new UpdateManager(MainActivity.this);
				// 捡测软件版本�?
				manager.stcheckUpdate(str);
			}
		}
	}

	// 重新加载页面
	private class reload extends Handler {

		private reload(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			if (str.equals("reload")) {
				fragmentPage4.reload();
			} else if (str.contains("/upload/stcommentimg")) {
				String stcommentimgurl = "";
				try {
					JSONObject jsons = new JSONObject(str);
					stcommentimgurl = jsons.getString("message");
				} catch (Exception ex) {

				}
				fragmentPage4.reload("javascript:chengestcommentimg('"
						+ stcommentimgurl + "')");
			} else if (str.contains("title") && str.contains("[{")) {
				fragmentPage1
						.updateVersionInfo("javascript:updateVersionInfo('"
								+ str + "')");
			}
		}
	}

	// js调用本地方法统一方法
	private class jstoloacle extends Handler {

		private jstoloacle(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			if (str.equals("indextomine")) {
				// 每次点击时都需要重新开始事务
				ft = fm.beginTransaction();
				// 把显示的Fragment隐藏
				setSelected();
				mone();
				ft.commit();
			} else if (str.equals("buytocart")) {
				ft = fm.beginTransaction();
				setSelected();
				buy();
				ft.commit();
			} else if (str.equals("indextosearch")) {
				// 每次点击时都需要重新开始事务
				ft = fm.beginTransaction();
				// 把显示的Fragment隐藏
				setSelected();
				search();
				ft.commit();
			} else if (str.contains("paygotomine")
					|| str.contains("buytomycontrol")) {
				// 每次点击时都需要重新开始事务
				ft = fm.beginTransaction();
				// 把显示的Fragment隐藏
				setSelected();
				// mone();
				ll_mone.setSelected(true);
				image_mone.setSelected(true);
				isucenter = 1;
				ishome = 0;
				issearch = 0;
				iscart = 0;
				issubmit = 0;
				isst301 = 0;
				isst302 = 0;
				isst303 = 0;
				isst301error = 0;
				isst301noBuy = 0;
				if (fragmentPage4 != null) {
					fragmentPage4 = null;
				}
				fragmentPage4 = new FragmentPage4();
				Bundle bundle = new Bundle();
				bundle.putString("paygotomine", str.split(",")[1]);
				fragmentPage4.setArguments(bundle);
				ft.add(R.id.fl_content, fragmentPage4);
				ft.commitAllowingStateLoss();
				// fragmentPage4.reload(str.split(",")[1]);
			} else if (str.contains("gost301") && !str.contains("gost301error")) {
				// 跳转到智慧采购
				ft = fm.beginTransaction();
				setSelected();
				st301();
				ft.commitAllowingStateLoss();
			} else if (str.contains("gost301error")) {
				// 跳转到智慧采购不可采购页
				ft = fm.beginTransaction();
				setSelected();
				st301error();
				ft.commitAllowingStateLoss();
			} else if (str.contains("gost302")) {
				// 跳转到智慧采购选择方案
				ft = fm.beginTransaction();
				setSelected();
				st302();
				ft.commitAllowingStateLoss();
			} else if (str.contains("gost303")) {
				// 跳转到智慧采购购买页面
				ft = fm.beginTransaction();
				setSelected();
				st303(str.split(",")[1]);
				ft.commitAllowingStateLoss();
			} else if (str.equals("GoSt30noBuy")) {
				ft = fm.beginTransaction();
				setSelected();
				st301noBuy();
				ft.commitAllowingStateLoss();
			}
		}
	}

	/**
	 * 再按�?次�??出程�?
	 */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isucenter == 1) {
				fragmentPage4.OnkeydownF();
			} else if (ishome == 1) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {// 两秒之内再按�?次则进入else条件
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
			} else if (issearch == 1) {
				fragmentPage2.OnkeydownF();
			} else if (iscart == 1) {
				fragmentPage3.OnkeydownF();
			} else if (issubmit == 1) {
				fragmentPage5.OnkeydownF();
			} else if (isst301 == 1) {
				GotoMine();
			} else if (isst301error == 1 || isst302 == 1 || isst301noBuy == 0) {
				GoSt30();
			} else if (isst303 == 1) {
				GoSt302();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}

					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * 同步�?下cookie
	 */
	private void syncCookie(Context context, String url, boolean isRemoveCookie) {
		try {
			Log.d("Nat: webView.syncCookie.url", url);

			CookieSyncManager.createInstance(context);

			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			if (isRemoveCookie) {
				cookieManager.removeSessionCookie();// 移除
				cookieManager.removeAllCookie();
			}
			String oldCookie = cookieManager.getCookie(url);
			if (oldCookie != null) {
				Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
			}

			StringBuilder sbCookie = new StringBuilder();
			// sbCookie.append(String.format("JSESSIONID=%s",
			// "INPUT YOUR JSESSIONID STRING"));
			// sbCookie.append(String.format(";domain=%s",
			// "INPUT YOUR DOMAIN STRING"));
			// sbCookie.append(String.format(";path=%s",
			// "INPUT YOUR PATH STRING"));
			// add Network type
			String networktype = GetNetworkType(context);
			sbCookie.append(String.format(";networktype=%s", networktype));
			String cookieValue = oldCookie + sbCookie.toString();
			cookieManager.setCookie(url, cookieValue);
			CookieSyncManager.getInstance().sync();

			String newCookie = cookieManager.getCookie(url);
			if (newCookie != null) {
				Log.d("Nat: webView.syncCookie.newCookie", newCookie);
			}
		} catch (Exception e) {
			Log.e("Nat: webView.syncCookie failed", e.toString());
		}
	}

	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 获取软件版本�?
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.newgame.sdk.yyaost", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
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

	public void gotohome() {
		// 每次点击时都需要重新开始事务
		ft = fm.beginTransaction();
		// 把显示的Fragment隐藏
		setSelected();
		home();
		ft.commit();
	}

	public void gotosubmitorder(String str) {
		submitoder = str;
		// 每次点击时都需要重新开始事务
		ft = fm.beginTransaction();
		// 把显示的Fragment隐藏
		setSelected();
		submitorder();
		ft.commit();
	}

	public void gotosubmitorder303(String str) {
		// 每次点击时都需要重新开始事务
		ft = fm.beginTransaction();
		// 把显示的Fragment隐藏
		setSelected();
		// mone();
		ll_mone.setSelected(true);
		image_mone.setSelected(true);
		isucenter = 0;
		ishome = 0;
		issearch = 0;
		iscart = 0;
		issubmit = 1;
		isst301 = 0;
		isst302 = 0;
		isst303 = 0;
		isst301error = 0;
		isst301noBuy = 0;
		if (fragmentPage5 != null) {
			fragmentPage5 = null;
		}
		fragmentPage5 = new FragmentPage5();
		Bundle bundle = new Bundle();
		bundle.putString("submitoder303", str);
		fragmentPage5.setArguments(bundle);
		ft.add(R.id.fl_content, fragmentPage5);
		ft.commitAllowingStateLoss();
	}

	public void GetCookie(String cookies) {
		oldCookie = cookies;
	}

	public String GetProducts() {
		return submitoder;
	}

	public String GetCookiesFrammat() {
		return oldCookie;
	}

	// 跳转到用户中心
	public void GotoMine() {
		// 每次点击时都需要重新开始事务
		ft = fm.beginTransaction();
		// 把显示的Fragment隐藏
		setSelected();
		mone();
		ft.commit();
	}

	// 支付界面跳转到用户中心
	public void PayGotoMine(String url) {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "paygotomine," + url;
		handler.sendMessage(message);
	}

	// 跳转到用户中心
	public void Gotobuy() {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "buytocart";
		handler.sendMessage(message);
	}

	// 跳转到智慧采购
	public void GoSt30() {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "gost301";
		handler.sendMessage(message);
	}

	// 跳转到智慧采购
	public int GoSt30first(boolean isset) {
		if (isset) {
			if (isfirst == 1) {
				isfirst = 0;
			} else {
				isfirst = 1;
			}
		}
		return isfirst;
	}

	// 跳转到智慧采购不可采购页面
	public void GoSt30error() {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "gost301error";
		handler.sendMessage(message);
	}

	// 跳转到智慧采购未采购采购页面
	public void GoSt30noBuy() {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "GoSt30noBuy";
		handler.sendMessage(message);
	}

	// 跳转到方案选择页面
	public void GoSt302() {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "gost302";
		handler.sendMessage(message);
	}

	// 跳转到智慧采购购买页
	public void GoSt303(String SchemeName) {
		jstoloacle handler = new jstoloacle(getMainLooper());
		Message message = new Message();
		message.obj = "gost303," + SchemeName;
		handler.sendMessage(message);
	}

	// 获取购物车商品数据
	public void GetGoodsDataJson(final String apiurl) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String sturl = getResources().getString(R.string.barUrl);
					URL url = new URL(sturl + apiurl);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestProperty("Cookie", oldCookie);
					conn.setRequestProperty("Accept-Charset", "utf-8");
					conn.setRequestProperty("contentType", "utf-8");
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == 200) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"utf-8"));
						String line = "";
						String str = "";
						while (null != (line = br.readLine())) {
							str += line;
						}
						// while ((n = in.read(b)) != -1) {
						// out.append(new String(b, 0, n));
						// }
						// String str = out.toString();

						// 2 收到消息后可再发消息到主线程
						GoodsDataJsonHandler catdata = new GoodsDataJsonHandler(
								getMainLooper());
						Message message = new Message();
						message.obj = str;
						if (apiurl.toLowerCase().contains("cart")
								&& !apiurl.toLowerCase().contains(
										"purchasecart")) {
							message.what = 1;
						} else if (apiurl.toLowerCase()
								.contains("purchasehome")) {
							if (apiurl.toLowerCase().contains("tab=1")) {
								message.what = 21;
							} else if (apiurl.toLowerCase().contains("tab=2")) {
								message.what = 22;
							} else {
								message.what = 2;
							}
						} else if (apiurl.toLowerCase().contains(
								"purchasescheme")) {
							message.what = 3;
						} else if (apiurl.toLowerCase()
								.contains("purchasecart")) {
							message.what = 4;
						}
						catdata.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("sss", e.toString());
				}
			}
		}).start();
	}

	private class GoodsDataJsonHandler extends Handler {

		private GoodsDataJsonHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = msg.obj.toString();
			int requesttype = msg.what;
			if (requesttype == 1) {
				if (str.contains("status\":\"nologin\"")) {
					try {
						fragmentPage3.hideloading();
					} catch (Exception ex) {
						//
					}

					GotoMine();
				} else {
					fragmentPage3.GetCartInfo(str, oldCookie);
				}
			} else if (requesttype == 2) {
				// 智慧采购部分
				fragmentPagest301.GetCartInfo(str, oldCookie);
			} else if (requesttype == 21) {
				fragmentPagest301error.GetCartInfo(str, oldCookie);
				// fragmentPagest301nobuy.GetCartInfo(str, oldCookie);
			} else if (requesttype == 22) {
				fragmentPagest301nobuy.GetCartInfo(str, oldCookie);
			} else if (requesttype == 3) {
				fragmentPagest302.GetCartInfo(str, oldCookie);
			} else if (requesttype == 4) {
				fragmentPagest303.GetCartInfo(str, oldCookie);
			}
		}
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String str = intent.getExtras().getString("data");

			String url = getResources().getString(R.string.barUrl)
					+ "/ucenter/orderlist";
			jstoloacle handler = new jstoloacle(getMainLooper());
			Message message = new Message();
			message.obj = "paygotomine," + url;
			handler.sendMessage(message);
		}
	};

	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
	};

	// dp转像素
	public int getPixelsFromDp(int size) {

		DisplayMetrics metrics = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

	}

	// 消息推送
	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void init() {
		JPushInterface.init(getApplicationContext());
	}

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.newgame.sdk.yyaost.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
		// if (null != msgText) {
		// msgText.setText(msg);
		// msgText.setVisibility(android.view.View.VISIBLE);
		// }
	}

	public void settag(String uid) {
		String tag = uid.trim();
		Set<String> tagSet = new LinkedHashSet<String>();
		tagSet.add(tag);
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
	}

	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i("JPush", logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i("JPush", logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_TAGS, tags),
							1000 * 60);
				} else {
					Log.i("JPush", "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e("JPush", logs);
			}

			// ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				// Log.d("JPush", "Set alias in handler.");
				// JPushInterface.setAliasAndTags(getApplicationContext(),
				// (String) msg.obj, null, mAliasCallback);
				break;

			case MSG_SET_TAGS:
				Log.d("JPush", "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i("JPush", "Unhandled msg - " + msg.what);
			}
		}
	};
}