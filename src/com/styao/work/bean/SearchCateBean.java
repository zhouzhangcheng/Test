package com.styao.work.bean;

import java.util.List;

public class SearchCateBean {
	private String CateName;
	private String CateId;
	private String ImagesUrl;
	private List<SearchCateBean> CateList;
	public String getCateName() {
		return CateName;
	}
	public void setCateName(String cateName) {
		CateName = cateName;
	}
	public String getCateId() {
		return CateId;
	}
	public void setCateId(String cateId) {
		CateId = cateId;
	}
	public String getImagesUrl() {
		return ImagesUrl;
	}
	public void setImagesUrl(String imagesUrl) {
		ImagesUrl = imagesUrl;
	}
	public List<SearchCateBean> getCateList() {
		return CateList;
	}
	public void setCateList(List<SearchCateBean> cateList) {
		CateList = cateList;
	}
	
}
