package com.newgame.sdk.yyaost;

import com.newgame.sdk.yyaost.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {
	public void onShare(Platform platform, ShareParams paramsToShare) {
		// 改写twitter分享内容中的text字段，否则会超长�?
		// 因为twitter会将图片地址当作文本的一部分去计算长�?
		if ("Twitter".equals(platform.getName())) {
			String text = platform.getContext().getString(R.string.share_content_short);
			paramsToShare.setText(text);
		}
	}
}
