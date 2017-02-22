package com.st.untill;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.newgame.sdk.yyaost.R;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;

public class StUntil {
	/**
	 * 将资质图片上传的js传入的参数解析为UID+资质ID形式
	 * 返回数组
	 * size=2
	 * return uidAndQid,uid在前
	 */
	public static int[] GetUidAndQid(String str)
	{
		int[] uidAndQid=new int[2];
		String[] str1=str.split(",");
		uidAndQid[0]=Integer.parseInt(str1[0]);
		uidAndQid[1]=Integer.parseInt(str1[1]);
		return uidAndQid;
	}
	
	//获取cookie中的uid
	public static String GetCookieUid(String cookie)
	{
		String uid="";
		if(cookie.toLowerCase().contains("uid"))
		{
			String uidstr1=cookie.split("uid")[1].split("&")[0].split("=")[1];
			if(!uidstr1.contains("-"))
			{
				uid=uidstr1;
			}
		}
		return uid;
	}
}
