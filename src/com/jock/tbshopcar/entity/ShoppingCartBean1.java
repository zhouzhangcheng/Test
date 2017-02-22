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
	/**��Ʒ����**/
	private static String TotalCount;
	public String getTotalCount()
	{
		return TotalCount;
	}
	public void setTotalCount(String TotalCount)
	{
		this.TotalCount = TotalCount;
	}
	/**��Ʒ�ܼ�**/
	private static String ProductAmount;
	public String getProductAmount()
	{
		return ProductAmount;
	}
	public void setProductAmount(String ProductAmount)
	{
		this.ProductAmount = ProductAmount;
	}
	/**����**/
	private static String FullCut;
	public String getFullCut()
	{
		return FullCut;
	}
	public void setFullCut(String FullCut)
	{
		this.FullCut = FullCut;
	}
	/**�����ܽ��**/
	private static String OrderAmount;
	public String getOrderAmount()
	{
		return OrderAmount;
	}
	public void setOrderAmount(String OrderAmount)
	{
		this.OrderAmount = OrderAmount;
	}
	/**�Ƿ�ȫ��ѡ��**/
	private static String IsCheckAll;
	public String getIsCheckAll()
	{
		return IsCheckAll;
	}
	public void setIsCheckAll(String IsCheckAll)
	{
		this.IsCheckAll = IsCheckAll;
	}
	/**���̹��ﳵ�б�**/
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
		/**�����Ƿ�ѡ��**/
		private boolean IsSelected;
		public boolean getIsSelected()
		{
			return IsSelected;
		}
		public void setIsSelected(boolean IsSelected)
		{
			this.IsSelected = IsSelected;
		}
		/**��������**/
		private String fullCut;
		public String getfullCut()
		{
			return fullCut;
		}
		public void setfullCut(String fullCut)
		{
			this.fullCut = fullCut;
		}
		/**������Ʒ�ܽ��**/
		private String productAmount;
		public String getproductAmount()
		{
			return productAmount;
		}
		public void setproductAmount(String productAmount)
		{
			this.productAmount = productAmount;
		}
		/**���̶����ܽ��**/
		private String OrderAmount;
		public String getOrderAmount()
		{
			return OrderAmount;
		}
		public void setOrderAmount(String OrderAmount)
		{
			this.OrderAmount = OrderAmount;
		}
		/**������Ϣ**/
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
			/**����id**/
			private String StoreId;
			public String getStoreId()
			{
				return StoreId;
			}
			public void setStoreId(String StoreId)
			{
				this.StoreId = StoreId;
			}
			/**��������**/
			private String Name;
			public String getName()
			{
				return Name;
			}
			public void setName(String Name)
			{
				this.Name = Name;
			}
			/**��ͷ������**/
			private String LowestdeliveryAmount;
			public String getLowestdeliveryAmount()
			{
				return LowestdeliveryAmount;
			}
			public void setLowestdeliveryAmount(String LowestdeliveryAmount)
			{
				this.LowestdeliveryAmount = LowestdeliveryAmount;
			}
			/**���ʽ��**/
			private String LowestFreeShippingAmount;
			public String getLowestFreeShippingAmount()
			{
				return LowestFreeShippingAmount;
			}
			public void setLowestFreeShippingAmount(String LowestFreeShippingAmount)
			{
				this.LowestFreeShippingAmount = LowestFreeShippingAmount;
			}
			/**Ĭ���ʷ�**/
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
		/**��������Ʒ�б�**/
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
			/**��Ʒ�Ƿ�ѡ��**/
//			private boolean IsSelected;
//			public boolean getIsSelected()
//			{
//				return IsSelected;
//			}
//			public void setIsSelected(boolean IsSelected)
//			{
//				this.IsSelected = IsSelected;
//			}
			/**��������Ʒ��Ϣ**/
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
				/**��Ʒ��¼id**/
				private String RecordId;
				public String getRecordId()
				{
					return RecordId;
				}
				public void setRecordId(String RecordId)
				{
					this.RecordId = RecordId;
				}
				/**����id**/
				private String Oid;
				public String getOid()
				{
					return Oid;
				}
				public void setOid(String Oid)
				{
					this.Oid = Oid;
				}
				/**ͨ����**/
				private String DrugsBase_ProName;
				public String getDrugsBase_ProName()
				{
					return DrugsBase_ProName;
				}
				public void setDrugsBase_ProName(String DrugsBase_ProName)
				{
					this.DrugsBase_ProName = DrugsBase_ProName;
				}
				/**�û�id**/
				private String Uid;
				public String getUid()
				{
					return Uid;
				}
				public void setUid(String Uid)
				{
					this.Uid = Uid;
				}
				/**�û�id**/
				private String Pid;
				public String getPid()
				{
					return Pid;
				}
				public void setPid(String Pid)
				{
					this.Pid = Pid;
				}
				/**��Ʒ��**/
				private String Name;
				public String getName()
				{
					return Name;
				}
				public void setName(String Name)
				{
					this.Name = Name;
				}
				/**�ۺ��**/
				private String DiscountPrice;
				public String getDiscountPrice()
				{
					return DiscountPrice;
				}
				public void setDiscountPrice(String DiscountPrice)
				{
					this.DiscountPrice = DiscountPrice;
				}
				/**��Ʒ��**/
				private String ShopPrice;
				public String getShopPrice()
				{
					return ShopPrice;
				}
				public void setShopPrice(String ShopPrice)
				{
					this.ShopPrice = ShopPrice;
				}
				/**��������**/
				private String BuyCount;
				public String getBuyCount()
				{
					return BuyCount;
				}
				public void setBuyCount(String BuyCount)
				{
					this.BuyCount = BuyCount;
				}
				/**��Ʒ���**/
				private String DrugsBase_Specification;
				public String getDrugsBase_Specification()
				{
					return DrugsBase_Specification;
				}
				public void setDrugsBase_Specification(String DrugsBase_Specification)
				{
					this.DrugsBase_Specification = DrugsBase_Specification;
				}
				/**��������**/
				private String DrugsBase_Manufacturer;
				public String getDrugsBase_Manufacturer()
				{
					return DrugsBase_Manufacturer;
				}
				public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer)
				{
					this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
				}
				/**׼�ֺ�**/
				private String DrugsBase_ApprovalNumber;
				public String getDrugsBase_ApprovalNumber()
				{
					return DrugsBase_ApprovalNumber;
				}
				public void setDrugsBase_ApprovalNumber(String DrugsBase_ApprovalNumber)
				{
					this.DrugsBase_ApprovalNumber = DrugsBase_ApprovalNumber;
				}
				/**��װid**/
				private String Goods_Package_ID;
				public String getGoods_Package_ID()
				{
					return Goods_Package_ID;
				}
				public void setGoods_Package_ID(String Goods_Package_ID)
				{
					this.Goods_Package_ID = Goods_Package_ID;
				}
				/**ͼƬ��ַ**/
				private String SmallImageUrl;
				public String getSmallImageUrl()
				{
					return SmallImageUrl;
				}
				public void setSmallImageUrl(String SmallImageUrl)
				{
					this.SmallImageUrl = SmallImageUrl;
				}
				/**���۷�ʽ:1���ޣ�2�а�װ��3��װ**/
				private String SellType;
				public String getSellType()
				{
					return SellType;
				}
				public void setSellType(String SellType)
				{
					this.SellType = SellType;
				}
				/**��װ����**/
				private String Goods_Pcs;
				public String getGoods_Pcs()
				{
					return Goods_Pcs;
				}
				public void setGoods_Pcs(String Goods_Pcs)
				{
					this.Goods_Pcs = Goods_Pcs;
				}
				/**�а�װװ����**/
				private String Goods_Pcs_Small;
				public String getGoods_Pcs_Small()
				{
					return Goods_Pcs_Small;
				}
				public void setGoods_Pcs_Small(String Goods_Pcs_Small)
				{
					this.Goods_Pcs_Small = Goods_Pcs_Small;
				}
				/**��С��������**/
				private String MinBuyNum;
				public String getMinBuyNum()
				{
					return MinBuyNum;
				}
				public void setMinBuyNum(String MinBuyNum)
				{
					this.MinBuyNum = MinBuyNum;
				}
				/**��װ��λ**/
				private String Goods_Unit;
				public String getGoods_Unit()
				{
					return Goods_Unit;
				}
				public void setGoods_Unit(String Goods_Unit)
				{
					this.Goods_Unit = Goods_Unit;
				}
				/**�Ƿ����**/
				private String iskong;
				public String getiskong()
				{
					return iskong;
				}
				public void setiskong(String iskong)
				{
					this.iskong = iskong;
				}
				/**�Ӽ۹��id**/
				private String pmid;
				public String getpmid()
				{
					return pmid;
				}
				public void setpmid(String pmid)
				{
					this.pmid = pmid;
				}
				/**�Ӽ۹�����Ʒid**/
				private String addpricebuyid;
				public String getaddpricebuyid()
				{
					return addpricebuyid;
				}
				public void setaddpricebuyid(String addpricebuyid)
				{
					this.addpricebuyid = addpricebuyid;
				}
				/**�Ӽ۹��Ӽ۹�ÿ������**/
				private String addpricebuypernum;
				public String getaddpricebuypernum()
				{
					return addpricebuypernum;
				}
				public void setaddpricebuypernum(String addpricebuypernum)
				{
					this.addpricebuypernum = addpricebuypernum;
				}
				/**�Ӽ۹��Ƿ���**/
				private int addpricebuystate;
				public int getaddpricebuystate()
				{
					return addpricebuystate;
				}
				public void setaddpricebuystate(int addpricebuystate)
				{
					this.addpricebuystate = addpricebuystate;
				}
				/**�Ӽ۹��Ӽ۹�������**/
				private String addpricebuynum;
				public String getaddpricebuynum()
				{
					return addpricebuynum;
				}
				public void setaddpricebuynum(String addpricebuynum)
				{
					this.addpricebuynum = addpricebuynum;
				}
				/**�Ӽ۹��Ӽ۹�����**/
				private String addpricebuycoast;
				public String getaddpricebuycoast()
				{
					return addpricebuycoast;
				}
				public void setaddpricebuycoast(String addpricebuycoast)
				{
					this.addpricebuycoast = addpricebuycoast;
				}
				/**��Ʒ���**/
				private String stock;
				public String getstock()
				{
					return stock;
				}
				public void setstock(String stock)
				{
					this.stock = stock;
				}
				/**��Ʒ�Ƿ�ѡ��**/
				private boolean IsSelect;
				public boolean getIsSelect()
				{
					return IsSelect;
				}
				public void setIsSelect(boolean IsSelect)
				{
					this.IsSelect = IsSelect;
				}
				/**�Ӽ۹���Ʒ��Ϣ**/
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
					/**��Ʒ��**/
					private String Name;
					public String getName()
					{
						return Name;
					}
					public void setName(String Name)
					{
						this.Name = Name;
					}
					/**��װ��λ**/
					private String Goods_Unit;
					public String getGoods_Unit()
					{
						return Goods_Unit;
					}
					public void setGoods_Unit(String Goods_Unit)
					{
						this.Goods_Unit = Goods_Unit;
					}
					/**��Ʒ���**/
					private String DrugsBase_Specification;
					public String getDrugsBase_Specification()
					{
						return DrugsBase_Specification;
					}
					public void setDrugsBase_Specification(String DrugsBase_Specification)
					{
						this.DrugsBase_Specification = DrugsBase_Specification;
					}
					/**��������**/
					private String DrugsBase_Manufacturer;
					public String getDrugsBase_Manufacturer()
					{
						return DrugsBase_Manufacturer;
					}
					public void setDrugsBase_Manufacturer(String DrugsBase_Manufacturer)
					{
						this.DrugsBase_Manufacturer = DrugsBase_Manufacturer;
					}
					/**ͼƬ��ַ**/
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
				/**�Ӽ۹�����ģ��**/
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
					/**����Ʒid**/
					private String firstpid;
					public String getfirstpid()
					{
						return firstpid;
					}
					public void setfirstpid(String firstpid)
					{
						this.firstpid = firstpid;
					}
					/**����Ʒid**/
					private String secondpid;
					public String getsecondpid()
					{
						return secondpid;
					}
					public void setsecondpid(String secondpid)
					{
						this.secondpid = secondpid;
					}
					/**�Ӽ۶�**/
					private String addPrice;
					public String getaddPrice()
					{
						return addPrice;
					}
					public void setaddPrice(String addPrice)
					{
						this.addPrice = addPrice;
					}
					/**�Ӽ۲���**/
					private String addPriceType;
					public String getaddPriceType()
					{
						return addPriceType;
					}
					public void setaddPriceType(String addPriceType)
					{
						this.addPriceType = addPriceType;
					}
					/**�Ӽ���-����Ʒ**/
					private String firstProudctStartNum;
					public String getfirstProudctStartNum()
					{
						return firstProudctStartNum;
					}
					public void setfirstProudctStartNum(String firstProudctStartNum)
					{
						this.firstProudctStartNum = firstProudctStartNum;
					}
					/**�Ӽ�ÿ��-����Ʒ**/
					private String firstProudctPerNum;
					public String getfirstProudctPerNum()
					{
						return firstProudctPerNum;
					}
					public void setfirstProudctPerNum(String firstProudctPerNum)
					{
						this.firstProudctPerNum = firstProudctPerNum;
					}
					/**�Ӽ۴���Ʒ����**/
					private String secondProudctNum;
					public String getsecondProudctNum()
					{
						return secondProudctNum;
					}
					public void setsecondProudctNum(String secondProudctNum)
					{
						this.secondProudctNum = secondProudctNum;
					}
					/**�id**/
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
				
				/**�ؼ���Ʒģ��**/
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
					/**��Ʒid**/
					private int pid;
					public int getpid()
					{
						return pid;
					}
					public void setpid(int pid)
					{
						this.pid = pid;
					}
					/**�ؼ��Ѿ���������**/
					private String hasbuycount;
					public String gethasbuycount()
					{
						return hasbuycount;
					}
					public void sethasbuycount(String hasbuycount)
					{
						this.hasbuycount = hasbuycount;
					}
					/**��Ʒ�ؼ�**/
					private String speprice;
					public String getspeprice()
					{
						return speprice;
					}
					public void setspeprice(String speprice)
					{
						this.speprice = speprice;
					}
					/**��ʷ��**/
					private String oldprice;
					public String getoldprice()
					{
						return oldprice;
					}
					public void setoldprice(String oldprice)
					{
						this.oldprice = oldprice;
					}
					/**0:���ޣ�1��ÿ�����ƣ�2��ÿ��ÿ������**/
					private int limittype;
					public int getlimittype()
					{
						return limittype;
					}
					public void setlimittype(int limittype)
					{
						this.limittype = limittype;
					}
					/**��������**/
					private String limitnumber;
					public String getlimitnumber()
					{
						return limitnumber;
					}
					public void setlimitnumber(String limitnumber)
					{
						this.limitnumber = limitnumber;
					}
					/**��ʼʱ��**/
					private String starttime;
					public String getstarttime()
					{
						return starttime;
					}
					public void setstarttime(String starttime)
					{
						this.starttime = starttime;
					}
					/**����ʱ��**/
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
