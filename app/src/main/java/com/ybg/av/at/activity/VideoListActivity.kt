package com.ybg.av.at.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import com.ybg.av.at.R
import com.ybg.av.at.adapter.CatalogAdapter
import com.ybg.av.at.adapter.RecyclerBaseAdapter
import com.ybg.av.at.bean.Catalog
import com.ybg.av.at.decoration.SpacesItemDecoration
import com.ybg.av.at.http.SendRequest
import com.ybg.av.at.http.callback.OkCallback
import com.ybg.av.at.http.parser.OkStringParser
import com.ybg.av.base.bean.JSonResultBean
import com.ybg.av.base.utils.GsonUtil
import kotlinx.android.synthetic.main.activity_video_list.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import java.util.*

class VideoListActivity : AppCompatActivity() {

    private var catalogId = 0
    private var pageNum = 1

    private var catalogList: MutableList<Catalog> = ArrayList<Catalog>()
    private lateinit var mCatalogAdapter: CatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        initView()
        initList()
        loadingData()
    }

    private fun initView() {
        if (intent != null) {
            catalogId = intent.extras.getInt("catalogId")
        }
        bt_pre.onClick {
            if (pageNum > 1) {
                pageNum -= 1
                tv_page_num.text = String.format("第%d页", pageNum)
                loadingData()
            }
        }
        bt_next.onClick {
            pageNum += 1
            tv_page_num.text = String.format("第%d页", pageNum)
            loadingData()
        }
    }

    private fun initList() {
        val decoration = SpacesItemDecoration(16)
        rv_catalog_list.addItemDecoration(decoration)
        //设置RecyclerView布局管理器为垂直排布
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_catalog_list.layoutManager = layoutManager

        mCatalogAdapter = CatalogAdapter(this)
        mCatalogAdapter.setDataList(catalogList)
        mCatalogAdapter.setOnItemClickListener(object : RecyclerBaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                VideoDetailActivity.start(this@VideoListActivity, catalogId, catalogList[position].href)
            }
        })

        rv_catalog_list.adapter = mCatalogAdapter
    }

    private fun loadingData() {
        SendRequest.listTitle(this, catalogId, pageNum, object : OkCallback<String>
        (OkStringParser()){

            override fun onSuccess(code: Int, response: String) {
                val jsonBean = JSonResultBean.fromJSON(response)
                if (jsonBean != null && jsonBean.isSuccess) {
                    val data = GsonUtil.createGson().fromJson<List<Catalog>>(jsonBean.data,
                            object : TypeToken<List<Catalog>>(){}.type)
                    if (data.isNotEmpty()) {
                        catalogList.clear()
                        catalogList.addAll(data)
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
        fun start(context: Context, catalogId: Int) {
            val intent = Intent(context, VideoListActivity::class.java)
            intent.putExtra("catalogId", catalogId)
            context.startActivity(intent)
        }
    }
}
