package com.jock.tbshopcar.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.util.Log;

import com.google.gson.Gson;

public class JsonReponseHandler
{

	/** listKey=pageEntity */
	public static <T> List<T> getListFromJsonWithPageEntity(Object response, Type type)
	{
		return getListFromJson(response, type, "StoreCartList");
	}

	/**
	 * �����������ص�JSONת��ΪList�����㣬
	 * 
	 * @param response
	 *            ��������HTTP��Ӧ��JSON���ݣ�֮������object����ϣ����msg.objֱ�Ӵ��͹���
	 * @param type
	 *            List���������ͣ���List<Comment>
	 * @param baseAdapter
	 *            ����ListView��������
	 * @param dialog
	 *            ���ȶԻ���
	 * @author �¸���, ����
	 */
	public static <T> List<T> getListFromJson(Object response, Type type, String listKey)
	{
		if (response == null)
		{
			return null;
		}
		JSONObject jo = null;
		if (response instanceof JSONObject)
		{
			jo = (JSONObject) response;
		} else if (response instanceof String)
		{
			try
			{
				jo = new JSONObject(response.toString());
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		List<T> list = null;
		try
		{
			// ȡlist
			if (jo != null && !jo.equals(""))
			{
				Log.v("jo", jo.toString());
				String json = String.valueOf(jo.getJSONArray(listKey));
				Gson gson = new Gson();
				if (!"null".equals(json))
				{// ����˿��ܷ���null
					list = gson.fromJson(json, type);
				}
				if (list == null)
				{
					list = new ArrayList<T>();
				}
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return list;
	}

}
