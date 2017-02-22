package com.jock.tbshopcar.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.WisdomBean;

/**
 * SHz
 */
public class WisdomSt301Dao {

    private volatile static WisdomSt301Dao instance = null;
    private SQLiteDatabase db;

    /**
     * 获取SimpleDemoDB实例
     */
    public static WisdomSt301Dao getInstance() {
        if (instance == null) {
            synchronized (WisdomSt301Dao.class) {
                if (instance == null) {
                    instance = new WisdomSt301Dao();
                }
            }
        }
        return instance;
    }

    private Cursor cursor;

    public void close() {
        if (db != null) {
            db.close();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public int getGoodsCount() {
        db = DBHelperSt301.getInstance().getReadableDatabase();
        cursor = db.rawQuery("select count(*) from " + DBHelperSt301.TB_SHOPPING_CART, null);
        int count = 0;
        //游标移到第一条记录准备获取数据
        if (cursor.moveToFirst()) {
            // 获取数据中的LONG类型数据
            count = (int) cursor.getLong(0);
        }
        close();
        return count;
    }

    public boolean isExistGood(String productID) {
        if (productID == null) {
            return false;
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        cursor = db.query(DBHelperSt301.TB_SHOPPING_CART, null, WisdomBean.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
        boolean isExist = cursor.moveToFirst();
        close();
        return isExist;
    }

    /**
     * 添加购物车商品信息
     *
     * @param productID 规格ID
     * @param num       商品数量
     */
    public void saveShoppingInfo(String productID, String num) {
        if (productID == null || "".equals(productID) || num == null || "".equals(num)) {
            return;
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(WisdomBean.KEY_PRODUCT_ID, productID);
        values.put(WisdomBean.KEY_NUM, num);
        db.insert(DBHelperSt301.TB_SHOPPING_CART, null, values);
        close();
    }

    /**
     * 删除购物车商品
     *
     * @param productID 规格ID
     */
    public void deleteShoppingInfo(String productID) {
        if (productID == null) {
            return;
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        db.delete(DBHelperSt301.TB_SHOPPING_CART, WisdomBean.KEY_PRODUCT_ID + " =?", new String[]{productID});
        close();
    }

    public void delAllGoods() {
        db = DBHelperSt301.getInstance().getReadableDatabase();
        db.delete(DBHelperSt301.TB_SHOPPING_CART, null, null);
        close();
    }

    public void deleteGoodList(List<String> goodList) {
        if (goodList == null) {
            return;
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        for (int i = 0; i < goodList.size(); i++) {
            db.delete(DBHelperSt301.TB_SHOPPING_CART, WisdomBean.KEY_PRODUCT_ID + " =?", new String[]{goodList.get(i)});
        }
        close();
    }

    /**
     * 修改购物车中某件商品的信息
     *
     * @param productID 规格ID
     * @param num       商品数量
     */
    public void updateGoodsNum(String productID, String num) {
        if (productID == null || "".equals(productID) || num == null || "".equals(num)) {
            return;
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        if (productID != null && !"".equals(productID) && num != null && !"".equals(num)) {
            values.put("num", num);
            db.update(DBHelperSt301.TB_SHOPPING_CART, values, WisdomBean.KEY_PRODUCT_ID + "=?", new String[]{productID});
        }
        close();
    }

    public String getNumByProductID(String productID) {
        if (productID == null) {
            return "1";
        }
        db = DBHelperSt301.getInstance().getReadableDatabase();
        cursor = db.query(DBHelperSt301.TB_SHOPPING_CART, new String[]{WisdomBean.KEY_NUM}, WisdomBean.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        close();
        return "1";
    }

    /**
     * 查询数据库中的购物车中的商品信息
     *
     * @return 购物车中的商品信息
     */
    public List<String> getProductList() {
        db = DBHelperSt301.getInstance().getReadableDatabase();
        List<String> mList = new ArrayList<String>();
        Cursor cursor = db.query(DBHelperSt301.TB_SHOPPING_CART, new String[]{WisdomBean.KEY_PRODUCT_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String productID = cursor.getString(0);
                if (productID != null && !"".equals(productID)) {
                    mList.add(productID);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }


}
