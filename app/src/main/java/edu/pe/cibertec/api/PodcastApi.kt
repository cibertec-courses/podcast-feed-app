package edu.pe.cibertec.api

import edu.pe.cibertec.model.PodcastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastApi {
    @GET("search")
    suspend fun searchPodcasts(
        @Query("term") term: String = "technology",
        @Query("entity") entity: String = "podcastEpisode",
        @Query("limit") limit: Int = 10
    ): PodcastResponse
}