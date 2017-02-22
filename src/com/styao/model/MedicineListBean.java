package com.styao.model;

public class MedicineListBean {
	private String id;
	private String Goods_Package_ID;
	private String DrugsBase_DrugName;
	private String DrugsBase_SimpeCode;
	private String DrugsBase_ProName;
	private String DrugsBase_Manufacturer;
	private String Goods_Pcs;
	private String Goods_Unit;
	private String ImageUrl;
	private String IsHighMargin;
	private String MaxGrossMargin;
	private String MinShopPrice;
	private String MarketPrice;
	private String PromotionTypes;

	public String getPromotionTypes() {
		return PromotionTypes;
	}

	public void setPromotionTypes(String promotionTypes) {
		PromotionTypes = promotionTypes;
	}

	public String getMarketPrice() {
		return MarketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		MarketPrice = marketPrice;
	}

	public String getMinShopPrice() {
		return MinShopPrice;
	}

	public void setMinShopPrice(String minShopPrice) {
		MinShopPrice = minShopPrice;
	}

	public String getMaxShopPrice() {
		return MaxShopPrice;
	}

	public void setMaxShopPrice(String maxShopPrice) {
		MaxShopPrice = maxShopPrice;
	}

	private String MaxShopPrice;

	public String getMaxGrossMargin() {
		return MaxGrossMargin;
	}

	public void setMaxGrossMargin(String maxGrossMargin) {
		MaxGrossMargin = maxGrossMargin;
	}

	public String getIsHighMargin() {
		return IsHighMargin;
	}

	public void setIsHighMargin(String isHighMargin) {
		IsHighMargin = isHighMargin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_Package_ID() {
		return Goods_Package_ID;
	}

	public void setGoods_Package_ID(String goods_Package_ID) {
		Goods_Package_ID = goods_Package_ID;
	}

	public String getDrugsBase_DrugName() {
		return DrugsBase_DrugName;
	}

	public void setDrugsBase_DrugName(String drugsBase_DrugName) {
		DrugsBase_DrugName = drugsBase_DrugName;
	}

	public String getDrugsBase_SimpeCode() {
		return DrugsBase_SimpeCode;
	}

	public void setDrugsBase_SimpeCode(String drugsBase_SimpeCode) {
		DrugsBase_SimpeCode = drugsBase_SimpeCode;
	}

	public String getDrugsBase_ProName() {
		return DrugsBase_ProName;
	}

	public void setDrugsBase_ProName(String drugsBase_ProName) {
		DrugsBase_ProName = drugsBase_ProName;
	}

	public String getDrugsBase_Manufacturer() {
		return DrugsBase_Manufacturer;
	}

	public void setDrugsBase_Manufacturer(String drugsBase_Manufacturer) {
		DrugsBase_Manufacturer = drugsBase_Manufacturer;
	}

	public String getGoods_Pcs() {
		return Goods_Pcs;
	}

	public void setGoods_Pcs(String goods_Pcs) {
		Goods_Pcs = goods_Pcs;
	}

	public String getGoods_Unit() {
		return Goods_Unit;
	}

	public void setGoods_Unit(String goods_Unit) {
		Goods_Unit = goods_Unit;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

}
