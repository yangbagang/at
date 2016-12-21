package com.ybg.av.at.http

import android.content.Context
import com.ybg.av.at.http.callback.OkCallback

object SendRequest {

    /**
     * 1.1获取分类列表
     *
     * @param mobile 手机号
     */
    fun listCatalog(tag: Context, siteKey: String, callback: OkCallback<*>) {
        val params = mapOf<String, String>("siteKey" to siteKey)
        OkHttpProxy.post(HttpUrl.listCatalog, tag, params, callback)
    }

    /**
     * 1.2获取文章列表
     *
     * @param mobile 手机号
     * @param captcha  验证码的值
     */

    fun listTitle(tag: Context, catalogId: Int, pageNum: Int, callback: OkCallback<*>) {
        val params = mapOf<String, Int>("catalogId" to catalogId, "pageNum" to pageNum)
        OkHttpProxy.post(HttpUrl.listTitle, tag, params, callback)
    }

    /**
     * 1.3查看文章详情
     *
     * @param mobile 手机号
     * @param password  密码
     */
    fun viewArticle(tag: Context, catalogId: String, articleUrl: String, callback: OkCallback<*>) {
        val params = mapOf<String, String>("catalogId" to catalogId, "articleUrl" to articleUrl)
        OkHttpProxy.post(HttpUrl.viewArticle, tag, params, callback)
    }

}
