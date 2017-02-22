package com.st.untill;

import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class VolleyHttp {

	private Context context;
	private Handler handler;
	private Map<String, String> maps;
	private String url;

	StringRequest stringRequest1 = new StringRequest(Method.GET, url,
			new Listener<String>() {
				@Override
				public void onResponse(String response) {
					// TODO Auto-generated method stub
					Message msg = handler.obtainMessage();
					msg.obj = response;
					handler.sendMessage(msg);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(context, error.toString(),
							Toast.LENGTH_SHORT).show();
				}

			}) {
		@Override
		protected Map<String, String> getParams() throws AuthFailureError {
			Map<String, String> map = maps;
			return map;
		};
	};
}
