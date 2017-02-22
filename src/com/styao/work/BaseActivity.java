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
						// ��imageView����ͼƬ
						myImageView.setImageBitmap(response);
					}
				}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// ����һ�Ŵ����ͼƬ����ʱ��ic_launcher����
						myImageView.setImageResource(R.drawable.ic_launcher);
					}
				});

		requestQueue.add(request);
	}
}
