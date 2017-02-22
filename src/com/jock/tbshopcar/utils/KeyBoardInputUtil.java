package com.jock.tbshopcar.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardInputUtil {
	public static void closeInput(Context context,View v){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //Ç¿ÖÆÒþ²Ø¼üÅÌ  
	}
}
