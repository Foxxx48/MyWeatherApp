package com.fox.myweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class Rain (
    @SerializedName("1h")
    var h : Double? = null

)
