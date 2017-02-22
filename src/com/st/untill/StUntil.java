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
	 * ������ͼƬ�ϴ���js����Ĳ�������ΪUID+����ID��ʽ
	 * ��������
	 * size=2
	 * return uidAndQid,uid��ǰ
	 */
	public static int[] GetUidAndQid(String str)
	{
		int[] uidAndQid=new int[2];
		String[] str1=str.split(",");
		uidAndQid[0]=Integer.parseInt(str1[0]);
		uidAndQid[1]=Integer.parseInt(str1[1]);
		return uidAndQid;
	}
	
	//��ȡcookie�е�uid
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
