package edu.pe.cibertec.model

import com.google.gson.annotations.SerializedName

data class Podcast(
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("artworkUrl160")
    val artWorkl100: String?,
    @SerializedName("previewURL")
    val previewUrl: String?
)