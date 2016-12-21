package com.ybg.av.at.adapter

import android.content.Context
import android.widget.TextView
import com.ybg.av.at.R
import com.ybg.av.at.bean.Catalog

/**
 * Created by yangbagang on 2016/12/12.
 */
class CatalogAdapter(context: Context) : RecyclerBaseAdapter<Catalog>(context) {

    private var catalog_name: TextView? = null

    override val rootResource: Int
        get() = R.layout.item_catalog_index

    override fun getView(viewHolder: BaseViewHolder, item: Catalog?, position: Int) {
        catalog_name = viewHolder.getView(R.id.tv_catalog_name)
        if (item != null && catalog_name != null) {
            catalog_name?.text = item.catalogName
        }
    }
}