package com.jock.tbshopcar.entity;

import java.util.ArrayList;

public class WisdomBean302
{
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
	/** ����ĸ���� **/
	private ArrayList<SchemeName> schemeName;

	public ArrayList<SchemeName> getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(ArrayList<SchemeName> schemeName) {
		this.schemeName = schemeName;
	}
	public static class SchemeName
	{
		/** ����id */
		private int id;
		public int getID() {
			return id;
		}
		public void setID(int id) {
			this.id = id;
		}
		/** �������� */
		private String SchemeName;
		public String getSchemeName() {
			return SchemeName;
		}
		public void setSchemeName(String SchemeName) {
			this.SchemeName = SchemeName;
		}
		/** ��Լʱ�� */
		private String EconomizeTime;
		public String getEconomizeTime() {
			return EconomizeTime;
		}
		public void setEconomizeTime(String EconomizeTime) {
			this.EconomizeTime = EconomizeTime;
		}
		/** ��Լ��Ǯ */
		private String EconomizeMoney;
		public String getEconomizeMoney() {
			return EconomizeMoney;
		}
		public void setEconomizeMoney(String EconomizeMoney) {
			this.EconomizeMoney = EconomizeMoney;
		}
		/** ���ٸ�Ʒ�ֵ���ԭ�ɹ��۸� */
		private String SuperiorityNum;
		public String getSuperiorityNum() {
			return SuperiorityNum;
		}
		public void setSuperiorityNum(String SuperiorityNum) {
			this.SuperiorityNum = SuperiorityNum;
		}
		/** �ڶ��ٸ����̲ɹ� */
		private String StoresCount;
		public String getStoresCount() {
			return StoresCount;
		}
		public void setStoresCount(String StoresCount) {
			this.StoresCount = StoresCount;
		}
		/** �ʷ�*/
		private String Postage;
		public String getPostage() {
			return Postage;
		}
		public void setPostage(String Postage) {
			this.Postage = Postage;
		}
		/** �ϼ�*/
		private String SurplusMoney;
		public String getSurplusMoney() {
			return SurplusMoney;
		}
		public void setSurplusMoney(String SurplusMoney) {
			this.SurplusMoney = SurplusMoney;
		}
		/** ��ƥ���Ʒ������*/
		private String mismatching;
		public String getmismatching() {
			return mismatching;
		}
		public void setmismatching(String mismatching) {
			this.mismatching = mismatching;
		}
	}
}
