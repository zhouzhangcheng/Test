package com.styao.work.bean;

import java.util.List;

public class ShopHomeData {
	private int VisitProductRecordCount; // ������Ʒ����
	private int BestProductListRecordCount;// ��Ʒ��Ʒ����
	private boolean isFavoriteStore;// �Ƿ��ղ�
	private boolean IsLogin; // �Ƿ��¼

	private BaseInfoModel BaseInfo;
	
	private List<ShopGoodInfo> AddPriceBuyProductsList;// ����
	private List<ShopGoodInfo> NewProductList;// ����
	private List<ShopGoodInfo> HotProductList;// ����
	private List<ShopGoodInfo> BestProductList;// ��Ʒ
	private List<ShopGoodInfo> VisitProductList;// ����
	private List<ShopGoodInfo> SpecialPriceList;// �ؼ�
	

	public List<ShopGoodInfo> getSpecialPriceList() {
		return SpecialPriceList;
	}

	public void setSpecialPriceList(List<ShopGoodInfo> specialPriceList) {
		SpecialPriceList = specialPriceList;
	}

	public int getVisitProductRecordCount() {
		return VisitProductRecordCount;
	}

	public void setVisitProductRecordCount(int visitProductRecordCount) {
		VisitProductRecordCount = visitProductRecordCount;
	}

	public int getBestProductListRecordCount() {
		return BestProductListRecordCount;
	}

	public void setBestProductListRecordCount(int bestProductListRecordCount) {
		BestProductListRecordCount = bestProductListRecordCount;
	}

	public boolean isFavoriteStore() {
		return isFavoriteStore;
	}

	public void setFavoriteStore(boolean isFavoriteStore) {
		this.isFavoriteStore = isFavoriteStore;
	}

	public boolean isIsLogin() {
		return IsLogin;
	}

	public void setIsLogin(boolean isLogin) {
		IsLogin = isLogin;
	}

	public BaseInfoModel getBaseInfo() {
		return BaseInfo;
	}

	public void setBaseInfo(BaseInfoModel baseInfo) {
		BaseInfo = baseInfo;
	}

	public List<ShopGoodInfo> getAddPriceBuyProductsList() {
		return AddPriceBuyProductsList;
	}

	public void setAddPriceBuyProductsList(
			List<ShopGoodInfo> addPriceBuyProductsList) {
		AddPriceBuyProductsList = addPriceBuyProductsList;
	}

	public List<ShopGoodInfo> getNewProductList() {
		return NewProductList;
	}

	public void setNewProductList(List<ShopGoodInfo> newProductList) {
		NewProductList = newProductList;
	}

	public List<ShopGoodInfo> getHotProductList() {
		return HotProductList;
	}

	public void setHotProductList(List<ShopGoodInfo> hotProductList) {
		HotProductList = hotProductList;
	}

	public List<ShopGoodInfo> getBestProductList() {
		return BestProductList;
	}

	public void setBestProductList(List<ShopGoodInfo> bestProductList) {
		BestProductList = bestProductList;
	}

	public List<ShopGoodInfo> getVisitProductList() {
		return VisitProductList;
	}

	public void setVisitProductList(List<ShopGoodInfo> visitProductList) {
		VisitProductList = visitProductList;
	}

	public class BaseInfoModel {
		private String Name;
		private String Logo;
		private String ManagerName;
		private int TaxPolicy; // 0��Ʊͬ�� 1��������Ʊ2�µ׿�Ʊ3���ӷ�Ʊ
		private String LowestdeliveryAmount; // ��ͷ���
		private String LowestFreeShippingAmount;// ����
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getLogo() {
			return Logo;
		}
		public void setLogo(String logo) {
			Logo = logo;
		}
		public String getManagerName() {
			return ManagerName;
		}
		public void setManagerName(String managerName) {
			ManagerName = managerName;
		}
		public int getTaxPolicy() {
			return TaxPolicy;
		}
		public void setTaxPolicy(int taxPolicy) {
			TaxPolicy = taxPolicy;
		}
		public String getLowestdeliveryAmount() {
			return LowestdeliveryAmount;
		}
		public void setLowestdeliveryAmount(String lowestdeliveryAmount) {
			LowestdeliveryAmount = lowestdeliveryAmount;
		}
		public String getLowestFreeShippingAmount() {
			return LowestFreeShippingAmount;
		}
		public void setLowestFreeShippingAmount(String lowestFreeShippingAmount) {
			LowestFreeShippingAmount = lowestFreeShippingAmount;
		}
		
		
	}

	public class ShopGoodInfo {
		private String pid;
		private String DrugsBase_DrugName; // ����
		private String DrugsBase_Specification; // ���
		private String DrugsBase_Manufacturer; // ��ַ
		private String isKong;// �Ƿ�����
		private String shopprice;
		private boolean IsSalesPromotion;// �Ƿ����
		private String GrossMargin;// ë����
		private String ImageUrl;
		private AddPriceBuyModelInfo AddPriceBuyModel;
		private String Goods_Unit;
		private SpecialPriceModelInfo SpecialPriceModel;
		
		public AddPriceBuyModelInfo getAddPriceBuyModel() {
			return AddPriceBuyModel;
		}
		public void setAddPriceBuyModel(AddPriceBuyModelInfo addPriceBuyModel) {
			AddPriceBuyModel = addPriceBuyModel;
		}
		public String getGoods_Unit() {
			return Goods_Unit;
		}
		public void setGoods_Unit(String goods_Unit) {
			Goods_Unit = goods_Unit;
		}
		public SpecialPriceModelInfo getSpecialPriceModel() {
			return SpecialPriceModel;
		}
		public void setSpecialPriceModel(SpecialPriceModelInfo specialPriceModel) {
			SpecialPriceModel = specialPriceModel;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getDrugsBase_DrugName() {
			return DrugsBase_DrugName;
		}
		public void setDrugsBase_DrugName(String drugsBase_DrugName) {
			DrugsBase_DrugName = drugsBase_DrugName;
		}
		public String getDrugsBase_Specification() {
			return DrugsBase_Specification;
		}
		public void setDrugsBase_Specification(String drugsBase_Specification) {
			DrugsBase_Specification = drugsBase_Specification;
		}
		public String getDrugsBase_Manufacturer() {
			return DrugsBase_Manufacturer;
		}
		public void setDrugsBase_Manufacturer(String drugsBase_Manufacturer) {
			DrugsBase_Manufacturer = drugsBase_Manufacturer;
		}
		public String getIsKong() {
			return isKong;
		}
		public void setIsKong(String isKong) {
			this.isKong = isKong;
		}
		public String getShopprice() {
			return shopprice;
		}
		public void setShopprice(String shopprice) {
			this.shopprice = shopprice;
		}
		public boolean isIsSalesPromotion() {
			return IsSalesPromotion;
		}
		public void setIsSalesPromotion(boolean isSalesPromotion) {
			IsSalesPromotion = isSalesPromotion;
		}
		public String getGrossMargin() {
			return GrossMargin;
		}
		public void setGrossMargin(String grossMargin) {
			GrossMargin = grossMargin;
		}
		public String getImageUrl() {
			return ImageUrl;
		}
		public void setImageUrl(String imageUrl) {
			ImageUrl = imageUrl;
		}
		
	}
	
	public class AddPriceBuyModelInfo{
		private String addPrice;
		private String secondProudctNum;
		public String getAddPrice() {
			return addPrice;
		}
		public void setAddPrice(String addPrice) {
			this.addPrice = addPrice;
		}
		public String getSecondProudctNum() {
			return secondProudctNum;
		}
		public void setSecondProudctNum(String secondProudctNum) {
			this.secondProudctNum = secondProudctNum;
		}
	}
	public class SpecialPriceModelInfo{
		private String speprice;
		public String getSpeprice() {
			return speprice;
		}

		public void setSpeprice(String speprice) {
			this.speprice = speprice;
		}
	}
}
