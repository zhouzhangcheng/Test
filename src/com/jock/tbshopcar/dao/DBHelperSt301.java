package com.jock.tbshopcar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jock.tbshopcar.entity.WisdomBean;

/**
 * 数据库帮助类
 *
 * 
 */
public class DBHelperSt301 extends SQLiteOpenHelper {

    /** 数据库名称常量 */
    public static final String DATABASE_NAME = "wisdom_st301.db3";
    /** 数据库版本常量 */
    private static final int DATABASE_VERSION = 3;
    /** 智慧采购表 */
    public static final String TB_SHOPPING_CART = "tb_wisdom_st301";

    private static DBHelperSt301 helper;

    private static Context APPLICATION_CONTEXT;

    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    public DBHelperSt301() {
        super(APPLICATION_CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelperSt301 getInstance() {
        if (helper == null) {
            helper = new DBHelperSt301();
        }
        return helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_SHOPPING_CART = "create table " + TB_SHOPPING_CART + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WisdomBean.KEY_PRODUCT_ID + " text,"
                + WisdomBean.KEY_NUM + " text"
                + ");";
        db.execSQL(CREATE_TB_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_SHOPPING_CART);
        onCreate(db);
    }

}
