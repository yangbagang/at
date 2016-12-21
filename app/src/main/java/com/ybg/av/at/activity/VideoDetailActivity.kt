package com.ybg.av.at.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.ybg.av.at.R
import com.ybg.av.at.bean.Article
import com.ybg.av.at.http.SendRequest
import com.ybg.av.at.http.callback.OkCallback
import com.ybg.av.at.http.parser.OkStringParser
import com.ybg.av.base.bean.JSonResultBean
import com.ybg.av.base.utils.GsonUtil
import kotlinx.android.synthetic.main.activity_video_detail.*
import org.jetbrains.anko.toast

class VideoDetailActivity : AppCompatActivity() {

    private var catalogId: Int = 0
    private var articleHref: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        initView()
        loadingData()
    }

    private fun initView() {
        if (intent != null) {
            catalogId = intent.extras.getInt("catalogId")
            articleHref = intent.extras.getString("href")
        }
    }

    private fun loadingData() {
        SendRequest.viewArticle(this, "$catalogId", articleHref, object : OkCallback<String>
        (OkStringParser()){
            override fun onSuccess(code: Int, response: String) {
                val jsonBean = JSonResultBean.fromJSON(response)
                if (jsonBean != null && jsonBean.isSuccess) {
                    val data = GsonUtil.createGson().fromJson<List<Article>>(jsonBean.data,
                            object : TypeToken<List<Article>>(){}.type)
                    if (data.isNotEmpty()) {
                        val article = data.first()
                        tv_url.text = article.href
                    } else {
                        toast("没有数据")
                    }
                } else {
                    jsonBean?.let {
                        toast(jsonBean.errorMsg)
                    }
                }
            }

            override fun onFailure(e: Throwable) {
                toast(e.message ?: "出错了")
            }
        })
    }

    companion object {
        fun start(context: Context, catalogId: Int, href: String) {
            val intent = Intent(context, VideoDetailActivity::class.java)
            intent.putExtra("catalogId", catalogId)
            intent.putExtra("href", href)
            context.startActivity(intent)
        }
    }
}
