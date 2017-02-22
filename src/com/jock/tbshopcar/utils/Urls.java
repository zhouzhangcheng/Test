package com.jock.tbshopcar.utils;

public class Urls {

//	 private static final String baseUrl = "http://192.168.1.7:100/";
	private static final String baseUrl = "http://api.m.sosoyy.com/";



	// 商品搜索页api
	public static final String searchDetail = baseUrl + "webapi/searchDetail";
	// 店铺搜索
	public static final String StoreSearchResult = baseUrl + "webapi/StoreSearchResult";
	// 商品搜索下拉提示
	public static final String mquery = baseUrl + "webapi/mquery?q=";
	// 店铺搜索下拉提示
	public static final String mqueryForStore = baseUrl + "webapi/mqueryForStore?q=";

	// 探索世界分类
	public static final String CateList = baseUrl + "/webapi/CateList";
	// 探索世界分类详情
	public static final String GetCateProducts = baseUrl + "webapi/GetCateProducts?cateId=";

//	 店铺首页
	 public static final String StoreIndex = baseUrl
	 + "webapi/StoreIndex?storeid=";
//	public static final String StoreIndex = "http://192.168.1.106:8080/test.com/StoreIndex"
//			+ "?storeid=";




	// 店铺收藏
	public static final String AddStoreToFavorite = baseUrl + "webapi/AddStoreToFavorite?storeid=";
	// 店铺取消收藏
	public static final String DelFavoriteStore = baseUrl + "webapi/DelFavoriteStore?storeid=";
	// 店铺分类
	public static final String StoreProductAssort = baseUrl + "webapi/StoreProductAssort?storeid=";
	// 店铺简介
	public static final String StoreIntroduction = baseUrl + "webapi/StoreIntroduction?storeid=";
	// 店铺内商品搜索
	public static final String InStoreSearchProduct = baseUrl + "webapi/InStoreSearchProduct?storeid=";
	// 产品详情
	public static final String ProductBuy = baseUrl +
			"product/ProductBuy?fromcategories=2&pid=";
	// 排行榜
	public static final String searchDetailWeb = baseUrl +
			"/search/SUI_prod_rank?iskong=1&nearest=0&cateid=";

}
