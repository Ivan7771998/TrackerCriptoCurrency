package com.example.trackercriptocurrency.model

import android.os.Parcel
import android.os.Parcelable


class CoinModel() : Parcelable {

    var asset_id: String? = null
    var name: String? = null
    var price_usd: String? = null
    var data_start: String? = null
    var data_end: String? = null
    var volume_1mth_usd: String? = null

    constructor(parcel: Parcel) : this() {
        asset_id = parcel.readString()
        name = parcel.readString()
        price_usd = parcel.readString()
        data_start = parcel.readString()
        data_end = parcel.readString()
        volume_1mth_usd = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(asset_id)
        parcel.writeString(name)
        parcel.writeString(price_usd)
        parcel.writeString(data_start)
        parcel.writeString(data_end)
        parcel.writeString(volume_1mth_usd)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinModel> {
        override fun createFromParcel(parcel: Parcel): CoinModel {
            return CoinModel(parcel)
        }

        override fun newArray(size: Int): Array<CoinModel?> {
            return arrayOfNulls(size)
        }
    }


}