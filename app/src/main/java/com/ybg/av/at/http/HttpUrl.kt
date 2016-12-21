package com.ybg.av.at.http

/**
 * 网络请求相关设置,配置请求地址及参数
 */
object HttpUrl {
    private val debug = true

    //开发服务器地址
    val API_HOST_DEBUG = "http://192.168.12.99:8080/ma"
    //生产服务器地址
    val API_HOST_PRODUCT = "https://139.224.186.241:8443/ma"

    val ROOT_URL = if (debug) API_HOST_DEBUG else API_HOST_PRODUCT

    /**
     * @return 获取用户信息
     */
    val viewArticle: String
        get() = ROOT_URL + "/siteArticle/viewArticle"

    val listTitle: String
        get() = ROOT_URL + "/siteCatalog/listTitle"

    val listCatalog: String
        get() = ROOT_URL + "/siteCatalog/list"

}
