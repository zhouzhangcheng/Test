package com.jock.tbshopcar.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jock.tbshopcar.entity.ShoppingCartBean;

/**
 * SHz
 */
public class ShoppingCartDao {

    private volatile static ShoppingCartDao instance = null;
    private SQLiteDatabase db;

    /**
     * ��ȡSimpleDemoDBʵ��
     */
    public static ShoppingCartDao getInstance() {
        if (instance == null) {
            synchronized (ShoppingCartDao.class) {
                if (instance == null) {
                    instance = new ShoppingCartDao();
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
        db = DBHelper.getInstance().getReadableDatabase();
        cursor = db.rawQuery("select count(*) from " + DBHelper.TB_SHOPPING_CART, null);
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
        db = DBHelper.getInstance().getReadableDatabase();
        cursor = db.query(DBHelper.TB_SHOPPING_CART, null, ShoppingCartBean.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
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
        db = DBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingCartBean.KEY_PRODUCT_ID, productID);
        values.put(ShoppingCartBean.KEY_NUM, num);
        db.insert(DBHelper.TB_SHOPPING_CART, null, values);
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
        db = DBHelper.getInstance().getReadableDatabase();
        db.delete(DBHelper.TB_SHOPPING_CART, ShoppingCartBean.KEY_PRODUCT_ID + " =?", new String[]{productID});
        close();
    }

    public void delAllGoods() {
        db = DBHelper.getInstance().getReadableDatabase();
        db.delete(DBHelper.TB_SHOPPING_CART, null, null);
        close();
    }

    public void deleteGoodList(List<String> goodList) {
        if (goodList == null) {
            return;
        }
        db = DBHelper.getInstance().getReadableDatabase();
        for (int i = 0; i < goodList.size(); i++) {
            db.delete(DBHelper.TB_SHOPPING_CART, ShoppingCartBean.KEY_PRODUCT_ID + " =?", new String[]{goodList.get(i)});
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
        db = DBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        if (productID != null && !"".equals(productID) && num != null && !"".equals(num)) {
            values.put("num", num);
            db.update(DBHelper.TB_SHOPPING_CART, values, ShoppingCartBean.KEY_PRODUCT_ID + "=?", new String[]{productID});
        }
        close();
    }

    public String getNumByProductID(String productID) {
        if (productID == null) {
            return "1";
        }
        db = DBHelper.getInstance().getReadableDatabase();
        cursor = db.query(DBHelper.TB_SHOPPING_CART, new String[]{ShoppingCartBean.KEY_NUM}, ShoppingCartBean.KEY_PRODUCT_ID + "=?", new String[]{productID}, null, null, null);
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
        db = DBHelper.getInstance().getReadableDatabase();
        List<String> mList = new ArrayList<String>();
        Cursor cursor = db.query(DBHelper.TB_SHOPPING_CART, new String[]{ShoppingCartBean.KEY_PRODUCT_ID}, null, null, null, null, null);
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
