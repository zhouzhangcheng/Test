package com.jock.tbshopcar.entity;

import java.util.ArrayList;


public class WisdomBean303
{
	/** 购物车商品数量 */
	public static final String KEY_NUM = "num";
	/** 购物车规格ID */
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
	/** 店铺列表 **/
	private ArrayList<StoreList> storeList;

	public ArrayList<StoreList> getStoreList() {
		return storeList;
	}

	public void setStoreList(ArrayList<StoreList> storeList) {
		this.storeList = storeList;
	}
	public static class StoreList
	{
		/** 店铺id */
		private int store_Id;
		public int getstore_Id() {
			return store_Id;
		}
		public void setstore_Id(int store_Id) {
			this.store_Id = store_Id;
		}
		/** 店铺名称 */
		private String store_Name;
		public String getstore_Name() {
			return store_Name;
		}
		public void setstore_Name(String store_Name) {
			this.store_Name = store_Name;
		}
		/** 满多少金额发货 */
		private String MoneySend;
		public String getMoneySend() {
			return MoneySend;
		}
		public void setMoneySend (String MoneySend) {
			this.MoneySend  = MoneySend;
		}
		/** 满多少金额包邮 */
		private String MoneyFreePostage;
		public String getMoneyFreePostage() {
			return MoneyFreePostage;
		}
		public void setMoneyFreePostage(String MoneyFreePostage) {
			this.MoneyFreePostage = MoneyFreePostage;
		}
		/** 邮费*/
		private String Postage;
		public String getPostage() {
			return Postage;
		}
		public void setPostage(String Postage) {
			this.Postage = Postage;
		}
		/** 合计金额*/
		private String Surplusmoney;
		public String getSurplusmoney() {
			return Surplusmoney;
		}
		public void setSurplusmoney(String Surplusmoney) {
			this.Surplusmoney = Surplusmoney;
		}
		
		/** 商品列表 **/
		private ArrayList<ProductsList> productsList;

		public ArrayList<ProductsList> getProductsList() {
			return productsList;
		}

		public void setProductsList(ArrayList<ProductsList> productsList) {
			this.productsList = productsList;
		}
		public static class ProductsList
		{
			/** 商品序号id */
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
			/** 生产企业 */
			private String DrugsBase_Manufacturer;
			public String getDrugsBase_Manufacturer() {
				return DrugsBase_Manufacturer;
			}
			public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer) {
				this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
			}
			/** 商品名称 */
			private String DrugsBase_DrugName;
			public String getDrugsBase_DrugName() {
				return DrugsBase_DrugName;
			}
			public void setDrugsBase_DrugName(String DrugsBase_DrugName) {
				this.DrugsBase_DrugName = DrugsBase_DrugName;
			}
			/** 商品规格 */
			private String DrugsBase_Specification;
			public String getDrugsBase_Specification() {
				return DrugsBase_Specification;
			}
			public void setDrugsBase_Specification(String DrugsBase_Specification) {
				this.DrugsBase_Specification = DrugsBase_Specification;
			}
			/** 商品库存 */
			private String stock;
			public String getstock() {
				return stock;
			}
			public void setstock(String stock) {
				this.stock = stock;
			}
			/** 商品购买价 */
			private String Price;
			public String getPrice() {
				return Price;
			}
			public void setPrice(String Price) {
				this.Price = Price;
			}
			/** 购买数量 */
			private String buyCount;
			public String getbuyCount() {
				return buyCount;
			}
			public void setbuyCount(String buyCount) {
				this.buyCount = buyCount;
			}
			/** 最小购买数量 */
			private String minBuy;
			public String getminBuy() {
				return minBuy;
			}
			public void setminBuy(String minBuy) {
				this.minBuy = minBuy;
			}
			/** 商品id */
			private String pid;
			public String getpid() {
				return pid;
			}
			public void setpid(String pid) {
				this.pid = pid;
			}
			/** 商品效期 */
			private String sxrq;
			public String getsxrq() {
				return sxrq;
			}
			public void setsxrq(String sxrq) {
				this.sxrq = sxrq;
			}
			/** 销售方式 */
			private String sellType;
			public String getsellType() {
				return sellType;
			}
			public void setsellType(String sellType) {
				this.sellType = sellType;
			}
			/** 中包装 */
			private String Product_Pcs_Small;
			public String getProduct_Pcs_Small() {
				return Product_Pcs_Small;
			}
			public void setProduct_Pcs_Small(String Product_Pcs_Small) {
				this.Product_Pcs_Small = Product_Pcs_Small;
			}
			/** 件装 */
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
