package com.jock.tbshopcar.entity;

import java.util.ArrayList;

public class WisdomBean301error
{
	
	/** ʧЧ */
	public static final String GOOD_INVALID = "0";

	public static final String GOOD_VALID = "1";

	/** ���ﳵ��Ʒ���� */
	public static final String KEY_NUM = "num";
	/** ���ﳵ���ID */
	public static final String KEY_PRODUCT_ID = "product_id";

	/** �Ƿ��ڱ༭״̬ */
	private boolean isEditing;
	/** ���Ƿ�ѡ�� */
	private boolean isGroupSelected;

	/** �������� */
	private String merchantName;

	/** ����ID */
	private String merID;

	/** �Ƿ�ʧЧ�б� */
	private boolean isInvalidList;

	private boolean isAllGoodsInvalid;

	private ArrayList<Goods> goods;

	private ArrayList<Dispatch> dispatch;
	
	
	
	/**��Ʒ����**/
	public static String TotalCount;
	public String getTotalCount()
	{
		return TotalCount;
	}

	public void setTotalCount(String TotalCount)
	{
		this.TotalCount = TotalCount;
	}
	/**�����Ƿ�ѡ��**/
	public static String IsSelected;
	public String getIsSelected()
	{
		return IsSelected;
	}

	public void setIsSelected(String IsSelected)
	{
		this.IsSelected = IsSelected;
	}
	
	
	
	
	
	
	

	public boolean isAllGoodsInvalid()
	{
		return isAllGoodsInvalid;
	}

	public void setIsAllGoodsInvalid(boolean isAllGoodsInvalid)
	{
		this.isAllGoodsInvalid = isAllGoodsInvalid;
	}

	public boolean isInvalidList()
	{
		return isInvalidList;
	}

	public void setIsInvalidList(boolean isInvalidList)
	{
		this.isInvalidList = isInvalidList;
	}

	public String getMerID()
	{
		return merID;
	}

	public void setMerID(String merID)
	{
		this.merID = merID;
	}

	public boolean isEditing()
	{
		return isEditing;
	}

	public boolean isGroupSelected()
	{
		return isGroupSelected;
	}

	public void setIsGroupSelected(boolean isGroupSelected)
	{
		this.isGroupSelected = isGroupSelected;
	}

	public ArrayList<Goods> getGoods()
	{
		return goods;
	}

	public void setGoods(ArrayList<Goods> goods)
	{
		this.goods = goods;
	}

	public ArrayList<Dispatch> getDispatch()
	{
		return dispatch;
	}

	public void setDispatch(ArrayList<Dispatch> dispatch)
	{
		this.dispatch = dispatch;
	}

	public String getMerchantName()
	{
		return merchantName;
	}

	public void setMerchantName(String merchantName)
	{
		this.merchantName = merchantName;
	}

	public void setIsEditing(boolean isEditing)
	{
		this.isEditing = isEditing;
	}

	/** ��Ʒ�࣬�����ñ���Ӧ�ü��ϱ�־ ' _local ' */
	public static class Goods
	{
		/** ���� */
		private String number = "1";
		/** ��ƷID */
		private String goodsID;
		/** ��Ʒ���� */
		private String goodsName;
		/** ��Ʒ����ͼƬ */
		private String goodsLogo;
		/** ��Ʒ��� */
		private String pdtDesc;
		/** �г��ۣ�ԭ�� */
		private String mkPrice;
		/** �۸񣬵�ǰ�۸� */
		private String price;
		/** �Ƿ�ʧЧ,0ɾ��(ʧЧ),1���� */
		private String status;
		/** �Ƿ��Ǳ༭״̬ */
		private boolean isEditing;
		/** �Ƿ�ѡ�� */
		private boolean isChildSelected;
		/** ���ID */
		private String productID;

		/***/
		private String sellPloyID;

		/** �Ƿ���ʧЧ�б������ */
		private boolean isInvalidItem;

		/** �Ƿ����� */
		private boolean isBelongInvalidList;

		/** ��ʱ���������Ϊ�˱���β���ػ����Σ�����һ����ITEM������ʾ������û��������������һ�����ߣ�������Ҫ�����⴦�� */
		private boolean isLastTempItem;

		public boolean isLastTempItem()
		{
			return isLastTempItem;
		}

		public void setIsLastTempItem(boolean isLastTempItem)
		{
			this.isLastTempItem = isLastTempItem;
		}

		public boolean isBelongInvalidList()
		{
			return isBelongInvalidList;
		}

		public void setIsBelongInvalidList(boolean isBelongInvalidList)
		{
			this.isBelongInvalidList = isBelongInvalidList;
		}

		public boolean isInvalidItem()
		{
			return isInvalidItem;
		}

		public void setIsInvalidItem(boolean isInvalidItem)
		{
			this.isInvalidItem = isInvalidItem;
		}

		public String getStatus()
		{
			return status;
		}

		public void setStatus(String status)
		{
			this.status = status;
		}

		public String getSellPloyID()
		{
			return sellPloyID;
		}

		public void setSellPloyID(String sellPloyID)
		{
			this.sellPloyID = sellPloyID;
		}

		public String getProductID()
		{
			return productID;
		}

		public void setProductID(String productID)
		{
			this.productID = productID;
		}

		public boolean isEditing()
		{
			return isEditing;
		}

		public void setIsEditing(boolean isEditing)
		{
			this.isEditing = isEditing;
		}

		public boolean isChildSelected()
		{
			return isChildSelected;
		}

		public void setIsChildSelected(boolean isChildSelected)
		{
			this.isChildSelected = isChildSelected;
		}

		public String getGoodsID()
		{
			return goodsID;
		}

		public void setGoodsID(String goodsID)
		{
			this.goodsID = goodsID;
		}

		public String getGoodsName()
		{
			return goodsName;
		}

		public void setGoodsName(String goodsName)
		{
			this.goodsName = goodsName;
		}

		public String getGoodsLogo()
		{
			return goodsLogo;
		}

		public void setGoodsLogo(String goodsLogo)
		{
			this.goodsLogo = goodsLogo;
		}

		public String getPdtDesc()
		{
			return pdtDesc;
		}

		public void setPdtDesc(String pdtDesc)
		{
			this.pdtDesc = pdtDesc;
		}

		public String getMkPrice()
		{
			return mkPrice;
		}

		public void setMkPrice(String mkPrice)
		{
			this.mkPrice = mkPrice;
		}

		public String getPrice()
		{
			return price;
		}

		public void setPrice(String price)
		{
			this.price = price;
		}

		public String getNumber()
		{
			return number;
		}

		public void setNumber(String number)
		{
			this.number = number;
		}

	}

	public static class Dispatch
	{

		/** ����ID */
		private String dispatchID;
		/** ���ͷ�ʽ���� */
		private String dispatchName;
		/** ���ͷ�ʽ��1�ʼģ�2���������3�ͻ����ţ� */
		private String dispatchType;
		/** ���ͷ��� */
		private String fee;
		/** ���������˷� */
		private String limitFee;

		public String getDispatchID()
		{
			return dispatchID;
		}

		public void setDispatchID(String dispatchID)
		{
			this.dispatchID = dispatchID;
		}

		public String getDispatchName()
		{
			return dispatchName;
		}

		public void setDispatchName(String dispatchName)
		{
			this.dispatchName = dispatchName;
		}

		public String getDispatchType()
		{
			return dispatchType;
		}

		public void setDispatchType(String dispatchType)
		{
			this.dispatchType = dispatchType;
		}

		public String getFee()
		{
			return fee;
		}

		public void setFee(String fee)
		{
			this.fee = fee;
		}

		public String getLimitFee()
		{
			return limitFee;
		}

		public void setLimitFee(String limitFee)
		{
			this.limitFee = limitFee;
		}
	}

}
