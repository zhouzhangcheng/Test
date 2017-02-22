package com.jock.tbshopcar.listener;

public interface OnWisdomChangeListener {
	 void onDataChange(String selectCount, String selectMoney,String allcount,String selectStore,String postageAcount);
	 void onSelectItem(boolean isSelectedAll);
	 void onOpenActivity(String pid);
	 void onOpenStore(String storeid);
	 void onOpenWisdomBuy(String SchemeName);
	 void onChangeTitle();
}
