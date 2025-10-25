package edu.pe.cibertec.model

import com.google.gson.annotations.SerializedName

data class PodcastResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Podcast>
)