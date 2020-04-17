package com.example.trackercriptocurrency.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.net.URL

data class ImageModel(
    @SerializedName("asset_id")
    @Expose
    var assetId: String,

    @SerializedName("url")
    @Expose
    var url: String
)