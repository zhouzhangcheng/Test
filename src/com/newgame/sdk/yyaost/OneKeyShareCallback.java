/*
 * 官网地站:http://www.ShareSDK.cn
 * �?术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第�?时间通过微信将版本更新内容推送给您�?�如果使用过程中有任何问题，也可以�?�过微信与我们取得联系，我们将会�?24小时内给予回复）
 *
 * Copyright (c) 2013�? ShareSDK.cn. All rights reserved.
 */
package com.newgame.sdk.yyaost;

import java.util.HashMap;

import android.util.Log;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class OneKeyShareCallback implements PlatformActionListener {
	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		Log.d(getClass().getSimpleName(), res.toString());
		// 在这里添加分享成功的处理代码
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// 在这里添加分享失败的处理代码
	}

	public void onCancel(Platform plat, int action) {
		// 在这里添加取消分享的处理代码
	}

}
