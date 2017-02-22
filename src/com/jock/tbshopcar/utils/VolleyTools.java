package com.jock.tbshopcar.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newgame.sdk.yyaost.ExampleApplication;
import com.newgame.sdk.yyaost.MainActivity;

public class VolleyTools {
	private Handler handler;
	private Map<String, String> mHeaders = new HashMap<String, String>();

	/**
	 * 
	 * @param httpurl
	 *            地址
	 * @param mode
	 *            1 表示post 2表示get 请求
	 * @param handler
	 *            回调
	 * @param values
	 *            请求参数
	 * @param context
	 *            context
	 */
	public VolleyTools(Handler handler) {
		this.handler = handler;
		mHeaders.put("Cookie", MainActivity.oldCookie);
	}

	public void StringRequest(String httpurl, Context context, int mode,
			final Map<String, String> values, final int flag) {
		int requesMode = Request.Method.GET;
		switch (mode) {
		case 1:
			requesMode = Request.Method.POST;
			break;
		case 2:
			requesMode = Request.Method.GET;
			break;
		default:
			requesMode = Request.Method.GET;
			break;
		}
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(requesMode, httpurl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Message msg = handler.obtainMessage();
						msg.obj = response;
						msg.what = flag;
						handler.sendMessage(msg);
						System.out.println("response~" + response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Message msg = handler.obtainMessage();
						msg.obj = error.toString();
						msg.what = -1;
						handler.sendMessage(msg);
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> map = values;
				return map;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return mHeaders;
			}
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue.add(stringRequest);
	}
}
