package com.jock.tbshopcar.entity;

import java.util.ArrayList;

public class WisdomBean302
{
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
	/** 首字母分类 **/
	private ArrayList<SchemeName> schemeName;

	public ArrayList<SchemeName> getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(ArrayList<SchemeName> schemeName) {
		this.schemeName = schemeName;
	}
	public static class SchemeName
	{
		/** 方案id */
		private int id;
		public int getID() {
			return id;
		}
		public void setID(int id) {
			this.id = id;
		}
		/** 方案名称 */
		private String SchemeName;
		public String getSchemeName() {
			return SchemeName;
		}
		public void setSchemeName(String SchemeName) {
			this.SchemeName = SchemeName;
		}
		/** 节约时间 */
		private String EconomizeTime;
		public String getEconomizeTime() {
			return EconomizeTime;
		}
		public void setEconomizeTime(String EconomizeTime) {
			this.EconomizeTime = EconomizeTime;
		}
		/** 节约金钱 */
		private String EconomizeMoney;
		public String getEconomizeMoney() {
			return EconomizeMoney;
		}
		public void setEconomizeMoney(String EconomizeMoney) {
			this.EconomizeMoney = EconomizeMoney;
		}
		/** 多少个品种低于原采购价格 */
		private String SuperiorityNum;
		public String getSuperiorityNum() {
			return SuperiorityNum;
		}
		public void setSuperiorityNum(String SuperiorityNum) {
			this.SuperiorityNum = SuperiorityNum;
		}
		/** 在多少个店铺采购 */
		private String StoresCount;
		public String getStoresCount() {
			return StoresCount;
		}
		public void setStoresCount(String StoresCount) {
			this.StoresCount = StoresCount;
		}
		/** 邮费*/
		private String Postage;
		public String getPostage() {
			return Postage;
		}
		public void setPostage(String Postage) {
			this.Postage = Postage;
		}
		/** 合计*/
		private String SurplusMoney;
		public String getSurplusMoney() {
			return SurplusMoney;
		}
		public void setSurplusMoney(String SurplusMoney) {
			this.SurplusMoney = SurplusMoney;
		}
		/** 不匹配的品种数量*/
		private String mismatching;
		public String getmismatching() {
			return mismatching;
		}
		public void setmismatching(String mismatching) {
			this.mismatching = mismatching;
		}
	}
}
