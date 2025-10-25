package edu.pe.cibertec.model

import com.google.gson.annotations.SerializedName

data class Podcast(
    @SerializedName("trackName")
    val trackName: String?,

    @SerializedName("artworkUrl160")
    val artworkUrl100: String?,

    @SerializedName("previewUrl")
    val previewUrl: String?
)