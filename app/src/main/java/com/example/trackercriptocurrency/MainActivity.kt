package com.example.trackercriptocurrency

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackercriptocurrency.`interface`.ILoadMore
import com.example.trackercriptocurrency.adapter.CoinAdapter
import com.example.trackercriptocurrency.common.Common
import com.example.trackercriptocurrency.model.CoinModel
import com.example.trackercriptocurrency.model.ImageModel
import com.example.trackercriptocurrency.retrofit.ServiceRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILoadMore {

    private var items: MutableList<CoinModel> = ArrayList()
    private lateinit var adapter: CoinAdapter
    var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coinRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeToRefresh.setOnRefreshListener {
            loadData()
        }
        loadData()

    }

    override fun onLoadMore() {
        if (items.size <= Common.MAX_COIN_LOAD)
        // loadData(items.size)
            Toast.makeText(this, "Data size is ${items.size}", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Data max is ${Common.MAX_COIN_LOAD}", Toast.LENGTH_SHORT).show()

    }

    private fun loadData() {
        disposable += ServiceRetrofit().api.getCoins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listData ->
                Log.e("data", listOf(listData).toString())
                disposable += ServiceRetrofit().api.getImage()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ listImage ->
                        progressBar.visibility = View.GONE
                        swipeToRefresh.visibility = View.VISIBLE
                        Log.e("dataImage", listOf(listImage).toString())
                        adapter = CoinAdapter(coinRecyclerView, this, listData, getMap(listImage))
                        coinRecyclerView.adapter = adapter
                        swipeToRefresh.isRefreshing = false
                    }, {
                        it.printStackTrace()
                    })

            }, {
                it.printStackTrace()
            })


    }

    private fun getMap(listImage: ArrayList<ImageModel>?): Map<String, String> {
        val mapImage = mutableMapOf<String, String>()
        if (listImage != null) {
            for (item in listImage) {
                mapImage[item.assetId] = item.url
            }
        }
        return mapImage
    }


}
