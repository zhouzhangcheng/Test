package com.jock.tbshopcar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.WisdomBean303;

/**
 * ���ݿ������
 *
 * 
 */
public class DBHelperSt303 extends SQLiteOpenHelper {

    /** ���ݿ����Ƴ��� */
    public static final String DATABASE_NAME = "wisdom_st303.db3";
    /** ���ݿ�汾���� */
    private static final int DATABASE_VERSION = 3;
    /** �ǻ۲ɹ���-303 */
    public static final String TB_SHOPPING_CART = "tb_wisdom_st303";

    private static DBHelperSt303 helper;

    private static Context APPLICATION_CONTEXT;

    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    public DBHelperSt303() {
        super(APPLICATION_CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelperSt303 getInstance() {
        if (helper == null) {
            helper = new DBHelperSt303();
        }
        return helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_SHOPPING_CART = "create table " + TB_SHOPPING_CART + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WisdomBean303.KEY_PRODUCT_ID + " text,"
                + WisdomBean303.KEY_NUM + " text"
                + ");";
        db.execSQL(CREATE_TB_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_SHOPPING_CART);
        onCreate(db);
    }

}
