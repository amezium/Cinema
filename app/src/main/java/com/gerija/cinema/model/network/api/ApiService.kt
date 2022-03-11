package com.gerija.cinema.model.network.api

import com.gerija.cinema.model.network.dto.DescriptionContainerDto
import com.gerija.cinema.model.network.dto.MoviesContainerDto
import com.gerija.cinema.model.network.dto.VideoContainerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getTopMovies(
        @Query("api_key") api_key: String = KEY,
        @Query("language") language: String = "ru"
    ): MoviesContainerDto

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String = KEY,
        @Query("language") language: String = "ru"
    ): DescriptionContainerDto

    @GET("movie/{movie_id}/{videos}")
    suspend fun getVideo(
        @Path("movie_id") id: Int,
        @Path("videos") videos: String = "videos",
        @Query("api_key") api_key: String = "d9ca6800f61b80298e342b05311fea59",
        @Query("language") language: String = "ru"
    ): VideoContainerDto

    companion object {

        private const val KEY = "d9ca6800f61b80298e342b05311fea59"

    }
}