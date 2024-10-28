package com.example.kotlinproject.models

import com.google.gson.annotations.SerializedName

data class AltTitle(
    @SerializedName("en") val en: String,
    @SerializedName("es-la") val esLa: String,
    @SerializedName("fr") val fr: String,
    @SerializedName("ja") val ja: String,
    @SerializedName("ja-ro") val jaRo: String,
    @SerializedName("ko") val ko: String,
    @SerializedName("nl") val nl: String,
    @SerializedName("pt-br") val ptBr: String,
    @SerializedName("ru") val ru: String,
    @SerializedName("th") val th: String,
    @SerializedName("vi") val vi: String,
    @SerializedName("zh") val zh: String
)