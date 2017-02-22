package com.styao.work;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.newgame.sdk.yyaost.R;

public class BaseActivity extends FragmentActivity {

	public void ImageRequest(final ImageView myImageView, String url) {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		ImageRequest request = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						// 给imageView设置图片
						myImageView.setImageBitmap(response);
					}
				}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// 设置一张错误的图片，临时用ic_launcher代替
						myImageView.setImageResource(R.drawable.ic_launcher);
					}
				});

		requestQueue.add(request);
	}
}
