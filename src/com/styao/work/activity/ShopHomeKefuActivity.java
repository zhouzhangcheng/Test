package com.styao.work.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jock.tbshopcar.utils.LoadingUtils;
import com.jock.tbshopcar.utils.TextViewUtils;
import com.jock.tbshopcar.utils.Urls;
import com.jock.tbshopcar.utils.VolleyTools;
import com.newgame.sdk.yyaost.ExampleApplication;
import com.newgame.sdk.yyaost.R;
import com.styao.work.BaseActivity;
import com.styao.work.bean.ShopDescData;

public class ShopHomeKefuActivity extends BaseActivity implements
		OnClickListener {
	private TextView shopName, shopCompanyName, shopPhone, shopQQ, shopAddr,
			shopSellCount, shopContacts, shopDesc, shopCollection;
	private View shopCollectionImage;
	private ShopDescData shopData;
	private boolean isLogin;
	private int storeid;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String str = (String) msg.obj;
				Gson gson = new Gson();
				shopData = gson.fromJson(str, ShopDescData.class);
				loadData();
				LoadingUtils.closeLoading(ShopHomeKefuActivity.this);
				break;
			case -1:
				LoadingUtils.closeLoading(ShopHomeKefuActivity.this);
				break;

			default:
				break;
			}
		};
	};
	private VolleyTools volleyTools;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_shophome_kefu);
		initView();
		isLogin = getIntent().getBooleanExtra("isLogin", false);
		volleyTools = new VolleyTools(handler);
		storeid = getIntent().getIntExtra("storeid", 0);
		volleyTools.StringRequest(Urls.StoreIntroduction + storeid, this, 2,
				null, 1);
		LoadingUtils.showInitLoading(this);
	}

	protected void loadData() {
		if (shopData != null) {
			TextViewUtils.setText(shopName, shopData.getStoreInfo().getName());
			TextViewUtils.setText(shopCompanyName, shopData.getStoreEname());
			TextViewUtils.setText(shopPhone, shopData.getMobile());
			TextViewUtils.setText(shopSellCount, shopData.getProductNum(),
					"商品数量：");
			TextViewUtils.setText(shopQQ, shopData.getQq());
			TextViewUtils.setText(shopDesc, shopData.getStoreInfo()
					.getDescription());
			TextViewUtils.setText(shopAddr, shopData.getStoreKeeperInfo()
					.getAddress());
			TextViewUtils.setText(shopContacts, shopData.getStoreKeeperInfo()
					.getName());
			if (shopData.isFavoriteStore()) {
				shopCollection.setText("已关注");
			} else {
				shopCollection.setText("未关注");
			}
			shopCollectionImage.setSelected(shopData.isFavoriteStore());
			shopCollection.setSelected(shopData.isFavoriteStore());
		}
	}

	private void initView() {
		shopName = (TextView) findViewById(R.id.shop_name);
		shopCompanyName = (TextView) findViewById(R.id.shop_company_name);
		shopPhone = (TextView) findViewById(R.id.shop_phone);
		shopQQ = (TextView) findViewById(R.id.shop_qq);
		shopAddr = (TextView) findViewById(R.id.shop_address);
		shopSellCount = (TextView) findViewById(R.id.shop_sell_count);
		shopContacts = (TextView) findViewById(R.id.shop_contacts);
		shopDesc = (TextView) findViewById(R.id.shop_desc);
		shopCollection = (TextView) findViewById(R.id.shop_home_collection_tv);
		shopCollectionImage = findViewById(R.id.shop_home_collection);
		findViewById(R.id.shop_home_collection_linear).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		shopQQ.setOnClickListener(this);
		shopPhone.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.shop_home_collection_linear:
			if (shopData != null && isLogin) {
				if (shopData.isFavoriteStore()) { // 取消收藏
					volleyTools.StringRequest(Urls.DelFavoriteStore + storeid,
							this, 2, null, 2);
					shopCollectionImage.setSelected(false);
					shopData.setFavoriteStore(false);
					shopCollection.setText("未关注");
				} else { // 收藏
					volleyTools
							.StringRequest(Urls.AddStoreToFavorite + storeid,
									this, 2, null, 3);
					shopCollectionImage.setSelected(true);
					shopData.setFavoriteStore(true);
					shopCollection.setText("已关注");
				}
			} else {
				isGoToLogin();
			}
			break;
		case R.id.shop_qq:
			if (shopData != null) {
				if (isQQClientAvailable(this)) {
					try {
						String url = "mqqwpa://im/chat?chat_type=wpa&uin="
								+ shopData.getQq();
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse(url)));
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(this, "打开qq客户端失败", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(this, "检查到您未安装QQ，请先下载安装！", Toast.LENGTH_LONG)
							.show();
				}

			}
			break;
		case R.id.shop_phone:
			if (shopData != null) {
				toGoCall(shopData.getMobile());
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 判断qq是否可用
	 * 
	 * @param context
	 * @return
	 */
	private boolean isQQClientAvailable(Context context) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				if (pn.equals("com.tencent.mobileqq")) {
					return true;
				}
			}
		}
		return false;
	}

	private void isGoToLogin() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
//		builder.setTitle("提示"); // 设置标题
//		builder.setMessage("是否去登录?"); // 设置内容
//		builder.setIcon(R.drawable.ic_launcher);// 设置图标，图片id即可
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss(); // 关闭dialog
//
//					}
//				});
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//		// 参数都设置完成了，创建并显示出来
//		builder.create().show();
		Toast.makeText(ExampleApplication.getContext(), "请登录", Toast.LENGTH_SHORT).show();
	}

	private void toGoCall(final String phone) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("提示"); // 设置标题
		builder.setMessage("是否拨打" + phone); // 设置内容
		builder.setIcon(R.drawable.ic_launcher);// 设置图标，图片id即可
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 关闭dialog
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + phone));
						// 开启系统拨号器
						startActivity(intent);
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		// 参数都设置完成了，创建并显示出来
		builder.create().show();
	}
}
