package com.styao.model;

import java.util.List;

public class ProductBean {
	private String Word;
	private String TotalCount;
	private String PageIndex;
	private String PageCount;
	private String StoreClassInfo;
	private PageModel PageModel;

	private List<BaseInfoList> BaseInfoList;

	public List<BaseInfoList> getBaseInfoList() {
		return BaseInfoList;
	}

	public void setBaseInfoList(List<BaseInfoList> baseInfoList) {
		BaseInfoList = baseInfoList;
	}

	public PageModel getPageModel() {
		return PageModel;
	}

	public void setPageModel(PageModel pageModel) {
		PageModel = pageModel;
	}

	public String getWord() {
		return Word;
	}

	public void setWord(String word) {
		Word = word;
	}

	public String getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(String totalCount) {
		TotalCount = totalCount;
	}

	public String getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(String pageIndex) {
		PageIndex = pageIndex;
	}

	public String getPageCount() {
		return PageCount;
	}

	public void setPageCount(String pageCount) {
		PageCount = pageCount;
	}

	public String getStoreClassInfo() {
		return StoreClassInfo;
	}

	public void setStoreClassInfo(String storeClassInfo) {
		StoreClassInfo = storeClassInfo;
	}
}
