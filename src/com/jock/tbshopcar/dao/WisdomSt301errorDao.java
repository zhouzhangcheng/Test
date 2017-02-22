package com.jock.tbshopcar.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.WisdomBean301error;

/**
 * SHz
 */
public class WisdomSt301errorDao {

    private volatile static WisdomSt301errorDao instance = null;
    private SQLiteDatabase db;

    /**
     * ��ȡSimpleDemoDBʵ��
     */
    public static WisdomSt301errorDao getInstance() {
        if (instance == null) {
            synchronized (WisdomSt301errorDao.class) {
                if (instance == null) {
                    instance = new WisdomSt301errorDao();
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
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        cursor = db.rawQuery("select count(*) from " + DBHelperSt301error.TB_SHOPPING_CART, null);
        int count = 0;
        //�α��Ƶ���һ����¼׼����ȡ����
        if (cursor.moveToFirst()) {
            // ��ȡ�����е�LONG��������
            count = (int) cursor.getLong(0);
        }
        close();
        return count;
    }

    public boolean isExistGood(String productID) {
        if (productID == null) {
            return false;
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        cursor = db.query(DBHelperSt301error.TB_SHOPPING_CART, null, WisdomBean301error.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
        boolean isExist = cursor.moveToFirst();
        close();
        return isExist;
    }

    /**
     * ��ӹ��ﳵ��Ʒ��Ϣ
     *
     * @param productID ���ID
     * @param num       ��Ʒ����
     */
    public void saveShoppingInfo(String productID, String num) {
        if (productID == null || "".equals(productID) || num == null || "".equals(num)) {
            return;
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(WisdomBean301error.KEY_PRODUCT_ID, productID);
        values.put(WisdomBean301error.KEY_NUM, num);
        db.insert(DBHelperSt301error.TB_SHOPPING_CART, null, values);
        close();
    }

    /**
     * ɾ�����ﳵ��Ʒ
     *
     * @param productID ���ID
     */
    public void deleteShoppingInfo(String productID) {
        if (productID == null) {
            return;
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        db.delete(DBHelperSt301error.TB_SHOPPING_CART, WisdomBean301error.KEY_PRODUCT_ID + " =?", new String[]{productID});
        close();
    }

    public void delAllGoods() {
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        db.delete(DBHelperSt301error.TB_SHOPPING_CART, null, null);
        close();
    }

    public void deleteGoodList(List<String> goodList) {
        if (goodList == null) {
            return;
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        for (int i = 0; i < goodList.size(); i++) {
            db.delete(DBHelperSt301error.TB_SHOPPING_CART, WisdomBean301error.KEY_PRODUCT_ID + " =?", new String[]{goodList.get(i)});
        }
        close();
    }

    /**
     * �޸Ĺ��ﳵ��ĳ����Ʒ����Ϣ
     *
     * @param productID ���ID
     * @param num       ��Ʒ����
     */
    public void updateGoodsNum(String productID, String num) {
        if (productID == null || "".equals(productID) || num == null || "".equals(num)) {
            return;
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        if (productID != null && !"".equals(productID) && num != null && !"".equals(num)) {
            values.put("num", num);
            db.update(DBHelperSt301error.TB_SHOPPING_CART, values, WisdomBean301error.KEY_PRODUCT_ID + "=?", new String[]{productID});
        }
        close();
    }

    public String getNumByProductID(String productID) {
        if (productID == null) {
            return "1";
        }
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        cursor = db.query(DBHelperSt301error.TB_SHOPPING_CART, new String[]{WisdomBean301error.KEY_NUM}, WisdomBean301error.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        close();
        return "1";
    }

    /**
     * ��ѯ���ݿ��еĹ��ﳵ�е���Ʒ��Ϣ
     *
     * @return ���ﳵ�е���Ʒ��Ϣ
     */
    public List<String> getProductList() {
        db = DBHelperSt301error.getInstance().getReadableDatabase();
        List<String> mList = new ArrayList<String>();
        Cursor cursor = db.query(DBHelperSt301error.TB_SHOPPING_CART, new String[]{WisdomBean301error.KEY_PRODUCT_ID}, null, null, null, null, null);
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
