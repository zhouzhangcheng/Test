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
import com.jock.tbshopcar.entity.ShoppingCartBean1.StoreCartList.StoreInfo;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.entity.WisdomBean302;
import com.jock.tbshopcar.entity.WisdomBean303;
import com.jock.tbshopcar.entity.WisdomBeanHome;
import com.jock.tbshopcar.utils.JsonReponseHandler;
import com.jock.tbshopcar.utils.ToastHelper;
import com.newgame.sdk.yyaost.R.string;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class Wisdom303HttpBiz {

	public static void setcookie(String cookie) {
		List<ShoppingCartBean1> list = null;
		list = new ArrayList<ShoppingCartBean1>();
		list.get(0).setoldCookie(cookie);
	}

	// 从给定位置读取Json文件
	public static String readJson(InputStream is) {
		// 从给定位置获取文件
		// File file = new File(path);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
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

	// 给定路径与Json文件，存储到硬盘
	public static void writeJson(String path, Object json, String fileName) {
		BufferedWriter writer = null;
		File file = new File(path + fileName + ".json");
		// 如果文件不存在，则新建一个
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 写入
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
		// System.out.println("文件写入成功！");
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
	//order 0:字母排序
	public static List<WisdomBean303> handleOrderList(JSONObject response,
			int errCode, String cookie,int order) {
		List<WisdomBean303> list = null;
		list = new ArrayList<WisdomBean303>();
		if (isSuccess(response, errCode)) {
			try {
				JSONArray json = response.getJSONArray("list");
				WisdomBean303 listinfo=new WisdomBean303();
				listinfo.setoldCookie(cookie);
				ArrayList<WisdomBean303.StoreList> storelist = new ArrayList<WisdomBean303.StoreList>();
				for (int i = 0; i < json.length(); i++) {
					JSONObject san = json.getJSONObject(i);
					WisdomBean303.StoreList info = new WisdomBean303.StoreList();
					info.setMoneyFreePostage(san.getString("MoneyFreePostage"));
					info.setMoneySend(san.getString("MoneySend"));
					info.setPostage(san.getString("Postage"));
					info.setstore_Id(Integer.parseInt(san.getString("store_Id")));
					String name="";
					info.setstore_Name(san.getString("store_Name"));
					info.setSurplusmoney(san.getString("Surplusmoney"));
					JSONArray json1=san.getJSONArray("li");
					ArrayList<WisdomBean303.StoreList.ProductsList> productlist=new ArrayList<WisdomBean303.StoreList.ProductsList>();
					for(int j=0;j<json1.length();j++)
					{
						JSONObject san1=json1.getJSONObject(j);
						WisdomBean303.StoreList.ProductsList info1=new WisdomBean303.StoreList.ProductsList();
						info1.setbuyCount(san1.getString("buyCount"));
						info1.setDrugsBase_DrugName(san1.getString("DrugsBase_DrugName"));
						info1.setDrugsBase_Manufacturer(san1.getString("DrugsBase_Manufacturer"));
						info1.setDrugsBase_Specification(san1.getString("DrugsBase_Specification"));
						info1.setGoods_Package_ID(Integer.parseInt(san1.getString("Goods_Package_ID")));
						info1.setID(Integer.parseInt(san1.getString("id")));
						info1.setminBuy(san1.getString("minBuy"));
						info1.setpid(san1.getString("pid"));
						info1.setPrice(san1.getString("Price"));
						info1.setstock(san1.getString("stock"));
						info1.setsxrq(san1.getString("sxrq"));
						info1.setsellType(san1.getString("sellType"));
						info1.setProduct_Pcs_Small(san1.getString("Product_Pcs_Small"));
						info1.setProduct_Pcs(san1.getString("Product_Pcs"));
						productlist.add(info1);
					}
					
					info.setProductsList(productlist);
					storelist.add(info);
				}
				listinfo.setStoreList(storelist);
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
	 * 是否成功
	 * 
	 * @param response
	 * @param errCode
	 * @param isShowErrorInfo
	 *            是否显示98型错误
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
