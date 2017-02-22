package com.styao.model;

import java.util.List;

public class StoreBean {
	private String Word;
	private String PageIndex;
	private PageModel PageModel;
	private List<BaseInfoList> BaseInfoList;

	public String getWord() {
		return Word;
	}

	public PageModel getPageModel() {
		return PageModel;
	}

	public void setPageModel(PageModel pageModel) {
		PageModel = pageModel;
	}

	public List<BaseInfoList> getBaseInfoList() {
		return BaseInfoList;
	}

	public void setBaseInfoList(List<BaseInfoList> baseInfoList) {
		BaseInfoList = baseInfoList;
	}

	public void setWord(String word) {
		Word = word;
	}

	public String getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(String pageIndex) {
		PageIndex = pageIndex;
	}

}
