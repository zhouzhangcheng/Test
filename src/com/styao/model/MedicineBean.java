package com.styao.model;

import java.util.List;

public class MedicineBean {

    private String pageIndex;
    private String recordCount;
    private String PageCount;
    private List<MedicineListBean>  list;
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getPageCount() {
		return PageCount;
	}
	public void setPageCount(String pageCount) {
		PageCount = pageCount;
	}
	public List<MedicineListBean> getList() {
		return list;
	}
	public void setList(List<MedicineListBean> list) {
		this.list = list;
	}
    
}
