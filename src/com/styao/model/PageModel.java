package com.styao.model;

public class PageModel {
	private String PageIndex;
	private String PageNumber;
	private String PrePageNumber;
	private String NextPageNumber;
	private String PageSize;
	private String TotalPages;
	private String HasPrePage;
	private String HasNextPage;
	private String IsFirstPage;
	private String IsLastPage;

	public String getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(String pageIndex) {
		PageIndex = pageIndex;
	}

	public String getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(String pageNumber) {
		PageNumber = pageNumber;
	}

	public String getPrePageNumber() {
		return PrePageNumber;
	}

	public void setPrePageNumber(String prePageNumber) {
		PrePageNumber = prePageNumber;
	}

	public String getNextPageNumber() {
		return NextPageNumber;
	}

	public void setNextPageNumber(String nextPageNumber) {
		NextPageNumber = nextPageNumber;
	}

	public String getPageSize() {
		return PageSize;
	}

	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	}

	public String getTotalPages() {
		return TotalPages;
	}

	public void setTotalPages(String totalPages) {
		TotalPages = totalPages;
	}

	public String getHasPrePage() {
		return HasPrePage;
	}

	public void setHasPrePage(String hasPrePage) {
		HasPrePage = hasPrePage;
	}

	public String getHasNextPage() {
		return HasNextPage;
	}

	public void setHasNextPage(String hasNextPage) {
		HasNextPage = hasNextPage;
	}

	public String getIsFirstPage() {
		return IsFirstPage;
	}

	public void setIsFirstPage(String isFirstPage) {
		IsFirstPage = isFirstPage;
	}

	public String getIsLastPage() {
		return IsLastPage;
	}

	public void setIsLastPage(String isLastPage) {
		IsLastPage = isLastPage;
	}

}
