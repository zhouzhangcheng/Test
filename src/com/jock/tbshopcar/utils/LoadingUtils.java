package com.jock.tbshopcar.utils;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.R.color;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

public class LoadingUtils {

	public static void showLoading(View v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.VISIBLE);
			v.findViewById(R.id.loading_background).setBackgroundColor(
					Color.TRANSPARENT);
			GifImageView gif = (GifImageView) v.findViewById(R.id.gif);
			GifDrawable gifDrawable = new GifDrawable(v.getResources(),
					R.drawable.loading);
			gif.setImageDrawable(gifDrawable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void showLoading(Activity v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.VISIBLE);
			v.findViewById(R.id.loading_background).setBackgroundColor(
					Color.TRANSPARENT);
			GifImageView gif = (GifImageView) v.findViewById(R.id.gif);
			GifDrawable gifDrawable = new GifDrawable(v.getResources(),
					R.drawable.loading);
			gif.setImageDrawable(gifDrawable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void showInitLoading(View v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.VISIBLE);
			v.findViewById(R.id.loading_background).setBackgroundColor(
					Color.WHITE);
			GifImageView gif = (GifImageView) v.findViewById(R.id.gif);
			GifDrawable gifDrawable = new GifDrawable(v.getResources(),
					R.drawable.loading);
			gif.setImageDrawable(gifDrawable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void showInitLoading(Activity v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.VISIBLE);
			v.findViewById(R.id.loading_background).setBackgroundColor(
					Color.WHITE);
			GifImageView gif = (GifImageView) v.findViewById(R.id.gif);
			GifDrawable gifDrawable = new GifDrawable(v.getResources(),
					R.drawable.loading);
			gif.setImageDrawable(gifDrawable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void closeLoading(Activity v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.GONE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void closeLoading(View v) {
		try {
			v.findViewById(R.id.loading_background).setVisibility(View.GONE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
