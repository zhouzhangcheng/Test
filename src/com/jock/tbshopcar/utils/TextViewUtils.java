package com.jock.tbshopcar.utils;

import android.text.TextUtils;
import android.widget.TextView;

public class TextViewUtils {
	public static void setText(TextView tv, String str) {
		if (!TextUtils.isEmpty(str)) {
			tv.setText(str);
		} else {
			tv.setText("");
		}
	}

	public static void setText(TextView tv, String str, String leftStr) {
		if (!TextUtils.isEmpty(str)) {
			tv.setText(leftStr + str);
		} else {
			tv.setText(leftStr);
		}
	}

	public static void setText(TextView tv, String str, String leftStr,
			String rightStr) {
		if (!TextUtils.isEmpty(str)) {
			tv.setText(leftStr + str + rightStr);
		} else {
			tv.setText(leftStr + rightStr);
		}
	}

	public static void setTextRight(TextView tv, String str, String rightStr) {
		if (!TextUtils.isEmpty(str)) {
			tv.setText(str + rightStr);
		} else {
			tv.setText(rightStr);
		}
	}

	public static void setText(TextView tv, String str, int end, String rightStr) {
		if (!TextUtils.isEmpty(str)) {
			tv.setText(str.substring(0, end) + rightStr);
		} else {
			tv.setText("0" + rightStr);
		}
	}

	public static String delPoint(String str) {
		if (str.endsWith(".0") || str.endsWith(".00")) {
			int index = str.indexOf('.');
			return str.substring(0, index);
		}
		return str;
	}
}
