package com.jock.tbshopcar.entity;

import java.util.ArrayList;
import java.util.List;

public class WisdomBean {
	/** ���ﳵ��Ʒ���� */
	public static final String KEY_NUM = "num";
	/** ���ﳵ���ID */
	public static final String KEY_PRODUCT_ID = "product_id";
	/**��������Ϣ**/
	private static String oldCookie;
	public String getoldCookie()
	{
		return oldCookie;
	}
	public void setoldCookie(String oldCookie)
	{
		this.oldCookie = oldCookie;
	}
	/**�ͷ��绰**/
	private static String mobile;
	public String getmobile()
	{
		return mobile;
	}
	public void setmobile(String mobile)
	{
		this.mobile = mobile;
	}
	/**��ƻ�����**/
	private static String matchingCount;
	public String getmatchingCount()
	{
		return matchingCount;
	}
	public void setmatchingCount(String matchingCount)
	{
		this.matchingCount = matchingCount;
	}
	/**δƥ������**/
	private static String notMatchingCount;
	public String getnotMatchingCount()
	{
		return notMatchingCount;
	}
	public void setnotMatchingCount(String notMatchingCount)
	{
		this.notMatchingCount = notMatchingCount;
	}
	/**���ɹ�����**/
	private static String notPurchaseCount;
	public String getnotPurchaseCount()
	{
		return notPurchaseCount;
	}
	public void setnotPurchaseCount(String notPurchaseCount)
	{
		this.notPurchaseCount = notPurchaseCount;
	}
	/** ����ĸ���� **/
	private ArrayList<FirstZiMu> firstzimulist;

	public ArrayList<FirstZiMu> getFirstZiMu() {
		return firstzimulist;
	}

	public void setFirstZiMu(ArrayList<FirstZiMu> firstzimulist) {
		this.firstzimulist = firstzimulist;
	}

	public static class FirstZiMu {
		
		/** ����ĸ */
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
			/** ���ID */
			private int id;

			public int getid() {
				return id;
			}

			public void setid(int id) {
				this.id = id;
			}
			/** erp��� */
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

			/** ����ĸ */
			private String PreChar;

			public String getPreChar() {
				return PreChar;
			}

			public void setPreChar(String PreChar) {
				this.PreChar = PreChar;
			}

			/** �������� */
			private String DrugsBase_Manufacturer;

			public String getDrugsBase_Manufacturer() {
				return DrugsBase_Manufacturer;
			}

			public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer) {
				this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
			}

			/** ��Ʒ���� **/
			private String DrugsBase_DrugName;

			public String getDrugsBase_DrugName() {
				return DrugsBase_DrugName;
			}

			public void setDrugsBase_DrugName(String DrugsBase_DrugName) {
				this.DrugsBase_DrugName = DrugsBase_DrugName;
			}

			/** ��Ʒ��� **/
			private String DrugsBase_Specification;

			public String getDrugsBase_Specification() {
				return DrugsBase_Specification;
			}

			public void setDrugsBase_Specification(
					String DrugsBase_Specification) {
				this.DrugsBase_Specification = DrugsBase_Specification;
			}

			/** ��Ʒ��� **/
			private double stock;

			public double getstock() {
				return stock;
			}

			public void setstock(double stock) {
				this.stock = stock;
			}

			/** ����ɹ�ʱ�� **/
			private String LastTimeString;

			public String getLastTimeString() {
				return LastTimeString;
			}

			public void setLastTimeString(String LastTimeString) {
				this.LastTimeString = LastTimeString;
			}

			/** ��ʷ�ɹ��� **/
			private String HistoryPrice;

			public String getHistoryPrice() {
				return HistoryPrice;
			}

			public void setHistoryPrice(String HistoryPrice) {
				this.HistoryPrice = HistoryPrice;
			}

			/** ��ݹ������� **/
			private String SalesVolume;

			public String getSalesVolume() {
				return SalesVolume;
			}

			public void setSalesVolume(String SalesVolume) {
				this.SalesVolume = SalesVolume;
			}

			/** ��С�۸� **/
			private String minPrice;

			public String getminPrice() {
				return minPrice;
			}

			public void setminPrice(String minPrice) {
				this.minPrice = minPrice;
			}

			/** ���۸� **/
			private String maxPrice;

			public String getmaxPrice() {
				return maxPrice;
			}

			public void setmaxPrice(String maxPrice) {
				this.maxPrice = maxPrice;
			}

			/** �����̶� **/
			private String priority;

			public String getpriority() {
				return priority;
			}

			public void setpriority(String priority) {
				this.priority = priority;
			}
			
			/** �������� **/
			private String buyCount;

			public String getbuyCount() {
				return buyCount;
			}

			public void setbuyCount(String buyCount) {
				this.buyCount = buyCount;
			}
			/** ���� **/
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
