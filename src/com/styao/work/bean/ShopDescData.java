package com.styao.work.bean;

public class ShopDescData {
	private boolean isFavoriteStore;
	private String mobile;
	private String qq;
	private String productNum;
	private String storeEname;
	
	private StoreInfoData StoreInfo;
	
	private StoreContactData StoreKeeperInfo;
	
	
	public boolean isFavoriteStore() {
		return isFavoriteStore;
	}
	public void setFavoriteStore(boolean isFavoriteStore) {
		this.isFavoriteStore = isFavoriteStore;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getStoreEname() {
		return storeEname;
	}
	public void setStoreEname(String storeEname) {
		this.storeEname = storeEname;
	}
	public StoreInfoData getStoreInfo() {
		return StoreInfo;
	}
	public void setStoreInfo(StoreInfoData storeInfo) {
		StoreInfo = storeInfo;
	}
	public StoreContactData getStoreKeeperInfo() {
		return StoreKeeperInfo;
	}
	public void setStoreKeeperInfo(StoreContactData storeKeeperInfo) {
		StoreKeeperInfo = storeKeeperInfo;
	}
	public class StoreInfoData{
		private String Name;
		private String Description;
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getDescription() {
			return Description;
		}
		public void setDescription(String description) {
			Description = description;
		}
		
	}
	public class StoreContactData{
		private String Name;
		private String Address;
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		
	}
	
}
