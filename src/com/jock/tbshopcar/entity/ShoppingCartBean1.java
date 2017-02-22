package com.jock.tbshopcar.entity;

import java.util.ArrayList;

import com.jock.tbshopcar.entity.ShoppingCartBean.Goods;

public class ShoppingCartBean1 {
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
	/**商品总数**/
	private static String TotalCount;
	public String getTotalCount()
	{
		return TotalCount;
	}
	public void setTotalCount(String TotalCount)
	{
		this.TotalCount = TotalCount;
	}
	/**商品总价**/
	private static String ProductAmount;
	public String getProductAmount()
	{
		return ProductAmount;
	}
	public void setProductAmount(String ProductAmount)
	{
		this.ProductAmount = ProductAmount;
	}
	/**满减**/
	private static String FullCut;
	public String getFullCut()
	{
		return FullCut;
	}
	public void setFullCut(String FullCut)
	{
		this.FullCut = FullCut;
	}
	/**订单总金额**/
	private static String OrderAmount;
	public String getOrderAmount()
	{
		return OrderAmount;
	}
	public void setOrderAmount(String OrderAmount)
	{
		this.OrderAmount = OrderAmount;
	}
	/**是否全部选中**/
	private static String IsCheckAll;
	public String getIsCheckAll()
	{
		return IsCheckAll;
	}
	public void setIsCheckAll(String IsCheckAll)
	{
		this.IsCheckAll = IsCheckAll;
	}
	/**店铺购物车列表**/
	private ArrayList<StoreCartList> StoreCartList;
	public ArrayList<StoreCartList> getStoreCartList()
	{
		return StoreCartList;
	}
	public void setStoreCartList(ArrayList<StoreCartList> StoreCartList)
	{
		this.StoreCartList = StoreCartList;
	}
	public static class StoreCartList
	{
		/**店铺是否选中**/
		private boolean IsSelected;
		public boolean getIsSelected()
		{
			return IsSelected;
		}
		public void setIsSelected(boolean IsSelected)
		{
			this.IsSelected = IsSelected;
		}
		/**店铺满减**/
		private String fullCut;
		public String getfullCut()
		{
			return fullCut;
		}
		public void setfullCut(String fullCut)
		{
			this.fullCut = fullCut;
		}
		/**店铺商品总金额**/
		private String productAmount;
		public String getproductAmount()
		{
			return productAmount;
		}
		public void setproductAmount(String productAmount)
		{
			this.productAmount = productAmount;
		}
		/**店铺订单总金额**/
		private String OrderAmount;
		public String getOrderAmount()
		{
			return OrderAmount;
		}
		public void setOrderAmount(String OrderAmount)
		{
			this.OrderAmount = OrderAmount;
		}
		/**店铺信息**/
		private ArrayList<StoreInfo> StoreInfo;
		public ArrayList<StoreInfo> getStoreInfo()
		{
			return StoreInfo;
		}
		public void setStoreInfo(ArrayList<StoreInfo> StoreInfo)
		{
			this.StoreInfo = StoreInfo;
		}
		public static class StoreInfo{
			/**店铺id**/
			private String StoreId;
			public String getStoreId()
			{
				return StoreId;
			}
			public void setStoreId(String StoreId)
			{
				this.StoreId = StoreId;
			}
			/**店铺名称**/
			private String Name;
			public String getName()
			{
				return Name;
			}
			public void setName(String Name)
			{
				this.Name = Name;
			}
			/**最低发货金额**/
			private String LowestdeliveryAmount;
			public String getLowestdeliveryAmount()
			{
				return LowestdeliveryAmount;
			}
			public void setLowestdeliveryAmount(String LowestdeliveryAmount)
			{
				this.LowestdeliveryAmount = LowestdeliveryAmount;
			}
			/**免邮金额**/
			private String LowestFreeShippingAmount;
			public String getLowestFreeShippingAmount()
			{
				return LowestFreeShippingAmount;
			}
			public void setLowestFreeShippingAmount(String LowestFreeShippingAmount)
			{
				this.LowestFreeShippingAmount = LowestFreeShippingAmount;
			}
			/**默认邮费**/
			private String DefaultShipFee;
			public String getDefaultShipFee()
			{
				return DefaultShipFee;
			}
			public void setDefaultShipFee(String DefaultShipFee)
			{
				this.DefaultShipFee = DefaultShipFee;
			}
		}
		/**店铺内商品列表**/
		private ArrayList<CartProductList> CartProductList;
		public ArrayList<CartProductList> getCartProductList()
		{
			return CartProductList;
		}
		public void setCartProductList(ArrayList<CartProductList> CartProductList)
		{
			this.CartProductList = CartProductList;
		}
		public static class CartProductList
		{
			/**商品是否选中**/
//			private boolean IsSelected;
//			public boolean getIsSelected()
//			{
//				return IsSelected;
//			}
//			public void setIsSelected(boolean IsSelected)
//			{
//				this.IsSelected = IsSelected;
//			}
			/**店铺内商品信息**/
			private ArrayList<OrderProductInfo> OrderProductInfo;
			public ArrayList<OrderProductInfo> getOrderProductInfo()
			{
				return OrderProductInfo;
			}
			public void setOrderProductInfo(ArrayList<OrderProductInfo> OrderProductInfo)
			{
				this.OrderProductInfo = OrderProductInfo;
			}
			public static class OrderProductInfo
			{
				private String BagCount;
				public String getBagCount()
				{
					return BagCount;
				}
				public void setBagCount(String BagCount)
				{
					this.BagCount = BagCount;
				}
				/**商品记录id**/
				private String RecordId;
				public String getRecordId()
				{
					return RecordId;
				}
				public void setRecordId(String RecordId)
				{
					this.RecordId = RecordId;
				}
				/**订单id**/
				private String Oid;
				public String getOid()
				{
					return Oid;
				}
				public void setOid(String Oid)
				{
					this.Oid = Oid;
				}
				/**通用名**/
				private String DrugsBase_ProName;
				public String getDrugsBase_ProName()
				{
					return DrugsBase_ProName;
				}
				public void setDrugsBase_ProName(String DrugsBase_ProName)
				{
					this.DrugsBase_ProName = DrugsBase_ProName;
				}
				/**用户id**/
				private String Uid;
				public String getUid()
				{
					return Uid;
				}
				public void setUid(String Uid)
				{
					this.Uid = Uid;
				}
				/**用户id**/
				private String Pid;
				public String getPid()
				{
					return Pid;
				}
				public void setPid(String Pid)
				{
					this.Pid = Pid;
				}
				/**商品名**/
				private String Name;
				public String getName()
				{
					return Name;
				}
				public void setName(String Name)
				{
					this.Name = Name;
				}
				/**折后价**/
				private String DiscountPrice;
				public String getDiscountPrice()
				{
					return DiscountPrice;
				}
				public void setDiscountPrice(String DiscountPrice)
				{
					this.DiscountPrice = DiscountPrice;
				}
				/**商品价**/
				private String ShopPrice;
				public String getShopPrice()
				{
					return ShopPrice;
				}
				public void setShopPrice(String ShopPrice)
				{
					this.ShopPrice = ShopPrice;
				}
				/**购买数量**/
				private String BuyCount;
				public String getBuyCount()
				{
					return BuyCount;
				}
				public void setBuyCount(String BuyCount)
				{
					this.BuyCount = BuyCount;
				}
				/**商品规格**/
				private String DrugsBase_Specification;
				public String getDrugsBase_Specification()
				{
					return DrugsBase_Specification;
				}
				public void setDrugsBase_Specification(String DrugsBase_Specification)
				{
					this.DrugsBase_Specification = DrugsBase_Specification;
				}
				/**生产厂家**/
				private String DrugsBase_Manufacturer;
				public String getDrugsBase_Manufacturer()
				{
					return DrugsBase_Manufacturer;
				}
				public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer)
				{
					this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
				}
				/**准字号**/
				private String DrugsBase_ApprovalNumber;
				public String getDrugsBase_ApprovalNumber()
				{
					return DrugsBase_ApprovalNumber;
				}
				public void setDrugsBase_ApprovalNumber(String DrugsBase_ApprovalNumber)
				{
					this.DrugsBase_ApprovalNumber = DrugsBase_ApprovalNumber;
				}
				/**件装id**/
				private String Goods_Package_ID;
				public String getGoods_Package_ID()
				{
					return Goods_Package_ID;
				}
				public void setGoods_Package_ID(String Goods_Package_ID)
				{
					this.Goods_Package_ID = Goods_Package_ID;
				}
				/**图片地址**/
				private String SmallImageUrl;
				public String getSmallImageUrl()
				{
					return SmallImageUrl;
				}
				public void setSmallImageUrl(String SmallImageUrl)
				{
					this.SmallImageUrl = SmallImageUrl;
				}
				/**销售方式:1不限，2中包装，3件装**/
				private String SellType;
				public String getSellType()
				{
					return SellType;
				}
				public void setSellType(String SellType)
				{
					this.SellType = SellType;
				}
				/**件装数量**/
				private String Goods_Pcs;
				public String getGoods_Pcs()
				{
					return Goods_Pcs;
				}
				public void setGoods_Pcs(String Goods_Pcs)
				{
					this.Goods_Pcs = Goods_Pcs;
				}
				/**中包装装数量**/
				private String Goods_Pcs_Small;
				public String getGoods_Pcs_Small()
				{
					return Goods_Pcs_Small;
				}
				public void setGoods_Pcs_Small(String Goods_Pcs_Small)
				{
					this.Goods_Pcs_Small = Goods_Pcs_Small;
				}
				/**最小购买数量**/
				private String MinBuyNum;
				public String getMinBuyNum()
				{
					return MinBuyNum;
				}
				public void setMinBuyNum(String MinBuyNum)
				{
					this.MinBuyNum = MinBuyNum;
				}
				/**包装单位**/
				private String Goods_Unit;
				public String getGoods_Unit()
				{
					return Goods_Unit;
				}
				public void setGoods_Unit(String Goods_Unit)
				{
					this.Goods_Unit = Goods_Unit;
				}
				/**是否控销**/
				private String iskong;
				public String getiskong()
				{
					return iskong;
				}
				public void setiskong(String iskong)
				{
					this.iskong = iskong;
				}
				/**加价购活动id**/
				private String pmid;
				public String getpmid()
				{
					return pmid;
				}
				public void setpmid(String pmid)
				{
					this.pmid = pmid;
				}
				/**加价购此商品id**/
				private String addpricebuyid;
				public String getaddpricebuyid()
				{
					return addpricebuyid;
				}
				public void setaddpricebuyid(String addpricebuyid)
				{
					this.addpricebuyid = addpricebuyid;
				}
				/**加价购加价购每满数量**/
				private String addpricebuypernum;
				public String getaddpricebuypernum()
				{
					return addpricebuypernum;
				}
				public void setaddpricebuypernum(String addpricebuypernum)
				{
					this.addpricebuypernum = addpricebuypernum;
				}
				/**加价购是否开启**/
				private int addpricebuystate;
				public int getaddpricebuystate()
				{
					return addpricebuystate;
				}
				public void setaddpricebuystate(int addpricebuystate)
				{
					this.addpricebuystate = addpricebuystate;
				}
				/**加价购加价购满数量**/
				private String addpricebuynum;
				public String getaddpricebuynum()
				{
					return addpricebuynum;
				}
				public void setaddpricebuynum(String addpricebuynum)
				{
					this.addpricebuynum = addpricebuynum;
				}
				/**加价购加价购单价**/
				private String addpricebuycoast;
				public String getaddpricebuycoast()
				{
					return addpricebuycoast;
				}
				public void setaddpricebuycoast(String addpricebuycoast)
				{
					this.addpricebuycoast = addpricebuycoast;
				}
				/**商品库存**/
				private String stock;
				public String getstock()
				{
					return stock;
				}
				public void setstock(String stock)
				{
					this.stock = stock;
				}
				/**商品是否选中**/
				private boolean IsSelect;
				public boolean getIsSelect()
				{
					return IsSelect;
				}
				public void setIsSelect(boolean IsSelect)
				{
					this.IsSelect = IsSelect;
				}
				/**加价购商品信息**/
				private ArrayList<addproduct>addproduct;
				public ArrayList<addproduct> getaddproduct()
				{
					return addproduct;
				}
				public void setaddproduct(ArrayList<addproduct> addproduct)
				{
					this.addproduct = addproduct;
				}
				public static class addproduct{
					private String BagCount;
					public String getBagCount()
					{
						return BagCount;
					}
					public void setBagCount(String BagCount)
					{
						this.BagCount = BagCount;
					}
					/**商品名**/
					private String Name;
					public String getName()
					{
						return Name;
					}
					public void setName(String Name)
					{
						this.Name = Name;
					}
					/**包装单位**/
					private String Goods_Unit;
					public String getGoods_Unit()
					{
						return Goods_Unit;
					}
					public void setGoods_Unit(String Goods_Unit)
					{
						this.Goods_Unit = Goods_Unit;
					}
					/**商品规格**/
					private String DrugsBase_Specification;
					public String getDrugsBase_Specification()
					{
						return DrugsBase_Specification;
					}
					public void setDrugsBase_Specification(String DrugsBase_Specification)
					{
						this.DrugsBase_Specification = DrugsBase_Specification;
					}
					/**生产厂家**/
					private String DrugsBase_Manufacturer;
					public String getDrugsBase_Manufacturer()
					{
						return DrugsBase_Manufacturer;
					}
					public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer)
					{
						this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
					}
					/**图片地址**/
					private String SmallImageUrl;
					public String getSmallImageUrl()
					{
						return SmallImageUrl;
					}
					public void setSmallImageUrl(String SmallImageUrl)
					{
						this.SmallImageUrl = SmallImageUrl;
					}
					/**stock**/
					private String stock;
					public String getstock()
					{
						return stock;
					}
					public void setstock(String stock)
					{
						this.stock = stock;
					}
				}
				/**加价购数据模型**/
				private ArrayList<addpricebuymodel> addpricebuymodel;
				public ArrayList<addpricebuymodel> getaddpricebuymodel()
				{
					return addpricebuymodel;
				}
				public void setaddpricebuymodel(ArrayList<addpricebuymodel> addpricebuymodel)
				{
					this.addpricebuymodel = addpricebuymodel;
				}
				public static class addpricebuymodel{
					/**主商品id**/
					private String firstpid;
					public String getfirstpid()
					{
						return firstpid;
					}
					public void setfirstpid(String firstpid)
					{
						this.firstpid = firstpid;
					}
					/**次商品id**/
					private String secondpid;
					public String getsecondpid()
					{
						return secondpid;
					}
					public void setsecondpid(String secondpid)
					{
						this.secondpid = secondpid;
					}
					/**加价额**/
					private String addPrice;
					public String getaddPrice()
					{
						return addPrice;
					}
					public void setaddPrice(String addPrice)
					{
						this.addPrice = addPrice;
					}
					/**加价策略**/
					private String addPriceType;
					public String getaddPriceType()
					{
						return addPriceType;
					}
					public void setaddPriceType(String addPriceType)
					{
						this.addPriceType = addPriceType;
					}
					/**加价满-主商品**/
					private String firstProudctStartNum;
					public String getfirstProudctStartNum()
					{
						return firstProudctStartNum;
					}
					public void setfirstProudctStartNum(String firstProudctStartNum)
					{
						this.firstProudctStartNum = firstProudctStartNum;
					}
					/**加价每满-主商品**/
					private String firstProudctPerNum;
					public String getfirstProudctPerNum()
					{
						return firstProudctPerNum;
					}
					public void setfirstProudctPerNum(String firstProudctPerNum)
					{
						this.firstProudctPerNum = firstProudctPerNum;
					}
					/**加价次商品数量**/
					private String secondProudctNum;
					public String getsecondProudctNum()
					{
						return secondProudctNum;
					}
					public void setsecondProudctNum(String secondProudctNum)
					{
						this.secondProudctNum = secondProudctNum;
					}
					/**活动id**/
					private String pmid;
					public String getpmid()
					{
						return pmid;
					}
					public void setpmid(String pmid)
					{
						this.pmid = pmid;
					}
				}
				
				/**特价商品模型**/
				private ArrayList<specialpricemodel>specialpricemodel;
				public ArrayList<specialpricemodel> getspecialpricemodel()
				{
					return specialpricemodel;
				}
				public void setspecialpricemodel(ArrayList<specialpricemodel> specialpricemodel)
				{
					this.specialpricemodel = specialpricemodel;
				}
				public static class specialpricemodel{
					/**商品id**/
					private int pid;
					public int getpid()
					{
						return pid;
					}
					public void setpid(int pid)
					{
						this.pid = pid;
					}
					/**特价已经购买数量**/
					private String hasbuycount;
					public String gethasbuycount()
					{
						return hasbuycount;
					}
					public void sethasbuycount(String hasbuycount)
					{
						this.hasbuycount = hasbuycount;
					}
					/**商品特价**/
					private String speprice;
					public String getspeprice()
					{
						return speprice;
					}
					public void setspeprice(String speprice)
					{
						this.speprice = speprice;
					}
					/**历史价**/
					private String oldprice;
					public String getoldprice()
					{
						return oldprice;
					}
					public void setoldprice(String oldprice)
					{
						this.oldprice = oldprice;
					}
					/**0:不限，1：每人限制，2：每天每人限制**/
					private int limittype;
					public int getlimittype()
					{
						return limittype;
					}
					public void setlimittype(int limittype)
					{
						this.limittype = limittype;
					}
					/**限制数量**/
					private String limitnumber;
					public String getlimitnumber()
					{
						return limitnumber;
					}
					public void setlimitnumber(String limitnumber)
					{
						this.limitnumber = limitnumber;
					}
					/**开始时间**/
					private String starttime;
					public String getstarttime()
					{
						return starttime;
					}
					public void setstarttime(String starttime)
					{
						this.starttime = starttime;
					}
					/**结束时间**/
					private String endtime;
					public String getendtime()
					{
						return endtime;
					}
					public void setendtime(String endtime)
					{
						this.endtime = endtime;
					}
				}
			}
		}
		
	}
}
