package com.example.trackercriptocurrency.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trackercriptocurrency.R
import com.example.trackercriptocurrency.`interface`.ILoadMore
import com.example.trackercriptocurrency.model.CoinModel
import kotlinx.android.synthetic.main.coin_layout.view.*

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var coinIcon: ImageView = itemView.coinIcon
    var coinSymbol: TextView = itemView.coinSymbol
    var name: TextView = itemView.coinName
    var priceUsd: TextView = itemView.priceUSD
    var dataStart: TextView = itemView.dataStart
    var dataEnd: TextView = itemView.dataEnd
}

class CoinAdapter(
    recyclerView: RecyclerView,
    var activity: Activity,
    var items: List<CoinModel>,
    var mapImage: Map<String, String>
) : RecyclerView.Adapter<CoinViewHolder>() {

    var loadMore: ILoadMore? = null
    var isLoading: Boolean = false
    var visibleThreshold = 5
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0

    init {
        val linearLayout = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayout.itemCount
                lastVisibleItem = linearLayout.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (loadMore != null)
                        loadMore!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

//    fun setLoadMore(loadMore: ILoadMore) {
//        this.loadMore = loadMore
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.coin_layout, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinModel = items[position]
        holder.coinSymbol.text = coinModel.asset_id
        holder.name.text = coinModel.name
        holder.priceUsd.text =
            if (coinModel.price_usd != null && coinModel.price_usd!!.length > 10) {
                coinModel.price_usd?.substring(0, 9)
            } else if (coinModel.price_usd.equals(null)) {
                "0.0"
            } else {
                coinModel.price_usd
            }
        holder.dataStart.text = coinModel.data_start
        holder.dataEnd.text = coinModel.data_end
        val path = if (mapImage.containsKey(coinModel.asset_id.toString())) {
            mapImage.getValue(coinModel.asset_id.toString())
        } else R.mipmap.ic_launcher_round
            Glide.with(activity)
                .load(path)
                .into(holder.coinIcon)

    }

    fun setLoaded() {
        isLoading = false
    }

    fun updateData(coinModels: List<CoinModel>) {
        this.items = coinModels
        notifyDataSetChanged()
    }

}