package com.newgame.sdk.yyaost;

import m.framework.ui.widget.slidingmenu.SlidingMenu;
import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;

public abstract class SlidingMenuPage  implements Callback {
	private SlidingMenu menu;
	private View pageView;

	public SlidingMenuPage(SlidingMenu menu) {
		this.menu = menu;
		pageView = initPage();
	}

	public Context getContext() {
		return menu.getContext();
	}

	public Resources getResources() {
		return menu.getResources();
	}

	public boolean isMenuShown() {
		return menu.isMenuShown();
	}

	public void hideMenu() {
		menu.hideMenu();
	}

	public void showMenu() {
		menu.showMenu();
	}

	public View findViewById(int id) {
		return menu.findViewById(id);
	}

	protected abstract View initPage();

	/** 获取页面的View实例 */
	public View getPage() {
		return pageView;
	}

	public boolean handleMessage(Message msg) {
		return false;
	}
}
