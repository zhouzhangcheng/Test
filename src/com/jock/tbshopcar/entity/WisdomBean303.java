package com.jock.tbshopcar.entity;

import java.util.ArrayList;


public class WisdomBean303
{
	/** ���ﳵ��Ʒ���� */
	public static final String KEY_NUM = "num";
	/** ���ﳵ���ID */
	public static final String KEY_PRODUCT_ID = "product_id";
	private static int ishasfinish;
	public int getishasfinish()
	{
		return ishasfinish;
	}
	public void setishasfinish(int ishasfinish)
	{
		this.ishasfinish = ishasfinish;
	}
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
	/** �����б� **/
	private ArrayList<StoreList> storeList;

	public ArrayList<StoreList> getStoreList() {
		return storeList;
	}

	public void setStoreList(ArrayList<StoreList> storeList) {
		this.storeList = storeList;
	}
	public static class StoreList
	{
		/** ����id */
		private int store_Id;
		public int getstore_Id() {
			return store_Id;
		}
		public void setstore_Id(int store_Id) {
			this.store_Id = store_Id;
		}
		/** �������� */
		private String store_Name;
		public String getstore_Name() {
			return store_Name;
		}
		public void setstore_Name(String store_Name) {
			this.store_Name = store_Name;
		}
		/** �����ٽ��� */
		private String MoneySend;
		public String getMoneySend() {
			return MoneySend;
		}
		public void setMoneySend (String MoneySend) {
			this.MoneySend  = MoneySend;
		}
		/** �����ٽ����� */
		private String MoneyFreePostage;
		public String getMoneyFreePostage() {
			return MoneyFreePostage;
		}
		public void setMoneyFreePostage(String MoneyFreePostage) {
			this.MoneyFreePostage = MoneyFreePostage;
		}
		/** �ʷ�*/
		private String Postage;
		public String getPostage() {
			return Postage;
		}
		public void setPostage(String Postage) {
			this.Postage = Postage;
		}
		/** �ϼƽ��*/
		private String Surplusmoney;
		public String getSurplusmoney() {
			return Surplusmoney;
		}
		public void setSurplusmoney(String Surplusmoney) {
			this.Surplusmoney = Surplusmoney;
		}
		
		/** ��Ʒ�б� **/
		private ArrayList<ProductsList> productsList;

		public ArrayList<ProductsList> getProductsList() {
			return productsList;
		}

		public void setProductsList(ArrayList<ProductsList> productsList) {
			this.productsList = productsList;
		}
		public static class ProductsList
		{
			/** ��Ʒ���id */
			private int id;
			public int getID() {
				return id;
			}
			public void setID(int id) {
				this.id = id;
			}
			/** Goods_Package_ID */
			private static int Goods_Package_ID;
			public int getGoods_Package_ID() {
				return Goods_Package_ID;
			}
			public void setGoods_Package_ID(int Goods_Package_ID) {
				this.Goods_Package_ID = Goods_Package_ID;
			}
			/** ������ҵ */
			private String DrugsBase_Manufacturer;
			public String getDrugsBase_Manufacturer() {
				return DrugsBase_Manufacturer;
			}
			public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer) {
				this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
			}
			/** ��Ʒ���� */
			private String DrugsBase_DrugName;
			public String getDrugsBase_DrugName() {
				return DrugsBase_DrugName;
			}
			public void setDrugsBase_DrugName(String DrugsBase_DrugName) {
				this.DrugsBase_DrugName = DrugsBase_DrugName;
			}
			/** ��Ʒ��� */
			private String DrugsBase_Specification;
			public String getDrugsBase_Specification() {
				return DrugsBase_Specification;
			}
			public void setDrugsBase_Specification(String DrugsBase_Specification) {
				this.DrugsBase_Specification = DrugsBase_Specification;
			}
			/** ��Ʒ��� */
			private String stock;
			public String getstock() {
				return stock;
			}
			public void setstock(String stock) {
				this.stock = stock;
			}
			/** ��Ʒ����� */
			private String Price;
			public String getPrice() {
				return Price;
			}
			public void setPrice(String Price) {
				this.Price = Price;
			}
			/** �������� */
			private String buyCount;
			public String getbuyCount() {
				return buyCount;
			}
			public void setbuyCount(String buyCount) {
				this.buyCount = buyCount;
			}
			/** ��С�������� */
			private String minBuy;
			public String getminBuy() {
				return minBuy;
			}
			public void setminBuy(String minBuy) {
				this.minBuy = minBuy;
			}
			/** ��Ʒid */
			private String pid;
			public String getpid() {
				return pid;
			}
			public void setpid(String pid) {
				this.pid = pid;
			}
			/** ��ƷЧ�� */
			private String sxrq;
			public String getsxrq() {
				return sxrq;
			}
			public void setsxrq(String sxrq) {
				this.sxrq = sxrq;
			}
			/** ���۷�ʽ */
			private String sellType;
			public String getsellType() {
				return sellType;
			}
			public void setsellType(String sellType) {
				this.sellType = sellType;
			}
			/** �а�װ */
			private String Product_Pcs_Small;
			public String getProduct_Pcs_Small() {
				return Product_Pcs_Small;
			}
			public void setProduct_Pcs_Small(String Product_Pcs_Small) {
				this.Product_Pcs_Small = Product_Pcs_Small;
			}
			/** ��װ */
			private String Product_Pcs;
			public String getProduct_Pcs() {
				return Product_Pcs;
			}
			public void setProduct_Pcs(String Product_Pcs) {
				this.Product_Pcs = Product_Pcs;
			}
		}
	}
}
