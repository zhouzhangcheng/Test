package com.jock.tbshopcar.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.entity.WisdomBean302;
import com.jock.tbshopcar.entity.WisdomBeanHome;
import com.jock.tbshopcar.utils.JsonReponseHandler;
import com.jock.tbshopcar.utils.ToastHelper;
import com.newgame.sdk.yyaost.R.string;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class Wisdom302HttpBiz {

	public static void setcookie(String cookie) {
		List<ShoppingCartBean1> list = null;
		list = new ArrayList<ShoppingCartBean1>();
		list.get(0).setoldCookie(cookie);
	}

	// �Ӹ���λ�ö�ȡJson�ļ�
	public static String readJson(InputStream is) {
		// �Ӹ���λ�û�ȡ�ļ�
		// File file = new File(path);
		BufferedReader reader = null;
		// ����ֵ,ʹ��StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			// ÿ�ζ�ȡ�ļ��Ļ���
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �ر��ļ���
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data.toString();
	}

	// ����·����Json�ļ����洢��Ӳ��
	public static void writeJson(String path, Object json, String fileName) {
		BufferedWriter writer = null;
		File file = new File(path + fileName + ".json");
		// ����ļ������ڣ����½�һ��
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// д��
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("�ļ�д��ɹ���");
	}

	public static void requestOrderList(Context context,
			ResponseCallBack callback) {
		try {
			InputStream is = context.getAssets().open("firm_order.json");
			String s = Wisdom301HttpBiz.readJson(is);
			callback.handleResponse(new JSONObject(s), 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	//order 0:��ĸ����
	public static List<WisdomBean302> handleOrderList(JSONObject response,
			int errCode, String cookie,int order) {
		List<WisdomBean302> list = null;
		list = new ArrayList<WisdomBean302>();
		if (isSuccess(response, errCode)) {
			try {
				JSONArray json = response.getJSONArray("list");
				WisdomBean302 listinfo=new WisdomBean302();
				listinfo.setoldCookie(cookie);
				ArrayList<WisdomBean302.SchemeName> schemeNamelist = new ArrayList<WisdomBean302.SchemeName>();
				for (int i = 0; i < json.length(); i++) {
					JSONObject san = json.getJSONObject(i);
					WisdomBean302.SchemeName info = new WisdomBean302.SchemeName();
					info.setEconomizeMoney(san.getString("EconomizeMoney"));
					info.setEconomizeTime(san.getString("EconomizeTime"));
					info.setmismatching(san.getString("mismatching"));
					info.setPostage(san.getString("Postage"));
					info.setSchemeName(san.getString("SchemeName"));
					info.setStoresCount(san.getString("StoresCount"));
					info.setSuperiorityNum(san.getString("SuperiorityNum"));
					info.setSurplusMoney(san.getString("SurplusMoney"));
					info.setID(i+1);
					schemeNamelist.add(info);
				}
				listinfo.setSchemeName(schemeNamelist);
				list.add(listinfo);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("JSONException", e.getMessage());
				return list;
			}
		}
		return list;
	}

	/**
	 * �Ƿ�ɹ�
	 * 
	 * @param response
	 * @param errCode
	 * @param isShowErrorInfo
	 *            �Ƿ���ʾ98�ʹ���
	 * @return
	 */
	public static boolean isSuccess(JSONObject response, int errCode) {
		String status = "ok";
		try {
			status = response.getString("list");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if(status.length()>10)
		{
			return true;
		}

		return false;
	}

}
