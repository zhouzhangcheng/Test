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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.entity.WisdomBean;
import com.jock.tbshopcar.entity.WisdomBeanHome;

/**
 * Created by zhourongsheng on 2016/12/5.
 */
public class Wisdom301HttpBiz {

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

	// order 0:字母排序
	public static List<WisdomBean> handleOrderList(JSONObject response,
			int errCode, String cookie, int order) {
		List<WisdomBean> list = null;
		list = new ArrayList<WisdomBean>();
		if (isSuccess(response, errCode)) {
			try {
				JSONArray json = response.getJSONArray("list");
				ArrayList<WisdomBeanHome.Goods> infogoodslist = new ArrayList<WisdomBeanHome.Goods>();
				int count = 0;
				for (int i = 0; i < json.length(); i++) {
					JSONObject san = json.getJSONObject(i);
					WisdomBeanHome.Goods goodsinfo = new WisdomBeanHome.Goods();
					goodsinfo.setid(Integer.parseInt(san.getString("id")));
					goodsinfo.setpsn(san.getString("psn"));
					goodsinfo.setGoods_Package_ID(Integer.parseInt(san
							.getString("Goods_Package_ID")));
					goodsinfo.setPreChar(san.getString("PreChar"));
					goodsinfo.setDrugsBase_Manufacturer(san
							.getString("DrugsBase_Manufacturer"));
					goodsinfo.setDrugsBase_DrugName(san
							.getString("DrugsBase_DrugName"));
					goodsinfo.setDrugsBase_Specification(san
							.getString("DrugsBase_Specification"));
					goodsinfo.setstock(Double.valueOf(san.getString("stock")));
					goodsinfo
							.setLastTimeString(san.getString("LastTimeString"));
					goodsinfo.setHistoryPrice(san.getString("HistoryPrice"));
					goodsinfo.setSalesVolume(san.getString("SalesVolume"));
					goodsinfo.setminPrice(san.getString("minPrice"));
					goodsinfo.setmaxPrice(san.getString("maxPrice"));
					goodsinfo.setpriority(san.getString("priority"));
					goodsinfo.setbuyCount(san.getString("buyCount"));
					goodsinfo.setBarcode(san.getString("Barcode"));
					goodsinfo.setBuyNumList(toList(san.getJSONArray("buyNumList")));
					infogoodslist.add(goodsinfo);
					count++;
				}

				if (infogoodslist.size() > 0) {
					int count1 = 0;
					if (order == 0)// 字母排序方式处理
					{
						WisdomBean info = new WisdomBean();
						String firstzimu = "";
						ArrayList<WisdomBean.FirstZiMu> zimulist = new ArrayList<WisdomBean.FirstZiMu>();
						for (int i = 0; i < infogoodslist.size(); i++) {
							firstzimu = infogoodslist.get(i).getPreChar()
									.trim().toUpperCase();
							WisdomBean.FirstZiMu zimulistinfo = new WisdomBean.FirstZiMu();
							zimulistinfo.setPreChar(firstzimu);
							ArrayList<WisdomBean.FirstZiMu.Goods> goodslist = new ArrayList<WisdomBean.FirstZiMu.Goods>();
							for (int j = i; j < infogoodslist.size(); j++) {
								WisdomBean.FirstZiMu.Goods goodslistinfo = new WisdomBean.FirstZiMu.Goods();
								String zimu = infogoodslist.get(j).getPreChar();
								if (infogoodslist.get(j).getPreChar()
										.toUpperCase().charAt(0) == firstzimu
										.charAt(0)) {
									goodslistinfo.setid(infogoodslist.get(j)
											.getid());
									goodslistinfo.setpsn(infogoodslist.get(j)
											.getpsn());
									goodslistinfo
											.setGoods_Package_ID(infogoodslist
													.get(j)
													.getGoods_Package_ID());
									goodslistinfo.setPreChar(infogoodslist.get(
											j).getPreChar());
									goodslistinfo
											.setDrugsBase_Manufacturer(infogoodslist
													.get(j)
													.getDrugsBase_Manufacturer());
									goodslistinfo
											.setDrugsBase_DrugName(infogoodslist
													.get(j)
													.getDrugsBase_DrugName());
									goodslistinfo
											.setDrugsBase_Specification(infogoodslist
													.get(j)
													.getDrugsBase_Specification());
									goodslistinfo.setstock(infogoodslist.get(j)
											.getstock());
									goodslistinfo
											.setLastTimeString(infogoodslist
													.get(j).getLastTimeString());
									goodslistinfo.setHistoryPrice(infogoodslist
											.get(j).getHistoryPrice());
									goodslistinfo.setSalesVolume(infogoodslist
											.get(j).getSalesVolume());
									goodslistinfo.setminPrice(infogoodslist
											.get(j).getminPrice());
									goodslistinfo.setmaxPrice(infogoodslist
											.get(j).getmaxPrice());
									goodslistinfo.setpriority(infogoodslist
											.get(j).getpriority());
									goodslistinfo.setbuyCount(infogoodslist
											.get(j).getbuyCount());
									goodslistinfo.setBarcode(infogoodslist.get(
											j).getBarcode());
									goodslistinfo.setBuyNumList(infogoodslist.get(j)
											.getBuyNumList());
									goodslist.add(goodslistinfo);
									count1++;
									if (count1 >= count) {
										break;
									}
								} else {
									i = j;
									i--;
									break;
								}
							}

							zimulistinfo.setGoods(goodslist);
							zimulist.add(zimulistinfo);
							if (count1 >= count) {
								break;
							}
						}
						info.setoldCookie(cookie);
						info.setFirstZiMu(zimulist);
						list.add(info);
					} else if (order == 1)// 库存
					{
						// Collections.sort(infogoodslist,new
						// Comparator<WisdomBeanHome.Goods>(){
						// public int compare(WisdomBeanHome.Goods arg0,
						// WisdomBeanHome.Goods arg1) {
						// if(arg0.getstock()>arg1.getstock())
						// {
						// return 1;
						// }else if(arg0.getstock()<arg1.getstock())
						// {
						// return -1;
						// }else
						// {
						// return 0;
						// }
						// }
						// });

						WisdomBean info = new WisdomBean();
						ArrayList<WisdomBean.FirstZiMu> zimulist = new ArrayList<WisdomBean.FirstZiMu>();
						for (int i = 0; i < 1; i++) {
							WisdomBean.FirstZiMu zimulistinfo = new WisdomBean.FirstZiMu();
							zimulistinfo.setPreChar("stock");
							ArrayList<WisdomBean.FirstZiMu.Goods> goodslist = new ArrayList<WisdomBean.FirstZiMu.Goods>();
							for (int j = 0; j < infogoodslist.size(); j++) {
								WisdomBean.FirstZiMu.Goods goodslistinfo = new WisdomBean.FirstZiMu.Goods();

								goodslistinfo.setid(infogoodslist.get(j)
										.getid());
								goodslistinfo.setpsn(infogoodslist.get(j)
										.getpsn());
								goodslistinfo.setGoods_Package_ID(infogoodslist
										.get(j).getGoods_Package_ID());
								goodslistinfo.setPreChar(infogoodslist.get(j)
										.getPreChar());
								goodslistinfo
										.setDrugsBase_Manufacturer(infogoodslist
												.get(j)
												.getDrugsBase_Manufacturer());
								goodslistinfo
										.setDrugsBase_DrugName(infogoodslist
												.get(j).getDrugsBase_DrugName());
								goodslistinfo
										.setDrugsBase_Specification(infogoodslist
												.get(j)
												.getDrugsBase_Specification());
								goodslistinfo.setstock(infogoodslist.get(j)
										.getstock());
								goodslistinfo.setLastTimeString(infogoodslist
										.get(j).getLastTimeString());
								goodslistinfo.setHistoryPrice(infogoodslist
										.get(j).getHistoryPrice());
								goodslistinfo.setSalesVolume(infogoodslist.get(
										j).getSalesVolume());
								goodslistinfo.setminPrice(infogoodslist.get(j)
										.getminPrice());
								goodslistinfo.setmaxPrice(infogoodslist.get(j)
										.getmaxPrice());
								goodslistinfo.setpriority(infogoodslist.get(j)
										.getpriority());
								goodslistinfo.setbuyCount(infogoodslist.get(j)
										.getbuyCount());
								goodslistinfo.setBuyNumList(infogoodslist.get(j)
										.getBuyNumList());
								goodslist.add(goodslistinfo);
							}

							zimulistinfo.setGoods(goodslist);
							zimulist.add(zimulistinfo);
						}
						info.setoldCookie(cookie);
						info.setFirstZiMu(zimulist);
						list.add(info);
					} else if (order == 2)// 紧急程度
					{
						// Collections.sort(infogoodslist,new
						// Comparator<WisdomBeanHome.Goods>(){
						// public int compare(WisdomBeanHome.Goods arg0,
						// WisdomBeanHome.Goods arg1) {
						// if(Double.valueOf(arg0.getpriority())>Double.valueOf(arg1.getpriority()))
						// {
						// return -1;
						// }else
						// if(Double.valueOf(arg0.getpriority())<Double.valueOf(arg1.getpriority()))
						// {
						// return 1;
						// }else
						// {
						// return 0;
						// }
						// }
						// });

						WisdomBean info = new WisdomBean();
						ArrayList<WisdomBean.FirstZiMu> zimulist = new ArrayList<WisdomBean.FirstZiMu>();

						info.setmobile(response.getString("mobile"));
						info.setmatchingCount(response
								.getString("matchingCount"));
						info.setnotMatchingCount(response
								.getString("notMatchingCount"));
						info.setnotPurchaseCount(response
								.getString("notPurchaseCount"));
						for (int i = 0; i < 1; i++) {
							WisdomBean.FirstZiMu zimulistinfo = new WisdomBean.FirstZiMu();
							zimulistinfo.setPreChar("jinji");
							ArrayList<WisdomBean.FirstZiMu.Goods> goodslist = new ArrayList<WisdomBean.FirstZiMu.Goods>();
							for (int j = 0; j < infogoodslist.size(); j++) {
								WisdomBean.FirstZiMu.Goods goodslistinfo = new WisdomBean.FirstZiMu.Goods();

								goodslistinfo.setid(infogoodslist.get(j)
										.getid());
								goodslistinfo.setpsn(infogoodslist.get(j)
										.getpsn());
								goodslistinfo.setGoods_Package_ID(infogoodslist
										.get(j).getGoods_Package_ID());
								goodslistinfo.setPreChar(infogoodslist.get(j)
										.getPreChar());
								goodslistinfo
										.setDrugsBase_Manufacturer(infogoodslist
												.get(j)
												.getDrugsBase_Manufacturer());
								goodslistinfo
										.setDrugsBase_DrugName(infogoodslist
												.get(j).getDrugsBase_DrugName());
								goodslistinfo
										.setDrugsBase_Specification(infogoodslist
												.get(j)
												.getDrugsBase_Specification());
								goodslistinfo.setstock(infogoodslist.get(j)
										.getstock());
								goodslistinfo.setLastTimeString(infogoodslist
										.get(j).getLastTimeString());
								goodslistinfo.setHistoryPrice(infogoodslist
										.get(j).getHistoryPrice());
								goodslistinfo.setSalesVolume(infogoodslist.get(
										j).getSalesVolume());
								goodslistinfo.setminPrice(infogoodslist.get(j)
										.getminPrice());
								goodslistinfo.setmaxPrice(infogoodslist.get(j)
										.getmaxPrice());
								goodslistinfo.setpriority(infogoodslist.get(j)
										.getpriority());
								goodslistinfo.setbuyCount(infogoodslist.get(j)
										.getbuyCount());
								goodslistinfo.setBuyNumList(infogoodslist.get(j)
										.getBuyNumList());
								goodslist.add(goodslistinfo);
							}

							zimulistinfo.setGoods(goodslist);
							zimulist.add(zimulistinfo);
						}
						info.setoldCookie(cookie);
						info.setFirstZiMu(zimulist);
						list.add(info);
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("JSONException", e.getMessage());
				return list;
			}
		} else {
			try {
				if (response.toString().contains("matchingCount")) {
					WisdomBean info = new WisdomBean();
					info.setmatchingCount(response.getString("matchingCount"));
					info.setmobile(response.getString("mobile"));
					info.setnotMatchingCount(response
							.getString("notMatchingCount"));
					info.setnotPurchaseCount(response
							.getString("notPurchaseCount"));
					info.setoldCookie(cookie);
					list.add(info);
				}
			} catch (Exception ex) {

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
		if (status.length() > 10) {
			return true;
		}

		return false;
	}

	private static List<String> toList(JSONArray jsonArr) {
		List<String> list = new ArrayList<String>();
		if (jsonArr != null) {
			for (int i = 0; i < jsonArr.length(); i++) {
				try {
					list.add(jsonArr.getString(i));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return list;
		}
		return list;
	}
}
