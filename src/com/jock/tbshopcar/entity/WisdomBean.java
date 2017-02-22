package com.jock.tbshopcar.entity;

import java.util.ArrayList;
import java.util.List;

public class WisdomBean {
	/** 购物车商品数量 */
	public static final String KEY_NUM = "num";
	/** 购物车规格ID */
	public static final String KEY_PRODUCT_ID = "product_id";
	/**上下文信息**/
	private static String oldCookie;
	public String getoldCookie()
	{
		return oldCookie;
	}
	public void setoldCookie(String oldCookie)
	{
		this.oldCookie = oldCookie;
	}
	/**客服电话**/
	private static String mobile;
	public String getmobile()
	{
		return mobile;
	}
	public void setmobile(String mobile)
	{
		this.mobile = mobile;
	}
	/**审计划数量**/
	private static String matchingCount;
	public String getmatchingCount()
	{
		return matchingCount;
	}
	public void setmatchingCount(String matchingCount)
	{
		this.matchingCount = matchingCount;
	}
	/**未匹配数量**/
	private static String notMatchingCount;
	public String getnotMatchingCount()
	{
		return notMatchingCount;
	}
	public void setnotMatchingCount(String notMatchingCount)
	{
		this.notMatchingCount = notMatchingCount;
	}
	/**不采购数量**/
	private static String notPurchaseCount;
	public String getnotPurchaseCount()
	{
		return notPurchaseCount;
	}
	public void setnotPurchaseCount(String notPurchaseCount)
	{
		this.notPurchaseCount = notPurchaseCount;
	}
	/** 首字母分类 **/
	private ArrayList<FirstZiMu> firstzimulist;

	public ArrayList<FirstZiMu> getFirstZiMu() {
		return firstzimulist;
	}

	public void setFirstZiMu(ArrayList<FirstZiMu> firstzimulist) {
		this.firstzimulist = firstzimulist;
	}

	public static class FirstZiMu {
		
		/** 首字母 */
		private String PreChar;

		public String getPreChar() {
			return PreChar;
		}

		public void setPreChar(String PreChar) {
			this.PreChar = PreChar;
		}

		private ArrayList<Goods> goods;

		public ArrayList<Goods> getGoods() {
			return goods;
		}

		public void setGoods(ArrayList<Goods> goods) {
			this.goods = goods;
		}

		public static class Goods {
			/** 序号ID */
			private int id;

			public int getid() {
				return id;
			}

			public void setid(int id) {
				this.id = id;
			}
			/** erp编号 */
			private String psn;
			public String getpsn() {
				return psn;
			}

			public void setpsn(String psn) {
				this.psn = psn;
			}

			/** Goods_Package_ID */
			private int Goods_Package_ID;

			public int getGoods_Package_ID() {
				return Goods_Package_ID;
			}

			public void setGoods_Package_ID(int Goods_Package_ID) {
				this.Goods_Package_ID = Goods_Package_ID;
			}

			/** 首字母 */
			private String PreChar;

			public String getPreChar() {
				return PreChar;
			}

			public void setPreChar(String PreChar) {
				this.PreChar = PreChar;
			}

			/** 生产厂家 */
			private String DrugsBase_Manufacturer;

			public String getDrugsBase_Manufacturer() {
				return DrugsBase_Manufacturer;
			}

			public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer) {
				this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
			}

			/** 商品名称 **/
			private String DrugsBase_DrugName;

			public String getDrugsBase_DrugName() {
				return DrugsBase_DrugName;
			}

			public void setDrugsBase_DrugName(String DrugsBase_DrugName) {
				this.DrugsBase_DrugName = DrugsBase_DrugName;
			}

			/** 商品规格 **/
			private String DrugsBase_Specification;

			public String getDrugsBase_Specification() {
				return DrugsBase_Specification;
			}

			public void setDrugsBase_Specification(
					String DrugsBase_Specification) {
				this.DrugsBase_Specification = DrugsBase_Specification;
			}

			/** 商品库存 **/
			private double stock;

			public double getstock() {
				return stock;
			}

			public void setstock(double stock) {
				this.stock = stock;
			}

			/** 最近采购时间 **/
			private String LastTimeString;

			public String getLastTimeString() {
				return LastTimeString;
			}

			public void setLastTimeString(String LastTimeString) {
				this.LastTimeString = LastTimeString;
			}

			/** 历史采购价 **/
			private String HistoryPrice;

			public String getHistoryPrice() {
				return HistoryPrice;
			}

			public void setHistoryPrice(String HistoryPrice) {
				this.HistoryPrice = HistoryPrice;
			}

			/** 快捷购买数量 **/
			private String SalesVolume;

			public String getSalesVolume() {
				return SalesVolume;
			}

			public void setSalesVolume(String SalesVolume) {
				this.SalesVolume = SalesVolume;
			}

			/** 最小价格 **/
			private String minPrice;

			public String getminPrice() {
				return minPrice;
			}

			public void setminPrice(String minPrice) {
				this.minPrice = minPrice;
			}

			/** 最大价格 **/
			private String maxPrice;

			public String getmaxPrice() {
				return maxPrice;
			}

			public void setmaxPrice(String maxPrice) {
				this.maxPrice = maxPrice;
			}

			/** 紧急程度 **/
			private String priority;

			public String getpriority() {
				return priority;
			}

			public void setpriority(String priority) {
				this.priority = priority;
			}
			
			/** 购买数量 **/
			private String buyCount;

			public String getbuyCount() {
				return buyCount;
			}

			public void setbuyCount(String buyCount) {
				this.buyCount = buyCount;
			}
			/** 条码 **/
			private String Barcode ;

			public String getBarcode () {
				return Barcode ;
			}

			public void setBarcode (String Barcode ) {
				this.Barcode  = Barcode ;
			}
			private List<String> buyNumList;

			public List<String> getBuyNumList() {
				return buyNumList;
			}

			public void setBuyNumList(List<String> buyNumList) {
				this.buyNumList = buyNumList;
			}
		}
	}
}
