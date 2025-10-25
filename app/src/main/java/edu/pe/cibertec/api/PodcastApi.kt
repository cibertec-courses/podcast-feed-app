package edu.pe.cibertec.api

import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastApi{
    @GET("search")
    suspend fun searchPodcast(
        @Query("term") term: String = "technology",
        @Query("entity")entity: String = "podcastEpisode",
        @Query("limit")limit: Int = 15
    )
}