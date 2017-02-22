package com.jock.tbshopcar.listener;

import java.sql.Ref;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.newgame.sdk.yyaost.R;
import com.jock.tbshopcar.dao.ShoppingCartDao;
import com.jock.tbshopcar.dao.WisdomSt301Dao;
import com.jock.tbshopcar.dao.WisdomSt301errorDao;
import com.jock.tbshopcar.dao.WisdomSt302Dao;
import com.jock.tbshopcar.dao.WisdomSt303Dao;
import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.ShoppingCartBean1;
import com.jock.tbshopcar.entity.ShoppingCartBean1.StoreCartList.CartProductList;
import com.jock.tbshopcar.entity.WisdomBean.FirstZiMu.Goods;
import com.jock.tbshopcar.entity.WisdomBean302;
import com.jock.tbshopcar.entity.WisdomBean303;
import com.jock.tbshopcar.entity.WisdomBean303.StoreList.ProductsList;
import com.jock.tbshopcar.utils.DecimalUtil;
import com.jock.tbshopcar.utils.ToastHelper;

public class ShoppingCartBiz {

	/**
	 * 选择全部，点下全部按钮，改变所有商品选中状态
	 */
	public static boolean selectAll(List<ShoppingCartBean1> list,
			boolean isSelectAll, ImageView ivCheck) {
		// isSelectAll = !isSelectAll;
		// ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
		// for (int i = 0; i < list.size(); i++) {
		// list.get(i).setIsGroupSelected(isSelectAll);
		// for (int j = 0; j < list.get(i).getGoods().size(); j++) {
		// list.get(i).getGoods().get(j).setIsChildSelected(isSelectAll);
		// }
		// }
		isSelectAll = !isSelectAll;
		ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
		for (int i = 0; i < list.get(0).getStoreCartList().size(); i++) {
			list.get(0).getStoreCartList().get(i).setIsSelected(isSelectAll);
			for (int j = 0; j < list.get(0).getStoreCartList().get(i)
					.getCartProductList().size(); j++) {
				list.get(0).getStoreCartList().get(i).getCartProductList()
						.get(j).getOrderProductInfo().get(0)
						.setIsSelect(isSelectAll);
			}
		}
		return isSelectAll;
	}

	/**
	 * 族内的所有组，是否都被选中，即全选
	 * 
	 * @param list
	 * @return
	 */
	private static boolean isSelectAllGroup(List<ShoppingCartBean1> list) {
		for (int i = 0; i < list.get(0).getStoreCartList().size(); i++) {
			// boolean isSelectGroup = list.get(i).isGroupSelected();
			boolean isSelectGroup = list.get(0).getStoreCartList().get(i)
					.getIsSelected();
			if (!isSelectGroup) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 组内所有子选项是否全部被选中
	 * 
	 * @param list
	 * @return
	 */
	private static boolean isSelectAllChild(
			List<ShoppingCartBean1.StoreCartList.CartProductList> list) {
		for (int i = 0; i < list.size(); i++) {
			boolean isSelectGroup = list.get(i).getOrderProductInfo().get(0)
					.getIsSelect();
			if (!isSelectGroup) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
	 * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
	 * 
	 * @param list
	 * @param groudPosition
	 * @param childPosition
	 * @return 是否选择全部
	 */
	public static boolean selectOne(List<ShoppingCartBean1> list,
			int groudPosition, int childPosition) {
		boolean isSelectAll;
		// boolean isSelectedOne =
		// !(list.get(groudPosition).getGoods().get(childPosition).isChildSelected());
		boolean isSelectedOne = !(list.get(0).getStoreCartList()
				.get(groudPosition).getCartProductList().get(childPosition)
				.getOrderProductInfo().get(0).getIsSelect());
		// list.get(groudPosition).getGoods().get(childPosition).setIsChildSelected(isSelectedOne);//单个图标的处理
		list.get(0).getStoreCartList().get(groudPosition).getCartProductList()
				.get(childPosition).getOrderProductInfo().get(0)
				.setIsSelect(isSelectedOne);
		// boolean isSelectCurrentGroup =
		// isSelectAllChild(list.get(groudPosition).getGoods());
		boolean isSelectCurrentGroup = isSelectAllChild(list.get(0)
				.getStoreCartList().get(groudPosition).getCartProductList());
		// list.get(groudPosition).setIsGroupSelected(isSelectCurrentGroup);//组图标的处理
		list.get(0).getStoreCartList().get(groudPosition)
				.setIsSelected(isSelectCurrentGroup);// 组图标的处理
		isSelectAll = isSelectAllGroup(list);
		return isSelectAll;
	}

	public static boolean selectGroup(List<ShoppingCartBean1> list,
			int groudPosition) {
		boolean isSelectAll;
		// boolean isSelected = !(list.get(groudPosition).isGroupSelected());
		boolean isSelected = !(list.get(0).getStoreCartList()
				.get(groudPosition).getIsSelected());
		// list.get(groudPosition).setIsGroupSelected(isSelected);
		list.get(0).getStoreCartList().get(groudPosition)
				.setIsSelected(isSelected);
		// for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
		// list.get(groudPosition).getGoods().get(i).setIsChildSelected(isSelected);
		// }
		for (int i = 0; i < list.get(0).getStoreCartList().get(groudPosition)
				.getCartProductList().size(); i++) {
			list.get(0).getStoreCartList().get(groudPosition)
					.getCartProductList().get(i).getOrderProductInfo().get(0)
					.setIsSelect(isSelected);
		}
		isSelectAll = isSelectAllGroup(list);
		return isSelectAll;
	}

	/**
	 * 勾与不勾选中选项
	 * 
	 * @param isSelect
	 *            原先状态
	 * @param ivCheck
	 * @return 是否勾上，之后状态
	 */
	public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
		if (isSelect) {
			ivCheck.setImageResource(R.drawable.ic_checked);
		} else {
			ivCheck.setImageResource(R.drawable.ic_uncheck);
		}
		return isSelect;
	}

	/** =====================上面是界面改动部分，下面是数据变化部分========================= */

	/**
	 * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
	 * 
	 * @return 0=选中的商品数量；1=选中的商品总价
	 */
	public static String[] getShoppingCount(List<ShoppingCartBean1> listGoods) {
		String[] infos = new String[2];
		String selectedCount = "0";
		String selectedMoney = "0";
		for (int i = 0; i < listGoods.get(0).getStoreCartList().size(); i++) {
			// 判断是否满足店铺发货金额
			if (getisfahuo(listGoods.get(0).getStoreCartList().get(i)
					.getCartProductList()) >= Double.valueOf(listGoods.get(0)
					.getStoreCartList().get(i).getStoreInfo().get(0)
					.getLowestdeliveryAmount())) {
				for (int j = 0; j < listGoods.get(0).getStoreCartList().get(i)
						.getCartProductList().size(); j++) {
					boolean isSelectd = listGoods.get(0).getStoreCartList()
							.get(i).getCartProductList().get(j)
							.getOrderProductInfo().get(0).getIsSelect();
					if (isSelectd) {
						String price = listGoods.get(0).getStoreCartList()
								.get(i).getCartProductList().get(j)
								.getOrderProductInfo().get(0).getShopPrice();
						String num = listGoods.get(0).getStoreCartList().get(i)
								.getCartProductList().get(j)
								.getOrderProductInfo().get(0).getBuyCount();
						String countMoney = DecimalUtil.multiply(price, num);
						selectedMoney = DecimalUtil.add(selectedMoney,
								countMoney);
						selectedCount = DecimalUtil.add(selectedCount, "1");
						// 判断加价购
						int addpricebuystate = listGoods.get(0)
								.getStoreCartList().get(i).getCartProductList()
								.get(j).getOrderProductInfo().get(0)
								.getaddpricebuystate();
						if (addpricebuystate > 0) {
							int addpricetype = Integer.parseInt(listGoods
									.get(0).getStoreCartList().get(i)
									.getCartProductList().get(j)
									.getOrderProductInfo().get(0)
									.getaddpricebuymodel().get(0)
									.getaddPriceType());
							String addprice = listGoods.get(0)
									.getStoreCartList().get(i)
									.getCartProductList().get(j)
									.getOrderProductInfo().get(0)
									.getaddpricebuymodel().get(0).getaddPrice();
							if (addpricetype == 0)// 满
							{
								selectedMoney = DecimalUtil.add(selectedMoney,
										addprice);
							} else {
								double addpernum = Double.valueOf(listGoods
										.get(0).getStoreCartList().get(i)
										.getCartProductList().get(j)
										.getOrderProductInfo().get(0)
										.getaddpricebuymodel().get(0)
										.getfirstProudctPerNum());
								selectedMoney = DecimalUtil
										.add(selectedMoney,
												DecimalUtil
														.multiply(
																addprice,
																(int) (Double
																		.valueOf(num) / addpernum)
																		+ ""));
							}
						}

					}
				}
			}
		}
		infos[0] = selectedCount;
		infos[1] = selectedMoney;
		return infos;
	}

	/**
	 * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
	 * 
	 * @return 0=选中的商品数量；1=选中的商品总价
	 */
	public static String[] getWisdomCount(List<WisdomBean303> listGoods) {
		String[] infos = new String[5];
		String selectedCount = "0";
		String selectedMoney = "0";
		String allcount = "0";// 全部品种
		String selectStore = "0";
		String postageAcount = "0";
		for (int i = 0; i < listGoods.get(0).getStoreList().size(); i++) {
			// 判断是否满足店铺发货金额
			double MoneySend = getisfahuo303(listGoods.get(0).getStoreList()
					.get(i).getProductsList());
			allcount = DecimalUtil.add(
					allcount,
					String.valueOf(listGoods.get(0).getStoreList().get(i)
							.getProductsList().size()));
			if (MoneySend >= Double.valueOf(listGoods.get(0).getStoreList()
					.get(i).getMoneySend())) {
				// 满足发货金额，直接加入总数
				selectedMoney = DecimalUtil.add(selectedMoney,
						String.valueOf(MoneySend));
				selectStore = DecimalUtil.add(selectStore, "1");
				double MoneyFreePostage = Double.valueOf(listGoods.get(0)
						.getStoreList().get(i).getMoneyFreePostage());
				// 判断是否免邮
				if (Double.valueOf(selectedMoney) < MoneyFreePostage)// 不免邮
				{
					postageAcount = DecimalUtil.add(postageAcount, listGoods
							.get(0).getStoreList().get(i).getPostage());
				}
				// 计算能购买商品总数
				for (int j = 0; j < listGoods.get(0).getStoreList().get(i)
						.getProductsList().size(); j++) {
					// 判断是否符合购买
					String errormsg = GetErrorMsg(listGoods.get(0)
							.getStoreList().get(i).getProductsList(), j);
					if (errormsg.length() < 1) {
						selectedCount = DecimalUtil.add(selectedCount, "1");
					}
				}
			}
		}
		if (selectedCount.contains(".")) {
			selectedCount = selectedCount.split("\\.")[0];
		}
		if (allcount.contains(".")) {
			allcount = allcount.split("\\.")[0];
		}
		if (selectStore.contains(".")) {
			selectStore = selectStore.split("\\.")[0];
		}
		infos[0] = selectedCount;
		infos[1] = selectedMoney;
		infos[2] = allcount;// 全部品种
		infos[3] = selectStore;
		infos[4] = postageAcount;
		return infos;
	}

	public static String[] getWisdomSelectGoods(List<WisdomBean303> listGoods) {
		String[] info = new String[2];
		String infos = "";
		int nobuycount = 0;
		for (int i = 0; i < listGoods.get(0).getStoreList().size(); i++) {
			// 判断是否满足店铺发货金额
			double MoneySend = getisfahuo303(listGoods.get(0).getStoreList()
					.get(i).getProductsList());
			if (MoneySend >= Double.valueOf(listGoods.get(0).getStoreList()
					.get(i).getMoneySend())) {
				// 计算能购买商品总数
				for (int j = 0; j < listGoods.get(0).getStoreList().get(i)
						.getProductsList().size(); j++) {
					// 判断是否符合购买
					String errormsg = GetErrorMsg(listGoods.get(0)
							.getStoreList().get(i).getProductsList(), j);
					if (errormsg.length() < 1) {
						infos = infos
								+ listGoods.get(0).getStoreList().get(i)
										.getProductsList().get(j).getpid()
								+ ",";
					} else {
						nobuycount++;
					}
				}
			} else {
				nobuycount += listGoods.get(0).getStoreList().get(i)
						.getProductsList().size();
			}
		}
		if (infos.length() > 1) {
			infos = infos.substring(0, infos.length() - 1);
		}
		info[0] = infos;
		info[1] = String.valueOf(nobuycount);
		return info;
	}

	/**
	 * get the total amount of selected goods,not including the purchase price
	 * increases(addprice)
	 * 
	 * @param listGoods
	 * @param isallcart
	 *            false is store and true is all product of cart
	 * @return
	 */
	public static double GetSelectedProductAmount(
			List<ShoppingCartBean1> listGoods, boolean isallcart,
			int groupPosition, int childPosition) {
		double amount = 0;
		String selectstoreamount = "0";
		if (isallcart) {

		} else {
			for (int i = 0; i < listGoods.get(0).getStoreCartList()
					.get(groupPosition).getCartProductList().size(); i++) {
				if (listGoods.get(0).getStoreCartList().get(groupPosition)
						.getCartProductList().get(i).getOrderProductInfo()
						.get(0).getIsSelect()) {
					String storepricecount = listGoods.get(0)
							.getStoreCartList().get(groupPosition)
							.getCartProductList().get(i).getOrderProductInfo()
							.get(0).getShopPrice();
					String buycount = listGoods.get(0).getStoreCartList()
							.get(groupPosition).getCartProductList().get(i)
							.getOrderProductInfo().get(0).getBuyCount();
					selectstoreamount = DecimalUtil.add(selectstoreamount,
							DecimalUtil.multiply(storepricecount, buycount));
				}
			}
		}
		amount = Double.valueOf(selectstoreamount);
		return amount;
	}

	public static double getisfahuo(
			List<ShoppingCartBean1.StoreCartList.CartProductList> listGoods) {
		String selectedMoney = "0";
		for (int i = 0; i < listGoods.size(); i++) {
			if (listGoods.get(i).getOrderProductInfo().get(0).getIsSelect()) {
				String price = listGoods.get(i).getOrderProductInfo().get(0)
						.getShopPrice();
				String num = listGoods.get(i).getOrderProductInfo().get(0)
						.getBuyCount();
				String countMoney = DecimalUtil.multiply(price, num);
				selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
			}
		}
		return Double.valueOf(selectedMoney);
	}

	public static double getisfahuo303(
			ArrayList<WisdomBean303.StoreList.ProductsList> listGoods) {
		String selectedMoney = "0";
		for (int i = 0; i < listGoods.size(); i++) {
			// 判断是否符合购买
			String errormsg = GetErrorMsg(listGoods, i);
			if (errormsg.length() < 1) {
				String price = listGoods.get(i).getPrice();
				String num = listGoods.get(i).getbuyCount();
				String countMoney = DecimalUtil.multiply(price, num);
				selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
			}
		}
		return Double.valueOf(selectedMoney);
	}

	public static String GetErrorMsg(ArrayList<ProductsList> productlist,
			int childPosition) {
		// TODO Auto-generated method stub
		double minbuy = Double.valueOf(productlist.get(childPosition)
				.getminBuy());
		double buycount = Double.valueOf(productlist.get(childPosition)
				.getbuyCount());
		int sellertype = Integer.parseInt(productlist.get(childPosition)
				.getsellType());
		double Product_Pcs_Small = Double.valueOf(productlist
				.get(childPosition).getProduct_Pcs_Small());
		double Product_Pcs = Double.valueOf(productlist.get(childPosition)
				.getProduct_Pcs());
		double stock = Double
				.valueOf(productlist.get(childPosition).getstock());
		String msg = "";
		if (minbuy > buycount) {
			msg = "不满最低采购数量，最低采购数量为" + minbuy;
		} else {
			if (sellertype == 2) {
				if (buycount % Product_Pcs_Small > 0.001) {
					msg = "购买数量不符合中包装" + Product_Pcs_Small + "的整数倍";
				}
			} else if (sellertype == 3) {
				if (buycount % Product_Pcs > 0.001) {
					msg = "购买数量不符合件装" + Product_Pcs + "的整数倍";
				}
			}
		}
		if (msg.length() < 1)// 需要判断库存
		{
			if (buycount > stock) {
				msg = "购买数量超库存，最大可购买数量为" + stock;
			}
		}
		return msg;
	}

	public static boolean getisstorselect(
			List<ShoppingCartBean1.StoreCartList.CartProductList> listGoods) {
		boolean ishasselect = false;
		for (int i = 0; i < listGoods.size(); i++) {
			if (listGoods.get(i).getOrderProductInfo().get(0).getIsSelect()) {
				ishasselect = true;
				break;
			}
		}
		return ishasselect;
	}

	public static boolean hasSelectedGoods(List<ShoppingCartBean1> listGoods) {
		String count = getShoppingCount(listGoods)[0];
		if ("0".equals(count)) {
			return false;
		}
		return true;
	}

	public static boolean hasSelectedGoods303(List<WisdomBean303> listGoods) {
		String count = getWisdomCount(listGoods)[0];
		if ("0".equals(count)) {
			return false;
		}
		return true;
	}

	// 验证选中商品的库存
	public static String hasfullstock(List<ShoppingCartBean1> listGoods) {
		String retstr = "";
		for (int i = 0; i < listGoods.get(0).getStoreCartList().size(); i++) {
			for (int j = 0; j < listGoods.get(0).getStoreCartList().get(i)
					.getCartProductList().size(); j++) {
				ShoppingCartBean1.StoreCartList.CartProductList goods = listGoods
						.get(0).getStoreCartList().get(i).getCartProductList()
						.get(j);
				if (goods.getOrderProductInfo().get(0).getIsSelect()) {
					if (Double.valueOf(goods.getOrderProductInfo().get(0)
							.getBuyCount()) > Double.valueOf(goods
							.getOrderProductInfo().get(0).getstock())) {
						retstr += goods.getOrderProductInfo().get(0).getName()
								+ "库存不足";
					}
					boolean isbiaopin = true;
					if (Double.valueOf(goods.getOrderProductInfo().get(0)
							.getBagCount()) > 0) {
						isbiaopin = false;
					}
					double buycount = Double.valueOf(goods
							.getOrderProductInfo().get(0).getBuyCount());
					if (!isbiaopin) {
						if (buycount
								% Double.valueOf(goods.getOrderProductInfo()
										.get(0).getBagCount()) > 0.001) {
							retstr += goods.getOrderProductInfo().get(0)
									.getName()
									+ "不是袋装的整数倍";
						}
					}
					if (retstr.length() > 0) {
						break;
					}
				}
			}
			if (retstr.length() > 0) {
				break;
			}
		}
		return retstr;
	}

	// 获取订单商品串
	public static String GetSelectedGoods(List<ShoppingCartBean1> listGoods) {
		String selectgoods = "";
		for (int i = 0; i < listGoods.get(0).getStoreCartList().size(); i++) {
			// 判断是否满足店铺发货金额
			if (getisfahuo(listGoods.get(0).getStoreCartList().get(i)
					.getCartProductList()) >= Double.valueOf(listGoods.get(0)
					.getStoreCartList().get(i).getStoreInfo().get(0)
					.getLowestdeliveryAmount())) {
				for (int j = 0; j < listGoods.get(0).getStoreCartList().get(i)
						.getCartProductList().size(); j++) {
					boolean isSelectd = listGoods.get(0).getStoreCartList()
							.get(i).getCartProductList().get(j)
							.getOrderProductInfo().get(0).getIsSelect();
					if (isSelectd) {
						String selectgood = listGoods.get(0).getStoreCartList()
								.get(i).getCartProductList().get(j)
								.getOrderProductInfo().get(0).getPid();

						selectgood = "0_" + selectgood + ",";

						selectgoods += selectgood;
					}

				}
			}
		}
		if (selectgoods.length() >= 1) {
			return selectgoods.substring(0, selectgoods.length() - 1);
		} else {
			return "";
		}
	}

	// 判断是否包含不发货商品
	public static boolean hasCannotSend(List<ShoppingCartBean1> listGoods) {
		boolean iscan = true;
		for (int i = 0; i < listGoods.get(0).getStoreCartList().size(); i++) {
			// 判断店铺是否有选中商品
			if (getisstorselect(listGoods.get(0).getStoreCartList().get(i)
					.getCartProductList())) {
				// 判断是否满足店铺发货金额
				if (getisfahuo(listGoods.get(0).getStoreCartList().get(i)
						.getCartProductList()) < Double.valueOf(listGoods
						.get(0).getStoreCartList().get(i).getStoreInfo().get(0)
						.getLowestdeliveryAmount())) {
					iscan = false;
					break;
				}
			}
		}
		return iscan;
	}

	/**
	 * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
	 * 
	 * @param productID
	 *            此商品的规格ID
	 * @param num
	 *            此商品的数量
	 */
	public static void addGoodToCart(String productID, String num) {
		ShoppingCartDao.getInstance().saveShoppingInfo(productID, num);
	}

	/**
	 * 删除某个商品,即删除其ProductID
	 * 
	 * @param productID
	 *            规格ID
	 */
	public static void delGood(String productID) {
		ShoppingCartDao.getInstance().deleteShoppingInfo(productID);
	}

	/** 删除全部商品 */
	public static void delAllGoods() {
		ShoppingCartDao.getInstance().delAllGoods();
	}

	/**
	 * 添加某商品的数量到数据库（301）
	 * 
	 * @param productID
	 *            此商品的规格ID
	 * @param num
	 *            此商品的数量
	 */
	public static void addGoodToCart301(String productID, String num) {
		WisdomSt301Dao.getInstance().saveShoppingInfo(productID, num);
	}

	/**
	 * 删除某个商品,即删除其ProductID——智慧采购301
	 * 
	 * @param productID
	 *            规格ID
	 */
	public static void delGood301(String productID) {
		WisdomSt301Dao.getInstance().deleteShoppingInfo(productID);
	}

	/** 删除全部商品 */
	public static void delAllGoods301() {
		WisdomSt301Dao.getInstance().delAllGoods();
	}
	
	
	/**
	 * 添加某商品的数量到数据库（301error）
	 * 
	 * @param productID
	 *            此商品的规格ID
	 * @param num
	 *            此商品的数量
	 */
	public static void addGoodToCart301error(String productID, String num) {
		WisdomSt301errorDao.getInstance().saveShoppingInfo(productID, num);
	}

	/**
	 * 删除某个商品,即删除其ProductID——智慧采购301
	 * 
	 * @param productID
	 *            规格ID
	 */
	public static void delGood301error(String productID) {
		WisdomSt301errorDao.getInstance().deleteShoppingInfo(productID);
	}

	/** 删除全部商品 */
	public static void delAllGoods301error() {
		WisdomSt301errorDao.getInstance().delAllGoods();
	}

	/**
	 * 添加某商品的数量到数据库（301）
	 * 
	 * @param productID
	 *            此商品的规格ID
	 * @param num
	 *            此商品的数量
	 */
	public static void addGoodToCart302(String productID, String num) {
		WisdomSt302Dao.getInstance().saveShoppingInfo(productID, num);
	}

	/**
	 * 删除某个商品,即删除其ProductID——智慧采购301
	 * 
	 * @param productID
	 *            规格ID
	 */
	public static void delGood302(String productID) {
		WisdomSt302Dao.getInstance().deleteShoppingInfo(productID);
	}

	/** 删除全部商品 */
	public static void delAllGoods302() {
		WisdomSt302Dao.getInstance().delAllGoods();
	}

	/**
	 * 添加某商品的数量到数据库（303）
	 * 
	 * @param productID
	 *            此商品的规格ID
	 * @param num
	 *            此商品的数量
	 */
	public static void addGoodToCart303(String productID, String num) {
		WisdomSt303Dao.getInstance().saveShoppingInfo(productID, num);
	}

	/**
	 * 删除某个商品,即删除其ProductID——智慧采购303
	 * 
	 * @param productID
	 *            规格ID
	 */
	public static void delGood303(String productID) {
		WisdomSt303Dao.getInstance().deleteShoppingInfo(productID);
	}

	/** 删除全部商品 */
	public static void delAllGoods303() {
		WisdomSt303Dao.getInstance().delAllGoods();
	}

	/** 增减数量，操作通用，数据不通用 */
	public static String addOrReduceGoodsNum(boolean isPlus,
			ShoppingCartBean1.StoreCartList.CartProductList goods,
			TextView tvNum) {
		// String currentNum = goods.getNumber().trim();
		String currentNum = goods.getOrderProductInfo().get(0).getBuyCount()
				.trim();
		String minbuy = goods.getOrderProductInfo().get(0).getMinBuyNum();
		if (Double.valueOf(currentNum) < Double.valueOf(minbuy)) {
			currentNum = minbuy;
		}
		String num = "1";// 最小销售
		boolean isbiaopin = true;// 标品
		if (Double.valueOf(goods.getOrderProductInfo().get(0).getBagCount()) > 0) {
			isbiaopin = false;
			num = goods.getOrderProductInfo().get(0).getBagCount();
		}
		// 销售方式
		int SellType = Integer.parseInt(goods.getOrderProductInfo().get(0)
				.getSellType());
		if (SellType == 2)// 中包装
		{
			if (Double.valueOf(goods.getOrderProductInfo().get(0)
					.getGoods_Pcs_Small()) > 0) {
				num = goods.getOrderProductInfo().get(0).getGoods_Pcs_Small();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}

		} else if (SellType == 3)// 件装
		{
			if (Double.valueOf(goods.getOrderProductInfo().get(0)
					.getGoods_Pcs()) > 0) {
				num = goods.getOrderProductInfo().get(0).getGoods_Pcs();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}
		}
		String numunit = num;
		if (isPlus) {
			num = String.valueOf(DecimalUtil.add(currentNum, num));
			if (Double.valueOf(num) > Double.valueOf(goods
					.getOrderProductInfo().get(0).getstock())) {
				ToastHelper.getInstance()
						.displayToastWithQuickClose(
								"库存不足，库存为"
										+ goods.getOrderProductInfo().get(0)
												.getstock());
				return "库存不足，库存为"
						+ goods.getOrderProductInfo().get(0).getstock();
			}
		} else {
			double i = Double.valueOf(currentNum);
			if (i > Double.valueOf(num) && i > Double.valueOf(minbuy)) {
				num = String.valueOf(i - Double.valueOf(num));
			} else {
				num = numunit;
				ToastHelper.getInstance().displayToastWithQuickClose(
						"购买数量不小于" + minbuy);
				return "购买数量不小于" + minbuy;
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			num = df.format(Double.valueOf(num));
		}
		String productID = goods.getOrderProductInfo().get(0).getPid();
		if (isbiaopin && num.contains(".")) {
			num = num.split("\\.")[0];
		}
		// num=DecimalUtil.add(num, "0");

		tvNum.setText(num);
		// goods.setNumber(num);
		goods.getOrderProductInfo().get(0).setBuyCount(num);
		updateGoodsNumber(productID, num);
		return "";
	}

	/** 增减数量，操作通用，数据不通用 */
	public static String addOrReduceGoodsNum303(boolean isPlus,
			ProductsList goods, TextView tvNum) {
		// String currentNum = goods.getNumber().trim();
		String currentNum = goods.getbuyCount().trim();
		String minbuy = goods.getminBuy();
		if (Double.valueOf(currentNum) < Double.valueOf(minbuy)) {
			currentNum = minbuy;
		}
		String num = "1";// 最小销售
		// 销售方式
		int SellType = Integer.parseInt(goods.getsellType());
		if (SellType == 2)// 中包装
		{
			if (Double.valueOf(goods.getProduct_Pcs_Small()) > 0) {
				num = goods.getProduct_Pcs_Small();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}

		} else if (SellType == 3)// 件装
		{
			if (Double.valueOf(goods.getsellType()) > 0) {
				num = goods.getProduct_Pcs();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}
		}
		String numunit = num;
		if (isPlus) {
			num = String.valueOf(DecimalUtil.add(currentNum, num));
			if (Double.valueOf(num) > Double.valueOf(goods.getstock())) {
				ToastHelper.getInstance().displayToastWithQuickClose(
						"库存不足，库存为" + goods.getstock());
				return "库存不足，库存为" + goods.getstock();
			}
		} else {
			double i = Double.valueOf(currentNum);
			if (i > Double.valueOf(num) && i > Double.valueOf(minbuy)) {
				num = String.valueOf(i - Double.valueOf(num));
			} else {
				num = numunit;
				ToastHelper.getInstance().displayToastWithQuickClose(
						"购买数量不小于" + minbuy);
				return "购买数量不小于" + minbuy;
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			num = df.format(Double.valueOf(num));
		}
		String productID = goods.getpid();
		if (num.contains(".")) {
			num = num.split("\\.")[0];
		}
		// num=DecimalUtil.add(num, "0");

		tvNum.setText(num);
		// goods.setNumber(num);
		goods.setbuyCount(num);
		updateGoodsNumber303(productID, num);
		return "";
	}

	/** 增减数量，操作通用，数据不通用 */
	public static String addOrReduceGoodsNum301(boolean isPlus, Goods goods,
			TextView tvNum) {
		// String currentNum = goods.getNumber().trim();
		String currentNum = goods.getbuyCount().trim();
		String num = "1";// 最小销售
		String numunit = num;
		if (isPlus) {
			num = String.valueOf(DecimalUtil.add(currentNum, num));
		} else {
			double i = Double.valueOf(currentNum);
			if (i > Double.valueOf(num)) {
				num = String.valueOf(i - Double.valueOf(num));
			} else {
				num = numunit;
				ToastHelper.getInstance().displayToastWithQuickClose(
						"购买数量不小于" + numunit);
				return "购买数量不小于" + numunit;
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			num = df.format(Double.valueOf(num));
		}
		if (num.contains(".")) {
			num = num.split("\\.")[0];
		}

		tvNum.setText(num);
		goods.setbuyCount(num);
		return "";
	}

	/** 是否打开加价购 */
	public static String setisselectaddprice(
			ShoppingCartBean1.StoreCartList.CartProductList.OrderProductInfo goods,
			TextView tvNum) {
		int addpricebuystate = goods.getaddpricebuystate();
		// String currentNum = goods.getNumber().trim();
		if (addpricebuystate == 1) {
			goods.setaddpricebuystate(0);
		} else {
			goods.setaddpricebuystate(1);
		}
		return "";
	}

	/**
	 * 更新购物车的单个商品数量
	 * 
	 * @param productID
	 * @param num
	 */
	public static void updateGoodsNumber(String productID, String num) {
		ShoppingCartDao.getInstance().updateGoodsNum(productID, num);
	}

	/**
	 * 更新购物车的单个商品数量
	 * 
	 * @param productID
	 * @param num
	 */
	public static void updateGoodsNumber303(String productID, String num) {
		WisdomSt303Dao.getInstance().updateGoodsNum(productID, num);
	}

	/**
	 * 查询购物车商品总数量
	 * <p/>
	 * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
	 * 
	 * @return
	 */
	public static int getGoodsCount() {
		return ShoppingCartDao.getInstance().getGoodsCount();
	}

	/**
	 * 查询购物车商品总数量
	 * <p/>
	 * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
	 * 
	 * @return
	 */
	public static int getGoodsCount303() {
		return WisdomSt303Dao.getInstance().getGoodsCount();
	}

	/**
	 * 获取所有商品ID，用于向服务器请求数据（非通用部分）
	 * 
	 * @return
	 */
	public static List<String> getAllProductID() {
		return ShoppingCartDao.getInstance().getProductList();
	}

	/**
	 * Get the promot message of add price buy addpricetype:0,filled;1:each full
	 * buycount: firstProudctStartNum:start first product of type 0
	 * firstProudctPerNum:each start first product of type 1
	 * secondProudctNum:amount of gift product
	 * 
	 * @return String
	 */
	public static String GetAddPriceInfo(
			ArrayList<CartProductList> productlist, int childPosition) {
		int addpricetype = Integer.parseInt(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getaddPriceType());
		String buycount = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getBuyCount();
		String firstProudctStartNum = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getfirstProudctStartNum();
		String firstProudctPerNum = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getfirstProudctPerNum();
		String secondProudctNum = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getsecondProudctNum();
		String zhuGood_unit = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getGoods_Unit();
		String ciGood_unit = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddproduct().get(0)
				.getGoods_Unit();
		String addprice = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getaddpricebuymodel().get(0).getaddPrice();
		boolean isbiaopin = true;// 有待处理，标品
		boolean isthisproduct = true;// 是否是本品
		int firstid = Integer.parseInt(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getfirstpid());
		int secondpid = Integer.parseInt(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getsecondpid());
		if (firstid != secondpid) {
			isthisproduct = false;
		}
		String addpriceinfo = "";
		String isthisinfo = "本品";
		if (!isthisproduct) {
			isthisinfo = "其它商品";
		}
		// 是否购满
		String isfull = "已购满";
		if (Double.valueOf(buycount) < Double.valueOf(firstProudctStartNum)
				+ Double.valueOf(firstProudctPerNum)) {
			if (addpricetype == 0)// filled
			{
				isfull = "购满" + firstProudctStartNum;
			} else// each full
			{
				isfull = "每购满" + firstProudctPerNum;
			}
		} else {
			if (addpricetype == 0)// filled
			{
				isfull = isfull + firstProudctStartNum;
			} else// each full
			{
				isfull = isfull + firstProudctPerNum;
			}
		}
		addpriceinfo = isfull + zhuGood_unit + "，加价" + addprice + "元可换购"
				+ isthisinfo + secondProudctNum + ciGood_unit;
		return addpriceinfo;
	}

	/**
	 * 获取加价购商品数量
	 * 
	 * @param productlist
	 * @param childPosition
	 * @return
	 */
	public static String Getaddproductnuminfo(
			ArrayList<CartProductList> productlist, int childPosition) {
		int addpricetype = Integer.parseInt(productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getaddPriceType());
		String buycount = productlist.get(childPosition).getOrderProductInfo()
				.get(0).getBuyCount();
		String secondProudctNum = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getsecondProudctNum();
		String firstProudctPerNum = productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddpricebuymodel().get(0)
				.getfirstProudctPerNum();
		double addstock = Double
				.valueOf(productlist.get(childPosition).getOrderProductInfo()
						.get(0).getaddproduct().get(0).getstock());

		boolean isbiaopin = true;// 有待处理,标品

		String addproductnuminfo = "";
		if (addpricetype == 0) {
			addproductnuminfo = "X" + secondProudctNum;
		} else {
			int addcishu = (int) Math.floor(Double.valueOf(buycount)
					/ Double.valueOf(firstProudctPerNum));
			if (Double.valueOf(productlist.get(childPosition)
					.getOrderProductInfo().get(0).getaddproduct().get(0)
					.getBagCount()) > 0) {
				isbiaopin = false;
			}
			if (isbiaopin) {
				secondProudctNum = secondProudctNum.split("\\.")[0];
				int num = Integer.parseInt(secondProudctNum) * addcishu;
				while (true) {
					if (num > addstock
							&& num >= Integer.parseInt(secondProudctNum)) {
						num = num - Integer.parseInt(secondProudctNum);
					} else {
						break;
					}
				}
				addproductnuminfo = "X" + num;
			} else {
				// 处理非标品
				double num = Double.valueOf(secondProudctNum) * addcishu;
				while (true) {
					if (num > addstock
							&& num >= Double.valueOf(secondProudctNum)) {
						num = num - Double.valueOf(secondProudctNum);
					} else {
						break;
					}
				}
				DecimalFormat df = new DecimalFormat("######0.00");
				addproductnuminfo = "X" + df.format(num);
			}
		}
		addproductnuminfo += productlist.get(childPosition)
				.getOrderProductInfo().get(0).getaddproduct().get(0)
				.getGoods_Unit();
		return addproductnuminfo;
	}

	/**
	 * 获取店铺内选中商品总金额
	 */
	public static String GetStoreCount(ArrayList<CartProductList> productlist) {
		// 店铺商品合计
		String storeacount = "0";
		for (int i = 0; i < productlist.size(); i++) {
			if (productlist.get(i).getOrderProductInfo().get(0).getIsSelect()) {
				String buycount = productlist.get(i).getOrderProductInfo()
						.get(0).getBuyCount();
				String shopprice = productlist.get(i).getOrderProductInfo()
						.get(0).getShopPrice();
				String productamount = DecimalUtil
						.multiply(buycount, shopprice);
				int thispmid = Integer.parseInt(productlist.get(i)
						.getOrderProductInfo().get(0).getaddpricebuymodel()
						.get(0).getpmid());
				boolean isbiaopin = true;// 有待处理,标品

				if (thispmid > 0)// 是加价购商品
				{
					if (Double.valueOf(productlist.get(i).getOrderProductInfo()
							.get(0).getaddproduct().get(0).getBagCount()) > 0) {
						isbiaopin = false;
					}
					String secondProudctNum = productlist.get(i)
							.getOrderProductInfo().get(0).getaddpricebuymodel()
							.get(0).getsecondProudctNum();
					String firstProudctPerNum = productlist.get(i)
							.getOrderProductInfo().get(0).getaddpricebuymodel()
							.get(0).getfirstProudctPerNum();
					double addstock = Double.valueOf(productlist.get(i)
							.getOrderProductInfo().get(0).getaddproduct()
							.get(0).getstock());
					int addpricebuystate = productlist.get(i)
							.getOrderProductInfo().get(0).getaddpricebuystate();
					if (addpricebuystate > 0)// 开启
					{
						int addpricetype = Integer
								.parseInt(productlist.get(i)
										.getOrderProductInfo().get(0)
										.getaddpricebuymodel().get(0)
										.getaddPriceType());
						String addprice = productlist.get(i)
								.getOrderProductInfo().get(0)
								.getaddpricebuymodel().get(0).getaddPrice();
						if (addpricetype == 0) {
							productamount = DecimalUtil.add(productamount,
									addprice);
						} else {
							int addcishu = (int) Math.floor(Double
									.valueOf(buycount)
									/ Double.valueOf(firstProudctPerNum));
							if (isbiaopin) {
								int num = Integer.parseInt(secondProudctNum)
										* addcishu;
								while (true) {
									if (num > addstock
											&& num >= Integer
													.parseInt(secondProudctNum)) {
										num = num
												- Integer
														.parseInt(secondProudctNum);
									} else {
										break;
									}
								}
								productamount = DecimalUtil.add(productamount,
										DecimalUtil.multiply(
												String.valueOf(addcishu),
												addprice));
							} else {
								// 处理非标品
								double num = Double.valueOf(secondProudctNum)
										* addcishu;
								while (true) {
									if (num > addstock
											&& num >= Double
													.valueOf(secondProudctNum)) {
										num = num
												- Double.valueOf(secondProudctNum);
									} else {
										break;
									}
								}
								DecimalFormat df = new DecimalFormat(
										"######0.00");
								productamount = DecimalUtil.add(
										productamount,
										DecimalUtil.multiply(
												df.format(addcishu), addprice));
							}
						}
					}
				}
				storeacount = DecimalUtil.add(productamount, storeacount);
			}
		}
		return storeacount;
	}

	/** 由于这次服务端没有保存商品数量，需要此步骤来处理数量（非通用部分） */
	public static void updateShopList(List<ShoppingCartBean> list) {
		if (list == null) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			ShoppingCartBean scb = list.get(i);
			if (scb == null) {
				continue;
			}
			ArrayList<ShoppingCartBean.Goods> list2 = scb.getGoods();
			if (list2 == null) {
				continue;
			}
			for (int j = 0; j < list2.size(); j++) {
				ShoppingCartBean.Goods goods = list2.get(j);
				if (goods == null) {
					continue;
				}
				String productID = goods.getProductID();
				String num = ShoppingCartDao.getInstance().getNumByProductID(
						productID);
				list.get(i).getGoods().get(j).setNumber(num);
			}
		}
	}

	/** 最低购买数量 */
	public static String GetMinBuyNum(List<ShoppingCartBean1> mListGoods1,
			int groupPosition, int childPosition) {
		// 此处为失去焦点时的处理内容
		int SellType = Integer.valueOf(mListGoods1.get(0).getStoreCartList()
				.get(groupPosition).getCartProductList().get(childPosition)
				.getOrderProductInfo().get(0).getSellType());
		String minnum = mListGoods1.get(0).getStoreCartList()
				.get(groupPosition).getCartProductList().get(childPosition)
				.getOrderProductInfo().get(0).getMinBuyNum();// 最小销售
		double addminnum = 1;
		double bagcount = Double.valueOf(mListGoods1.get(0).getStoreCartList()
				.get(groupPosition).getCartProductList().get(childPosition)
				.getOrderProductInfo().get(0).getBagCount());
		if (bagcount > 0) {
			addminnum = bagcount;
		}
		if (SellType == 2)// 中包装
		{
			minnum = mListGoods1.get(0).getStoreCartList().get(groupPosition)
					.getCartProductList().get(childPosition)
					.getOrderProductInfo().get(0).getGoods_Pcs_Small();
			addminnum = Double.valueOf(minnum);
		} else if (SellType == 3)// 件装
		{
			minnum = mListGoods1.get(0).getStoreCartList().get(groupPosition)
					.getCartProductList().get(childPosition)
					.getOrderProductInfo().get(0).getGoods_Pcs();
			addminnum = Double.valueOf(minnum);
		}
		if(addminnum<Double.valueOf(minnum))
		{
			addminnum = Double.valueOf(minnum);
		}
		if (bagcount > 0) {
		DecimalFormat df = new DecimalFormat("######0.00");
		 return df.format(Double.valueOf(addminnum));
		}else
		{
			DecimalFormat df = new DecimalFormat("######0");
			 return df.format(Double.valueOf(addminnum));
		}

	}
	
	/** alert 增加数量 */
	public static String AlertAddOrReduceGoodsNum(boolean isPlus,
			ShoppingCartBean1.StoreCartList.CartProductList goods,
			TextView tvNum) {
		// String currentNum = goods.getNumber().trim();
		String currentNum = tvNum.getText().toString()
				.trim();
		String minbuy = goods.getOrderProductInfo().get(0).getMinBuyNum();
		if (Double.valueOf(currentNum) < Double.valueOf(minbuy)) {
			currentNum = minbuy;
		}
		String num = "1";// 最小销售
		boolean isbiaopin = true;// 标品
		if (Double.valueOf(goods.getOrderProductInfo().get(0).getBagCount()) > 0) {
			isbiaopin = false;
			num = goods.getOrderProductInfo().get(0).getBagCount();
		}
		// 销售方式
		int SellType = Integer.parseInt(goods.getOrderProductInfo().get(0)
				.getSellType());
		if (SellType == 2)// 中包装
		{
			if (Double.valueOf(goods.getOrderProductInfo().get(0)
					.getGoods_Pcs_Small()) > 0) {
				num = goods.getOrderProductInfo().get(0).getGoods_Pcs_Small();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}

		} else if (SellType == 3)// 件装
		{
			if (Double.valueOf(goods.getOrderProductInfo().get(0)
					.getGoods_Pcs()) > 0) {
				num = goods.getOrderProductInfo().get(0).getGoods_Pcs();
			}
			if (Double.valueOf(currentNum) % Double.valueOf(num) > 0.001) {
				currentNum = String.valueOf(new Double(Double
						.valueOf(currentNum) / Double.valueOf(num)).intValue()
						* Double.valueOf(num));
			}
		}
		String numunit = num;
		if (isPlus) {
			num = String.valueOf(DecimalUtil.add(currentNum, num));
			if (Double.valueOf(num) > Double.valueOf(goods
					.getOrderProductInfo().get(0).getstock())) {
				ToastHelper.getInstance()
						.displayToastWithQuickClose(
								"库存不足，库存为"
										+ goods.getOrderProductInfo().get(0)
												.getstock());
				return "库存不足，库存为"
						+ goods.getOrderProductInfo().get(0).getstock();
			}
		} else {
			double i = Double.valueOf(currentNum);
			if (i > Double.valueOf(num) && i > Double.valueOf(minbuy)) {
				num = String.valueOf(i - Double.valueOf(num));
			} else {
				num = numunit;
				ToastHelper.getInstance().displayToastWithQuickClose(
						"购买数量不小于" + minbuy);
				return "购买数量不小于" + minbuy;
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			num = df.format(Double.valueOf(num));
		}
		String productID = goods.getOrderProductInfo().get(0).getPid();
		if (isbiaopin && num.contains(".")) {
			num = num.split("\\.")[0];
		}
		tvNum.setText(num);
		return "";
	}
}
