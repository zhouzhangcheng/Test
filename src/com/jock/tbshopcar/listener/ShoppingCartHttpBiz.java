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
import com.jock.tbshopcar.utils.JsonReponseHandler;
import com.jock.tbshopcar.utils.ToastHelper;
import com.newgame.sdk.yyaost.R.string;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class ShoppingCartHttpBiz {

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
			String s = ShoppingCartHttpBiz.readJson(is);
			callback.handleResponse(new JSONObject(s), 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static List<ShoppingCartBean1> handleOrderList(JSONObject response,
			int errCode, String cookie) {
		List<ShoppingCartBean1> list = null;
		list = new ArrayList<ShoppingCartBean1>();
		if (isSuccess(response, errCode)) {
			try {
				JSONObject json = new JSONObject(response.getString("list"));
				ShoppingCartBean1 info = new ShoppingCartBean1();
				info.setishasfinish(0);
				info.setoldCookie(cookie);
				info.setTotalCount(json.getString("TotalCount"));
				info.setProductAmount(json.getString("ProductAmount"));
				info.setFullCut(json.getString("FullCut"));
				info.setOrderAmount(json.getString("OrderAmount"));
				info.setIsCheckAll(json.getString("IsCheckAll"));
				JSONArray jsonStoreCartList = json
						.getJSONArray("StoreCartList");
				ArrayList<ShoppingCartBean1.StoreCartList> infocartlist = new ArrayList<ShoppingCartBean1.StoreCartList>();
				for (int i = 0; i < jsonStoreCartList.length(); i++) {
					JSONObject san = jsonStoreCartList.getJSONObject(i);
					ShoppingCartBean1.StoreCartList infocartlistinfo = new ShoppingCartBean1.StoreCartList();
					infocartlistinfo.setIsSelected(Boolean.parseBoolean(san
							.getString("IsSelected")));
					infocartlistinfo.setfullCut(san.getString("fullCut"));
					infocartlistinfo.setproductAmount(san
							.getString("productAmount"));
					infocartlistinfo.setOrderAmount(san
							.getString("OrderAmount"));
					// 店铺信息
					JSONObject jsonstoreinfo = new JSONObject(
							san.getString("StoreInfo"));
					ArrayList<ShoppingCartBean1.StoreCartList.StoreInfo> StoreInfolist = new ArrayList<ShoppingCartBean1.StoreCartList.StoreInfo>();
					ShoppingCartBean1.StoreCartList.StoreInfo storeinfos = new ShoppingCartBean1.StoreCartList.StoreInfo();
					storeinfos.setStoreId(jsonstoreinfo.getString("StoreId"));
					storeinfos.setName(jsonstoreinfo.getString("Name"));
					storeinfos.setLowestdeliveryAmount(jsonstoreinfo
							.getString("LowestdeliveryAmount"));
					storeinfos.setLowestFreeShippingAmount(jsonstoreinfo
							.getString("LowestFreeShippingAmount"));
					storeinfos.setDefaultShipFee(jsonstoreinfo
							.getString("DefaultShipFee"));
					StoreInfolist.add(storeinfos);
					infocartlistinfo.setStoreInfo(StoreInfolist);
					// 第二级商品列表
					JSONArray jsonStoreProductList = san
							.getJSONArray("CartProductList");
					ArrayList<ShoppingCartBean1.StoreCartList.CartProductList> infoproductlist = new ArrayList<ShoppingCartBean1.StoreCartList.CartProductList>();
					for (int j = 0; j < jsonStoreProductList.length(); j++) {
						JSONObject three = jsonStoreProductList
								.getJSONObject(j);
						ShoppingCartBean1.StoreCartList.CartProductList infoproduct = new ShoppingCartBean1.StoreCartList.CartProductList();
						// 第三级商品信息
						ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo> OrderProductInfolist = new ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo>();
						for (int k = 0; k < 1; k++) {
							// JSONObject four =
							// jsonproductinfolist.getJSONObject(k);
							JSONObject four = new JSONObject(
									three.getString("OrderProductInfo"));
							ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo Info3 = new ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo();
							Info3.setRecordId(four.getString("RecordId"));
							Info3.setOid(four.getString("Oid"));
							Info3.setDrugsBase_ProName(four.getString("Name"));
							Info3.setUid(four.getString("Uid"));
							Info3.setPid(four.getString("Pid"));
							Info3.setName(four.getString("Name"));
							Info3.setDiscountPrice(four
									.getString("DiscountPrice"));
							Info3.setShopPrice(four.getString("ShopPrice"));
							Info3.setBuyCount(four.getString("BuyCount"));
							Info3.setDrugsBase_Specification(four
									.getString("DrugsBase_Specification"));
							Info3.setDrugsBase_Manufacturer(four
									.getString("DrugsBase_Manufacturer"));
							Info3.setDrugsBase_ApprovalNumber(four
									.getString("DrugsBase_ApprovalNumber"));
							Info3.setGoods_Package_ID(four
									.getString("Goods_Package_ID"));
							Info3.setSmallImageUrl(four
									.getString("SmallImageUrl"));
							Info3.setSellType(four.getString("SellType"));
							Info3.setGoods_Pcs(four.getString("Product_Pcs"));
							Info3.setGoods_Pcs_Small(four
									.getString("Product_Pcs_Small"));
							Info3.setMinBuyNum(four.getString("MinBuyNum"));
							Info3.setGoods_Unit(four.getString("Goods_Unit"));
							Info3.setiskong(four.getString("iskong"));
							Info3.setpmid(four.getString("pmid"));
							Info3.setaddpricebuyid(four
									.getString("addpricebuyid"));
							Info3.setaddpricebuypernum(four
									.getString("addpricebuypernum"));
							Info3.setaddpricebuynum(four
									.getString("addpricebuynum"));
							Info3.setaddpricebuycoast(four
									.getString("addpricebuycoast"));
							Info3.setstock(four.getString("stock"));
							if (Integer.parseInt(four.getString("IsSelect")) == 1) {
								Info3.setIsSelect(true);
							} else {
								Info3.setIsSelect(false);
							}
							// 加价购默认关闭
							if (Double.valueOf(four
									.getString("addpricebuyid")) > 0
									&& (Double.valueOf(four
											.getString("addpricebuypernum")) + Double.valueOf(four
													.getString("addpricebuynum"))) > 0) {
								Info3.setaddpricebuystate(1);
							} else {
								Info3.setaddpricebuystate(0);
							}
							//非标品袋装量
							Info3.setBagCount(four.getString("BagCount"));
							// 第四级加价购模型
							ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addpricebuymodel> addpricebuymodellist = new ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addpricebuymodel>();
							JSONObject five = new JSONObject(
									four.getString("addpricebuymodel"));
							ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addpricebuymodel Info4 = new ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addpricebuymodel();
							Info4.setfirstpid(five.getString("firstpid"));
							Info4.setsecondpid(five.getString("secondpid"));
							Info4.setaddPrice(five.getString("addPrice"));
							Info4.setaddPriceType(five
									.getString("addPriceType"));
							Info4.setfirstProudctStartNum(five
									.getString("firstProudctStartNum"));
							Info4.setfirstProudctPerNum(five
									.getString("firstProudctPerNum"));
							Info4.setsecondProudctNum(five
									.getString("secondProudctNum"));
							Info4.setpmid(five.getString("pmid"));
							addpricebuymodellist.add(Info4);
							// 加价购商品
							ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addproduct> addproductlist = new ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addproduct>();
							String addproductstr = four.getString("addproduct");
							ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addproduct addproductinfo = new ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.addproduct();
//							Log.v("addproduct", addproductstr);
							if (Integer.parseInt(five.getString("pmid")) <= 0) {
								addproductinfo.setName("");
								addproductinfo.setDrugsBase_Manufacturer("");
								addproductinfo.setDrugsBase_Specification("");
								addproductinfo.setSmallImageUrl("");
								addproductinfo.setGoods_Unit("");
								addproductinfo.setstock("");
								addproductinfo.setBagCount("");
							} else {
								JSONObject jsonaddproduct = new JSONObject(
										four.getString("addproduct"));

								addproductinfo.setName(jsonaddproduct
										.getString("Name"));
								addproductinfo
										.setDrugsBase_Manufacturer(jsonaddproduct
												.getString("DrugsBase_Manufacturer"));
								addproductinfo
										.setDrugsBase_Specification(jsonaddproduct
												.getString("DrugsBase_Specification"));
								addproductinfo.setSmallImageUrl(jsonaddproduct
										.getString("SmallImageUrl"));
								addproductinfo.setGoods_Unit(jsonaddproduct
										.getString("Goods_Unit"));
								addproductinfo.setstock(jsonaddproduct
										.getString("stock"));
								addproductinfo.setBagCount(jsonaddproduct
										.getString("BagCount"));
							}
							addproductlist.add(addproductinfo);
							Info3.setaddproduct(addproductlist);
							Info3.setaddpricebuymodel(addpricebuymodellist);
							//specialproduct
							ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.specialpricemodel> specialpricemodel=new ArrayList<ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.specialpricemodel>();
							if(four.toString().contains("specialpricemodel"))//specialpricemodel
							{
								ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.specialpricemodel specialpriceinfo=new ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo.specialpricemodel();
								JSONObject tejia = new JSONObject(
										four.getString("specialpricemodel"));
								if(tejia.toString().contains("pid"))
								{
									specialpriceinfo.setpid(Integer.parseInt(tejia.getString("pid")));
									specialpriceinfo.sethasbuycount(tejia.getString("buycount"));
									specialpriceinfo.setlimittype(Integer.parseInt(tejia.getString("limittype")));
									specialpriceinfo.setlimitnumber(tejia.getString("limitnumber"));
									specialpriceinfo.setspeprice(tejia.getString("speprice"));
									specialpriceinfo.setstarttime(tejia.getString("starttime"));
									specialpriceinfo.setendtime(tejia.getString("endtime"));
									specialpriceinfo.setoldprice(Info3.getShopPrice());
									specialpricemodel.add(specialpriceinfo);
								}
							}
							Info3.setspecialpricemodel(specialpricemodel);
							OrderProductInfolist.add(Info3);
						}
						infoproduct.setOrderProductInfo(OrderProductInfolist);
						infoproductlist.add(infoproduct);
					}
					infocartlistinfo.setCartProductList(infoproductlist);
					infocartlist.add(infocartlistinfo);
				}

				info.setStoreCartList(infocartlist);
				list.add(info);
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
			status = response.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if (errCode == 0 && response != null
				&& status.toLowerCase().equals("ok")) {
			return true;
		} else {

			String message = "";

			if (message == null || "".equals(message)) {
				message = "对不起，请求失败";
			}
			if (status.toLowerCase().equals("nologin")) {
				message = "你还没有登录";
			}
			ToastHelper.getInstance()._toast(message);
		}

		return false;
	}

}
