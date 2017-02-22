package com.jock.tbshopcar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jock.tbshopcar.entity.ShoppingCartBean;
import com.jock.tbshopcar.entity.WisdomBean301error;

/**
 * 数据库帮助类
 *
 * 
 */
public class DBHelperSt301error extends SQLiteOpenHelper {

    /** 数据库名称常量 */
    public static final String DATABASE_NAME = "wisdom_st301error.db3";
    /** 数据库版本常量 */
    private static final int DATABASE_VERSION = 3;
    /** 智慧采购表-不能采购 */
    public static final String TB_SHOPPING_CART = "tb_wisdom_st301error";

    private static DBHelperSt301error helper;

    private static Context APPLICATION_CONTEXT;

    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    public DBHelperSt301error() {
        super(APPLICATION_CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelperSt301error getInstance() {
        if (helper == null) {
            helper = new DBHelperSt301error();
        }
        return helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_SHOPPING_CART = "create table " + TB_SHOPPING_CART + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WisdomBean301error.KEY_PRODUCT_ID + " text,"
                + WisdomBean301error.KEY_NUM + " text"
                + ");";
        db.execSQL(CREATE_TB_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_SHOPPING_CART);
        onCreate(db);
    }

}
