package com.jock.tbshopcar.listener;

public interface OnShoppingCartChangeListener {
    void onDataChange(String selectCount, String selectMoney);
    void onSelectItem(boolean isSelectedAll); 
    void onOpenActivity(String pid);
    void onOpenStore(String storeid);
}
