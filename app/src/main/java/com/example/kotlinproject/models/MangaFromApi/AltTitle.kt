package com.example.kotlinproject.models.MangaFromApi

import com.google.gson.annotations.SerializedName

data class AltTitle(
    val ar: String,
    val en: String,
    val fr: String,
    val ja: String,
    @SerializedName("ja-ro") val jaRo: String,
    val ko: String,
    val ru: String,
    val vi: String,
    val zh: String
)