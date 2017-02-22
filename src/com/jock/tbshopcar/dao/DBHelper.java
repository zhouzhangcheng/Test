package com.jock.tbshopcar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jock.tbshopcar.entity.ShoppingCartBean;

/**
 * ���ݿ������
 *
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

    /** ���ݿ����Ƴ��� */
    public static final String DATABASE_NAME = "shopping_cart.db3";
    /** ���ݿ�汾���� */
    private static final int DATABASE_VERSION = 3;
    /** ���ﳵ�� */
    public static final String TB_SHOPPING_CART = "tb_shopping_cart";

    private static DBHelper helper;

    private static Context APPLICATION_CONTEXT;

    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    public DBHelper() {
        super(APPLICATION_CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance() {
        if (helper == null) {
            helper = new DBHelper();
        }
        return helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_SHOPPING_CART = "create table " + TB_SHOPPING_CART + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ShoppingCartBean.KEY_PRODUCT_ID + " text,"
                + ShoppingCartBean.KEY_NUM + " text"
                + ");";
        db.execSQL(CREATE_TB_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_SHOPPING_CART);
        onCreate(db);
    }

}
