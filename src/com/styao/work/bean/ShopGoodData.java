package com.styao.work.bean;

import java.util.List;

public class ShopGoodData {
	private List<StoreProduct> StoreOlineProductList;
	public List<StoreProduct> getStoreOlineProductList() {
		return StoreOlineProductList;
	}
	public void setStoreOlineProductList(List<StoreProduct> storeOlineProductList) {
		StoreOlineProductList = storeOlineProductList;
	}

	public class StoreProduct {
		private int Pid;
		private String DrugsBase_DrugName; // ͨ����
		private String DrugsBase_Manufacturer; // ��������
		private String DrugsBase_Specification; // ���
		private String MarketPrice;// �г��۸�
		private String ShopPrice; // ����۸�
		private String GrossMargin; // ë����
		private String IsKong;// �Ƿ���������Ʒ
		private String IsBest; // �Ƿ�Ʒ -1 ȫ����0��1��
		private String IsHighMargin;//�Ƿ�������ҩƷ(��ë��)
		private String ImageUrl;
		private String PromotionTypes;
		
		
		public String getPromotionTypes() {
			return PromotionTypes;
		}
		public void setPromotionTypes(String promotionTypes) {
			PromotionTypes = promotionTypes;
		}
		public int getPid() {
			return Pid;
		}
		public void setPid(int pid) {
			Pid = pid;
		}
		public String getDrugsBase_DrugName() {
			return DrugsBase_DrugName;
		}
		public void setDrugsBase_DrugName(String drugsBase_DrugName) {
			DrugsBase_DrugName = drugsBase_DrugName;
		}
		public String getDrugsBase_Manufacturer() {
			return DrugsBase_Manufacturer;
		}
		public void setDrugsBase_Manufacturer(String drugsBase_Manufacturer) {
			DrugsBase_Manufacturer = drugsBase_Manufacturer;
		}
		public String getDrugsBase_Specification() {
			return DrugsBase_Specification;
		}
		public void setDrugsBase_Specification(String drugsBase_Specification) {
			DrugsBase_Specification = drugsBase_Specification;
		}
		public String getMarketPrice() {
			return MarketPrice;
		}
		public void setMarketPrice(String marketPrice) {
			MarketPrice = marketPrice;
		}
		public String getShopPrice() {
			return ShopPrice;
		}
		public void setShopPrice(String shopPrice) {
			ShopPrice = shopPrice;
		}
		public String getGrossMargin() {
			return GrossMargin;
		}
		public void setGrossMargin(String grossMargin) {
			GrossMargin = grossMargin;
		}
		public String getIsKong() {
			return IsKong;
		}
		public void setIsKong(String isKong) {
			IsKong = isKong;
		}
		public String getIsBest() {
			return IsBest;
		}
		public void setIsBest(String isBest) {
			IsBest = isBest;
		}
		public String getIsHighMargin() {
			return IsHighMargin;
		}
		public void setIsHighMargin(String isHighMargin) {
			IsHighMargin = isHighMargin;
		}
		public String getImageUrl() {
			return ImageUrl;
		}
		public void setImageUrl(String imageUrl) {
			ImageUrl = imageUrl;
		}
		
		
	}
}
