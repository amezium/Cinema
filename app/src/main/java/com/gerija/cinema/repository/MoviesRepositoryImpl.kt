package com.gerija.cinema.repository

import com.gerija.cinema.model.network.api.ApiService
import com.gerija.cinema.model.network.dto.DescriptionContainerDto
import com.gerija.cinema.model.network.dto.MoviesContainerDto
import com.gerija.cinema.model.network.dto.VideoContainerDto

class MoviesRepositoryImpl(private val apiService: ApiService) : MoviesRepository {


    override suspend fun getTopMovies(): Result<MoviesContainerDto> {
        return Result.success(apiService.getTopMovies())

    }

    override suspend fun getMoviesDetails(id: Int): Result<DescriptionContainerDto> {
        return Result.success(apiService.getMoviesDetails(id))
    }

    override suspend fun getVideo(id: Int): Result<VideoContainerDto> {
        return Result.success(apiService.getVideo(id))
    }

}