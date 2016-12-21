package com.ybg.av.at.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var catalogList: MutableList<Catalog> = ArrayList<Catalog>()
    private lateinit var mCatalogAdapter: CatalogAdapter
    private var siteKey = "tb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCatalog()
        loadingData()
    }

    private fun loadingData() {
        SendRequest.listCatalog(this, siteKey, object : OkCallback<String>(OkStringParser()){

            override fun onSuccess(code: Int, response: String) {
                val jsonBean = JSonResultBean.fromJSON(response)
                if (jsonBean != null && jsonBean.isSuccess) {
                    val data = GsonUtil.createGson().fromJson<List<Catalog>>(jsonBean.data,
                            object : TypeToken<List<Catalog>>(){}.type)
                    if (data.isNotEmpty()) {
                        catalogList.clear()
                        catalogList.addAll(data)
                        mCatalogAdapter.notifyDataSetChanged()
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
                println(e.message)
                toast(e.message ?: "出错了")
                e.printStackTrace()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_tb) {
            siteKey = "tb"
            loadingData()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //初始化图片选择框
    private fun initCatalog() {
        val decoration = SpacesItemDecoration(16)
        rv_catalog_list.addItemDecoration(decoration)
        //设置RecyclerView布局管理器为2列垂直排布
        rv_catalog_list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        mCatalogAdapter = CatalogAdapter(this)
        mCatalogAdapter.setDataList(catalogList)
        mCatalogAdapter.setOnItemClickListener(object : RecyclerBaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                VideoListActivity.start(this@MainActivity, catalogList[position].id)
            }
        })

        rv_catalog_list.adapter = mCatalogAdapter
    }
}
