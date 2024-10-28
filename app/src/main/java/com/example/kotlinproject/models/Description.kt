package com.example.kotlinproject.models

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en") val en: String,
    @SerializedName("es-la") val esLa: String,    // Переименовываем в CamelCase
    @SerializedName("ja") val ja: String,
    @SerializedName("pt-br") val ptBr: String,    // Переименовываем в CamelCase
    @SerializedName("vi") val vi: String,
    @SerializedName("zh") val zh: String
)