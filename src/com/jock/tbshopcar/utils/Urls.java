package com.jock.tbshopcar.utils;

public class Urls {

//	 private static final String baseUrl = "http://192.168.1.7:100/";
	private static final String baseUrl = "http://api.m.sosoyy.com/";



	// ��Ʒ����ҳapi
	public static final String searchDetail = baseUrl + "webapi/searchDetail";
	// ��������
	public static final String StoreSearchResult = baseUrl + "webapi/StoreSearchResult";
	// ��Ʒ����������ʾ
	public static final String mquery = baseUrl + "webapi/mquery?q=";
	// ��������������ʾ
	public static final String mqueryForStore = baseUrl + "webapi/mqueryForStore?q=";

	// ̽���������
	public static final String CateList = baseUrl + "/webapi/CateList";
	// ̽�������������
	public static final String GetCateProducts = baseUrl + "webapi/GetCateProducts?cateId=";

//	 ������ҳ
	 public static final String StoreIndex = baseUrl
	 + "webapi/StoreIndex?storeid=";
//	public static final String StoreIndex = "http://192.168.1.106:8080/test.com/StoreIndex"
//			+ "?storeid=";




	// �����ղ�
	public static final String AddStoreToFavorite = baseUrl + "webapi/AddStoreToFavorite?storeid=";
	// ����ȡ���ղ�
	public static final String DelFavoriteStore = baseUrl + "webapi/DelFavoriteStore?storeid=";
	// ���̷���
	public static final String StoreProductAssort = baseUrl + "webapi/StoreProductAssort?storeid=";
	// ���̼��
	public static final String StoreIntroduction = baseUrl + "webapi/StoreIntroduction?storeid=";
	// ��������Ʒ����
	public static final String InStoreSearchProduct = baseUrl + "webapi/InStoreSearchProduct?storeid=";
	// ��Ʒ����
	public static final String ProductBuy = baseUrl +
			"product/ProductBuy?fromcategories=2&pid=";
	// ���а�
	public static final String searchDetailWeb = baseUrl +
			"/search/SUI_prod_rank?iskong=1&nearest=0&cateid=";

}
